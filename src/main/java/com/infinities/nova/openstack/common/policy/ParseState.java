package com.infinities.nova.openstack.common.policy;

import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.infinities.nova.openstack.common.policy.reducer.ExtendAndExprReducer;
import com.infinities.nova.openstack.common.policy.reducer.ExtendOrExprReducer;
import com.infinities.nova.openstack.common.policy.reducer.MakeAndExprReducer;
import com.infinities.nova.openstack.common.policy.reducer.MakeNotExprReducer;
import com.infinities.nova.openstack.common.policy.reducer.MakeOrExprReducer;
import com.infinities.nova.openstack.common.policy.reducer.WrapCheckReducer;

public class ParseState {

	private final static String CLOUD_NOT_PARSE = "Could not parse rule";
	private final List<Entry<String, BaseCheck>> entrys;
	private final BaseReducer reducer = new WrapCheckReducer(new MakeAndExprReducer(new ExtendAndExprReducer(
			new MakeOrExprReducer(new ExtendOrExprReducer(new MakeNotExprReducer(null))))));
	private final static Logger logger = LoggerFactory.getLogger(ParseState.class);


	public ParseState() {
		entrys = Lists.newArrayList();
	}

	private void reduce() {
		while (reducer.reduce(entrys) != null)
			;
	}

	public void shift(Entry<String, BaseCheck> entry) {
		entrys.add(entry);
		this.reduce();
	}

	public Entry<String, BaseCheck> getResult() {
		if (entrys.size() != 1) {
			// ValueError
			for (Entry<String, BaseCheck> entry : entrys) {
				logger.debug("value error:{}, {}", new Object[] { entry.getKey(), entry.getValue().getRule() });

			}

			throw new IllegalStateException(CLOUD_NOT_PARSE);
		}
		return entrys.get(0);
	}
}