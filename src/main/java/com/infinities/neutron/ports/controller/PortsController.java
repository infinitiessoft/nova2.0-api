package com.infinities.neutron.ports.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.ports.model.PortForCreateTemplate;
import com.infinities.neutron.ports.model.PortTemplate;
import com.infinities.neutron.ports.model.Ports;

public interface PortsController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	Ports index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param portId
	 * @return
	 * @throws Exception
	 */
	PortTemplate show(ContainerRequestContext requestContext, String projectId, String portId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param portForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	PortTemplate create(ContainerRequestContext requestContext, String projectId,
			PortForCreateTemplate portForCreateTemplate) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param portId
	 * @param portForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	PortTemplate update(ContainerRequestContext requestContext, String projectId, String portId, PortForCreateTemplate portForCreateTemplate) throws Exception;
	
	/**
	 * @param projectId
	 * @param portId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String portId, ContainerRequestContext requestContext) throws Exception;

}
