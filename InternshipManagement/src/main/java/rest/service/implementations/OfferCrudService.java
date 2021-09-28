package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.OfferDataStore;
import entities.Offer;
import responses.implementations.OfferStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.OfferValidator;

@Stateless
public class OfferCrudService extends AbstractCrudService<Offer, OfferStoreResponse> {

  @EJB
  private OfferDataStore offerDataStore;

  @EJB
  private OfferValidator offerValidator;

  @Override
  protected DataStore<Offer> getDataStore() {
    return offerDataStore;
  }

  @Override
  protected Class<OfferStoreResponse> getResponseClass() {
    return OfferStoreResponse.class;
  }

  @Override
  protected EntityValidator<Offer> getValidator() {
    return offerValidator;
  }

}
