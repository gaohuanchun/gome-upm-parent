package com.gome.upm.domain.prtg;

import java.util.Date;

public class MoNetSensor {

	public static final Integer NORMAL_STATE = 0;
	public static final Integer STOP_STATE = 1;
	public static final Integer ALARM_STATE = 2;
	private Integer sensorId;

	private String remark;

	private String probe;

	private Integer groupId;

	private String groupName;

	private Integer deviceId;

	private String deviceName;

	private String sensorName;

	private String status;

	private Integer statusRaw;

	private String message;

	private String messageRaw;

	private String lastvalue;

	private String lastvalueRaw;

	private Integer priority;

	private String favorite;

	private Integer favoriteRaw;

	private Integer state;

	private String type;

	private Date alarmTime;
	
	private String alarmTimeStr;

	private Date sdate;

	private Date edate;

	private Double lastvalueUse;

	private String alarmContent;

	private String host;
	
	

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public Double getLastvalueUse() {
		return lastvalueUse;
	}

	public void setLastvalueUse(Double lastvalueUse) {
		this.lastvalueUse = lastvalueUse;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getProbe() {
		return probe;
	}

	public void setProbe(String probe) {
		this.probe = probe == null ? null : probe.trim();
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName == null ? null : deviceName.trim();
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName == null ? null : sensorName.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Integer getStatusRaw() {
		return statusRaw;
	}

	public void setStatusRaw(Integer statusRaw) {
		this.statusRaw = statusRaw;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public String getMessageRaw() {
		return messageRaw;
	}

	public void setMessageRaw(String messageRaw) {
		this.messageRaw = messageRaw == null ? null : messageRaw.trim();
	}

	public String getLastvalue() {
		return lastvalue;
	}

	public void setLastvalue(String lastvalue) {
		this.lastvalue = lastvalue == null ? null : lastvalue.trim();
	}

	public String getLastvalueRaw() {
		return lastvalueRaw;
	}

	public void setLastvalueRaw(String lastvalueRaw) {
		this.lastvalueRaw = lastvalueRaw == null ? null : lastvalueRaw.trim();
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite == null ? null : favorite.trim();
	}

	public Integer getFavoriteRaw() {
		return favoriteRaw;
	}

	public void setFavoriteRaw(Integer favoriteRaw) {
		this.favoriteRaw = favoriteRaw;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
}