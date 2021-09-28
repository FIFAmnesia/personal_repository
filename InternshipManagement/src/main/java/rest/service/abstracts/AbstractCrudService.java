package rest.service.abstracts;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import datastore.abstracts.DataStore;
import exceptions.ValidationException;
import responses.abstracts.ServiceResponse;
import responses.abstracts.StoreResponse;
import rest.validation.abstracts.EntityValidator;

public abstract class AbstractCrudService<E extends GenericEntity, R extends StoreResponse<E>> implements CrudService<E, R> {

  private static final Logger log = Logger.getLogger(AbstractCrudService.class);

  protected abstract DataStore<E> getDataStore();

  protected abstract Class<R> getResponseClass();

  protected abstract EntityValidator<E> getValidator();

  @Override
  public ServiceResponse<E> read(Long id, Boolean fetch) {
    R resp = getResponseInstance();
    Status status = Status.OK;

    try {
      List<E> records = new ArrayList<>();
      if (id != null) {
        E entity = null;

        getValidator().validatePreRead(records);

        if (!fetch) {
          entity = getDataStore().find(id);
        } else {
          entity = getDataStore().findTree(id);
        }

        if (entity != null) {
          records.add(entity);
        }
      } else {
        List<E> entities = null;

        if (!fetch) {
          entities = getDataStore().findList();
        } else {
          entities = getDataStore().findListTree();
        }

        if (entities != null && !entities.isEmpty()) {
          records.addAll(entities);
        }
      }

      getValidator().validatePostRead(records);

      resp.setSuccess(true);
      resp.setTotalCount(new Long(records.size()));
      resp.setRecords(records);

    } catch (ValidationException | EJBTransactionRolledbackException e) {
      Throwable t = e.getCause();
      String msg = (t != null ? t.getMessage(): e.getMessage());
      status = Status.INTERNAL_SERVER_ERROR;

      if (e instanceof ValidationException) {
        status = Status.BAD_REQUEST;
        log.error("Validation after read for " + getDataStore().getEntityClass().getSimpleName() + " failed: " + msg, e);
      } else {
        log.error("Failed to read from " + getDataStore().getClass().getSimpleName() + "!", e);
      }

      resp.setSuccess(false);
      resp.setMessage(msg);
    } catch (Exception e) {
      log.error("Failed to read from " + getDataStore().getClass().getSimpleName() + "!", e);

      status = Status.CONFLICT;
      resp.setSuccess(false);
      resp.setMessage(e.getMessage());
    }

    return new ServiceResponse<>(status, resp);
  }

  @Override
  public ServiceResponse<E> create(E dto) {
    R resp = getResponseInstance();
    Status status = Status.CREATED;

    try {
      dto = getValidator().validatePreCreate(dto);
      E rec = getDataStore().persist(dto);
      dto = getValidator().validatePostCreate(dto, rec);

      resp.setSuccess(true);
      resp.setTotalCount(1L);

      List<E> records = new ArrayList<>(1);
      records.add(rec);
      resp.setRecords(records);

    } catch (ValidationException | EJBTransactionRolledbackException e) {
      Throwable t = e.getCause();
      String msg = (t != null ? t.getMessage(): e.getMessage());
      status = Status.INTERNAL_SERVER_ERROR;

      if (e instanceof ValidationException) {
        status = Status.BAD_REQUEST;
        log.error("Validation for persisting of " + getDataStore().getEntityClass().getSimpleName() + " failed: " + msg, e);
      } else if (t instanceof PersistenceException) {
        String className = t.getCause() != null ? t.getCause().getClass().getName() : t.getClass().getName();
        log.error("Persisting of " + getDataStore().getEntityClass().getSimpleName() + " failed due to: " + className + t.getMessage());
      } else {
        log.error("Persisting of " + getDataStore().getEntityClass().getSimpleName() + " failed due to UNCHECKED exception: " + msg, e);
      }

      resp.setSuccess(false);
      resp.setMessage(msg);
    } catch (Exception e) {
      log.error("Failed to create a new " + getDataStore().getEntityClass().getSimpleName() + " record!", e);

      status = Status.CONFLICT;
      resp.setSuccess(false);
      resp.setMessage(e.getMessage());
    }

    return new ServiceResponse<>(status, resp);
  }

  @Override
  public ServiceResponse<E> update(E dto, Long id) {
    R resp = getResponseInstance();
    Status status = Status.OK;

    try {
      if (dto.getId() != null) {
        if (!dto.getId().equals(id)) {
          throw new IllegalArgumentException("Value of id does not match the id field in the request payload: " + id + " != " + dto.getId());
        }
      }

      E ent = getDataStore().findTree(id);

      if (ent == null) {
        resp.setSuccess(false);
        resp.setMessage("Specified record was not found");

        return new ServiceResponse<>(Status.NOT_FOUND, resp);
      }

      dto = getValidator().validatePreUpdate(dto, ent);
      ent = getDataStore().merge(dto, ent);
      dto = getValidator().validatePostUpdate(dto, ent);

      resp.setSuccess(true);
      resp.setTotalCount(1L);

      List<E> records = new ArrayList<>(1);
      records.add(ent);

      resp.setRecords(records);

    } catch (ValidationException | EJBTransactionRolledbackException e) {
      Throwable t = e.getCause();
      String msg = (t != null ? t.getMessage(): e.getMessage());
      status = Status.INTERNAL_SERVER_ERROR;

      if (e instanceof ValidationException) {
        status = Status.BAD_REQUEST;
        log.error("Validation for update of " + getDataStore().getEntityClass().getSimpleName() + " failed: " + msg, e);
      } else {
        log.error("Updating of " + getDataStore().getEntityClass().getSimpleName() + " failed due to UNCHECKED exception: " + msg, e);
      }

      resp.setSuccess(false);
      resp.setMessage(msg);
    } catch (Exception e) {
      log.error("Failed to update " + getDataStore().getClass().getSimpleName() + " record with id=" + id + "!", e);

      status = Status.CONFLICT;
      resp.setSuccess(false);
      resp.setMessage(e.getMessage());
    }

    return new ServiceResponse<>(status, resp);
  }

  @Override
  public ServiceResponse<E> delete(Long id) {
    R resp = getResponseInstance();
    Status status = Status.OK;

    try {
      E ent = getDataStore().find(id);

      getValidator().validatePreDelete(ent);
      getDataStore().remove(ent);
      getValidator().validatePostDelete(ent);

      resp.setSuccess(true);

    } catch (ValidationException | EJBTransactionRolledbackException e) {
      Throwable t = e.getCause();
      String msg = (t != null ? t.getMessage(): e.getMessage());
      status = Status.INTERNAL_SERVER_ERROR;

      if (e instanceof ValidationException) {
        status = Status.BAD_REQUEST;
        log.error("Validation for deletion of " + getDataStore().getEntityClass().getSimpleName() + " failed: " + msg, e);
      } else {
        log.error("Deletion of " + getDataStore().getEntityClass().getSimpleName() + " failed due to UNCHECKED exception: " + msg, e);
      }

      resp.setSuccess(false);
      resp.setMessage(msg);
    } catch (Exception e) {
      log.error("Failed to delete " + getDataStore().getClass().getSimpleName() + " record with id=" + id + "!", e);

      status = Status.CONFLICT;
      resp.setSuccess(false);
      resp.setMessage(e.getMessage());
    }

    return new ServiceResponse<>(status, resp);
  }

  private R getResponseInstance() {
    Class<R> clazz = getResponseClass();

    try {
      return getResponseClass().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new InvalidParameterException(clazz.getName() + "'s constructor is not public, please check the generated file!");
    }
  }

}
