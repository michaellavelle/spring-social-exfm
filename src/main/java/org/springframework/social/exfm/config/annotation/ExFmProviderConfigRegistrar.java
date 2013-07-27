package org.springframework.social.exfm.config.annotation;
/*
 * Copyright 2013 the original author or authors.
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

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.social.config.annotation.AbstractProviderConfigRegistrarSupport;
import org.springframework.social.exfm.api.ExFm;
import org.springframework.social.exfm.config.support.ExFmApiHelper;
import org.springframework.social.exfm.connect.ExFmConnectionFactory;
import org.springframework.social.exfm.security.ExFmAuthenticationService;
import org.springframework.social.security.provider.SocialAuthenticationService;


/**
 * {@link ImportBeanDefinitionRegistrar} for configuring a {@link ExFmConnectionFactory} bean and a request-scoped {@link ExFm} bean.
 * @author Michael Lavelle
 */
public class ExFmProviderConfigRegistrar extends AbstractProviderConfigRegistrarSupport {

	public ExFmProviderConfigRegistrar() {
		super(EnableExFm.class, ExFmConnectionFactory.class, ExFmApiHelper.class);
	}
	
	
	@Override
	protected BeanDefinition getConnectionFactoryBeanDefinition(String appId,
			String appSecret, Map<String, Object> allAttributes) {
		return BeanDefinitionBuilder.genericBeanDefinition(connectionFactoryClass).addConstructorArgValue(appId).
		addConstructorArgValue(appSecret).addConstructorArgValue(getOauthAuthorizeUrl(allAttributes)).addConstructorArgValue(getOauthTokenUrl(allAttributes)).addConstructorArgValue(getOauthApiBaseUrl(allAttributes)).getBeanDefinition();
	}


	@Override
	protected BeanDefinition getAuthenticationServiceBeanDefinition(
			String appId, String appSecret, Map<String, Object> allAttributes) {
		return BeanDefinitionBuilder.genericBeanDefinition(authenticationServiceClass).addConstructorArgValue(appId).
		addConstructorArgValue(appSecret).addConstructorArgValue(getOauthAuthorizeUrl(allAttributes)).addConstructorArgValue(getOauthTokenUrl(allAttributes)).addConstructorArgValue(getOauthApiBaseUrl(allAttributes)).getBeanDefinition();

	}


	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		return ExFmAuthenticationService.class;
	}
	
	protected String getOauthAuthorizeUrl(Map<String, Object> allAttributes) {
		return (String)allAttributes.get("oauthAuthorizeUrl");
	}
	
	protected String getOauthTokenUrl(Map<String, Object> allAttributes) {
		return (String)allAttributes.get("oauthTokenUrl");
	}
	

	protected String getOauthApiBaseUrl(Map<String, Object> allAttributes) {
		return (String)allAttributes.get("oauthApiBaseUrl");
	}
	
	
}
