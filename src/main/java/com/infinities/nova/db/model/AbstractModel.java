package com.infinities.nova.db.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar createdAt = Calendar.getInstance();
	private Calendar updatedAt = Calendar.getInstance();
	private Calendar deletedAt = Calendar.getInstance();
	private Integer deleted = 0;
	private int version;


	@Column(name = "CREATED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "UPDATED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "DELETED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Calendar deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Version
	@Column(name = "OPTLOCK")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "DELETED", length = 1)
	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
