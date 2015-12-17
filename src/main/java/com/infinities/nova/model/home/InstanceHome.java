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
