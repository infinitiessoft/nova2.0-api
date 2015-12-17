package com.infinities.keystonemiddleware.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Response;

import com.google.common.base.Strings;
import com.infinities.keystonemiddleware.model.v3.AccessInfoV3;
import com.infinities.keystonemiddleware.model.v3.wrapper.TokenV3Wrapper;
import com.infinities.keystonemiddleware.model.wrapper.AccessWrapper;

public abstract class AccessInfo {

	public final static int STALE_TOKEN_DURATION = 30;

	protected ServiceCatalog serviceCatalog;


	// protected AccessInfo() {
	// this.serviceCatalog = ServiceCatalog.build(this, null,
	// this.getRegionName());
	// }

	public abstract String getRegionName();

	public boolean willExpireSoon(int staleDuration) {
		if (staleDuration == 0) {
			staleDuration = STALE_TOKEN_DURATION;
		}
		Calendar soon = Calendar.getInstance();
		soon.add(Calendar.SECOND, staleDuration);
		return soon.after(this.getExpires());
	}

	public abstract boolean hasServiceCatalog();

	public abstract String getAuthToken();

	public abstract Calendar getExpires();

	public abstract Calendar getIssued();

	public abstract String getUsername();

	public abstract String getUserId();

	public abstract String getUserDomainId();

	public abstract String getUserDomainName();

	public abstract List<String> getRoleIds();

	public abstract List<String> getRoleNames();

	public abstract String getDomainName();

	public abstract String getDomainId();

	public abstract String getProjectName();

	public String getTenantName() {
		return getProjectName();
	}

	public abstract boolean isScoped();

	public abstract boolean isProjectScoped();

	public abstract boolean isDomainScoped();

	public abstract String getTrustId();

	public abstract boolean isTrustScoped();

	public abstract String getTrusteeUserId();

	public abstract String getTrustorUserId();

	public abstract String getProjectId();

	public String getTenantId() {
		return getProjectId();
	}

	public abstract String getProjectDomainId();

	public abstract String getProjectDomainName();

	public abstract List<URL> getAuthUrl() throws MalformedURLException;

	public abstract String getVersion();

	public abstract String getOauthAccessTokenId();

	public abstract String getOauthConsumerId();

	public static AccessInfo build(Response response, TokenWrapper body, String regionName) {
		if (body != null) {
			if (AccessInfoV3.isValid(body)) {
				String tokenid = null;
				if (response != null) {
					tokenid = response.getHeaderString("X-Subject-Token");
				}
				com.infinities.keystonemiddleware.model.v3.Token token = ((TokenV3Wrapper) body).getToken();
				if (!Strings.isNullOrEmpty(regionName)) {
					token.setRegionName(regionName);
				}
				return new AccessInfoV3(tokenid, token);

			} else if (AccessInfoV2.isValid(body)) {
				if (!Strings.isNullOrEmpty(regionName)) {
					((AccessWrapper) body).getAccess().setRegionName(regionName);
				}
				return new AccessInfoV2(((AccessWrapper) body).getAccess());
			} else {
				throw new RuntimeException("Unrecognized auth response");
			}
		} else {
			return new AccessInfoV2(null);
		}

	}

	public ServiceCatalog getServiceCatalog() {
		return serviceCatalog;
	}
}
