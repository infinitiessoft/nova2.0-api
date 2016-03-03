package com.infinities.neutron.subnets.api;

import java.util.List;

import com.infinities.neutron.subnets.model.Subnet;
import com.infinities.neutron.subnets.model.SubnetForCreateTemplate;
import com.infinities.nova.NovaRequestContext;

public interface SubnetsApi {

	/**
	 * @param context
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	List<Subnet> getSubnets(NovaRequestContext context, String projectId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param subnetId
	 * @return
	 * @throws Exception
	 */
	Subnet getSubnet(NovaRequestContext context, String projectId, String subnetId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param subnetForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Subnet createSubnet(NovaRequestContext context, String projectId, SubnetForCreateTemplate subnetForCreateTemplate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param subnetId
	 * @param subnetForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Subnet updateSubnet(NovaRequestContext context, String projectId, String subnetId, SubnetForCreateTemplate subnetForCreateTemplate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param subnetId
	 * @throws Exception
	 */
	void deleteSubnet(NovaRequestContext context, String projectId, String subnetId) throws Exception;
}
