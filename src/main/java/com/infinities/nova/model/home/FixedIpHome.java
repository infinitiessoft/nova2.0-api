package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.FixedIp;

public interface FixedIpHome {

	List<FixedIp> getByVirtualInterfaceId(NovaRequestContext context, Integer vifId);
}
