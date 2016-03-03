package com.infinities.neutron.networks.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@SuppressWarnings("serial")
@JsonRootName("network")
public class NetworkForUpdate implements Serializable{
	
	private String name;

    @JsonProperty("admin_state_up")
    private Boolean adminStateUp;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAdminStateUp() {
		return adminStateUp;
	}

	public void setAdminStateUp(Boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}
}
