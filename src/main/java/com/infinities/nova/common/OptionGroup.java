package com.infinities.nova.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Strings;

public class OptionGroup {

	private String name;
	private String title;
	private Map<String, Option> opts;


	public OptionGroup(String name, String title) {
		this.name = name;
		this.title = String.format("%s options", Strings.isNullOrEmpty(title) ? name : title);
		opts = new ConcurrentHashMap<String, Option>();
	}

	public boolean registerOption(Option option) {
		if (isOptRegistered(opts, option)) {
			return false;
		}
		opts.put(option.getDest(), option);

		return true;
	}

	private boolean isOptRegistered(Map<String, Option> opts, Option option) {
		if (opts.containsKey(option.getDest())) {
			if (!opts.get(option.getDest()).equals(option)) {
				throw new RuntimeException("duplicate option: " + option.getName());
			}
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Option get(String key) {
		return opts.get(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionGroup other = (OptionGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
