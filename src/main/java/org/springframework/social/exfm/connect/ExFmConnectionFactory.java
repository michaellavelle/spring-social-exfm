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

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.exfm.api.ExFm;

/**
 * ExFm ConnectionFactory implementation.
 * 
 * @author Michael Lavelle
 */
public class ExFmConnectionFactory extends OAuth2ConnectionFactory<ExFm> {

	public ExFmConnectionFactory(String clientId, String clientSecret, String oauthAuthorizeUrl, String oauthTokenUrl,
			String oauthApiBaseUrl) {
		super("exfm",
				new ExFmServiceProvider(clientId, clientSecret, oauthAuthorizeUrl, oauthTokenUrl, oauthApiBaseUrl),
				new ExFmAdapter());
	}

	@Override
	public boolean supportsStateParameter() {
		return false;
	}

}
