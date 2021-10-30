package rest.validation.implementations;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;

import datastore.implementations.CompanyDataStore;
import datastore.implementations.RoleDataStore;
import entities.Company;
import entities.Credentials;
import entities.JobDescription;
import entities.Role;
import entities.StudentInformation;
import entities.User;
import exceptions.ValidationException;
import responses.abstracts.ServiceResponse;
import rest.service.implementations.StudentInformationCrudService;
import rest.validation.abstracts.AbstractEntityValidator;

@Stateless
public class UserValidator extends AbstractEntityValidator<User> {

  @EJB
  private RoleDataStore roleDataStore;

  @EJB
  private CompanyDataStore companyDataStore;

  @EJB
  private StudentInformationCrudService studentInformationCrudService;

  @Override
  public void validatePreRead(List<User> entities) throws ValidationException {
    // TODO Auto-generated method stub
  }

  @Override
  public User validatePreCreate(User dto) throws ValidationException {
    Role roleDto = dto.getRole();
    if (roleDto == null) {
      throw new ValidationException("Role is not provided for the new User. Please provide a role!");
    }
    if (roleDto.getName() == null) {
      throw new ValidationException("The name of the Role is not provided. Please provide one of the three names: admin / student / employer.");
    }

    Role role = roleDataStore.findByName(roleDto.getName());
    if (role == null) {
      throw new ValidationException("A Role with the provided name doesn't exist. Please provide one of the three names: admin / student / employer.");
    }

    dto.setRole(role);

    if (role.getName().equals("employer")) {
      Long companyId = dto.getCompanyId();
      if (companyId == null) {
        throw new ValidationException("Company id is not provided. An employer must have a company!");
      }

      Company company = companyDataStore.find(companyId);
      if (company == null) {
        throw new ValidationException("Company with id: " + companyId + " doesn't exist, please provide a different id!");
      }

      dto.setCompany(company);
    } else if (role.getName().equals("student")) {
      StudentInformation studentInformationDto = dto.getStudentInformation();

      if (studentInformationDto == null) {
        throw new ValidationException("Student information isn't provided for the new student. Please provide the additional student information.");
      }

      ServiceResponse<StudentInformation> response = studentInformationCrudService.create(studentInformationDto);
      if (!response.getResponse().isSuccess()) {
        throw new ValidationException("The provided information about the student information couldn't be persisted: " + response.getResponse().getMessage());
      }

      StudentInformation studentInformation = response.getResponse().getRecords().get(0);
      dto.setStudentInformation(studentInformation);
    }

    Credentials credentials = dto.getCredentials();
    if (credentials == null || (credentials.getUsername() == null | credentials.getPassword() == null)) {
      throw new ValidationException("Credentials (username / password) must be provided, otherwise a User cannot be created.");
    }

    credentials.setPassword(DigestUtils.sha256Hex(credentials.getPassword()));
    credentials.setUser(dto);

    dto.setCredentials(credentials);

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
    entities.forEach(user -> user.setCredentials(null));
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
