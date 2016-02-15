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
package com.infinities.nova.keypairs.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author pohsun
 *
 */
public class MinimalKeyPairTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "keypair")
	private MinimalKeyPair keyPair;


	/**
	 * @param keyPair
	 */
	public MinimalKeyPairTemplate(MinimalKeyPair keyPair) {
		super();
		this.keyPair = keyPair;
	}

	public MinimalKeyPairTemplate() {

	}

	/**
	 * @return the keyPair
	 */
	public MinimalKeyPair getKeyPair() {
		return keyPair;
	}

	/**
	 * @param keyPair
	 *            the keyPair to set
	 */
	public void setKeyPair(MinimalKeyPair keyPair) {
		this.keyPair = keyPair;
	}

}
