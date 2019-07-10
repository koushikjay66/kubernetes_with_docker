package spotify.search.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import spotify.search.entities.ErrorMessage;
import spotify.search.entities.TrackInfo;
import spotify.search.exception.ClientRequestException;
import spotify.search.exception.RemoteApiException;
import spotify.search.exception.ResourceNotFoundException;
import spotify.configurations.CustomSpotifyApi;

/**
 * 
 * @author Haroon Gul
 *
 */

@Path("tracks")
public class SearchResource {
	//e.g http://localhost:9999/tracks/search?title=faded&artist=alan%20walker
	
	@GET
	@Path("/search")
	public TrackInfo searchTrack(@QueryParam("title") String title, @QueryParam("artist") String artist) {
		
		if(title == null || title.equals("")) {
			throw new ClientRequestException(new ErrorMessage("Required Parameters is missing"));
		}
		
		//query that will be searched
		String queryToSearch;
		
		//if artist is null just search for track if not search for both
		if(artist == null || artist.equals("")) {
			queryToSearch =  title; 
		} 
		else {
			queryToSearch = title + " "+ artist;
		}
		
		//search request
		SearchItemRequest request = CustomSpotifyApi
				.getInstance()
				.searchItem(queryToSearch, "track")
				.limit(50)
				.build();
		
		try {
			
			SearchResult result = request.execute();
			Track[] tracks = result.getTracks().getItems();
			
			Track track = null;
			
			//mechanism which will match the artist name with artists of a single track
			if(artist != null) {
				
				for(Track thisTrack : tracks) {
					for(ArtistSimplified thisArtist : thisTrack.getArtists()) {
						if(thisArtist.getName().equals(artist)) {
							track = thisTrack;
							break;
						}
					}
				}
			}

			
			// if no matched track found from the above mechanism use the 1st track in the list 
			if (track == null && tracks.length > 0) {
				track = tracks[0];
			}
			
			// if there is no track found
			if(track == null) {
				throw new ResourceNotFoundException(new ErrorMessage(String.format
						("No searched track found for title: %s, artist: %s", title, artist)));
			}
			
			TrackInfo info = new TrackInfo();
			String str = "";
			info.setTrackId(track.getId());
			info.setSongTitle(track.getName());
			
			//if a track has more than 1 artist then get the names of these artist
			if(track.getArtists().length > 1) {
				for(ArtistSimplified a : track.getArtists()) {
					str = str + ", " + a.getName();
				}
				str = str.substring(2, str.length());
				info.setArtistName(str);
			}
			else {
				info.setArtistName(track.getArtists()[0].getName());
			}
			
			return info;
			
		} catch (NotFoundException e) {
			throw new ResourceNotFoundException(new ErrorMessage(e.getMessage()));
		} catch (BadRequestException e) {
			throw new ClientRequestException(new ErrorMessage(e.getMessage()));
		} catch (SpotifyWebApiException | IOException e){
			throw new RemoteApiException(new ErrorMessage(e.getMessage()));
		}	
	}
}
