package com.infinities.nova.model.home.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.FixedIp;
import com.infinities.nova.model.home.FixedIpHome;

public class FixedIpHomeImpl extends AbstractHome implements FixedIpHome {

	@Override
	public List<FixedIp> getByVirtualInterfaceId(NovaRequestContext context, Integer vifId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FixedIp> cq = cb.createQuery(FixedIp.class);
		Root<FixedIp> root = cq.from(FixedIp.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
		predicateList.add(cb.equal(root.<Integer> get("virtualIntetfaceId"), vifId));

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<FixedIp> q = em.createQuery(cq);
		return q.getResultList();
	}

}
