package com.infinities.nova.openstack.common.policy;

public interface BaseCheck {

	String getRule();

	boolean check(Target target, Credentials creds, Enforcer enforcer);

}
