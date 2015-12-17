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
package com.infinities.nova.model.home;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.quota.BaseResource;
import com.infinities.nova.quota.ProjectQuotaSet;
import com.infinities.nova.quota.ProjectUserQuotaSet;
import com.infinities.nova.quota.ProjectUserQuotaUsageSet;
import com.infinities.nova.quota.QuotaClassSet;
import com.infinities.nova.response.model.QuotaSet;

public interface QuotaHome {

	// userid=null
	ProjectUserQuota quotaGet(NovaRequestContext context, String projectid, String resource, String userid);

	Quota quotaGet(NovaRequestContext context, String projectid, String resource);

	QuotaClass quotaClassGet(NovaRequestContext context, String quotaClass, String resource);

	QuotaClassSet quotaClassGetDefault(NovaRequestContext context) throws Exception;

	QuotaClassSet quotaClassGetAllByName(NovaRequestContext context, String quotaClass) throws Exception;

	ProjectUserQuotaSet quotaGetAllByProjectAndUser(NovaRequestContext context, String projectid, String userid)
			throws Exception;

	ProjectQuotaSet quotaGetAllByProject(NovaRequestContext context, String projectid) throws Exception;

	ProjectUserQuotaUsageSet quotaUsageGetAllByProjectAndUser(NovaRequestContext context, String projectid, String userid)
			throws Exception;

	List<ProjectUserQuota> quotaGetAll(NovaRequestContext context, String projectid);

	ProjectUserQuotaUsageSet quotaUsageGetAllByProject(NovaRequestContext context, String projectid) throws Exception;

	// projectid=null, userid=null
	List<String> quotaReserve(NovaRequestContext context, Map<String, BaseResource> resources, QuotaSet projectQuotas,
			QuotaSet userQuotas, Map<String, Integer> deltas, Calendar expire, int untilRefresh, int maxAge,
			String projectid, String userid) throws Exception;

	void reservationCommit(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception;

	void reservationRollback(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception;

	void quotaUsageUpdate(NovaRequestContext elevated, String projectid, String userid, String resource, Integer inUse,
			Integer reserved, Integer untilRefresh);

	void quotaDestroyAllByProjectAndUser(NovaRequestContext context, String projectid, String userid);

	void quotaDestroyAllByProject(NovaRequestContext context, String projectid);

	void reservationExpire(NovaRequestContext context);
}
