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
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "keypair")
public class KeyPair implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "user_id")
	private String userId;

	@XmlElement(name = "public_key")
	private String publicKey;

	@XmlElement(name = "private_key")
	private String privateKey;

	private String fingerprint;


	public KeyPair() {
		super();
	}

	public KeyPair(String name) {
		this.name = name;
	}

	public KeyPair(String name, String publicKey) {
		this(name);
		this.publicKey = publicKey;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the publicKey
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * @param publicKey
	 *            the publicKey to set
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * @return the privateKey
	 */
	public String getPrivateKey() {
		return privateKey;
	}

	/**
	 * @return the fingerprint
	 */
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param privateKey
	 *            the privateKey to set
	 */
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * @param fingerprint
	 *            the fingerprint to set
	 */
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KeyPair [name=" + name + ", userId=" + userId + ", publicKey=" + publicKey + ", privateKey=" + privateKey
				+ ", fingerprint=" + fingerprint + "]";
	}

}
