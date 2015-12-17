package com.infinities.nova.quota;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.quota.QuotaUsageSet.Usage;
import com.infinities.nova.response.model.QuotaSet;
import com.infinities.nova.util.StringUtils;

public class NoopQuotaDriver implements QuotaDriver {

	@Override
	public ProjectUserQuota
			getByProjectAndUser(NovaRequestContext context, String projectid, String userid, String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quota getByProject(NovaRequestContext context, String projectid, String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaClass getByClass(NovaRequestContext context, String quotaClass, String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaSet getDefaults(NovaRequestContext context, Map<String, BaseResource> resources) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaSet getClassQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String quotaClass,
			boolean defaults) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaUsageSet getUserQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String userid, String quotaClass, boolean defaults, boolean usages, ProjectQuotaSet projectQuotas,
			ProjectUserQuotaSet userQuotas) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuotaUsageSet getProjectQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String quotaClass, boolean defaults, boolean usages, boolean remains, QuotaSet projectQuotas) throws Exception {
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
	public QuotaUsageSet getSettableQuotas(NovaRequestContext context, Map<String, BaseResource> resources,
			String projectid, String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void limitCheck(NovaRequestContext context, Map<String, BaseResource> resources, Map<String, Integer> values,
			String projectid, String userid) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> reserve(NovaRequestContext context, Map<String, BaseResource> resources,
			Map<String, Integer> deltas, Calendar expire, String projectid, String userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commit(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void usageReset(NovaRequestContext context, List<String> resources) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyAllByProjectAndUser(NovaRequestContext context, String projectid, String userid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expire(NovaRequestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyAllByProject(NovaRequestContext context, String projectid) {
		// TODO Auto-generated method stub

	}

}
