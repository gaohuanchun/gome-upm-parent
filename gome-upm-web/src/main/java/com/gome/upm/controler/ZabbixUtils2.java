package com.gome.upm.controler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gome.upm.domain.ServerHost;

public class ZabbixUtils2 {
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
	
	public static List<HashMap<String, String>> getItemIds(String hostids,String names,String key) throws Exception{
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
	public static void main(String[] args) throws Exception {
		String hostids = "11982";
		String names = "vfs.fs.size";
		String key = "total";
		List<HashMap<String,String>> itemIds = getItemIds(hostids, names, key);
		System.out.println(itemIds);
		for (int i = 0; i < itemIds.size(); i++) {
			String itemid = itemIds.get(i).get("itemid");
			long time_till = new Date().getTime();
			long time_from = time_till-1800000*2;
			JSONArray datas = ZabbixUtils.getDataByItemId(hostids, itemid, time_from, time_till);
			System.out.println(datas);
		}
	}
}
