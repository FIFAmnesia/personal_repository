package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.Company;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class CompanyValidator extends AbstractEntityValidator<Company> {

  @Override
  public void validatePreRead(List<Company> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Company validatePreCreate(Company dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Company validatePreUpdate(Company dto, Company ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Company ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Company> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Company validatePostCreate(Company dto, Company ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Company validatePostUpdate(Company dto, Company ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Company ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
