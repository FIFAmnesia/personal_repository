package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.User;

@Stateless
public class UserDataStore extends AbstractDataStore<User> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<User> getEntityClass() {
    return User.class;
  }

  @Override
  public User find(Long id) {
    return entityManager.find(User.class, id);
  }

  @Override
  public List<User> findList() {
    TypedQuery<User> typedQuery = entityManager
        .createQuery("SELECT u FROM User u", User.class);
    return typedQuery.getResultList();
  }

  @Override
  public User findTree(Long id) {
    TypedQuery<User> typedQuery = entityManager
        .createQuery("SELECT u FROM User u "
            + "LEFT JOIN FETCH u.company "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role r "
            + "LEFT JOIN FETCH r.permissions "
            + "LEFT JOIN FETCH u.credentials "
            + "LEFT JOIN FETCH u.requests req "
            + "LEFT JOIN FETCH req.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "LEFT JOIN FETCH u.internships "
            + "WHERE u.id = :id", User.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<User> findListTree() {
    TypedQuery<User> typedQuery = entityManager
        .createQuery("SELECT u FROM User u "
            + "LEFT JOIN FETCH u.company "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH u.credentials "
            + "LEFT JOIN FETCH u.requests r "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "LEFT JOIN FETCH u.internships", User.class);
    return typedQuery.getResultList();
  }

  public User findStudentWithInformation(Long id) {
    TypedQuery<User> typedQuery = entityManager
        .createQuery("SELECT u FROM User u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "WHERE u.id = :id", User.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  public User findEmployerByCompanyName(String companyName) {
    TypedQuery<User> typedQuery = entityManager
        .createQuery("SELECT u FROM User u "
            + "LEFT JOIN FETCH u.company c "
            + "WHERE c.name = :companyName", User.class);
    typedQuery.setParameter("companyName", companyName);
    return typedQuery.getSingleResult();
  }

  public User findUserForAuthentication(String username) {
    TypedQuery<User> typedQuery = entityManager
        .createQuery("SELECT u FROM User u "
            + "LEFT JOIN FETCH u.company "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role r "
            + "LEFT JOIN FETCH r.permissions "
            + "LEFT JOIN FETCH u.credentials c "
            + "WHERE c.username = :username", User.class);
    typedQuery.setParameter("username", username);
    return typedQuery.getSingleResult();
  }

}
