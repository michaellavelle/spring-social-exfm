package org.springframework.social.exfm.api;

public interface SongOperations {

	public Song getSongBySourceUrl(String sourceUrl);
	public Song getSongById(String songId);
	public void loveSongById(String songId);
	public void loveSongBySourceUrl(String sourceUrl);


}
