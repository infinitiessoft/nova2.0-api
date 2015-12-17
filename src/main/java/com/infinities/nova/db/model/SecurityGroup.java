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
package com.infinities.nova.db.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SECURITY_GROUP")
public class SecurityGroup extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private String userId;
	private String projectId;
	private Set<SecurityGroupInstanceAssociation> securityGroupInstanceAssociations = new HashSet<SecurityGroupInstanceAssociation>(
			0);
	private Set<SecurityGroupIngressRule> securityGroupIngressRules = new HashSet<SecurityGroupIngressRule>(0);
	private Set<SecurityGroupIngressRule> granteeGroupRules = new HashSet<SecurityGroupIngressRule>(0);


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "USERID", length = 255)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "PROJECTID", length = 255)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "securityGroup", cascade = CascadeType.REMOVE)
	public Set<SecurityGroupInstanceAssociation> getSecurityGroupInstanceAssociations() {
		return securityGroupInstanceAssociations;
	}

	public void setSecurityGroupInstanceAssociations(Set<SecurityGroupInstanceAssociation> securityGroupInstanceAssociations) {
		this.securityGroupInstanceAssociations = securityGroupInstanceAssociations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentGroup", cascade = CascadeType.REMOVE)
	public Set<SecurityGroupIngressRule> getSecurityGroupIngressRules() {
		return securityGroupIngressRules;
	}

	public void setSecurityGroupIngressRules(Set<SecurityGroupIngressRule> securityGroupIngressRules) {
		this.securityGroupIngressRules = securityGroupIngressRules;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "granteeGroup", cascade = CascadeType.REMOVE)
	public Set<SecurityGroupIngressRule> getGranteeGroupRules() {
		return granteeGroupRules;
	}

	public void setGranteeGroupRules(Set<SecurityGroupIngressRule> granteeGroupRules) {
		this.granteeGroupRules = granteeGroupRules;
	}

}
