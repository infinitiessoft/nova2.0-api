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

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.response.model.KeyPair;

/**
 * @author pohsun
 *
 */
public interface KeyPairsApi {

	KeyPair createKeyPair(NovaRequestContext context, String userId, String keyName) throws Exception;

	KeyPair importKeyPair(NovaRequestContext context, String userId, String keyName, String publicKey) throws Exception;

	KeyPair getKeyPair(NovaRequestContext context, String userId, String keyName) throws Exception;

	List<KeyPair> getKeyPairs(NovaRequestContext context, String userId) throws Exception;

	void deleteKeyPair(NovaRequestContext context, String userId, String keyName) throws Exception;

}
