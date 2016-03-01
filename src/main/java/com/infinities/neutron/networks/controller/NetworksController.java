package com.infinities.neutron.networks.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.networks.model.NetworkForCreate;
import com.infinities.neutron.networks.model.NetworkForUpdate;
import com.infinities.neutron.networks.model.NetworkTemplate;
import com.infinities.neutron.networks.model.Networks;

public interface NetworksController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	Networks index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param networkId
	 * @return
	 * @throws Exception
	 */
	NetworkTemplate show(ContainerRequestContext requestContext, String projectId, String networkId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param networkForCreate
	 * @return
	 * @throws Exception
	 */
	NetworkTemplate create(ContainerRequestContext requestContext, String projectId,
			NetworkForCreate networkForCreate) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param networkId
	 * @param networkForUpdate
	 * @return
	 * @throws Exception
	 */
	NetworkTemplate update(ContainerRequestContext requestContext, String projectId, String networkId, NetworkForUpdate network) throws Exception;
	
	/**
	 * @param projectId
	 * @param networkId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String networkId, ContainerRequestContext requestContext) throws Exception;

}
