package com.infinities.nova.model.home;

import com.infinities.nova.api.NovaRequestContext;



public interface SecurityGroupIngressRuleHome {
	
	public Long getSecurityGroupRuleCountByGroup(NovaRequestContext context,String securityGroupId);

}
