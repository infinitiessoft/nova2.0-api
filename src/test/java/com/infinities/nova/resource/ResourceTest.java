/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
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
