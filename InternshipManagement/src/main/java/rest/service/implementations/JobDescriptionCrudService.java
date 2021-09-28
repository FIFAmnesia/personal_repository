package rest.service.implementations;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.abstracts.DataStore;
import datastore.implementations.JobDescriptionDataStore;
import entities.JobDescription;
import responses.implementations.JobDescriptionStoreResponse;
import rest.service.abstracts.AbstractCrudService;
import rest.validation.abstracts.EntityValidator;
import rest.validation.implementations.JobDescriptionValidator;

@Stateless
public class JobDescriptionCrudService extends AbstractCrudService<JobDescription, JobDescriptionStoreResponse> {

  @EJB
  private JobDescriptionDataStore jobDescriptionDataStore;

  @EJB
  private JobDescriptionValidator jobDescriptionValidator;

  @Override
  protected DataStore<JobDescription> getDataStore() {
    return jobDescriptionDataStore;
  }

  @Override
  protected Class<JobDescriptionStoreResponse> getResponseClass() {
    return JobDescriptionStoreResponse.class;
  }

  @Override
  protected EntityValidator<JobDescription> getValidator() {
    return jobDescriptionValidator;
  }

}
