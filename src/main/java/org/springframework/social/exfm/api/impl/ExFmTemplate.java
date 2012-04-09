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

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.exfm.api.ExFm;
import org.springframework.social.exfm.api.MeOperations;
import org.springframework.social.exfm.api.SongOperations;
import org.springframework.social.exfm.api.UsersOperations;
import org.springframework.social.exfm.api.impl.json.ExFmModule;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class ExFmTemplate extends AbstractOAuth2ApiBinding implements ExFm {

	private UsersOperations usersOperations;

	private MeOperations meOperations;
	
	private SongOperations songOperations;

	private ObjectMapper objectMapper;

	/**
	 * Create a new instance of ExFmTemplate. This constructor creates a new
	 * ExFmTemplate able to perform unauthenticated operations against ExFm's
	 * API. Some operations do not require OAuth authentication. For example,
	 * retrieving a specified user's profile or loved tracks does not require
	 * authentication . An ExFmTemplate created with this constructor will
	 * support those operations. Those operations requiring authentication will
	 * throw {@link NotAuthorizedException}.
	 */
	public ExFmTemplate(String apiBaseUrl) {
		initialize(apiBaseUrl, null);
	}

	/**
	 * Create a new instance of ExFmTemplate. This constructor creates the
	 * ExFmTemplate using a given access token, and requires a given OAuth API
	 * Url for ExFM (not currently available)
	 * 
	 * @param oauthApiBaseUrl An OAuth API Base url for ExFM (not currently available)
	 * @param accessToken
	 *            An access token given by ExFmTemplate after a successful OAuth
	 *            2 authentication
	 */
	public ExFmTemplate(String oauthApiBaseUrl, String accessToken) {
		super(accessToken);
		initialize(oauthApiBaseUrl, accessToken);
	}

	/**
	 * Create a new instance of ExFmTemplate. This constructor creates the
	 * ExFmTemplate using a given username and password, and requires the base
	 * url of the existing ExFm (not oauth) API
	 * 
	 * @param apiBaseUrl The existing ExFM API base url (eg. http://ex.fm/api/v3 )
	 * @param username
	 * @param password
	 * 
	 */
	public ExFmTemplate(String apiBaseUrl, String username, String password) {
		initialize(apiBaseUrl, username, password);
	}

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super
				.getMessageConverters();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		return messageConverters;
	}

	private void initSubApis(String oauthApiBaseUrl, String accessToken) {
		usersOperations = new UsersTemplate(oauthApiBaseUrl, getRestTemplate(),
				isAuthorized());
		meOperations = new MeTemplate(oauthApiBaseUrl, getRestTemplate(),
				isAuthorized());
		
		songOperations = new SongTemplate(oauthApiBaseUrl, getRestTemplate(),isAuthorized());

	}

	private void initSubApis(String apiBaseUrl, String username, String password) {
		usersOperations = new UsersTemplate(apiBaseUrl, getRestTemplate(),
				isAuthorized());
		meOperations = new MeTemplate(apiBaseUrl, getRestTemplate(), username,
				password);
		
		songOperations = new SongTemplate(apiBaseUrl, getRestTemplate(),username,password);

	}

	// private helpers
	private void initialize(String apiBaseUrl, String accessToken) {
		registerExFmJsonModule(getRestTemplate());
		getRestTemplate().setErrorHandler(new ExFmErrorHandler());
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()

		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(apiBaseUrl, accessToken);

	}

	// private helpers
	private void initialize(String apiBaseUrl, String username, String password) {
		registerExFmJsonModule(getRestTemplate());
		getRestTemplate().setErrorHandler(new ExFmErrorHandler());
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()

		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(apiBaseUrl, username, password);

	}

	private void registerExFmJsonModule(RestTemplate restTemplate) {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new ExFmModule());
		List<HttpMessageConverter<?>> converters = restTemplate
				.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}

	@Override
	public UsersOperations usersOperations() {
		return usersOperations;
	}

	@Override
	public MeOperations meOperations() {
		return meOperations;
	}
	
	@Override
	public SongOperations songOperations() {
		return songOperations;
	}

}
