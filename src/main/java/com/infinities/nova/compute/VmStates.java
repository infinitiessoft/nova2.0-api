package com.infinities.nova.compute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VmStates {

	public final static String ACTIVE = "active";
	public final static String BUILDING = "building";
	public final static String PAUSED = "paused";
	public final static String SUSPENDED = "suspended";
	public final static String STOPPED = "stopped";
	public final static String RESCUED = "rescued";
	public final static String RESIZED = "resized";
	public final static String SOFT_DELETED = "soft-delete";
	public final static String DELETED = "deleted";
	public final static String ERROR = "error";
	public final static String SHELVED = "shelved";
	public final static String SHELVED_OFFLOADED = "shelved_offloaded";
	public final static List<String> ALLOW_SOFT_REBOOT;
	public final static List<String> ALLOW_HARD_REBOOT;
	static {
		List<String> list = new ArrayList<String>();
		list.add(ACTIVE);
		ALLOW_SOFT_REBOOT = Collections.unmodifiableList(list);

		List<String> list2 = new ArrayList<String>();
		list2.add(ACTIVE);
		list2.add(STOPPED);
		list2.add(PAUSED);
		list2.add(SUSPENDED);
		list2.add(ERROR);
		ALLOW_HARD_REBOOT = Collections.unmodifiableList(list2);
	}

}
