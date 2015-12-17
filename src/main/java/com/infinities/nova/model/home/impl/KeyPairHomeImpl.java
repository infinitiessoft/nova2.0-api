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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.model.home.KeyPairHome;
import com.infinities.nova.response.model.KeyPair;

public class KeyPairHomeImpl extends AbstractHome implements KeyPairHome {

	@Override
	public Long getKeyPairCountByUser(NovaRequestContext context, String userid) {
		NovaRequestContext.authorizeUserContext(context, userid);
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<KeyPair> root = cq.from(KeyPair.class);
		cq.select(cb.count(root));

		List<Predicate> predicateList = modelQuery(context, null, null, cb, root);
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

}
