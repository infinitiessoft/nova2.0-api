package com.infinities.keystonemiddleware.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

public class ServiceCatalogV2 extends ServiceCatalog {

	private AccessInfoV2 catalog;


	public ServiceCatalogV2(AccessInfo accessInfo, String regionName) {
		super(regionName);
		this.catalog = (AccessInfoV2) accessInfo;
	}

	public static boolean isValid(AccessInfo accessInfo) {
		return (accessInfo instanceof AccessInfoV2);
	}

	@Override
	public TokenMetadata getToken() {
		TokenMetadata token = new TokenMetadata();
		token.setId(catalog.getAccess().getToken().getId());
		token.setExpires(catalog.getAccess().getToken().getExpires());

		try {
			token.setUserid(catalog.getAccess().getUser().getId());
			token.setTenantid(catalog.getAccess().getToken().getTenant().getId());
		} catch (Exception e) {

		}
		return token;
	}

	public boolean isEndpointTypeMatch(Access.Service.Endpoint endpoint, String endpointType) {
		if ("adminURL".equals(endpointType) && Strings.isNullOrEmpty(endpoint.getAdminURL())) {
			return true;
		}

		if ("publicURL".equals(endpointType) && Strings.isNullOrEmpty(endpoint.getPublicURL())) {
			return true;
		}

		if ("internalURL".equals(endpointType) && Strings.isNullOrEmpty(endpoint.getInternalURL())) {
			return true;
		}

		return false;
	}

	@Override
	public String normalizeEndpointType(String endpointType) {
		if (!Strings.isNullOrEmpty(endpointType) && !endpointType.contains("URL")) {
			endpointType = endpointType + "URL";
		}
		return endpointType;
	}

	public List<Access.Service> getData() {
		return catalog.getAccess().getServiceCatalog();
	}

	@Override
	public List<URL> getURLs(String serviceType, String endpointType, String regionName, String serviceName)
			throws MalformedURLException {
		if (Strings.isNullOrEmpty(serviceType)) {
			serviceType = "identity";
		}

		if (Strings.isNullOrEmpty(endpointType)) {
			endpointType = "publicURL";
		}

		endpointType = normalizeEndpointType(endpointType);
		List<Access.Service.Endpoint> endpoints = getServiceEndpoints(serviceType, endpointType, regionName, serviceName);

		if (endpoints != null) {
			List<URL> urls = new ArrayList<URL>();
			for (Access.Service.Endpoint endpoint : endpoints) {
				if ("publicURL".equals(endpointType)) {
					urls.add(new URL(endpoint.getPublicURL()));
				}
				if ("internalURL".equals(endpointType)) {
					urls.add(new URL(endpoint.getInternalURL()));
				}
				if ("adminURL".equals(endpointType)) {
					urls.add(new URL(endpoint.getAdminURL()));
				}
			}
			return urls;
		}

		return null;
	}

	public Map<String, List<Access.Service.Endpoint>> getEndpoints(String serviceType, String endpointType,
			String regionName, String serviceName) {
		endpointType = normalizeEndpointType(endpointType);

		if (Strings.isNullOrEmpty(regionName)) {
			regionName = this.getRegionName();
		}

		Map<String, List<Access.Service.Endpoint>> sc = new HashMap<String, List<Access.Service.Endpoint>>();

		for (Access.Service service : this.getData()) {
			String st;
			st = service.getType();
			try {
				if (!Strings.isNullOrEmpty(serviceType) && !serviceType.equals(st)) {
					continue;
				}
			} catch (Exception e) {
				continue;
			}

			String sn;
			if (!Strings.isNullOrEmpty(serviceName)) {
				sn = service.getName();
				if (!serviceName.equals(sn)) {
					continue;
				}
			}

			sc.put(st, new ArrayList<Access.Service.Endpoint>());
			for (Access.Service.Endpoint endpoint : service.getEndpoints()) {
				if (!Strings.isNullOrEmpty(endpointType) && !this.isEndpointTypeMatch(endpoint, endpointType)) {
					continue;
				}

				if (!Strings.isNullOrEmpty(regionName) && !regionName.equals(endpoint.getRegion())) {
					continue;
				}

				sc.get(st).add(endpoint);
			}

		}

		return sc;
	}

	public List<Access.Service.Endpoint> getServiceEndpoints(String serviceType, String endpointType, String regionName,
			String serviceName) {
		Map<String, List<Access.Service.Endpoint>> scEndpoints = this.getEndpoints(serviceType, endpointType, regionName,
				serviceName);

		List<Access.Service.Endpoint> endpoints = scEndpoints.get(serviceType);
		if (endpoints == null) {
			return null;
		}

		return endpoints;
	}

}
