package spotify.charts.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import spotify.charts.entities.ErrorMessage;

public class ClientRequestException extends WebApplicationException {
    public ClientRequestException(ErrorMessage message) {
        super(Response.status(400).entity(message).build());
    }
}
