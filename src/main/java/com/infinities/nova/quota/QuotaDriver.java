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

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.response.model.QuotaSet;

public interface QuotaDriver {

	ProjectUserQuota getByProjectAndUser(NovaRequestContext context, String projectid, String userid, String resource);

	Quota getByProject(NovaRequestContext context, String projectid, String resource);

	QuotaClass getByClass(NovaRequestContext context, String quotaClass, String resource);

	QuotaSet getDefaults(NovaRequestContext context, Map<String, BaseResource> resources) throws Exception;

	QuotaSet getClassQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String quotaClass,
			boolean defaults) throws Exception;

	QuotaUsageSet getUserQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String userid, String quotaClass, boolean defaults, boolean usages, ProjectQuotaSet projectQuotas,
			ProjectUserQuotaSet userQuotas) throws Exception;

	QuotaUsageSet getProjectQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String quotaClass, boolean defaults, boolean usages, boolean remains, QuotaSet projectQuotas) throws Exception;

	QuotaUsageSet getSettableQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String userid) throws Exception;

	void limitCheck(NovaRequestContext context, Map<String, BaseResource> resources, Map<String, Integer> values,
			String projectid, String userid) throws Exception;

	List<String> reserve(NovaRequestContext context, Map<String, BaseResource> resources, Map<String, Integer> deltas,
			Calendar expire, String projectid, String userid) throws Exception;

	void commit(NovaRequestContext context, List<String> reservations, String projectid, String userid) throws Exception;

	void rollback(NovaRequestContext context, List<String> reservations, String projectid, String userid) throws Exception;

	void usageReset(NovaRequestContext context, List<String> resources) throws Exception;

	void destroyAllByProjectAndUser(NovaRequestContext context, String projectid, String userid);

	void expire(NovaRequestContext context);

	void destroyAllByProject(NovaRequestContext context, String projectid);

}
