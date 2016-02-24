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

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.snapshots.model.SnapshotForCreateTemplate;
import com.infinities.nova.snapshots.model.SnapshotTemplate;
import com.infinities.nova.snapshots.model.Snapshots;

/**
 * @author pohsun
 *
 */
public interface SnapshotsController {

	/**
	 * @param requestContext
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	Snapshots index(ContainerRequestContext requestContext, String projectId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param snapshotId
	 * @return
	 * @throws Exception
	 */
	SnapshotTemplate show(ContainerRequestContext requestContext, String projectId, String snapshotId) throws Exception;

	/**
	 * @param requestContext
	 * @param projectId
	 * @param snapshotForCreateTemplate
	 * @return
	 * @throws Exception
	 */
	SnapshotTemplate create(ContainerRequestContext requestContext, String projectId,
			SnapshotForCreateTemplate snapshotForCreateTemplate) throws Exception;

	/**
	 * @param projectId
	 * @param snapshotId
	 * @param requestContext
	 * @throws Exception
	 */
	void delete(String projectId, String snapshotId, ContainerRequestContext requestContext) throws Exception;

}
