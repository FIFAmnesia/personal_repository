package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.service.abstracts.GenericEntity;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "student_information")
public class StudentInformation implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "faculty_number", nullable = false)
  private String facultyNumber;

  @Column(name = "group_number", nullable = false)
  private Long groupNumber;

  @Column(name = "current_academic_year", nullable = false)
  private Long currentAcademicYear;

  @Column(name = "speciality", nullable = false)
  private String speciality;

  @JsonIgnore
  @OneToOne(fetch = LAZY, mappedBy = "studentInformation", targetEntity = User.class)
  private User user;

  @Transient
  private Long userId;

  public StudentInformation() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFacultyNumber() {
    return facultyNumber;
  }

  public void setFacultyNumber(String facultyNumber) {
    this.facultyNumber = facultyNumber;
  }

  public Long getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(Long groupNumber) {
    this.groupNumber = groupNumber;
  }

  public Long getCurrentAcademicYear() {
    return currentAcademicYear;
  }

  public void setCurrentAcademicYear(Long currentAcademicYear) {
    this.currentAcademicYear = currentAcademicYear;
  }

  public String getSpeciality() {
    return speciality;
  }

  public void setSpeciality(String speciality) {
    this.speciality = speciality;
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
