package com.gome.upm.domain;

import java.io.Serializable;
import java.util.Date;

public class MoCpsBO implements Serializable{
	private static final long serialVersionUID = -5899054034605273666L;
	private Date startTime;
	private Date endTime;
	private Integer count;
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
	
}
