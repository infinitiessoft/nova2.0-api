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
package com.infinities.nova.snapshots.controller;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.snapshots.api.SnapshotsApi;
import com.infinities.nova.snapshots.model.Snapshot;
import com.infinities.nova.snapshots.model.SnapshotForCreateTemplate;
import com.infinities.nova.snapshots.model.SnapshotTemplate;
import com.infinities.nova.snapshots.model.Snapshots;
import com.infinities.nova.snapshots.view.ViewBuilder;

/**
 * @author pohsun
 *
 */
public class SnapshotsControllerImpl implements SnapshotsController {

	private final ViewBuilder builder = new ViewBuilder();
	private SnapshotsApi snapshotsApi;


	public SnapshotsControllerImpl(SnapshotsApi snapshotsApi) {
		this.snapshotsApi = snapshotsApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.controller.SnapshotsController#index(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String)
	 */
	@Override
	public Snapshots index(ContainerRequestContext requestContext, String projectId) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		List<Snapshot> snapshots = snapshotsApi.getSnapshots(context, projectId);
		return builder.index(requestContext, snapshots);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.controller.SnapshotsController#show(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SnapshotTemplate show(ContainerRequestContext requestContext, String projectId, String snapshotId)
			throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Snapshot snapshot = snapshotsApi.getSnapshot(context, projectId, snapshotId);

		return builder.show(requestContext, snapshot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.controller.SnapshotsController#create(javax
	 * .ws.rs.container.ContainerRequestContext, java.lang.String,
	 * com.infinities.nova.snapshots.model.SnapshotForCreateTemplate)
	 */
	@Override
	public SnapshotTemplate create(ContainerRequestContext requestContext, String projectId,
			SnapshotForCreateTemplate snapshotForCreateTemplate) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		Snapshot snapshot = snapshotsApi.createSnapshot(context, projectId, snapshotForCreateTemplate);

		return builder.show(requestContext, snapshot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.controller.SnapshotsController#delete(java
	 * .lang.String, java.lang.String,
	 * javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void delete(String projectId, String snapshotId, ContainerRequestContext requestContext) throws Exception {
		NovaRequestContext context = (NovaRequestContext) requestContext.getProperty("nova.context");
		snapshotsApi.deleteSnapshot(context, projectId, snapshotId);
	}

}
