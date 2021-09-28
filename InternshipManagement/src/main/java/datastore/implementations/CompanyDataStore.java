package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Company;

@Stateless
public class CompanyDataStore extends AbstractDataStore<Company> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Company> getEntityClass() {
    return Company.class;
  }

  @Override
  public Company find(Long id) {
    return entityManager.find(Company.class, id);
  }

  @Override
  public List<Company> findList() {
    TypedQuery<Company> typedQuery = entityManager
        .createQuery("SELECT c FROM Company c", Company.class);
    return typedQuery.getResultList();
  }

  @Override
  public Company findTree(Long id) {
    TypedQuery<Company> typedQuery = entityManager
        .createQuery("SELECT c FROM Company c "
            + "LEFT JOIN FETCH c.users u "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH c.offers o "
            + "LEFT JOIN FETCH o.jobDescription "
            + "LEFT JOIN FETCH o.requests "
            + "WHERE c.id = :id", Company.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Company> findListTree() {
    TypedQuery<Company> typedQuery = entityManager
        .createQuery("SELECT c FROM Company c "
            + "LEFT JOIN FETCH c.users u "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH c.offers o "
            + "LEFT JOIN FETCH o.jobDescription "
            + "LEFT JOIN FETCH o.requests", Company.class);
    return typedQuery.getResultList();
  }

}
