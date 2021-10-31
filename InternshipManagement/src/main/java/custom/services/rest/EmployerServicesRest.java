package custom.services.rest;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import aaa.service.interceptors.ParameterValueValidationInterceptor;
import custom.services.handlers.EmployerServicesHandler;
import entities.Internship;

@Path("/services/employer")
public class EmployerServicesRest {

  @EJB
  private EmployerServicesHandler employerServicesHandler;

  @Path("/offers")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActiveOffers(@QueryParam("companyId") Long companyId) {
    return employerServicesHandler.getActiveOffers(companyId);
  }

  @Path("/requests")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPendingRequests(@QueryParam("companyId") Long companyId) {
    return employerServicesHandler.getPendingRequests(companyId);
  }

  @Path("/request/{requestId}/approve")
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Interceptors(value = {ParameterValueValidationInterceptor.class})
  public Response approveRequest(@PathParam("requestId") Long requestId) {
    return employerServicesHandler.updateRequest(requestId, true);
  }

  @Path("/request/{requestId}/decline")
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response declineRequest(@PathParam("requestId") Long requestId) {
    return employerServicesHandler.updateRequest(requestId, false);
  }

  @Path("/internships")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllInternships(@QueryParam("companyId") Long companyId) {
    return employerServicesHandler.getAllInternships(companyId);
  }

  @Path("/internship/{internshipId}")
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response declineRequest(Internship rec, @PathParam("internshipId") Long internshipId) {
    return employerServicesHandler.updateInternship(rec, internshipId);
  }

}
