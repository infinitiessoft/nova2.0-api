package com.infinities.neutron.ports.controller;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.neutron.ports.api.PortsApi;
import com.infinities.neutron.ports.model.Port;
import com.infinities.neutron.ports.model.PortForCreateTemplate;
import com.infinities.neutron.ports.model.PortTemplate;
import com.infinities.neutron.ports.model.Ports;
import com.infinities.neutron.ports.views.ViewBuilder;
import com.infinities.nova.NovaRequestContext;

public class PortsControllerImpl implements PortsController{

	private final PortsApi portsApi;
	private final ViewBuilder builder = new ViewBuilder();
	
	public PortsControllerImpl(PortsApi portsApi) {
		this.portsApi = portsApi;
	}
	
	@Override
	public Ports index(ContainerRequestContext requestContext, String projectId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<Port> ports = portsApi.getPorts(context, projectId);
		return builder.index(requestContext, ports);
	}

	@Override
	public PortTemplate show(ContainerRequestContext requestContext, String projectId, String portId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Port port = portsApi.getPort(context, projectId, portId);
		return builder.show(requestContext, port);
	}

	@Override
	public PortTemplate create(ContainerRequestContext requestContext, String projectId, PortForCreateTemplate portForCreateTemplate)
			throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Port port = portsApi.createPort(context, projectId, portForCreateTemplate);
		return builder.show(requestContext, port);
	}

	@Override
	public PortTemplate update(ContainerRequestContext requestContext, String projectId, String portId,
			PortForCreateTemplate portForCreateTemplate) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Port port = portsApi.updatePort(context, projectId, portId, portForCreateTemplate);
		return builder.show(requestContext, port);
	}

	@Override
	public void delete(String projectId, String portId, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		portsApi.deletePort(context, projectId, portId);
	}

}
