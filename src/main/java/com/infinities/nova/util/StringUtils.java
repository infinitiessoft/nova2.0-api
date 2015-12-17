package com.infinities.nova.util;

public class StringUtils {

	private StringUtils() {

	}

	public static String rstrip(String orig, String word) {
		String ret = orig;
		if (orig.endsWith(word)) {
			ret = orig.substring(0, orig.lastIndexOf(word));
		}

		return ret;
	}

	public static String getMethodName(String resourceName) {
		String[] split = resourceName.split("_");
		String after = "";
		for (int i = 0; i < split.length; i++) {
			split[i] = getInitialCapital(split[i]);
			after += split[i];
		}
		return after;
	}

	public static String getInitialCapital(String original) {
		return Character.toUpperCase(original.charAt(0)) + original.substring(1);
	}

}
