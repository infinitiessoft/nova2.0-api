package com.infinities.nova.api.openstack.compute.service.api;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.Service;

public interface ServiceApi {

	// diabled=none, setZones=false
	public List<Service> getAll(NovaRequestContext context, Boolean disabled, boolean setZones);
}
