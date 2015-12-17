package com.infinities.nova.api.openstack.compute.flavors;

import javax.ws.rs.container.ContainerRequestContext;

import com.infinities.nova.api.openstack.wsgi.Controller;

public interface FlavorsController extends Controller {

	public MinimalFlavorsTemplate index(ContainerRequestContext requestContext) throws Exception;

	public FlavorsTemplate detail(ContainerRequestContext requestContext) throws Exception;

	public FlavorTemplate show(String flavorid, ContainerRequestContext requestContext) throws Exception;

}
