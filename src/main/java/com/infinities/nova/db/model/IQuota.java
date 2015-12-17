package com.infinities.nova.db.model;

public interface IQuota {

	Long getId();

	String getResource();

	Integer getHardLimit();

	// String getProjectId();
}
