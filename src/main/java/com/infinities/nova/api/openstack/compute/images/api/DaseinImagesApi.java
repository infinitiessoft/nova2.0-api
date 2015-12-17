package com.infinities.nova.api.openstack.compute.images.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.compute.ImageFilterOptions;
import org.dasein.cloud.compute.MachineImage;

import com.google.common.base.Preconditions;
import com.infinities.nova.api.Context;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.Common.PaginationParams;
import com.infinities.nova.api.openstack.compute.images.ImagesFilter;
import com.infinities.nova.response.model.Image;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedMachineImageSupport;
import com.infinities.skyport.service.ConfigurationHome;

public class DaseinImagesApi implements ImagesApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinImagesApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	@Override
	public Image get(NovaRequestContext context, String imageId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<MachineImage> result = getSupport(context.getProjectId()).getImage(imageId);
		MachineImage machineImage = result.get();
		Image image = toImage(machineImage);
		return image;
	}

	private Image toImage(MachineImage machineImage) {
		Image output = new Image();
		Calendar created = Calendar.getInstance();
		created.setTimeInMillis(machineImage.getCreationTimestamp());
		output.setCreated(created);
		output.setId(machineImage.getProviderMachineImageId());
		output.setMetadata(machineImage.getProviderMetadata());
		output.setMinDisk((int) machineImage.getMinimumDiskSizeGb());
		// output.setMinRam();
		output.setName(machineImage.getName());
		// output.setProgress();
		// Image.Server server = new Image.Server();
		// server.setId(input.getServer());
		// output.setServer(server);
		// output.setSize(input.getSize());
		output.setStatus(machineImage.getCurrentState().name());
		// output.setUpdated();
		output.setMetadata(machineImage.getTags());
		return output;
	}

	@Override
	public List<Image> getAll(NovaRequestContext context, ImagesFilter filters, PaginationParams pageParams)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<MachineImage>> result =
				getSupport(context.getProjectId()).listImages(ImageFilterOptions.getInstance());
		Iterable<MachineImage> iterable = result.get();
		Iterator<MachineImage> iterator = iterable.iterator();

		List<Image> images = new ArrayList<Image>();
		while (iterator.hasNext()) {
			MachineImage machineImage = iterator.next();
			images.add(toImage(machineImage));
		}

		return images;
	}

	@Override
	public void delete(NovaRequestContext context, String imageId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).remove(imageId);
	}

	private CachedMachineImageSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasImageSupport()) {
				return provider.getComputeServices().getImageSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

}
