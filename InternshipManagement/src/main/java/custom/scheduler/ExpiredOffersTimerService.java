package custom.scheduler;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import org.jboss.logging.Logger;

import custom.helpers.OfferHelper;
import custom.helpers.RequestHelper;
import datastore.implementations.OfferDataStore;
import entities.Offer;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ExpiredOffersTimerService {

  @EJB
  private OfferHelper offerHelper;

  @EJB
  private RequestHelper requestHelper;

  @EJB
  private OfferDataStore offerDataStore;

  @Resource
  private TimerService ts;

  private static final Logger logger = Logger.getLogger(ExpiredOffersTimerService.class);

  private static Long timerIteration = 0L; 

  @PostConstruct
  public void init() {
    //ts.createCalendarTimer(new ScheduleExpression().hour("*").minute("*").second("*/10"), new TimerConfig("ExpireOffers", false));
    ts.createCalendarTimer(new ScheduleExpression().hour("0").minute("0").second("1"), new TimerConfig("ExpireOffers", false));
  }

  @Timeout
  public void actionScheduler(Timer t) {
    logger.info("Timer: " + t.getInfo() + "; interval: 10s " + "iteration: " + ++timerIteration);

    List<Offer> expiredOffers = offerDataStore.findExpiredOffers();
    if (expiredOffers != null && !expiredOffers.isEmpty()) {
      offerHelper.handleExpiredOffers(expiredOffers);
      requestHelper.declinePendingRequestsForOffers(expiredOffers);
    }
  }

}
