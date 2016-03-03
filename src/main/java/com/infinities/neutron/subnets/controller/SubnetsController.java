package com.infinities.neutron.subnets.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.subnets.model.SubnetForCreateTemplate;
import com.infinities.neutron.subnets.model.SubnetTemplate;
import com.infinities.neutron.subnets.model.Subnets;

public interface SubnetsController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	Subnets index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param subnetId
	 * @return
	 * @throws Exception
	 */
	SubnetTemplate show(ContainerRequestContext requestContext, String projectId, String subnetId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param subnetForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	SubnetTemplate create(ContainerRequestContext requestContext, String projectId, SubnetForCreateTemplate subnet) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param subnetId
	 * @param subnetForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	SubnetTemplate update(ContainerRequestContext requestContext, String projectId, String subnetId, SubnetForCreateTemplate subnet) throws Exception;
	
	/**
	 * @param projectId
	 * @param subnetId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String subnetId, ContainerRequestContext requestContext) throws Exception;

}
