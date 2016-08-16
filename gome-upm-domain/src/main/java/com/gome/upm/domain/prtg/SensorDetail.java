package com.gome.upm.domain.prtg;

import java.io.Serializable;

/**
 * sensor数据
 * 
 * @author zhangzhixiang-ds
 *
 */
public class SensorDetail implements Serializable {

	private static final long serialVersionUID = -1772660756753494140L;
	
	private String objId;

	private String name;

	private String sensorType;

	private String interval;

	private String probeName;

	private String parentGroupName;

	private String parentDeviceName;

	private String parentDeviceId;

	private String lastValue;

	private String lastMessage;

	private String favorite;

	private String statusText;

	private String statusId;

	private String lastUp;

	private String lastDown;

	private String lastCheck;

	private String upTime;

	private String upTimeTime;

	private String downTime;

	private String downTimeTime;

	private String upDownTotal;

	private String upDownSince;

	/**
	 * 传感器优先级
	 */
	private int priority;

	/**
	 * 传感器类型 flow other
	 */
	private String type;
	
	

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public String getParentGroupName() {
		return parentGroupName;
	}

	public void setParentGroupName(String parentGroupName) {
		this.parentGroupName = parentGroupName;
	}

	public String getParentDeviceName() {
		return parentDeviceName;
	}

	public void setParentDeviceName(String parentDeviceName) {
		this.parentDeviceName = parentDeviceName;
	}

	public String getParentDeviceId() {
		return parentDeviceId;
	}

	public void setParentDeviceId(String parentDeviceId) {
		this.parentDeviceId = parentDeviceId;
	}

	public String getLastValue() {
		return lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getLastUp() {
		return lastUp;
	}

	public void setLastUp(String lastUp) {
		this.lastUp = lastUp;
	}

	public String getLastDown() {
		return lastDown;
	}

	public void setLastDown(String lastDown) {
		this.lastDown = lastDown;
	}

	public String getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(String lastCheck) {
		this.lastCheck = lastCheck;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public String getUpTimeTime() {
		return upTimeTime;
	}

	public void setUpTimeTime(String upTimeTime) {
		this.upTimeTime = upTimeTime;
	}

	public String getDownTime() {
		return downTime;
	}

	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}

	public String getDownTimeTime() {
		return downTimeTime;
	}

	public void setDownTimeTime(String downTimeTime) {
		this.downTimeTime = downTimeTime;
	}

	public String getUpDownTotal() {
		return upDownTotal;
	}

	public void setUpDownTotal(String upDownTotal) {
		this.upDownTotal = upDownTotal;
	}

	public String getUpDownSince() {
		return upDownSince;
	}

	public void setUpDownSince(String upDownSince) {
		this.upDownSince = upDownSince;
	}

	@Override
	public String toString() {
		return "SensorDetail [name=" + name + ", sensorType=" + sensorType + ", interval=" + interval + ", probeName="
				+ probeName + ", parentGroupName=" + parentGroupName + ", parentDeviceName=" + parentDeviceName
				+ ", parentDeviceId=" + parentDeviceId + ", lastValue=" + lastValue + ", lastMessage=" + lastMessage
				+ ", favorite=" + favorite + ", statusText=" + statusText + ", statusId=" + statusId + ", lastUp="
				+ lastUp + ", lastDown=" + lastDown + ", lastCheck=" + lastCheck + ", upTime=" + upTime
				+ ", upTimeTime=" + upTimeTime + ", downTime=" + downTime + ", downTimeTime=" + downTimeTime
				+ ", upDownTotal=" + upDownTotal + ", upDownSince=" + upDownSince + "]";
	}

}
