package com.infinities.nova.api.v2.factory;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.api.v2.Version2Api;

public class Version2ApiFactory implements Factory<Version2Api> {

	@Override
	public void dispose(Version2Api arg0) {

	}

	@Override
	public Version2Api provide() {
		return new Version2Api();
	}

}
