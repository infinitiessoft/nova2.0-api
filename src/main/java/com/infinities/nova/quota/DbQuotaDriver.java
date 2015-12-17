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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.common.Config;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.model.home.QuotaHome;
import com.infinities.nova.model.home.impl.QuotaHomeImpl;
import com.infinities.nova.quota.QuotaUsageSet.Usage;
import com.infinities.nova.response.model.QuotaSet;
import com.infinities.nova.util.StringUtils;

public class DbQuotaDriver implements QuotaDriver {

	private final static int UNLIMITED_VALUE = -1;
	private final QuotaHome db;


	public DbQuotaDriver() {
		this.db = new QuotaHomeImpl();
	}

	@Override
	public ProjectUserQuota getByProjectAndUser(NovaRequestContext context, String projectid, String userid, String resource) {
		return db.quotaGet(context, projectid, resource, userid);
	}

	@Override
	public Quota getByProject(NovaRequestContext context, String projectid, String resource) {
		return db.quotaGet(context, projectid, resource);
	}

	@Override
	public QuotaClass getByClass(NovaRequestContext context, String quotaClass, String resource) {
		return db.quotaClassGet(context, quotaClass, resource);
	}

	@Override
	public QuotaSet getDefaults(NovaRequestContext context, Map<String, BaseResource> resources) throws Exception {
		QuotaClassSet defaultQuotas = db.quotaClassGetDefault(context);
		QuotaSet quotas = setUpQuotas(resources, defaultQuotas);
		return quotas;
	}

	private QuotaSet setUpQuotas(Map<String, BaseResource> resources, QuotaSet defaultQuotas) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		QuotaSet quotas = new QuotaSet();

		for (BaseResource resource : resources.values()) {
			setUpQuota(resource, defaultQuotas, quotas);
		}

