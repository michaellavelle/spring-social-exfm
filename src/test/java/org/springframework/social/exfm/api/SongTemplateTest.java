package org.springframework.social.exfm.api;

import org.springframework.social.exfm.api.impl.ExFmTemplate;

public class SongTemplateTest extends AbstractSongTemplateTest {


	private final static String USERNAME = "someUserName";
	private final static String PASSWORD = "somePassword";
	
	@Override
	protected String getAccessToken() {
		// No access token for the non-oauth SongTemplate
		return null;
	}

	@Override
	protected ExFmTemplate createExFmTemplate() {
		return new ExFmTemplate(API_BASE_URL, USERNAME, PASSWORD);

	}

	@Override
	protected String getUserNamePasswordQueryString() {
		return "?username=" + USERNAME + "&password=" + PASSWORD;

	}

}
