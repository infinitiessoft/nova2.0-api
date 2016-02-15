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
package com.infinities.nova.quota;

import java.util.Map.Entry;

import com.google.common.base.Function;
import com.infinities.nova.NovaRequestContext;

public class CountableResource extends AbsoluteResource {

	private final Function<Entry<NovaRequestContext, String>, Long> count;


	public CountableResource(String name, Function<Entry<NovaRequestContext, String>, Long> count, String flag) {
		super(name, flag);
		this.count = count;
	}

	public Function<Entry<NovaRequestContext, String>, Long> getCount() {
		return count;
	}

}
