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
