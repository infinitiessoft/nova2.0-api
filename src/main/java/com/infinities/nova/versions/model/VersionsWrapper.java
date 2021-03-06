/*******************************************************************************
 * Copyright 2015 InfinitiesSoft Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.infinities.nova.versions.model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VersionsWrapper {

	private Collection<Version> versions;


	public VersionsWrapper() {

	}

	public VersionsWrapper(Collection<Version> versions) {
		this.versions = versions;
	}

	public Collection<Version> getVersions() {
		return versions;
	}

	public void setVersions(Collection<Version> versions) {
		this.versions = versions;
	}

}
