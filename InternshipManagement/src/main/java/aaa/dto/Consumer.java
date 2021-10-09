package aaa.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import entities.Permission;
import entities.User;
import util.PermissionsUtil;

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

  public boolean canExecute(String methodName) {
    return canRead(methodName);
  }

  public boolean canCreate(String entityName) {
    int permission = extractPermission(entityName);
    return PermissionsUtil.hasCreatePermission(permission);
  }

  public boolean canRead(String entityName) {
    int permission = extractPermission(entityName);
    return PermissionsUtil.hasReadPermission(permission);
  }

  public boolean canUpdate(String entityName) {
    int permission = extractPermission(entityName);
    return PermissionsUtil.hasUpdatePermission(permission);
  }

  public boolean canDelete(String entityName) {
    int permission = extractPermission(entityName);
    return PermissionsUtil.hasDeletePermission(permission);
  }

  private int extractPermission(String entityName) {
    int permissionValue = 0;

    if (user.getRole().getPermissions() == null) {
      return permissionValue;
    }

    for(Permission permission : user.getRole().getPermissions()) {
      permissionValue |= permission.getResourceName().equals(entityName) ? permission.getValue() : 0;
    }

    return permissionValue;
  }

}
