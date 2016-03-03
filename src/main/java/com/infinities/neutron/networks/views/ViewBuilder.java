package com.infinities.neutron.networks.views;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.networks.model.Network;
import com.infinities.neutron.networks.model.NetworkTemplate;
import com.infinities.neutron.networks.model.Networks;


public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param networks
	 * @return
	 */
	public Networks index(ContainerRequestContext requestContext, List<Network> networks) {
		Networks ret = new Networks();
		ret.setList(networks);
		return ret;
	}
	
	/**
	 * @param requestContext
	 * @param network
	 * @return
	 */
	public NetworkTemplate show(ContainerRequestContext requestContext, Network network) {
		NetworkTemplate template = new NetworkTemplate();
		template.setNetwork(network);
		return template;
	}
	
}
