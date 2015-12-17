package com.infinities.nova.quota;

public class AbsoluteResource extends BaseResource {

	private final static String validMethod = "check";


	public AbsoluteResource(String name, String flag) {
		super(name, flag);
	}

	@Override
	public String getValidMethod() {
		return validMethod;
	}

}
