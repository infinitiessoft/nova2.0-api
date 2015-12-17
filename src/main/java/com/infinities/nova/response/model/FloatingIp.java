package com.infinities.nova.response.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "floating_ip")
public class FloatingIp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String pool;

	private String ip;

	@XmlElement(name = "fixed_ip")
	private String fixedIp;

	@XmlElement(name = "instance_id")
	private String instanceId;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the pool
	 */
	public String getPool() {
		return pool;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return the fixedIp
	 */
	public String getFixedIp() {
		return fixedIp;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FloatingIp [id=" + id + ", pool=" + pool + ", ip=" + ip
				+ ", fixedIp=" + fixedIp + ", instanceId=" + instanceId + "]";
	}

}
