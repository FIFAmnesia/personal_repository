package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.JobDescription;

@Stateless
public class JobDescriptionDataStore extends AbstractDataStore<JobDescription> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<JobDescription> getEntityClass() {
    return JobDescription.class;
  }

  @Override
  public JobDescription find(Long id) {
    return entityManager.find(JobDescription.class, id);
  }

  @Override
  public List<JobDescription> findList() {
    TypedQuery<JobDescription> typedQuery = entityManager
        .createQuery("SELECT jd FROM JobDescription jd", JobDescription.class);
    return typedQuery.getResultList();
  }

  @Override
  public JobDescription findTree(Long id) {
    TypedQuery<JobDescription> typedQuery = entityManager
        .createQuery("SELECT jd FROM JobDescription jd "
            + "LEFT JOIN FETCH jd.offers o "
            + "LEFT JOIN FETCH o.company "
            + "WHERE jd.id = :id", JobDescription.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<JobDescription> findListTree() {
    TypedQuery<JobDescription> typedQuery = entityManager
        .createQuery("SELECT jd FROM JobDescription jd "
            + "LEFT JOIN FETCH jd.offers o "
            + "LEFT JOIN FETCH o.company", JobDescription.class);
    return typedQuery.getResultList();
  }

  public JobDescription findByPositionAndMainTechnology(String position, String mainTechnology) {
    TypedQuery<JobDescription> typedQuery = entityManager
        .createQuery("SELECT jd FROM JobDescription jd "
            + "WHERE jd.position = :position "
            + "AND jd.mainTechnology = :mainTechnology", JobDescription.class);
    typedQuery.setParameter("position", position);
    typedQuery.setParameter("mainTechnology", mainTechnology);
    return typedQuery.getSingleResult();
  }

  public Boolean existingJobDescription(String position, String mainTechnology) {
    TypedQuery<Long> typedQuery = entityManager
        .createQuery("SELECT COUNT(jd) FROM JobDescription jd "
            + "WHERE jd.position = :position "
            + "AND jd.mainTechnology = :mainTechnology", Long.class);
    typedQuery.setParameter("position", position);
    typedQuery.setParameter("mainTechnology", mainTechnology);
    return typedQuery.getSingleResult() > 0;
  }

}
