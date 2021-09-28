package rest.validation.implementations;

import java.util.List;

import javax.ejb.Stateless;

import entities.StudentInformation;
import exceptions.ValidationException;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class StudentInformationValidator extends AbstractEntityValidator<StudentInformation> {

  @Override
  public void validatePreRead(List<StudentInformation> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public StudentInformation validatePreCreate(StudentInformation dto) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public StudentInformation validatePreUpdate(StudentInformation dto, StudentInformation ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePreDelete(StudentInformation ent) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public void validatePostRead(List<StudentInformation> entities) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public StudentInformation validatePostCreate(StudentInformation dto, StudentInformation ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public StudentInformation validatePostUpdate(StudentInformation dto, StudentInformation ent) throws ValidationException {
    // TODO Auto-generated method stub
    return dto;
  }

  @Override
  public void validatePostDelete(StudentInformation ent) throws ValidationException {
    // TODO Auto-generated method stub
    
  }

}
