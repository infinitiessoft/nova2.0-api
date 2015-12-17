package com.infinities.nova.api.openstack.common.template;

import java.io.Serializable;
import java.util.Map;

public class MetadataTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> metadata;


	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * Set the metadata
	 * 
	 * @param metadata
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

}
