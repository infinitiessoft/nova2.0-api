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
package com.infinities.nova.securitygroups.rules.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.network.Direction;
import org.dasein.cloud.network.Firewall;
import org.dasein.cloud.network.FirewallRule;
import org.dasein.cloud.network.FirewallRuleCreateOptions;
import org.dasein.cloud.network.Permission;
import org.dasein.cloud.network.Protocol;
import org.dasein.cloud.network.RuleTarget;

import com.google.common.base.Preconditions;
import com.infinities.api.openstack.commons.context.Context;
import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule.Group;
import com.infinities.nova.securitygroups.model.SecurityGroup.Rule.IpRange;
import com.infinities.nova.securitygroups.rules.model.SecurityGroupRuleForCreate;
import com.infinities.nova.securitygroups.rules.model.SecurityGroupRuleForCreateTemplate;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.async.service.network.AsyncFirewallSupport;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinSecurityGroupRulesApi implements SecurityGroupRulesApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinSecurityGroupRulesApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.rules.api.SecurityGroupRulesApi#createRule
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * com.infinities.
	 * nova.securitygroups.rules.model.SecurityGroupRuleForCreateTemplate)
	 */
	@Override
	public Rule createRule(OpenstackRequestContext context, String projectId, SecurityGroupRuleForCreateTemplate body)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		String cidr = body.getSecurityGroupRule().getCidr();

		RuleTarget sourceEndpoint = RuleTarget.getCIDR(cidr);
		RuleTarget endEndpoint = RuleTarget.getCIDR(cidr);
		Protocol protocol = Protocol.valueOf(body.getSecurityGroupRule().getIpProtocol().toUpperCase());
		FirewallRuleCreateOptions ruleOptions =
				FirewallRuleCreateOptions.getInstance(Direction.INGRESS, Permission.ALLOW, sourceEndpoint, protocol,
						endEndpoint, body.getSecurityGroupRule().getFromPort(), body.getSecurityGroupRule().getToPort());

		AsyncResult<String> ret =
				this.getSupport(context.getProjectId()).authorize(body.getSecurityGroupRule().getParentGroupId(),
						ruleOptions);
		Rule rule = toRule(body.getSecurityGroupRule());
		return getRule(context, projectId, body.getSecurityGroupRule().getParentGroupId(), ret.get(), rule);
	}

	/**
	 * @param context
	 * @param projectId
	 * @param string
	 * @return
	 * @throws ConcurrentException
	 * @throws CloudException
	 * @throws InternalException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private Rule getRule(OpenstackRequestContext context, String projectId, String securityGroupId, String ruleId, Rule rule)
			throws InternalException, CloudException, ConcurrentException, InterruptedException, ExecutionException {
		AsyncResult<Firewall> result = this.getSupport(context.getProjectId()).getFirewall(securityGroupId);
		Firewall firewall = result.get();
		Collection<FirewallRule> firewallRules = firewall.getRules();
		Iterable<FirewallRule> iterable = firewallRules;
		if (firewallRules == null || firewallRules.isEmpty()) {
			iterable = this.getSupport(context.getProjectId()).getRules(firewall.getProviderFirewallId()).get();
		}
		Iterator<FirewallRule> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			FirewallRule firewallRule = iterator.next();
			if (ruleId.equals(firewallRule.getProviderRuleId())) {
				return toRule(firewallRule, firewall, projectId);
			}
		}
		return rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.securitygroups.rules.api.SecurityGroupRulesApi#deleteRule
	 * (com.infinities.nova.OpenstackRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteRule(OpenstackRequestContext context, String projectId, String securityGroupRuleId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).revoke(securityGroupRuleId);
		result.get();
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

	/**
	 * @param securityGroupRule
	 * @return
	 */
	private Rule toRule(SecurityGroupRuleForCreate securityGroupRule) {
		Rule rule = new Rule();
		rule.setFromPort(securityGroupRule.getFromPort());
		Group group = new Group();
		rule.setGroup(group);
		rule.setIpProtocol(securityGroupRule.getIpProtocol());
		IpRange ipRange = new IpRange();
		ipRange.setCidr(securityGroupRule.getCidr());
		rule.setIpRange(ipRange);
		rule.setParentGroupId(securityGroupRule.getParentGroupId());
		rule.setToPort(securityGroupRule.getToPort());
		return rule;
	}

	private String getTenantId(String id) throws CloudException, InternalException {
		CachedServiceProvider provider = configurationHome.findById(id);
		Preconditions.checkArgument(provider != null, "invalid project id:" + id);
		return provider.getContext().getAccountNumber();
	}

}
