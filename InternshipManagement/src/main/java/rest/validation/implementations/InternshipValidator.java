package rest.validation.implementations;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import custom.helpers.MailHelper;
import custom.helpers.RequestHelper;
import datastore.implementations.InternshipDataStore;
import datastore.implementations.OfferDataStore;
import datastore.implementations.UserDataStore;
import entities.Internship;
import entities.Offer;
import entities.User;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class InternshipValidator extends AbstractEntityValidator<Internship> {

  @EJB
  private UserDataStore userDateStore;

  @EJB
  private OfferDataStore offerDateStore;

  @EJB
  private InternshipDataStore internshipDataStore;

  @EJB
  private RequestHelper requestHelper;

  @EJB
  private MailHelper mailHelper;

  @Override
  public void validatePreRead(List<Internship> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Internship validatePreCreate(Internship dto) throws ValidationException {
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
    Offer offer = offerDateStore.findOfferWithCompanyAndJobDescription(offerId);

    if (user == null && offer == null) {
      throw new ValidationException("User with id: " + userId + " and Offer with id: " + offerId + " don't exist, please provide different ids!");
    }
    if (user == null) {
      throw new ValidationException("User with id: " + userId + " doesn't exist, please prvoide a different id!");
    }
    if (offer == null) {
      throw new ValidationException("Offer with id: " + offerId + " doesn't exist, please prvoide a different id!");
    }

    Long internshipsForCurrentAcademicYear = internshipDataStore.countInternshipsForCurrentAcademicYear(userId, user.getStudentInformation().getCurrentAcademicYear());
    if (internshipsForCurrentAcademicYear > 0) {
      String firstName = user.getFirstName();
      String lastName = user.getLastName();
      String facultyNumber = user.getStudentInformation().getFacultyNumber();
      Long currentAcademicYear = user.getStudentInformation().getCurrentAcademicYear();
      throw new ValidationException("Student " + firstName + " " + lastName + " with faculty number: " + facultyNumber + " already has an ongoing or completed internship for academic year: " + currentAcademicYear + "! Good luck!");
    }

    dto.setUser(user);
    dto.setOffer(offer);
    dto.setAcedemicYear(user.getStudentInformation().getCurrentAcademicYear());
    dto.setCompleted(false);
    dto.setNote(null);

    return dto;
  }

  @Override
  public Internship validatePreUpdate(Internship dto, Internship ent) throws ValidationException {
    if (dto.getNote() != null) {
      dto.setCompleted(true);
    }

    return dto;
  }

  @Override
  public void validatePreDelete(Internship ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Internship> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Internship validatePostCreate(Internship dto, Internship ent) throws ValidationException {
    Offer offer = ent.getOffer();
    Long newRemainingPositions = offer.getRemainingPositions() - 1;
    offer.setRemainingPositions(newRemainingPositions);

    if (newRemainingPositions.equals(0L)) {
      offer.setActive(false);
      requestHelper.declineRemainingRequestsForOffer(offer);
    }

    offerDateStore.merge(offer);
    offerDateStore.flush();

    mailHelper.handleEmailsForAcceptedInternship(ent);

    return dto;
  }

  @Override
  public Internship validatePostUpdate(Internship dto, Internship ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Internship ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
