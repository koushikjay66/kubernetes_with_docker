package de.uniba.dsg.models;

/**
 * TODO:
 * Song attributes should be
 * - title:String
 * - artist:String (possibly multiple artists concatenated with ", ")
 * - duration:double (in seconds)
 */
public class Song {
	private String title;
	private String artist_;
	private double duration;


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist_() {
		return artist_;
	}
	public void setArtist_(String artist_) {
		this.artist_ = artist_;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}


}