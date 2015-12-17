package com.infinities.nova.openstack.common.policy.reducer;

import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.BaseReducer;
import com.infinities.nova.openstack.common.policy.check.AndCheck;

public class MakeAndExprReducer extends AbstractReducer {

	private final static List<List<String>> reducers = Lists.newArrayList();

	static {
		List<String> reducer1 = Lists.newArrayList();
		reducer1.add("check");
		reducer1.add("and");
		reducer1.add("check");
		reducers.add(reducer1);
	}


	public MakeAndExprReducer(BaseReducer reducer) {
		super(reducer);
	}

	@Override
	public List<List<String>> getReducers() {
		return reducers;
	}

	@Override
	public Entry<String, BaseCheck> getEntry(List<Entry<String, BaseCheck>> entry) {
		List<BaseCheck> checks = Lists.newArrayList();
		checks.add(entry.get(0).getValue());
		checks.add(entry.get(2).getValue());
		BaseCheck andCheck = new AndCheck(checks);
		return Maps.immutableEntry("and_expr", andCheck);
	}
}
