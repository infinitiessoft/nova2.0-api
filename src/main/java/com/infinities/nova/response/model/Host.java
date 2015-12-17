package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class Host implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final class ResourceWrapper implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static final class Resource implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private String project;

			@XmlElement(name = "memory_mb")
			private Integer memoryMb;

			private String host;

			private Integer cpu;

			@XmlElement(name = "disk_gb")
			private Integer diskGb;

			/**
			 * @return the project
			 */
			public String getProject() {
				return project;
			}

			/**
			 * @return the memoryMb
			 */
			public Integer getMemoryMb() {
				return memoryMb;
			}

			/**
			 * @return the host
			 */
			public String getHost() {
				return host;
			}

			/**
			 * @return the cpu
			 */
			public Integer getCpu() {
				return cpu;
			}

			/**
			 * @return the diskGb
			 */
			public Integer getDiskGb() {
				return diskGb;
			}

			/* (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {
				return "Resource [project=" + project + ", memoryMb="
						+ memoryMb + ", host=" + host + ", cpu=" + cpu
						+ ", diskGb=" + diskGb + "]";
			}

		}

		private Resource resource;

		/**
		 * @return the resource
		 */
		public Resource getResource() {
			return resource;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ResourceWrapper [resource=" + resource + "]";
		}

	}

	private List<ResourceWrapper> host;

	/**
	 * @return the host
	 */
	public List<ResourceWrapper> getHost() {
		return host;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Host [host=" + host + "]";
	}

}

