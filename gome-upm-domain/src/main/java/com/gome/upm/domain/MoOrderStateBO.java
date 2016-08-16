package com.gome.upm.domain;

import java.io.Serializable;
import java.util.Date;

public class MoOrderStateBO implements Serializable{
	private static final long serialVersionUID = 1338275163033276341L;
	/** 主键ID */
	private Long id;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private Integer minute;
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
