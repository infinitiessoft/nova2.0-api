package com.infinities.nova.api.openstack.compute.images;

import java.io.Serializable;

public class MinimalImageTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MinimalImage image;


	public MinimalImageTemplate(MinimalImage image) {
		super();
		this.image = image;
	}

	public MinimalImage getImage() {
		return image;
	}

}
