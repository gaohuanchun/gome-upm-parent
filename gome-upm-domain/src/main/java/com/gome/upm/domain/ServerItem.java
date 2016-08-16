package com.gome.upm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 服务器监控实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月21日    liuhk    新建
 * </pre>
 */
public class ServerItem {
	
	//ID
	private Long id;
	
	//host
	private String hostId;
	
	//host
	private String host;
	
	//host
	private String name;
	//itemId
	private String itemId;
	
	List<ServerItemDetail>  itemDetailList = new ArrayList<ServerItemDetail>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public List<ServerItemDetail> getItemDetailList() {
		return itemDetailList;
	}

	public void setItemDetailList(List<ServerItemDetail> itemDetailList) {
		this.itemDetailList = itemDetailList;
	}
	
}
