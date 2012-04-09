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
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.ResourceNotFoundException;

/**
 * @author Michael Lavelle
 */
public  abstract class AbstractSongTemplateTest extends AbstractExFmApiTest {

	protected abstract String getAccessToken();

	
	@Test(expected = NotAuthorizedException.class)
	public void loveSongById_unauthorized() {
		unauthorizedLastFm.songOperations().loveSongById("12345");
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void loveSongBySourceUrl_unauthorized() {
		unauthorizedLastFm.songOperations().loveSongBySourceUrl("http://abc.com");
	}


}
