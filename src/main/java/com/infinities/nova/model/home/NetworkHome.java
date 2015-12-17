package com.infinities.nova.model.home;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.Network;

public interface NetworkHome {

	Network networkGet(NovaRequestContext context, Long networkId, String projectOnly);

	Network networkGetByUuid(NovaRequestContext context, String uuid);

}
