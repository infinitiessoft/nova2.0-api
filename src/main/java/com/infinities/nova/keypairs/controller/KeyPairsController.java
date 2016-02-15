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

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.keypairs.model.KeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairTemplate;
import com.infinities.nova.keypairs.model.MinimalKeyPairsTemplate;

/**
 * @author pohsun
 *
 */
public interface KeyPairsController {

	MinimalKeyPairsTemplate index(ContainerRequestContext requestContext) throws Exception;

	MinimalKeyPairTemplate create(ContainerRequestContext requestContext, KeyPairTemplate body) throws Exception;

	void delete(String keyPairName, ContainerRequestContext requestContext) throws Exception;

	KeyPairTemplate show(ContainerRequestContext requestContext, String id) throws Exception;
}
