package custom.services.executors;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.implementations.InternshipDataStore;
import datastore.implementations.RequestDataStore;
import entities.Internship;
import entities.Request;
import responses.abstracts.ServiceResponse;
import rest.service.implementations.RequestCrudService;

@Stateless
public class EmployerServicesExecutor {

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private RequestCrudService requestCrudService;

  @EJB
  private InternshipDataStore internshipDataStore;

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

}
