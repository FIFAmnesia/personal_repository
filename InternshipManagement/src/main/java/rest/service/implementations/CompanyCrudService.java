package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.CompanyDataStore;
import entities.Company;
import responses.implementations.CompanyStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.CompanyValidator;

@Stateless
public class CompanyCrudService extends AbstractCrudService<Company, CompanyStoreResponse> {

  @EJB
  private CompanyDataStore companyDataStore;

  @EJB
  private CompanyValidator companyValidator;

  @Override
  protected DataStore<Company> getDataStore() {
    return companyDataStore;
  }

  @Override
  protected Class<CompanyStoreResponse> getResponseClass() {
    return CompanyStoreResponse.class;
  }

  @Override
  protected EntityValidator<Company> getValidator() {
    return companyValidator;
  }

}
