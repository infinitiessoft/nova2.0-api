package com.infinities.nova.api.openstack.compute.flavors;

public class FlavorsFilter {

	private Boolean isPublic;
	private Boolean disabled;
	private Integer minMemoryMb;
	private Integer minRootGb;


	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Integer getMinMemoryMb() {
		return minMemoryMb;
	}

	public void setMinMemoryMb(Integer minMemoryMb) {
		this.minMemoryMb = minMemoryMb;
	}

	public Integer getMinRootGb() {
		return minRootGb;
	}

	public void setMinRootGb(Integer minRootGb) {
		this.minRootGb = minRootGb;
	}

}
