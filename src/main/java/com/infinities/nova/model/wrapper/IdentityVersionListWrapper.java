package com.infinities.nova.model.wrapper;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.infinities.nova.response.model.Version;

@XmlRootElement
public class IdentityVersionListWrapper {

	private Collection<Version> values;


	public IdentityVersionListWrapper() {

	}

	public IdentityVersionListWrapper(Collection<Version> values) {
		this.values = values;
	}

	public Collection<Version> getValues() {
		return values;
	}

	public void setValues(Collection<Version> values) {
		this.values = values;
	}

}
