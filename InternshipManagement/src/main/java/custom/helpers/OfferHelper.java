package custom.helpers;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import datastore.implementations.OfferDataStore;
import entities.Offer;

@Stateless
public class OfferHelper {

  @EJB
  private OfferDataStore offerDataStore;

  private static final Logger logger = Logger.getLogger(OfferHelper.class);

  public void handleExpiredOffers(List<Offer> expiredOffers) {
    logger.info("Initiated expiration of offers!");
    for (Offer offer : expiredOffers) {
      offer.setActive(false);
      offerDataStore.merge(offer);
      logger.info("Deactivated offer with id: " + offer.getId() + "; The offer was active until : " + offer.getActiveUntilDate() + " but the current date is: " + new Date());
    }

    offerDataStore.flush();
  }

}
