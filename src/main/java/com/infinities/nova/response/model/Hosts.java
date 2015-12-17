package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class Hosts implements Iterable<Hosts.Host>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final class Host {

		private String zone;

		@XmlElement(name = "host_name")
		private String hostName;

		private String service;

		/**
		 * @return the hostName
		 */
		public String getHostName() {
			return hostName;
		}

		/**
		 * @return the service
		 */
		public String getService() {
			return service;
		}

		public String getZone() {
			return zone;
		}

		public void setZone(String zone) {
			this.zone = zone;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Host [hostName=" + hostName + ", service=" + service + "]";
		}

	}

	@XmlElement(name = "hosts")
	private List<Host> list;

	/**
	 * @return the list
	 */
	public List<Host> getList() {
		return list;
	}

	@Override
	public Iterator<Hosts.Host> iterator() {
		return list.iterator();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hosts [list=" + list + "]";
	}



}
