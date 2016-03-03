package com.infinities.neutron.ports.views;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.ports.model.Port;
import com.infinities.neutron.ports.model.PortTemplate;
import com.infinities.neutron.ports.model.Ports;

public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param ports
	 * @return
	 */
	public Ports index(ContainerRequestContext requestContext, List<Port> ports) {
		Ports ret = new Ports();
		ret.setList(ports);
		return ret;
	}
	
	/**
	 * @param requestContext
	 * @param network
	 * @return
	 */
	public PortTemplate show(ContainerRequestContext requestContext, Port port) {
		PortTemplate template = new PortTemplate();
		template.setPort(port);
		return template;
	}
}
