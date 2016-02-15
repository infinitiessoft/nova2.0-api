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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.db.model.InstanceType;
import com.infinities.nova.exception.FlavorNotFoundByNameException;
import com.infinities.nova.exception.FlavorNotFoundException;
import com.infinities.nova.flavors.controller.FlavorsFilter;
import com.infinities.nova.model.home.InstanceTypeHome;

public class InstanceTypeHomeImpl extends AbstractHome implements InstanceTypeHome {

	private final static Logger logger = LoggerFactory.getLogger(InstanceTypeHomeImpl.class);

	private final static Map<String, String> sortKeyMap;

	static {
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("memory_mb", "memoryMb");
		keyMap.put("root_gb", "rootGb");
		keyMap.put("ephemeral_gb", "ephemeralGb");
		keyMap.put("rxtx_factor", "rxtxFactor");
		keyMap.put("vcpu_weight", "vcpuWeight");
		keyMap.put("is_public", "isPublic");
		keyMap.put("created_at", "createdAt");
		keyMap.put("updated_at", "updatedAt");
		keyMap.put("deleted_at", "deletedAt");

		sortKeyMap = Collections.unmodifiableMap(keyMap);
	}


	// inactive=false,filter=null,sortKey=flavorid,sortDir=asc,limit=null,marker=null;
	@Override
	public List<InstanceType> flavorGetAll(NovaRequestContext context, boolean inactive, FlavorsFilter filter,
			String sortKey, String sortDir, Integer limit, String marker) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (Strings.isNullOrEmpty(sortKey)) {
			sortKey = "flavorid";
		}
		if (Strings.isNullOrEmpty(sortDir)) {
			sortDir = "asc";
		}

		String readDeleted = "no";
		if (inactive) {
			readDeleted = "yes";
		}

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InstanceType> cq = cb.createQuery(InstanceType.class);
		Root<InstanceType> root = cq.from(InstanceType.class);
		cq.select(root);

		List<Predicate> predicateList = flavorGetQuery(context, readDeleted, cb, root, cq);
		if (filter.getMinMemoryMb() != null) {
			Expression<Number> exp = root.get("memoryMb");
			predicateList.add(cb.ge(exp, filter.getMinMemoryMb()));
		}

		if (filter.getMinRootGb() != null) {
			Expression<Number> exp = root.get("rootGb");
			predicateList.add(cb.ge(exp, filter.getMinRootGb()));
		}

		if (filter.getDisabled() != null) {
			Expression<Boolean> exp = root.get("disabled");
			predicateList.add(cb.equal(exp, filter.getDisabled()));
		}

		if (filter.getIsPublic() != null) {
			logger.debug("isPublic? {}", filter.getIsPublic());
			Path<Boolean> path = root.get("isPublic");
			Predicate pIsPublic = cb.equal(path, filter.getIsPublic());
			predicateList.add(pIsPublic);
		}

		InstanceType markerRow = null;
		if (!Strings.isNullOrEmpty(marker)) {
			EntityManager em2 = getEntityManager();
			CriteriaBuilder cb2 = em2.getCriteriaBuilder();
			CriteriaQuery<InstanceType> cq2 = cb2.createQuery(InstanceType.class);
			Root<InstanceType> root2 = cq2.from(InstanceType.class);
			cq2.select(root2);
			List<Predicate> predicateList2 = flavorGetQuery(context, readDeleted, cb2, root2, cq2);
			predicateList2.add(cb2.equal(root2.get("flavorid"), marker));
			cq2.where(predicateList2.toArray(new Predicate[predicateList2.size()]));
			TypedQuery<InstanceType> q2 = em2.createQuery(cq2);
			markerRow = q2.getSingleResult();
			if (markerRow == null) {
				String msg = String.format("Marker %s could not be found.", marker);
				throw new IllegalStateException(msg);
			}
			logger.debug("marker flavorid: {}", markerRow.getFlavorid());
		}

		List<String> sortKeys = new ArrayList<String>();
		sortKeys.add(sortKey);
		sortKeys.add("id");
		TypedQuery<InstanceType> q = paginateQuery(cq, cb, root, predicateList, limit, sortKeys, markerRow, sortDir, null);
		List<InstanceType> result = q.getResultList();

		for (InstanceType r : result) {
			System.err.println(r.getId() + "  " + r.getFlavorid());
		}

