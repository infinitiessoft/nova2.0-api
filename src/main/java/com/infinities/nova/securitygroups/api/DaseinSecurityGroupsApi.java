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
package com.infinities.nova.securitygroups.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.network.Firewall;
import org.dasein.cloud.network.FirewallCreateOptions;
import org.dasein.cloud.network.FirewallRule;

import com.google.common.base.Preconditions;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.api.openstack.commons.exception.http.HTTPNotImplementedException;
import com.infinities.nova.securitygroups.model.SecurityForCreateTemplate;
import com.infinities.nova.securitygroups.model.SecurityGroup;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule.Group;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule.IpRange;
import com.infinities.nova.securitygroups.model.SecurityGroupTemplate;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncFirewallSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedVirtualMachineSupport;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinSecurityGroupsApi implements SecurityGroupsApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinSecurityGroupsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.api.SecurityGroupsApi#createSecurityGroup
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * com.infinities.nova.securitygroups.model.SecurityGroupTemplate)
	 */
	@Override
	public SecurityGroup createSecurityGroup(OpenstackRequestContext context, String projectId,
			SecurityForCreateTemplate body) throws InterruptedException, ExecutionException, Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		String name = body.getSecurityGroup().getName();
		String description = body.getSecurityGroup().getDescription();
		FirewallCreateOptions options = FirewallCreateOptions.getInstance(name, description);

		AsyncResult<String> ret = this.getSupport(context.getProjectId()).create(options);
		return getSecurityGroup(context, projectId, ret.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.api.SecurityGroupsApi#getSecurityGroups
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String)
	 */
	@Override
	public List<SecurityGroup> getSecurityGroups(OpenstackRequestContext context, String projectId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<Firewall>> result = this.getSupport(context.getProjectId()).list();
		Iterable<Firewall> iterable = result.get();
		List<SecurityGroup> securityGroups = new ArrayList<SecurityGroup>();
		Iterator<Firewall> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			Firewall firewall = iterator.next();
			securityGroups.add(toSecurityGroup(context, firewall, projectId));
		}
		return securityGroups;
	}

	/**
	 * @param securityGroup
	 * @return
	 * @throws InternalException
	 * @throws CloudException
	 * @throws ConcurrentException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private SecurityGroup toSecurityGroup(OpenstackRequestContext context, Firewall firewall, String projectId)
			throws CloudException, InternalException, ConcurrentException, InterruptedException, ExecutionException {
		SecurityGroup securityGroup = new SecurityGroup();
		securityGroup.setDescription(firewall.getDescription());
		securityGroup.setId(firewall.getProviderFirewallId());
		securityGroup.setName(firewall.getName());
		securityGroup.setTenantId(getTenantId(projectId));
		List<Rule> rules = new ArrayList<Rule>();
		Collection<FirewallRule> firewallRules = firewall.getRules();
		Iterable<FirewallRule> iterable = firewallRules;
		if (firewallRules == null || firewallRules.isEmpty()) {
			iterable = this.getSupport(context.getProjectId()).getRules(firewall.getProviderFirewallId()).get();
		}
		Iterator<FirewallRule> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			FirewallRule firewallRule = iterator.next();
			rules.add(toRule(firewallRule, firewall, projectId));
		}
		securityGroup.setRules(rules);
		return securityGroup;
	}

	/**
	 * @param firewallRule
	 * @return
	 * @throws InternalException
	 * @throws CloudException
	 */
	private Rule toRule(FirewallRule firewallRule, Firewall firewall, String projectId) throws CloudException,
			InternalException {
		Rule rule = new Rule();
		rule.setFromPort(firewallRule.getStartPort());
		Group group = new Group();
		group.setName(firewall.getName());
		group.setTenantId(getTenantId(projectId));
		rule.setGroup(group);
		rule.setId(firewallRule.getProviderRuleId());
		rule.setIpProtocol(firewallRule.getProtocol().name());
		IpRange ipRange = new IpRange();
		ipRange.setCidr(firewallRule.getSourceEndpoint().getCidr());
		rule.setIpRange(ipRange);
		rule.setParentGroupId(firewallRule.getFirewallId());
		rule.setToPort(firewallRule.getEndPort());
		return rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.api.SecurityGroupsApi#getSecurityGroup
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SecurityGroup getSecurityGroup(OpenstackRequestContext context, String projectId, String securityGroupId)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Firewall> result = this.getSupport(context.getProjectId()).getFirewall(securityGroupId);
		Firewall firewall = result.get();
		return toSecurityGroup(context, firewall, projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.api.SecurityGroupsApi#deleteSecurityGroup
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteSecurityGroup(OpenstackRequestContext context, String projectId, String securityGroupId)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).delete(securityGroupId);
		result.get();
	}

	private AsyncFirewallSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasNetworkServices()) {
			if (provider.getNetworkServices().hasFirewallSupport()) {
				return provider.getNetworkServices().getFirewallSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	private String getTenantId(String id) throws CloudException, InternalException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		return provider.getContext().getAccountNumber();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.api.SecurityGroupsApi#updateSecurityGroup
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String,
	 * com.infinities.nova.securitygroups.model.SecurityGroupTemplate)
	 */
	@Override
	public SecurityGroup updateSecurityGroup(OpenstackRequestContext context, String projectId, String securityGroupId,
			SecurityGroupTemplate body) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		throw new HTTPNotImplementedException("service not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.api.SecurityGroupsApi#getSecurityGroups
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<SecurityGroup> getSecurityGroups(OpenstackRequestContext context, String projectId, String serverId)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<String>> result = this.getVirtualMahcineSupport(context.getProjectId()).listFirewalls(serverId);
		Iterable<String> iterable = result.get();
		List<SecurityGroup> securityGroups = new ArrayList<SecurityGroup>();
		Iterator<String> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			String firewallId = iterator.next();
			securityGroups.add(getSecurityGroup(context, projectId, firewallId));
		}
		return securityGroups;
	}

	private CachedVirtualMachineSupport getVirtualMahcineSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasVirtualMachineSupport()) {
				return provider.getComputeServices().getVirtualMachineSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

}
