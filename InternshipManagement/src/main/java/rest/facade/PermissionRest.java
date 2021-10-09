package rest.facade;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import annotations.ManagedEntity;
import entities.Permission;
import responses.implementations.PermissionStoreResponse;
import rest.api.abstracts.AbstractCrudApi;
import rest.service.abstracts.CrudService;
import rest.service.implementations.PermissionCrudService;

@Path("/permission")
@ManagedEntity(Permission.class)
public class PermissionRest extends AbstractCrudApi<Permission, PermissionStoreResponse> {

  @EJB
  private PermissionCrudService permissionCrudService;

  @Override
  protected CrudService<Permission, PermissionStoreResponse> getServiceBean() {
    return permissionCrudService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response read(@QueryParam("id") Long id, @DefaultValue("false") @QueryParam("fetch") Boolean fetch) {
    return super.read(id, fetch);
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response create(Permission rec) {
    return super.create(rec);
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(Permission rec, @PathParam("id") Long id) {
    return super.update(rec, id);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") Long id) {
    return super.delete(id);
  }

}
