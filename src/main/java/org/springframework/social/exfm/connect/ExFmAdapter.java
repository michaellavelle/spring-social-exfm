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

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.exfm.api.ExFm;
import org.springframework.social.exfm.api.ExFmProfile;

/**
 * ExFm ApiAdapter implementation.
 * 
 * @author Michael Lavelle
 */
public class ExFmAdapter implements ApiAdapter<ExFm> {

	@Override
	public UserProfile fetchUserProfile(ExFm exFm) {
		ExFmProfile profile = exFm.meOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getName()).setUsername(profile.getUsername()).build();

	}

	@Override
	public void setConnectionValues(ExFm exFm, ConnectionValues values) {
		ExFmProfile profile = exFm.meOperations().getUserProfile();
		values.setProviderUserId(profile.getUsername());
		values.setDisplayName(profile.getName());
		values.setProfileUrl("http://ex.fm/" + profile.getUsername());
		values.setImageUrl(profile.getImage() == null ? null : profile.getImage().getOriginal());
	}

	@Override
	public boolean test(ExFm exFm) {
		try {
			exFm.meOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void updateStatus(ExFm exFm, String arg1) {

	}

}
