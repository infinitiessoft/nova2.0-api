package com.infinities.nova.api.openstack.compute.images.api;

import java.util.Map;

import com.infinities.nova.api.NovaRequestContext;

public interface ImageMetadataApi {

	void updateAll(NovaRequestContext context, String imageId, Map<String, String> metadata) throws Exception;

	void create(NovaRequestContext context, String imageId, Map<String, String> metadata) throws Exception;

	void update(NovaRequestContext context, String imageId, String key, Map<String, String> meta) throws Exception;

	void delete(NovaRequestContext context, String imageId, String key) throws Exception;

}
