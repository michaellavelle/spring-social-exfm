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

import org.springframework.social.exfm.api.UserOperations;
import org.springframework.social.exfm.api.UsersOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class UsersTemplate extends AbstractExFmOperations implements UsersOperations {

	public UsersTemplate(String apiBaseUrl, RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(apiBaseUrl, restTemplate, isAuthorizedForUser);
	}

	@Override
	public UserOperations userOperations(String username) {
		return new UserTemplate(getApiBaseUrl(), restTemplate, isAuthorizedForUser, username);
	}

}
