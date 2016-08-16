package com.gome.upm.domain;

/**
 * 
 * 服务器监控实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月21日    liuhk    新建
 * </pre>
 */
public class ServerItemDetail {

	//ID
	private Long id;
	
	//host
	private String hostId;
	
	private String itemId;
	
	//host
	private String clock;
	//host
	private long time_from;
	
	private long time_till;
	//host
	private String value;

	public long getTime_from() {
		return time_from;
	}

	public void setTime_from(long time_from) {
		this.time_from = time_from;
	}

	public long getTime_till() {
		return time_till;
	}

	public void setTime_till(long time_till) {
		this.time_till = time_till;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

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

	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
