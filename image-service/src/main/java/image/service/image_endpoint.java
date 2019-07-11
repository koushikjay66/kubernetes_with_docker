package image.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Kowshik Dipta Das Joy <kowshik-dipta-das.joy@stud.uni-bamberg.de
 */

@Produces(MediaType.APPLICATION_JSON)
@Path("/albumArt")
public class image_endpoint {

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{songID}")
	public String  getImage(@PathParam("songID") String songID) {

		return "This is Koushik";
	}
}
