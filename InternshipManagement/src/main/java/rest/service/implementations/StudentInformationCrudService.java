package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.StudentInformationDataStore;
import entities.StudentInformation;
import responses.implementations.StudentInformationStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.StudentInformationValidator;

@Stateless
public class StudentInformationCrudService extends AbstractCrudService<StudentInformation, StudentInformationStoreResponse> {

  @EJB
  private StudentInformationDataStore studentInformationDataStore;

  @EJB
  private StudentInformationValidator studentInformationValidator;

  @Override
  protected DataStore<StudentInformation> getDataStore() {
    return studentInformationDataStore;
  }

  @Override
  protected Class<StudentInformationStoreResponse> getResponseClass() {
    return StudentInformationStoreResponse.class;
  }

  @Override
  protected EntityValidator<StudentInformation> getValidator() {
    return studentInformationValidator;
  }

}
