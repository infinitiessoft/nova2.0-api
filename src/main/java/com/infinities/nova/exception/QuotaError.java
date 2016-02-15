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
package com.infinities.nova.exception;

import java.util.ArrayList;
import java.util.List;

public class QuotaError extends NovaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public QuotaError() {
		this(null);
	}

	public QuotaError(String message, Object... args) {
		super(403, message, args);
		List<Object> list = new ArrayList<Object>();
		list.add(0);
		this.getHeaders().put("Retry-After", list);
		this.setSafe(true);
	}

	@Override
	public String getMsgFmt() {
		return "Quota exceeded: code=%s";
	}

}
