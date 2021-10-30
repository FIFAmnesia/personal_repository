package entities;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.service.abstracts.GenericEntity;

@Entity
@Table(name = "credentials")
public class Credentials implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @JsonIgnore
  @OneToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @Transient
  private Long userId;

  public Credentials() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  @Transient
  @XmlElement(name = "userId")
  public Long getUserId() {
    return getUser() != null ? getUser().getId() : this.userId;
  }

  @Transient
  public void setUserId(final Long userId) {
    this.userId = userId;
  }

}
