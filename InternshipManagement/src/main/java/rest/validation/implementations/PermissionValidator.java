package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.Permission;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class PermissionValidator extends AbstractEntityValidator<Permission> {

  @Override
  public void validatePreRead(List<Permission> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Permission validatePreCreate(Permission dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Permission validatePreUpdate(Permission dto, Permission ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Permission ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Permission> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Permission validatePostCreate(Permission dto, Permission ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Permission validatePostUpdate(Permission dto, Permission ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Permission ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
