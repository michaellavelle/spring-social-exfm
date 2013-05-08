/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.exfm.api;

/**
 * @author Michael Lavelle
 */
public class Song {

	private String id;
	private String url;
	private String title;
	private String artist;
	
	public String getArtist() {
		return artist;
	}

	private Image image;

	public Song(String id, String url,String artist, String title) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.artist = artist;
	}

	public String getId() {
		return id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

}
