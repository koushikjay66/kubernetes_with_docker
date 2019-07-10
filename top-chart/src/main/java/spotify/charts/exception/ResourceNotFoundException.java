package spotify.charts.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import spotify.charts.entities.ErrorMessage;

public class ResourceNotFoundException extends WebApplicationException {
    public ResourceNotFoundException(ErrorMessage message) {
        super(Response.status(404).entity(message).build());
    }
}
