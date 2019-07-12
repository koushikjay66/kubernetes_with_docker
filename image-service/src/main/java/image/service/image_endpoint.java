package image.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Track;

import image.models.AlbumArtModel;
import spotify.configurations.CustomSpotifyApi;

/**
 * @author Kowshik Dipta Das Joy <kowshik-dipta-das.joy@stud.uni-bamberg.de
 */

@Produces(MediaType.APPLICATION_JSON)
@Path("albumArt")
public class image_endpoint {

	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{songID}")
	public AlbumArtModel  getImage(@PathParam("songID") String songID) {

		 try {
			 AlbumSimplified t =CustomSpotifyApi.getInstance().getTrack(songID).build().execute().getAlbum();
			 AlbumArtModel arts=new AlbumArtModel();
			 arts.setAlbumID(t.getId());
			 arts.setSongID(songID);
			 arts.setUrls(t.getImages()[0].getUrl());
			
			 return arts;
			
		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			switch (e.getMessage()) {
			case "invalid id":
				 throw new WebApplicationException("Please chekwith your Track ID" , 404);
			default:
				throw new WebApplicationException();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new WebApplicationException();
			
		}
		
	}
}
