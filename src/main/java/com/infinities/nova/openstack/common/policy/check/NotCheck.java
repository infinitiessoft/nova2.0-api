package com.infinities.nova.openstack.common.policy.check;

import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class NotCheck implements BaseCheck {

	private final BaseCheck rule;


	public NotCheck(BaseCheck rule) {
		this.rule = rule;
	}

	@Override
	public String getRule() {
		return "not";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {
		return !rule.check(target, creds, enforcer);
	}

	public String toString() {
		return String.format("not %s", rule);
	}
}
