package aaa.service.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import aaa.dto.Consumer;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ConsumerCache {

  private Set<Consumer> consumers;

  private Map<String, Consumer> tokenToConsumer;

  @PostConstruct
  public void init() {
    if (tokenToConsumer == null) {
      tokenToConsumer = new HashMap<>();
    }

    if (consumers == null) {
      consumers = new HashSet<>();
    }
  }

  @Lock(LockType.READ)
  public Set<Consumer> getConsumers() {
    return consumers;
  }

  @Lock(LockType.READ)
  public Map<String, Consumer> getTokenToConsumerRelations() {
    return tokenToConsumer;
  }

  @Lock(LockType.READ)
  public Consumer retrieveConsumerByAccessToken(String key) {
    return tokenToConsumer.get(key);
  }

  @Lock(LockType.WRITE)
  public void addConsumerToCache(String accessToken, Consumer consumer) {
    consumers.add(consumer);
    tokenToConsumer.put(accessToken, consumer);
  }

  @Lock(LockType.WRITE)
  public void removeConsumerFromCache(String accessToken) {
    consumers.removeIf(c -> c.getAccessToken().equals(accessToken));
    tokenToConsumer.remove(accessToken);
  }

}