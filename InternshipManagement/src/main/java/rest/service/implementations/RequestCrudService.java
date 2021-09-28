package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.RequestDataStore;
import entities.Request;
import responses.implementations.RequestStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.RequestValidator;

@Stateless
public class RequestCrudService extends AbstractCrudService<Request, RequestStoreResponse> {

  @EJB
  private RequestDataStore requestDataStore;

  @EJB
  private RequestValidator requestValidator;

  @Override
  protected DataStore<Request> getDataStore() {
    return requestDataStore;
  }

  @Override
  protected Class<RequestStoreResponse> getResponseClass() {
    return RequestStoreResponse.class;
  }

  @Override
  protected EntityValidator<Request> getValidator() {
    return requestValidator;
  }

}
