package com.infinities.nova.model.wrapper;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VersionsWrapper {

	private IdentityVersionListWrapper versions;


	public VersionsWrapper() {

	}

	public VersionsWrapper(IdentityVersionListWrapper versions) {
		this.versions = versions;

	}

	public IdentityVersionListWrapper getVersions() {
		return versions;
	}

	public void setVersions(IdentityVersionListWrapper versions) {
		this.versions = versions;
	}

}
