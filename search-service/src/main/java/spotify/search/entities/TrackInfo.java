package spotify.search.entities;

public class TrackInfo {
	private String trackId;
	private String artistName;
	private String artistsName;
	private String songTitle;
	/**
	 * @return the songTitle
	 */
	public String getSongTitle() {
		return songTitle;
	}
	/**
	 * @param songTitle the songTitle to set
	 */
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	/**
	 * @return the trackId
	 */
	public String getTrackId() {
		return trackId;
	}
	/**
	 * @param trackId the trackId to set
	 */
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	/**
	 * @return the artistName
	 */
	public String getArtistName() {
		return artistName;
	}
	/**
	 * @param artistName the artistName to set
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	/**
	 * @return the artistsName
	 */
	public String getArtistsName() {
		return artistsName;
	}
	/**
	 * @param artistsName the artistsName to set
	 */
	public void setArtistsName(String artistsName) {
		this.artistsName = artistsName;
	}
}
