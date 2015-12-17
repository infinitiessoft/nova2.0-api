package com.infinities.nova.network.model;

import java.util.ArrayList;
import java.util.List;

public class Subnet {

	private String cidr;
	private List<IP> dns = new ArrayList<IP>();
	private IP gateway;
	private List<IP> ips = new ArrayList<IP>();
	private List<String> routes = new ArrayList<String>();
	private String version;


	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public List<IP> getDns() {
		return dns;
	}

	public void setDns(List<IP> dns) {
		this.dns = dns;
	}

	public IP getGateway() {
		return gateway;
	}

	public void setGateway(IP gateway) {
		this.gateway = gateway;
	}

	public List<IP> getIps() {
		return ips;
	}

	public void setIps(List<IP> ips) {
		this.ips = ips;
	}

	public List<String> getRoutes() {
		return routes;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
