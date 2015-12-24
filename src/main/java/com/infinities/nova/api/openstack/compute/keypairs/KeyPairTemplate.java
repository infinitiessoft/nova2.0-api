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
package com.infinities.nova.api.openstack.compute.keypairs;

import java.io.Serializable;

import com.infinities.nova.response.model.KeyPair;

/**
 * @author pohsun
 *
 */
public class KeyPairTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KeyPair keyPair;


	/**
	 * 
	 */
	public KeyPairTemplate() {
		super();
	}

	/**
	 * @param keyPair
	 */
	public KeyPairTemplate(KeyPair keyPair) {
		super();
		this.keyPair = keyPair;
	}

	/**
	 * @param keyPair
	 *            the keyPair to set
	 */
	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	/**
	 * @return the keyPair
	 */
	public KeyPair getKeyPair() {
		return keyPair;
	}

}