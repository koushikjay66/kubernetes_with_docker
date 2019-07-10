package spotify.charts.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.exceptions.detailed.BadRequestException;
import com.wrapper.spotify.exceptions.detailed.NotFoundException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;

import spotify.charts.entities.ErrorMessage;
import spotify.charts.entities.Song;
import spotify.charts.exception.ClientRequestException;
import spotify.charts.exception.RemoteApiException;
import spotify.charts.exception.ResourceNotFoundException;
import spotify.configurations.CustomSpotifyApi;

/**
 * 
 * @author Haroon Gul
 *
 */

@Path("charts")
public class ChartsResource {
	//e.g http://localhost:9999/charts/3TVXtAsR1Inumwj472S9r4
	
	@GET
	@Path("{artistID}")
	public List<Song> getTopTracks(@PathParam("artistID") String artistId) {
		 
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
			
			List<Song> songs = new ArrayList<Song>();
			
			//get all the tracks
			for(Track track: allTracks) {
				Song song = new Song();
				song.setId(track.getId());
				song.setTitle(track.getName());
				
				//if track has more than 1 artists then get the names of all the artists
				if(track.getArtists().length > 1) {
					String artistName = "";
					for(ArtistSimplified artist : track.getArtists()) {
						artistName = artistName + ", " + artist.getName();
					}
					song.setArtist(artistName.substring(2, artistName.length()));
				}
				
				//if track has just one artist
				else {
					song.setArtist(track.getArtists()[0].getName());
				}
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
