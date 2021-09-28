package custom.services.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import custom.services.handlers.EmployerServicesHandler;

@Path("/services/employer")
public class EmployerServicesRest {

  @EJB
  private EmployerServicesHandler employerServicesHandler;

  @Path("/requests")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPendingRequests(@QueryParam("companyId") Long companyId) {
    return employerServicesHandler.getPendingRequests(companyId);
  }

  @Path("/request/{requestId}/approve")
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response approveRequest(@PathParam("requestId")Long requestId) {
    return employerServicesHandler.updateRequest(requestId, true);
  }

  @Path("/request/{requestId}/decline")
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response declineRequest(@PathParam("requestId")Long requestId) {
    return employerServicesHandler.updateRequest(requestId, false);
  }

  @Path("/internships")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllInternships(@QueryParam("companyId") Long companyId) {
    return employerServicesHandler.getAllInternships(companyId);
  }

}
