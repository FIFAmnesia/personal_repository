package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.Role;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class RoleValidator extends AbstractEntityValidator<Role> {

  @Override
  public void validatePreRead(List<Role> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Role validatePreCreate(Role dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Role validatePreUpdate(Role dto, Role ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Role ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Role> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Role validatePostCreate(Role dto, Role ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Role validatePostUpdate(Role dto, Role ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Role ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
