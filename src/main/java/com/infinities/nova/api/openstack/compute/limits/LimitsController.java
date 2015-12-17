package com.infinities.nova.api.openstack.compute.limits;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.api.openstack.wsgi.Controller;

public interface LimitsController extends Controller {

	public LimitsTemplate index(ContainerRequestContext requestContext) throws Exception;
}
