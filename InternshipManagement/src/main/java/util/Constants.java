package util;

public class Constants {

  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

  public static class MessageTemplates{
    public static final String APPROVED_REQUEST = "{firstName} {lastName}, your request for an internship in company {companyName} has been approved!";
    public static final String DECLINED_REQUEST = "{firstName} {lastName}, your request for an internship in company {companyName} has been declined. Good luck in future endeavours!";
    public static final String INACTIVE_OFFER = "{firstName} {lastName}, your request for an internship in company {companyName} has been automatically declined due the offer expiring!";
    public static final String NO_REMAINING_POSITIONS_FOR_OFFER = "{firstName} {lastName}, your request for an internship in company {companyName} has been declined due no more available positions!";
    public static final String ACCEPTED_INTERNSHIP = "{firstName} {lastName} has accepted to have an internship in your company. For more information about the student check your account!";
  }

  public static class MessageSubjects{
    public static final String REQUEST_APPROVAL = "Request approval";
    public static final String EXPIRED_OFFER = "Expired offer";
    public static final String NO_REMAINING_POSITIONS_FOR_OFFER = "No more remaining positions for offer";
    public static final String NEW_INTERNSHIP = "New internship in your company";
  }

}
