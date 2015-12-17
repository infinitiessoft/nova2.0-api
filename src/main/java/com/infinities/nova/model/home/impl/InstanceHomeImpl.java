package com.infinities.nova.model.home.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.exception.InstanceNotFoundException;
import com.infinities.nova.api.exception.InvalidIDException;
import com.infinities.nova.api.openstack.compute.servers.ServersFilter;
import com.infinities.nova.db.model.Instance;
import com.infinities.nova.model.home.InstanceHome;

public class InstanceHomeImpl extends AbstractHome implements InstanceHome {

	private final static Map<String, String> SORT_KEY_MAP;
	static {
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("user_id", "userId");
		keyMap.put("project_id", "projectId");
		keyMap.put("image_ref", "imageRef");
		keyMap.put("kernel_id", "kernelId");
		keyMap.put("ramdisk_id", "ramdiskId");
		keyMap.put("launch_index", "launchIndex");
		keyMap.put("key_name", "keyName");
		keyMap.put("key_data", "keyData");
		keyMap.put("power_state", "powerState");
		keyMap.put("vm_state", "vmState");
		keyMap.put("task_state", "taskState");
		keyMap.put("memory_mb", "memoryMb");
		keyMap.put("root_gb", "rootGb");
		keyMap.put("ephemeral_gb", "ephemeralGb");
		keyMap.put("ephemeral_key_uuid", "ephemeralKeyUuid");
		keyMap.put("instance_type_id", "instanceTypeId");
		keyMap.put("user_data", "userData");
		keyMap.put("reservation_id", "reservationId");
		keyMap.put("scheduled_at", "scheduledAt");
		keyMap.put("launched_at", "launchedAt");
		keyMap.put("terminated_at", "terminatedAt");
		keyMap.put("availability_zone", "availabilityZone");
		keyMap.put("display_name", "displayName");
		keyMap.put("display_description", "displayDescription");
		keyMap.put("launched_on", "launchedOn");
		keyMap.put("os_type", "osType");
		keyMap.put("locked_by", "lockedBy");
		keyMap.put("vm_mode", "vmMode");
		keyMap.put("root_device_name", "rootDeviceName");
		keyMap.put("default_ephemeral_device", "defaultEphemeralDevice");
		keyMap.put("default_swap_device", "defaultSwapDevice");
		keyMap.put("config_drive", "configDrive");
		keyMap.put("access_ip_v4", "accessIpV4");
		keyMap.put("access_ip_v6", "accessIpV6");
		keyMap.put("internal_id", "internalId");
		keyMap.put("created_at", "createdAt");
		keyMap.put("updated_at", "updatedAt");
		keyMap.put("deleted_at", "deletedAt");
		keyMap.put("security_groups", "securityGroups");
		SORT_KEY_MAP = Collections.unmodifiableMap(keyMap);
	}


	@Override
	public List<Instance> instanceGetAllByFilters(NovaRequestContext context, ServersFilter filters, String sortKey,
			String sortDir, Integer limit, String marker, List<String> columnsToJoin) {

		if (limit == 0) {
			return new ArrayList<Instance>();
		}

		if (columnsToJoin == null) {
			columnsToJoin = new ArrayList<String>();
			columnsToJoin.add("securityGroups");
			columnsToJoin.add("metadata");
		}

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Instance> cq = cb.createQuery(Instance.class);
		Root<Instance> root = cq.from(Instance.class);
		cq.select(root);
		cq.distinct(true);

		for (String column : columnsToJoin) {
			String c = column;
			if (SORT_KEY_MAP.containsKey(column)) {
				c = SORT_KEY_MAP.get(column);
			}
			root.fetch(c, JoinType.LEFT);
		}

		List<Order> orders = new ArrayList<Order>();
		if ("asc".equals(sortDir)) {
			if (SORT_KEY_MAP.containsKey(sortKey)) {
				sortKey = SORT_KEY_MAP.get(sortKey);
			}
			Order order = cb.asc(root.get(sortKey));
			orders.add(order);
		} else if ("desc".equals(sortDir)) {
			if (SORT_KEY_MAP.containsKey(sortKey)) {
				sortKey = SORT_KEY_MAP.get(sortKey);
			}
			Order order = cb.asc(root.get(sortKey));
			orders.add(order);
		}

		List<Predicate> predicateList = new ArrayList<Predicate>();

		if (filters.getChangesSince() != null) {
			predicateList.add(cb.greaterThanOrEqualTo(root.<Calendar> get("updatedAt"), filters.getChangesSince()));
		}

		if (!context.getIsAdmin()) {
			if (!Strings.isNullOrEmpty(context.getProjectId())) {
				filters.setProjectId(context.getProjectId());
			} else {
				filters.setUserId(context.getUserId());
			}
		}

		exactFilter(cq, cb, root, predicateList, filters);
		regexFilter(cb, root, predicateList, filters);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Instance> q = em.createQuery(cq);
		List<Instance> instances = q.getResultList();
		return instancesFillMetadata(context, instances);
	}

