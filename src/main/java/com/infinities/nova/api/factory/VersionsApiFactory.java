package com.infinities.nova.api.factory;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.VersionsApi;

public class VersionsApiFactory implements Factory<VersionsApi> {

	@Override
	public void dispose(VersionsApi arg0) {

	}

	@Override
	public VersionsApi provide() {
		return new VersionsApi();
	}

}
