package com.infinities.nova.model.home.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.VirtualInterface;
import com.infinities.nova.model.home.VirtualInterfaceHome;

public class VirtualInterfaceHomeImpl extends AbstractHome implements VirtualInterfaceHome {

	@Override
	public List<VirtualInterface> getAll(NovaRequestContext context) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<VirtualInterface> cq = cb.createQuery(VirtualInterface.class);
		Root<VirtualInterface> root = cq.from(VirtualInterface.class);
		cq.select(root);

		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);

		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<VirtualInterface> q = em.createQuery(cq);
		return q.getResultList();
	}

}
