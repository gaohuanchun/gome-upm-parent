package com.gome.upm.domain;

import java.io.Serializable;
import java.util.Date;

public class MoSynBO implements Serializable{
	private static final long serialVersionUID = -8130615184368180784L;
	private Long id;
	private String name;
	private String type;
	private Integer count;
	private String synTime;
	private Date reTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getSynTime() {
		return synTime;
	}
	public void setSynTime(String synTime) {
		this.synTime = synTime;
	}
	public Date getReTime() {
		return reTime;
	}
	public void setReTime(Date reTime) {
		this.reTime = reTime;
	}
}
