/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.nova.images.api.driver;

import com.infinities.nova.NovaRequestContext;

public interface ImagesDriver {

	// public interface ImagesService {
	//
	// List<Image> getDetail(NovaRequestContext context, Map<String, String>
	// filters, Map<String, Object> pageParams);
	//
	// Image show(NovaRequestContext context, String imageId, boolean
	// includeLocations) throws Exception;
	//
	// void delete(NovaRequestContext context, String imageId);
	// }
	//
	// ImagesService getDefaultImageService();
	//
	// Entry<ImagesService, String> getRemoteImageService(NovaRequestContext
	// context, String idOrUri);

	void delete(NovaRequestContext context, String imageId) throws Exception;

}
