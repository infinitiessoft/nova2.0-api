package com.infinities.nova.network.model;

import java.net.UnknownHostException;

import com.google.common.base.Strings;
import com.infinities.nova.util.CIDRUtils;

public class IP extends Model {

	private String address;
	private String type;
	private String version;
	private String macAddress;


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public boolean isInSubnet(com.infinities.nova.network.model.Subnet subnet) throws UnknownHostException {
		if (!Strings.isNullOrEmpty(getAddress()) && !Strings.isNullOrEmpty(subnet.getCidr())) {
			return new CIDRUtils(subnet.getCidr()).isInRange(getAddress());
		}
		return false;
	}

}
