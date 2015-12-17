package com.infinities.nova.model.home.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.exception.ImageNotFoundException;
import com.infinities.nova.api.openstack.Common.PaginationParams;
import com.infinities.nova.api.openstack.compute.images.ImagesFilter;
import com.infinities.nova.db.model.Image;
import com.infinities.nova.model.home.ImageHome;

public class ImageHomeImpl extends AbstractHome implements ImageHome {

	private final static Logger logger = LoggerFactory.getLogger(ImageHomeImpl.class);
	// private final static Set<String> STATUSES;
	private final static Map<String, String> SORT_KEY_MAP;

	// static {
	// Set<String> statuses = new HashSet<String>();
	// statuses.add("active");
	// statuses.add("saving");
	// statuses.add("queued");
	// statuses.add("killed");
	// statuses.add("pending_delete");
	// statuses.add("deleted");
	// STATUSES = Collections.unmodifiableSet(statuses);
	// }

	static {
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("min_ram", "minRam");
		keyMap.put("min_disk", "minDisk");
		keyMap.put("disk_format", "diskFormat");
		keyMap.put("container_format", "containerFormat");
		keyMap.put("virtual_size", "virtualSize");
		keyMap.put("is_public", "isPublic");
		keyMap.put("created_at", "createdAt");
		keyMap.put("updated_at", "updatedAt");
		keyMap.put("deleted_at", "deletedAt");
		SORT_KEY_MAP = Collections.unmodifiableMap(keyMap);
	}


	// filters=null,marker=null,limit=null,sortKey="createdAt",sortDir="desc",memberStatus="accepted",isPublic=null,adminAsUser=false,returnTag=false
	@Override
	public List<Image> imageGetAll(NovaRequestContext context, ImagesFilter filters, PaginationParams pageParams)
			throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		String sortKey = filters.getSortKey();
		if (Strings.isNullOrEmpty(sortKey)) {
			sortKey = "created_at";
		}

		String sortDir = filters.getSortDir();
		if (Strings.isNullOrEmpty(sortDir)) {
			sortDir = "desc";
		}

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		Root<Image> root = cq.from(Image.class);
		root.fetch("metadata", JoinType.LEFT);
		cq.select(root);
		cq.distinct(true);

		List<Predicate> predicateList = imageGetQuery(context, cb, root, cq);
		Integer minRam = filters.getMinRam();
		if (minRam != null) {
			Expression<Number> exp = root.get("minRam");
			predicateList.add(cb.ge(exp, minRam));
		}

		Integer minDisk = filters.getMinDisk();
		if (minDisk != null) {
			Expression<Number> exp = root.get("minDisk");
			predicateList.add(cb.ge(exp, minDisk));
		}

		String name = filters.getName();
		if (name != null) {
			Expression<String> exp = root.get("name");
			predicateList.add(cb.equal(exp, name));
		}

		String server = filters.getServer();
		if (server != null) {
			Expression<String> exp = root.get("server");
			predicateList.add(cb.equal(exp, server));
		}

		String status = filters.getStatus();
		if (status != null) {
			Expression<String> exp = root.get("status");
			predicateList.add(cb.equal(exp, status));
		}

		String type = filters.getType();
		if (type != null) {
			Expression<String> exp = root.get("type");
			predicateList.add(cb.equal(exp, type));
		}

		Calendar changesSince = filters.getChangesSince();
		if (changesSince != null) {
			Expression<Calendar> exp = root.get("changesSince");
			predicateList.add(cb.greaterThanOrEqualTo(exp, changesSince));
		}

		Image markerRow = null;
		String marker = pageParams.getMarker();
		if (!Strings.isNullOrEmpty(marker)) {
			EntityManager em2 = getEntityManager();
			CriteriaBuilder cb2 = em2.getCriteriaBuilder();
			CriteriaQuery<Image> cq2 = cb2.createQuery(Image.class);
			Root<Image> root2 = cq2.from(Image.class);
			cq2.select(root2);
			List<Predicate> predicateList2 = imageGetQuery(context, cb2, root2, cq2);
			predicateList2.add(cb2.equal(root2.get("imageId"), marker));
			cq2.where(predicateList2.toArray(new Predicate[predicateList2.size()]));
			TypedQuery<Image> q2 = em2.createQuery(cq2);
			markerRow = q2.getSingleResult();
			if (markerRow == null) {
				String msg = String.format("Marker %s could not be found.", marker);
				throw new IllegalStateException(msg);
			}
			logger.debug("marker imageid: {}", markerRow.getImageId());
		}

		List<String> sortKeys = new ArrayList<String>();
		sortKeys.add(sortKey);
		sortKeys.add("id");
		Integer limit = pageParams.getLimit();
		TypedQuery<Image> q = paginateQuery(cq, cb, root, predicateList, limit, sortKeys, markerRow, sortDir, null);
		List<Image> result = q.getResultList();

		return result;
	}

	// readDeleted=null
	public List<Predicate> imageGetQuery(NovaRequestContext context, CriteriaBuilder cb, Root<Image> root,
			CriteriaQuery<Image> cq) {
		return new ArrayList<Predicate>();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TypedQuery<Image> paginateQuery(CriteriaQuery<Image> cq, CriteriaBuilder cb, Root<Image> root,
			List<Predicate> predicateList, Integer limit, List<String> sortKeys, Image marker, String sortDir,
			List<String> sortDirs) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
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
			if (SORT_KEY_MAP.containsKey(currentSortKey)) {
				currentSortKey = SORT_KEY_MAP.get(currentSortKey);
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
		TypedQuery<Image> q = em.createQuery(cq);
		if (limit != null) {
			q.setMaxResults(limit);
		}

		return q;
	}

	@SuppressWarnings("rawtypes")
	private Comparable getValue(Image marker, String attr) throws SecurityException, NoSuchMethodException,
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

	// showingDeleted = false
	@Override
	public Image imageGet(NovaRequestContext context, String imageId) {
		Image image = imageGetInner(context, imageId);
		return image;
	}

	// showingDeleted = false
	private Image imageGetInner(NovaRequestContext context, String imageId) {

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Image> cq = cb.createQuery(Image.class);
		Root<Image> root = cq.from(Image.class);
		root.fetch("metadata", JoinType.LEFT);
		cq.select(root);
		cq.where(cb.equal(root.get("imageId"), imageId));

		TypedQuery<Image> q = em.createQuery(cq);
		Image image = null;
		try {
			image = q.getSingleResult();
		} catch (NoResultException e) {
			String msg = String.format("No image found with ID %s", imageId);
			logger.debug(msg);
			throw new ImageNotFoundException(null, imageId);
		}
		return image;
	}

}
