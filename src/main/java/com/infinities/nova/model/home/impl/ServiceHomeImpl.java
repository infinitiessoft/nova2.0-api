package com.infinities.nova.model.home.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.Service;
import com.infinities.nova.model.home.ServiceHome;

public class ServiceHomeImpl extends AbstractHome implements ServiceHome {

	@Override
	public List<Service> serviceGetAll(NovaRequestContext context, Boolean disabled) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Service> cq = cb.createQuery(Service.class);
		Root<Service> root = cq.from(Service.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);

		if (disabled != null) {
			predicateList.add(cb.equal(root.get("disabled"), disabled));
		}

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Service> q = em.createQuery(cq);
		return q.getResultList();
	}

}
