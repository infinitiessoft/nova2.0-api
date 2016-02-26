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
package com.infinities.nova.servers.interfaces.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.network.NetworkInterface;
import org.dasein.cloud.network.RawAddress;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.exception.InterfaceAttachmentNotFoundException;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachment;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachment.FixedIp;
import com.infinities.nova.servers.interfaces.model.InterfaceAttachmentForCreateTemplate;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncVLANSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.network.NICAttachOptions;
import com.infinities.skyport.network.NICDetachOptions;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinInterfaceAttachmentsApi implements InterfaceAttachmentsApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinInterfaceAttachmentsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.interfaces.api.InterfaceAttachmentsApi#
	 * getInterfaceAttachments(com.infinities.nova.NovaRequestContext,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public List<InterfaceAttachment> getInterfaceAttachments(NovaRequestContext context, String projectId, String serverId)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<NetworkInterface>> result =
				this.getSupport(context.getProjectId()).listNetworkInterfacesForVM(serverId);
		Iterable<NetworkInterface> iterable = result.get();
		List<InterfaceAttachment> interfaceAttachments = new ArrayList<InterfaceAttachment>();
		Iterator<NetworkInterface> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			NetworkInterface interfaceAttachment = iterator.next();
			interfaceAttachments.add(toInterfaceAttachment(interfaceAttachment));
		}
		return interfaceAttachments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infinities.nova.servers.interfaces.api.InterfaceAttachmentsApi#
	 * getInterfaceAttachment(com.infinities.nova.NovaRequestContext,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public InterfaceAttachment getInterfaceAttachment(NovaRequestContext context, String projectId, String serverId,
			String interfaceAttachmentId) throws Exception {
		List<InterfaceAttachment> interfaceAttachments = getInterfaceAttachments(context, projectId, serverId);
		for (InterfaceAttachment attachment : interfaceAttachments) {
			if (interfaceAttachmentId.equals(attachment.getPortId())) {
				return attachment;
			}
		}
		throw new InterfaceAttachmentNotFoundException(null, interfaceAttachmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.interfaces.api.InterfaceAttachmentsApi#attach
	 * (com.infinities.nova.NovaRequestContext, java.lang.String,
	 * java.lang.String, com.infinities.nova.servers.interfaces.model.
	 * InterfaceAttachmentForCreateTemplate)
	 */
	@Override
	public InterfaceAttachment attach(NovaRequestContext context, String projectId, String serverId,
			InterfaceAttachmentForCreateTemplate interfaceAttachmentForCreateTemplate) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		String portId = interfaceAttachmentForCreateTemplate.getInterfaceAttachment().getPortId();
		String netId = interfaceAttachmentForCreateTemplate.getInterfaceAttachment().getNetId();
		NICAttachOptions options =
				Strings.isNullOrEmpty(portId) ? NICAttachOptions.getInstanceForVlan(netId, serverId) : NICAttachOptions
						.getInstanceForPort(portId, serverId);
		AsyncResult<String> result = this.getSupport(context.getProjectId()).attachNetworkInterface(options);
		String interfaceAttachmentId = result.get();
		return getInterfaceAttachment(context, projectId, serverId, interfaceAttachmentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.servers.interfaces.api.InterfaceAttachmentsApi#detach
	 * (com.infinities.nova.NovaRequestContext, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void detach(NovaRequestContext context, String projectId, String serverId, String interfaceAttachmentId)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		NICDetachOptions options = NICDetachOptions.getInstanceForPort(interfaceAttachmentId, serverId);
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).detachNetworkInterface(options);
		result.get();
	}

	private AsyncVLANSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasNetworkServices()) {
			if (provider.getNetworkServices().hasVlanSupport()) {
				return provider.getNetworkServices().getVlanSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);
	}

	/**
	 * @param interfaceAttachment
	 * @return
	 */
	private InterfaceAttachment toInterfaceAttachment(NetworkInterface networkInterface) {
		InterfaceAttachment attachment = new InterfaceAttachment();
		attachment.setPortId(networkInterface.getProviderNetworkInterfaceId());
		attachment.setMacAddr(networkInterface.getMacAddress());
		attachment.setNetId(networkInterface.getProviderVlanId());
		attachment.setPortState(networkInterface.getCurrentState().name());
		List<FixedIp> fixedIps = new ArrayList<FixedIp>();
		attachment.setFixedIps(fixedIps);
		if (networkInterface.getIpAddresses() != null) {
			for (RawAddress address : networkInterface.getIpAddresses()) {
				FixedIp fixedIp = new FixedIp();
				fixedIp.setIpAddress(address.getIpAddress());
				fixedIp.setSubnetId(networkInterface.getProviderSubnetId());
				fixedIps.add(fixedIp);
			}
		}

		return attachment;
	}

}
