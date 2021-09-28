package custom.helpers;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang3.text.StrSubstitutor;

import entities.Internship;
import entities.Request;
import util.Constants;

@Stateless
public class MessageTemplateHelper {

  public String getFormattedMessageForApprovedOrDeclinedRequest(Request request, boolean approved) {
    String template = null;

    if (approved) {
      template = Constants.MessageTemplates.APPROVED_REQUEST;
    } else {
      template = Constants.MessageTemplates.DECLINED_REQUEST;
    }

    Map<String, String> values = new HashMap<>();
    values.put("firstName", request.getUser().getFirstName());
    values.put("lastName", request.getUser().getLastName());
    values.put("companyName", request.getOffer().getCompany().getName());

    return StrSubstitutor.replace(template, values, "{", "}");
  }

  public String getFormattedMessageForRequestsAfterInactiveOffer(Request request) {
    String template = Constants.MessageTemplates.INACTIVE_OFFER;

    Map<String, String> values = new HashMap<>();
    values.put("firstName", request.getUser().getFirstName());
    values.put("lastName", request.getUser().getLastName());
    values.put("companyName", request.getOffer().getCompany().getName());

    return StrSubstitutor.replace(template, values, "{", "}");
  }

  public String getFormattedMessageForOfferWithNoRemainingPositions(Request request) {
    String template = Constants.MessageTemplates.NO_REMAINING_POSITIONS_FOR_OFFER;

    Map<String, String> values = new HashMap<>();
    values.put("firstName", request.getUser().getFirstName());
    values.put("lastName", request.getUser().getLastName());
    values.put("companyName", request.getOffer().getCompany().getName());

    return StrSubstitutor.replace(template, values, "{", "}");
  }

  public String getFormattedMessageForAcceptedInternship(Internship internship) {
    String template = Constants.MessageTemplates.ACCEPTED_INTERNSHIP;

    Map<String, String> values = new HashMap<>();
    values.put("firstName", internship.getUser().getFirstName());
    values.put("lastName", internship.getUser().getLastName());

    return StrSubstitutor.replace(template, values, "{", "}");
  }

}
