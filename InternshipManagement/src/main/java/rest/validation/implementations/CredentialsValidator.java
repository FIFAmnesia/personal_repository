package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.Credentials;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class CredentialsValidator extends AbstractEntityValidator<Credentials> {

  @Override
  public void validatePreRead(List<Credentials> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Credentials validatePreCreate(Credentials dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Credentials validatePreUpdate(Credentials dto, Credentials ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Credentials ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Credentials> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Credentials validatePostCreate(Credentials dto, Credentials ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Credentials validatePostUpdate(Credentials dto, Credentials ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Credentials ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
