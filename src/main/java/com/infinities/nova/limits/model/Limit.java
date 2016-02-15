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

import java.net.URI;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Limit implements Cloneable {

	private Logger logger = LoggerFactory.getLogger(Limit.class);
	private String verb;
	private URI uri;
	private String regex;
	private int value;
	private TimeUnit unit;
	private String unitString;
	private double remaining;
	private double waterLevel;
	private long capacity;
	private double requestValue;
	private String errorMessage;
	private Calendar lastRequest;
	private Calendar nextRequest;


	public Limit() {

	}

	public Limit(String verb, URI uri, String regex, int value, TimeUnit unit) {
		this.verb = verb.toLowerCase();
		this.uri = uri;
		this.regex = regex;
		this.value = value;
		this.unit = unit;
		this.unitString = displayUnit().toLowerCase();
		this.remaining = value;

		if (value <= 0) {
			throw new IllegalArgumentException("Limit value must be > 0");
		}

		lastRequest = null;
		nextRequest = null;

		this.waterLevel = 0;
		this.capacity = unit.toSeconds(1);

		this.requestValue = capacity / value;

		this.errorMessage = String.format("Only %d %s request(s) can be made to %s every %s", value, verb, uri.toString(),
				unitString);
	}

	public String call(String verb, URI uri) {
		if (!this.verb.equals(verb) || !uri.toString().matches(regex)) {
			return null;
		}

		Calendar now = getTime();

		if (lastRequest == null) {
			lastRequest = (Calendar) now.clone();
		}

		long leakValue = TimeUnit.MILLISECONDS.toSeconds(now.getTimeInMillis() - lastRequest.getTimeInMillis());
		waterLevel -= leakValue;
		waterLevel = waterLevel > 0 ? waterLevel : 0;
		waterLevel += requestValue;

		double difference = waterLevel - capacity;
		logger.debug("leakValue: {}, requestValue: {}, waterLevel: {}, difference: {}, capacity: {}", new Object[] {
				leakValue, requestValue, waterLevel, difference, capacity });

		lastRequest = (Calendar) now.clone();

		if (difference > 0d) {
			waterLevel -= requestValue;
			nextRequest = (Calendar) now.clone();
			nextRequest.add(Calendar.SECOND, (int) difference);
			return String.valueOf(difference);
		}

		long cap = capacity;
		double water = waterLevel;
		int val = value;

		double f = ((cap - water) / cap) * val;
		remaining = Math.floor(f);
		nextRequest = now;

		return null;
	}

	private Calendar getTime() {
		return Calendar.getInstance();
	}

	private String displayUnit() {
		return unit.name();
	}

	public LimitWrapper display() {
		Calendar resetTime = this.nextRequest == null ? getTime() : nextRequest;
		return new LimitWrapper(verb, uri, regex, value, remaining, displayUnit(), resetTime);
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
