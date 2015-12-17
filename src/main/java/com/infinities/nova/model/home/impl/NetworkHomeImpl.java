//package com.infinities.nova.model.home.impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import com.google.common.base.Strings;
//import com.infinities.nova.api.NovaRequestContext;
//import com.infinities.nova.api.exception.NetworkNotFoundException;
//import com.infinities.nova.api.exception.NetworkNotFoundForUUIDException;
//import com.infinities.nova.db.model.Network;
//import com.infinities.nova.model.home.NetworkHome;
//
//public class NetworkHomeImpl extends AbstractHome implements NetworkHome {
//
//	// projectOnly="allow_none"
//	@Override
//	public Network networkGet(NovaRequestContext context, Long networkId, String projectOnly) {
//		if (Strings.isNullOrEmpty(projectOnly)) {
//			projectOnly = "allow_none";
//		}
//		EntityManager em = getEntityManager();
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Network> cq = cb.createQuery(Network.class);
//		Root<Network> root = cq.from(Network.class);
//		cq.select(root);
//
//		List<Predicate> predicateList = modelQuery(context, null, projectOnly, cb, root);
//		predicateList.add(cb.equal(root.<Long> get("id"), networkId));
//
//		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
//		TypedQuery<Network> q = em.createQuery(cq);
//		try {
//			Network network = q.getSingleResult();
//			return network;
//		} catch (NoResultException e) {
//			throw new NetworkNotFoundException(String.valueOf(networkId));
//		}
//	}
//
//	@Override
//	public Network networkGetByUuid(NovaRequestContext context, String uuid) {
//		EntityManager em = getEntityManager();
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Network> cq = cb.createQuery(Network.class);
//		Root<Network> root = cq.from(Network.class);
//		cq.select(root);
//
//		List<Predicate> predicateList = modelQuery(context, "no", null, cb, root);
//		predicateList.add(cb.equal(root.<String> get("uuid"), uuid));
//
//		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
//		TypedQuery<Network> q = em.createQuery(cq);
//		try {
//			Network network = q.getSingleResult();
//			return network;
//		} catch (NoResultException e) {
//			throw new NetworkNotFoundForUUIDException(uuid);
//		}
//	}
//
//}
