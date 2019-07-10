package spotify.search.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import spotify.search.entities.ErrorMessage;


public class RemoteApiException extends WebApplicationException {
    public RemoteApiException(ErrorMessage message) {
        super(Response.status(500).entity(message).build());
    }
}
