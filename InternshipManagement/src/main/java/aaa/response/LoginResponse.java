package aaa.response;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import aaa.dto.LoginRequest;
import responses.abstracts.StoreResponse;

import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.util.List;

@XmlAccessorType(NONE)
@XmlRootElement(name="storeResponse")
public class LoginResponse extends StoreResponse<LoginRequest> {

  @XmlElementWrapper(name="records")
  @JsonProperty(value="records")
  @XmlElement(name="loginRequest",type=LoginRequest.class)
  private List<LoginRequest> records;

  @Override
  public void setRecords(List<LoginRequest> records) {
      this.records = records;
  }

  @Override
  public List<LoginRequest> getRecords() {
      return records;
  }

}
