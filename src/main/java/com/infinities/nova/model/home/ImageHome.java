package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.Common.PaginationParams;
import com.infinities.nova.api.openstack.compute.images.ImagesFilter;
import com.infinities.nova.db.model.Image;

public interface ImageHome {

	List<Image> imageGetAll(NovaRequestContext context, ImagesFilter filters, PaginationParams pageParams) throws Exception;

	Image imageGet(NovaRequestContext context, String imageId);

	// void imageDestroy(NovaRequestContext context, String imageId);

	// Image imageUpdate(NovaRequestContext context, String imageId,
	// com.infinities.glance.model.Image values,
	// Boolean purgeProps, String fromState) throws Exception;

}
