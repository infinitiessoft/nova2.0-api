package com.infinities.nova.openstack.common.policy;

import java.util.Set;

public interface Credentials {

	boolean getIsAdmin();

	Set<String> getRoles();

}
