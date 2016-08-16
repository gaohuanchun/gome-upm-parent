package com.gome.upm.dao.impl;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Repository;
import com.gome.upm.common.gtrace.Constants;
import com.gome.upm.dao.ITraceNodeDao;
import com.gome.upm.dao.util.ElasticSearchUtils;
import com.gome.upm.dao.util.HBaseUtils;
import com.gome.upm.dao.util.SortUtil;
import com.gome.upm.domain.TraceInfo;
import com.gome.upm.domain.TraceNodeInfo;
import com.gome.upm.domain.TraceNodesResult;
import com.gome.upm.domain.TraceSearchInfo;

import gome.gtrace.api.util.StringUtil;

@Repository("traceTreeDao")
public class TraceNodeDao implements ITraceNodeDao{
	
	@Resource(name="hbaseUtils")
    private HBaseUtils hBaseUtils;
	
	@Resource(name="esUtils")
    private ElasticSearchUtils elasticSearchUtils;
	
	/**
	 * 查询HBase
	 */
    @Override
	public TraceNodesResult queryTraceNodesByTraceId(String traceId)
			throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Table table = hBaseUtils.getConnection().getTable(TableName.valueOf(Constants.TABLE_NAME_CHAIN));
        Get g = new Get(Bytes.toBytes(traceId));
        g.setFilter(new ColumnCountGetFilter(Constants.MAX_SEARCH_SPAN_SIZE + 1));
        Result r = table.get(g);
        Map<String, TraceNodeInfo> traceLogMap = new HashMap<String, TraceNodeInfo>();
        Map<String, TraceNodeInfo> rpcMap = new HashMap<String, TraceNodeInfo>();
        TraceNodesResult result = new TraceNodesResult();
        if (r.rawCells().length < Constants.MAX_SEARCH_SPAN_SIZE) {
            for (Cell cell : r.rawCells()) {
                doDealSingleSpan(traceLogMap, rpcMap, cell);
            }
            computeRPCInfo(rpcMap, traceLogMap);
            result.setOverMaxQueryNodeNumber(false);
            result.setResult(traceLogMap.values());
        }else{
            result.setOverMaxQueryNodeNumber(true);
        }
        return result;
	}

    @Override
	public Collection<TraceNodeInfo> queryEntranceNodeByTraceId(String traceId)
			throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Table table = hBaseUtils.getConnection().getTable(TableName.valueOf(Constants.TABLE_NAME_CHAIN));
        Get g = new Get(Bytes.toBytes(traceId));
        g.addColumn("call-chain".getBytes(), "0".getBytes());
        g.addColumn("call-chain".getBytes(), "0-S".getBytes());
        g.addColumn("call-chain".getBytes(), "0.0".getBytes());
        Result r = table.get(g);

        Map<String, TraceNodeInfo> traceLogMap = new HashMap<String, TraceNodeInfo>();
        Map<String, TraceNodeInfo> rpcMap = new HashMap<String, TraceNodeInfo>();
        Cell cell = r.getColumnLatestCell("call-chain".getBytes(), "0".getBytes());
        if (cell == null){
            cell = r.getColumnLatestCell("call-chain".getBytes(), "0-S".getBytes());
        }
        doDealSingleSpan(traceLogMap, rpcMap, cell);

        cell = r.getColumnLatestCell("call-chain".getBytes(), "0.0".getBytes());
        doDealSingleSpan(traceLogMap, rpcMap, cell);

        computeRPCInfo(rpcMap, traceLogMap);
        return traceLogMap.values();
	}

	private void doDealSingleSpan(Map<String, TraceNodeInfo> traceLogMap, Map<String, TraceNodeInfo> rpcMap, Cell cell) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (cell != null && cell.getValueArray().length > 0) {
            String colId = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                    cell.getQualifierLength());
            TraceNodeInfo tmpEntry = TraceNodeInfo.convert(
                    Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()), colId);
            // 特殊处理RPC的服务端信息
            if (colId.endsWith(Constants.RPC_END_FLAG)) {
                rpcMap.put(colId.substring(0, colId.lastIndexOf(Constants.RPC_END_FLAG)), tmpEntry);
            } else {
                SortUtil.addCurNodeTreeMapKey(traceLogMap, colId, tmpEntry);
            }
        }
    }

    private void computeRPCInfo(Map<String, TraceNodeInfo> rpcMap, Map<String, TraceNodeInfo> traceLogMap) {
        // 合并处理
        if (rpcMap.size() > 0) {
            for (Map.Entry<String, TraceNodeInfo> rpcVO : rpcMap.entrySet()) {
                String colId = rpcVO.getKey();
                if (traceLogMap.containsKey(colId)) {
                    TraceNodeInfo logVO = traceLogMap.get(colId);
                    TraceNodeInfo serverLog = rpcVO.getValue();
                    if (StringUtil.isEmpty(logVO.getStatusCodeStr()) || Constants.STATUS_CODE_9.equals(logVO.getStatusCodeStr())) {
                        serverLog.setColId(colId);
                        traceLogMap.put(colId, serverLog);
                    } else {
                        TraceNodeInfo clientLog = traceLogMap.get(colId);
                        clientLog.setApplicationIdStr(clientLog.getApplicationIdStr() + " --> " + serverLog.getApplicationIdStr());
                        clientLog.setViewPointId(serverLog.getViewPointId());
                        clientLog.setViewPointIdSub(serverLog.getViewPointIdSub());
                        clientLog.setAddress(serverLog.getAddress());
                        if (StringUtil.isEmpty(clientLog.getExceptionStack())) {
                            clientLog.setExceptionStack(serverLog.getExceptionStack());
                        }else{
                            clientLog.setServerExceptionStr(serverLog.getServerExceptionStr());
                        }
                        //System.out.println("1");
                    }
                    logVO.addTimeLine(rpcVO.getValue().getStartDate(), rpcVO.getValue().getCost());
                } else {
                    traceLogMap.put(colId, rpcVO.getValue());
                }
            }
        }
    }
    
    /**
     * es全文检索查询
     */
    @Override
	public TraceSearchInfo getTracesByBussinessKey(String keyWord, int from, int size) {
		QueryBuilder query = multiMatchQuery(keyWord, "businessKey");
		TraceSearchInfo tsinfo = new TraceSearchInfo();
		List<TraceInfo> traceInfoList = new ArrayList<TraceInfo>();
		SearchResponse searchResponse = elasticSearchUtils.getEsClient()
				.prepareSearch(elasticSearchUtils.getIndex())
				.addHighlightedField("applicationId")
				.addHighlightedField("businessKey")
				.setHighlighterPreTags("<b><font color='red'>")
				.setHighlighterPostTags("</font></b>")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(query)
				.setFrom(from).setSize(size).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		tsinfo.setTotalHits(hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		if (searchHists.length > 0) {
			for (SearchHit hit : searchHists) {
				traceInfoList.add(wrapTraceInfo(hit));
			}
			tsinfo.setTraceInfoList(traceInfoList);
		}
		return tsinfo;
	}
    
	private TraceInfo wrapTraceInfo(SearchHit hit) {
		TraceInfo traceInfo = new TraceInfo();
		traceInfo.setAddress(hit.getSource().get("address")!=null?(String) hit.getSource().get("address"):"");
		traceInfo.setApplicationId(hit.getSource().get("applicationId")!=null?(String) hit.getSource().get("applicationId"):"");
		traceInfo.setBusinessKey(hit.getSource().get("businessKey")!=null?(String) hit.getSource().get("businessKey"):"");
		traceInfo.setTraceId(hit.getSource().get("traceId")!=null?(String) hit.getSource().get("traceId"):"");
		traceInfo.setCallType(hit.getSource().get("callType")!=null?(String) hit.getSource().get("callType"):"");
		traceInfo.setCost(hit.getSource().get("cost")!=null?Long.parseLong(hit.getSource().get("cost").toString()):0);
		traceInfo.setExceptionStack(hit.getSource().get("exceptionStack")!=null?(String) hit.getSource().get("exceptionStack"):"");
		traceInfo.setLevelId(hit.getSource().get("levelId")!=null?(int) hit.getSource().get("levelId"):0);
		traceInfo.setOriginData(hit.getSource().get("originData")!=null?(String) hit.getSource().get("originData"):"");
		traceInfo.setParentLevel(hit.getSource().get("parentLevel")!=null?(String) hit.getSource().get("parentLevel"):"");
		traceInfo.setProcessNo(hit.getSource().get("processNo")!=null?(String) hit.getSource().get("processNo"):"");
		traceInfo.setReceiver(hit.getSource().get("isReceiver")!=null?(boolean) hit.getSource().get("isReceiver"):false);
		traceInfo.setSpanType(hit.getSource().get("spanType")!=null?(String) hit.getSource().get("spanType"):"");
		traceInfo.setStartDate(hit.getSource().get("startDate")!=null?(long) hit.getSource().get("startDate"):0);
		traceInfo.setStatusCode(hit.getSource().get("statusCode")!=null?Byte.parseByte(hit.getSource().get("statusCode").toString()):0);
		traceInfo.setUserId(hit.getSource().get("userId")!=null?(String) hit.getSource().get("userId"):"");
		traceInfo.setViewPointId(hit.getSource().get("viewPointId")!=null?(String) hit.getSource().get("viewPointId"):"");
		return traceInfo;
	}
}
