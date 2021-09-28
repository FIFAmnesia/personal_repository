package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.User;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class UserValidator extends AbstractEntityValidator<User> {

  @Override
  public void validatePreRead(List<User> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public User validatePreCreate(User dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public User validatePreUpdate(User dto, User ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(User ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<User> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public User validatePostCreate(User dto, User ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public User validatePostUpdate(User dto, User ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(User ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
