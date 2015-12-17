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
package com.infinities.nova.compute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PowerState {

	public final static int NOSTATE = 0x00;
	public final static int RUNNING = 0x01;
	public final static int PAUSED = 0x03;
	public final static int SHUTDOWN = 0x04;
	public final static int CRASHED = 0x06;
	public final static int SUSPENDED = 0x07;
	public final static int BUILDING = 0x09;

	public final static Map<Integer, String> STATE_MAP;

	static {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(NOSTATE, "pending");
		map.put(RUNNING, "runngin");
		map.put(PAUSED, "paused");
		map.put(SHUTDOWN, "shutdown");
		map.put(CRASHED, "crashed");
		map.put(SUSPENDED, "suspended");
		map.put(BUILDING, "building");
		STATE_MAP = Collections.unmodifiableMap(map);
	}

}
