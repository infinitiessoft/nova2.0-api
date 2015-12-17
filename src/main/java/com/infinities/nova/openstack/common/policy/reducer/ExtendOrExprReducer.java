package com.infinities.nova.openstack.common.policy.reducer;

import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infinities.nova.openstack.common.policy.BaseCheck;
import com.infinities.nova.openstack.common.policy.BaseReducer;
import com.infinities.nova.openstack.common.policy.check.OrCheck;

public class ExtendOrExprReducer extends AbstractReducer {

	private final static List<List<String>> reducers = Lists.newArrayList();
	static {
		List<String> reducer1 = Lists.newArrayList();
		reducer1.add("or_expr");
		reducer1.add("or");
		reducer1.add("check");
		reducers.add(reducer1);
	}


	public ExtendOrExprReducer(BaseReducer reducer) {
		super(reducer);
	}

	@Override
	public List<List<String>> getReducers() {
		return reducers;
	}

	@Override
	public Entry<String, BaseCheck> getEntry(List<Entry<String, BaseCheck>> entrys) {
		((OrCheck) entrys.get(0).getValue()).addCheck(entrys.get(2).getValue());
		return Maps.immutableEntry("or_expr", entrys.get(0).getValue());
	}
}
