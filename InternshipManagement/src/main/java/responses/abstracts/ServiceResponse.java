package responses.abstracts;

import javax.ws.rs.core.Response.Status;

public class ServiceResponse<E> {

  private Status status;
  private StoreResponse<E> response;

  public ServiceResponse(Status status, StoreResponse<E> response) {
    this.status = status;
    this.response = response;
  }

  public Status getStatus() {
    return status;
  }

  public StoreResponse<E> getResponse() {
    return response;
  }

}
