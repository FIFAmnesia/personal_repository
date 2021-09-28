package custom.services.executors;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.implementations.InternshipDataStore;
import datastore.implementations.OfferDataStore;
import datastore.implementations.RequestDataStore;
import entities.Internship;
import entities.Offer;
import entities.Request;

@Stateless
public class StudentServicesExecutor {

  @EJB
  private OfferDataStore offerDataStore;

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private InternshipDataStore internshipDataStore;

  public List<Offer> getActiveOffers(Long userId, String position, String technology) {
    List<Long> requestedOfferIds = new ArrayList<>();
    List<Request> requestsByUser = requestDataStore.findRequestsByUser(userId);
    if (requestsByUser != null && !requestsByUser.isEmpty()) {
      requestsByUser.forEach(r -> requestedOfferIds.add(r.getOfferId()));
    }

    return offerDataStore.findActiveOffers(position, technology, requestedOfferIds);
  }

  public List<Request> getApprovedRequests(Long userId) {
    return requestDataStore.findApprovedRequests(userId);
  }

  public List<Internship> getAllInternships(Long userId) {
    return internshipDataStore.findAllInternshipsForUser(userId);
  }

}
