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
package org.springframework.social.exfm.api.impl.json;


import org.springframework.social.exfm.api.ExFmProfile;
import org.springframework.social.exfm.api.Song;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module for setting up mixin annotations on ExFm model types. This
 * enables the use of Jackson annotations without directly annotating the model
 * classes themselves.
 * 
 * @author Michael Lavelle
 */
public class ExFmModule extends SimpleModule {

	public ExFmModule() {
		super("ExFmModule", new Version(1, 0, 0, null,null,null));
	}

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(ExFmProfile.class, ExFmProfileMixin.class);
		context.setMixInAnnotations(Song.class, SongMixin.class);

	}
}
