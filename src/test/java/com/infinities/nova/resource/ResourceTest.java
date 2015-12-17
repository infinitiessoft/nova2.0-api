package com.infinities.nova.resource;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import com.infinities.nova.api.AbstractDbAndJerseyTest;
import com.infinities.skyport.jpa.JpaProperties;

public abstract class ResourceTest extends AbstractDbAndJerseyTest {

	protected final static String TOKEN = "admin:admin";


	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new GrizzlyWebContainerFactory();
	}

	@Override
	protected Application configure() {
		JpaProperties.PERSISTENCE_UNIT_NAME = "com.infinities.skyport.nova.jpa.test";
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return getApplication();
	}

	protected abstract Application getApplication();

}
