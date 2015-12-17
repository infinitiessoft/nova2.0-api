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
