package rest.validation.abstracts;

import java.util.List;

import exceptions.ValidationException;

public interface EntityValidator<E> {

  public void validatePreRead(List<E> entities) throws ValidationException;

  public E validatePreCreate(E dto) throws ValidationException;

  public E validatePreUpdate(E dto, E ent) throws ValidationException;

  public void validatePreDelete(E ent) throws ValidationException;

  public void validatePostRead(List<E> entities) throws ValidationException;

  public E validatePostCreate(E dto, E ent) throws ValidationException;

  public E validatePostUpdate(E dto, E ent) throws ValidationException;

  public void validatePostDelete(E ent) throws ValidationException;

}
