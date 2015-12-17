package com.infinities.nova.common;

import java.net.URI;
import java.util.List;

public abstract class Option {

	private final String name;
	private String value;


	public Option(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String asText() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int asInteger() {
		throw new IllegalArgumentException("value is not an integer");
	}

	public boolean asBoolean() {
		throw new IllegalArgumentException("value is not a boolean");
	}

	public List<String> asList() {
		throw new IllegalArgumentException("value is not a list");
	}

	public URI asURI() {
		throw new IllegalArgumentException("value is not a uri");
	}

	public String getDest() {
		return name.replace('-', '_');
	}

	public abstract void resetValue(String value);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Option other = (Option) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
