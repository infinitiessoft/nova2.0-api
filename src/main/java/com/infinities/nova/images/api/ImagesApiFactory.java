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
package com.infinities.nova.images.api;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.infinities.nova.images.api.driver.ImagesDriver;

public class ImagesApiFactory implements Factory<ImagesApi> {

	private final ImagesDriver driver;


	@Inject
	public ImagesApiFactory(ImagesDriver driver) {
		this.driver = driver;
	}

	@Override
	public ImagesApi provide() {
		return new ImagesApiImpl(driver);
	}

	@Override
	public void dispose(ImagesApi instance) {
	}

}
