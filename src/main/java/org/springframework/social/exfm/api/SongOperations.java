package org.springframework.social.exfm.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongOperations {

	public Song getSongBySourceUrl(String sourceUrl);
	public Song getSongById(String songId);
	public void loveSongById(String songId);
	public void loveSongBySourceUrl(String sourceUrl);

	public Page<Song> search(String searchText);
	public Page<Song> search(String searchText,Pageable pageable);


}
