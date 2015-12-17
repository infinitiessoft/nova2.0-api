package com.infinities.nova.openstack.common.policy.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class HttpCheck extends Check {

	private final Logger logger = LoggerFactory.getLogger(HttpCheck.class);


	@Override
	public String getRule() {
		return "http";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {
		logger.warn("HttpCheck not implemented yet");
		return false;
		// String url = "http:" + MessageFormat.format(getMatch(),
		// target.getUrl());

	}

	@Override
	public Check newInstance(String kind, String match) {
		Check check = new HttpCheck();
		check.setKind(kind);
		check.setMatch(match);
		return check;
	}
}
