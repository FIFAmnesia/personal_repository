package aaa.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import aaa.dto.Consumer;
import aaa.dto.LoginRequest;
import aaa.response.LoginResponse;
import aaa.service.AuthenticationService;
import aaa.service.cache.ConsumerCache;

@Path("/aaa")
public class AuthenticationRest {

  @EJB
  private AuthenticationService authenticationService;

  @EJB
  private ConsumerCache consumerCache;

  private static final Logger logger = Logger.getLogger(AuthenticationRest.class);

  @POST
  @Path("/login")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response login(LoginRequest request) {
    LoginResponse resp = new LoginResponse();

    try {
      Consumer consumer = authenticationService.authenticate(request);
      request.setConsumer(consumer);

      List<LoginRequest> records = new ArrayList<LoginRequest>();
      records.add(request);

      resp.setSuccess(Boolean.TRUE);
      resp.setRecords(records);
      resp.setTotalCount((long)records.size());
    } catch (Exception e) {

      resp.setSuccess(Boolean.FALSE);
      resp.setMessage(e.getMessage());
      logger.error("loginError: " + e.getMessage());

      return Response.status(Status.FORBIDDEN).entity(resp).build();
    }

    return Response.status(Status.OK).entity(resp).build();
  }

  @GET
  @Path("/logout")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML})
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response logout(@QueryParam("accessToken") String accessToken) {
    Boolean success = Boolean.FALSE;

    try {
      success = authenticationService.logout(accessToken);
    } catch (Exception e) {
      logger.error("Logout error: " + e.getMessage());
      return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

    if (success.equals(Boolean.FALSE)) {
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    return Response.status(Status.OK).build();
  }

// endpoint to check in debug mode the contents of the cache
//  @GET
//  @Path("/login/getCache")
//  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//  public Response getCache(LoginRequest request) {
//    Set<Consumer> consumers = consumerCache.getConsumers();
//    Map<String, Consumer> tokenToConsumer = consumerCache.getTokenToConsumerRelations();
//
//    return Response.status(Status.OK).entity(new LoginResponse()).build();
//  }

}
