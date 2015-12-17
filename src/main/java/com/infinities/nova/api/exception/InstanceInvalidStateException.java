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
package com.infinities.nova.api.exception;

public class InstanceInvalidStateException extends InvalidException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean notLauched;
	private Object action;
	private Object attr;
	private Object state;


	public InstanceInvalidStateException(String message, Boolean notLaunched, Object... args) {
		super(message, args);
		this.notLauched = notLaunched;
		if (args != null) {
			if (args.length >= 1) {
				action = args[0];
			}
			if (args.length >= 2) {
				attr = args[1];
			}
			if (args.length >= 3) {
				state = args[2];
			}
		}

	}

	@Override
	public String getMsgFmt() {
		return "Instance %s in %s %s. Cannot %s while the instance is in this state.";
	}

	public Boolean getNotLauched() {
		return notLauched;
	}

	public void setNotLauched(Boolean notLauched) {
		this.notLauched = notLauched;
	}

	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	public Object getAttr() {
		return attr;
	}

	public void setAttr(Object attr) {
		this.attr = attr;
	}

	public Object getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = state;
	}

}
