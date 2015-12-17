package com.infinities.nova.db.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FAULT")
public class Fault implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private Integer code;

	private String message;

	private String details;

	private Calendar created;

	private Instance instance;

	private int version;


	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CODE")
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	@Column(name = "MESSAGE", length = 255)
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	@Column(name = "DETAILS", length = 255)
	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @return the created
	 */
	public Calendar getCreated() {
		return created;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	@OneToOne(mappedBy = "fault")
	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	@Version
	@Column(name = "OPTLOCK")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fault [code=" + code + ", message=" + message + ", details=" + details + ", created=" + created + "]";
	}

}
