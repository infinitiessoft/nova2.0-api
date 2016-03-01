package com.infinities.neutron.networks.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@SuppressWarnings("serial")
@JsonRootName("network")
public class NetworkForCreate implements Serializable{
	
	private String name;

    @JsonProperty("admin_state_up")
    private Boolean adminStateUp;
    
    @JsonProperty("tenant_id")
    private String tenantId;
    
    @JsonProperty("router:external")
    private Boolean routerExternal;
    
    private Boolean shared;

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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Boolean getRouterExternal() {
		return routerExternal;
	}

	public void setRouterExternal(Boolean routerExternal) {
		this.routerExternal = routerExternal;
	}

	public Boolean getShared() {
		return shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}
    
}
