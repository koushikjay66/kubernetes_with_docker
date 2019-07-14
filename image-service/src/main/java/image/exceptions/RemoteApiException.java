package image.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import image.models.ErrorMessage;



public class RemoteApiException extends WebApplicationException {
    public RemoteApiException(ErrorMessage message) {
        super(Response.status(500).entity(message).build());
    }
}
