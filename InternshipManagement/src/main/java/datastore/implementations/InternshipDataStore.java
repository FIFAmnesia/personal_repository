package datastore.implementations;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import datastore.abstracts.AbstractDataStore;
import entities.Internship;

@Stateless
public class InternshipDataStore extends AbstractDataStore<Internship> {

  @PersistenceContext(unitName = "internship_management")
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Class<Internship> getEntityClass() {
    return Internship.class;
  }

  @Override
  public Internship find(Long id) {
    return entityManager.find(Internship.class, id);
  }

  @Override
  public List<Internship> findList() {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i", Internship.class);
    return typedQuery.getResultList();
  }

  @Override
  public Internship findTree(Long id) {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i "
            + "LEFT JOIN FETCH i.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH i.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE i.id = :id", Internship.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
  }

  @Override
  public List<Internship> findListTree() {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i "
            + "LEFT JOIN FETCH i.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH u.role "
            + "LEFT JOIN FETCH i.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription", Internship.class);
    return typedQuery.getResultList();
  }

  public List<Internship> findAllInternshipsForUser(Long userId) {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i "
            + "LEFT JOIN FETCH i.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH i.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE u.id = :userId", Internship.class);
    typedQuery.setParameter("userId", userId);
    return typedQuery.getResultList();
  }

  public Long countInternshipsForCurrentAcademicYear(Long userId, Long currentAcademicYear) {
    TypedQuery<Long> typedQuery = entityManager
        .createQuery("SELECT count(i) FROM Internship i "
            + "LEFT JOIN i.user u "
            + "LEFT JOIN u.studentInformation si "
            + "WHERE u.id = :userId "
            + "AND si.currentAcademicYear = :currentAcademicYear", Long.class);
    typedQuery.setParameter("userId", userId);
    typedQuery.setParameter("currentAcademicYear", currentAcademicYear);
    return typedQuery.getSingleResult();
  }

  public List<Internship> findAllInternshipsForCompany(Long companyId) {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i "
            + "LEFT JOIN FETCH i.user u "
            + "LEFT JOIN FETCH u.studentInformation "
            + "LEFT JOIN FETCH i.offer o "
            + "LEFT JOIN FETCH o.company c "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE c.id = :companyId", Internship.class);
    typedQuery.setParameter("companyId", companyId);
    return typedQuery.getResultList();
  }

  public List<Internship> findAllInternshipsForStudent(String facultyNumber) {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i "
            + "LEFT JOIN FETCH i.user u "
            + "LEFT JOIN FETCH u.studentInformation si "
            + "LEFT JOIN FETCH i.offer o "
            + "LEFT JOIN FETCH o.company "
            + "LEFT JOIN FETCH o.jobDescription "
            + "WHERE si.facultyNumber = :facultyNumber", Internship.class);
    typedQuery.setParameter("facultyNumber", facultyNumber);
    return typedQuery.getResultList();
  }

  public List<Internship> findAllInternshipsForOffer(Long offerId) {
    TypedQuery<Internship> typedQuery = entityManager
        .createQuery("SELECT i FROM Internship i "
            + "LEFT JOIN FETCH i.user u "
            + "LEFT JOIN FETCH i.offer o "
            + "WHERE o.id = :offerId", Internship.class);
    typedQuery.setParameter("offerId", offerId);
    return typedQuery.getResultList();
  }

}
