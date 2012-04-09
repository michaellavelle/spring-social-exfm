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

import org.springframework.social.exfm.api.ExFmProfile;
import org.springframework.social.exfm.api.MeOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class MeTemplate extends AbstractUserTemplate implements MeOperations {

	private String username;
	private String password;
	private boolean useOauth;

	public MeTemplate(String oauthApiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		super(oauthApiBaseUrl, restTemplate, isAuthorizedForUser);
		this.useOauth = true;
	}

	public MeTemplate(String apiBaseUrl, RestTemplate restTemplate,
			String username, String password) {
		super(apiBaseUrl, restTemplate, true);
		this.username = username;
		this.password = password;
		this.useOauth = false;
	}

	@Override
	public ExFmProfile getUserProfile() {
		requireAuthorization();
		return super.getUserProfile();
	}

	@Override
	protected String getApiResourceUrl(String resourcePath) {
		return super.getApiResourceUrl(resourcePath)
				+ (useOauth ? ""
						: ("?username=" + username + "&password=" + password));
	}

	@Override
	protected String getApiResourceBaseUrl() {
		return getApiBaseUrl() + "/me";
	}
	
	

}
