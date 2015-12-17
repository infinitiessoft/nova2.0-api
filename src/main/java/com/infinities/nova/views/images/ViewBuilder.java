package com.infinities.nova.views.images;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.base.Strings;
import com.infinities.nova.api.openstack.compute.images.ImageTemplate;
import com.infinities.nova.api.openstack.compute.images.ImagesTemplate;
import com.infinities.nova.api.openstack.compute.images.MinimalImage;
import com.infinities.nova.api.openstack.compute.images.MinimalImageTemplate;
import com.infinities.nova.api.openstack.compute.images.MinimalImagesTemplate;
import com.infinities.nova.common.Config;
import com.infinities.nova.response.model.Image;
import com.infinities.nova.response.model.Link;
import com.infinities.nova.views.AbstractViewBuilder;

public class ViewBuilder extends AbstractViewBuilder {

	private final static String COLLECTION_NAME = "images";


	// private final static Map<String, String> STATUS_MAP;
	// private final static Map<String, Integer> PROGRESS_MAP;

	// static {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("active", "ACTIVE");
	// map.put("queued", "SAVING");
	// map.put("saving", "SAVING");
	// map.put("deleted", "DELETED");
	// map.put("pending_delete", "DELETED");
	// map.put("killed", "ERROR");
	// STATUS_MAP = Collections.unmodifiableMap(map);

	// Map<String, Integer> progressMap = new HashMap<String, Integer>();
	// progressMap.put("queued", 25);
	// progressMap.put("saving", 50);
	// progressMap.put("active", 100);
	// PROGRESS_MAP = Collections.unmodifiableMap(progressMap);
	// }

	public MinimalImagesTemplate index(ContainerRequestContext requestContext, List<Image> images) throws URISyntaxException {
		List<MinimalImage> imageList = new ArrayList<MinimalImage>();
		for (Image image : images) {
			imageList.add(basic(requestContext, image).getImage());
		}

		List<Link> imagesLinks = getCollectionLinks(requestContext, images, COLLECTION_NAME);

		MinimalImagesTemplate ret = new MinimalImagesTemplate();
		ret.setList(imageList);
		ret.setLinks(imagesLinks);
		return ret;
	}

	public ImagesTemplate detail(ContainerRequestContext requestContext, List<Image> images) throws URISyntaxException {
		List<com.infinities.nova.response.model.Image> imageList = new ArrayList<com.infinities.nova.response.model.Image>();
		for (Image image : images) {
			imageList.add(show(requestContext, image).getImage());
		}

		List<Link> imagesLinks = getCollectionLinks(requestContext, images, COLLECTION_NAME);
		ImagesTemplate imagesTemplate = new ImagesTemplate();
		imagesTemplate.setList(imageList);
		imagesTemplate.setLinks(imagesLinks);

		return imagesTemplate;
	}

	private List<Link> getCollectionLinks(ContainerRequestContext requestContext, List<Image> images, String collectionName)
			throws URISyntaxException {
		List<Link> links = new ArrayList<Link>();
		String limitQP = requestContext.getUriInfo().getQueryParameters().getFirst("limit");
		int limitQ = Config.Instance.getOpt("osapi_max_limit").asInteger();
		int maxItems = limitQ;

		if (!Strings.isNullOrEmpty(limitQP)) {
			int limit = Integer.parseInt(limitQP);
			maxItems = Math.min(maxItems, limit);
		}

		if (maxItems == images.size()) {
			int item = images.size() - 1;
			if (item >= 0) {
				Image lastItem = images.get(item);
				String lastItemId = lastItem.getId();
				Link link = new Link();
				link.setRel("next");
				link.setHref(getNextLink(requestContext, lastItemId, collectionName));
				links.add(link);
			}
		}

		if (links.isEmpty()) {
			return null;
		}

		return links;
	}

	private MinimalImageTemplate basic(ContainerRequestContext requestContext, Image image) throws URISyntaxException {
		MinimalImage m = new MinimalImage();
		m.setId(image.getId());
		m.setName(image.getName());
		m.setLinks(getLinks(requestContext, image.getId(), COLLECTION_NAME));
		return new MinimalImageTemplate(m);
	}

	@Override
	protected List<Link> getLinks(ContainerRequestContext request, String identifier, String collectionName)
			throws URISyntaxException {
		List<Link> links = new ArrayList<Link>();
		Link link = new Link();
		link.setRel("self");
		link.setHref(getHrefLink(request, identifier, collectionName));
		links.add(link);

		Link link2 = new Link();
		link2.setRel("bookmark");
		link2.setHref(getBookmarkLink(request, identifier, collectionName));
		links.add(link2);

		// Link link3 = new Link();
		// link3.setRel("alternate");
		// link3.setType("application/vnd.openstack.image");
		// link3.setHref(getAlternateLink(request, identifier));
		// links.add(link3);
		return links;
	}

	public ImageTemplate show(ContainerRequestContext requestContext, Image image) throws URISyntaxException {
		com.infinities.nova.response.model.Image m = new com.infinities.nova.response.model.Image();
		m.setId(image.getId());
		m.setName(image.getName());
		Integer minRam = image.getMinRam() == null ? 0 : image.getMinRam();
		m.setMinRam(minRam);
		Integer minDisk = image.getMinDisk() == null ? 0 : image.getMinDisk();
		m.setMinDisk(minDisk);
		Map<String, String> metadata = new HashMap<String, String>();
		if (image.getMetadata() != null) {
			for (String key : image.getMetadata().keySet()) {
				metadata.put(key, image.getMetadata().get(key));
			}
		}
		m.setMetadata(metadata);
		m.setCreated(image.getCreated());
		m.setUpdated(image.getUpdated());
		m.setStatus(image.getStatus());
		m.setProgress(image.getProgress());
		m.setLinks(getLinks(requestContext, image.getId(), COLLECTION_NAME));

		String instanceUuid = m.getMetadata().get("instance_uuid");
		if (!Strings.isNullOrEmpty(instanceUuid)) {
			String serverRef = getHrefLink(requestContext, instanceUuid, "servers");
			com.infinities.nova.response.model.Image.Server server = new com.infinities.nova.response.model.Image.Server();
			server.setId(instanceUuid);
			Link link = new Link();
			link.setHref(serverRef);
			link.setRel("self");
			server.getLinks().add(link);
			Link bookMark = new Link();
			bookMark.setRel("bookmark");
			bookMark.setHref(getBookmarkLink(requestContext, instanceUuid, "servers"));
		}

		return new ImageTemplate(m);
	}

	// private Integer getProgress(Image image) {
	// if (PROGRESS_MAP.containsKey(image.getStatus())) {
	// return PROGRESS_MAP.get(image.getStatus());
	// } else {
	// return 0;
	// }
	// }

	// private String getStatus(Image image) {
	// if (STATUS_MAP.containsKey(image.getStatus())) {
	// return STATUS_MAP.get(image.getStatus());
	// } else {
	// return "UNKNOWN";
	// }
	// }
}
