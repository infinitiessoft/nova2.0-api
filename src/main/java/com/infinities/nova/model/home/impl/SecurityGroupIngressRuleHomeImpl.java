package com.infinities.nova.model.home.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.SecurityGroupIngressRule;
import com.infinities.nova.model.home.SecurityGroupIngressRuleHome;

public class SecurityGroupIngressRuleHomeImpl extends AbstractHome implements SecurityGroupIngressRuleHome {

	@Override
	public Long getSecurityGroupRuleCountByGroup(NovaRequestContext context, String securityGroupId) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<SecurityGroupIngressRule> root = cq.from(SecurityGroupIngressRule.class);
		cq.select(cb.count(root));

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

}
