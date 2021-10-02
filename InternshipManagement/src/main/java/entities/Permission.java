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

import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.service.abstracts.GenericEntity;

@Entity
@Table(name = "permission")
public class Permission implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "resource_name", nullable = false)
  private String resourceName;

  @Column(name = "value", nullable = false)
  private byte value;

  @JsonIgnore
  @ManyToOne(fetch = LAZY, optional = true)
  @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
  private Role role;

  @Transient
  private Long roleId;

  public Permission() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public byte getValue() {
    return value;
  }

  public void setValue(byte value) {
    this.value = value;
  }

  public Role getRole() {
    if (Hibernate.isInitialized(this.role)) {
      return this.role;
    }
    return null;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Transient
  @XmlElement(name = "roleId")
  public Long getRoleId() {
    return getRole() != null ? getRole().getId() : this.roleId;
  }

  @Transient
  public void setRoleId(final Long roleId) {
    this.roleId = roleId;
  }

}
