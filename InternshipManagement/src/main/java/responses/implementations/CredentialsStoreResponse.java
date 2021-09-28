package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.Credentials;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class CredentialsStoreResponse extends StoreResponse<Credentials> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = Credentials.class)
  private List<Credentials> records;

  /**
   * An empty constructor
   */
  public CredentialsStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public CredentialsStoreResponse(List<Credentials> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public CredentialsStoreResponse(List<Credentials> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public CredentialsStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<Credentials> records) {
    this.records = records;
  }

  @Override
  public List<Credentials> getRecords() {
    return records;
  }

}
