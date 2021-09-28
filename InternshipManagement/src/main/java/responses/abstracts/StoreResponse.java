package responses.abstracts;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "storeResponse")
public abstract class StoreResponse<T> {

  @XmlElement(name = "success")
  private boolean success;

  @XmlElement(name = "message")
  private String message;

  @XmlElement(name = "totalCount")
  private Long totalCount;

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public abstract void setRecords(List<T> records);

  public abstract List<T> getRecords();

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public Long getTotalCount() {
    return totalCount;
  }

}
