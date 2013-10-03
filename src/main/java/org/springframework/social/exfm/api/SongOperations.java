package org.springframework.social.exfm.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongOperations {

	public Song getSongBySourceUrl(String sourceUrl);

	public Song getSongById(String songId);

	public void loveSongById(String songId);

	public void loveSongBySourceUrl(String sourceUrl);

	public void loveSongById(String songId, String contextUrl, String fromUser);

	public void loveSongBySourceUrl(String sourceUrl, String contextUrl, String fromUser);

	public Page<Song> search(String searchText);

	public Page<Song> search(String searchText, Pageable pageable);

}
