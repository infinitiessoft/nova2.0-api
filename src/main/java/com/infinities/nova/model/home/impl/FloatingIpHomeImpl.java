package com.infinities.nova.model.home.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.FloatingIp;
import com.infinities.nova.model.home.FloatingIpHome;

public class FloatingIpHomeImpl extends AbstractHome implements FloatingIpHome {

	@Override
	public List<FloatingIp> floatingIpGetByFixedAddress(NovaRequestContext context, String fixedIp) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FloatingIp> cq = cb.createQuery(FloatingIp.class);
		Root<FloatingIp> root = cq.from(FloatingIp.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		predicateList.add(cb.equal(root.get("fixedIp").get("address"), fixedIp));

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<FloatingIp> q = em.createQuery(cq);
		return q.getResultList();
	}

}
