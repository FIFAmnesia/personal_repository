package aaa.service.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

import aaa.dto.Consumer;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ConsumerCache {

  private Map<String, Consumer> tokenToUser;

  private Set<Consumer> consumers;

  @PostConstruct
  public void init() {
    if (tokenToUser == null) {
      tokenToUser = new HashMap<>();
    }

    if (consumers == null) {
      consumers = new HashSet<>();
    }
  }
}
