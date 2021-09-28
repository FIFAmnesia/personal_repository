package entities;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rest.service.abstracts.GenericEntity;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "address", nullable = false)
  private String address;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "company", targetEntity = User.class)
  private Set<User> users;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "company", targetEntity = Offer.class)
  private Set<Offer> offers;

  public Company() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Set<User> getUsers() {
    if (Hibernate.isInitialized(this.users)) {
      return this.users;
    }
    return null;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
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
