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
package com.infinities.nova.keypairs.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.identity.SSHKeypair;

import com.google.common.base.Preconditions;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.keypairs.model.KeyPair;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.identity.AsyncShellKeySupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinKeyPairsApi implements KeyPairsApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinKeyPairsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.api.openstack.compute.keypairs.api.KeyPairsApi#
	 * createKeyPair(java.lang.String, java.lang.String)
	 */
	@Override
	public KeyPair createKeyPair(OpenstackRequestContext context, String userId, String keyName) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<SSHKeypair> result = getSupport(context.getProjectId()).createKeypair(keyName);
		SSHKeypair sshKeyPair = result.get();
		KeyPair keyPair = toKeyPair(sshKeyPair);
		return keyPair;
	}

	/**
	 * @param sshKeyPair
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private KeyPair toKeyPair(SSHKeypair sshKeyPair) throws UnsupportedEncodingException {
		KeyPair keyPair = new KeyPair();
		String fingerprint = sshKeyPair.getFingerprint();
		byte[] privateKey = sshKeyPair.getPrivateKey();
		String publicKey = sshKeyPair.getPublicKey();
		String name = sshKeyPair.getName();
		String userId = sshKeyPair.getProviderOwnerId();
		keyPair.setName(name);
		keyPair.setPublicKey(publicKey);
		keyPair.setFingerprint(fingerprint);
		if (privateKey != null) {
			keyPair.setPrivateKey(new String(privateKey, "UTF-8"));
		}
		keyPair.setUserId(userId);
		return keyPair;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.api.openstack.compute.keypairs.api.KeyPairsApi#getKeyPair
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public KeyPair getKeyPair(OpenstackRequestContext context, String userId, String keyName) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<SSHKeypair> result = getSupport(context.getProjectId()).getKeypair(keyName);
		SSHKeypair sshKeyPair = result.get();
		KeyPair keyPair = toKeyPair(sshKeyPair);
		return keyPair;
	}

	private AsyncShellKeySupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasIdentityServices()) {
			if (provider.getIdentityServices().hasShellKeySupport()) {
				return provider.getIdentityServices().getShellKeySupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.api.openstack.compute.keypairs.api.KeyPairsApi#
	 * importKeyPair(com.infinities.nova.api.OpenstackRequestContext,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public KeyPair importKeyPair(OpenstackRequestContext context, String userId, String keyName, String publicKey)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<SSHKeypair> result = getSupport(context.getProjectId()).importKeypair(keyName, publicKey);
		SSHKeypair sshKeyPair = result.get();
		KeyPair keyPair = toKeyPair(sshKeyPair);
		return keyPair;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.api.openstack.compute.keypairs.api.KeyPairsApi#
	 * getKeyPairs(com.infinities.nova.api.OpenstackRequestContext,
	 * java.lang.String)
	 */
	@Override
	public List<KeyPair> getKeyPairs(OpenstackRequestContext context, String userId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Iterable<SSHKeypair>> result = getSupport(context.getProjectId()).list();
		Iterable<SSHKeypair> iterable = result.get();
		Iterator<SSHKeypair> iterator = iterable.iterator();

		List<KeyPair> keyPairs = new ArrayList<KeyPair>();
		while (iterator.hasNext()) {
			SSHKeypair sshKeyPair = iterator.next();
			keyPairs.add(toKeyPair(sshKeyPair));
		}

		return keyPairs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.api.openstack.compute.keypairs.api.KeyPairsApi#
	 * deleteKeyPair(com.infinities.nova.api.OpenstackRequestContext,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteKeyPair(OpenstackRequestContext context, String userId, String keyName) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		getSupport(context.getProjectId()).deleteKeypair(keyName).get();
	}

}
