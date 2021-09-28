package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.PermissionDataStore;
import entities.Permission;
import responses.implementations.PermissionStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.PermissionValidator;

@Stateless
public class PermissionCrudService extends AbstractCrudService<Permission, PermissionStoreResponse> {

  @EJB
  private PermissionDataStore permissionDataStore;

  @EJB
  private PermissionValidator permissionValidator;

  @Override
  protected DataStore<Permission> getDataStore() {
    return permissionDataStore;
  }

  @Override
  protected Class<PermissionStoreResponse> getResponseClass() {
    return PermissionStoreResponse.class;
  }

  @Override
  protected EntityValidator<Permission> getValidator() {
    return permissionValidator;
  }

}
