package aaa.service;

import java.util.Set;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;

import aaa.dto.Consumer;
import aaa.dto.LoginRequest;
import aaa.service.cache.ConsumerCache;
import datastore.implementations.UserDataStore;
import entities.Credentials;
import entities.User;
import exceptions.ValidationException;
import exceptions.WrongPasswordException;
import util.StringUtil;

@Stateless
public class AuthenticationService {

  @EJB
  private ConsumerCache consumerCache;

  @EJB
  private UserDataStore userDataStore;

  public Consumer authenticate(LoginRequest request) throws ValidationException {
    if (!request.getUsername().isEmpty() && !request.getPassword().isEmpty()) {
      try {
        return userPassAuthentication(request.getUsername(), request.getPassword());
      } catch (WrongPasswordException e) {
        throw new ValidationException(e.getMessage());
      }
    }

    throw new ValidationException("Authentication data is not provided.");
  }

  private Consumer userPassAuthentication(String username, String password)
      throws WrongPasswordException, ValidationException {

    final Set<Consumer> consumers = consumerCache.getConsumers();

    for (Consumer checkedConsumer : consumers) {
      if (checkedConsumer.getUser().getCredentials().getUsername().equals(username)) {
        User user = userDataStore.findTree(checkedConsumer.getUser().getId());
        // user = detachUser(user);
        return checkUserCredentials(user, password, checkedConsumer);
      }
    }

    User user = userDataStore.findUserForAuthentication(username);
    if (user == null) {
      throw new ValidationException("User not found", "username", username);
    }
    // user = detachUser(user);

    return checkUserCredentials(user, password, null);
  }

  private Consumer checkUserCredentials(final User user, String password, final Consumer checkedConsumer)
      throws WrongPasswordException, ValidationException {

    Credentials userCredentials = user.getCredentials();
    if (!userCredentials.getPassword().toLowerCase().equals(DigestUtils.sha256Hex(password).toLowerCase())) {
      throw new WrongPasswordException("User with username: " + userCredentials.getUsername() + " has provided wrong password: " + password);
    }

    if (checkedConsumer != null) {
      return resetConsumer(checkedConsumer, user);
    }

    return createAuthorizedConsumer(user, password);
  }

  private Consumer createAuthorizedConsumer(User user, String password)
      throws ValidationException {

    final Consumer consumer = new Consumer();
    consumer.setUser(user);
    consumer.setAccessToken(generateToken(consumer, password));

    consumerCache.addConsumerToCache(consumer.getAccessToken(), consumer);

    return consumer;
  }

  private Consumer resetConsumer(Consumer consumer, User user) {
    consumer.setUser(user);
    consumerCache.removeConsumerFromCache(consumer.getAccessToken());
    consumerCache.addConsumerToCache(consumer.getAccessToken(), consumer);
    return consumer;
  }

  public String generateToken(final Consumer consumer, final String password) throws ValidationException {
    final StringBuilder builder = new StringBuilder().append(consumer.getUser().getCredentials().getUsername()).append(UUID.randomUUID());

    if (password != null) {
      builder.append(password);
    }

    // if (consumer.getAccessToken() != null) {
    // builder.append(consumer.getAccessToken());
    // }

    builder.append(UUID.randomUUID());

    return StringUtil.hash(builder.toString(), "MD5");
  }

}
