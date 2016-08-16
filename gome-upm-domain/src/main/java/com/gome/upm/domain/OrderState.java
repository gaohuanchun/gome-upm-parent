package com.gome.upm.domain;

import java.io.Serializable;

public class OrderState implements Serializable{
	private static final long serialVersionUID = 7498405196644291413L;
	/** 主键ID */
	private Long id;
	private String name;
	private String type;
	private String orderType;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
