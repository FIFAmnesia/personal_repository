package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.JobDescription;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class JobDescriptionStoreResponse extends StoreResponse<JobDescription> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = JobDescription.class)
  private List<JobDescription> records;

  /**
   * An empty constructor
   */
  public JobDescriptionStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public JobDescriptionStoreResponse(List<JobDescription> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public JobDescriptionStoreResponse(List<JobDescription> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public JobDescriptionStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<JobDescription> records) {
    this.records = records;
  }

  @Override
  public List<JobDescription> getRecords() {
    return records;
  }

}
