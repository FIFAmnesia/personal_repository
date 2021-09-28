package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Role;

@Stateless
public class RoleDataStore extends AbstractDataStore<Role> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Role> getEntityClass() {
    return Role.class;
  }

  @Override
  public Role find(Long id) {
    return entityManager.find(Role.class, id);
  }

  @Override
  public List<Role> findList() {
    TypedQuery<Role> typedQuery = entityManager
        .createQuery("SELECT r FROM Role r", Role.class);
    return typedQuery.getResultList();
  }

  @Override
  public Role findTree(Long id) {
    TypedQuery<Role> typedQuery = entityManager
        .createQuery("SELECT r FROM Role r "
            + "LEFT JOIN FETCH r.users "
            + "LEFT JOIN FETCH r.permissions "
            + "WHERE r.id = :id", Role.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Role> findListTree() {
    TypedQuery<Role> typedQuery = entityManager
        .createQuery("SELECT r FROM Role r "
            + "LEFT JOIN FETCH r.users "
            + "LEFT JOIN FETCH r.permissions", Role.class);
    return typedQuery.getResultList();
  }

}
