package aaa.service.interceptors;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.specimpl.BuiltResponse;

import aaa.dto.Consumer;
import aaa.service.cache.ConsumerCache;
import annotations.ManagedEntity;
import datastore.implementations.SettingDataStore;
import rest.service.abstracts.GenericEntity;

@Provider
public class AuthorizationServiceInterceptor {

  @Inject
  private HttpServletRequest httpRequest;

  @EJB
  private ConsumerCache consumerCache;

  @EJB
  private SettingDataStore settingDataStore;

  private static Map<String, String> methodsEntities = new HashMap<String, String>();

  private static String systemToken = null;

  private final static Logger log = Logger.getLogger(AuthorizationServiceInterceptor.class);

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

      boolean canExecute = false;
      if (canExecute(consumer, inv)) {
        canExecute = true;
      }

      if (!canExecute) {
        log.info("Does not have execute right. canExecute:  " + canExecute);
        return Response.status(Response.Status.FORBIDDEN).build();
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

  private boolean canExecute(Consumer c, InvocationContext inv) {
    Method method = inv.getMethod();

    //ManagedEntity me = method.getAnnotation(ManagedEntity.class);
    ManagedEntity me = inv.getTarget().getClass().getSuperclass().getAnnotation(ManagedEntity.class);

    if (me != null) {
      // NC: returns entity which tries to access invocation method
      String entityName = getEntityNameOfInvocationMethod(inv, me.value());

      // NC: returns interface method of invoking method

      if (method.isAnnotationPresent(GET.class)) {
        return c.canRead(entityName);
      } else if (method.isAnnotationPresent(POST.class)) {
        return c.canCreate(entityName);
      } else if (method.isAnnotationPresent(PUT.class)) {
        return c.canUpdate(entityName);
      } else if (method.isAnnotationPresent(DELETE.class)) {
        return c.canDelete(entityName);
      } else {
        log.warn("Non-REST method passed for authorization!");
        return false;
      }
    } else {
      // NC: In addition to table names, EntityEntity can contain methodNames too in the following format: NameOfClass.nameOfMethod
      return c.canExecute(getMethodNameInDbFormat(inv));
    }
  }

  private String getMethodNameInDbFormat(InvocationContext inv) {
    return inv.getTarget().getClass().getSimpleName() + '.' + inv.getMethod().getName();
  }

  private String getEntityNameOfInvocationMethod(InvocationContext inv, Class<? extends GenericEntity> entityClass) {
    String methodName = getMethodNameInDbFormat(inv);

    if (methodsEntities.containsKey(methodName)) {
      return methodsEntities.get(methodName);
    }

    String entityName = entityClass.getSimpleName();
    methodsEntities.put(methodName, entityName);

    return entityName;
  }

}
