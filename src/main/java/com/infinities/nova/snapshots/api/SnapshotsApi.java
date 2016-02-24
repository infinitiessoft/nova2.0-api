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

import java.util.List;

import com.infinities.nova.NovaRequestContext;
import com.infinities.nova.snapshots.model.Snapshot;
import com.infinities.nova.snapshots.model.SnapshotForCreateTemplate;

/**
 * @author pohsun
 *
 */
public interface SnapshotsApi {

	/**
	 * @param context
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	List<Snapshot> getSnapshots(NovaRequestContext context, String projectId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param snapshotId
	 * @return
	 * @throws Exception
	 */
	Snapshot getSnapshot(NovaRequestContext context, String projectId, String snapshotId) throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param snapshotForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	Snapshot createSnapshot(NovaRequestContext context, String projectId, SnapshotForCreateTemplate snapshotForCreateTemplate)
			throws Exception;

	/**
	 * @param context
	 * @param projectId
	 * @param snapshotId
	 * @throws Exception
	 */
	void deleteSnapshot(NovaRequestContext context, String projectId, String snapshotId) throws Exception;

}
