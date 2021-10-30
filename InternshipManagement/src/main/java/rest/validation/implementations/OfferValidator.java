package rest.validation.implementations;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.implementations.CompanyDataStore;
import datastore.implementations.JobDescriptionDataStore;
import entities.Company;
import entities.JobDescription;
import entities.Offer;
import exceptions.ValidationException;
import responses.abstracts.ServiceResponse;
import rest.service.implementations.JobDescriptionCrudService;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class OfferValidator extends AbstractEntityValidator<Offer> {

  @EJB
  private CompanyDataStore companyDataStore;

  @EJB
  private JobDescriptionDataStore jobDescriptionDataStore;

  @EJB
  private JobDescriptionCrudService jobDescirptionCrudService;

  @Override
  public void validatePreRead(List<Offer> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Offer validatePreCreate(Offer dto) throws ValidationException {
    Long companyId = dto.getCompanyId();
    JobDescription jobDescriptionDto = dto.getJobDescription();

    if (companyId == null
        && (jobDescriptionDto == null
            || (jobDescriptionDto != null && (jobDescriptionDto.getMainTechnology() == null || jobDescriptionDto.getPosition() == null)))) {
      throw new ValidationException("Company id and job description are not provided. An offer cannot be created!");
    }
    if (companyId == null) {
      throw new ValidationException("Company id is not provided. An offer cannot be created!");
    }
    if (jobDescriptionDto == null
        || (jobDescriptionDto != null && (jobDescriptionDto.getMainTechnology() == null || jobDescriptionDto.getPosition() == null))) {
      throw new ValidationException("Job description is not provided. An offer cannot be created!");
    }

    Company company = companyDataStore.find(companyId);
    JobDescription jobDescription = null;
    
    Boolean jobDescriptionExist = jobDescriptionDataStore.existingJobDescription(jobDescriptionDto.getPosition(), jobDescriptionDto.getMainTechnology());
    if (jobDescriptionExist) {
      jobDescription = jobDescriptionDataStore.findByPositionAndMainTechnology(jobDescriptionDto.getPosition(), jobDescriptionDto.getMainTechnology());
    } else {
      ServiceResponse<JobDescription> response = jobDescirptionCrudService.create(jobDescriptionDto);
      if (!response.getResponse().isSuccess()) {
        throw new ValidationException("The provided information about the job description couldn't be persisted: " + response.getResponse().getMessage());
      }
      jobDescription = response.getResponse().getRecords().get(0);
    }

    if (company == null) {
      throw new ValidationException("Company with id: " + companyId + " doesn't exist, please provide a different id!");
    }

    dto.setActive(true);
    dto.setCompany(company);
    dto.setJobDescription(jobDescription);

    return dto;
  }

  @Override
  public Offer validatePreUpdate(Offer dto, Offer ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Offer ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Offer> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Offer validatePostCreate(Offer dto, Offer ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Offer validatePostUpdate(Offer dto, Offer ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Offer ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
