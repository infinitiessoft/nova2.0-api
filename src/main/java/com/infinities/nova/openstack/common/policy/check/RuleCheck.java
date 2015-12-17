package com.infinities.nova.openstack.common.policy.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class RuleCheck extends Check {

	private final static Logger logger = LoggerFactory.getLogger(RuleCheck.class);


	@Override
	public String getRule() {
		return "rule";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {
		try {
			return enforcer.getRules().get(this.getMatch()).check(target, creds, enforcer);
		} catch (Exception e) {
			logger.warn("invalid match", e);
			return false;
		}
	}

	@Override
	public Check newInstance(String kind, String match) {
		logger.debug("new rule check: {}, {}", new Object[] { kind, match });
		Check check = new RuleCheck();
		check.setKind(kind);
		check.setMatch(match);
		return check;
	}
}
