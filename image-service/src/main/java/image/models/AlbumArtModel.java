package image.models;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlbumArtModel {

	private String albumID;
	private String SongID;
	private String urls; // Multiple Image URLS in , seperated
	public String getAlbumID() {
		return albumID;
	}
	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}
	public String getSongID() {
		return SongID;
	}
	public void setSongID(String songID) {
		SongID = songID;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String url) {
		this.urls = url;
	}
	
	public void setUrls(String [] urls) {
		
		this.urls=String.join(",", Arrays.asList(urls));
		
		
	}
	
	
}
