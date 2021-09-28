package entities;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonFormat;

import rest.service.abstracts.GenericEntity;
import util.Constants;

@Entity
@Table(name = "request")
public class Request implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATE_FORMAT)
  @Column(name = "made_on", nullable = false)
  private Date madeOn;

  @Column(name = "approved")
  private Boolean approved;

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

  public Request() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getMadeOn() {
    return madeOn;
  }

  public void setMadeOn(Date madeOn) {
    this.madeOn = madeOn;
  }

  public Boolean getApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
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
