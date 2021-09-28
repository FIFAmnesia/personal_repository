package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.Setting;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class SettingValidator extends AbstractEntityValidator<Setting> {

  @Override
  public void validatePreRead(List<Setting> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public Setting validatePreCreate(Setting dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Setting validatePreUpdate(Setting dto, Setting ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(Setting ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<Setting> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Setting validatePostCreate(Setting dto, Setting ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public Setting validatePostUpdate(Setting dto, Setting ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(Setting ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
