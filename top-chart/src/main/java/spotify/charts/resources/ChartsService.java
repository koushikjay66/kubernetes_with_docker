package spotify.charts.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;

import spotify.charts.entities.ErrorMessage;
import spotify.charts.entities.Song;
import spotify.charts.exception.ClientRequestException;
import spotify.charts.exception.RemoteApiException;
import spotify.charts.exception.ResourceNotFoundException;
import spotify.configurations.CustomSpotifyApi;

@Path("charts")
public class ChartsService {
	//e.g http://localhost:9999/charts/3TVXtAsR1Inumwj472S9r4
	
	@GET
	@Path("{artistID}")
	public List<Song> getTopTracks(@PathParam("artistID") String artistId) {
		
		//check if artistId is null means no artistId is passed to the method getTopTracks 
		if(artistId == null) {
			throw new ClientRequestException(new ErrorMessage("Required Parameter artistID is missing"));
		}
		
		CountryCode countryCode = CountryCode.SE;
		GetArtistsTopTracksRequest topTracksRequest = CustomSpotifyApi.getInstance()
				.getArtistsTopTracks(artistId, countryCode)
				.build();
		
		try {
			
			Track[] allTracks = topTracksRequest.execute();
			
			if(allTracks == null) {
				throw new ResourceNotFoundException(new ErrorMessage(String.format("No songs found for this id: %s", artistId)));
			}
			
			List<Track> tracks = new ArrayList<>();
			tracks = Arrays.asList(allTracks);
			List<Song> songs = new ArrayList<Song>();
			for(int i = 0; i < 5 && tracks.get(i) != null; i++) {
				Song song = new Song();
				song.setTitle(tracks.get(i).getName());
				song.setArtist(tracks.get(i).getArtists()[0].getName());
				song.setDuration((double)tracks.get(i).getDurationMs()/1000); //change duration to seconds
				
				songs.add(song);
			}
			
			return songs;
		} catch (NotFoundException e) {
			throw new ResourceNotFoundException(new ErrorMessage(e.getMessage()));
		} catch (BadRequestException e) {
			throw new ClientRequestException(new ErrorMessage(e.getMessage()));
		} catch (SpotifyWebApiException | IOException e){
			throw new RemoteApiException(new ErrorMessage(e.getMessage()));
		}	
	}
}
