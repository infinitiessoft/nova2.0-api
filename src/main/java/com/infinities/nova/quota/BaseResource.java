package com.infinities.nova.quota;

import javax.ws.rs.NotFoundException;

import com.google.common.base.Strings;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.common.Config;

public abstract class BaseResource {

	private String name;
	private String flag;


	public int quota(QuotaDriver driver, NovaRequestContext context, String projectid, String quotaClass) {
		if (Strings.isNullOrEmpty(projectid)) {
			projectid = context.getProjectId();
		}

		if (Strings.isNullOrEmpty(quotaClass)) {
			quotaClass = context.getQuotaClass();
		}

		if (!Strings.isNullOrEmpty(projectid)) {
			try {
				return driver.getByProject(context, projectid, name).getHardLimit();
			} catch (NotFoundException e) {
				// pass
			}
		}

		if (!Strings.isNullOrEmpty(quotaClass)) {
			try {
				return driver.getByClass(context, quotaClass, name).getHardLimit();
			} catch (NotFoundException e) {
				// pass
			}
		}

		return getDefault();
	}

	// flag=null;
	public BaseResource(String name, String flag) {
		this.name = name;
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getDefault() {
		return Strings.isNullOrEmpty(flag) ? -1 : Config.Instance.getOpt(flag).asInteger();
	}

	public abstract String getValidMethod();

}
