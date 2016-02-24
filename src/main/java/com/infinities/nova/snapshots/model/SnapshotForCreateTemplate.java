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
package com.infinities.nova.snapshots.model;

import java.io.Serializable;

/**
 * @author pohsun
 *
 */
public class SnapshotForCreateTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SnapshotForCreate snapshot;


	/**
	 * @return the snapshot
	 */
	public SnapshotForCreate getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshot
	 *            the snapshot to set
	 */
	public void setSnapshot(SnapshotForCreate snapshot) {
		this.snapshot = snapshot;
	}

}
