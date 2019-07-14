package image.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import image.models.ErrorMessage;

public class ClientRequestException extends WebApplicationException {
    public ClientRequestException(ErrorMessage message) {
        super(Response.status(400).entity(message).build());
    }
}
