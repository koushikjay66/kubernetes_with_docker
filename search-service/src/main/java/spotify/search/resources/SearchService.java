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
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

import spotify.search.entities.ArtistInfo;
import spotify.search.entities.ErrorMessage;
import spotify.search.entities.TrackInfo;
import spotify.search.exception.ClientRequestException;
import spotify.search.exception.RemoteApiException;
import spotify.search.exception.ResourceNotFoundException;
import spotify.configurations.CustomSpotifyApi;

@Path("tracks")
public class SearchService {
	//e.g http://localhost:9999/tracks/search?title=faded&artist=alan%20walker
	
	@GET
	@Path("/search")
	public TrackInfo searchTrack(@QueryParam("title") String title, @QueryParam("artist") String artist) {
		
		if(title == null) {
			throw new ClientRequestException(new ErrorMessage("Required Parameters is missing"));
		}
		
		SearchTracksRequest request = CustomSpotifyApi
				.getInstance()
				.searchTracks(title)
				.build();
		
		try {
			
			Paging<Track> paging = request.execute();
			Track[] tracks = paging.getItems();
			
			if (tracks != null && tracks.length == 0) {
				throw new ResourceNotFoundException(new ErrorMessage(String.format("No matching track found for query: %s", title)));
			}
			
			Track track = tracks[0];
			TrackInfo info = new TrackInfo();
			String str = "";
			info.setTrackId(track.getId());
			info.setSongTitle(track.getName());
			if(track.getArtists().length > 1) {
				for(int i=0; i<track.getArtists().length; i++ ) {
					str = str + ", " + track.getArtists()[i].getName();
				}
				str = str.substring(2, str.length());
				info.setArtistsName(str);
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
	
	@GET
	@Path("/artist")
	public ArtistInfo searchArtist(@QueryParam("artist") String artistName) {
		
		//check if artist name is null means no artistId is passed to the method getTopTracks 
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
