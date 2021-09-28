package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Offer;

@Stateless
public class OfferDataStore extends AbstractDataStore<Offer> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Offer> getEntityClass() {
    return Offer.class;
  }

  @Override
  public Offer find(Long id) {
    return entityManager.find(Offer.class, id);
  }

  @Override
  public List<Offer> findList() {
    TypedQuery<Offer> typedQuery = entityManager
        .createQuery("SELECT o FROM Offer o", Offer.class);
    return typedQuery.getResultList();
  }

  @Override
  public Offer findTree(Long id) {
    TypedQuery<Offer> typedQuery = entityManager
        .createQuery("SELECT o FROM Offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "LEFT JOIN FETCH o.requests r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH o.internships i "
            + "LEFT JOIN FETCH i.user "
            + "WHERE o.id = :id", Offer.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Offer> findListTree() {
    TypedQuery<Offer> typedQuery = entityManager
        .createQuery("SELECT o FROM Offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "LEFT JOIN FETCH o.requests r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH o.internships i "
            + "LEFT JOIN FETCH i.user ", Offer.class);
    return typedQuery.getResultList();
  }

  public List<Offer> findActiveOffers(String position, String technology, List<Long> requestedOfferIds) {
    String query = "SELECT o FROM Offer o "
        + "LEFT JOIN FETCH o.company "
        + "LEFT JOIN FETCH o.jobDescription j "
        + "WHERE o.active = :active ";
    
    if (!requestedOfferIds.isEmpty()) {
      query += "AND o.id NOT IN (:offerIds) ";
    }
    if (position != null) {
      query += "AND j.position = :position ";
    }
    if (technology != null) {
      query += "AND j.mainTechnology = :technology ";
    }

    TypedQuery<Offer> typedQuery = entityManager
        .createQuery(query, Offer.class);
    typedQuery.setParameter("active", true);
    
    if (!requestedOfferIds.isEmpty()) {
      typedQuery.setParameter("offerIds", requestedOfferIds);
    }
    if (position != null) {
      typedQuery.setParameter("position", position);
    }
    if (technology != null) {
      typedQuery.setParameter("technology", technology);
    }

    return typedQuery.getResultList();
  }

  public List<Offer> findExpiredOffers() {
    TypedQuery<Offer> typedQuery = entityManager
        .createQuery("SELECT o FROM Offer o "
            + "WHERE o.activeUntilDate <= CURDATE() "
            + "AND o.active = :active", Offer.class);
    typedQuery.setParameter("active", true);
    return typedQuery.getResultList();
  }

  public Offer findOfferWithCompanyAndJobDescription(Long offerId) {
    TypedQuery<Offer> typedQuery = entityManager
        .createQuery("SELECT o FROM Offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE o.id = :offerId", Offer.class);
    typedQuery.setParameter("offerId", offerId);
    return typedQuery.getSingleResult();
  }

}
