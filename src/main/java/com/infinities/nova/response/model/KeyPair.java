package com.infinities.nova.response.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "keypair")
public class KeyPair implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
