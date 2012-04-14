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

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.exfm.api.impl.json.AbstractPaginatedResponse;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public abstract class AbstractExFmOperations {

	protected final RestTemplate restTemplate;
	protected final boolean isAuthorizedForUser;
	private String apiBaseUrl;

	public AbstractExFmOperations(String apiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		this.restTemplate = restTemplate;
		this.isAuthorizedForUser = isAuthorizedForUser;
		this.apiBaseUrl = apiBaseUrl;
	}	
	
	protected void requireAuthorization() {
		if (!isAuthorizedForUser) {
			throw new MissingAuthorizationException();
		}
	}

	protected String getApiBaseUrl() {
		return apiBaseUrl;
	}

}
