package com.infinities.nova.response.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "certificate")
public class Certificate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String data;

	@XmlElement(name = "private_key")
	private String privateKey;


	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the privateKey
	 */
	public String getPrivateKey() {
		return privateKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Certificate [data=" + data + ", privateKey=" + privateKey + "]";
	}

}
