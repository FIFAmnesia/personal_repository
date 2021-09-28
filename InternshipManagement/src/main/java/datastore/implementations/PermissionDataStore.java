package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Permission;

@Stateless
public class PermissionDataStore extends AbstractDataStore<Permission> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Permission> getEntityClass() {
    return Permission.class;
  }

  @Override
  public Permission find(Long id) {
    return entityManager.find(Permission.class, id);
  }

  @Override
  public List<Permission> findList() {
    TypedQuery<Permission> typedQuery = entityManager
        .createQuery("SELECT p FROM Permission p", Permission.class);
    return typedQuery.getResultList();
  }

  @Override
  public Permission findTree(Long id) {
    TypedQuery<Permission> typedQuery = entityManager
        .createQuery("SELECT p FROM Permission p "
            + "LEFT JOIN FETCH p.role "
            + "WHERE p.id = :id", Permission.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Permission> findListTree() {
    TypedQuery<Permission> typedQuery = entityManager
        .createQuery("SELECT p FROM Permission p "
            + "LEFT JOIN FETCH p.role", Permission.class);
    return typedQuery.getResultList();
  }

}
