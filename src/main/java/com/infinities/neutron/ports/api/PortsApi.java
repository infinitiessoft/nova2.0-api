package com.infinities.neutron.ports.api;

import java.util.List;

import com.infinities.neutron.ports.model.Port;
import com.infinities.neutron.ports.model.PortForCreateTemplate;
import com.infinities.nova.NovaRequestContext;

public interface PortsApi {

	/**
	 * @param context
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	List<Port> getPorts(NovaRequestContext context, String projectId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param portId
	 * @return
	 * @throws Exception
	 */
	Port getPort(NovaRequestContext context, String projectId, String portId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param portForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Port createPort(NovaRequestContext context, String projectId, PortForCreateTemplate portForCreateTemplate)
			throws Exception;
	
	/**
	 * @param context
	 * @param projectId
	 * @param portId
	 * @param portForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Port updatePort(NovaRequestContext context, String projectId, String portId, PortForCreateTemplate portForCreateTemplate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param portId
	 * @throws Exception
	 */
	void deletePort(NovaRequestContext context, String projectId, String portId) throws Exception;
}
