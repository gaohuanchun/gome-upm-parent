package com.gome.upm.domain.prtg;

import java.io.Serializable;

/**
 * 首页top5
 * @author zhangzhixiang-ds
 *
 */
public class IndexTOP5 implements Comparable<IndexTOP5>, Serializable {

	private static final long serialVersionUID = -7664279378915920789L;
	
	/**
	 * 设备id
	 */
	private String deviceId;
	
	/**
	 * 设备名称
	 */
	private String deviceName;
	
	/**
	 * 传感器id
	 */
	private String sensorId;
	
	/**
	 * 传感器名称
	 */
	private String sensorName;
	
	/**
	 * 传感器lastvalue值
	 */
	private Double lastVal;
	
	private String host;
	
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public Double getLastVal() {
		return lastVal;
	}

	public void setLastVal(Double lastVal) {
		this.lastVal = lastVal;
	}

	@Override
	public int compareTo(IndexTOP5 o) {
		return o.getLastVal().compareTo(lastVal);
	}

	@Override
	public String toString() {
		return "IndexTOP5 [deviceId=" + deviceId + ", deviceName=" + deviceName + ", sensorId=" + sensorId
				+ ", sensorName=" + sensorName + ", lastVal=" + lastVal + ", host=" + host + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((lastVal == null) ? 0 : lastVal.hashCode());
		result = prime * result + ((sensorId == null) ? 0 : sensorId.hashCode());
		result = prime * result + ((sensorName == null) ? 0 : sensorName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndexTOP5 other = (IndexTOP5) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (lastVal == null) {
			if (other.lastVal != null)
				return false;
		} else if (!lastVal.equals(other.lastVal))
			return false;
		if (sensorId == null) {
			if (other.sensorId != null)
				return false;
		} else if (!sensorId.equals(other.sensorId))
			return false;
		if (sensorName == null) {
			if (other.sensorName != null)
				return false;
		} else if (!sensorName.equals(other.sensorName))
			return false;
		return true;
	}


    


	
	

}
