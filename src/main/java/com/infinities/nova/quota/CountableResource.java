package com.infinities.nova.quota;

import java.util.Map.Entry;

import com.google.common.base.Function;
import com.infinities.nova.api.NovaRequestContext;

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
