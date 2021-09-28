package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.SettingDataStore;
import entities.Setting;
import responses.implementations.SettingStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.SettingValidator;

@Stateless
public class SettingCrudService extends AbstractCrudService<Setting, SettingStoreResponse> {

  @EJB
  private SettingDataStore settingDataStore;

  @EJB
  private SettingValidator settingValidator;

  @Override
  protected DataStore<Setting> getDataStore() {
    return settingDataStore;
  }

  @Override
  protected Class<SettingStoreResponse> getResponseClass() {
    return SettingStoreResponse.class;
  }

  @Override
  protected EntityValidator<Setting> getValidator() {
    return settingValidator;
  }

}
