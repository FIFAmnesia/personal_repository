package custom.services.rest;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import aaa.service.interceptors.ParameterValueValidationInterceptor;
import custom.services.handlers.StudentServicesHandler;

@Path("/services/student")
public class StudentServicesRest {

  @EJB
  private StudentServicesHandler studentServicesHandler;

  @Path("/offers")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Interceptors(value = {ParameterValueValidationInterceptor.class})
  public Response getActiveOffers(@QueryParam("userId") Long userId, @QueryParam("position") String position, @QueryParam("technology") String technology) {
    return studentServicesHandler.getActiveOffers(userId, position, technology);
  }

  @Path("/requests")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getApprovedRequests(@QueryParam("userId") Long userId) {
    return studentServicesHandler.getApprovedRequests(userId);
  }

  @Path("/internships")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllInternships(@QueryParam("userId") Long userId) {
    return studentServicesHandler.getAllInternships(userId);
  }

}
