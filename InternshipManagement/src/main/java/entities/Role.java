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
@Table(name = "role")
public class Role implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "role", targetEntity = Permission.class)
  private Set<Permission> permissions;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "role", targetEntity = User.class)
  private Set<User> users;

  public Role() {
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Permission> getPermissions() {
    if (Hibernate.isInitialized(this.permissions)) {
      return this.permissions;
    }
    return null;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
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

}
