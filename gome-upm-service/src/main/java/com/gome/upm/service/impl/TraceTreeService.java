package com.gome.upm.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.upm.common.gtrace.Constants;
import com.gome.upm.dao.ITraceNodeDao;
import com.gome.upm.dao.util.ReplaceAddressUtil;
import com.gome.upm.domain.TraceNodeInfo;
import com.gome.upm.domain.TraceNodesResult;
import com.gome.upm.domain.TraceSearchInfo;
import com.gome.upm.domain.TraceTreeInfo;
import com.gome.upm.service.ITraceTreeService;

import gome.gtrace.util.SpanLevelIdComparators;

@Service("traceTreeService")
public class TraceTreeService implements ITraceTreeService {
	
	@Resource(name="traceTreeDao")
	private ITraceNodeDao iTraceTreeDao;

	@Override
	public TraceTreeInfo queryTraceTreeByTraceId(String traceId)
			throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
		TraceTreeInfo traceTreeInfo = new TraceTreeInfo(traceId);
		TraceNodesResult traceNodesResult = iTraceTreeDao.queryTraceNodesByTraceId(traceId);
		List<TraceNodeInfo> traceNodeInfoList = traceNodesResult.getResult();
		if (traceNodesResult.isOverMaxQueryNodeNumber()) {
			traceNodeInfoList = new ArrayList<TraceNodeInfo>();
			traceNodeInfoList.addAll(iTraceTreeDao.queryEntranceNodeByTraceId(traceId));
			traceTreeInfo.setRealNodeSize(Constants.MAX_SEARCH_SPAN_SIZE + 1);
		} else {
			traceTreeInfo.setRealNodeSize(traceNodeInfoList.size());
		}

		if (traceNodeInfoList.size() > 0) {
			final List<Long> endTime = new ArrayList<Long>();
			endTime.add(0, traceNodeInfoList.get(0).getEndDate());

			Collections.sort(traceNodeInfoList, new Comparator<TraceNodeInfo>() {
				@Override
				public int compare(TraceNodeInfo arg0, TraceNodeInfo arg1) {
					if (endTime.get(0) < arg0.getEndDate()) {
						endTime.set(0, arg0.getEndDate());
					}
					if (endTime.get(0) < arg1.getEndDate()) {
						endTime.set(0, arg1.getEndDate());
					}
					return SpanLevelIdComparators.ascCompare(arg0.getColId(), arg1.getColId());
				}
			});

			// 截断
			int subIndex = traceNodeInfoList.size();
			if (subIndex > Constants.MAX_SHOW_SPAN_SIZE) {
				subIndex = Constants.MAX_SHOW_SPAN_SIZE;
			}
			traceTreeInfo.setHasBeenSpiltNodes(traceNodeInfoList.subList(0, subIndex));
			traceTreeInfo.setBeginTime(traceNodeInfoList.get(0).getStartDate());
			traceTreeInfo.setEndTime(endTime.get(0));
			if (traceNodeInfoList.get(0) != null) {
				traceTreeInfo.fillCallChainTreeToken(ReplaceAddressUtil.replace(
						traceNodeInfoList.get(0).getViewPointId(), traceNodeInfoList.get(0).getApplicationId()));
			}
		}

		return traceTreeInfo;
	}

	@Override
	public TraceSearchInfo getTracesByBussinessKey(String keyWord, int from, int size) {
		return iTraceTreeDao.getTracesByBussinessKey(keyWord,from,size);
	}
}
