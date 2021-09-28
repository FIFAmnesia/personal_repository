package responses.implementations;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import entities.Company;
import responses.abstracts.StoreResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public class CompanyStoreResponse extends StoreResponse<Company> {

  @XmlElementWrapper(name = "records")
  @JsonProperty(value = "records")
  @XmlElement(name = "user", type = Company.class)
  private List<Company> records;

  /**
   * An empty constructor
   */
  public CompanyStoreResponse() {
  }

  /**
   * An OK (successful) response constructor
   */
  public CompanyStoreResponse(List<Company> records, Long totalCount) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
  }

  /**
   * A Warning (still successful, but with a warning message) response constructor
   */
  public CompanyStoreResponse(List<Company> records, Long totalCount, String message) {
    setSuccess(true);
    setRecords(records);
    setTotalCount(totalCount);
    setMessage(message);
  }

  /**
   * An Error (unsuccessful) response constructor
   */
  public CompanyStoreResponse(String message) {
    setSuccess(false);
    setMessage(message);
  }

  @Override
  public void setRecords(List<Company> records) {
    this.records = records;
  }

  @Override
  public List<Company> getRecords() {
    return records;
  }

}