	// private List<String> manualJoinColumns(List<String> columnsToJoin) {
	// List<String> manualJoins = new ArrayList<String>();
	// for (String column : new String[] { "metadata", "system_metadata",
	// "pci_devices" }) {
	// if (columnsToJoin.contains(column)) {
	// columnsToJoin.remove(column);
	// manualJoins.add(column);
	// }
	// }
	// return manualJoins;
	// }

	private void exactFilter(CriteriaQuery<Instance> cq, CriteriaBuilder cb, Root<Instance> root,
			List<Predicate> predicateList, ServersFilter filters) {

		// if (!Strings.isNullOrEmpty(filters.getProjectId())) {
		// predicateList.add(cb.equal(root.get("projectId"),
		// filters.getProjectId()));
		// }
		//
		// if (!Strings.isNullOrEmpty(filters.getUserId())) {
		// predicateList.add(cb.equal(root.get("userId"), filters.getUserId()));
		// }

		if (!Strings.isNullOrEmpty(filters.getImage())) {
			predicateList.add(cb.equal(root.get("imageId"), filters.getImage()));
		}

		if (filters.getVmState() != null && !filters.getVmState().isEmpty()) {
			predicateList.add(root.<String> get("vmState").in(filters.getVmState()));
		}

		// if (filters.getInstanceTypeId() != null) {
		// predicateList.add(cb.equal(root.get("instanceTypeId"),
		// filters.getInstanceTypeId()));
		// }

		if (filters.getUuids() != null) {
			predicateList.add(root.<String> get("instanceId").in(filters.getUuids()));
		}

		if (!Strings.isNullOrEmpty(filters.getHost())) {
			predicateList.add(cb.equal(root.get("host"), filters.getHost()));
		}

		if (filters.getTaskState() != null && !filters.getTaskState().isEmpty()) {
			predicateList.add(root.<String> get("taskState").in(filters.getTaskState()));
		}

		if (filters.getMetadata() != null && !filters.getMetadata().isEmpty()) {
			for (Entry<String, String> entry : filters.getMetadata().entrySet()) {
				Subquery<Instance> subquery = cq.subquery(Instance.class);
				MapJoin<Instance, String, String> metadataRoot = subquery.correlate(root).joinMap("metadata");
				Predicate keyPredicate = cb.equal(metadataRoot.get("key"), entry.getKey());
				Predicate valuePredicate = cb.equal(metadataRoot.get("value"), entry.getValue());
				subquery.where(keyPredicate, valuePredicate);
				predicateList.add(cb.any(subquery).isNotNull());
			}
		}
	}

	private void regexFilter(CriteriaBuilder cb, Root<Instance> root, List<Predicate> predicateList, ServersFilter filters) {
		if (!Strings.isNullOrEmpty(filters.getReservationId())) {
			predicateList.add(cb.like(root.<String> get("reservationId"), "%" + filters.getReservationId() + "%"));
		}

		if (!Strings.isNullOrEmpty(filters.getDisplayName())) {
			predicateList.add(cb.like(root.<String> get("displayName"), "%" + filters.getDisplayName() + "%"));
		}
	}

	private List<Instance> instancesFillMetadata(NovaRequestContext context, List<Instance> instances) {
		// jpa will join all onetomany relationship automately
		return instances;
	}

	@Override
	public Instance instanceGetByUuid(NovaRequestContext context, String uuid, List<String> columnsToJoin) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Instance> cq = cb.createQuery(Instance.class);
		Root<Instance> root = cq.from(Instance.class);
		root.fetch("securityGroups", JoinType.LEFT);// .fetch("rules",
													// JoinType.LEFT);
		for (String column : columnsToJoin) {
			if ("security_groups".equals(column)) {
				continue;
			}
			String key = column;
			if (SORT_KEY_MAP.containsKey(column)) {
				key = SORT_KEY_MAP.get(column);
			}
			root.fetch(key, JoinType.LEFT);
		}

		cq.select(root);
		List<Predicate> predicateList = new ArrayList<Predicate>();// modelQuery(context,
																	// null,
																	// "true",
																	// cb,
																	// root);
		predicateList.add(cb.equal(root.get("instanceId"), uuid));
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Instance> q = em.createQuery(cq);
		try {
			Instance instance = q.getSingleResult();
			return instance;
		} catch (NoResultException e) {
			throw new InstanceNotFoundException(null, uuid);
		}
	}

	@Override
	public Instance instanceGet(NovaRequestContext context, long id) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Instance> cq = cb.createQuery(Instance.class);
		Root<Instance> root = cq.from(Instance.class);
		cq.select(root);
		List<Predicate> predicateList = modelQuery(context, null, "true", cb, root);
		predicateList.add(cb.equal(root.<Long> get("id"), id));
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Instance> q = em.createQuery(cq);
		try {
			Instance instance = q.getSingleResult();
			return instance;
		} catch (Exception e) {
			throw new InvalidIDException(null, id);
		}
	}
}
