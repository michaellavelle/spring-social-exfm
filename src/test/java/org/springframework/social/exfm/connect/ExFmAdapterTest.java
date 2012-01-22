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
package org.springframework.social.exfm.connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.exfm.api.ExFm;
import org.springframework.social.exfm.api.ExFmProfile;
import org.springframework.social.exfm.api.Image;
import org.springframework.social.exfm.api.MeOperations;

/**
 * @author Michael Lavelle
 */
public class ExFmAdapterTest {

	private ExFmAdapter apiAdapter = new ExFmAdapter();

	private ExFm exFm = Mockito.mock(ExFm.class);

	@Test
	public void fetchProfile() {
		MeOperations meOperations = Mockito.mock(MeOperations.class);
		Mockito.when(exFm.meOperations()).thenReturn(meOperations);
		Mockito.when(meOperations.getUserProfile()).thenReturn(
				createExFmProfile());

		UserProfile profile = apiAdapter.fetchUserProfile(exFm);
		assertEquals("Matt Slip", profile.getName());
		assertEquals("Matt", profile.getFirstName());
		assertEquals("Slip", profile.getLastName());
		assertNull(profile.getEmail());
		assertEquals("mattslip", profile.getUsername());
	}

	private ExFmProfile createExFmProfile() {
		ExFmProfile exFmProfile = new ExFmProfile("mattslip");
		exFmProfile.setName("Matt Slip");

		Image image = new Image();
		image.setSmall("http://images.extension.fm/avatar_small_mattslip");
		image.setMedium("http://images.extension.fm/avatar_medium_mattslip");
		image.setOriginal("http://images.extension.fm/avatar_orig_mattslip");

		exFmProfile.setImage(image);

		return exFmProfile;
	}

	@Test
	public void setConnectionValues() {
		MeOperations meOperations = Mockito.mock(MeOperations.class);

		Mockito.when(exFm.meOperations()).thenReturn(meOperations);
		Mockito.when(meOperations.getUserProfile()).thenReturn(
				createExFmProfile());

		TestConnectionValues connectionValues = new TestConnectionValues();
		apiAdapter.setConnectionValues(exFm, connectionValues);
		assertEquals("Matt Slip", connectionValues.getDisplayName());
		assertEquals("http://images.extension.fm/avatar_orig_mattslip",
				connectionValues.getImageUrl());
		assertEquals("http://ex.fm/mattslip", connectionValues.getProfileUrl());
		assertEquals("mattslip", connectionValues.getProviderUserId());
	}

	private static class TestConnectionValues implements ConnectionValues {

		private String displayName;
		private String imageUrl;
		private String profileUrl;
		private String providerUserId;

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getProfileUrl() {
			return profileUrl;
		}

		public void setProfileUrl(String profileUrl) {
			this.profileUrl = profileUrl;
		}

		public String getProviderUserId() {
			return providerUserId;
		}

		public void setProviderUserId(String providerUserId) {
			this.providerUserId = providerUserId;
		}

	}
}
