package aaa.service.interceptors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.specimpl.BuiltResponse;

import aaa.dto.Consumer;
import aaa.service.cache.ConsumerCache;
import datastore.implementations.InternshipDataStore;
import datastore.implementations.RequestDataStore;
import datastore.implementations.SettingDataStore;
import entities.Internship;
import entities.Request;
import exceptions.ValidationException;
import rest.service.abstracts.GenericEntity;

@Provider
public class ParameterValueValidationInterceptor {

  @Inject
  private HttpServletRequest httpRequest;

  @EJB
  private ConsumerCache consumerCache;

  @EJB
  private SettingDataStore settingDataStore;

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private InternshipDataStore internshipDataStore;

  private static String systemToken = null;

  private final static Logger log = Logger.getLogger(ParameterValueValidationInterceptor.class);

  @AroundInvoke
  public Object checkPermission(InvocationContext inv) throws Exception {
    BuiltResponse authorizationResponse = (BuiltResponse) authorizationCheck(inv);

    if (authorizationResponse.getStatus() != 200) {
      return authorizationResponse;
    }

    return inv.proceed();
  }

  private Object authorizationCheck(InvocationContext inv) {
    if (systemToken == null) {
      systemToken = settingDataStore.findByName("aaa.system.token");
    }

    Object fetchTokenResponse = fetchToken();
    String token = fetchTokenResponse.toString();

    if (systemToken != null && systemToken.equals(token)) {
      return Response.status(Response.Status.OK).build();
    }

    Map<String, Consumer> relations = consumerCache.getTokenToConsumerRelations();
    if (relations.containsKey(token)) {

      Consumer consumer = relations.get(token);
      if (consumer == null) {
        log.info("Consumer is null");
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      try {
        validateParameters(consumer, inv);
      } catch (ValidationException e) {
        log.info(e.getMessage());
        return Response.status(Status.UNAUTHORIZED).build();
      } catch (IllegalArgumentException | IllegalAccessException e) {
        return Response.status(Status.UNAUTHORIZED).build();
      }

      return Response.status(Response.Status.OK).build();
    }

    log.info("Token DOES NOT exist: user is not logged in...");
    return Response.status(Response.Status.UNAUTHORIZED).build();
  }

  /**
   * Get the token from the request headers, if there is no such header - UNAUTHORIZED; if the header is not a valid Basic or OAuth authorization - BAD_REQEUST
   *
   * @return
   */
  private Object fetchToken() {
    String token = httpRequest.getHeader("Authorization");
    String paramToken = httpRequest.getParameter("Authorization");

    log.debug("Token: " + token);

    String[] tokenArr;
    if (token == null || token.isEmpty()) {
      if (paramToken == null || paramToken.isEmpty()) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      } else {
        tokenArr = paramToken.split(" ");
      }
    } else {
      tokenArr = token.split(" ");
    }

    if (!tokenArr[0].equals("Basic") && !tokenArr[0].equals("OAuth") && tokenArr.length != 2) {
      return Response.status(Response.Status.BAD_REQUEST).entity("No token provided!").build();
    }

    token = tokenArr[1];
    return token;
  }

  private void validateParameters(Consumer consumer, InvocationContext inv) throws ValidationException, IllegalArgumentException, IllegalAccessException {
    Method method = inv.getMethod();
    Object[] invParameters = inv.getParameters();

    if (method.isAnnotationPresent(POST.class)) {
      Object entityObject = invParameters[0];
      if (GenericEntity.class.isAssignableFrom(entityObject.getClass())) {
        Field[] fields = entityObject.getClass().getDeclaredFields();

        Object userId = extractFieldValue("userId", fields, entityObject);
        if (userId != null) {
          validateUser(userId, consumer);
        }

        Object companyId = extractFieldValue("companyId", fields, entityObject);
        if (companyId != null) {
          validateCompany(companyId, consumer);
        }
      }
    }

    if (method.isAnnotationPresent(GET.class) || method.isAnnotationPresent(PUT.class)) {
      Parameter[] methodParameters = method.getParameters();
      for (int i = 0; i < methodParameters.length; ++i) {
        String paramName = extractParamNameFromAnnotations(methodParameters[i]);
        Object paramValue = invParameters[i];

        if (paramName.equals("userId")) {
          validateUser(paramValue, consumer);
        }

        if (paramName.equals("companyId")) {
          validateCompany(paramValue, consumer);
        }

        if (paramName.equals("requestId")) {
          Request request = requestDataStore.findTree((Long) paramValue);
          validateCompany(request.getOffer().getCompany().getId(), consumer);
        }

        if (paramName.equals("internshipId")) {
          Internship internship = internshipDataStore.findTree((Long) paramValue);
          validateCompany(internship.getOffer().getCompany().getId(), consumer);
        }
      }
    }
  }

  private void validateUser(Object userId, Consumer consumer) throws ValidationException {
    if (!userId.equals(consumer.getUser().getId())) {
      throw new ValidationException("The user's id doesn't match the provided userId...");
    }
  }

  private void validateCompany(Object companyId, Consumer consumer) throws ValidationException {
    if (!companyId.equals(consumer.getUser().getCompany().getId())) {
      throw new ValidationException("The user's companyId doesn't match the provided companyId...");
    }
  }

  private Object extractFieldValue(String fieldName, Field[] fields, Object entity) throws IllegalArgumentException, IllegalAccessException {
    Object returnFieldValue = null;

    for (Field field : fields) {
      if (field.getName().equals(fieldName)) {
        field.setAccessible(true);
        Object fieldValue = field.get(entity);
        if (fieldValue != null) {
          returnFieldValue = fieldValue;
          break;
        }
      }
    }

    return returnFieldValue;
  }

  private String extractParamNameFromAnnotations(Parameter parameter) throws IllegalAccessException, IllegalArgumentException {
    // Keep just because it took time, but it's very much unnecessary
    // Object annotationValue = annotation.annotationType().getMethod("value").invoke(annotation);
    String paramName = "";
    QueryParam qp = parameter.getAnnotation(QueryParam.class);
    PathParam pp = parameter.getAnnotation(PathParam.class);
    if (qp != null) {
      paramName = qp.value();
    } else if (pp != null) {
      paramName = pp.value();
    }

    return paramName;
  }

}
