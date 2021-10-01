package aaa.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static javax.xml.bind.annotation.XmlAccessType.NONE;

@XmlAccessorType(NONE)
@XmlRootElement(name = "loginRequest")
public class LoginRequest {

  private String username;

  private String password;

  private Consumer consumer;

  @XmlElement(name = "consumer", type = Consumer.class)
  public Consumer getConsumer() {
    return consumer;
  }

  public void setConsumer(Consumer consumer) {
    this.consumer = consumer;
  }

  @XmlElement(name = "username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @XmlElement(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
