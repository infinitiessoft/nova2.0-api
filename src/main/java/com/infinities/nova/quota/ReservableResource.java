package com.infinities.nova.quota;

public class ReservableResource extends BaseResource {

	private final String sync;
	private final static String validMethod = "reserve";


	public ReservableResource(String name, String sync, String flag) {
		super(name, flag);
		this.sync = sync;
	}

	public String getSync() {
		return sync;
	}

	@Override
	public String getValidMethod() {
		return validMethod;
	}

}
