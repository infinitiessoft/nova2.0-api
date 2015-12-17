package com.infinities.nova.openstack.common.policy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;


public interface Enforcer {
	
	public void setRules(Map<String, BaseCheck> rules, boolean overwrite, boolean useConf);
	
	public void clear();
	
	public void loadRules(boolean forceReload) throws MalformedURLException, IOException;
	
	public boolean enforce(Object rule, Target target, Credentials creds, boolean doRaise, Exception ex) throws Exception;

	public Map<String, BaseCheck> getRules();
}
