package com.infinities.nova.quota;

import com.infinities.nova.response.model.QuotaSet;

public class ProjectUserQuotaSet extends QuotaSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectId;
	private String userId;


	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
