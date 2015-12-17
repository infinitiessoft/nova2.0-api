package com.infinities.nova.model.wrapper;

import javax.xml.bind.annotation.XmlRootElement;

import com.infinities.nova.response.model.Version;

@XmlRootElement
public class VersionWrapper {

	private Version version;


	public VersionWrapper() {

	}

	public VersionWrapper(Version version) {
		this.version = version;

	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

}
