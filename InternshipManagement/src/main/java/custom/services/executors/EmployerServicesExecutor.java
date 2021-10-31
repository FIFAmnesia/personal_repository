package custom.services.executors;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.implementations.InternshipDataStore;
import datastore.implementations.OfferDataStore;
import datastore.implementations.RequestDataStore;
import entities.Internship;
import entities.Offer;
import entities.Request;
import responses.abstracts.ServiceResponse;
import rest.service.implementations.InternshipCrudService;
import rest.service.implementations.RequestCrudService;

@Stateless
public class EmployerServicesExecutor {

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private RequestCrudService requestCrudService;

  @EJB
  private InternshipDataStore internshipDataStore;

  @EJB
  private OfferDataStore offerDataStore;

  @EJB
  private InternshipCrudService internshipCrudService;

  public List<Offer> getActiveOffers(Long companyId) {
    return offerDataStore.findActiveOffers(companyId);
  }

  public List<Request> getPendingRequests(Long companyId) {
    return requestDataStore.findPendingRequests(companyId);
  }

  public ServiceResponse<Request> updateRequest(Long requestId, boolean approved) {
    Request request = requestDataStore.findTree(requestId);
    request.setApproved(approved);
    return requestCrudService.update(request, requestId);
  }

  public List<Internship> getAllInternships(Long companyId) {
    return internshipDataStore.findAllInternshipsForCompany(companyId);
  }

  public ServiceResponse<Internship> updateInternship(Internship rec, Long internshipId) {
    return internshipCrudService.update(rec, internshipId);
  }

}
