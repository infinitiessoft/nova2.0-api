package com.infinities.nova.model.home;

import java.util.List;

import com.infinities.nova.api.NovaRequestContext;
import com.infinities.nova.api.openstack.compute.servers.ServersFilter;
import com.infinities.nova.db.model.Instance;

public interface InstanceHome {

	List<Instance> instanceGetAllByFilters(NovaRequestContext context, ServersFilter filters, String sortKey,
			String sortDir, Integer limit, String marker, List<String> columnsToJoin);

	Instance instanceGetByUuid(NovaRequestContext context, String uuid, List<String> columnsToJoin);

	Instance instanceGet(NovaRequestContext context, long id);

}
