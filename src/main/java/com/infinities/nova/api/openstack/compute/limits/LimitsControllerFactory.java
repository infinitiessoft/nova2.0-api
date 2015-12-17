package com.infinities.nova.api.openstack.compute.limits;

import org.glassfish.hk2.api.Factory;

public class LimitsControllerFactory implements Factory<LimitsController> {

	@Override
	public LimitsController provide() {
		return new LimitsControllerImpl();
	}

	@Override
	public void dispose(LimitsController instance) {
	}

}
