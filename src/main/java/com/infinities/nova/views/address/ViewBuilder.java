package com.infinities.nova.views.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.infinities.nova.api.openstack.Common;
import com.infinities.nova.api.openstack.Common.CommonNetwork;
import com.infinities.nova.response.model.Server.Addresses;
import com.infinities.nova.response.model.Server.Addresses.Address;
import com.infinities.nova.views.AbstractViewBuilder;

public class ViewBuilder extends AbstractViewBuilder {

	// private final static String COLLECTION_NAME = "addresses";

	public Addresses index(Map<String, Common.CommonNetwork> networks) {
		Addresses addresses = new Addresses();
		for (Entry<String, Common.CommonNetwork> entry : networks.entrySet()) {
			String label = entry.getKey();
			Common.CommonNetwork network = entry.getValue();
			Map<String, List<Address>> map = show(network, label);
			addresses.add(label, map.get(label));
		}
		return addresses;
	}

	public Map<String, List<Address>> show(CommonNetwork network, String label) {
		List<com.infinities.nova.db.model.Address> allIps = new ArrayList<com.infinities.nova.db.model.Address>();
		allIps.addAll(network.getIps());
		allIps.addAll(network.getFloatingIps());

		List<Address> addresses = new ArrayList<Address>();
		for (com.infinities.nova.db.model.Address ip : allIps) {
			addresses.add(basic(ip));
		}
		Map<String, List<Address>> map = new HashMap<String, List<Address>>();
		map.put(label, addresses);

		return map;
	}

	private Address basic(com.infinities.nova.db.model.Address ip) {
		Address address = new Address();
		address.setAddr(ip.getAddr());
		address.setVersion(String.valueOf(ip.getIpVersion()));
		return address;
	}

}
