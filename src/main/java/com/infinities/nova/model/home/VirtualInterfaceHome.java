package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.db.model.VirtualInterface;



public interface VirtualInterfaceHome {
	
	List<VirtualInterface> getAll(NovaRequestContext context);

}
