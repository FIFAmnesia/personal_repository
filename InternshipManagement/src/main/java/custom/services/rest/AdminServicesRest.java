package custom.services.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import custom.services.handlers.AdminServicesHandler;

@Path("/services/admin")
public class AdminServicesRest {

  @EJB
  private AdminServicesHandler adminServicesHandler;

  @Path("/internships")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllInternships(@QueryParam("facultyNumber") String facultyNumber) {
    return adminServicesHandler.getAllInternships(facultyNumber);
  }

}
