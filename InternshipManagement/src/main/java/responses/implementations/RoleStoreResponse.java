package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.Role;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class RoleStoreResponse extends StoreResponse<Role> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = Role.class)
  private List<Role> records;

  /**
   * An empty constructor
   */
  public RoleStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public RoleStoreResponse(List<Role> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public RoleStoreResponse(List<Role> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public RoleStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<Role> records) {
    this.records = records;
  }

  @Override
  public List<Role> getRecords() {
    return records;
  }

}
