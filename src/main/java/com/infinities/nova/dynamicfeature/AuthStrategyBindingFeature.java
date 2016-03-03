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
package com.infinities.nova.dynamicfeature;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.infinities.nova.common.config.Config;
import com.infinities.nova.middleware.AuthTokenMiddleware;
import com.infinities.nova.middleware.KeystonecontextMiddleware;
import com.infinities.nova.middleware.NoAuthMiddleware;

/**
 * @author pohsun
 *
 */
@Provider
public class AuthStrategyBindingFeature implements DynamicFeature {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.ws.rs.container.DynamicFeature#configure(javax.ws.rs.container.
	 * ResourceInfo, javax.ws.rs.core.FeatureContext)
	 */
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		String strategy = Config.Instance.getOpt("auth_strategy").asText();
		if ("noauth".equals(strategy)) {
			context.register(NoAuthMiddleware.class);
		} else if ("keystone".equals(strategy)) {
			context.register(AuthTokenMiddleware.class);
			context.register(KeystonecontextMiddleware.class);
		}
	}

}
