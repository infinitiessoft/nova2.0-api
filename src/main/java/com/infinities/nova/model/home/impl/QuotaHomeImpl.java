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
package com.infinities.nova.model.home.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jersey.repackaged.com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.db.model.FixedIp;
import com.infinities.nova.db.model.IQuota;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.db.model.InstanceType;
import com.infinities.nova.db.model.ProjectUserQuota;
import com.infinities.nova.db.model.Quota;
import com.infinities.nova.db.model.QuotaClass;
import com.infinities.nova.db.model.QuotaUsage;
import com.infinities.nova.db.model.Reservation;
import com.infinities.nova.db.model.SecurityGroup;
import com.infinities.nova.exception.OverQuotaException;
import com.infinities.nova.exception.ProjectQuotaNotFoundException;
import com.infinities.nova.exception.ProjectUserQuotaNotFoundException;
import com.infinities.nova.exception.QuotaClassNotFoundException;
import com.infinities.nova.model.home.InstanceTypeHome;
import com.infinities.nova.model.home.QuotaHome;
import com.infinities.nova.quota.BaseResource;
import com.infinities.nova.quota.DbQuotaUsageSet;
import com.infinities.nova.quota.ProjectQuotaSet;
import com.infinities.nova.quota.ProjectUserQuotaSet;
import com.infinities.nova.quota.ProjectUserQuotaUsageSet;
import com.infinities.nova.quota.QuotaClassSet;
import com.infinities.nova.quota.QuotaUsageSet;
import com.infinities.nova.quota.QuotaUsageSet.Usage;
import com.infinities.nova.quota.ReservableResource;
import com.infinities.nova.response.model.FloatingIp;
import com.infinities.nova.response.model.QuotaSet;
import com.infinities.nova.util.ReflectUtils;
import com.infinities.nova.util.StringUtils;

public class QuotaHomeImpl extends AbstractHome implements QuotaHome {

	private final static String DEFAULT_QUOTA_NAME = "default";
	private final static Logger logger = LoggerFactory.getLogger(QuotaHomeImpl.class);
	private final static Set<String> PER_PROJECT_QUOTAS;
	private final static InstanceTypeHome instanceTypeHome = new InstanceTypeHomeImpl();

	static {
		Set<String> perProjectQuotas = new HashSet<String>();
		perProjectQuotas.add("fixed_ips");
		perProjectQuotas.add("floating_ips");
		perProjectQuotas.add("networks");
		PER_PROJECT_QUOTAS = Collections.unmodifiableSet(perProjectQuotas);
	}


	@Override
	public ProjectUserQuota quotaGet(NovaRequestContext context, String projectid, String resource, String userid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProjectUserQuota> cq = cb.createQuery(ProjectUserQuota.class);
		Root<ProjectUserQuota> root = cq.from(ProjectUserQuota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		Predicate pProjectId = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectId);
		Predicate pResource = cb.equal(root.get("resource"), resource);
		predicateList.add(pResource);

		if (!Strings.isNullOrEmpty(userid)) {
			Predicate pUserId = cb.equal(root.get("userId"), userid);
			predicateList.add(pUserId);
		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<ProjectUserQuota> q = em.createQuery(cq);
		try {
			ProjectUserQuota result = q.getSingleResult();
			return result;
		} catch (NoResultException e) {
			if (!Strings.isNullOrEmpty(userid)) {
				throw new ProjectUserQuotaNotFoundException(null, userid, projectid);
			} else {
				throw new ProjectQuotaNotFoundException(null, projectid);
			}
		}
	}

	@Override
	public Quota quotaGet(NovaRequestContext context, String projectid, String resource) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Quota> cq = cb.createQuery(Quota.class);
		Root<Quota> root = cq.from(Quota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		Predicate pProjectId = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectId);
		Predicate pResource = cb.equal(root.get("resource"), resource);
		predicateList.add(pResource);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Quota> q = em.createQuery(cq);
		try {
			Quota result = q.getSingleResult();
			return result;
		} catch (NoResultException e) {
			throw new ProjectQuotaNotFoundException(null, projectid);
		}
	}

	@Override
	public QuotaClass quotaClassGet(NovaRequestContext context, String quotaClass, String resource) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaClass> cq = cb.createQuery(QuotaClass.class);
		Root<QuotaClass> root = cq.from(QuotaClass.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pClassName = cb.equal(root.get("className"), quotaClass);
		predicateList.add(pClassName);
		Predicate pResource = cb.equal(root.get("resource"), resource);
		predicateList.add(pResource);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaClass> q = em.createQuery(cq);

		try {
			QuotaClass result = q.getSingleResult();
			return result;
		} catch (NoResultException e) {
			throw new QuotaClassNotFoundException(null, quotaClass);
		}
	}

