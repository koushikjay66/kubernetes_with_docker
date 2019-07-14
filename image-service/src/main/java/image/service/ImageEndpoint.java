package image.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Image;

import image.exceptions.ClientRequestException;
import image.models.AlbumArtModel;
import image.models.ErrorMessage;
import spotify.configurations.CustomSpotifyApi;
import image.exceptions.RemoteApiException;
import image.exceptions.ResourceNotFoundException;

/**
 * @author Kowshik Dipta Das Joy <kowshik-dipta-das.joy@stud.uni-bamberg.de
 */

@Produces(MediaType.APPLICATION_JSON)
@Path("cover")
public class ImageEndpoint {
	//e.g http://localhost:8080/image-service/cover/15JINEqzVMv3SvJTAXAKED
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{songID}")
	public AlbumArtModel getImage(@PathParam("songID") String songID) {
		
		if(songID == null) {
			throw new ClientRequestException(new ErrorMessage("Required Parameter is missing"));
		}
		
		try {
			
			AlbumSimplified t = CustomSpotifyApi.getInstance().getTrack(songID).build().execute().getAlbum();
			
			//throw this if we have no matching track
			if(t == null) {
				throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching track found for track id: %s", songID)));
			}
			
			Image image = t.getImages()[0];
			
			//throw this if we have no image
			if(image == null) {
				throw new ResourceNotFoundException(new ErrorMessage(String.format("No image found for track id: %s", songID)));
			}
			
			AlbumArtModel arts = new AlbumArtModel();
			arts.setAlbumID(t.getId());
			arts.setSongID(songID);
			arts.setUrls(image.getUrl());
			
			return arts;
			
		} catch (NotFoundException e) {
			throw new ResourceNotFoundException(new ErrorMessage(e.getMessage()));
		} catch (BadRequestException e) {
			throw new ClientRequestException(new ErrorMessage(e.getMessage()));
		} catch (SpotifyWebApiException | IOException e){
			throw new RemoteApiException(new ErrorMessage(e.getMessage()));
		}
		
	}
}
