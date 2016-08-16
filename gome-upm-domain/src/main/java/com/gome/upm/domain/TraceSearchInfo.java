package com.gome.upm.domain;

import java.util.List;

public class TraceSearchInfo {
	/**
	 * 检索命中总记录数
	 */
	private long totalHits;
    private List<TraceInfo> traceInfoList;
    // 检索的业务key
    private String businessKey;
    
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}

	public List<TraceInfo> getTraceInfoList() {
		return traceInfoList;
	}

	public void setTraceInfoList(List<TraceInfo> traceInfoList) {
		this.traceInfoList = traceInfoList;
	}
}
