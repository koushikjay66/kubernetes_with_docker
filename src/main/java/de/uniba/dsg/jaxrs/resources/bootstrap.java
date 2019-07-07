package de.uniba.dsg.jaxrs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

@Path("/")
public class bootstrap {


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String WelcomeUser() {

		return "Welcome to the Group 19 Project";
	}
}
