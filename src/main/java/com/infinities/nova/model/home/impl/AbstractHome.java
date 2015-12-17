package com.infinities.nova.model.home.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.skyport.jpa.AbstractJpaHome;
import com.infinities.skyport.jpa.EntityManagerHelper;

public abstract class AbstractHome {

	protected transient EntityManager entityManager; // testing
	private static final Logger logger = LoggerFactory.getLogger(AbstractJpaHome.class);


	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
		if (this.entityManager != null) {
			return entityManager;
		}

		EntityManager em = EntityManagerHelper.getEntityManager();
		if (!em.getTransaction().isActive()) {
			try {
				EntityManagerHelper.beginTransaction();
			} catch (RuntimeException e) {
				EntityManagerHelper.closeEntityManager();
				logger.warn("transaction is closed unexpected", e);
			}
		}
		logger.debug("EntityManager: {} in {}", new Object[] { em.toString(), this.getClass().getSimpleName() });

		return em;
	}

	// readDeleted=null, projectOnly=false
	protected List<Predicate> modelQuery(NovaRequestContext context, String readDeleted, String projectOnly,
			CriteriaBuilder cb, Root<?> root) {
		List<Predicate> predicates = Lists.newArrayList();

		if (Strings.isNullOrEmpty(readDeleted)) {
			readDeleted = context.getReadDeleted();
		}
		int defaultDeletedValue = 0;

		if ("no".equals(readDeleted)) {
			Predicate pdeleted = cb.equal(root.get("deleted"), defaultDeletedValue);
			predicates.add(pdeleted);
		} else if ("yes".equals(readDeleted)) {
			// pass
		} else if ("only".equals(readDeleted)) {
			Predicate pdeleted = cb.notEqual(root.get("deleted"), defaultDeletedValue);
			predicates.add(pdeleted);
		} else {
			throw new RuntimeException("Unrecognized readDeleted value: " + readDeleted);
		}

		if (NovaRequestContext.isUserContext(context) && !Strings.isNullOrEmpty(projectOnly)) {
			if ("allow_none".equals(projectOnly)) {
				Predicate pProjectid = cb.equal(root.get("projectId"), context.getProjectId());
				Predicate pNull = cb.isNull(root.get("projectId"));
				Predicate pOr = cb.or(pProjectid, pNull);
				predicates.add(pOr);
			} else {
				Predicate pProjectid = cb.equal(root.get("projectId"), context.getProjectId());
				predicates.add(pProjectid);
			}
		}

		return predicates;
	}
}
