package com.infinities.nova.api.openstack.compute.flavors;

import java.io.Serializable;

public class MinimalFlavorTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MinimalFlavor flavor;


	public MinimalFlavorTemplate(MinimalFlavor flavor) {
		super();
		this.flavor = flavor;
	}

	public MinimalFlavor getFlavor() {
		return flavor;
	}

}
