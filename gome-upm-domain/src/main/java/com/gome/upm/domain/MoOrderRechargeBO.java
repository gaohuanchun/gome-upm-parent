package com.gome.upm.domain;

import java.io.Serializable;
import java.util.Date;

public class MoOrderRechargeBO implements Serializable{
	private static final long serialVersionUID = 893407464888560250L;
	/** 主键ID */
	private Long id;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private Integer minute;
	
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
}
