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
package com.infinities.nova.images.api;

import java.util.List;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.AbstractPaginableController.PaginationParams;
import com.infinities.nova.images.controller.ImagesFilter;
import com.infinities.nova.images.model.Image;

public interface ImagesApi {

	// List<Image> getAll(OpenstackRequestContext context, Map<String, String>
	// filters,
	// Map<String, Object> pageParams);

	// includeLocation=false, showDeleted=false
	Image get(OpenstackRequestContext context, String imageId) throws Exception;

	// void destroy(OpenstackRequestContext context, String imageId) throws
	// Exception;

	// marker=null, limit=null, sortKey="createdAt", sortDir="desc",
	// memberStatus="accepted", isPublic=null, adminAsUser=false,
	// returnTag=false
	// List<Image> getAll(OpenstackRequestContext context, String name, String
	// status, String containerFormat, String diskFormat,
	// Long minRam, Long minDisk, Long sizeMin, Long sizeMax, Calendar
	// changesSince, Boolean protectedValue,
	// Boolean deleted, Integer limit, String sortKey, String sortDir, String
	// marker, String memberStatus,
	// Boolean adminAsUser, String visibility) throws Exception;

	List<Image> getAll(OpenstackRequestContext context, ImagesFilter filters, PaginationParams pageParams) throws Exception;

	// data=null, purgeProps=false
	// Image update(OpenstackRequestContext context, String imageId, Image
	// imageInfo,
	// Object data, Boolean purgeProps)
	// throws Exception;

	void delete(OpenstackRequestContext context, String imageId) throws Exception;

}
