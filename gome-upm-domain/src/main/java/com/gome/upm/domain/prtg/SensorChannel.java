package com.gome.upm.domain.prtg;

import java.io.Serializable;

/**
 * sensor channel
 * @author zhangzhixiang-ds
 *
 */
public class SensorChannel implements Serializable {

	private static final long serialVersionUID = -2535692384267748791L;
	
	private String name;
	
	private String lastValue;
	
	private String lastValueRaw;
	
	private String lastValueSpeed;
	
	private String lastValueRawSpeed;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastValue() {
		return lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}

	public String getLastValueRaw() {
		return lastValueRaw;
	}

	public void setLastValueRaw(String lastValueRaw) {
		this.lastValueRaw = lastValueRaw;
	}

	public String getLastValueSpeed() {
		return lastValueSpeed;
	}

	public void setLastValueSpeed(String lastValueSpeed) {
		this.lastValueSpeed = lastValueSpeed;
	}

	public String getLastValueRawSpeed() {
		return lastValueRawSpeed;
	}

	public void setLastValueRawSpeed(String lastValueRawSpeed) {
		this.lastValueRawSpeed = lastValueRawSpeed;
	}

	@Override
	public String toString() {
		return "SensorChannel [name=" + name + ", lastValue=" + lastValue + ", lastValueRaw=" + lastValueRaw
				+ ", lastValueSpeed=" + lastValueSpeed + ", lastValueRawSpeed=" + lastValueRawSpeed + "]";
	}
	
	

}
