package custom.helpers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import datastore.implementations.InternshipDataStore;
import datastore.implementations.RequestDataStore;
import entities.Internship;
import entities.Offer;
import entities.Request;

@Stateless
public class RequestHelper {

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private InternshipDataStore internshipDataStore;

  @EJB
  private MailHelper mailHelper;

  private static final Logger logger = Logger.getLogger(RequestHelper.class);

  public void declinePendingRequestsForOffers(List<Offer> expiredOffers) {
    logger.info("Initiated refusal of pending requests!");
    List<Long> offerIds = new ArrayList<>();
    expiredOffers.forEach(offer -> offerIds.add(offer.getId()));

    //List<String> studentEmails = new ArrayList<>();
    List<Request> pendingRequestsForExpiredOffers = requestDataStore.findPendingRequestsForOffers(offerIds);
    if (pendingRequestsForExpiredOffers != null && !pendingRequestsForExpiredOffers.isEmpty()) {
      for (Request request : pendingRequestsForExpiredOffers) {
        request.setApproved(false);
        requestDataStore.merge(request);
        logger.info("Declined request with id: " + request.getId() + " for offer with id: " + request.getOfferId());
      }

      requestDataStore.flush();
      mailHelper.handleEmailsForRequestsAfterInactiveOffer(pendingRequestsForExpiredOffers);
      //pendingRequestsForExpiredOffers.forEach(request -> studentEmails.add(request.getUser().getEmail()));
    }

//    if (!studentEmails.isEmpty()) {
//      logger.info("Due to declined offers, the following students with emails: " + studentEmails + " will be notified by email!");
//      mailHelper.handleEmails(studentEmails, false);
//    }
  }

  public void declineRemainingRequestsForOffer(Offer expiredOffer) {
    logger.info("Initiated refusal of remaining requests!");
    Long offerId = expiredOffer.getId();
    //List<String> studentEmails = new ArrayList<>();
    List<Long> usersWithInternshipForOffer = new ArrayList<>();

    List<Internship> internshipsForOffer = internshipDataStore.findAllInternshipsForOffer(offerId);
    internshipsForOffer.forEach(i -> usersWithInternshipForOffer.add(i.getUserId()));

    List<Request> remainingRequestsForExpiredOffers = requestDataStore.findRemainingRequestsForOffer(offerId, usersWithInternshipForOffer);
    if (remainingRequestsForExpiredOffers != null && !remainingRequestsForExpiredOffers.isEmpty()) {
      for (Request request : remainingRequestsForExpiredOffers) {
        request.setApproved(false);
        requestDataStore.merge(request);
        logger.info("Declined request with id: " + request.getId() + " for offer with id: " + request.getOfferId());
      }

      requestDataStore.flush();
      mailHelper.handleEmailsForOfferWithNoRemainingPositions(remainingRequestsForExpiredOffers);
      //remainingRequestsForExpiredOffers.forEach(request -> studentEmails.add(request.getUser().getEmail()));
    }

//    if (!studentEmails.isEmpty()) {
//      logger.info("Due to no remaining position for offer, the following students with emails: " + studentEmails + " will be notified by email!");
//      mailHelper.handleEmails(studentEmails, false);
//    }
  }

}
