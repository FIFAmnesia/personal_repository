package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.User;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class UserStoreResponse extends StoreResponse<User> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = User.class)
  private List<User> records;

  /**
   * An empty constructor
   */
  public UserStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public UserStoreResponse(List<User> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public UserStoreResponse(List<User> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public UserStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<User> records) {
    this.records = records;
  }

  @Override
  public List<User> getRecords() {
    return records;
  }

}
