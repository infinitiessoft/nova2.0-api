package com.infinities.nova.model.home;

import com.infinities.nova.api.NovaRequestContext;


public interface KeyPairHome {
	
	public Long getKeyPairCountByUser(NovaRequestContext context,String userid);

}
