package com.infinities.nova.model.wrapper;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.infinities.nova.response.model.Version;

@XmlRootElement
public class VersionListWrapper {

	private Collection<Version> versions;


	public VersionListWrapper() {

	}

	public VersionListWrapper(Collection<Version> versions) {
		this.versions = versions;
	}

	public Collection<Version> getVersions() {
		return versions;
	}

	public void setVersions(Collection<Version> versions) {
		this.versions = versions;
	}

}