	@Override
	public QuotaClassSet quotaClassGetDefault(NovaRequestContext context) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaClass> cq = cb.createQuery(QuotaClass.class);
		Root<QuotaClass> root = cq.from(QuotaClass.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pClassName = cb.equal(root.get("className"), DEFAULT_QUOTA_NAME);
		predicateList.add(pClassName);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaClass> q = em.createQuery(cq);

		List<QuotaClass> rows = q.getResultList();
		QuotaClassSet result = new QuotaClassSet();
		result.setClassName(DEFAULT_QUOTA_NAME);
		setUpQuotaSet(result, rows);
		return result;
	}

	@Override
	public QuotaClassSet quotaClassGetAllByName(NovaRequestContext context, String quotaClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		NovaRequestContext.authorizeQuotaClassContext(context, quotaClass);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaClass> cq = cb.createQuery(QuotaClass.class);
		Root<QuotaClass> root = cq.from(QuotaClass.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pClassName = cb.equal(root.get("className"), quotaClass);
		predicateList.add(pClassName);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaClass> q = em.createQuery(cq);

		List<QuotaClass> rows = q.getResultList();
		QuotaClassSet result = new QuotaClassSet();
		result.setClassName(quotaClass);
		setUpQuotaSet(result, rows);
		return result;
	}

	@Override
	public ProjectUserQuotaSet quotaGetAllByProjectAndUser(NovaRequestContext context, String projectid, String userid)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProjectUserQuota> cq = cb.createQuery(ProjectUserQuota.class);
		Root<ProjectUserQuota> root = cq.from(ProjectUserQuota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		Predicate pProjectId = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectId);
		Predicate pUserId = cb.equal(root.get("userId"), userid);
		predicateList.add(pUserId);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<ProjectUserQuota> q = em.createQuery(cq);

		List<ProjectUserQuota> rows = q.getResultList();
		ProjectUserQuotaSet result = new ProjectUserQuotaSet();
		result.setProjectId(projectid);
		result.setUserId(userid);
		setUpQuotaSet(result, rows);
		return result;
	}

	@Override
	public ProjectQuotaSet quotaGetAllByProject(NovaRequestContext context, String projectid) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Quota> cq = cb.createQuery(Quota.class);
		Root<Quota> root = cq.from(Quota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectId = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectId);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Quota> q = em.createQuery(cq);

