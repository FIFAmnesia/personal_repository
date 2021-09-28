package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.Hibernate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import rest.service.abstracts.GenericEntity;
import static javax.persistence.FetchType.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "email", nullable = false)
  private String email;

  @ManyToOne(fetch = LAZY, optional = true)
  @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
  private Company company;

  @OneToOne(fetch = LAZY, optional = true)
  @JoinColumn(name = "student_information_id", referencedColumnName = "id", nullable = false)
  private StudentInformation studentInformation;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
  private Role role;

  @JsonIgnore
  @OneToOne(fetch = LAZY, mappedBy = "user", targetEntity = Credentials.class)
  private Credentials credentials;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "user", targetEntity = Request.class)
  private Set<Request> requests;
  
  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "user", targetEntity = Internship.class)
  private Set<Internship> internships;

  @Transient
  private Long companyId;

  @Transient
  private Long studentInformationId;

  @Transient
  private Long roleId;

  @Transient
  private Long credentialsId;

  public User() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Company getCompany() {
    if (Hibernate.isInitialized(this.company)) {
      return this.company;
    }
    return null;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public StudentInformation getStudentInformation() {
    if (Hibernate.isInitialized(this.studentInformation)) {
      return this.studentInformation;
    }
    return null;
  }

  public void setStudentInformation(StudentInformation studentInformation) {
    this.studentInformation = studentInformation;
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

  public Credentials getCredentials() {
    if (Hibernate.isInitialized(this.credentials)) {
      return this.credentials;
    }
    return null;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public Set<Request> getRequests() {
    if (Hibernate.isInitialized(this.requests)) {
      return this.requests;
    }
    return null;
  }

  public void setRequests(Set<Request> requests) {
    this.requests = requests;
  }
  
  public Set<Internship> getInternships() {
    if (Hibernate.isInitialized(this.internships)) {
      return this.internships;
    }
    return null;
  }

  public void setInternships(Set<Internship> internships) {
    this.internships = internships;
  }

  @Transient
  @XmlElement(name = "companyId")
  public Long getCompanyId() {
    return getCompany() != null ? getCompany().getId() : this.companyId;
  }

  @Transient
  public void setCompanyId(final Long companyId) {
    this.companyId = companyId;
  }

  @Transient
  @XmlElement(name = "studentInformationId")
  public Long getStudentInformationId() {
    return getStudentInformation() != null ? getStudentInformation().getId() : this.studentInformationId;
  }

  @Transient
  public void setStudentInformationId(final Long studentInformationId) {
    this.studentInformationId = studentInformationId;
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

  @Transient
  @XmlElement(name = "credentialsId")
  public Long getCredentialsId() {
    return getCredentials() != null ? getCredentials().getId() : this.credentialsId;
  }

  @Transient
  public void setCredentialsId(final Long credentialsId) {
    this.credentialsId = credentialsId;
  }

}
