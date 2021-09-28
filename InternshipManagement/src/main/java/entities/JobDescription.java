package entities;

import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.service.abstracts.GenericEntity;

@Entity
@Table(name = "job_description")
public class JobDescription implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "position", nullable = false)
  private String position;

  @Column(name = "main_technology")
  private String mainTechnology;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "jobDescription", targetEntity = Offer.class)
  private Set<Offer> offers;

  public JobDescription() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getMainTechnology() {
    return mainTechnology;
  }

  public void setMainTechnology(String mainTechnology) {
    this.mainTechnology = mainTechnology;
  }

  public Set<Offer> getOffers() {
    if (Hibernate.isInitialized(this.offers)) {
      return this.offers;
    }
    return null;
  }

  public void setOffers(Set<Offer> offers) {
    this.offers = offers;
  }

}
