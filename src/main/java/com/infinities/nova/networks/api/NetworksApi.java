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
package com.infinities.nova.networks.api;

import java.util.List;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.networks.model.Network;
import com.infinities.nova.networks.model.NetworkForCreateTemplate;

/**
 * @author pohsun
 *
 */
public interface NetworksApi {

	/**
	 * @param context
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	List<Network> getNetworks(NovaRequestContext context, String projectId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param networkId
	 * @return
	 * @throws Exception
	 */
	Network getNetwork(NovaRequestContext context, String projectId, String networkId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param networkForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Network createNetwork(NovaRequestContext context, String projectId, NetworkForCreateTemplate networkForCreateTemplate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param networkId
	 * @throws Exception
	 */
	void deleteNetwork(NovaRequestContext context, String projectId, String networkId) throws Exception;

}
