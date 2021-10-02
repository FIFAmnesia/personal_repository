package aaa.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import entities.User;

@XmlRootElement(name = "consumer")
public class Consumer {

  private String accessToken;

  private User user;

  @XmlElement(name = "accessToken")
  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @XmlElement(name = "user", type = User.class)
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
