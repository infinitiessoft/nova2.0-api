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
