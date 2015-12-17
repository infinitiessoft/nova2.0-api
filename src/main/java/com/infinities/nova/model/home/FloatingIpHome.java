package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.FloatingIp;

public interface FloatingIpHome {

	List<FloatingIp> floatingIpGetByFixedAddress(NovaRequestContext context, String fixedIp);

}
