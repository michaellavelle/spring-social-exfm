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

import org.springframework.social.exfm.api.ExFm;
import org.springframework.social.exfm.api.impl.ExFmTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * ExFm ServiceProvider implementation.
 * 
 * @author Michael Lavelle
 */
public class ExFmServiceProvider extends AbstractOAuth2ServiceProvider<ExFm> {

	private String oauthApiBaseUrl;

	public ExFmServiceProvider(String clientId, String clientSecret,
			String oauthAuthorizeUrl, String oauthTokenUrl,
			String oauthApiBaseUrl) {
		super(new ExFmOAuth2Template(clientId, clientSecret, oauthAuthorizeUrl,
				oauthTokenUrl));
		this.oauthApiBaseUrl = oauthApiBaseUrl;
	}

	@Override
	public ExFm getApi(String accessToken) {
		return new ExFmTemplate(oauthApiBaseUrl, accessToken);
	}

}
