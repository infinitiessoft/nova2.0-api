package com.infinities.nova.db.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "QUOTAS", uniqueConstraints = { @UniqueConstraint(columnNames = { "PROJECT_ID", "RESOURCE", "DELETED" }) })
public class Quota extends AbstractModel implements IQuota {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String projectId;
	private String resource;
	private Integer hardLimit;


	@Override
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Column(name = "RESOURCE", length = 30)
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	@Column(name = "HARD_LIMIT", length = 10)
	public Integer getHardLimit() {
		return hardLimit;
	}

	public void setHardLimit(Integer hardLimit) {
		this.hardLimit = hardLimit;
	}

	@Column(name = "PROJECT_ID", length = 50)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
