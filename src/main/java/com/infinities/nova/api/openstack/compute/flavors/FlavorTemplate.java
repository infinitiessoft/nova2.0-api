package com.infinities.nova.api.openstack.compute.flavors;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FlavorTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Flavor flavor;


	public FlavorTemplate() {

	}

	public FlavorTemplate(Flavor flavor) {
		this.flavor = flavor;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}
}
