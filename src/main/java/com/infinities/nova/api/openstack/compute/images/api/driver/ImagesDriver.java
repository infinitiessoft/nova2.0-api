package com.infinities.nova.api.openstack.compute.images.api.driver;

import com.infinities.nova.api.NovaRequestContext;

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
