package com.infinities.nova.api;

public class Context {

	// readDeleted = no
	public static NovaRequestContext getAdminContext(String readDeleted) throws Exception {
		NovaRequestContext context = new NovaRequestContext(null, null, true, readDeleted, null, null, null, null, null,
				false, null, null, null, null, false);
		return context;
	}
}
