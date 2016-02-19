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
package com.infinities.nova.common.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.infinities.nova.util.FileScanner;
import com.infinities.skyport.util.PropertiesHolder;

public enum Config {
	Instance;

	public enum Type {
		DEFAULT, keystone_authtoken, hyperv, zookeeper, osapi_v3, conductor, keymgr, cells, database, image_file_url,
		baremetal, rpc_notifier2, matchmaker_redis, ssl, trusted_computing, upgrade_levels, matchmaker_ring, vmware, spice,
		glance;
	}


	private final Logger logger = LoggerFactory.getLogger(Config.class);
	private URL DEFAULT_CONFIG_FILENAME;
	public final static String CONFIG_FOLDER = PropertiesHolder.CONFIG_FOLDER;
	private final Table<Type, String, Option> OPTIONS = HashBasedTable.create();
	private final Pattern pattern = Pattern.compile("%\\((.*?)\\)", Pattern.DOTALL);


	private Config() {
		try {
			preSetup();
			parseConfigFiles();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void preSetup() throws MalformedURLException {
		// DEFAULT_CONFIG_FILENAME =
		// getClass().getResource(KeystoneApplication.CONF_DIR +
		// "keystone.conf");
		DEFAULT_CONFIG_FILENAME = getURL(CONFIG_FOLDER + File.separator + "nova.conf");

		// nova.api.openstack.common
		OPTIONS.put(Config.Type.DEFAULT, "osapi_max_limit", Options.newIntOpt("osapi_max_limit", 1000));
		OPTIONS.put(Config.Type.DEFAULT, "osapi_compute_link_prefix", Options.newStrOpt("osapi_compute_link_prefix"));
		OPTIONS.put(Config.Type.DEFAULT, "osapi_glance_link_prefix", Options.newStrOpt("osapi_glance_link_prefix"));

		// keystonemiddleware.auth_token
		// deprecated use identity_uri
		OPTIONS.put(Config.Type.keystone_authtoken, "auth_admin_prefix", Options.newStrOpt("auth_admin_prefix", ""));
		// deprecated use identity_uri
		OPTIONS.put(Config.Type.keystone_authtoken, "auth_host", Options.newStrOpt("auth_host", "127.0.0.1"));
		// deprecated use identity_uri
		OPTIONS.put(Config.Type.keystone_authtoken, "auth_port", Options.newIntOpt("auth_port", 35357));
		// deprecated use identity_uri
		OPTIONS.put(Config.Type.keystone_authtoken, "auth_protocol", Options.newStrOpt("auth_protocol", "https"));
		OPTIONS.put(Config.Type.keystone_authtoken, "auth_uri", Options.newStrOpt("auth_uri", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "identity_uri", Options.newStrOpt("identity_uri", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "auth_version", Options.newStrOpt("auth_version", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "delay_auth_decision", Options.newBoolOpt("delay_auth_decision", false));
		OPTIONS.put(Config.Type.keystone_authtoken, "http_connect_timeout", Options.newIntOpt("http_connect_timeout", 1000));
		OPTIONS.put(Config.Type.keystone_authtoken, "http_request_max_retries",
				Options.newIntOpt("http_request_max_retries", 3));
		OPTIONS.put(Config.Type.keystone_authtoken, "admin_token", Options.newStrOpt("admin_token", true, ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "admin_user", Options.newStrOpt("admin_user"));
		OPTIONS.put(Config.Type.keystone_authtoken, "admin_password", Options.newStrOpt("admin_password", true, ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "admin_tenant_name", Options.newStrOpt("admin_tenant_name", "admin"));
		OPTIONS.put(Config.Type.keystone_authtoken, "cache", Options.newStrOpt("cache", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "certfile", Options.newStrOpt("certfile", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "keyfile", Options.newStrOpt("keyfile", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "cafile", Options.newStrOpt("cafile", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "insecure", Options.newBoolOpt("insecure", false));
		OPTIONS.put(Config.Type.keystone_authtoken, "signing_dir", Options.newStrOpt("signing_dir", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "memcached_servers",
				Options.newListOpt("memcached_servers", new ArrayList<String>()));
		OPTIONS.put(Config.Type.keystone_authtoken, "token_cache_time", Options.newIntOpt("token_cache_time", 300));
		OPTIONS.put(Config.Type.keystone_authtoken, "revocation_cache_time", Options.newIntOpt("revocation_cache_time", 10));
		OPTIONS.put(Config.Type.keystone_authtoken, "memcache_security_strategy",
				Options.newStrOpt("memcache_security_strategy", ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "memcache_secret_key",
				Options.newStrOpt("memcache_secret_key", true, ""));
		OPTIONS.put(Config.Type.keystone_authtoken, "include_service_catalog",
				Options.newBoolOpt("include_service_catalog", true));
		OPTIONS.put(Config.Type.keystone_authtoken, "enforce_token_bind",
				Options.newStrOpt("enforce_token_bind", "permissive"));
		OPTIONS.put(Config.Type.keystone_authtoken, "check_revocations_for_cached",
				Options.newBoolOpt("check_revocations_for_cached", false));

		// nova.api.auth
		// use RateLimitingMiddleware or not?
		OPTIONS.put(Config.Type.DEFAULT, "api_rate_limit", Options.newBoolOpt("api_rate_limit", false));
		// use keystone or not?
		OPTIONS.put(Config.Type.DEFAULT, "auth_strategy", Options.newStrOpt("auth_strategy", "keystone"));
		OPTIONS.put(Config.Type.DEFAULT, "use_forwarded_for", Options.newBoolOpt("use_forwarded_for", false));

		// nova.openstack.common.policy
		OPTIONS.put(Config.Type.DEFAULT, "policy_file", Options.newStrOpt("policy_file", "policy.json"));
		OPTIONS.put(Config.Type.DEFAULT, "policy_default_rule", Options.newStrOpt("policy_default_rule", "default"));

		// nova.api.sizelimit
		OPTIONS.put(Config.Type.DEFAULT, "osapi_max_request_body_size",
				Options.newIntOpt("osapi_max_request_body_size", 114688));

		// nova.api.openstack.compute.init
		OPTIONS.put(Config.Type.DEFAULT, "allow_instance_snapshots", Options.newBoolOpt("allow_instance_snapshots", true));

		// noca.quota
		OPTIONS.put(Config.Type.DEFAULT, "quota_instances", Options.newIntOpt("quota_instances", 10));
		OPTIONS.put(Config.Type.DEFAULT, "quota_cores", Options.newIntOpt("quota_cores", 20));
		OPTIONS.put(Config.Type.DEFAULT, "quota_ram", Options.newIntOpt("quota_ram", 50 * 1024));
		OPTIONS.put(Config.Type.DEFAULT, "quota_floating_ips", Options.newIntOpt("quota_floating_ips", 10));
		OPTIONS.put(Config.Type.DEFAULT, "quota_fixed_ips", Options.newIntOpt("quota_fixed_ips", -1));
		OPTIONS.put(Config.Type.DEFAULT, "quota_metadata_items", Options.newIntOpt("quota_metadata_items", 128));
		OPTIONS.put(Config.Type.DEFAULT, "quota_injected_files", Options.newIntOpt("quota_injected_files", 5));
		OPTIONS.put(Config.Type.DEFAULT, "quota_injected_file_content_bytes",
				Options.newIntOpt("quota_injected_file_content_bytes", 10 * 1024));
		OPTIONS.put(Config.Type.DEFAULT, "quota_injected_file_path_length",
				Options.newIntOpt("quota_injected_file_path_length", 255));
		OPTIONS.put(Config.Type.DEFAULT, "quota_security_groups", Options.newIntOpt("quota_security_groups", 10));
		OPTIONS.put(Config.Type.DEFAULT, "quota_security_group_rules", Options.newIntOpt("quota_security_group_rules", 20));
		OPTIONS.put(Config.Type.DEFAULT, "quota_key_pairs", Options.newIntOpt("quota_key_pairs", 100));
		OPTIONS.put(Config.Type.DEFAULT, "quota_server_groups", Options.newIntOpt("quota_server_groups", 10));
		OPTIONS.put(Config.Type.DEFAULT, "quota_server_group_members", Options.newIntOpt("quota_server_group_members", 10));
		OPTIONS.put(Config.Type.DEFAULT, "reservation_expire", Options.newIntOpt("reservation_expire", 86400));
		OPTIONS.put(Config.Type.DEFAULT, "until_refresh", Options.newIntOpt("until_refresh", 0));
		OPTIONS.put(Config.Type.DEFAULT, "max_age", Options.newIntOpt("max_age", 0));
		OPTIONS.put(Config.Type.DEFAULT, "quota_driver",
				Options.newStrOpt("quota_driver", "com.infinities.nova.quota.NoopQuotaDriver"));

		// nova.image.glance
		OPTIONS.put(Config.Type.glance, "host", Options.newStrOpt("host", "127.0.0.1"));
		OPTIONS.put(Config.Type.glance, "port", Options.newIntOpt("port", 9292));
		OPTIONS.put(Config.Type.glance, "protocol", Options.newStrOpt("protocol", "http"));
		OPTIONS.put(Config.Type.glance, "api_servers", Options.newListOpt("api_servers", new ArrayList<String>()));
		OPTIONS.put(Config.Type.glance, "api_insecure", Options.newBoolOpt("api_insecure", false));
		OPTIONS.put(Config.Type.glance, "num_retries", Options.newIntOpt("num_retries", 0));
		OPTIONS.put(Config.Type.glance, "allowed_direct_url_schemas",
				Options.newListOpt("allowed_direct_url_schemas", new ArrayList<String>()));

		// glance.api.middleware.context
		OPTIONS.put(Config.Type.DEFAULT, "owner_is_tenant", Options.newBoolOpt("owner_is_tenant", true));

		OPTIONS.put(Config.Type.DEFAULT, "limit_param_default", Options.newIntOpt("limit_param_default", 25));
		OPTIONS.put(Config.Type.DEFAULT, "api_limit_max", Options.newIntOpt("api_limit_max", 1000));

		// password_length
		OPTIONS.put(Config.Type.DEFAULT, "password_length", Options.newIntOpt("password_length", 12));

		// nova.compute.flavors
		OPTIONS.put(Config.Type.DEFAULT, "default_flavor", Options.newStrOpt("default_flavor", "m1.small"));

		// nova.availability_zones
		OPTIONS.put(Config.Type.DEFAULT, "internal_service_availability_zone",
				Options.newStrOpt("internal_service_availability_zone", "internal"));
		OPTIONS.put(Config.Type.DEFAULT, "default_availability_zone", Options.newStrOpt("default_availability_zone", "nova"));

		List<String> algorithms = new ArrayList<String>();
		algorithms.add("md5");
		OPTIONS.put(Config.Type.keystone_authtoken, "hash_algorithms", Options.newListOpt("hash_algorithms", algorithms));

		OPTIONS.put(Config.Type.DEFAULT, "fatal_exception_format_errors",
				Options.newBoolOpt("fatal_exception_format_errors", false));
	}

	private void parseConfigFiles() throws IOException {
		FileScanner scanner = new FileScanner(DEFAULT_CONFIG_FILENAME);
		Table<Type, String, String> customTable = scanner.read();
		for (Cell<Type, String, String> cell : customTable.cellSet()) {
			if (OPTIONS.contains(cell.getRowKey(), cell.getColumnKey())) {
				logger.debug("reset FILE_OPTIONS {}.{} = {}",
						new Object[] { cell.getRowKey(), cell.getColumnKey(), cell.getValue() });
				OPTIONS.get(cell.getRowKey(), cell.getColumnKey()).resetValue(cell.getValue());
			} else {
				OPTIONS.put(cell.getRowKey(), cell.getColumnKey(), Options.newStrOpt(cell.getValue()));
			}
		}
	}

	public Option getOpt(Type type, String attr) {
		Option option = OPTIONS.get(type, attr);
		if (option instanceof StringOption) {
			Option newOption = Options.newStrOpt(option.getName(), option.getValue());
			Matcher matcher = pattern.matcher(option.asText());
			while (matcher.find()) {
				String match = matcher.group(1);

				logger.debug("sub-option pattern match: {}", match);
				Option suboption = OPTIONS.get(type, match);
				if (suboption != null) {
					String newValue = matcher.replaceFirst(suboption.getValue());
					newOption.setValue(newValue);
				}
			}
			return newOption;
		} else {
			return option;
		}
	}

	public Option getOpt(String attr) {
		return getOpt(Type.DEFAULT, attr);
	}

	public URL getURL(String filePath) {
		File file = new File(filePath);
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public String findFile(String name) {
		String path = CONFIG_FOLDER + File.separator + name;
		File f = new File(path);
		if (f.exists()) {
			return f.getAbsolutePath();
		} else {
			return null;
		}
	}
}
