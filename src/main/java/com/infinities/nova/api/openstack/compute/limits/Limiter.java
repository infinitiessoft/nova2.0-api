package com.infinities.nova.api.openstack.compute.limits;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import jersey.repackaged.com.google.common.collect.Maps;

import com.google.common.base.Strings;

public class Limiter {

	private Map<String, List<Limit>> levels;
	private final static String NONE = "none";


	public Limiter(List<Limit> limits) throws CloneNotSupportedException {
		List<Limit> clone = deepcopy(limits);

		levels = new HashMap<String, List<Limit>>();
		levels.put(NONE, clone);
	}

	private List<Limit> deepcopy(List<Limit> limits) throws CloneNotSupportedException {
		List<Limit> clones = new ArrayList<Limit>();

		for (Limit limit : limits) {
			clones.add((Limit) limit.clone());
		}
		return clones;
	}

	public List<LimitWrapper> getLimits(String username) {
		List<LimitWrapper> wrappers = new ArrayList<LimitWrapper>();
		if (Strings.isNullOrEmpty(username)) {
			username = NONE;
		}
		for (Limit limit : levels.get(username)) {
			wrappers.add(limit.display());
		}
		return wrappers;
	}

	public Entry<String, String> checkForDelay(String verb, URI uri, String username) {
		if (Strings.isNullOrEmpty(username)) {
			username = NONE;
		}

		List<Entry<String, String>> delays = new ArrayList<Entry<String, String>>();
		String delay;
		for (Limit limit : levels.get(username)) {
			delay = limit.call(verb, uri);
			if (delay != null) {
				delays.add(Maps.immutableEntry(delay, limit.getErrorMessage()));
			}
		}

		if (!delays.isEmpty()) {
			Collections.sort(delays, new Comparator<Entry<String, String>>() {

				@Override
				public int compare(Entry<String, String> arg0, Entry<String, String> arg1) {
					return arg0.getKey().compareTo(arg1.getKey());
				}

			});
			return delays.get(0);
		}

		return Maps.immutableEntry(null, null);
	}

	public static List<Limit> parseLimits(String limits) throws URISyntaxException {
		limits = limits.trim();
		if (Strings.isNullOrEmpty(limits)) {
			return new ArrayList<Limit>();
		}

		List<Limit> result = new ArrayList<Limit>();
		for (String group : limits.split(";")) {
			group = group.trim();
			if (!group.startsWith("(") || !group.endsWith(")")) {
				throw new IllegalArgumentException("Limit rules must be surrounded by parentheses");
			}

			group = group.substring(1, group.length() - 1);

			String a[] = group.split(",");
			String args[] = new String[a.length];
			for (int i = 0; i < a.length; i++) {
				args[i] = a[i].trim();
			}

			if (args.length != 5) {
				throw new IllegalArgumentException(
						"Limit rules must contain the following arguments: verb, uri, regex, value, unit");
			}

			String verb = args[0];
			URI uri = new URI(args[1]);
			String regex = args[2];
			int value = Integer.parseInt(args[3]);
			TimeUnit unit = TimeUnit.valueOf(args[4].toUpperCase());

			result.add(new Limit(verb, uri, regex, value, unit));
		}

		return result;
	}
}
