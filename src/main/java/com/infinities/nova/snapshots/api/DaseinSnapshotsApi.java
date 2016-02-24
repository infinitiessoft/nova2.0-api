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
package com.infinities.nova.snapshots.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.dasein.cloud.compute.SnapshotCreateOptions;

import com.google.common.base.Preconditions;
import com.infinities.nova.Context;
import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.exception.SnapshotNotFoundException;
import com.infinities.nova.snapshots.model.Snapshot;
import com.infinities.nova.snapshots.model.SnapshotForCreateTemplate;
import com.infinities.skyport.async.AsyncResult;
import com.infinities.skyport.cache.CachedServiceProvider;
import com.infinities.skyport.cache.service.compute.CachedSnapshotSupport;
import com.infinities.skyport.service.ConfigurationHome;

/**
 * @author pohsun
 *
 */
public class DaseinSnapshotsApi implements SnapshotsApi {

	private ConfigurationHome configurationHome;


	@Inject
	public DaseinSnapshotsApi(ConfigurationHome configurationHome) {
		this.configurationHome = configurationHome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.api.SnapshotsApi#getSnapshots(com.infinities
	 * .nova.NovaRequestContext, java.lang.String)
	 */
	@Override
	public List<Snapshot> getSnapshots(NovaRequestContext context, String projectId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<Iterable<org.dasein.cloud.compute.Snapshot>> result =
				this.getSupport(context.getProjectId()).listSnapshots();

		Iterable<org.dasein.cloud.compute.Snapshot> iterable = result.get();
		List<Snapshot> snapshots = new ArrayList<Snapshot>();
		Iterator<org.dasein.cloud.compute.Snapshot> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			org.dasein.cloud.compute.Snapshot snapshot = iterator.next();
			snapshots.add(toSnapshot(snapshot));
		}
		return snapshots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.api.SnapshotsApi#getSnapshot(com.infinities
	 * .nova.NovaRequestContext, java.lang.String, java.lang.String)
	 */
	@Override
	public Snapshot getSnapshot(NovaRequestContext context, String projectId, String snapshotId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		AsyncResult<org.dasein.cloud.compute.Snapshot> result =
				this.getSupport(context.getProjectId()).getSnapshot(snapshotId);
		org.dasein.cloud.compute.Snapshot snapshot = result.get();
		if (snapshot == null) {
			throw new SnapshotNotFoundException(null, snapshotId);
		}
		return toSnapshot(snapshot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.api.SnapshotsApi#createVolume(com.infinities
	 * .nova.NovaRequestContext, java.lang.String,
	 * com.infinities.nova.snapshots.model.SnapshotForCreateTemplate)
	 */
	@Override
	public Snapshot createSnapshot(NovaRequestContext context, String projectId,
			SnapshotForCreateTemplate snapshotForCreateTemplate) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}

		String volumeId = snapshotForCreateTemplate.getSnapshot().getVolumeId();
		String name = snapshotForCreateTemplate.getSnapshot().getName();
		String description = snapshotForCreateTemplate.getSnapshot().getDescription();
		SnapshotCreateOptions options = SnapshotCreateOptions.getInstanceForCreate(volumeId, name, description);

		AsyncResult<String> ret = this.getSupport(context.getProjectId()).createSnapshot(options);
		return getSnapshot(context, projectId, ret.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinities.nova.snapshots.api.SnapshotsApi#deleteVolume(com.infinities
	 * .nova.NovaRequestContext, java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteSnapshot(NovaRequestContext context, String projectId, String snapshotId) throws Exception {
		if (context == null) {
			context = Context.getAdminContext("no");
		}
		AsyncResult<Void> result = this.getSupport(context.getProjectId()).remove(snapshotId);
		System.err.println("remove snapshot:" + snapshotId);
		result.get();
	}

	private CachedSnapshotSupport getSupport(String id) throws ConcurrentException {
		CachedServiceProvider provider = configurationHome.findById(id);

		Preconditions.checkArgument(provider != null, "invalid project id:" + id);

		if (provider.hasComputeServices()) {
			if (provider.getComputeServices().hasSnapshotSupport()) {
				return provider.getComputeServices().getSnapshotSupport();
			}
		}
		throw new UnsupportedOperationException("service not supported for " + id);

	}

	/**
	 * @param snapshot
	 * @return
	 */
	private Snapshot toSnapshot(org.dasein.cloud.compute.Snapshot snapshot) {
		Snapshot ret = new Snapshot();
		Calendar createdAt = Calendar.getInstance();
		createdAt.setTimeInMillis(snapshot.getSnapshotTimestamp());
		ret.setCreatedAt(createdAt);
		ret.setDescription(snapshot.getDescription());
		ret.setId(snapshot.getProviderSnapshotId());
		ret.setName(snapshot.getName());
		ret.setSize(snapshot.getSizeInGb());
		ret.setStatus(snapshot.getCurrentState().name());
		ret.setVolumeId(snapshot.getVolumeId());
		return ret;
	}
}
