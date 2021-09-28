package rest.api.abstracts;

import javax.ws.rs.core.Response;

import responses.abstracts.ServiceResponse;
import responses.abstracts.StoreResponse;
import rest.service.abstracts.CrudService;

public abstract class AbstractCrudApi<E, R extends StoreResponse<E>> implements CrudApi<E, R> {

  protected abstract CrudService<E, R> getServiceBean();

  @Override
  public Response read(Long id, Boolean fetch) {
    ServiceResponse<E> response = getServiceBean().read(id, fetch);
    return Response.status(response.getStatus()).entity(response.getResponse()).build();
  }

  @Override
  public Response create(E dto) {
    ServiceResponse<E> response = getServiceBean().create(dto);
    return Response.status(response.getStatus()).entity(response.getResponse()).build();
  }

  @Override
  public Response update(E dto, Long id) {
    ServiceResponse<E> response = getServiceBean().update(dto, id);
    return Response.status(response.getStatus()).entity(response.getResponse()).build();
  }

  public Response delete(Long id) {
    ServiceResponse<E> response = getServiceBean().delete(id);
    return Response.status(response.getStatus()).entity(response.getResponse()).build();
  }

}
