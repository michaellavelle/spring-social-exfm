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

import java.io.Serializable;

/**
 * @author Michael Lavelle
 */
public class ExFmProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String name;
	private String bio;
	private int totalLoved;
	private int totalFollowing;
	private int totalFollowers;

	private String location;
	private String website;
	private boolean online;
	private boolean isBetaTester;
	private boolean viewerFollowing;

	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getBio() {
		return bio;
	}

	public int getTotalLoved() {
		return totalLoved;
	}

	public int getTotalFollowing() {
		return totalFollowing;
	}

	public int getTotalFollowers() {
		return totalFollowers;
	}

	public String getLocation() {
		return location;
	}

	public String getWebsite() {
		return website;
	}

	public boolean isOnline() {
		return online;
	}

	public boolean isBetaTester() {
		return isBetaTester;
	}

	public boolean isViewerFollowing() {
		return viewerFollowing;
	}

	public void setTotalLoved(int totalLoved) {
		this.totalLoved = totalLoved;
	}

	public void setTotalFollowing(int totalFollowing) {
		this.totalFollowing = totalFollowing;
	}

	public void setTotalFollowers(int totalFollowers) {
		this.totalFollowers = totalFollowers;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setBetaTester(boolean isBetaTester) {
		this.isBetaTester = isBetaTester;
	}

	public void setViewerFollowing(boolean viewerFollowing) {
		this.viewerFollowing = viewerFollowing;
	}

	public ExFmProfile(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

}
