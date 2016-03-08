/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.nova.resource;

import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.infinities.api.openstack.commons.dynamicfeature.OpenstackContext;
import com.infinities.api.openstack.commons.exception.http.HTTPBadRequestException;
import com.infinities.api.openstack.commons.exception.http.HTTPNotImplementedException;
import com.infinities.api.openstack.commons.namebinding.CheckProjectId;
import com.infinities.nova.servers.controller.ServersController;
import com.infinities.nova.servers.model.MinimalServersTemplate;
import com.infinities.nova.servers.model.ServerForCreateTemplate;
import com.infinities.nova.servers.model.ServerTemplate;
import com.infinities.nova.servers.model.ServersTemplate;
import com.infinities.nova.servers.model.ServerAction.ChangePassword;
import com.infinities.nova.servers.model.ServerAction.ConfirmResize;
import com.infinities.nova.servers.model.ServerAction.CreateImage;
import com.infinities.nova.servers.model.ServerAction.Pause;
import com.infinities.nova.servers.model.ServerAction.Reboot;
import com.infinities.nova.servers.model.ServerAction.Rebuild;
import com.infinities.nova.servers.model.ServerAction.Resize;
import com.infinities.nova.servers.model.ServerAction.Resume;
import com.infinities.nova.servers.model.ServerAction.RevertResize;
import com.infinities.nova.servers.model.ServerAction.Start;
import com.infinities.nova.servers.model.ServerAction.Stop;
import com.infinities.nova.servers.model.ServerAction.Suspend;
import com.infinities.nova.servers.model.ServerAction.Unpause;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CheckProjectId
@OpenstackContext
public class ServersResource {

	private final ServersController controller;


	@Autowired
	public ServersResource(ServersController controller) {
		this.controller = controller;
	}

	@GET
	public MinimalServersTemplate index(@PathParam("projectId") String projectId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.index(requestContext);
	}

	@GET
	@Path("detail")
	public ServersTemplate datail(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext)
			throws Exception {

		return controller.detail(requestContext);
	}

	@GET
	@Path("{serverId}")
	public ServerTemplate show(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@Context ContainerRequestContext requestContext) throws Exception {

		return controller.show(serverId, requestContext);
	}

	@POST
	public Response create(@PathParam("projectId") String projectId, @Context ContainerRequestContext requestContext,
			ServerForCreateTemplate serverTemplate) throws Exception {

		return controller.create(requestContext, serverTemplate.getServer());
	}

	@PUT
	@Path("{serverId}")
	public ServerTemplate update(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@Context ContainerRequestContext requestContext, ServerForCreateTemplate serverTemplate) throws Exception {

		return controller.update(requestContext, serverId, serverTemplate.getServer());
	}

	@DELETE
	@Path("{serverId}")
	public Response delete(@PathParam("projectId") String projectId, @PathParam("serverId") String serverId,
			@Context ContainerRequestContext requestContext) throws Exception {

		controller.delete(serverId, requestContext);
		return Response.status(Status.NO_CONTENT).build();
	}

	@Path("{serverId}/metadata")
	public Class<ServerMetadataResource> getServerMetadataResource() {
		return ServerMetadataResource.class;
	}

	@Path("{serverId}/os-security-groups")
	public Class<ServerSecurityGroupsResource> getServerSecurityGroupsResource() {
		return ServerSecurityGroupsResource.class;
	}

	@Path("{serverId}/ips")
	public Class<ServerIpsResource> getServerIpsResource() {
		return ServerIpsResource.class;
	}

	@Path("{serverId}/os-volume_attachments")
	public Class<VolumeAttachmentsResource> getVolumeAttachmentsResource() {
		return VolumeAttachmentsResource.class;
	}

	@Path("{serverId}/os-interface")
	public Class<InterfaceAttachmentsResource> getInterfaceAttachmentsResource() {
		return InterfaceAttachmentsResource.class;
	}

	@POST
	@Path("{serverId}/action")
	public Response action(@PathParam("serverId") String serverId, @Context ContainerRequestContext requestContext,
			JsonNode node) throws JsonProcessingException, Exception {
		if (node != null) {
			if (node.has("changePassword")) {
				return controller.changePassword(serverId, requestContext,
						toValue(ChangePassword.class, node.get("changePassword")));
			} else if (node.has("reboot")) {
				return controller.reboot(serverId, requestContext, toValue(Reboot.class, node.get("reboot")));
			} else if (node.has("rebuild")) {
				ServerTemplate template =
						controller.rebuild(serverId, requestContext, toValue(Rebuild.class, node.get("rebuild")));
				return Response.accepted(template).build();
			} else if (node.has("resize")) {
				return controller.resize(serverId, requestContext, toValue(Resize.class, node.get("resize")));
			} else if (node.has("confirmResize")) {
				return controller.confirmResize(serverId, requestContext,
						toValue(ConfirmResize.class, node.get("confirmResize")));
			} else if (node.has("revertResize")) {
				return controller.revertResize(serverId, requestContext,
						toValue(RevertResize.class, node.get("revertResize")));
			} else if (node.has("createImage")) {
				return controller.createImage(serverId, requestContext, toValue(CreateImage.class, node.get("createImage")));
			} else if (node.has("pause")) {
				return controller.pause(serverId, requestContext, toValue(Pause.class, node.get("pause")));
			} else if (node.has("unpause")) {
				return controller.unpause(serverId, requestContext, toValue(Unpause.class, node.get("unpause")));
			} else if (node.has("suspend")) {
				return controller.suspend(serverId, requestContext, toValue(Suspend.class, node.get("suspend")));
			} else if (node.has("resume")) {
				return controller.resume(serverId, requestContext, toValue(Resume.class, node.get("resume")));
			} else if (node.has("os-start")) {
				return controller.start(serverId, requestContext, toValue(Start.class, node.get("os-start")));
			} else if (node.has("os-stop")) {
				return controller.stop(serverId, requestContext, toValue(Stop.class, node.get("os-stop")));
			} else if (node.has("migrate")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("resetNetwork")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("injectNetworkInfo")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("lock")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("unlock")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("createBackup")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("os-migrateLive")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("os-resetState")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.has("evacuate")) {
				throw new HTTPNotImplementedException("action not support");
			} else if (node.hasNonNull(0)) {
				String msg = "There is no such action:%s";
				msg = String.format(msg, node.get(0).asText());
				throw new HTTPBadRequestException(msg);
			}
		}
		throw new HTTPBadRequestException("Malformed request body");

	}

	private <R> R toValue(Class<R> c, JsonNode node) throws JsonProcessingException {
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		// if using BOTH JAXB annotations AND Jackson annotations:
		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		ObjectMapper mapper =
				new ObjectMapper().registerModule(new Hibernate4Module())
						.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
						.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"))
						.enable(SerializationFeature.INDENT_OUTPUT)
						.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
						.setAnnotationIntrospector(new AnnotationIntrospectorPair(introspector, secondary));
		R object = mapper.treeToValue(node, c);
		return object;
	}
}