		return quotas;
	}

	private void setUpQuota(BaseResource resource, QuotaSet fromQuotas, QuotaSet toQuotas) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String resourceName = resource.getName();
		String methodName = StringUtils.getMethodName(resourceName);
		Class<?>[] param = new Class<?>[1];
		param[0] = Integer.class;
		Object obj = invokeQuotaGetter(methodName, fromQuotas);
		Integer value = null;
		if (obj != null) {
			value = (Integer) obj;
		}
		value = getValue(value, resource.getDefault());
		Method toMethod = toQuotas.getClass().getMethod("set" + methodName, param);
		toMethod.invoke(toQuotas, value);
	}

	private Object invokeQuotaGetter(String methodName, QuotaSet quotas) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method fromMethod = quotas.getClass().getMethod("get" + methodName);
		Object obj = fromMethod.invoke(quotas);
		return obj;
	}

	private int getValue(Integer value, Integer defaultVal) {
		return value == null ? defaultVal : value;
	}

	private int getValue(Object value, Object defaultVal) {
		return value == null ? (Integer) defaultVal : (Integer) value;
	}

	// defaults=true
	@Override
	public QuotaSet getClassQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String quotaClass,
			boolean defaults) throws Exception {
		QuotaSet quotas = new QuotaSet();
		QuotaClassSet classQuotas = db.quotaClassGetAllByName(context, quotaClass);
		for (BaseResource resource : resources.values()) {
			String resourceName = resource.getName();
			String methodName = StringUtils.getMethodName(resourceName);
			Object obj = invokeQuotaGetter(methodName, classQuotas);
			if (defaults || obj != null) {
				setUpQuota(resource, classQuotas, quotas);
			}
		}

		return quotas;
	}

	// quotaClass=null, defaults=true, usages=true, projectQuotas=null,
	// userQuotas=null
	@Override
	public QuotaUsageSet getUserQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String userid, String quotaClass, boolean defaults, boolean usages, ProjectQuotaSet projectQuotas,
			ProjectUserQuotaSet userQuotas) throws Exception {
		if (userQuotas == null) {
			userQuotas = db.quotaGetAllByProjectAndUser(context, projectid, userid);
		}

		if (projectQuotas == null) {
			projectQuotas = db.quotaGetAllByProject(context, projectid);
		}

		Method[] methods = projectQuotas.getClass().getMethods();
		for (Method method : methods) {
			if (method.getReturnType().equals(Integer.class)) {
				Object obj = method.invoke(projectQuotas);
				if (obj != null) {
					Integer value = (Integer) obj;
					String methodName = method.getName(); // getter
					Object originalValue = invokeQuotaGetter(methodName, userQuotas);

					if (originalValue == null) {
						Class<?>[] param = new Class<?>[1];
						param[0] = Integer.class;
						Method userQuotaSetter = userQuotas.getClass().getMethod(methodName.replaceFirst("g", "s"), param);
						userQuotaSetter.invoke(userQuotas, value);
					}
				}
			}
		}

		QuotaUsageSet userUsages = null;

		if (usages) {
			userUsages = db.quotaUsageGetAllByProjectAndUser(context, projectid, userid);
		}

		return processQuotas(context, resources, projectid, userQuotas, quotaClass, defaults, userUsages, false);
	}

	// quotaClass=null, defaults=true, usages=null, remains=false
	private QuotaUsageSet processQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			QuotaSet quotas, String quotaClass, boolean defaults, QuotaUsageSet usages, boolean remains) throws Exception {
		QuotaUsageSet modifiedQuotas = new QuotaUsageSet();

		if (projectid.equals(context.getProjectId())) {
			quotaClass = context.getQuotaClass();
		}
		QuotaSet classQuotas;
		if (!Strings.isNullOrEmpty(quotaClass)) {
			classQuotas = db.quotaClassGetAllByName(context, quotaClass);
		} else {
			classQuotas = new QuotaSet();
		}

		QuotaSet defaultQuotas = getDefaults(context, resources);

		for (BaseResource resource : resources.values()) {
			String resourceName = resource.getName();
			String methodName = StringUtils.getMethodName(resourceName);
			Object obj = invokeQuotaGetter(methodName, quotas);
			if (!defaults && obj == null) {
				continue;
			}
			Object classObj = invokeQuotaGetter(methodName, classQuotas);
			Object defaultObj = invokeQuotaGetter(methodName, defaultQuotas);

			Integer limit = getValue((Integer) obj, getValue(classObj, defaultObj));
			Class<?>[] param = new Class<?>[1];
			param[0] = Usage.class;
			Method userQuotaSetter = modifiedQuotas.getClass().getMethod("set" + methodName, param);
			Usage limitUsage = new Usage();
			limitUsage.setLimit(limit);
			userQuotaSetter.invoke(modifiedQuotas, limitUsage);

			if (usages != null) {
				Method fromMethod = usages.getClass().getMethod("get" + methodName);
				Object usageObj = fromMethod.invoke(quotas);
				Usage usage = new Usage();
				if (usageObj != null) {
					usage = (Usage) usageObj;
				}
				limitUsage.setInUse(getValue(usage.getInUse(), 0));
				limitUsage.setReserved(getValue(usage.getReserved(), 0));
			}

			if (remains) {
				limitUsage.setRemains(limit);
			}
		}

		if (remains) {
			List<ProjectUserQuota> allQuotas = db.quotaGetAll(context, projectid);
			for (ProjectUserQuota quota : allQuotas) {
				String resourceName = quota.getResource();
				String methodName = StringUtils.getMethodName(resourceName);
				Method fromMethod = modifiedQuotas.getClass().getMethod("get" + methodName);
				Object obj = fromMethod.invoke(modifiedQuotas);
				if (obj != null) {
					Usage usage = (Usage) obj;
					usage.setRemains(usage.getRemains() - quota.getHardLimit());
				}
			}
		}
		return modifiedQuotas;
	}

	// quota_class=null, defaults=true, usages=true, remains=false,
	// projectQuotas=null
	@Override
	public QuotaUsageSet getProjectQuotas(NovaRequestContext context, Map<String, BaseResource> resources, String projectid,
			String quotaClass, boolean defaults, boolean usages, boolean remains, QuotaSet projectQuotas) throws Exception {
		if (projectQuotas == null) {
			projectQuotas = db.quotaGetAllByProject(context, projectid);
		}

		ProjectUserQuotaUsageSet projectUsages = null;

		if (usages) {
			projectUsages = db.quotaUsageGetAllByProject(context, projectid);
		}

		return processQuotas(context, resources, projectid, projectQuotas, quotaClass, defaults, projectUsages, remains);
	}

	// userid=null
	@Override
	public QuotaUsageSet getSettableQuotas(NovaRequestContext context, Map<String, BaseResource> resources,
			String projectid, String userid) throws Exception {
		QuotaUsageSet settableQuotas = new QuotaUsageSet();
		ProjectQuotaSet dbProjQuotas = db.quotaGetAllByProject(context, projectid);
		QuotaUsageSet projectQuotas = getProjectQuotas(context, resources, projectid, null, true, true, true, dbProjQuotas);

		if (!Strings.isNullOrEmpty(userid)) {
			ProjectUserQuotaSet settedQuotas = db.quotaGetAllByProjectAndUser(context, projectid, userid);
			QuotaUsageSet userQuotas = getUserQuotas(context, resources, projectid, userid, null, true, true, dbProjQuotas,
					settedQuotas);

			Method[] methods = userQuotas.getClass().getMethods();
			for (Method method : methods) {
				if (method.getReturnType().equals(Usage.class)) {
					Object obj = method.invoke(userQuotas);
					if (obj != null) {
						Usage value = (Usage) obj;
						Object settedQuotasObj = invokeQuotaGetter(method.getName(), settedQuotas);

						Method projectQuotasMethod = projectQuotas.getClass().getMethod(method.getName());
						Object projectQuotasObj = projectQuotasMethod.invoke(projectQuotas);
						Usage projectQuotasUsage = (Usage) projectQuotasObj;

						int maximum = sumQuotaValues(projectQuotasUsage.getRemains(), getValue(settedQuotasObj, 0));
						int minimum = value.getInUse() + value.getReserved();
						Usage usage = new Usage();
						usage.setMaximum(maximum);
						usage.setMinimum(minimum);

						Class<?>[] param = new Class<?>[1];
						param[0] = Usage.class;
						Method settableQuotasSetter = settableQuotas.getClass().getMethod(
								method.getName().replaceFirst("g", "s"), param);
						settableQuotasSetter.invoke(settableQuotas, usage);
					}
				}
			}
		} else {
			Method[] methods = projectQuotas.getClass().getMethods();
			for (Method method : methods) {
				if (method.getReturnType().equals(Usage.class)) {
					Object obj = method.invoke(projectQuotas);
					if (obj != null) {
						Usage value = (Usage) obj;

						int minimum = Math.max(subQuotaValues(value.getLimit(), value.getRemains()), value.getInUse()
								+ value.getReserved());

						Usage usage = new Usage();
						usage.setMaximum(-1);
						usage.setMinimum(minimum);

						Class<?>[] param = new Class<?>[1];
						param[0] = Usage.class;
						Method settableQuotasSetter = settableQuotas.getClass().getMethod(
								method.getName().replaceFirst("g", "s"), param);
						settableQuotasSetter.invoke(settableQuotas, usage);
					}
				}
			}
		}

		return settableQuotas;
	}

	private boolean isUnlimitedValue(int v) {
		return v <= UNLIMITED_VALUE;
	}

	private int sumQuotaValues(int v1, int v2) {
		if (isUnlimitedValue(v1) || isUnlimitedValue(v2)) {
			return UNLIMITED_VALUE;
		}
		return v1 + v2;
	}

	private int subQuotaValues(int v1, int v2) {
		if (isUnlimitedValue(v1) || isUnlimitedValue(v2)) {
			return UNLIMITED_VALUE;
		}
		return v1 - v2;
	}

	// projectid=null, userid=null
	@Override
	public void limitCheck(NovaRequestContext context, Map<String, BaseResource> resources, Map<String, Integer> values,
			String projectid, String userid) throws Exception {

		validMethodCallCheckResources(values, "check");

		Set<String> unders = new HashSet<String>();
		for (Entry<String, Integer> entry : values.entrySet()) {
			if (entry.getValue() < 0) {
				unders.add(entry.getKey());
			}
		}
		if (unders.size() > 0) {
			String msg = String.format("Change would make usage less than 0 for the following resources: %s", unders);
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
		}

		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}

		if (Strings.isNullOrEmpty(userid)) {
			userid = context.getUserId();
		}

		ProjectQuotaSet projectQuotas = db.quotaGetAllByProject(context, projectid);
		QuotaSet quotas = getQuotas(context, resources, values.keySet(), false, projectid, null, projectQuotas);
		QuotaSet userQuotas = getQuotas(context, resources, values.keySet(), false, projectid, userid, projectQuotas);

		List<String> overs = new ArrayList<String>();
		for (Entry<String, Integer> entry : values.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			String methodName = StringUtils.getMethodName(key);
			Integer quotasValue = (Integer) invokeQuotaGetter(methodName, quotas);
			Integer userQuotasValue = (Integer) invokeQuotaGetter(methodName, userQuotas);
			if (quotasValue >= 0 && quotasValue < value || (userQuotasValue >= 0 && userQuotasValue < value)) {
				overs.add(key);
			}
		}

		if (!overs.isEmpty()) {
			// Map<String, Integer> headroom = new HashMap<String, Integer>();
			// for (String key : overs) {
			// String methodName = getMethodName(key);
			// Integer quotasValue = (Integer) invokeQuotaGetter(methodName,
			// quotas);
			// Integer projectQuotasValue = (Integer)
			// invokeQuotaGetter(methodName, projectQuotas);
			// if (quotasValue == null && projectQuotasValue != null) {
			// headroom.put(key, projectQuotasValue);
			// } else if (quotasValue != null && projectQuotasValue == null) {
			// headroom.put(key, quotasValue);
			// } else if (quotasValue != null && projectQuotasValue != null) {
			// int min = Math.min(quotasValue, projectQuotasValue);
			// headroom.put(key, min);
			// }
			//
			// }
			String msg = String.format("Quota exceeded for resources: %s", overs);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build());
		}
	}

	private void validMethodCallCheckResources(Map<String, Integer> resource, String method) {
		for (String name : resource.keySet()) {
			validMethodCallCheckResource(name, method);
		}
	}

	private void validMethodCallCheckResource(String name, String method) {
		if (!QuotaEngine.getQUOTAS().contains(name)) {
			String msg = String.format("Wrong quota method %s used on resource %s", method, name);
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
		}

		BaseResource res = QuotaEngine.getQUOTAS().get(name);

		if (!res.getValidMethod().endsWith(method)) {
			String msg = String.format("Wrong quota method %s used on resource %s", method, name);
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
		}
	}

	// projectid=null, userid=null, projectQuotas=null
	private QuotaSet getQuotas(NovaRequestContext context, Map<String, BaseResource> resources, Set<String> keys,
			boolean hasSync, String projectid, String userid, ProjectQuotaSet projectQuotas) throws Exception {

		Set<String> desired = new HashSet<String>(keys);

		Function<Class<?>, Boolean> syncFilt;
		if (hasSync) {
			syncFilt = new Function<Class<?>, Boolean>() {

				@Override
				public Boolean apply(Class<?> input) {
					try {
						return input.getMethod("getSync") != null;
					} catch (Exception e) {
						return false;
					}
				}

			};
		} else {
			syncFilt = new Function<Class<?>, Boolean>() {

				@Override
				public Boolean apply(Class<?> input) {

					try {
						return input.getMethod("getSync") == null;
					} catch (NoSuchMethodException e) {
						return true;
					} catch (SecurityException e) {
						return false;
					}
				}

			};
		}

		Map<String, BaseResource> subResources = new HashMap<String, BaseResource>();
		for (Entry<String, BaseResource> entry : resources.entrySet()) {
			if (desired.contains(entry.getKey()) && syncFilt.apply(entry.getValue().getClass())) {
				subResources.put(entry.getKey(), entry.getValue());
			}
		}

		if (keys.size() != subResources.size()) {
			Set<String> unknown = Sets.difference(desired, subResources.keySet());
			String msg = String.format("Unknown quota resources %s.", unknown);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(msg).build());
		}

		QuotaUsageSet quotas = null;
		if (!Strings.isNullOrEmpty(userid)) {
			quotas = getUserQuotas(context, subResources, projectid, userid, context.getQuotaClass(), true, false,
					projectQuotas, null);
		} else {
			quotas = getProjectQuotas(context, subResources, projectid, context.getQuotaClass(), true, false, false,
					projectQuotas);
		}

		QuotaSet set = new QuotaSet();
		Method[] methods = quotas.getClass().getMethods();
		for (Method method : methods) {
			if (method.getReturnType().equals(Usage.class)) {
				Object obj = method.invoke(quotas);
				if (obj != null) {
					Usage value = (Usage) obj;
					Class<?>[] param = new Class<?>[1];
					param[0] = Integer.class;
					Method toMethod = set.getClass().getMethod(method.getName().replaceFirst("g", "s"), param);
					toMethod.invoke(set, value.getLimit());
				}
			}
		}
		return set;
	}

	// expire=null, projectid=null, userid=null
	@Override
	public List<String> reserve(NovaRequestContext context, Map<String, BaseResource> resources,
			Map<String, Integer> deltas, Calendar expire, String projectid, String userid) throws Exception {

		validMethodCallCheckResources(deltas, "reserve");

		if (expire == null) {
			expire = Calendar.getInstance();
			expire.add(Calendar.SECOND, Config.Instance.getOpt("reservation_expire").asInteger());
		}

		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}

		if (Strings.isNullOrEmpty(userid)) {
			userid = context.getUserId();
		}

		ProjectQuotaSet projectQuotas = db.quotaGetAllByProject(context, projectid);
		QuotaSet quotas = getQuotas(context, resources, deltas.keySet(), true, projectid, null, projectQuotas);
		QuotaSet userQuotas = getQuotas(context, resources, deltas.keySet(), true, projectid, userid, projectQuotas);

		return db.quotaReserve(context, resources, quotas, userQuotas, deltas, expire,
				Config.Instance.getOpt("until_refresh").asInteger(), Config.Instance.getOpt("max_age").asInteger(),
				projectid, userid);
	}

	@Override
	public void commit(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}

		if (Strings.isNullOrEmpty(userid)) {
			userid = context.getUserId();
		}

		db.reservationCommit(context, reservations, projectid, userid);
	}

	@Override
	public void rollback(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}

		if (Strings.isNullOrEmpty(userid)) {
			userid = context.getUserId();
		}

		db.reservationRollback(context, reservations, projectid, userid);
	}

	@Override
	public void usageReset(NovaRequestContext context, List<String> resources) throws CloneNotSupportedException {
		NovaRequestContext elevated = context.elevated(null);

		for (String resource : resources) {
			try {
				db.quotaUsageUpdate(elevated, context.getProjectId(), context.getUserId(), resource, -1, null, null);
			} catch (WebApplicationException e) {
				if (e.getResponse().getStatus() != Status.NOT_FOUND.getStatusCode()) {
					throw e;
				}
			}
		}
	}

	@Override
	public void destroyAllByProjectAndUser(NovaRequestContext context, String projectid, String userid) {
		db.quotaDestroyAllByProjectAndUser(context, projectid, userid);
	}

	@Override
	public void destroyAllByProject(NovaRequestContext context, String projectid) {
		db.quotaDestroyAllByProject(context, projectid);
	}

	@Override
	public void expire(NovaRequestContext context) {
		db.reservationExpire(context);
	}

}
