package rest.api.abstracts;

import javax.ws.rs.core.Response;

import responses.abstracts.StoreResponse;

public interface CrudApi<E, R extends StoreResponse<E>> {

  public Response read(Long id, Boolean fetch);

  public Response create(E rec);

  public Response update(E rec, Long id);

  public Response delete(Long id);

}
