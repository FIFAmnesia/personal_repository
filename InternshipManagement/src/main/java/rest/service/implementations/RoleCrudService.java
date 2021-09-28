package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.RoleDataStore;
import entities.Role;
import responses.implementations.RoleStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.RoleValidator;

@Stateless
public class RoleCrudService extends AbstractCrudService<Role, RoleStoreResponse> {

  @EJB
  private RoleDataStore roleDataStore;

  @EJB
  private RoleValidator roleValidator;

  @Override
  protected DataStore<Role> getDataStore() {
    return roleDataStore;
  }

  @Override
  protected Class<RoleStoreResponse> getResponseClass() {
    return RoleStoreResponse.class;
  }

  @Override
  protected EntityValidator<Role> getValidator() {
    return roleValidator;
  }

}
