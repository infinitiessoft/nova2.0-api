package com.infinities.nova.network.model;

public class NetworkRequest {

	private String networkId;
	private String address;
	private String portId;
	private String pciRequestId;


	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}

	public String getPciRequestId() {
		return pciRequestId;
	}

	public void setPciRequestId(String pciRequestId) {
		this.pciRequestId = pciRequestId;
	}

}
