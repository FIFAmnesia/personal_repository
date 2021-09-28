package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.InternshipDataStore;
import entities.Internship;
import responses.implementations.InternshipStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.InternshipValidator;

@Stateless
public class InternshipCrudService extends AbstractCrudService<Internship, InternshipStoreResponse> {

  @EJB
  private InternshipDataStore internshipDataStore;

  @EJB
  private InternshipValidator internshipValidator;

  @Override
  protected DataStore<Internship> getDataStore() {
    return internshipDataStore;
  }

  @Override
  protected Class<InternshipStoreResponse> getResponseClass() {
    return InternshipStoreResponse.class;
  }

  @Override
  protected EntityValidator<Internship> getValidator() {
    return internshipValidator;
  }

}
