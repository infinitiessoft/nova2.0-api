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
