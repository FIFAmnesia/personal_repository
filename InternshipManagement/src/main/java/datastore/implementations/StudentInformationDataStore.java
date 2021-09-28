package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.StudentInformation;

@Stateless
public class StudentInformationDataStore extends AbstractDataStore<StudentInformation> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<StudentInformation> getEntityClass() {
    return StudentInformation.class;
  }

  @Override
  public StudentInformation find(Long id) {
    return entityManager.find(StudentInformation.class, id);
  }

  @Override
  public List<StudentInformation> findList() {
    TypedQuery<StudentInformation> typedQuery = entityManager
        .createQuery("SELECT si FROM StudentInformation si", StudentInformation.class);
    return typedQuery.getResultList();
  }

  @Override
  public StudentInformation findTree(Long id) {
    TypedQuery<StudentInformation> typedQuery = entityManager
        .createQuery("SELECT si FROM StudentInformation si "
            + "LEFT JOIN FETCH si.user u "
            + "LEFT JOIN FETCH u.role "
            + "WHERE si.id = :id", StudentInformation.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<StudentInformation> findListTree() {
    TypedQuery<StudentInformation> typedQuery = entityManager
        .createQuery("SELECT si FROM StudentInformation si "
            + "LEFT JOIN FETCH si.user u "
            + "LEFT JOIN FETCH u.role", StudentInformation.class);
    return typedQuery.getResultList();
  }

}
