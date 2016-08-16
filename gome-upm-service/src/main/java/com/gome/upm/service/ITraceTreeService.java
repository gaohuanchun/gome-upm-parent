package com.gome.upm.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.gome.upm.common.Page;
import com.gome.upm.domain.TraceSearchInfo;
import com.gome.upm.domain.TraceTreeInfo;

/**
 * 根据traceID查询链路接口
 * @author zhouyaliang
 *
 */
public interface ITraceTreeService {
	TraceTreeInfo queryTraceTreeByTraceId(String traceId)
			throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException;
	
	TraceSearchInfo getTracesByBussinessKey(String businessKey, int from, int size);
}
