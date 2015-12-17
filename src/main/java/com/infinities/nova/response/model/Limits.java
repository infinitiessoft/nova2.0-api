package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "limits")
public class Limits implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final class RateLimit implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		public static final class LimitEntry implements Serializable {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@XmlElement(name = "next-available")
			private Calendar nextAvailable;

			private String unit;

			private String verb;

			private Integer remaining;

			private Integer available;

			private Integer value;


			/**
			 * @return the nextAvailable
			 */
			public Calendar getNextAvailable() {
				return nextAvailable;
			}

			/**
			 * @return the unit
			 */
			public String getUnit() {
				return unit;
			}

			/**
			 * @return the verb
			 */
			public String getVerb() {
				return verb;
			}

			/**
			 * @return the remaining
			 */
			public Integer getRemaining() {
				return remaining;
			}

			/**
			 * @return the available
			 */
			public Integer getAvailable() {
				return available;
			}

			/**
			 * @return the value
			 */
			public Integer getValue() {
				return value;
			}

			public void setNextAvailable(Calendar nextAvailable) {
				this.nextAvailable = nextAvailable;
			}

			public void setUnit(String unit) {
				this.unit = unit;
			}

			public void setVerb(String verb) {
				this.verb = verb;
			}

			public void setRemaining(Integer remaining) {
				this.remaining = remaining;
			}

			public void setAvailable(Integer available) {
				this.available = available;
			}

			public void setValue(Integer value) {
				this.value = value;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {
				return "LimitEntry [nextAvailable=" + nextAvailable + ", unit=" + unit + ", verb=" + verb + ", remaining="
						+ remaining + ", available=" + available + ", value=" + value + "]";
			}

		}


		private String regex;

		private String uri;

		private List<LimitEntry> limit = new ArrayList<LimitEntry>();


		/**
		 * @return the regex
		 */
		public String getRegex() {
			return regex;
		}

		/**
		 * @return the uri
		 */
		public String getUri() {
			return uri;
		}

		/**
		 * @return the limit
		 */
		public List<LimitEntry> getLimit() {
			return limit;
		}

		public void setRegex(String regex) {
			this.regex = regex;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public void setLimit(List<LimitEntry> limit) {
			this.limit = limit;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RateLimit [regex=" + regex + ", uri=" + uri + ", limit=" + limit + "]";
		}

	}

	public static final class AbsoluteLimit {

		private Integer maxServerMeta = 0;
		// private Integer serverMetaUsed = 0;

		private Integer maxPersonality = 0;
		// private Integer personalityUsed = 0;

		private Integer maxImageMeta = 0;
		// private Integer imageMetaUsed = 0;

		private Integer maxPersonalitySize = 0;
		// private Integer personalitySizeUsed = 0;

		private Integer maxTotalCores = 0;
		// private Integer totalCoresUsed = 0;

		private Integer maxTotalInstances = 0;
		// private Integer totalInstancesUsed = 0;

		private Integer maxTotalRAMSize = 0;
		// private Integer totalRAMUsed = 0;

		private Integer maxSecurityGroupRules = 0;
		// private Integer securityGroupRulesUsed = 0;

		private Integer maxTotalKeypairs = 0;
		// private Integer totalKeyPairsUsed = 0;

		// private Integer maxTotalVolumes = 0;
		// private Integer totalVolumesUsed = 0;

		private Integer maxSecurityGroups = 0;
		// private Integer totalSecurityGroupsUsed = 0;

		private Integer maxTotalFloatingIps = 0;


		// private Integer totalFloatingIpsUsed = 0;

		// private Integer maxTotalVolumeGigabytes = 0;

		// private Integer totalVolumeGigabytesUsed = 0;

		/**
		 * @return the maxServerMeta
		 */
		public Integer getMaxServerMeta() {
			return maxServerMeta;
		}

		/**
		 * @return the maxPersonality
		 */
		public Integer getMaxPersonality() {
			return maxPersonality;
		}

		/**
		 * @return the maxImageMeta
		 */
		public Integer getMaxImageMeta() {
			return maxImageMeta;
		}

		/**
		 * @return the maxPersonalitySize
		 */
		public Integer getMaxPersonalitySize() {
			return maxPersonalitySize;
		}

