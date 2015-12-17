package com.infinities.nova.network.model;

import java.util.ArrayList;
import java.util.List;

public class FixedIP extends IP {

	private List<IP> floatingIps = new ArrayList<IP>();


	public FixedIP() {
		this.setType("fixed");
	}

	public List<IP> getFloatingIps() {
		return floatingIps;
	}

	public void setFloatingIps(List<IP> floatingIps) {
		this.floatingIps = floatingIps;
	}

}
