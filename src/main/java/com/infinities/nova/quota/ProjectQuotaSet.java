package com.infinities.nova.quota;

import com.infinities.nova.response.model.QuotaSet;

public class ProjectQuotaSet extends QuotaSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectId;


	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
