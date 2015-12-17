package com.infinities.nova.api;

import java.util.Map;

import com.infinities.nova.api.exception.PolicyNotAuthorizedException;
import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.Credentials;
import com.infinities.nova.openstack.common.policy.Enforcer;
import com.infinities.nova.openstack.common.policy.EnforcerImpl;
import com.infinities.nova.openstack.common.policy.Target;

public class Policy {

	private static Enforcer enforcer = null;


	private Policy() {

	}

	// public synchronized static void reset() {
	// if (enforcer != null) {
	// enforcer.clear();
	// enforcer = null;
	// }
	// }

	private synchronized static void init(String policyFile, Map<String, BaseCheck> rules, String defaultRule,
			boolean useConf) {
		if (enforcer == null) {
			enforcer = new EnforcerImpl(policyFile, rules, defaultRule, useConf);
		}
	}

	public static boolean checkIsAdmin(NovaRequestContext context) throws Exception {
		init(null, null, null, true);

		Credentials credentials = context;
		Target target = context;
		return enforcer.enforce("context_is_admin", target, credentials, false, null);
	}

	// public static Map<String, BaseCheck> getRules() {
	// if (enforcer != null) {
	// return enforcer.getRules();
	// }
	// return null;
	// }

	// public static void setRules(Map<String, BaseCheck> rules, boolean
	// overwrite, boolean useConf) {
	// init(null, null, null, false);
	// enforcer.setRules(rules, overwrite, useConf);
	// }

	// doRaise=true, exc=null
	public static boolean enforce(NovaRequestContext context, String action, Target target, boolean doRaise, Exception exc)
			throws Exception {
		init(null, null, null, true);
		Credentials credentials = context;
		if (exc == null) {
			exc = new PolicyNotAuthorizedException(null, action);
		}

		return enforcer.enforce(action, target, credentials, doRaise, exc);

	}
}
