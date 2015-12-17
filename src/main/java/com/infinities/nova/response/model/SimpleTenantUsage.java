/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.nova.response.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tenant_usage")
public class SimpleTenantUsage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "total_memory_mb_usage")
	private BigDecimal totalMemoryMbUsage;

	@XmlElement(name = "total_vcpus_usage")
	private BigDecimal totalVcpusUsage;

	@XmlElement(name = "total_local_gb_usage")
	private BigDecimal totalLocalGbUsage;

	private String start;

	private String stop;

	@XmlElement(name = "tenant_id")
	private String tenantId;

	@XmlElement(name = "total_hours")
	private String totalHours;

	@XmlElement(name = "server_usages")
	private List<ServerUsage> serverUsages;


	/**
	 * @return the totalMemoryMbUsage
	 */
	public BigDecimal getTotalMemoryMbUsage() {
		return totalMemoryMbUsage;
	}

	/**
	 * @param totalMemoryMbUsage
	 *            the totalMemoryMbUsage to set
	 */
	public void setTotalMemoryMbUsage(BigDecimal totalMemoryMbUsage) {
		this.totalMemoryMbUsage = totalMemoryMbUsage;
	}

	/**
	 * @return the totalVcpusUsage
	 */
	public BigDecimal getTotalVcpusUsage() {
		return totalVcpusUsage;
	}

	/**
	 * @param totalVcpusUsage
	 *            the totalVcpusUsage to set
	 */
	public void setTotalVcpusUsage(BigDecimal totalVcpusUsage) {
		this.totalVcpusUsage = totalVcpusUsage;
	}

	/**
	 * @return the totalLocalGbUsage
	 */
	public BigDecimal getTotalLocalGbUsage() {
		return totalLocalGbUsage;
	}

	/**
	 * @param totalLocalGbUsage
	 *            the totalLocalGbUsage to set
	 */
	public void setTotalLocalGbUsage(BigDecimal totalLocalGbUsage) {
		this.totalLocalGbUsage = totalLocalGbUsage;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the stop
	 */
	public String getStop() {
		return stop;
	}

	/**
	 * @param stop
	 *            the stop to set
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the totalHours
	 */
	public String getTotalHours() {
		return totalHours;
	}

	/**
	 * @param totalHours
	 *            the totalHours to set
	 */
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public List<ServerUsage> getServerUsages() {
		return serverUsages;
	}

	public void setServerUsages(List<ServerUsage> serverUsages) {
		this.serverUsages = serverUsages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleTenantUsage [totalMemoryMbUsage=" + totalMemoryMbUsage + ", totalVcpusUsage=" + totalVcpusUsage
				+ ", totalLocalGbUsage=" + totalLocalGbUsage + ", start=" + start + ", stop=" + stop + ", tenantId="
				+ tenantId + ", totalHours=" + totalHours + "]";
	}


	public static final class ServerUsage implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@XmlElement(name = "instance_id")
		private String instanceId;

		private Integer uptime;

		@XmlElement(name = "started_at")
		private String startedAt;

		@XmlElement(name = "ended_at")
		private String endedAt;

		@XmlElement(name = "memory_mb")
		private Integer memoryMb;

		@XmlElement(name = "tenant_id")
		private String tenantId;

		private String state;

		private Double hours;

		private Integer vcpus;

		private String flavor;

		@XmlElement(name = "local_gb")
		private Integer localDiskSize;

		private String name;


		public String getInstanceId() {
			return instanceId;
		}

		public Integer getUptime() {
			return uptime;
		}

		public String getStartedAt() {
			return startedAt;
		}

		public String getEndedAt() {
			return endedAt;
		}

		public Integer getMemoryMb() {
			return memoryMb;
		}

		public String getTenantId() {
			return tenantId;
		}

		public String getState() {
			return state;
		}

		public Double getHours() {
			return hours;
		}

		public Integer getVcpus() {
			return vcpus;
		}

		public String getFlavor() {
			return flavor;
		}

		public Integer getLocalDiskSize() {
			return localDiskSize;
		}

		public String getName() {
			return name;
		}
	}
}
