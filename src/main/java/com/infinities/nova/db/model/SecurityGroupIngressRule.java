package com.infinities.nova.db.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SECURITY_GROUP_RULES")
public class SecurityGroupIngressRule extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private SecurityGroup parentGroup;
	private String protocol;
	private Integer fromPort;
	private Integer toPort;
	private String cidr;
	private SecurityGroup granteeGroup;


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_GROUP_ID")
	public SecurityGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(SecurityGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	@Column(name = "PROTOCOL", length = 255)
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "FROM_PORT")
	public Integer getFromPort() {
		return fromPort;
	}

	public void setFromPort(Integer fromPort) {
		this.fromPort = fromPort;
	}

	@Column(name = "TO_PORT")
	public Integer getToPort() {
		return toPort;
	}

	public void setToPort(Integer toPort) {
		this.toPort = toPort;
	}

	@Column(name = "CIDR")
	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	public SecurityGroup getGranteeGroup() {
		return granteeGroup;
	}

	public void setGranteeGroup(SecurityGroup granteeGroup) {
		this.granteeGroup = granteeGroup;
	}

}
