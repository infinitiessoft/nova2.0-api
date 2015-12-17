package com.infinities.nova.model.home.impl.factory;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.model.home.QuotaHome;
import com.infinities.nova.model.home.impl.QuotaHomeImpl;

public class QuotaHomeFactory implements Factory<QuotaHome> {

	@Override
	public QuotaHome provide() {
		return new QuotaHomeImpl();
	}

	@Override
	public void dispose(QuotaHome instance) {
	}

}
