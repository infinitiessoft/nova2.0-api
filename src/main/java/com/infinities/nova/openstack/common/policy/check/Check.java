package com.infinities.nova.openstack.common.policy.check;

import com.infinities.nova.openstack.common.policy.BaseCheck;

public abstract class Check implements BaseCheck {

	private String kind;// the field before the ':'
	private String match;// the field after the ':'


	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public abstract Check newInstance(String kind, String match);

	public String toString() {
		return String.format("%s:%s", kind, match);
	}

}
