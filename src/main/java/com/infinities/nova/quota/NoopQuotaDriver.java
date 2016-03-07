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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.quota.QuotaUsageSet.Usage;
import com.infinities.nova.response.model.QuotaSet;
import com.infinities.nova.util.StringUtils;

public class NoopQuotaDriver implements QuotaDriver {

	@Override
	public ProjectUserQuota getByProjectAndUser(OpenstackRequestContext context, String projectid, String userid,
			String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quota getByProject(OpenstackRequestContext context, String projectid, String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaClass getByClass(OpenstackRequestContext context, String quotaClass, String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaSet getDefaults(OpenstackRequestContext context, Map<String, BaseResource> resources) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaSet getClassQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources, String quotaClass,
			boolean defaults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaUsageSet getUserQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources,
			String projectid, String userid, String quotaClass, boolean defaults, boolean usages,
			ProjectQuotaSet projectQuotas, ProjectUserQuotaSet userQuotas) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaUsageSet getProjectQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources,
			String projectid, String quotaClass, boolean defaults, boolean usages, boolean remains, QuotaSet projectQuotas)
			throws Exception {
		return getNoopQuotas(resources, usages, remains);
	}

	private QuotaUsageSet getNoopQuotas(Map<String, BaseResource> resources, boolean usages, boolean remains)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		QuotaUsageSet quotas = new QuotaUsageSet();

		for (BaseResource resource : resources.values()) {
			String resourceName = resource.getName();
			String methodName = StringUtils.getMethodName(resourceName);
			Class<?>[] param = new Class<?>[1];
			param[0] = Usage.class;
			Method userQuotaSetter = quotas.getClass().getMethod("set" + methodName, param);
			Usage usage = new Usage();
			usage.setLimit(-1);
			userQuotaSetter.invoke(quotas, usage);

			if (usages) {
				usage.setInUse(-1);
				usage.setReserved(-1);
			}

			if (remains) {
				usage.setRemains(-1);
			}
		}
		return quotas;
	}

	@Override
	public QuotaUsageSet getSettableQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources,
			String projectid, String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void limitCheck(OpenstackRequestContext context, Map<String, BaseResource> resources,
			Map<String, Integer> values, String projectid, String userid) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> reserve(OpenstackRequestContext context, Map<String, BaseResource> resources,
			Map<String, Integer> deltas, Calendar expire, String projectid, String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commit(OpenstackRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback(OpenstackRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void usageReset(OpenstackRequestContext context, List<String> resources) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyAllByProjectAndUser(OpenstackRequestContext context, String projectid, String userid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expire(OpenstackRequestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyAllByProject(OpenstackRequestContext context, String projectid) {
		// TODO Auto-generated method stub

	}

}
