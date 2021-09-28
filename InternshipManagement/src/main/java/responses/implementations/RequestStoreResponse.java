package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.Request;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class RequestStoreResponse extends StoreResponse<Request> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = Request.class)
  private List<Request> records;

  /**
   * An empty constructor
   */
  public RequestStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public RequestStoreResponse(List<Request> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public RequestStoreResponse(List<Request> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public RequestStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<Request> records) {
    this.records = records;
  }

  @Override
  public List<Request> getRecords() {
    return records;
  }

}
