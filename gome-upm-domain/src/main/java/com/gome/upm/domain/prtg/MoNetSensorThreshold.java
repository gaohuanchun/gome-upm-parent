package com.gome.upm.domain.prtg;

import java.util.Date;

public class MoNetSensorThreshold {
	public static final Integer NORMAL_STATE = 0;
	public static final Integer ALARM_STATE = 1;
	private Integer sensorId;

	private Double sensorThreshold;

	private String aliaName;

	private Integer state;

	private Date alarmTime;

	private String alarmType;

	private String groupName;

	private String deviceName;

	private String sensorName;

	private Integer sensorState;

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getSensorState() {
		return sensorState;
	}

	public void setSensorState(Integer sensorState) {
		this.sensorState = sensorState;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Double getSensorThreshold() {
		return sensorThreshold;
	}

	public void setSensorThreshold(Double sensorThreshold) {
		this.sensorThreshold = sensorThreshold;
	}

	public String getAliaName() {
		return aliaName;
	}

	public void setAliaName(String aliaName) {
		this.aliaName = aliaName == null ? null : aliaName.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
}