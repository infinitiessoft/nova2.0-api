package com.infinities.nova.api.openstack.compute.availablity_zones.api;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.infinities.nova.api.NovaRequestContext;

public interface AvailabilityZoneApi {

	// getOnlyAvailable=false, withHosts=false
	public Entry<Map<String, Set<String>>, Map<String, Set<String>>> getAvailabilityZones(NovaRequestContext context, boolean getOnlyAvailable, boolean withHosts);

}
