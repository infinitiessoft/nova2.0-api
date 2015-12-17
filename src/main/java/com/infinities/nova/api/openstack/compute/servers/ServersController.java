package com.infinities.nova.api.openstack.compute.servers;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.infinities.nova.response.model.ServerAction;
import com.infinities.nova.response.model.ServerAction.ChangePassword;
import com.infinities.nova.response.model.ServerAction.ConfirmResize;
import com.infinities.nova.response.model.ServerAction.CreateImage;
import com.infinities.nova.response.model.ServerAction.Pause;
import com.infinities.nova.response.model.ServerAction.Rebuild;
import com.infinities.nova.response.model.ServerAction.Resume;
import com.infinities.nova.response.model.ServerAction.RevertResize;
import com.infinities.nova.response.model.ServerAction.Start;
import com.infinities.nova.response.model.ServerAction.Stop;
import com.infinities.nova.response.model.ServerAction.Suspend;
import com.infinities.nova.response.model.ServerAction.Unpause;
import com.infinities.nova.response.model.ServerForCreate;

public interface ServersController {

	ServersTemplate detail(ContainerRequestContext requestContext) throws Exception;

	ServerTemplate show(String serverId, ContainerRequestContext requestContext) throws Exception;

	Response create(ContainerRequestContext requestContext, ServerForCreate server) throws Exception;

	void delete(String serverId, ContainerRequestContext requestContext) throws Exception;

	ServerTemplate update(ContainerRequestContext requestContext, String serverId, ServerForCreate server) throws Exception;

	MinimalServersTemplate index(ContainerRequestContext requestContext) throws Exception;

	Response reboot(String serverId, ContainerRequestContext requestContext, ServerAction.Reboot body) throws Exception;

	Response resize(String serverId, ContainerRequestContext requestContext, ServerAction.Resize body) throws Exception;

	ServerTemplate rebuild(String serverId, ContainerRequestContext requestContext, Rebuild body) throws Exception;

	Response revertResize(String serverId, ContainerRequestContext requestContext, RevertResize body) throws Exception;

	Response confirmResize(String serverId, ContainerRequestContext requestContext, ConfirmResize body) throws Exception;

	Response changePassword(String serverId, ContainerRequestContext requestContext, ChangePassword body) throws Exception;

	Response createImage(String serverId, ContainerRequestContext requestContext, CreateImage body) throws Exception;

	Response pause(String serverId, ContainerRequestContext requestContext, Pause value) throws Exception;

	Response unpause(String serverId, ContainerRequestContext requestContext, Unpause value) throws Exception;

	Response suspend(String serverId, ContainerRequestContext requestContext, Suspend value) throws Exception;

	Response resume(String serverId, ContainerRequestContext requestContext, Resume value) throws Exception;

	Response start(String serverId, ContainerRequestContext requestContext, Start value) throws Exception;

	Response stop(String serverId, ContainerRequestContext requestContext, Stop value) throws Exception;

}
