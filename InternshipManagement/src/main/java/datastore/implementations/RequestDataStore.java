package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Request;

@Stateless
public class RequestDataStore extends AbstractDataStore<Request> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Request> getEntityClass() {
    return Request.class;
  }

  @Override
  public Request find(Long id) {
    return entityManager.find(Request.class, id);
  }

  @Override
  public List<Request> findList() {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r", Request.class);
    return typedQuery.getResultList();
  }

  @Override
  public Request findTree(Long id) {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE r.id = :id", Request.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Request> findListTree() {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription", Request.class);
    return typedQuery.getResultList();
  }

  public List<Request> findRequestsByUser(Long userId) {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH r.offer o "
            + "WHERE u.id = :userId", Request.class);
    typedQuery.setParameter("userId", userId);
    return typedQuery.getResultList();
  }

  public List<Request> findApprovedRequests(Long userId) {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE r.approved = :approved "
            + "AND u.id = :userId", Request.class);
    typedQuery.setParameter("approved", true);
    typedQuery.setParameter("userId", userId);
    return typedQuery.getResultList();
  }

  public List<Request> findPendingRequests(Long companyId) {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company c "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE r.approved IS NULL "
            + "AND c.id = :companyId", Request.class);
    typedQuery.setParameter("companyId", companyId);
    return typedQuery.getResultList();
  }

  public List<Request> findPendingRequestsForOffers(List<Long> offerIds) {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company "
            //+ "WHERE r.approved != :approved "
            + "WHERE o.id IN (:offerIds) ", Request.class);
    //typedQuery.setParameter("approved", true);
    typedQuery.setParameter("offerIds", offerIds);
    return typedQuery.getResultList();
  }

  public List<Request> findRemainingRequestsForOffer(Long offerId, List<Long> userIds) {
    TypedQuery<Request> typedQuery = entityManager
        .createQuery("SELECT r FROM Request r "
            + "LEFT JOIN FETCH r.user u "
            + "LEFT JOIN FETCH r.offer o "
            + "LEFT JOIN FETCH o.company "
            + "WHERE o.id = :offerId "
            + "AND r.approved IS NULL "
            + "AND u.id NOT IN (:userIds)", Request.class);
    typedQuery.setParameter("offerId", offerId);
    typedQuery.setParameter("userIds", userIds);
    return typedQuery.getResultList();
  }

  public Boolean existingRequest(Long userId, Long offerId) {
    TypedQuery<Long> typedQuery = entityManager
        .createQuery("SELECT COUNT(r) FROM Request r "
            + "LEFT JOIN r.user u "
            + "LEFT JOIN r.offer o "
            + "WHERE u.id = :userId "
            + "AND o.id = :offerId", Long.class);
    typedQuery.setParameter("userId", userId);
    typedQuery.setParameter("offerId", offerId);
    return typedQuery.getSingleResult() > 0;
  }

}
