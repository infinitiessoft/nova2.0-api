package com.infinities.nova.openstack.common.policy.check;

import java.util.List;

import jersey.repackaged.com.google.common.base.Joiner;

import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.Target;

public class OrCheck implements BaseCheck {

	private final List<BaseCheck> rules;


	public OrCheck(List<BaseCheck> rules) {
		this.rules = rules;
	}

	@Override
	public String getRule() {
		return "or";
	}

	@Override
	public boolean check(Target target, Credentials creds, Enforcer enforcer) {

		for (BaseCheck rule : rules) {
			if (rule.check(target, creds, enforcer)) {
				return true;
			}
		}
		return false;
	}

	public void addCheck(BaseCheck check) {
		this.rules.add(check);
	}

	public String toString() {
		String join = Joiner.on(" or ").join(rules);
		return String.format("(%s)", join);
	}
}
