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
package com.infinities.nova.quota;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jersey.repackaged.com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.common.Config;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.model.home.KeyPairHome;
import com.infinities.nova.model.home.SecurityGroupIngressRuleHome;
import com.infinities.nova.model.home.impl.KeyPairHomeImpl;
import com.infinities.nova.model.home.impl.SecurityGroupIngressRuleHomeImpl;
import com.infinities.nova.response.model.QuotaSet;

public class QuotaEngine {

	private final static Logger logger = LoggerFactory.getLogger(QuotaEngine.class);
	private String driverCls;
	private QuotaDriver driver;
	private final ConcurrentHashMap<String, BaseResource> resources;


	private QuotaEngine() {
		this(null);
	}

	private QuotaEngine(String quotaDriverClass) {
		this.resources = new ConcurrentHashMap<String, BaseResource>();
		this.driverCls = quotaDriverClass;
		this.driver = null;
	}

	public QuotaDriver getDriver() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (driver != null) {
			return driver;
		}

		if (Strings.isNullOrEmpty(driverCls)) {
			driverCls = Config.Instance.getOpt("quota_driver").asText();
		}

		Class<?> c = Class.forName(driverCls);
		driver = (QuotaDriver) c.newInstance();
		return driver;
	}

	public boolean contains(BaseResource resource) {
		return resources.containsValue(resource);
	}

	public boolean contains(String resource) {
		return resources.containsKey(resource);
	}

	public BaseResource get(String key) {
		return resources.get(key);
	}

	public void registerResource(BaseResource resource) {
		this.resources.put(resource.getName(), resource);
	}

	public void registerResources(List<BaseResource> resources) {
		for (BaseResource resource : resources) {
			this.registerResource(resource);
		}
	}

	public ProjectUserQuota getByProjectAndUser(NovaRequestContext context, String projectid, String userid, String resource)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return getDriver().getByProjectAndUser(context, projectid, userid, resource);
	}

	public Quota getByProject(NovaRequestContext context, String projectid, String resource) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		return getDriver().getByProject(context, projectid, resource);
	}

	public QuotaClass getByClass(NovaRequestContext context, String quotaClass, String resource)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return getDriver().getByClass(context, quotaClass, resource);
	}

	public QuotaSet getDefaults(NovaRequestContext context) throws Exception {
		return getDriver().getDefaults(context, resources);
	}

	// defaults = true
	public QuotaSet getClassQuotas(NovaRequestContext context, String quotaClass, boolean defaults) throws Exception {
		return getDriver().getClassQuotas(context, resources, quotaClass, defaults);
	}

	// defaults = true, usages = true
	public QuotaUsageSet getUserQuotas(NovaRequestContext context, String projectid, String userid, String quotaClass,
			boolean defaults, boolean usages) throws Exception {
		return getDriver().getUserQuotas(context, resources, projectid, userid, quotaClass, defaults, usages, null, null);
	}

	// quotaClass = null, defaults = true, usages = true, remains = false
	public QuotaUsageSet getProjectQuotas(NovaRequestContext context, String projectid, String quotaClass, boolean defaults,
			boolean usages, boolean remains) throws Exception {
		return getDriver().getProjectQuotas(context, resources, projectid, quotaClass, defaults, usages, remains, null);
	}

	// userid = null;
	public QuotaUsageSet getSettableQuotas(NovaRequestContext context, String projectid, String userid) throws Exception {
		return getDriver().getSettableQuotas(context, resources, projectid, userid);
	}

	public long count(NovaRequestContext context, String resource, String arg) {
		BaseResource res = resources.get(resource);
		if (res == null || !(res instanceof CountableResource)) {
			String msg = String.format("Unknown quota resources %s.", resource);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(msg).build());
		}

		return ((CountableResource) res).getCount().apply(Maps.immutableEntry(context, arg));
	}

	public void limitCheck(NovaRequestContext context, String projectid, String userid, Map<String, Integer> values)
			throws Exception {
		logger.debug("resource: {}", resources);
		getDriver().limitCheck(context, resources, values, projectid, userid);
	}

	public List<String> reserve(NovaRequestContext context, Calendar expire, String projectid, String userid,
			Map<String, Integer> deltas) throws Exception {
		return getDriver().reserve(context, resources, deltas, expire, projectid, userid);
	}

	public void commit(NovaRequestContext context, List<String> reservations, String projectid, String userid) {
		try {
			getDriver().commit(context, reservations, projectid, userid);
		} catch (Exception e) {
			logger.error("Failed to commit reservations %s", reservations);
			return;
		}
		logger.debug("Committed reservations %s", reservations);
	}

	public void rollback(NovaRequestContext context, List<String> reservations, String projectid, String userid) {
		try {
			getDriver().rollback(context, reservations, projectid, userid);
		} catch (Exception e) {
			logger.error("Failed to roll back reservations %s", reservations);
			return;
		}
		logger.debug("Rolled back reservations %s", reservations);
	}

	public void usageReset(NovaRequestContext context, List<String> resources) throws Exception {
		getDriver().usageReset(context, resources);
	}

	public void detroyAllByProjectAndUser(NovaRequestContext context, String projectid, String userid)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		getDriver().destroyAllByProjectAndUser(context, projectid, userid);
	}

	public void detroyAllByProject(NovaRequestContext context, String projectid) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		getDriver().destroyAllByProject(context, projectid);
	}

	public void expire(NovaRequestContext context) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		getDriver().expire(context);
	}

	public Set<String> getResources() {
		return resources.keySet();
	}


	private static final class KeypairGetCountByUser implements Function<Entry<NovaRequestContext, String>, Long> {

		private final KeyPairHome home;


		private KeypairGetCountByUser() {
			home = new KeyPairHomeImpl();
		}

		@Override
		public Long apply(Entry<NovaRequestContext, String> entry) {
			return home.getKeyPairCountByUser(entry.getKey(), entry.getValue());
		}
	}

	private static final class SecurityGroupRuleCountByCount implements Function<Entry<NovaRequestContext, String>, Long> {

		private final SecurityGroupIngressRuleHome home;


		private SecurityGroupRuleCountByCount() {
			home = new SecurityGroupIngressRuleHomeImpl();
		}

		@Override
		public Long apply(Entry<NovaRequestContext, String> entry) {
			return home.getSecurityGroupRuleCountByGroup(entry.getKey(), entry.getValue());
		}
	}


	// private static final class ServerGroupCountMembersByUser implements
	// Function<Entry<NovaRequestContext, String>, Long> {
	//
	// private final SecurityGroupIngressRuleHome home;
	//
	//
	// private SecurityGroupRuleCountByCount() {
	// home = new SecurityGroupIngressRuleHomeImpl();
	// }
	//
	// @Override
	// public Long apply(Entry<NovaRequestContext, String> entry) {
	// return home.getSecurityGroupRuleCountByGroup(entry.getKey(),
	// entry.getValue());
	// }
	// }

	private static QuotaEngine quotas;


	public static QuotaEngine getQUOTAS() {
		if (quotas == null) {
			quotas = new QuotaEngine();
			List<BaseResource> resources = new ArrayList<BaseResource>();
			resources.add(new ReservableResource("instances", "_sync_instances", "quota_instances"));
			resources.add(new ReservableResource("cores", "_sync_instances", "quota_cores"));
			resources.add(new ReservableResource("ram", "_sync_instances", "quota_ram"));
			resources.add(new ReservableResource("security_groups", "_sync_security_groups", "quota_security_groups"));
			resources.add(new ReservableResource("floating_ips", "_sync_floating_ips", "quota_floating_ips"));
			resources.add(new ReservableResource("fixed_ips", "_sync_fixed_ips", "quota_fixed_ips"));
			resources.add(new AbsoluteResource("metadata_items", "quota_metadata_items"));
			resources.add(new AbsoluteResource("injected_files", "quota_injected_files"));
			resources.add(new AbsoluteResource("injected_file_content_bytes", "quota_injected_file_content_bytes"));
			resources.add(new AbsoluteResource("injected_file_path_bytes", "quota_injected_file_path_length"));
			resources.add(new CountableResource("security_group_rules", new SecurityGroupRuleCountByCount(),
					"quota_security_group_rules"));
			resources.add(new CountableResource("key_pairs", new KeypairGetCountByUser(), "quota_key_pairs"));
			// resources.add(new ReservableResource("server_groups",
			// "_sync_server_groups", "quota_server_groups"));
			// resources.add(new CountableResource("server_group_members", new
			// ServerGroupCountMembersByUser(),
			// "quota_server_group_members"));
			quotas.registerResources(resources);
		}
		return quotas;
	}

}
