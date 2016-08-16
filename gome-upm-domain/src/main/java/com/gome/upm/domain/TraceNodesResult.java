package com.gome.upm.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TraceNodesResult {
	private boolean isOverMaxQueryNodeNumber;

    private List<TraceNodeInfo> result;

    public TraceNodesResult(){
		result = new ArrayList<TraceNodeInfo>();
    }


    public boolean isOverMaxQueryNodeNumber() {
        return isOverMaxQueryNodeNumber;
    }

    public void setOverMaxQueryNodeNumber(boolean overMaxQueryNodeNumber) {
        isOverMaxQueryNodeNumber = overMaxQueryNodeNumber;
    }

    public List<TraceNodeInfo> getResult() {
        return result;
    }

    public void setResult(Collection<TraceNodeInfo> result) {
        this.result.addAll(result);
    }
}
