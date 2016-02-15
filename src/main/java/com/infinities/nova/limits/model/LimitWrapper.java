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
package com.infinities.nova.limits.model;

import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;

public class LimitWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String verb;
	private URI uri;
	private String regex;
	private int value;
	private double remaning;
	private String unit;
	private Calendar resetTime;


	public LimitWrapper() {

	}

	public LimitWrapper(String verb, URI uri, String regex, int value, double remaning, String unit, Calendar resetTime) {
		super();
		this.verb = verb;
		this.uri = uri;
		this.regex = regex;
		this.value = value;
		this.remaning = remaning;
		this.unit = unit;
		this.resetTime = resetTime;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public double getRemaning() {
		return remaning;
	}

	public void setRemaning(double remaning) {
		this.remaning = remaning;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Calendar getResetTime() {
		return resetTime;
	}

	public void setResetTime(Calendar resetTime) {
		this.resetTime = resetTime;
	}

}
