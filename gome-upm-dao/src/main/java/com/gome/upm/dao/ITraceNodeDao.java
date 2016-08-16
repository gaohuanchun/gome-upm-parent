package com.gome.upm.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.gome.upm.domain.TraceNodeInfo;
import com.gome.upm.domain.TraceNodesResult;
import com.gome.upm.domain.TraceSearchInfo;

public interface ITraceNodeDao {
	TraceNodesResult queryTraceNodesByTraceId(String traceId)
			throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	Collection<TraceNodeInfo> queryEntranceNodeByTraceId(String traceId)
			throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	public TraceSearchInfo getTracesByBussinessKey(String businessKey, int from, int size);
}
