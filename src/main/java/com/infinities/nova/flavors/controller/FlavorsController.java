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
package com.infinities.nova.flavors.controller;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.Controller;
import com.infinities.nova.flavors.model.FlavorTemplate;
import com.infinities.nova.flavors.model.FlavorsTemplate;
import com.infinities.nova.flavors.model.MinimalFlavorsTemplate;

public interface FlavorsController extends Controller {

	public MinimalFlavorsTemplate index(ContainerRequestContext requestContext) throws Exception;

	public FlavorsTemplate detail(ContainerRequestContext requestContext) throws Exception;

	public FlavorTemplate show(String flavorid, ContainerRequestContext requestContext) throws Exception;

}
