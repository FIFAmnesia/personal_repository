package rest.validation.implementations;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import custom.helpers.MailHelper;
import datastore.implementations.InternshipDataStore;
import datastore.implementations.OfferDataStore;
import datastore.implementations.RequestDataStore;
import datastore.implementations.UserDataStore;
import entities.Offer;
import entities.Request;
import entities.User;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class RequestValidator extends AbstractEntityValidator<Request> {

  @EJB
  private UserDataStore userDateStore;

  @EJB
  private OfferDataStore offerDateStore;

  @EJB
  private InternshipDataStore internshipDataStore;

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private MailHelper mailHelper;

  @Override
  public void validatePreRead(List<Request> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Request validatePreCreate(Request dto) throws ValidationException {
    Long userId = dto.getUserId();
    Long offerId = dto.getOfferId();

    if (userId == null && offerId == null) {
      throw new ValidationException("User id and Offer id are not provided. A request cannot be created!");
    }
    if (userId == null) {
      throw new ValidationException("User id is not provided. A request cannot be created!");
    }
    if (offerId == null) {
      throw new ValidationException("Offer id is not provided. A request cannot be created!");
    }

    User user = userDateStore.findStudentWithInformation(userId);
    Offer offer = offerDateStore.find(offerId);
    
    if (user == null && offer == null) {
      throw new ValidationException("User with id: " + userId + " and Offer with id: " + offerId + " don't exist, please provide different ids!");
    }
    if (user == null) {
      throw new ValidationException("User with id: " + userId + " doesn't exist, please prvoide a different id!");
    }
    if (offer == null) {
      throw new ValidationException("Offer with id: " + offerId + " doesn't exist, please prvoide a different id!");
    }

    Boolean existingRecord = requestDataStore.existingRequest(userId, offerId);
    if (existingRecord) {
      throw new ValidationException("There already exist a request with userId: " + userId + " and offerId: " + offerId);
    }

    Long internshipsForCurrentAcademicYear = internshipDataStore.countInternshipsForCurrentAcademicYear(userId, user.getStudentInformation().getCurrentAcademicYear());
    if (internshipsForCurrentAcademicYear > 0) {
      String firstName = user.getFirstName();
      String lastName = user.getLastName();
      String facultyNumber = user.getStudentInformation().getFacultyNumber();
      Long currentAcademicYear = user.getStudentInformation().getCurrentAcademicYear();
      throw new ValidationException("Student " + firstName + " " + lastName + " with faculty number: " + facultyNumber + " already has an ongoing or completed internship for academic year: " + currentAcademicYear + "! Any further request cannot be made for the current year!");
    }

    dto.setUser(user);
    dto.setOffer(offer);
    dto.setApproved(null);
    dto.setMadeOn(new Date());

    return dto;
  }

  @Override
  public Request validatePreUpdate(Request dto, Request ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Request ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Request> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Request validatePostCreate(Request dto, Request ent) throws ValidationException {

    return dto;
  }

  @Override
  public Request validatePostUpdate(Request dto, Request ent) throws ValidationException {
    if (dto.getApproved() != null) {
      mailHelper.handleEmailsForApprovedOrDeclinedRequest(ent, dto.getApproved());
    }
    return dto;
  }

  @Override
  public void validatePostDelete(Request ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