		/**
		 * @return the maxTotalCores
		 */
		public Integer getMaxTotalCores() {
			return maxTotalCores;
		}

		/**
		 * @return the maxTotalInstances
		 */
		public Integer getMaxTotalInstances() {
			return maxTotalInstances;
		}

		/**
		 * @return the maxTotalRAMSize
		 */
		public Integer getMaxTotalRAMSize() {
			return maxTotalRAMSize;
		}

		// /**
		// * @return the totalVolumesUsed
		// */
		// public Integer getTotalVolumesUsed() {
		// return totalVolumesUsed;
		// }

		/**
		 * @return the maxSecurityGroupRules
		 */
		public Integer getMaxSecurityGroupRules() {
			return maxSecurityGroupRules;
		}

		/**
		 * @return the maxTotalKeypairs
		 */
		public Integer getMaxTotalKeypairs() {
			return maxTotalKeypairs;
		}

		// /**
		// * @return the totalCoresUsed
		// */
		// public Integer getTotalCoresUsed() {
		// return totalCoresUsed;
		// }

		// /**
		// * @return the maxTotalVolumes
		// */
		// public Integer getMaxTotalVolumes() {
		// return maxTotalVolumes;
		// }

		// /**
		// * @return the totalRAMUsed
		// */
		// public Integer getTotalRAMUsed() {
		// return totalRAMUsed;
		// }
		//
		// /**
		// * @return the totalInstancesUsed
		// */
		// public Integer getTotalInstancesUsed() {
		// return totalInstancesUsed;
		// }

		/**
		 * @return the maxSecurityGroups
		 */
		public Integer getMaxSecurityGroups() {
			return maxSecurityGroups;
		}

		// /**
		// * @return the totalVolumeGigabytesUsed
		// */
		// public Integer getTotalVolumeGigabytesUsed() {
		// return totalVolumeGigabytesUsed;
		// }
		//
		// /**
		// * @return the totalSecurityGroupsUsed
		// */
		// public Integer getTotalSecurityGroupsUsed() {
		// return totalSecurityGroupsUsed;
		// }

		/**
		 * @return the maxTotalFloatingIps
		 */
		public Integer getMaxTotalFloatingIps() {
			return maxTotalFloatingIps;
		}

		// /**
		// * @return the totalKeyPairsUsed
		// */
		// public Integer getTotalKeyPairsUsed() {
		// return totalKeyPairsUsed;
		// }

		// /**
		// * @return the maxTotalVolumeGigabytes
		// */
		// public Integer getMaxTotalVolumeGigabytes() {
		// return maxTotalVolumeGigabytes;
		// }

		// /**
		// * @return the serverMetaUsed
		// */
		// public Integer getServerMetaUsed() {
		// return serverMetaUsed;
		// }
		//
		// /**
		// * @return the personalityUsed
		// */
		// public Integer getPersonalityUsed() {
		// return personalityUsed;
		// }
		//
		// /**
		// * @return the imageMetaUsed
		// */
		// public Integer getImageMetaUsed() {
		// return imageMetaUsed;
		// }
		//
		// /**
		// * @return the personalitySizeUsed
		// */
		// public Integer getPersonalitySizeUsed() {
		// return personalitySizeUsed;
		// }
		//
		// /**
		// * @return the securityGroupRulesUsed
		// */
		// public Integer getSecurityGroupRulesUsed() {
		// return securityGroupRulesUsed;
		// }
		//
		// /**
		// * @return the totalFloatingIpsUsed
		// */
		// public Integer getTotalFloatingIpsUsed() {
		// return totalFloatingIpsUsed;
		// }

		public void setMaxServerMeta(Integer maxServerMeta) {
			this.maxServerMeta = maxServerMeta;
		}

		// public void setServerMetaUsed(Integer serverMetaUsed) {
		// this.serverMetaUsed = serverMetaUsed;
		// }

		public void setMaxPersonality(Integer maxPersonality) {
			this.maxPersonality = maxPersonality;
		}

		// public void setPersonalityUsed(Integer personalityUsed) {
		// this.personalityUsed = personalityUsed;
		// }

		public void setMaxImageMeta(Integer maxImageMeta) {
			this.maxImageMeta = maxImageMeta;
		}

		// public void setImageMetaUsed(Integer imageMetaUsed) {
		// this.imageMetaUsed = imageMetaUsed;
		// }

