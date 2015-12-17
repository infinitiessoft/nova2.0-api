package com.infinities.nova.network.model;

import java.util.ArrayList;
import java.util.List;

public class Network extends Model {

	private String id;
	private String bridge;
	private String label;
	private List<Subnet> subnets = new ArrayList<Subnet>();


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBridge() {
		return bridge;
	}

	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Subnet> getSubnets() {
		return subnets;
	}

	public void setSubnets(List<Subnet> subnets) {
		this.subnets = subnets;
	}

}
