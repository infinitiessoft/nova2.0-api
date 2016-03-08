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

import java.util.List;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.keypairs.model.KeyPair;

/**
 * @author pohsun
 *
 */
public interface KeyPairsApi {

	KeyPair createKeyPair(OpenstackRequestContext context, String userId, String keyName) throws Exception;

	KeyPair importKeyPair(OpenstackRequestContext context, String userId, String keyName, String publicKey) throws Exception;

	KeyPair getKeyPair(OpenstackRequestContext context, String userId, String keyName) throws Exception;

	List<KeyPair> getKeyPairs(OpenstackRequestContext context, String userId) throws Exception;

	void deleteKeyPair(OpenstackRequestContext context, String userId, String keyName) throws Exception;

}
