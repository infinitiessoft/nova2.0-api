package com.infinities.nova.api.openstack.compute.flavors.api;

import java.util.List;

import com.infinities.nova.api.Context;
import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.flavors.FlavorsFilter;
import com.infinities.nova.db.model.InstanceType;
import com.infinities.nova.model.home.InstanceTypeHome;
import com.infinities.nova.model.home.impl.InstanceTypeHomeImpl;

public class FlavorsApiImpl implements FlavorsApi {

	private final InstanceTypeHome db;


	public FlavorsApiImpl() {
		db = new InstanceTypeHomeImpl();
	}

	// context=null, inactive=false,
	// filters=null,sortKey=flavorid,sortDir=asc,limit=null,marker=null
	@Override
	public List<InstanceType> getAllFlavorsSortedList(NovaRequestContext context, FlavorsFilter filter, String sortKey,
			String sortDir, Integer limit, String marker) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		List<InstanceType> instTypes = db.flavorGetAll(context, false, filter, sortKey, sortDir, limit, marker);
		return instTypes;
	}

	// context=null,readDeleted = yes
	@Override
	public InstanceType getFlavorByFlavorId(String flavorid, NovaRequestContext context, String readDeleted)
			throws Exception {
		if (context == null) {
			context = Context.getAdminContext(readDeleted);
		}
		return db.flavorGetByFlavorId(context, flavorid, readDeleted);
	}

	// @Override
	// public InstanceType getDefaultFlavor() throws Exception {
	// String name = Config.Instance.getOpt("default_flavor").asText();
	// return getFlavorByName(name, null);
	// }
	//
	// private InstanceType getFlavorByName(String name, NovaRequestContext
	// context) throws Exception {
	// if (Strings.isNullOrEmpty(name)) {
	// return getDefaultFlavor();
	// }
	// if (context == null) {
	// context = Context.getAdminContext("no");
	// }
	//
	// return db.flavorGetByName(context, name);
	// }
}