		return result;
	}

	// marker=null, sortDir=null, sortDirs=null
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TypedQuery<InstanceType> paginateQuery(CriteriaQuery<InstanceType> cq, CriteriaBuilder cb,
			Root<InstanceType> root, List<Predicate> predicateList, Integer limit, List<String> sortKeys,
			InstanceType marker, String sortDir, List<String> sortDirs) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		EntityManager em = getEntityManager();

		if (!sortKeys.contains("id")) {
			logger.warn("Id not in sortKeys; is sortKeys unique?");
		}

		if (!Strings.isNullOrEmpty(sortDir) && sortDirs != null) {
			throw new IllegalArgumentException();
		}

		if (Strings.isNullOrEmpty(sortDir) && sortDirs == null) {
			sortDir = "asc";
		}

		if (sortDirs == null) {
			sortDirs = new ArrayList<String>();
			for (int i = 0; i < sortKeys.size(); i++) {
				sortDirs.add(sortDir);
			}
		}

		if (sortDirs.size() != sortKeys.size()) {
			throw new IllegalArgumentException();
		}

		List<Order> orders = new ArrayList<Order>();
		for (int i = 0; i < sortKeys.size(); i++) {
			String currentSortKey = sortKeys.get(i);
			if (sortKeyMap.containsKey(currentSortKey)) {
				currentSortKey = sortKeyMap.get(currentSortKey);
			}
			String currentSortDir = sortDirs.get(i);
			if ("asc".equals(currentSortDir)) {
				Order order = cb.asc(root.get(currentSortKey));
				orders.add(order);
			} else {
				Order order = cb.desc(root.get(currentSortKey));
				orders.add(order);
			}
		}
		cq.orderBy(orders);

		if (marker != null) {
			List<Comparable> markerValues = new ArrayList<Comparable>();
			for (String key : sortKeys) {
				markerValues.add(getValue(marker, key));
			}

			List<Predicate> criteriaList = new ArrayList<Predicate>();
			for (int i = 0; i < sortKeys.size(); i++) {
				List<Predicate> critAttrs = new ArrayList<Predicate>();
				for (int j = 0; j < i; j++) {
					logger.debug("key: {} == value: {}", new Object[] { sortKeys.get(j), markerValues.get(j) });
					Predicate predicate = cb.equal(root.get(sortKeys.get(j)), markerValues.get(j));
					critAttrs.add(predicate);
				}

				String modelAttr = sortKeys.get(i);
				if ("desc".equals(sortDirs.get(i))) {
					logger.debug("key: {} < value: {}", new Object[] { modelAttr, markerValues.get(i) });
					Predicate predicate = cb.lessThan(root.<Comparable> get(modelAttr), markerValues.get(i));
					critAttrs.add(predicate);
				} else {
					logger.debug("key: {} > value: {}", new Object[] { modelAttr, markerValues.get(i) });
					Predicate predicate = cb.greaterThan(root.<Comparable> get(modelAttr), markerValues.get(i));
					critAttrs.add(predicate);
				}
				Predicate predicate = cb.and(critAttrs.toArray(new Predicate[critAttrs.size()]));
				criteriaList.add(predicate);
			}
			Predicate predicate = cb.or(criteriaList.toArray(new Predicate[criteriaList.size()]));
			predicateList.add(predicate);
		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<InstanceType> q = em.createQuery(cq);
		if (limit != null) {
			q.setMaxResults(limit);
		}

		return q;
	}

	@SuppressWarnings("rawtypes")
	private Comparable getValue(InstanceType marker, String attr) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method m = marker.getClass().getMethod(concentrateMethodName(attr));
		Comparable obj = (Comparable) m.invoke(marker);

		return obj;
	}

	private String concentrateMethodName(String orig) {
		if (orig.contains("_")) {
			String[] split = orig.split("_");
			orig = "";
			for (String s : split) {
				orig += Character.toUpperCase(s.charAt(0)) + s.substring(1);
			}
		} else {
			orig = Character.toUpperCase(orig.charAt(0)) + orig.substring(1);
		}
		return "get" + orig;
	}

	// readDeleted=null
	public List<Predicate> flavorGetQuery(NovaRequestContext context, String readDeleted, CriteriaBuilder cb,
			Root<InstanceType> root, CriteriaQuery<InstanceType> cq) {
		List<Predicate> predicateList = modelQuery(context, readDeleted, null, cb, root);
		logger.debug("context is admin? {}", context.getIsAdmin());
		if (!context.getIsAdmin()) {
			Path<Boolean> path = root.get("isPublic");
			Predicate pIsPublic = cb.isTrue(path);
			predicateList.add(pIsPublic);
		}
		return predicateList;
	}

	@Override
	public InstanceType flavorGetByFlavorId(NovaRequestContext context, String flavorid, String readDeleted) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InstanceType> cq = cb.createQuery(InstanceType.class);
		Root<InstanceType> root = cq.from(InstanceType.class);
		cq.select(root);

		List<Predicate> predicateList = flavorGetQuery(context, readDeleted, cb, root, cq);

		Expression<String> exp = root.get("flavorid");
		predicateList.add(cb.equal(exp, flavorid));
		Order orderDeleted = cb.asc(root.get("deleted"));
		Order orderId = cb.asc(root.get("id"));
		cq.orderBy(orderDeleted, orderId);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<InstanceType> q = em.createQuery(cq);

		InstanceType result = q.getSingleResult();

		if (result == null) {
			throw new FlavorNotFoundException(null, flavorid);
		}

		return result;
	}

	@Override
	public InstanceType flavorGetByName(NovaRequestContext context, String name) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<InstanceType> cq = cb.createQuery(InstanceType.class);
		Root<InstanceType> root = cq.from(InstanceType.class);
		cq.select(root);

		List<Predicate> predicateList = flavorGetQuery(context, null, cb, root, cq);

		Expression<String> exp = root.get("name");
		predicateList.add(cb.equal(exp, name));
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<InstanceType> q = em.createQuery(cq);

		InstanceType result = q.getSingleResult();

		if (result == null) {
			throw new FlavorNotFoundByNameException(null, name);
		}

		return result;
	}
}
