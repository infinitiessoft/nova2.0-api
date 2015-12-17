package com.infinities.nova.api.openstack.compute.images.api;

import java.util.ArrayList;
import java.util.List;

import com.infinities.nova.api.Context;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.Common.PaginationParams;
import com.infinities.nova.api.openstack.compute.images.ImagesFilter;
import com.infinities.nova.api.openstack.compute.images.api.driver.ImagesDriver;
import com.infinities.nova.model.home.ImageHome;
import com.infinities.nova.model.home.impl.ImageHomeImpl;
import com.infinities.nova.response.model.Image;

public class ImagesApiImpl implements ImagesApi {

	private final ImageHome db;
	private final ImagesDriver driver;


	public ImagesApiImpl(ImagesDriver driver) {
		this.db = new ImageHomeImpl();
		this.driver = driver;
	}

	@Override
	public Image get(NovaRequestContext context, String imageId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		Image image = normalizeImage(db.imageGet(context, imageId));
		return image;
	}

	private Image normalizeImage(com.infinities.nova.db.model.Image input) {
		Image output = new Image();
		output.setCreated(input.getCreatedAt());
		output.setId(input.getImageId());
		output.setMetadata(input.getMetadata());
		output.setMinDisk(input.getMinDisk());
		output.setMinRam(input.getMinRam());
		output.setName(input.getName());
		output.setProgress(input.getProgress());
		Image.Server server = new Image.Server();
		server.setId(input.getServer());
		output.setServer(server);
		output.setSize(input.getSize());
		output.setStatus(input.getStatus());
		output.setUpdated(input.getUpdatedAt());

		return output;
	}

	@Override
	public List<Image> getAll(NovaRequestContext context, ImagesFilter filters, PaginationParams pageParams)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		List<Image> images = new ArrayList<Image>();
		for (com.infinities.nova.db.model.Image image : db.imageGetAll(context, filters, pageParams)) {
			images.add(normalizeImage(image));
		}
		return images;
	}

	@Override
	public void delete(NovaRequestContext context, String imageId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		driver.delete(context, imageId);
	}

}