		List<Quota> rows = q.getResultList();
		ProjectQuotaSet result = new ProjectQuotaSet();
		result.setProjectId(projectid);
		setUpQuotaSet(result, rows);
		return result;
	}

	private void setUpQuotaSet(QuotaSet set, List<? extends IQuota> rows) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for (IQuota row : rows) {
			String resource = row.getResource();
			String name = StringUtils.getMethodName(resource);
			Class<?>[] param = new Class<?>[1];
			param[0] = Integer.class;
			Method toMethod = set.getClass().getMethod("set" + name, param);
			toMethod.invoke(set, row.getHardLimit());
		}
	}

	private void setUpQuotaUsageSet(QuotaUsageSet set, List<QuotaUsage> rows) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (QuotaUsage row : rows) {
			Integer inUse = row.getInUse();
			Integer reserve = row.getReserved();
			String resource = row.getResource();
			String name = StringUtils.getMethodName(resource);
			Object obj = getObject(set, resource);

			if (obj == null) {
				Usage usage = new Usage();
				usage.setInUse(inUse);
				usage.setReserved(reserve);
				Class<?>[] param = new Class<?>[1];
				param[0] = Usage.class;
				Method setMethod = set.getClass().getMethod("set" + name, param);
				setMethod.invoke(set, usage);
			} else {
				Usage usage = (Usage) obj;
				usage.setInUse(usage.getInUse() + inUse);
				usage.setReserved(usage.getReserved() + reserve);
			}
		}
	}

	@Override
	public ProjectUserQuotaUsageSet quotaUsageGetAllByProjectAndUser(NovaRequestContext context, String projectid,
			String userid) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return quotaUsageGetAll(context, projectid, userid);
	}

	// user = null
	private ProjectUserQuotaUsageSet quotaUsageGetAll(NovaRequestContext context, String projectid, String userid)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaUsage> cq = cb.createQuery(QuotaUsage.class);
		Root<QuotaUsage> root = cq.from(QuotaUsage.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);

		ProjectUserQuotaUsageSet result = new ProjectUserQuotaUsageSet();
		result.setProjectId(projectid);

		if (!Strings.isNullOrEmpty(userid)) {
			Predicate pUserid = cb.equal(root.get("userId"), userid);
			Predicate pNull = cb.isNull(root.get("userId"));
			Predicate pOr = cb.or(pUserid, pNull);
			predicateList.add(pOr);
			result.setUserId(userid);
		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaUsage> q = em.createQuery(cq);
		List<QuotaUsage> rows = q.getResultList();
		setUpQuotaUsageSet(result, rows);
		return result;
	}

	@Override
	public List<ProjectUserQuota> quotaGetAll(NovaRequestContext context, String projectid) {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProjectUserQuota> cq = cb.createQuery(ProjectUserQuota.class);
		Root<ProjectUserQuota> root = cq.from(ProjectUserQuota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectId = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectId);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<ProjectUserQuota> q = em.createQuery(cq);
		List<ProjectUserQuota> result = q.getResultList();
		return result;
	}

	@Override
	public ProjectUserQuotaUsageSet quotaUsageGetAllByProject(NovaRequestContext context, String projectid)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return quotaUsageGetAll(context, projectid, null);
	}

	@Override
	public List<String> quotaReserve(NovaRequestContext context, Map<String, BaseResource> resources,
			QuotaSet projectQuotas, QuotaSet userQuotas, Map<String, Integer> deltas, Calendar expire, int untilRefresh,
			int maxAge, String projectid, String userid) throws InterruptedException, CloneNotSupportedException,
			NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		while (true) {
			try {
				return quotaReserveWithoutRetry(context, resources, projectQuotas, userQuotas, deltas, expire, untilRefresh,
						maxAge, projectid, userid);
			} catch (PersistenceException e) {
				logger.warn("Deadlock detected when running quotoReserve: Retrying...", e);
				Thread.sleep(500);
				continue;
			}
		}
	}

	private List<String> quotaReserveWithoutRetry(NovaRequestContext context, Map<String, BaseResource> resources,
			QuotaSet projectQuotas, QuotaSet userQuotas, Map<String, Integer> deltas, Calendar expire, Integer untilRefresh,
			Integer maxAge, String projectid, String userid) throws CloneNotSupportedException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		NovaRequestContext elevated = context.elevated(null);

		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}
		if (Strings.isNullOrEmpty(userid)) {
			userid = context.getUserId();
		}

		Entry<QuotaUsageSet, DbQuotaUsageSet> entry = getProjectUserQuotaUsages(context, projectid, userid);
		QuotaUsageSet projectUsages = entry.getKey();
		DbQuotaUsageSet userUsages = entry.getValue();
		List<String> work = new ArrayList<String>(deltas.keySet());
		while (!work.isEmpty()) {
			String resource = work.get(0);
			boolean refresh = false;
			if ((!PER_PROJECT_QUOTAS.contains(resource)) && (!contains(resource, userUsages))) {
				QuotaUsage usage = quotaUsageCreate(elevated, projectid, userid, resource, 0, 0, untilRefresh);
				replace(userUsages, resource, usage);
				refresh = true;
			} else if ((PER_PROJECT_QUOTAS.contains(resource)) && (!contains(resource, userUsages))) {
				QuotaUsage usage = quotaUsageCreate(elevated, projectid, null, resource, 0, 0, untilRefresh);
				replace(userUsages, resource, usage);
				refresh = true;
			} else if (getUsage(userUsages, resource).getInUse() < 0) {
				refresh = true;
			} else if (getUsage(userUsages, resource).getUntilRefresh() != null) {
				QuotaUsage usage = getUsage(userUsages, resource);
				usage.setUntilRefresh(usage.getUntilRefresh() - 1);
				if (usage.getUntilRefresh() <= 0) {
					refresh = true;
				}
			} else if (maxAge != null
					&& ((Calendar.getInstance().getTimeInMillis() - getUsage(userUsages, resource).getUpdatedAt()
							.getTimeInMillis()) / 1000) >= maxAge) {
				refresh = true;
			}

			if (refresh) {
				String sync = ((ReservableResource) resources.get(resource)).getSync();
				Map<String, Integer> updates = quotaSyncFunctions(sync, elevated, projectid, userid);

				for (Entry<String, Integer> item : updates.entrySet()) {
					if ((!PER_PROJECT_QUOTAS.contains(item.getKey())) && (!contains(item.getKey(), userUsages))) {
						QuotaUsage usage = quotaUsageCreate(elevated, projectid, userid, item.getKey(), 0, 0, untilRefresh);
						replace(userUsages, item.getKey(), usage);
					}
					if ((PER_PROJECT_QUOTAS.contains(item.getKey())) && (!contains(item.getKey(), userUsages))) {
						QuotaUsage usage = quotaUsageCreate(elevated, projectid, null, item.getKey(), 0, 0, untilRefresh);
						replace(userUsages, item.getKey(), usage);
					}
					QuotaUsage usage = getUsage(userUsages, item.getKey());
					if (usage.getInUse().intValue() != item.getValue().intValue()) {
						logger.debug(
								"quota_usages out of sync, updating. project_id: {}, user_id: {}, resource: {}, tracked usage: {}, acutal usage: {}",
								new Object[] { projectid, userid, item.getKey(), usage.getInUse(), item.getValue() });
					}

					usage.setInUse(item.getValue());
					usage.setUntilRefresh(untilRefresh);
					work.remove(item.getKey());
				}
			}
		}

		List<String> unders = new ArrayList<String>();
		for (Entry<String, Integer> item : deltas.entrySet()) {
			QuotaUsage usage = getUsage(userUsages, item.getKey());
			if (item.getValue() < 0 && (item.getValue() + usage.getInUse() < 0)) {
				unders.add(item.getKey());
			}
		}

		setUpProjectUsagesByUserUsages(projectUsages, userUsages);
		List<String> overs = new ArrayList<String>();
		for (Entry<String, Integer> item : deltas.entrySet()) {
			Integer userQuota = getQuota(userQuotas, item.getKey());
			Integer projectQuota = getQuota(projectQuotas, item.getKey());
			Usage projectUsage = getUsage(projectUsages, item.getKey());
			QuotaUsage userUsage = getUsage(userUsages, item.getKey());
			Integer delta = item.getValue();
			if (userQuota >= 0 && delta >= 0
					&& (projectQuota < delta + projectUsage.getTotal() || userQuota < delta + userUsage.getTotal())) {
				overs.add(item.getKey());
			}
		}

		List<String> reservations = new ArrayList<String>();
		if (overs.isEmpty()) {
			for (Entry<String, Integer> item : deltas.entrySet()) {
				String res = item.getKey();
				Integer delta = item.getValue();
				QuotaUsage usage = getUsage(userUsages, res);
				Reservation reservation =
						reservationCreate(elevated, UUID.randomUUID().toString(), usage, projectid, userid, res, delta,
								expire);
				reservations.add(reservation.getUuid());
				if (delta > 0) {
					usage.setReserved(usage.getReserved() + delta);
				}
			}
		}

		saveQuotaUsages(userUsages);

		if (!unders.isEmpty()) {
			logger.warn("Change will make usage less than 0 for the following resources: {}", unders);
		}

		if (!overs.isEmpty()) {
			throw new OverQuotaException(null, overs);
		}

		return reservations;
	}

	private Reservation reservationCreate(NovaRequestContext elevated, String uuid, QuotaUsage usage, String projectid,
			String userid, String resource, Integer delta, Calendar expire) {
		Reservation ref = new Reservation();
		ref.setUuid(uuid);
		ref.setUsage(usage);
		usage.getReservations().add(ref);
		ref.setProjectId(projectid);
		ref.setUserId(userid);
		ref.setResource(resource);
		ref.setDelta(delta);
		ref.setExpire(expire);
		EntityManager em = getEntityManager();
		em.persist(ref);
		return ref;
	}

	private void saveQuotaUsages(DbQuotaUsageSet usages) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method[] methods = usages.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get") && method.getReturnType().equals(QuotaUsage.class)) {
				Object obj = method.invoke(usages);
				if (obj != null) {
					QuotaUsage usage = (QuotaUsage) obj;
					EntityManager em = getEntityManager();
					em.persist(usage);
				}
			}
		}
	}

	private Usage getUsage(QuotaUsageSet usages, String resource) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = getObject(usages, resource);
		if (obj == null) {
			return null;
		} else {
			return (Usage) obj;
		}
	}

	private Integer getQuota(QuotaSet userQuotas, String resource) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = getObject(userQuotas, resource);
		if (obj == null) {
			return null;
		} else {
			return (Integer) obj;
		}
	}

	private Object getObject(Object o, String resource) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name = StringUtils.getMethodName(resource);
		return ReflectUtils.getObject(name, o);
	}

	private void setUpProjectUsagesByUserUsages(QuotaUsageSet projectUsages, DbQuotaUsageSet userUsages)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		Method[] methods = userUsages.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get") && method.getReturnType().equals(QuotaUsage.class)) {
				Object obj = method.invoke(userUsages);
				if (obj != null) {
					QuotaUsage usage = (QuotaUsage) obj;
					Method getMethod = projectUsages.getClass().getMethod(method.getName());
					Object getObj = getMethod.invoke(projectUsages);
					if (getObj == null) {
						Class<?>[] param = new Class<?>[1];
						param[0] = QuotaUsage.class;
						Method setMethod =
								projectUsages.getClass().getMethod(method.getName().replaceFirst("get", "set"), param);
						setMethod.invoke(projectUsages, setUpUsage(usage));

					}
				}
			}
		}
	}

	private Usage setUpUsage(QuotaUsage quotaUsage) {
		Usage usage = new Usage();
		usage.setInUse(quotaUsage.getInUse());
		usage.setReserved(quotaUsage.getReserved());
		usage.setTotal(quotaUsage.getTotal());
		return usage;
	}

	// instances, ram, cores, floating_ips, fixed_ips, security_groups
	private Map<String, Integer>
			quotaSyncFunctions(String sync, NovaRequestContext elevated, String projectid, String userid) {
		if (sync.equals("_sync_instances")) {
			return syncInstances(elevated, projectid, userid);
		} else if (sync.equals("_sync_floating_ips")) {
			return syncFloatingIps(elevated, projectid, userid);
		} else if (sync.equals("_sync_fixed_ips")) {
			return syncFixedIps(elevated, projectid, userid);
		} else if (sync.equals("_sync_security_groups")) {
			return syncSecurityGroups(elevated, projectid, userid);
		}
		String msg = String.format("Quota Sync function %s could not be found.", sync);
		throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(msg).build());
	}

	private Map<String, Integer> syncInstances(NovaRequestContext context, String projectid, String userid) {
		return instanceDataGetForUser(context, projectid, userid);
	}

	private Map<String, Integer> syncFloatingIps(NovaRequestContext context, String projectid, String userid) {
		return floatingIpCountByProject(context, projectid);
	}

	private Map<String, Integer> floatingIpCountByProject(NovaRequestContext context, String projectid) {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FloatingIp> cq = cb.createQuery(FloatingIp.class);
		Root<FloatingIp> root = cq.from(FloatingIp.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pAutoAssigned = cb.equal(root.get("autoAssigned"), false);
		predicateList.add(pAutoAssigned);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<FloatingIp> q = em.createQuery(cq);

		List<FloatingIp> rows = q.getResultList();
		QuotaSet usage = new QuotaSet();
		usage.setFloatingIps(rows.size());
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("floating_ips", usage.getFloatingIps());
		return result;
	}

	private Map<String, Integer> syncFixedIps(NovaRequestContext context, String projectid, String userid) {
		return fixedIpCountByProject(context, projectid);
	}

	private Map<String, Integer> fixedIpCountByProject(NovaRequestContext context, String projectid) {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FixedIp> cq = cb.createQuery(FixedIp.class);
		Root<FixedIp> root = cq.from(FixedIp.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("instance.projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pAutoAssigned = cb.equal(root.get("autoAssigned"), false);
		predicateList.add(pAutoAssigned);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<FixedIp> q = em.createQuery(cq);

		List<FixedIp> rows = q.getResultList();
		QuotaSet usage = new QuotaSet();
		usage.setFixedIps(rows.size());
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("fixed_ips", usage.getFixedIps());
		return result;
	}

	private Map<String, Integer> syncSecurityGroups(NovaRequestContext context, String projectid, String userid) {
		return securityGroupCountByProject(context, projectid, userid);
	}

	private Map<String, Integer> securityGroupCountByProject(NovaRequestContext context, String projectid, String userid) {
		NovaRequestContext.authorizeProjectContext(context, projectid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SecurityGroup> cq = cb.createQuery(SecurityGroup.class);
		Root<SecurityGroup> root = cq.from(SecurityGroup.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pUserid = cb.equal(root.get("userId"), userid);
		predicateList.add(pUserid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<SecurityGroup> q = em.createQuery(cq);

		List<SecurityGroup> rows = q.getResultList();
		QuotaSet usage = new QuotaSet();
		usage.setSecurityGroups(rows.size());
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("security_groups", usage.getSecurityGroups());
		return result;
	}

	// instances, ram, cores
	private Map<String, Integer> instanceDataGetForUser(NovaRequestContext context, String projectid, String userid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Instance> cq = cb.createQuery(Instance.class);
		Root<Instance> root = cq.from(Instance.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		if (!Strings.isNullOrEmpty(userid)) {
			Predicate pUserid = cb.equal(root.get("userId"), userid);
			predicateList.add(pUserid);
		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Instance> q = em.createQuery(cq);

		List<Instance> rows = q.getResultList();
		QuotaSet usage = new QuotaSet();
		usage.setInstances(rows.size());
		usage.setRam(0);
		usage.setCores(0);
		for (Instance row : rows) {
			InstanceType instanceType = instanceTypeHome.flavorGetByFlavorId(context, row.getFlavorId(), "no");
			if (instanceType != null) {
				usage.setRam(usage.getRam() + instanceType.getMemoryMb());
				usage.setCores(usage.getCores() + instanceType.getVcpus());
			}
		}

		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("instances", usage.getInstances());
		result.put("ram", usage.getRam());
		result.put("cores", usage.getCores());
		return result;
	}

	private QuotaUsage getUsage(DbQuotaUsageSet userUsages, String resource) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object obj = getObject(userUsages, resource);
		return obj == null ? null : (QuotaUsage) obj;
	}

	private void replace(DbQuotaUsageSet userUsages, String resource, QuotaUsage usage) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name = StringUtils.getMethodName(resource);
		Class<?>[] param = new Class<?>[1];
		param[0] = QuotaUsage.class;
		Method setMethod = userUsages.getClass().getMethod("set" + name, param);
		setMethod.invoke(userUsages, usage);
	}

	private QuotaUsage quotaUsageCreate(NovaRequestContext elevated, String projectid, String userid, String resource,
			int inUse, int reserved, int untilRefresh) {
		QuotaUsage usage = new QuotaUsage();
		usage.setProjectId(projectid);
		usage.setUserId(userid);
		usage.setResource(resource);
		usage.setInUse(inUse);
		usage.setReserved(reserved);
		usage.setUntilRefresh(untilRefresh);
		usage.setUpdatedAt(Calendar.getInstance());
		EntityManager em = getEntityManager();
		em.persist(usage);
		return usage;
	}

	private boolean contains(String resource, DbQuotaUsageSet usages) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (Strings.isNullOrEmpty(resource)) {
			return false;
		}
		Object obj = getObject(usages, resource);
		return obj != null;
	}

	private Entry<QuotaUsageSet, DbQuotaUsageSet> getProjectUserQuotaUsages(NovaRequestContext context, String projectid,
			String userid) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaUsage> cq = cb.createQuery(QuotaUsage.class);
		Root<QuotaUsage> root = cq.from(QuotaUsage.class);
		cq.select(root);
		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaUsage> q = em.createQuery(cq);
		q.setLockMode(LockModeType.WRITE);
		List<QuotaUsage> rows = q.getResultList();

		QuotaUsageSet projResult = new QuotaUsageSet();
		DbQuotaUsageSet userResult = new DbQuotaUsageSet();

		for (QuotaUsage row : rows) {
			String resource = row.getResource();
			String name = StringUtils.getMethodName(resource);
			int inUse = row.getInUse() == null ? 0 : row.getInUse();
			int reserved = row.getReserved() == null ? 0 : row.getReserved();
			int total = inUse + reserved;
			Usage usage = new Usage();
			usage.setInUse(inUse);
			usage.setReserved(reserved);
			usage.setTotal(total);

			Class<?>[] param = new Class<?>[1];
			param[0] = QuotaUsage.class;
			Method setMethod = projResult.getClass().getMethod("set" + name, param);
			setMethod.invoke(projResult, usage);

			if (Strings.isNullOrEmpty(row.getUserId()) || row.getUserId().equals(userid)) {
				param = new Class<?>[1];
				param[0] = QuotaUsage.class;
				setMethod = userResult.getClass().getMethod("set" + name, param);
				setMethod.invoke(userResult, row);
			}
		}

		return Maps.immutableEntry(projResult, userResult);
	}

	@Override
	public void reservationCommit(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws Exception {
		Entry<QuotaUsageSet, DbQuotaUsageSet> entry = getProjectUserQuotaUsages(context, projectid, userid);
		DbQuotaUsageSet userUsage = entry.getValue();

		List<Reservation> reservationQuery = quotaReservationsQuery(context, reservations);

		EntityManager em = getEntityManager();
		for (Reservation reservation : reservationQuery) {
			QuotaUsage usage = getUsage(userUsage, reservation.getResource());
			if (reservation.getDelta() >= 0) {
				usage.setReserved(usage.getReserved() - reservation.getDelta());
			}
			usage.setInUse(usage.getInUse() + reservation.getDelta());

			// reservation.setDeleted(reservation.getId());
			reservation.setDeletedAt(Calendar.getInstance());
			em.merge(reservation);
		}
	}

	private List<Reservation> quotaReservationsQuery(NovaRequestContext context, List<String> reservations) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> root = cq.from(Reservation.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pUUID = root.get("uuid").in(reservations);
		predicateList.add(pUUID);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Reservation> q = em.createQuery(cq);
		q.setLockMode(LockModeType.WRITE);
		List<Reservation> rows = q.getResultList();
		return rows;
	}

	@Override
	public void reservationRollback(NovaRequestContext context, List<String> reservations, String projectid, String userid)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Entry<QuotaUsageSet, DbQuotaUsageSet> entry = getProjectUserQuotaUsages(context, projectid, userid);
		DbQuotaUsageSet userUsage = entry.getValue();

		List<Reservation> reservationQuery = quotaReservationsQuery(context, reservations);

		EntityManager em = getEntityManager();
		for (Reservation reservation : reservationQuery) {
			QuotaUsage usage = getUsage(userUsage, reservation.getResource());
			if (reservation.getDelta() >= 0) {
				usage.setReserved(usage.getReserved() - reservation.getDelta());
			}

			// reservation.setDeleted(reservation.getId());
			reservation.setDeletedAt(Calendar.getInstance());
			em.merge(reservation);
		}

	}

	@Override
	public void quotaUsageUpdate(NovaRequestContext context, String projectid, String userid, String resource,
			Integer inUse, Integer reserved, Integer untilRefresh) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaUsage> cq = cb.createQuery(QuotaUsage.class);
		Root<QuotaUsage> root = cq.from(QuotaUsage.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pResource = cb.equal(root.get("resource"), projectid);
		predicateList.add(pResource);

		Predicate pUserid = cb.equal(root.get("userId"), userid);
		Predicate pNull = cb.isNull(root.get("userId"));
		Predicate pOr = cb.or(pUserid, pNull);
		predicateList.add(pOr);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaUsage> q = em.createQuery(cq);
		List<QuotaUsage> rows = q.getResultList();

		if (rows.isEmpty()) {
			String msg = String.format("Quota usage for project %s could not be found.", projectid);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(msg).build());
		}

		for (QuotaUsage row : rows) {
			if (inUse != null) {
				row.setInUse(inUse);
			}
			if (reserved != null) {
				row.setReserved(reserved);
			}
			if (untilRefresh != null) {
				row.setUntilRefresh(untilRefresh);
			}
			em.merge(row);
		}

	}

	@Override
	public void quotaDestroyAllByProjectAndUser(NovaRequestContext context, String projectid, String userid) {
		quotaDestroyAllProjectUserQuotaByProjectAndUser(context, projectid, userid);
		quotaDestroyAllQuotaUsageByProjectAndUser(context, projectid, userid);
		quotaDestroyAllReservationByProjectAndUser(context, projectid, userid);
	}

	private void quotaDestroyAllReservationByProjectAndUser(NovaRequestContext context, String projectid, String userid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> root = cq.from(Reservation.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pUserid = cb.equal(root.get("userId"), userid);
		predicateList.add(pUserid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Reservation> q = em.createQuery(cq);
		List<Reservation> reservations = q.getResultList();
		for (Reservation reservation : reservations) {
			// reservation.setDeleted(reservation.getId());
			reservation.setDeletedAt(Calendar.getInstance());
			em.merge(reservation);
		}
	}

	private void quotaDestroyAllQuotaUsageByProjectAndUser(NovaRequestContext context, String projectid, String userid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaUsage> cq = cb.createQuery(QuotaUsage.class);
		Root<QuotaUsage> root = cq.from(QuotaUsage.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pUserid = cb.equal(root.get("userId"), userid);
		predicateList.add(pUserid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaUsage> q = em.createQuery(cq);
		List<QuotaUsage> quotaUsages = q.getResultList();
		for (QuotaUsage quota : quotaUsages) {
			// quota.setDeleted(quota.getId());
			quota.setDeletedAt(Calendar.getInstance());
			em.merge(quota);
		}
	}

	private void
			quotaDestroyAllProjectUserQuotaByProjectAndUser(NovaRequestContext context, String projectid, String userid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProjectUserQuota> cq = cb.createQuery(ProjectUserQuota.class);
		Root<ProjectUserQuota> root = cq.from(ProjectUserQuota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);
		Predicate pUserid = cb.equal(root.get("userId"), userid);
		predicateList.add(pUserid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<ProjectUserQuota> q = em.createQuery(cq);
		List<ProjectUserQuota> projectUserQuotas = q.getResultList();
		for (ProjectUserQuota quota : projectUserQuotas) {
			// quota.setDeleted(quota.getId());
			quota.setDeletedAt(Calendar.getInstance());
			em.merge(quota);
		}
	}

	@Override
	public void quotaDestroyAllByProject(NovaRequestContext context, String projectid) {
		quotaDestroyAllQuotaByProject(context, projectid);
		quotaDestroyAllProjectUserQuotaByProject(context, projectid);
		quotaDestroyAllQuotaUsageByProject(context, projectid);
		quotaDestroyAllReservationByProject(context, projectid);
	}

	private void quotaDestroyAllReservationByProject(NovaRequestContext context, String projectid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> root = cq.from(Reservation.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Reservation> q = em.createQuery(cq);
		List<Reservation> reservations = q.getResultList();
		for (Reservation reservation : reservations) {
			// reservation.setDeleted(reservation.getId());
			reservation.setDeletedAt(Calendar.getInstance());
			em.merge(reservation);
		}
	}

	private void quotaDestroyAllQuotaUsageByProject(NovaRequestContext context, String projectid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QuotaUsage> cq = cb.createQuery(QuotaUsage.class);
		Root<QuotaUsage> root = cq.from(QuotaUsage.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<QuotaUsage> q = em.createQuery(cq);
		List<QuotaUsage> quotaUsages = q.getResultList();
		for (QuotaUsage quota : quotaUsages) {
			// quota.setDeleted(quota.getId());
			quota.setDeletedAt(Calendar.getInstance());
			em.merge(quota);
		}
	}

	private void quotaDestroyAllProjectUserQuotaByProject(NovaRequestContext context, String projectid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProjectUserQuota> cq = cb.createQuery(ProjectUserQuota.class);
		Root<ProjectUserQuota> root = cq.from(ProjectUserQuota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<ProjectUserQuota> q = em.createQuery(cq);
		List<ProjectUserQuota> projectUserQuotas = q.getResultList();
		for (ProjectUserQuota quota : projectUserQuotas) {
			// quota.setDeleted(quota.getId());
			quota.setDeletedAt(Calendar.getInstance());
			em.merge(quota);
		}
	}

	private void quotaDestroyAllQuotaByProject(NovaRequestContext context, String projectid) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Quota> cq = cb.createQuery(Quota.class);
		Root<Quota> root = cq.from(Quota.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Predicate pProjectid = cb.equal(root.get("projectId"), projectid);
		predicateList.add(pProjectid);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Quota> q = em.createQuery(cq);
		List<Quota> quotas = q.getResultList();
		for (Quota quota : quotas) {
			// quota.setDeleted(quota.getId());
			quota.setDeletedAt(Calendar.getInstance());
			em.merge(quota);
		}
	}

	@Override
	public void reservationExpire(NovaRequestContext context) {
		Calendar now = Calendar.getInstance();
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
		Root<Reservation> root = cq.from(Reservation.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		Path<Calendar> expire = root.get("expire");
		Predicate pCalendar = cb.lessThan(expire, now);
		predicateList.add(pCalendar);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Reservation> q = em.createQuery(cq);
		List<Reservation> reservations = q.getResultList();
		for (Reservation reservation : reservations) {
			if (reservation.getDelta() >= 0) {
				reservation.getUsage().setReserved(reservation.getUsage().getReserved() - reservation.getDelta());
			}
			// reservation.setDeleted(reservation.getId());
			reservation.setDeletedAt(Calendar.getInstance());
			em.merge(reservation);
		}
	}
}
