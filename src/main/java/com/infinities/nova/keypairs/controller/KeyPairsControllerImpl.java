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
package com.infinities.nova.keypairs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.exception.KeyPairNotFoundException;
import com.infinities.nova.exception.http.HTTPBadRequestException;
import com.infinities.nova.exception.http.HTTPNotFoundException;
import com.infinities.nova.extensions.Extensions;
import com.infinities.nova.keypairs.api.KeyPairsApi;
import com.infinities.nova.keypairs.model.KeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPair;
import com.infinities.nova.keypairs.model.MinimalKeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairsTemplate;
import com.infinities.nova.policy.Target;
import com.infinities.nova.response.model.KeyPair;

/**
 * @author pohsun
 *
 */
public class KeyPairsControllerImpl implements KeyPairsController {

	// private final static Logger logger =
	// LoggerFactory.getLogger(KeyPairsControllerImpl.class);
	private KeyPairsApi keyPairsApi;


	public KeyPairsControllerImpl(KeyPairsApi keyPairsApi) {
		this.keyPairsApi = keyPairsApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.api.openstack.compute.keypairs.KeyPairsController
	 * #index(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public MinimalKeyPairsTemplate index(ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		authorize(context, null, "index");
		List<KeyPair> keyPairs = keyPairsApi.getKeyPairs(context, context.getUserId());
		List<MinimalKeyPair> rval = new ArrayList<MinimalKeyPair>();
		for (KeyPair keyPair : keyPairs) {
			rval.add(toMinimalKeyPair(keyPair));
		}

		return new MinimalKeyPairsTemplate(rval);
	}

	/**
	 * @param keyPair
	 * @return
	 */
	private MinimalKeyPair toMinimalKeyPair(KeyPair keyPair) {
		MinimalKeyPair ret = new MinimalKeyPair();
		ret.setFingerprint(keyPair.getFingerprint());
		ret.setName(keyPair.getName());
		ret.setPublicKey(keyPair.getPublicKey());
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.api.openstack.compute.keypairs.KeyPairsController
	 * #create(javax.ws.rs.container.ContainerRequestContext,
	 * com.infinities.nova.response.model.KeyPair)
	 */
	@Override
	public MinimalKeyPairTemplate create(ContainerRequestContext requestContext, KeyPairTemplate body) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		authorize(context, null, "create");

		KeyPair params = body.getKeypair();
		if (params == null || Strings.isNullOrEmpty(params.getName())) {
			String msg = "Invalid request body";
			throw new HTTPBadRequestException(msg);
		}

		MinimalKeyPair minimalKeyPair = null;
		if (!Strings.isNullOrEmpty(params.getPublicKey())) {
			KeyPair keyPair =
					keyPairsApi.importKeyPair(context, context.getUserId(), params.getName(), params.getPublicKey());
			minimalKeyPair = toMinimalKeyPair(keyPair);
		} else {
			KeyPair keyPair = keyPairsApi.createKeyPair(context, context.getUserId(), params.getName());
			minimalKeyPair = toMinimalKeyPair(keyPair);
			minimalKeyPair.setPrivateKey(keyPair.getPrivateKey());
		}
		return new MinimalKeyPairTemplate(minimalKeyPair);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.api.openstack.compute.keypairs.KeyPairsController
	 * #delete(java.lang.String, javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void delete(String keyPairName, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		authorize(context, null, "delete");
		try {
			keyPairsApi.deleteKeyPair(context, context.getUserId(), keyPairName);
		} catch (KeyPairNotFoundException e) {
			throw new HTTPNotFoundException();
		}
	}

	private boolean authorize(NovaRequestContext context, Target target, String action) throws Exception {
		return Extensions.extensionAuthorizer("compute", "keypairs").authorize(context, target, action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.api.openstack.compute.keypairs.KeyPairsController
	 * #show(javax.ws.rs.container.ContainerRequestContext, java.lang.String)
	 */
	@Override
	public KeyPairTemplate show(ContainerRequestContext requestContext, String id) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		authorize(context, null, "show");
		try {
			KeyPair keyPair = keyPairsApi.getKeyPair(context, context.getUserId(), id);
			return new KeyPairTemplate(keyPair);
		} catch (KeyPairNotFoundException e) {
			throw new HTTPNotFoundException();
		}
	}

	// private boolean softAuthorize(NovaRequestContext context, Target target,
	// String action) throws Exception {
	// return Extensions.softExtensionAuthorizer("compute",
	// "keypairs").authorize(context, target, action);
	// }

}
