package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.StudentInformation;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class StudentInformationStoreResponse extends StoreResponse<StudentInformation> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = StudentInformation.class)
  private List<StudentInformation> records;

  /**
   * An empty constructor
   */
  public StudentInformationStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public StudentInformationStoreResponse(List<StudentInformation> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public StudentInformationStoreResponse(List<StudentInformation> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public StudentInformationStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<StudentInformation> records) {
    this.records = records;
  }

  @Override
  public List<StudentInformation> getRecords() {
    return records;
  }

}
