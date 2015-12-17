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
package com.infinities.nova.openstack.common;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class Local {

	private static ThreadLocal<Map<String, Object>> strongStore = new ThreadLocal<Map<String, Object>>();
	private static ThreadLocal<Map<String, Object>> store = new ThreadLocal<Map<String, Object>>();


	public static Map<String, Object> getStrongStore() {
		if (store.get() == null) {
			store.set(new HashMap<String, Object>());
		}
		return strongStore.get();
	}

	public static void setStrongStore(ThreadLocal<Map<String, Object>> strongStore) {
		Local.strongStore = strongStore;
	}

	public static Map<String, Object> getStore() {
		if (store.get() == null) {
			store.set(new WeakHashMap<String, Object>());
		}
		return store.get();
	}

	public static void setStore(ThreadLocal<Map<String, Object>> store) {
		Local.store = store;
	}

}
