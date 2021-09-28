package custom.helpers;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import org.jboss.logging.Logger;

import datastore.implementations.SettingDataStore;
import entities.Internship;
import entities.Request;

@Stateless
public class MailHelper {

  @EJB
  private SettingDataStore settingDataStore;

  @EJB
  private MessageHelper messageHelper;

  private static Properties properties;

  private static String USERNAME;

  private static String PASSWORD;

  private static final Logger logger = Logger.getLogger(MailHelper.class);

  @PostConstruct
  public void init() {
    if (properties == null) {
      properties = populateProperties();
    }

    if (USERNAME == null) {
      USERNAME = settingDataStore.getSettingValue("email_sender_username");
    }

    if (PASSWORD == null) {
      PASSWORD = settingDataStore.getSettingValue("email_sender_password");
    }
  }

  @Asynchronous
  public void handleEmailsForApprovedOrDeclinedRequest(Request request, boolean approved) {
    logger.info("MailHelper asynch method initiated for handling of emails for an approved or declined request by employer...");

    Session session = getSessionInstance();
    Message message = messageHelper.getMessageForApprovedOrDeclinedRequest(session, request, approved, USERNAME);

    try {
      Transport.send(message);
    } catch (MessagingException e) {
      logger.info("Sending of messages failed...");
      e.printStackTrace();
    }
  }

  @Asynchronous
  public void handleEmailsForRequestsAfterInactiveOffer(List<Request> requests) {
    logger.info("MailHelper asynch method initiated for handling of emails for request, the offer of which was deacivated by the timer service...");

    Session session = getSessionInstance();
    List<Message> messages = messageHelper.getMessagesForRequestsAfterInactiveOffer(session, requests, USERNAME);

    for (Message message : messages) {
      try {
        Transport.send(message);
      } catch (MessagingException e) {
        logger.info("Sending of messages failed...");
        e.printStackTrace();
      }
    }
  }

  @Asynchronous
  public void handleEmailsForOfferWithNoRemainingPositions(List<Request> requests) {
    logger.info("MailHelper asynch method initiated for handling of emails for request, the offer of which has no remaining positions left...");

    Session session = getSessionInstance();
    List<Message> messages = messageHelper.getMessagesForOfferWithNoRemainingPositions(session, requests, USERNAME);

    for (Message message : messages) {
      try {
        Transport.send(message);
      } catch (MessagingException e) {
        logger.info("Sending of messages failed...");
        e.printStackTrace();
      }
    }
  }

  @Asynchronous
  public void handleEmailsForAcceptedInternship(Internship internship) {
    logger.info("MailHelper asynch method initiated for handling of emails for accepted internships, the employer will be notified...");

    Session session = getSessionInstance();
    Message message = messageHelper.getMessageForAcceptedInternship(session, internship, USERNAME);

    try {
      Transport.send(message);
    } catch (MessagingException e) {
      logger.info("Sending of messages failed...");
      e.printStackTrace();
    }
  }

  private Properties populateProperties() {
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");

    return prop;
  }

  private Session getSessionInstance() {
    return Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
      }
    });
  }

}
