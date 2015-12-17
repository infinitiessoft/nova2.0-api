package com.infinities.nova.api.openstack.compute.flavors;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.exception.http.HTTPBadRequestException;
import com.infinities.nova.api.exception.http.HTTPNotFoundException;
import com.infinities.nova.api.openstack.Common;
import com.infinities.nova.api.openstack.compute.flavors.api.FlavorsApi;
import com.infinities.nova.db.model.InstanceType;
import com.infinities.nova.views.flavors.ViewBuilder;

public class FlavorsControllerImpl implements FlavorsController {

	private final static Logger logger = LoggerFactory.getLogger(FlavorsControllerImpl.class);
	private final ViewBuilder builder = new ViewBuilder();

	private final FlavorsApi flavorsApi;


	public FlavorsControllerImpl(FlavorsApi flavorsApi) {
		this.flavorsApi = flavorsApi;
	}

	@Override
	public MinimalFlavorsTemplate index(ContainerRequestContext requestContext) throws Exception {
		List<InstanceType> limitedFlavors = getFlavors(requestContext);
		return builder.index(requestContext, limitedFlavors);
	}

	@Override
	public FlavorsTemplate detail(ContainerRequestContext requestContext) throws Exception {
		List<InstanceType> limitedFlavors = getFlavors(requestContext);
		// requestContext.cache_db_flavors(limitedFlavors);
		return builder.detail(requestContext, limitedFlavors);
	}

	@Override
	public FlavorTemplate show(String flavorid, ContainerRequestContext requestContext) throws Exception {
		try {
			NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
			InstanceType flavor = flavorsApi.getFlavorByFlavorId(flavorid, context, "yes");
			// requestContext.cache_db_flavor(flavor);
			return builder.show(requestContext, flavor);
		} catch (Exception e) {
			throw new HTTPNotFoundException();
		}
	}

	private List<InstanceType> getFlavors(ContainerRequestContext requestContext) {
		FlavorsFilter filter = new FlavorsFilter();
		UriInfo uriInfo = requestContext.getUriInfo();
		String sortKey = uriInfo.getQueryParameters().getFirst("sort_key");
		if (Strings.isNullOrEmpty(sortKey)) {
			sortKey = "flavorid";
		}
		String sortDir = uriInfo.getQueryParameters().getFirst("sort_dir");
		if (Strings.isNullOrEmpty(sortDir)) {
			sortDir = "asc";
		}
		// String limitStr = uriInfo.getQueryParameters().getFirst("limit");
		// Integer limit = null;
		// if (!Strings.isNullOrEmpty(limitStr)) {
		// limit = Integer.parseInt(limitStr);
		// }
		// String marker = uriInfo.getQueryParameters().getFirst("marker");

		Entry<Integer, String> entry = Common.getLimitAndMarker(requestContext);
		Integer limit = entry.getKey();
		String marker = entry.getValue();

		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		if (context.getIsAdmin()) {
			filter.setIsPublic(parseIsPublic(uriInfo.getQueryParameters().getFirst("is_public")));
		} else {
			filter.setIsPublic(true);
			filter.setDisabled(false);
		}

		String minRam = uriInfo.getQueryParameters().getFirst("minRam");
		if (!Strings.isNullOrEmpty(minRam)) {
			try {
				filter.setMinMemoryMb(Integer.parseInt(minRam));
			} catch (Exception e) {
				String msg = String.format("Invalid minRam filter [%s]", minRam);
				throw new HTTPBadRequestException(msg);
			}
		}

		String minDisk = uriInfo.getQueryParameters().getFirst("minDisk");
		if (!Strings.isNullOrEmpty(minDisk)) {
			try {
				filter.setMinRootGb(Integer.parseInt(minDisk));
			} catch (Exception e) {
				String msg = String.format("Invalid minDisk filter [%s]", minDisk);
				throw new HTTPBadRequestException(msg);
			}
		}

		try {
			List<InstanceType> limitedFlavors = flavorsApi.getAllFlavorsSortedList(context, filter, sortKey, sortDir, limit,
					marker);
			return limitedFlavors;
		} catch (Exception e) {
			String msg = String.format("marker [%s] not found", marker);
			logger.error(msg, e);
			throw new HTTPBadRequestException(msg);
		}
	}

	private Boolean parseIsPublic(String isPublic) {
		if (Strings.isNullOrEmpty(isPublic)) {
			return true;
		} else {
			try {
				return Boolean.parseBoolean(isPublic);
			} catch (Exception e) {
				String msg = String.format("Invalid is_public filter [%s]", isPublic);
				throw new HTTPBadRequestException(msg);
			}
		}
	}
}
