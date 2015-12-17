package com.infinities.nova.openstack.common.policy.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class IsAdminCheck extends Check {

	private final static Logger logger = LoggerFactory.getLogger(IsAdminCheck.class);


	@Override
	public String getRule() {
		return "is_admin";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {
		return creds.getIsAdmin() == Boolean.parseBoolean(getMatch().toLowerCase());
	}

	@Override
	public Check newInstance(String kind, String match) {
		logger.debug("new rule check: {}, {}", new Object[] { kind, match });
		IsAdminCheck check = new IsAdminCheck();
		boolean expected = ("true".equals(match.toLowerCase()));
		check.setKind(kind);
		check.setMatch(String.valueOf(expected));
		return check;
	}
}
