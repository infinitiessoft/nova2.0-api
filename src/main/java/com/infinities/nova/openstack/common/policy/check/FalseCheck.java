package com.infinities.nova.openstack.common.policy.check;

import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class FalseCheck implements BaseCheck {

	@Override
	public String getRule() {
		return "!";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {
		return false;
	}

	public String toString() {
		return "!";
	}

}
