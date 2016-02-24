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
package com.infinities.nova.snapshots.view;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.snapshots.model.Snapshot;
import com.infinities.nova.snapshots.model.SnapshotTemplate;
import com.infinities.nova.snapshots.model.Snapshots;

/**
 * @author pohsun
 *
 */
public class ViewBuilder {

	/**
	 * @param requestContext
	 * @param snapshots
	 * @return
	 */
	public Snapshots index(ContainerRequestContext requestContext, List<Snapshot> snapshots) {
		Snapshots ret = new Snapshots();
		ret.setList(snapshots);
		return ret;
	}

	/**
	 * @param requestContext
	 * @param snapshot
	 * @return
	 */
	public SnapshotTemplate show(ContainerRequestContext requestContext, Snapshot snapshot) {
		SnapshotTemplate template = new SnapshotTemplate();
		template.setSnapshot(snapshot);
		return template;
	}

}
