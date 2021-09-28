package entities;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.Hibernate;

import rest.service.abstracts.GenericEntity;

@Entity
@Table(name = "internship")
public class Internship implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "academic_year", nullable = false)
  private Long acedemicYear;

  @Column(name = "completed", nullable = false)
  private Boolean completed = false;

  @Column(name = "note")
  private String note;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
  private Offer offer;

  @Transient
  private Long userId;

  @Transient
  private Long offerId;

  public Internship() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAcedemicYear() {
    return acedemicYear;
  }

  public void setAcedemicYear(Long acedemicYear) {
    this.acedemicYear = acedemicYear;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public User getUser() {
    if (Hibernate.isInitialized(this.user)) {
      return this.user;
    }
    return null;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Offer getOffer() {
    if (Hibernate.isInitialized(this.offer)) {
      return this.offer;
    }
    return null;
  }

  public void setOffer(Offer offer) {
    this.offer = offer;
  }

  @Transient
  @XmlElement(name = "userId")
  public Long getUserId() {
    return getUser() != null ? getUser().getId() : this.userId;
  }

  @Transient
  public void setUserId(final Long userId) {
    this.userId = userId;
  }

  @Transient
  @XmlElement(name = "offerId")
  public Long getOfferId() {
    return getOffer() != null ? getOffer().getId() : this.offerId;
  }

  @Transient
  public void setOfferId(final Long offerId) {
    this.offerId = offerId;
  }

}
