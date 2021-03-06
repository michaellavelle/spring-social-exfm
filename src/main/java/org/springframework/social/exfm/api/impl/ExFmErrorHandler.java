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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.ServerDownException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Subclass of {@link DefaultResponseErrorHandler} that handles errors from
 * ExFms's API, interpreting them into appropriate exceptions.
 * 
 * @author Michael Lavelle
 */
class ExFmErrorHandler extends DefaultResponseErrorHandler {

	private void handleUncategorizedError(ClientHttpResponse response, Status status) throws IOException {
		try {
			super.handleError(response);
		} catch (Exception e) {
			if (status != null && !status.getStatus_code().equals("200")) {
				String m = status.getStatus_text();
				throw new UncategorizedApiException("exfm", m, e);
			} else {
				String content = readFully(response.getBody());
				if (content.contains("404 NOT FOUND")) {
					throw new ResourceNotFoundException("exfm", "Not found");
				}

				throw new UncategorizedApiException("exfm", "No error details from ExFm", e);
			}
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		Status errorDetails = extractErrorDetailsFromResponse(response);

		if (errorDetails == null || errorDetails.getStatus_code().equals("200")) {
			handleUncategorizedError(response, errorDetails);
		}
		handleExFmError(response.getStatusCode(), errorDetails);

		// if not otherwise handled, do default handling and wrap with
		// UncategorizedApiException
		handleUncategorizedError(response, errorDetails);
	}

	private boolean isIgnorableError(Status status) {
		// Loving a song which is already loved results in status code 400, with
		// certain text - don't treat as an error
		return status != null && "Already loved this song".equals(status.getStatus_text());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {

		if (super.hasError(response)) {
			Status errorDetails = extractErrorDetailsFromResponse(response);
			return !isIgnorableError(errorDetails);
		}
		// only bother checking the body for errors if we get past the default
		// error check
		String content = readFully(response.getBody());
		return !content.contains("\"status_code\": 200") && !content.contains("\"status_code\":200");
	}

	void handleExFmError(HttpStatus statusCode, Status status) {

		String message = status.getStatus_text();

		HttpStatus httpStatus = statusCode != HttpStatus.OK ? statusCode : HttpStatus.valueOf(Integer.parseInt(status
				.getStatus_code()));

		if (httpStatus == HttpStatus.OK) {
			// Should never happen
		} else if (httpStatus == HttpStatus.BAD_REQUEST) {
			throw new ResourceNotFoundException("exfm", message);

		} else if (httpStatus == HttpStatus.NOT_FOUND) {
			throw new ResourceNotFoundException("exfm", message);

		} else if (httpStatus == HttpStatus.UNAUTHORIZED) {

			throw new NotAuthorizedException("exfm", message);
		} else if (httpStatus == HttpStatus.FORBIDDEN) {

			throw new OperationNotPermittedException("exfm", message);
		} else if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
			throw new InternalServerErrorException("exfm", message);
		} else if (httpStatus == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new ServerDownException("exfm", message);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Status {
		private String status_code;
		private String status_text;

		public String getStatus_code() {
			return status_code;
		}

		public void setStatus_code(String status_code) {
			this.status_code = status_code;
		}

		public String getStatus_text() {
			return status_text;
		}

		public void setStatus_text(String status_text) {
			this.status_text = status_text;
		}

	}

	/*
	 * Attempts to extract ExFm error details from the response. Returns null if
	 * the response doesn't match the expected JSON error response.
	 */
	private Status extractErrorDetailsFromResponse(ClientHttpResponse response) throws IOException {

		ObjectMapper mapper = new ObjectMapper(new JsonFactory());

		try {
			String json = readFully(response.getBody());
			Status responseStatus = mapper.<Status> readValue(json, new TypeReference<Status>() {
			});
			if (!responseStatus.getStatus_code().equals("200")) {
				return responseStatus;
			} else {
				return null;
			}
		} catch (JsonParseException e) {
			return null;
		}

	}

	private String readFully(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		while (reader.ready()) {
			sb.append(reader.readLine());
		}
		return sb.toString();
	}

}
