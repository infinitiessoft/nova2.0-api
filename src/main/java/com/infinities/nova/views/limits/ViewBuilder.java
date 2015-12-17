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
package com.infinities.nova.views.limits;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.infinities.nova.api.openstack.compute.limits.LimitWrapper;
import com.infinities.nova.api.openstack.compute.limits.LimitsTemplate;
import com.infinities.nova.quota.QuotaUsageSet;
import com.infinities.nova.response.model.Limits.AbsoluteLimit;
import com.infinities.nova.response.model.Limits.RateLimit;
import com.infinities.nova.response.model.Limits.RateLimit.LimitEntry;

public class ViewBuilder {

	// private final Map<String, List<String>> limitNames;

	public ViewBuilder() {
		// limitNames = new HashMap<String, List<String>>();
		// List<String> ram = new ArrayList<String>();
		// ram.add("maxToToalRAMSize");
		// limitNames.put("ram", ram);
		//
		// List<String> instances = new ArrayList<String>();
		// instances.add("maxTotalInstances");
		// limitNames.put("instances", instances);
		//
		// List<String> cores = new ArrayList<String>();
		// cores.add("maxTotalCores");
		// limitNames.put("cores", cores);
		//
		// List<String> keypairs = new ArrayList<String>();
		// keypairs.add("maxTotalKeypairs");
		// limitNames.put("key_pairs", keypairs);
		//
		// List<String> floatingIps = new ArrayList<String>();
		// floatingIps.add("maxTotalFloatingIps");
		// limitNames.put("floating_ips", floatingIps);
		//
		// List<String> metadataItems = new ArrayList<String>();
		// metadataItems.add("maxServerMeta");
		// metadataItems.add("maxImageMeta");
		// limitNames.put("metadata_items", metadataItems);
		//
		// List<String> injectedFiles = new ArrayList<String>();
		// injectedFiles.add("maxPersonality");
		// limitNames.put("injected_files", injectedFiles);
		//
		// List<String> injectedFilesContentBytes = new ArrayList<String>();
		// injectedFilesContentBytes.add("maxPersonalitySize");
		// limitNames.put("injected_file_content_bytes",
		// injectedFilesContentBytes);
		//
		// List<String> securityGroups = new ArrayList<String>();
		// securityGroups.add("maxSecurityGroups");
		// limitNames.put("security_groups", securityGroups);
		//
		// List<String> securityGroupRules = new ArrayList<String>();
		// securityGroupRules.add("maxSecurityGroupRules");
		// limitNames.put("security_groups_rules", securityGroupRules);

	}

	public LimitsTemplate build(List<LimitWrapper> rateLimits, QuotaUsageSet absLimits) {
		List<RateLimit> rates = buildRateLimits(rateLimits);
		AbsoluteLimit absolute = buildAboluteLimits(absLimits);

		com.infinities.nova.response.model.Limits limits = new com.infinities.nova.response.model.Limits();
		limits.setAbsolute(absolute);
		limits.setRate(rates);
		LimitsTemplate output = new LimitsTemplate(limits);
		return output;
	}

	private AbsoluteLimit buildAboluteLimits(QuotaUsageSet absLimits) {
		AbsoluteLimit limits = new AbsoluteLimit();
		limits.setMaxTotalRAMSize(absLimits.getRam().getLimit());
		limits.setMaxTotalInstances(absLimits.getInstances().getLimit());
		limits.setMaxTotalCores(absLimits.getCores().getLimit());
		limits.setMaxTotalKeypairs(absLimits.getKeyPairs().getLimit());
		limits.setMaxTotalFloatingIps(absLimits.getFloatingIps().getLimit());
		limits.setMaxServerMeta(absLimits.getMetadataItems().getLimit());
		limits.setMaxImageMeta(absLimits.getMetadataItems().getLimit());
		limits.setMaxPersonality(absLimits.getInjectedFiles().getLimit());
		limits.setMaxPersonalitySize(absLimits.getInjectedFileContentBytes().getLimit());
		limits.setMaxSecurityGroups(absLimits.getSecurityGroups().getLimit());
		limits.setMaxSecurityGroupRules(absLimits.getSecurityGroupRules().getLimit());
		return limits;
	}

	private List<RateLimit> buildRateLimits(List<LimitWrapper> rateLimits) {
		List<RateLimit> limits = new ArrayList<RateLimit>();

		for (LimitWrapper wrapper : rateLimits) {
			RateLimit rateLimitKey = null;
			LimitEntry rateLimit = buildRateLimit(wrapper);
			for (RateLimit limit : limits) {
				if (limit.getUri().equals(wrapper.getUri().toString()) && limit.getRegex().equals(wrapper.getRegex())) {
					rateLimitKey = limit;
					break;
				}
			}

			if (rateLimitKey == null) {
				rateLimitKey = new RateLimit();
				rateLimitKey.setUri(wrapper.getUri().toString());
				rateLimitKey.setRegex(wrapper.getRegex());
				rateLimitKey.setLimit(new ArrayList<LimitEntry>());
				limits.add(rateLimitKey);
			}

			rateLimitKey.getLimit().add(rateLimit);
		}
		return limits;
	}

	private LimitEntry buildRateLimit(LimitWrapper rateLimit) {
		Calendar nextAvail = rateLimit.getResetTime();
		LimitEntry entry = new LimitEntry();
		entry.setVerb(rateLimit.getVerb());
		entry.setValue(rateLimit.getValue());
		entry.setRemaining(new Double(rateLimit.getRemaning()).intValue());
		entry.setUnit(rateLimit.getUnit());
		entry.setNextAvailable(nextAvail);
		return entry;
	}

}
