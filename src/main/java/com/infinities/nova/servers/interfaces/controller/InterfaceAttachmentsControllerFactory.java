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
package com.infinities.nova.servers.interfaces.controller;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.servers.interfaces.api.InterfaceAttachmentsApi;

public class InterfaceAttachmentsControllerFactory implements Factory<InterfaceAttachmentsController> {

	private final InterfaceAttachmentsApi interfaceAttachmentsApi;


	@Inject
	public InterfaceAttachmentsControllerFactory(InterfaceAttachmentsApi interfaceAttachmentsApi) {
		this.interfaceAttachmentsApi = interfaceAttachmentsApi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.glassfish.hk2.api.Factory#provide()
	 */
	@Override
	public InterfaceAttachmentsController provide() {
		return new InterfaceAttachmentsControllerImpl(interfaceAttachmentsApi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.glassfish.hk2.api.Factory#dispose(java.lang.Object)
	 */
	@Override
	public void dispose(InterfaceAttachmentsController instance) {

	}

}
