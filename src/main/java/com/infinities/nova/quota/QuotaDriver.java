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

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.infinities.api.openstack.commons.context.OpenstackRequestContext;
import com.infinities.nova.quota.model.ProjectQuotaSet;
import com.infinities.nova.quota.model.ProjectUserQuota;
import com.infinities.nova.quota.model.ProjectUserQuotaSet;
import com.infinities.nova.quota.model.Quota;
import com.infinities.nova.quota.model.QuotaClass;
import com.infinities.nova.quota.model.QuotaSet;
import com.infinities.nova.quota.model.QuotaUsageSet;

public interface QuotaDriver {

	ProjectUserQuota getByProjectAndUser(OpenstackRequestContext context, String projectid, String userid, String resource);

	Quota getByProject(OpenstackRequestContext context, String projectid, String resource);

	QuotaClass getByClass(OpenstackRequestContext context, String quotaClass, String resource);

	QuotaSet getDefaults(OpenstackRequestContext context, Map<String, BaseResource> resources) throws Exception;

	QuotaSet getClassQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources, String quotaClass,
			boolean defaults) throws Exception;

	QuotaUsageSet getUserQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources, String projectid,
			String userid, String quotaClass, boolean defaults, boolean usages, ProjectQuotaSet projectQuotas,
			ProjectUserQuotaSet userQuotas) throws Exception;

	QuotaUsageSet getProjectQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources, String projectid,
			String quotaClass, boolean defaults, boolean usages, boolean remains, QuotaSet projectQuotas) throws Exception;

	QuotaUsageSet getSettableQuotas(OpenstackRequestContext context, Map<String, BaseResource> resources, String projectid,
			String userid) throws Exception;

	void limitCheck(OpenstackRequestContext context, Map<String, BaseResource> resources, Map<String, Integer> values,
			String projectid, String userid) throws Exception;

	List<String> reserve(OpenstackRequestContext context, Map<String, BaseResource> resources, Map<String, Integer> deltas,
			Calendar expire, String projectid, String userid) throws Exception;

	void commit(OpenstackRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception;

	void rollback(OpenstackRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception;

	void usageReset(OpenstackRequestContext context, List<String> resources) throws Exception;

	void destroyAllByProjectAndUser(OpenstackRequestContext context, String projectid, String userid);

	void expire(OpenstackRequestContext context);

	void destroyAllByProject(OpenstackRequestContext context, String projectid);

}
