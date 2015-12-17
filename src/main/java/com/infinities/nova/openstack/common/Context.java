package com.infinities.nova.openstack.common;

import java.util.UUID;

public class Context {

	public static String generateRequestId() {
		return String.format("req-%s", UUID.randomUUID());
	}
	
}
