package com.infinities.nova.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

	private ReflectUtils() {

	}


	public enum Mode {
		get, set
	}


	public static Object getObject(String methodName, Object quotas) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method fromMethod = quotas.getClass().getMethod("get" + methodName);
		Object obj = fromMethod.invoke(quotas);
		return obj;
	}

}
