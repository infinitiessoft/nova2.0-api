package com.infinities.nova.network.model;

import java.util.HashSet;
import java.util.Set;

public class VIF extends Model {

	private String id;
	private String address;
	private Network network;
	private String type;
	private String details;
	private String devname;
	private String ovsInterfaceid;
	private String qbhParams;
	private String qbgParams;
	private String active;
	private String vnicType;
	private String profile;
	private Float rxtxCap;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDevname() {
		return devname;
	}

	public void setDevname(String devname) {
		this.devname = devname;
	}

	public String getOvsInterfaceid() {
		return ovsInterfaceid;
	}

	public void setOvsInterfaceid(String ovsInterfaceid) {
		this.ovsInterfaceid = ovsInterfaceid;
	}

	public String getQbhParams() {
		return qbhParams;
	}

	public void setQbhParams(String qbhParams) {
		this.qbhParams = qbhParams;
	}

	public String getQbgParams() {
		return qbgParams;
	}

	public void setQbgParams(String qbgParams) {
		this.qbgParams = qbgParams;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getVnicType() {
		return vnicType;
	}

	public void setVnicType(String vnicType) {
		this.vnicType = vnicType;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Float getRxtxCap() {
		return rxtxCap;
	}

	public void setRxtxCap(Float rxtxCap) {
		this.rxtxCap = rxtxCap;
	}

	public Set<IP> getFixedIps() {
		Set<IP> ips = new HashSet<IP>();
		for (Subnet subnet : network.getSubnets()) {
			ips.addAll(subnet.getIps());
		}
		return ips;
	}

	public Set<IP> getFloatingIps() {
		Set<IP> ips = new HashSet<IP>();

		for (IP fixedIp : getFixedIps()) {
			ips.addAll(((FixedIP) fixedIp).getFloatingIps());
		}

		return ips;
	}
}
