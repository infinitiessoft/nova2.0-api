package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsFilter;
import com.infinities.nova.db.model.InstanceType;

public interface InstanceTypeHome {

	// inactive=false,filter=null,sortKey=flavorid,sortDir=asc,limit=null,marker=null;
	List<InstanceType> flavorGetAll(NovaRequestContext context, boolean inactive, FlavorsFilter filter, String sortKey,
			String sortDir, Integer limit, String marker) throws Exception;

	InstanceType flavorGetByFlavorId(NovaRequestContext context, String flavorid, String readDeleted);

	InstanceType flavorGetByName(NovaRequestContext context, String name);

}
