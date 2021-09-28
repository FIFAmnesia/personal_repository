package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.CredentialsDataStore;
import entities.Credentials;
import responses.implementations.CredentialsStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.CredentialsValidator;

@Stateless
public class CredentialsCrudService extends AbstractCrudService<Credentials, CredentialsStoreResponse> {

  @EJB
  private CredentialsDataStore credentialsDataStore;

  @EJB
  private CredentialsValidator credentialsValidator;

  @Override
  protected DataStore<Credentials> getDataStore() {
    return credentialsDataStore;
  }

  @Override
  protected Class<CredentialsStoreResponse> getResponseClass() {
    return CredentialsStoreResponse.class;
  }

  @Override
  protected EntityValidator<Credentials> getValidator() {
    return credentialsValidator;
  }

}
