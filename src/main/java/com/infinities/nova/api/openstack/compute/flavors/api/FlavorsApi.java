package com.infinities.nova.api.openstack.compute.flavors.api;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsFilter;
import com.infinities.nova.db.model.InstanceType;

public interface FlavorsApi {

	List<InstanceType> getAllFlavorsSortedList(NovaRequestContext context, FlavorsFilter filter, String sortKey,
			String sortDir, Integer limit, String marker) throws Exception;

	// context=null,readDeleted = yes
	InstanceType getFlavorByFlavorId(String flavorid, NovaRequestContext context, String readDeleted) throws Exception;

	// InstanceType getDefaultFlavor() throws Exception;
}
