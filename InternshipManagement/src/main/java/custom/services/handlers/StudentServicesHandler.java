package custom.services.handlers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import custom.services.executors.StudentServicesExecutor;
import entities.Internship;
import entities.Offer;
import entities.Request;
import responses.implementations.InternshipStoreResponse;
import responses.implementations.OfferStoreResponse;
import responses.implementations.RequestStoreResponse;

@Stateless
public class StudentServicesHandler {

  @EJB
  private StudentServicesExecutor studentServicesExecutor;
  
  public Response getActiveOffers(Long userId, String position, String technology) {
    OfferStoreResponse response = new OfferStoreResponse();

    List<Offer> offers = studentServicesExecutor.getActiveOffers(userId, position, technology);
    response.setRecords(offers);
    response.setSuccess(true);
    response.setTotalCount(new Long(offers.size()));
    if (response.getTotalCount().equals(0L)) {
      response.setMessage("No available offers at this moment of time!");
    }

    return Response.status(Status.OK).entity(response).build(); 
  }

  public Response getApprovedRequests(Long userId) {
    RequestStoreResponse response = new RequestStoreResponse();

    List<Request> requests = studentServicesExecutor.getApprovedRequests(userId);
    response.setRecords(requests);
    response.setSuccess(true);
    response.setTotalCount(new Long(requests.size()));
    if (response.getTotalCount().equals(0L)) {
      response.setMessage("No approved requests at this moment of time!");
    }

    return Response.status(Status.OK).entity(response).build(); 
  }

  public Response getAllInternships(Long userId) {
    InternshipStoreResponse response = new InternshipStoreResponse();

    List<Internship> internships = studentServicesExecutor.getAllInternships(userId);
    response.setRecords(internships);
    response.setSuccess(true);
    response.setTotalCount(new Long(internships.size()));
    if (response.getTotalCount().equals(0L)) {
      response.setMessage("No completed or ongoing internships at this moment of time!");
    }

    return Response.status(Status.OK).entity(response).build(); 
  }

}
