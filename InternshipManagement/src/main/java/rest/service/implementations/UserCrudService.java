package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.UserDataStore;
import entities.User;
import responses.implementations.UserStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.UserValidator;

@Stateless
public class UserCrudService extends AbstractCrudService<User, UserStoreResponse> {

  @EJB
  private UserDataStore userDataStore;

  @EJB
  private UserValidator userValidator;

  @Override
  protected DataStore<User> getDataStore() {
    return userDataStore;
  }

  @Override
  protected Class<UserStoreResponse> getResponseClass() {
    return UserStoreResponse.class;
  }

  @Override
  protected EntityValidator<User> getValidator() {
    return userValidator;
  }

}
