package custom.services.handlers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import custom.services.executors.AdminServicesExecutor;
import entities.Internship;
import responses.implementations.InternshipStoreResponse;

@Stateless
public class AdminServicesHandler {
  
  @EJB
  private AdminServicesExecutor adminServicesExecutor;

  public Response getAllInternships(String facultyNumber) {
    InternshipStoreResponse response = new InternshipStoreResponse();

    List<Internship> internships = adminServicesExecutor.getAllInternships(facultyNumber);
    response.setRecords(internships);
    response.setSuccess(true);
    response.setTotalCount(new Long(internships.size()));
    if (response.getTotalCount().equals(0L)) {
      response.setMessage("No completed or ongoing internships at this moment of time!");
    }

    return Response.status(Status.OK).entity(response).build(); 
  }

}