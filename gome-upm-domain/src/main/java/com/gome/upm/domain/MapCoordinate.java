package com.gome.upm.domain;

import java.io.Serializable;

public class MapCoordinate implements Serializable{
	private static final long serialVersionUID = 6209143144992127632L;
	private Long id;
	private String name;
	private String xaxis;
	private String yaxis;
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
	public String getXaxis() {
		return xaxis;
	}
	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}
	public String getYaxis() {
		return yaxis;
	}
	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}
}
