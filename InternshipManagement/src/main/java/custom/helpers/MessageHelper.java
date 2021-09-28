package custom.helpers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;

import datastore.implementations.UserDataStore;
import entities.Internship;
import entities.Request;
import entities.User;
import util.Constants;

@Stateless
public class MessageHelper {

  @EJB
  private MessageTemplateHelper messageTemplateHelper;

  @EJB
  private UserDataStore userDataStore;

  private static final Logger logger = Logger.getLogger(MessageHelper.class);

  public Message getMessageForApprovedOrDeclinedRequest(Session session, Request request, boolean approved, String username) {
    Message message = null;

    String emailTo = request.getUser().getEmail();
    try {
      message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
      message.setSubject(Constants.MessageSubjects.REQUEST_APPROVAL);
      message.setText(messageTemplateHelper.getFormattedMessageForApprovedOrDeclinedRequest(request, approved));
    } catch (AddressException e) {
      logger.info("Problem occured while constructing the messages...");
    } catch (MessagingException e) {
      logger.info("Problem occured while constructing the messages...");
    }

    return message;
  }

  public List<Message> getMessagesForRequestsAfterInactiveOffer(Session session, List<Request> requests, String username) {
    List<Message> messagesToReturn = new ArrayList<>(requests.size());

    for (Request request : requests) {
      String emailTo = request.getUser().getEmail();
      try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        message.setSubject(Constants.MessageSubjects.EXPIRED_OFFER);
        message.setText(messageTemplateHelper.getFormattedMessageForRequestsAfterInactiveOffer(request));

        messagesToReturn.add(message);
      } catch (AddressException e) {
        logger.info("Problem occured while constructing the messages...");
      } catch (MessagingException e) {
        logger.info("Problem occured while constructing the messages...");
      }
    }

    return messagesToReturn;
  }

  public List<Message> getMessagesForOfferWithNoRemainingPositions(Session session, List<Request> requests, String username) {
    List<Message> messagesToReturn = new ArrayList<>(requests.size());

    for (Request request : requests) {
      String emailTo = request.getUser().getEmail();
      try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        message.setSubject(Constants.MessageSubjects.NO_REMAINING_POSITIONS_FOR_OFFER);
        message.setText(messageTemplateHelper.getFormattedMessageForOfferWithNoRemainingPositions(request));

        messagesToReturn.add(message);
      } catch (AddressException e) {
        logger.info("Problem occured while constructing the messages...");
      } catch (MessagingException e) {
        logger.info("Problem occured while constructing the messages...");
      }
    }

    return messagesToReturn;
  }

  public Message getMessageForAcceptedInternship(Session session, Internship internship, String username) {
    Message message = null;

    User employer = userDataStore.findEmployerByCompanyName(internship.getOffer().getCompany().getName());
    String emailTo = employer.getEmail();
    try {
      message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
      message.setSubject(Constants.MessageSubjects.NEW_INTERNSHIP);
      message.setText(messageTemplateHelper.getFormattedMessageForAcceptedInternship(internship));
    } catch (AddressException e) {
      logger.info("Problem occured while constructing the messages...");
    } catch (MessagingException e) {
      logger.info("Problem occured while constructing the messages...");
    }

    return message;
  }

}
