package com.gome.upm.service.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ZabbixUtils {
	private static String URL      = "http://zabbix.ds.gome.com.cn/zabbix/api_jsonrpc.php";
	private static String AUTH     = null;
	private static String USERNAME = "liuhaikun-ds";
	private static String PASSWORD = "1qazxcv";
	

	/**
	 * 向Zabbix发送Post请求，并返回json格式字符串
	 * 
	 * @param param 请求参数
	 * @return
	 * @throws Exception
	 */
	private static String sendPost(String param) throws Exception {
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		StringBuffer sb = null;
		try {
			//创建连接
			URL url = new URL(URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式

			connection.connect();

			//POST请求
			out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();

			//读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return sb.toString();

	}


	/**
	 * 通过用户名和密码设置AUTH，获得权限
	 * 
	 * @throws Exception
	 */
	private static void setAuth() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", USERNAME);
		params.put("password", PASSWORD);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "user.login");
		map.put("params", params);
		map.put("auth", null);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = JSON.parseObject(response);
		AUTH = json.getString("result");
	}


	/**
	 * 获得主机hostid
	 * 
	 * @param host Zabbix中配置的主机hosts
	 * @return 返回hostid
	 * @throws Exception
	 */
	private static String getHostid(String host) throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("host", host);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "hostid");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		String hostid = json.getString("hostid");
		return hostid;
	}


	/**
	 * 获得某主机的某个监控项id
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static String getItemid(String hostid, String item) throws Exception {
		setAuth();
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("key_", item);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "itemids");
		params.put("hostids", hostid);
		params.put("search", search);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "item.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = null;
		try {
			json = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String itemid = json.getString("itemid");
		return itemid;
	}
	/**
	 * 获得某主机的interfaces
	 * 
	 * @param host 主机
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static String getHostInterface(String hostid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("hostids", hostid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "hostinterface.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		return (String) json.get("ip");
	}


	/**
	 * 获取某主机某监控项在一段时间内的数据
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @param time_from 起始时间
	 * @param time_till 结束时间
	 * @throws Exception
	 */
	public static JSONArray getDatas(String hostid, String item, Date time_from, Date time_till)
			throws Exception {
		setAuth();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("history", 0);
		params.put("sortfield", "clock");
		params.put("itemids", getItemid(hostid, item));
		params.put("time_from", time_from.getTime() / 1000);
		params.put("time_till", time_till.getTime() / 1000);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "history.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray json = JSON.parseObject(response).getJSONArray("result");
		return json;
	}

	
	/**
	 * 获得主机host列表
	 * 
	 * @param host Zabbix中配置的主机hosts
	 * @return 返回hostid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getHostList(String status) throws Exception {
		setAuth();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("status", status);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("host", "name");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "host.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		Object[] array = jsonArray.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject((Map<String, Object>) array[i]);
			list.add(jsonObject);
		}
		return list;
	}
	/**
	 * 获得Template列表
	 * 
	 * @param host Zabbix中配置的主机hosts
	 * @return 返回hostid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getTemplateList(String status) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("status", status);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "template.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 1);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		Object[] array = jsonArray.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject((Map<String, Object>) array[i]);
			list.add(jsonObject);
		}
		return list;
	}
	
	/**
	 * 获得某主机的监控项列表
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> getItems(String hostid) throws Exception {
		setAuth();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("hostids", hostid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "item.get");
		map.put("params", params);
		map.put("sortfield", "status");
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		Object[] array = jsonArray.toArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String itemid = (String) jsonObject.get("itemid");
			String key_ = (String) jsonObject.get("key_");
			String name = (String) jsonObject.get("name");
			String delay = (String) jsonObject.get("delay");
			String history = (String) jsonObject.get("history");
			String trends = (String) jsonObject.get("trends");
			String status = (String) jsonObject.get("status");
			HashMap<String, String> outputP = new HashMap<String, String>();
			outputP.put("itemid", itemid);
			outputP.put("key_", key_);
			outputP.put("name", name);
			outputP.put("delay", delay);
			outputP.put("history", history);
			outputP.put("trends", trends);
			outputP.put("status", status);
			list.add(outputP);
		}
		return list;
	}
	
	/**
	 * 获得某主机的监控项列表
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static JSONObject getItems(String hostid,String key_) throws Exception {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("key_", key_);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("key_", "key_");
		output.put("lastvalue", "lastvalue");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", output);
		params.put("search", search);
		params.put("hostids", hostid);
		params.put("limit", 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "item.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject object = null;
		try {
			object = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		} catch (Exception e) {
		}
		return object;
	}
	
	/**
	 * 获得某主机的图标对象
	 * 
	 * @param host 主机
	 * @return
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> getGraph(String hostid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("hostids", hostid);
		params.put("sortfield", "name");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "graph.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		return list;
	}
	
	/**
	 * 获得某主机的图标对象
	 * 
	 * @param host 主机
	 * @return 
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> getImage(String hostid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("hostids", hostid);
		params.put("sortfield", "name");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "graph.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		return list;
	}
	/**
	 * 获取主机组列表
	 * @param hostgroup
	 * @return
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> getHostGroup() throws Exception {
		Map<String, Object> filter = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("filter", filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "hostgroup.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		Object[] array = jsonArray.toArray();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String groupid = (String) jsonObject.get("groupid");
			String name = (String) jsonObject.get("name");
			HashMap<String, String> outputP = new HashMap<String, String>();
			outputP.put("groupid", groupid);
			outputP.put("name", name);
			list.add(outputP);
		}
		return list;
	}
	
	public static Double getLastValue(Map<String, Object> map, String monitorItem) throws Exception{
		Double lastvalue = null;
		try {
			setAuth();
			JSONObject objectMemeor = getItems(map.get("hostid").toString(),monitorItem);
			if(objectMemeor!=null){
				String f = (String) objectMemeor.get("lastvalue");
				BigDecimal bd = new BigDecimal(f);
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
				lastvalue = Double.parseDouble(bd+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastvalue;
	}
	
	public static String getHostGroup(String hostid) throws Exception {
		setAuth();
		Map<String, Object> filter = new HashMap<String, Object>();
		String[] str = {hostid};
		filter.put("hostids", str);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		//params.put("filter", filter);
		params.put("hostids", str);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "hostgroup.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONObject json = JSON.parseObject(response).getJSONArray("result").getJSONObject(0);
		String name = json.getString("name");
		return name;
	}
	/**
	 * 获得监控项总数
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static int getAllItems(String status) throws Exception {
		setAuth();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("status", status);
		params.put("filter", filter);
		params.put("output", "itemid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "item.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = JSON.parseObject(response).getJSONArray("result");
		int length = jsonArray.toArray().length;
		return length;
	}
	/**
	 * 获得某主机的触发器列表
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static int getTrigger(String status) throws Exception {
		setAuth();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("status", status);
		params.put("output", "triggerid");
		params.put("filter", filter);
		map.put("jsonrpc", "2.0");
		map.put("method", "trigger.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = null;
		try {
			jsonArray = JSON.parseObject(response).getJSONArray("result");
		} catch (Exception e) {
		}
		return jsonArray.toArray().length;
	}
	
	public static List<HashMap<String, String>> getItemIds(String hostids,String names,String key) throws Exception{
		setAuth();
		JSONArray jsonArray = getItem(hostids,names);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < jsonArray.toArray().length; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String itemid = (String) jsonObject.get("itemid");
			String key_ = (String) jsonObject.get("key_");
			if(key_.contains(key)){
				HashMap<String, String> outputP = new HashMap<String, String>();
				outputP.put("itemid", itemid);
				outputP.put("key_", key_);
				list.add(outputP);
			}
		}
		return list;
	}
	
	/**
	 * 获得多项监控项列表
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @return 监控项itemid
	 * @throws Exception
	 */
	public static JSONArray getItem(String hostids,String name) throws Exception {
		setAuth();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> search = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("hostids", hostids);
		search.put("key_", name);
		params.put("search", search);
		map.put("jsonrpc", "2.0");
		map.put("method", "item.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray jsonArray = null;
		try {
			jsonArray = JSON.parseObject(response).getJSONArray("result");
		} catch (Exception e) {
		}
		return jsonArray;
	}
	
	
	/**
	 * 获取某主机某监控项实时的数据
	 * 
	 * @param host 主机
	 * @param item 监控项
	 * @param time_from 起始时间
	 * @param time_till 结束时间
	 * @throws Exception
	 */
	public static JSONArray getDataByItemId(String hostid, String itemId)
			throws Exception {
		setAuth();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("output", "extend");
		params.put("history", 0);
		params.put("sortfield", "clock");
		params.put("itemids", itemId);
		params.put("limit", 1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonrpc", "2.0");
		map.put("method", "history.get");
		map.put("params", params);
		map.put("auth", AUTH);
		map.put("id", 0);
		String param = JSON.toJSONString(map);
		String response = sendPost(param);
		JSONArray json = JSON.parseObject(response).getJSONArray("result");
		return json;
	}
}
