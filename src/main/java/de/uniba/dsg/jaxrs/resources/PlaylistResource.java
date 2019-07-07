package de.uniba.dsg.jaxrs.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.uniba.dsg.interfaces.PlaylistApi;
import de.uniba.dsg.models.PlaylistRequest;

@Path("/playlist")
public class PlaylistResource  implements PlaylistApi{

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response createPlaylist(PlaylistRequest request) {
		// TODO Auto-generated method stub
		
		return null;
	}
	


}
