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
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author pohsun
 *
 */
public class MinimalKeyPairsTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "keypairs")
	private List<MinimalKeyPair> keyPairs;


	/**
	 * 
	 */
	public MinimalKeyPairsTemplate() {
		super();
	}

	/**
	 * @param keyPairs
	 */
	public MinimalKeyPairsTemplate(List<MinimalKeyPair> keyPairs) {
		super();
		this.keyPairs = keyPairs;
	}

	/**
	 * @return the keyPairs
	 */
	public List<MinimalKeyPair> getKeyPairs() {
		return keyPairs;
	}

	/**
	 * @param keyPairs
	 *            the keyPairs to set
	 */
	public void setKeyPairs(List<MinimalKeyPair> keyPairs) {
		this.keyPairs = keyPairs;
	}

}
