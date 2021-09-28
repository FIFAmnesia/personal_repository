package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.JobDescription;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class JobDescriptionValidator extends AbstractEntityValidator<JobDescription> {

  @Override
  public void validatePreRead(List<JobDescription> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public JobDescription validatePreCreate(JobDescription dto) throws ValidationException {
    return dto;
  }

  @Override
  public JobDescription validatePreUpdate(JobDescription dto, JobDescription ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(JobDescription ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<JobDescription> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public JobDescription validatePostCreate(JobDescription dto, JobDescription ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public JobDescription validatePostUpdate(JobDescription dto, JobDescription ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(JobDescription ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
