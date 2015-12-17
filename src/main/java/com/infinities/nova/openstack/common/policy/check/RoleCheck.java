package com.infinities.nova.openstack.common.policy.check;

import java.util.Set;

import jersey.repackaged.com.google.common.collect.Sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class RoleCheck extends Check {

	private final static Logger logger = LoggerFactory.getLogger(RoleCheck.class);


	@Override
	public String getRule() {
		return "role";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {
		// TODO are roles from trust?
		Set<String> roles = Sets.newHashSet();
		for (String role : creds.getRoles()) {
			roles.add(role.toLowerCase());
		}
		return roles.contains(this.getMatch().toLowerCase());
	}

	@Override
	public Check newInstance(String kind, String match) {
		logger.debug("new role check: {}, {}", new Object[] { kind, match });
		Check check = new RoleCheck();
		check.setKind(kind);
		check.setMatch(match);
		return check;
	}
}