		public void setMaxPersonalitySize(Integer maxPersonalitySize) {
			this.maxPersonalitySize = maxPersonalitySize;
		}

		// public void setPersonalitySizeUsed(Integer personalitySizeUsed) {
		// this.personalitySizeUsed = personalitySizeUsed;
		// }

		public void setMaxTotalCores(Integer maxTotalCores) {
			this.maxTotalCores = maxTotalCores;
		}

		// public void setTotalCoresUsed(Integer totalCoresUsed) {
		// this.totalCoresUsed = totalCoresUsed;
		// }

		public void setMaxTotalInstances(Integer maxTotalInstances) {
			this.maxTotalInstances = maxTotalInstances;
		}

		// public void setTotalInstancesUsed(Integer totalInstancesUsed) {
		// this.totalInstancesUsed = totalInstancesUsed;
		// }

		public void setMaxTotalRAMSize(Integer maxTotalRAMSize) {
			this.maxTotalRAMSize = maxTotalRAMSize;
		}

		// public void setTotalRAMUsed(Integer totalRAMUsed) {
		// this.totalRAMUsed = totalRAMUsed;
		// }

		public void setMaxSecurityGroupRules(Integer maxSecurityGroupRules) {
			this.maxSecurityGroupRules = maxSecurityGroupRules;
		}

		// public void setSecurityGroupRulesUsed(Integer securityGroupRulesUsed)
		// {
		// this.securityGroupRulesUsed = securityGroupRulesUsed;
		// }

		public void setMaxTotalKeypairs(Integer maxTotalKeypairs) {
			this.maxTotalKeypairs = maxTotalKeypairs;
		}

		// public void setTotalKeyPairsUsed(Integer totalKeyPairsUsed) {
		// this.totalKeyPairsUsed = totalKeyPairsUsed;
		// }

		// public void setMaxTotalVolumes(Integer maxTotalVolumes) {
		// this.maxTotalVolumes = maxTotalVolumes;
		// }

		// public void setTotalVolumesUsed(Integer totalVolumesUsed) {
		// this.totalVolumesUsed = totalVolumesUsed;
		// }

		public void setMaxSecurityGroups(Integer maxSecurityGroups) {
			this.maxSecurityGroups = maxSecurityGroups;
		}

		// public void setTotalSecurityGroupsUsed(Integer
		// totalSecurityGroupsUsed) {
		// this.totalSecurityGroupsUsed = totalSecurityGroupsUsed;
		// }

		public void setMaxTotalFloatingIps(Integer maxTotalFloatingIps) {
			this.maxTotalFloatingIps = maxTotalFloatingIps;
		}

		// public void setTotalFloatingIpsUsed(Integer totalFloatingIpsUsed) {
		// this.totalFloatingIpsUsed = totalFloatingIpsUsed;
		// }

		// public void setMaxTotalVolumeGigabytes(Integer
		// maxTotalVolumeGigabytes) {
		// this.maxTotalVolumeGigabytes = maxTotalVolumeGigabytes;
		// }

		// public void setTotalVolumeGigabytesUsed(Integer
		// totalVolumeGigabytesUsed) {
		// this.totalVolumeGigabytesUsed = totalVolumeGigabytesUsed;
		// }

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "AbsoluteLimit [maxServerMeta=" + maxServerMeta + ", maxPersonality=" + maxPersonality
					+ ", maxImageMeta=" + maxImageMeta + ", maxPersonalitySize=" + maxPersonalitySize + ", maxTotalCores="
					+ maxTotalCores + ", maxTotalInstances=" + maxTotalInstances + ", maxTotalRAMSize=" + maxTotalRAMSize
					+ "]";
		}

	}


	private List<RateLimit> rate;

	private AbsoluteLimit absolute;


	/**
	 * @return the rate
	 */
	public List<RateLimit> getRate() {
		return rate;
	}

	/**
	 * @return the absolute
	 */
	public AbsoluteLimit getAbsolute() {
		return absolute;
	}

	public void setRate(List<RateLimit> rate) {
		this.rate = rate;
	}

	public void setAbsolute(AbsoluteLimit absolute) {
		this.absolute = absolute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Limits [rate=" + rate + ", absolute=" + absolute + "]";
	}

}
