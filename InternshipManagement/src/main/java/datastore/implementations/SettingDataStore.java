package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Setting;

@Stateless
public class SettingDataStore extends AbstractDataStore<Setting> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Setting> getEntityClass() {
    return Setting.class;
  }

  @Override
  public Setting find(Long id) {
    return entityManager.find(Setting.class, id);
  }

  @Override
  public List<Setting> findList() {
    TypedQuery<Setting> typedQuery = entityManager
        .createQuery("SELECT s FROM Setting s", Setting.class);
    return typedQuery.getResultList();
  }

  @Override
  public Setting findTree(Long id) {
    return this.find(id);
  }

  @Override
  public List<Setting> findListTree() {
    return this.findList();
  }

  public String findByName(String name) {
    TypedQuery<String> typedQuery = entityManager
        .createQuery("SELECT value FROM Setting s "
            + "WHERE s.name = :name", String.class);
    typedQuery.setParameter("name", name);
    return typedQuery.getSingleResult();
  }

}
