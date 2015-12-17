package com.infinities.nova.api.openstack.common.template;

import java.io.Serializable;
import java.util.Map;

public class MetaItemTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> meta;


	/**
	 * @return the metadata
	 */
	public Map<String, String> getMeta() {
		return meta;
	}

	/**
	 * Set the metadata
	 * 
	 * @param metadata
	 */
	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}

}
