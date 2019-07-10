package spotify.search.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;

import spotify.configurations.CustomSpotifyApi;
import spotify.search.entities.ArtistInfo;
import spotify.search.entities.ErrorMessage;
import spotify.search.exception.ClientRequestException;
import spotify.search.exception.RemoteApiException;
import spotify.search.exception.ResourceNotFoundException;

/**
 * 
 * @author Haroon Gul
 *
 */

@Path("/artist")
public class ArtistResource {
	
	//e.g http://localhost:9999/artist/search?name=drake
	
	@GET
	@Path("search")
	public ArtistInfo searchArtist(@QueryParam("name") String artistName) {
		
		if(artistName == null) {
			throw new ClientRequestException(new ErrorMessage("Required Parameters is missing"));
		}
		
		SearchArtistsRequest artistsRequest = CustomSpotifyApi
				.getInstance()
				.searchArtists(artistName)
				.build();
		
		try {
			
			Paging<Artist> paging = artistsRequest.execute();
			Artist[] artists = paging.getItems();
			
			if(artists != null && artists.length == 0) {
				throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching artist found for query: %s", artistName)));
			}
			
			Artist artist = artists[0];
			ArtistInfo artistInfo = new ArtistInfo();
			artistInfo.setArtistId(artist.getId());
			artistInfo.setArtistName(artist.getName());
			
			return artistInfo;
		
		} catch (NotFoundException e) {
			throw new ResourceNotFoundException(new ErrorMessage(e.getMessage()));
		} catch (BadRequestException e) {
			throw new ClientRequestException(new ErrorMessage(e.getMessage()));
		} catch (SpotifyWebApiException | IOException e){
			throw new RemoteApiException(new ErrorMessage(e.getMessage()));
		}	
	}
}
