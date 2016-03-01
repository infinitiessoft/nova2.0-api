package com.infinities.neutron.subnets.controller;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.subnets.api.SubnetsApi;
import com.infinities.neutron.subnets.model.Subnet;
import com.infinities.neutron.subnets.model.SubnetForCreateTemplate;
import com.infinities.neutron.subnets.model.SubnetTemplate;
import com.infinities.neutron.subnets.model.Subnets;
import com.infinities.neutron.subnets.views.ViewBuilder;
import com.infinities.nova.NovaRequestContext;

public class SubnetsControllerImpl implements SubnetsController{

	private final SubnetsApi subnetsApi;
	private final ViewBuilder builder = new ViewBuilder();
	
	public SubnetsControllerImpl(SubnetsApi subnetsApi) {
		this.subnetsApi = subnetsApi;
	}
	
	@Override
	public Subnets index(ContainerRequestContext requestContext, String projectId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<Subnet> subnets = subnetsApi.getSubnets(context, projectId);
		return builder.index(requestContext, subnets);
	}

	@Override
	public SubnetTemplate show(ContainerRequestContext requestContext, String projectId, String subnetId)
			throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Subnet subnet = subnetsApi.getSubnet(context, projectId, subnetId);
		return builder.show(requestContext, subnet);
	}

	@Override
	public SubnetTemplate create(ContainerRequestContext requestContext, String projectId, SubnetForCreateTemplate subnetForCreateTemplate)
			throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Subnet subnet = subnetsApi.createSubnet(context, projectId, subnetForCreateTemplate);
		return builder.show(requestContext, subnet);
	}

	@Override
	public SubnetTemplate update(ContainerRequestContext requestContext, String projectId, String subnetId,
			SubnetForCreateTemplate subnetForCreateTemplate) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Subnet subnet = subnetsApi.updateSubnet(context, projectId, subnetId, subnetForCreateTemplate);
		return builder.show(requestContext, subnet);
	}

	@Override
	public void delete(String projectId, String subnetId, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		subnetsApi.deleteSubnet(context, projectId, subnetId);
	}

}
