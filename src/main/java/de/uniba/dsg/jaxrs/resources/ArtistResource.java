package de.uniba.dsg.jaxrs.resources;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Track;

import de.uniba.dsg.CustomSpotifyApi;
import de.uniba.dsg.interfaces.ArtistApi;
import de.uniba.dsg.jaxrs.exceptions.RemoteApiException;
import de.uniba.dsg.jaxrs.exceptions.ResourceNotFoundException;
import de.uniba.dsg.models.ErrorMessage;
import de.uniba.dsg.models.Interpret;
import de.uniba.dsg.models.Song;

@Path("/artist")
public class ArtistResource implements ArtistApi{

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{artistId}")
	public Interpret getArtist(@PathParam("artistId") String artistId) {
		// TODO Auto-generated method stub

		Interpret i = null;
		System.out.println( artistId);
		if(artistId.equals(null)) {
			throw new ClientErrorException("You need to give a client ID", 400);
		}

		try {

			Artist artist_ =CustomSpotifyApi.getInstance()
					.getArtist(artistId.trim())
					.build()
					.execute();


			i = new Interpret();
			i.setGenres(Arrays.asList(artist_.getGenres()));
			i.setId(artist_.getId());
			i.setName(artist_.getName());
			i.setPopularity(artist_.getPopularity());
			return i;

		} catch (SpotifyWebApiException  e) {
			switch (e.getMessage()) {
			case "Invalid access token":
				throw new WebApplicationException(Response.status(500).build());
			case "invalid id" :
				throw new ClientErrorException("Cannot find with this Artist ID" , 404);
			default:
				throw new WebApplicationException(Response.status(500).build());
			}
			
		}catch(IOException e ) {
			throw new RemoteApiException(new ErrorMessage("IO Exceltion has been occured "+e.getMessage()));

		}

	}

	@Override
	@GET
	@Path("/topTracks/{artistID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Song> getTopTracks(@PathParam("artistID") String artistId) {

		if(artistId.equals(null)) {
			throw new ClientErrorException("You need to give a client ID", 400);
		}
		try {
			Track[] tracks= CustomSpotifyApi.getInstance()
					.getArtistsTopTracks(artistId, CountryCode.US)
					.build().execute();

			Song [] songs = new Song[tracks.length];
			Song temp_song;
			for (int i = 0; i < songs.length; i++) {
				temp_song= new Song();

				// Use String buffer to save memory.. mutable
				StringBuffer sb_artist = new StringBuffer();

				for (int j = 0; j < tracks[i].getArtists().length; j++) {
					sb_artist.append(tracks[i].getArtists()[j].getName());
					sb_artist.append(",");
				}

				// there will be last comma , remove that
				sb_artist=new StringBuffer(sb_artist.substring(0, sb_artist.length()-1));


				temp_song.setArtist_(sb_artist.toString());
				temp_song.setDuration(tracks[i].getDurationMs()*0.001d);
				temp_song.setTitle(tracks[i].getName());
				songs[i]= temp_song;
			}

			return Arrays.asList(songs);

		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			throw new ClientErrorException("Cannot find with this Artist ID" , 404);
			//	e.printStackTrace();
		} catch (IOException e) {

			throw new RemoteApiException(new ErrorMessage("IO Exceltion has been occured "+e.getMessage()));
		}
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSimilarArtist/{artistID}")
	public Interpret getSimilarArtist(@PathParam("artistID") String artistId) {
		// TODO Auto-generated method stub
		if(artistId.equals(null)) {
			throw new ClientErrorException("You need to give a client ID", 400);
		}
		try {
			Artist [] artist_ =CustomSpotifyApi.getInstance()
					.getArtistsRelatedArtists(artistId)
					.build().execute();


			// Get a Random Value here
			int i = new Random().nextInt(artist_.length-1);

			Interpret interpret_= new Interpret();
			interpret_.setId(artist_[i].getId());
			interpret_.setName(artist_[i].getName());
			interpret_.setPopularity(artist_[i].getPopularity());
			interpret_.setGenres(Arrays.asList(artist_[i].getGenres()));

			return interpret_;

		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			throw new ClientErrorException("Cannot find with this Artist ID" , 404);
		} catch (IOException e) {
			throw new RemoteApiException(new ErrorMessage("IO Exceltion has been occured "+e.getMessage()));
		}

	}

}
