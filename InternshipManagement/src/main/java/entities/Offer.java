package entities;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import rest.service.abstracts.GenericEntity;
import util.Constants;

@Entity
@Table(name = "offer")
public class Offer implements GenericEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATE_FORMAT)
  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATE_FORMAT)
  @Column(name = "end_date", nullable = false)
  private Date endDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_DATE_FORMAT)
  @Column(name = "active_until_date", nullable = false)
  private Date activeUntilDate;

  @Column(name = "remaining_positions", nullable = false)
  private Long remainingPositions;

  @Column(name = "active", nullable = false)
  private Boolean active = true;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
  private Company company;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "job_description_id", referencedColumnName = "id", nullable = false)
  private JobDescription jobDescription;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "offer", targetEntity = Request.class)
  private Set<Request> requests;

  @JsonIgnore
  @OneToMany(fetch = LAZY, mappedBy = "offer", targetEntity = Internship.class)
  private Set<Internship> internships;

  @Transient
  private Long companyId;

  @Transient
  private Long jobDescriptionId;

  public Offer() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getActiveUntilDate() {
    return activeUntilDate;
  }

  public void setActiveUntilDate(Date activeUntilDate) {
    this.activeUntilDate = activeUntilDate;
  }

  public Long getRemainingPositions() {
    return remainingPositions;
  }

  public void setRemainingPositions(Long remainingPositions) {
    this.remainingPositions = remainingPositions;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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

  public JobDescription getJobDescription() {
    if (Hibernate.isInitialized(this.jobDescription)) {
      return this.jobDescription;
    }
    return null;
  }

  public void setJobDescription(JobDescription jobDescription) {
    this.jobDescription = jobDescription;
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
  @XmlElement(name = "jobDescriptionId")
  public Long getJobDescriptionId() {
    return getJobDescription() != null ? getJobDescription().getId() : this.jobDescriptionId;
  }

  @Transient
  public void setJobDescriptionId(final Long jobDescriptionId) {
    this.jobDescriptionId = jobDescriptionId;
  }

}
