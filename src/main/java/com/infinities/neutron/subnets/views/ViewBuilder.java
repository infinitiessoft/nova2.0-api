package com.infinities.neutron.subnets.views;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.subnets.model.Subnet;
import com.infinities.neutron.subnets.model.SubnetTemplate;
import com.infinities.neutron.subnets.model.Subnets;

public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param subnets
	 * @return
	 */
	public Subnets index(ContainerRequestContext requestContext, List<Subnet> subnets) {
		Subnets ret = new Subnets();
		ret.setList(subnets);
		return ret;
	}
	
	/**
	 * @param requestContext
	 * @param subnet
	 * @return
	 */
	public SubnetTemplate show(ContainerRequestContext requestContext, Subnet subnet) {
		SubnetTemplate template = new SubnetTemplate();
		template.setSubnet(subnet);
		return template;
	}
}
