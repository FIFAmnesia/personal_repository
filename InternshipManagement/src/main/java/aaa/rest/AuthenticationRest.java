package aaa.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import aaa.dto.Consumer;
import aaa.dto.LoginRequest;
import aaa.response.LoginResponse;
import aaa.service.AuthenticationService;

@Path("/aaa")
public class AuthenticationRest {

  @EJB
  AuthenticationService authenticationService;

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
    } catch (Exception e) {

      resp.setSuccess(Boolean.FALSE);
      resp.setMessage(e.getMessage());
      logger.error("loginError: " + e.getMessage());

      return Response.status(Status.FORBIDDEN).entity(resp).build();
    }
    return Response.status(Status.OK).entity(resp).build();

  }

}