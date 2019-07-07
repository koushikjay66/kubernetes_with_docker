package de.uniba.dsg.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO:
 * PlaylistRequest attributes should be
 * - title:String
 * - artistSeeds:List<String>, must be serialized as 'seeds'
 * - numberOfSongs:int, must be serialized as 'size'
 */
@XmlRootElement
public class PlaylistRequest {

	public String title;
	public List<String> seeds;
	public int size;
}
