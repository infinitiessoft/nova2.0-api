package com.infinities.nova.api.openstack.compute.service.api;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.availablity_zones.api.AvailabilityZoneApiImpl;
import com.infinities.nova.db.model.Service;
import com.infinities.nova.model.home.impl.ServiceHomeImpl;

public class ServiceApiImpl implements ServiceApi {

	@Override
	public List<Service> getAll(NovaRequestContext context, Boolean disabled, boolean setZones) {
		List<Service> dbServices = new ServiceHomeImpl().serviceGetAll(context, disabled);

		if (setZones) {
			dbServices = AvailabilityZoneApiImpl.setAvailabilityZones(context, dbServices);
		}
		return dbServices;
	}

}
