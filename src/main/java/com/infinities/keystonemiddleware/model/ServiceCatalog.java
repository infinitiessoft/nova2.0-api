package com.infinities.keystonemiddleware.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.infinities.keystonemiddleware.model.v3.ServiceCatalogV3;

public abstract class ServiceCatalog {

	private String regionName;


	protected ServiceCatalog(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public abstract TokenMetadata getToken();

	// public abstract boolean isEndpointTypeMatch(Endpoint endpoint, String
	// endpointType);

	public abstract String normalizeEndpointType(String endpointType);
	
	public abstract List<URL> getURLs(String serviceType, String endpointType, String regionName, String serviceName) throws MalformedURLException;

	public static ServiceCatalog build(AccessInfo accessInfo, String token, String regionName) {
		if (ServiceCatalogV3.isValid(accessInfo)) {
			return new ServiceCatalogV3(token, accessInfo, regionName);
		} else if (ServiceCatalogV2.isValid(accessInfo)) {
			return new ServiceCatalogV2(accessInfo, regionName);
		} else {
			throw new RuntimeException("Unrecognized auth response");
		}
	}
}
