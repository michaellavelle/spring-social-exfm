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
package org.springframework.social.exfm.api.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.exfm.api.Song;
import org.springframework.social.exfm.api.UserOperations;
import org.springframework.social.exfm.api.impl.json.ExFmSongsResponse;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class UserTemplate extends AbstractUserTemplate implements UserOperations {

	private String userId;

	public UserTemplate(String apiBaseUrl, RestTemplate restTemplate, boolean isAuthorizedForUser, String userId) {
		super(apiBaseUrl, restTemplate, isAuthorizedForUser);
		this.userId = userId;
	}

	@Override
	protected String getApiResourceBaseUrl() {
		return getApiBaseUrl() + "/user/" + userId;
	}

	@Override
	public Page<Song> getLovedSongs(Pageable pageable) {
		ExFmSongsResponse songsResponse = restTemplate.getForObject(getApiResourceUrl("/loved", pageable),
				ExFmSongsResponse.class);
		return songsResponse.createPage(pageable);
	}

	@Override
	public Page<Song> getLovedSongs() {
		return getLovedSongs(null);
	}

}
