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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.header;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.ResourceNotFoundException;

/**
 * @author Michael Lavelle
 */
public abstract class AbstractUserTemplateTest extends AbstractExFmApiTest {

	protected abstract String getAccessToken();

	@Test
	public void getUserProfile_currentUser() {
		mockServer
				.expect(requestTo(API_BASE_URL + "/me"
						+ getUserNamePasswordQueryString()))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("testdata/full-profile"),
								responseHeaders));

		if (getAccessToken() != null) {
			mockServer.expect(header("Authorization", "Bearer "
					+ getAccessToken()));

		}

		ExFmProfile profile = exFm.meOperations().getUserProfile();
		assertBasicProfileData(profile);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getUserProfile_currentUser_unauthorized() {
		unauthorizedLastFm.meOperations().getUserProfile();
	}

	@Test
	public void getUserProfile_specificUserByUserId() {

		mockServer.expect(requestTo(API_BASE_URL + "/user/mattslip"))
				.andExpect(method(GET))
				// .andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/full-profile"),
								responseHeaders));

		ExFmProfile profile = exFm.usersOperations().userOperations("mattslip")
				.getUserProfile();
		assertBasicProfileData(profile);
	}

	@Test
	public void getLovedSongs() {

		mockServer.expect(requestTo(API_BASE_URL + "/user/mattslip/loved"))
				.andExpect(method(GET))
				// .andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/loved-songs"),
								responseHeaders));

		Page<Song> songs = exFm.usersOperations().userOperations("mattslip")
				.getLovedSongs();
		assertSongData(songs.getContent().get(0));
	}

	@Test
	public void getLovedSongs_withoutAuthorization() {

		mockUnauthorizedServer
				.expect(requestTo(API_BASE_URL + "/user/mattslip/loved"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("testdata/loved-songs"),
								responseHeaders));

		Page<Song> songList = unauthorizedLastFm.usersOperations()
				.userOperations("mattslip").getLovedSongs();
		assertSongData(songList.getContent().get(0));

	}

	@Test(expected = ResourceNotFoundException.class)
	public void getLovedSongs_unknownUser() {

		mockServer
				.expect(requestTo(API_BASE_URL + "/user/someOtherUser/loved"))
				.andExpect(method(GET))
				// .andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/unknown-user"),
								responseHeaders));

		exFm.usersOperations().userOperations("someOtherUser").getLovedSongs();

	}

	private void assertBasicProfileData(ExFmProfile profile) {
		assertEquals("mattslip", profile.getUsername());
		assertEquals("Matt Slip", profile.getName());
	}

	private void assertBasicSongData(Song song) {
		assertEquals("Madonna – Get Together (Monsieur Adi Remix)",
				song.getTitle());
		assertEquals("Madonna",song.getArtist());
	}

	private void assertSongData(Song song) {
		assertBasicSongData(song);
		assertEquals(
				"http://ohmyrock.net/wp-content/uploads/2011/06/FM22/22-08%20Get%20Together%20%28Monsieur%20Adi%20Remix%29.mp3",
				song.getUrl());

	}

}
