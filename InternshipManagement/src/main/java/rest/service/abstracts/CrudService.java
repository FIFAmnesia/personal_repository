package rest.service.abstracts;

import responses.abstracts.ServiceResponse;
import responses.abstracts.StoreResponse;

public interface CrudService<E, R extends StoreResponse<E>> {

  public ServiceResponse<E> read(Long id, Boolean fetch);

  public ServiceResponse<E> create(E dto);

  public ServiceResponse<E> update(E dto, Long id);

  public ServiceResponse<E> delete(Long id);

}
