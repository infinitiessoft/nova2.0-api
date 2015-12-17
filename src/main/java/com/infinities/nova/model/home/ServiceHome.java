package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.Service;

public interface ServiceHome {

	// disabled = null
	List<Service> serviceGetAll(NovaRequestContext context, Boolean disabled);

}
