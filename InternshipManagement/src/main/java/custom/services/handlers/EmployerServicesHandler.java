package custom.services.handlers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import custom.services.executors.EmployerServicesExecutor;
import entities.Internship;
import entities.Request;
import responses.abstracts.ServiceResponse;
import responses.implementations.InternshipStoreResponse;
import responses.implementations.RequestStoreResponse;

@Stateless
public class EmployerServicesHandler {

  @EJB
  private EmployerServicesExecutor employerServicesExecutor;

  public Response getPendingRequests(Long companyId) {
    RequestStoreResponse response = new RequestStoreResponse();

    List<Request> requests = employerServicesExecutor.getPendingRequests(companyId);
    response.setRecords(requests);
    response.setSuccess(true);
    response.setTotalCount(new Long(requests.size()));
    if (response.getTotalCount().equals(0L)) {
      response.setMessage("No unapproved requests left for active offers! Thanks for handling the available ones!");
    }

    return Response.status(Status.OK).entity(response).build(); 
  }

  public Response updateRequest(Long requestId, boolean approved) {
    ServiceResponse<Request> response = employerServicesExecutor.updateRequest(requestId, approved);
    return Response.status(response.getStatus()).entity(response.getResponse().getRecords().get(0)).build();
  }

  public Response getAllInternships(Long companyId) {
    InternshipStoreResponse response = new InternshipStoreResponse();

    List<Internship> internships = employerServicesExecutor.getAllInternships(companyId);
    response.setRecords(internships);
    response.setSuccess(true);
    response.setTotalCount(new Long(internships.size()));
    if (response.getTotalCount().equals(0L)) {
      response.setMessage("No completed or ongoing internships at this moment of time!");
    }

    return Response.status(Status.OK).entity(response).build(); 
  }

  public Response updateInternship(Internship rec, Long internshipId) {
    ServiceResponse<Internship> response = employerServicesExecutor.updateInternship(rec, internshipId);
    return Response.status(response.getStatus()).entity(response.getResponse().getRecords().get(0)).build();
  }

}
