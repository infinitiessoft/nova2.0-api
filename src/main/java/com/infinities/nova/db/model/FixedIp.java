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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "FIXED_IPS", uniqueConstraints = { @UniqueConstraint(columnNames = { "ADDRESS", "DELETED" }) })
public class FixedIp extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String address;
	private Integer virtualIntetfaceId;
	private Instance instance;
	private Boolean allocated = false;
	private Boolean leased = false;
	private Boolean reserved = false;
	private String host;
	private Network network;
	private Set<FloatingIp> floatingIps = new HashSet<FloatingIp>(0);


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ADDRESS", length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "VIRTUAL_INTERFACE_ID")
	public Integer getVirtualIntetfaceId() {
		return virtualIntetfaceId;
	}

	public void setVirtualIntetfaceId(Integer virtualIntetfaceId) {
		this.virtualIntetfaceId = virtualIntetfaceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTANCE_ID")
	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	@Column(name = "ALLOCATED")
	public Boolean getAllocated() {
		return allocated;
	}

	public void setAllocated(Boolean allocated) {
		this.allocated = allocated;
	}

	@Column(name = "LEASED")
	public Boolean getLeased() {
		return leased;
	}

	public void setLeased(Boolean leased) {
		this.leased = leased;
	}

	@Column(name = "RESERVED")
	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	@Column(name = "HOST", length = 255)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NETWORK_ID")
	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fixedIp", cascade = CascadeType.REMOVE)
	public Set<FloatingIp> getFloatingIps() {
		return floatingIps;
	}

	public void setFloatingIps(Set<FloatingIp> floatingIps) {
		this.floatingIps = floatingIps;
	}

}
