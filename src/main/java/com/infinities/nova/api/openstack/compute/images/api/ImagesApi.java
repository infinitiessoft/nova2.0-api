package com.infinities.nova.api.openstack.compute.images.api;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.Common.PaginationParams;
import com.infinities.nova.api.openstack.compute.images.ImagesFilter;
import com.infinities.nova.response.model.Image;

public interface ImagesApi {

	// List<Image> getAll(NovaRequestContext context, Map<String, String>
	// filters,
	// Map<String, Object> pageParams);

	// includeLocation=false, showDeleted=false
	Image get(NovaRequestContext context, String imageId) throws Exception;

	// void destroy(NovaRequestContext context, String imageId) throws
	// Exception;

	// marker=null, limit=null, sortKey="createdAt", sortDir="desc",
	// memberStatus="accepted", isPublic=null, adminAsUser=false,
	// returnTag=false
	// List<Image> getAll(NovaRequestContext context, String name, String
	// status, String containerFormat, String diskFormat,
	// Long minRam, Long minDisk, Long sizeMin, Long sizeMax, Calendar
	// changesSince, Boolean protectedValue,
	// Boolean deleted, Integer limit, String sortKey, String sortDir, String
	// marker, String memberStatus,
	// Boolean adminAsUser, String visibility) throws Exception;

	List<Image> getAll(NovaRequestContext context, ImagesFilter filters, PaginationParams pageParams) throws Exception;

	// data=null, purgeProps=false
	// Image update(NovaRequestContext context, String imageId, Image imageInfo,
	// Object data, Boolean purgeProps)
	// throws Exception;

	void delete(NovaRequestContext context, String imageId) throws Exception;

}
