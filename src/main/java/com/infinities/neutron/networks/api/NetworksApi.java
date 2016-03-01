package com.infinities.neutron.networks.api;

import java.util.List;

import com.infinities.neutron.networks.model.Network;
import com.infinities.neutron.networks.model.NetworkForCreate;
import com.infinities.neutron.networks.model.NetworkForUpdate;
import com.infinities.nova.NovaRequestContext;

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
	 * @param networkForCreate
	 * @return
	 * @throws Exception
	 */
	Network createNetwork(NovaRequestContext context, String projectId, NetworkForCreate networkForCreate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param networkId
	 * @param networkForUpdate
	 * @return
	 * @throws Exception
	 */
	Network updateNetwork(NovaRequestContext context, String projectId, String networkId, NetworkForUpdate networkForUpdate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param networkId
	 * @throws Exception
	 */
	void deleteNetwork(NovaRequestContext context, String projectId, String networkId) throws Exception;
}
