package com.infinities.nova.quota;

public class ProjectUserQuotaUsageSet extends QuotaUsageSet {

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
