package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Credentials;

@Stateless
public class CredentialsDataStore extends AbstractDataStore<Credentials> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Credentials> getEntityClass() {
    return Credentials.class;
  }

  @Override
  public Credentials find(Long id) {
    return entityManager.find(Credentials.class, id);
  }

  @Override
  public List<Credentials> findList() {
    TypedQuery<Credentials> typedQuery = entityManager
        .createQuery("SELECT c FROM Credentials c", Credentials.class);
    return typedQuery.getResultList();
  }

  @Override
  public Credentials findTree(Long id) {
    TypedQuery<Credentials> typedQuery = entityManager
        .createQuery("SELECT c FROM Credentials c "
            + "LEFT JOIN FETCH c.user u "
            + "LEFT JOIN FETCH u.company "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role r "
            + "LEFT JOIN FETCH r.permissions "
            + "WHERE c.id = :id", Credentials.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Credentials> findListTree() {
    TypedQuery<Credentials> typedQuery = entityManager
        .createQuery("SELECT c FROM Credentials c "
            + "LEFT JOIN FETCH c.user u "
            + "LEFT JOIN FETCH u.company "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role r "
            + "LEFT JOIN FETCH r.permissions", Credentials.class);
    return typedQuery.getResultList();
  }

}
