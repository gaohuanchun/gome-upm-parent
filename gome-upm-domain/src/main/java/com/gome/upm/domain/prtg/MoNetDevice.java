package com.gome.upm.domain.prtg;

import java.util.Date;

public class MoNetDevice {

	public static final Integer NORMAL_STATE = 0;
	public static final Integer STOP_STATE = 1;
	public static final Integer ALARM_STATE = 2;
	private Integer deviceId;

	private String probeName;

	private Integer groupId;

	private String groupName;

	private String deviceName;

	private String host;

	private String downsens;

	private Integer downsensRaw;

	private String partialdownsens;

	private Integer partialdownsensRaw;

	private String downacksens;

	private Integer downacksensRaw;

	private String upsens;

	private Integer upsensRaw;

	private String warnsens;

	private Integer warnsensRaw;

	private String pausedsens;

	private Integer pausedsensRaw;

	private String unusualsens;

	private Integer unusualsensRaw;

	private String undefinedsens;

	private Integer undefinedsensRaw;

	private Integer state;

	private Date alarmTime;

	private String alarmTimeStr;

	private String alarmContent;

	private Integer sensorError;

	private Integer sensorTotal;

	private Date sdate;

	private Date edate;

	/**
	 * 设备优先级
	 */
	private int priority;

	/**
	 * 设备状态
	 */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
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

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName == null ? null : probeName.trim();
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName == null ? null : deviceName.trim();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host == null ? null : host.trim();
	}

	public String getDownsens() {
		return downsens;
	}

	public void setDownsens(String downsens) {
		this.downsens = downsens == null ? null : downsens.trim();
	}

	public Integer getDownsensRaw() {
		return downsensRaw;
	}

	public void setDownsensRaw(Integer downsensRaw) {
		this.downsensRaw = downsensRaw;
	}

	public String getPartialdownsens() {
		return partialdownsens;
	}

	public void setPartialdownsens(String partialdownsens) {
		this.partialdownsens = partialdownsens == null ? null : partialdownsens.trim();
	}

	public Integer getPartialdownsensRaw() {
		return partialdownsensRaw;
	}

	public void setPartialdownsensRaw(Integer partialdownsensRaw) {
		this.partialdownsensRaw = partialdownsensRaw;
	}

	public String getDownacksens() {
		return downacksens;
	}

	public void setDownacksens(String downacksens) {
		this.downacksens = downacksens == null ? null : downacksens.trim();
	}

	public Integer getDownacksensRaw() {
		return downacksensRaw;
	}

	public void setDownacksensRaw(Integer downacksensRaw) {
		this.downacksensRaw = downacksensRaw;
	}

	public String getUpsens() {
		return upsens;
	}

	public void setUpsens(String upsens) {
		this.upsens = upsens == null ? null : upsens.trim();
	}

	public Integer getUpsensRaw() {
		return upsensRaw;
	}

	public void setUpsensRaw(Integer upsensRaw) {
		this.upsensRaw = upsensRaw;
	}

	public String getWarnsens() {
		return warnsens;
	}

	public void setWarnsens(String warnsens) {
		this.warnsens = warnsens == null ? null : warnsens.trim();
	}

	public Integer getWarnsensRaw() {
		return warnsensRaw;
	}

	public void setWarnsensRaw(Integer warnsensRaw) {
		this.warnsensRaw = warnsensRaw;
	}

	public String getPausedsens() {
		return pausedsens;
	}

	public void setPausedsens(String pausedsens) {
		this.pausedsens = pausedsens == null ? null : pausedsens.trim();
	}

	public Integer getPausedsensRaw() {
		return pausedsensRaw;
	}

	public void setPausedsensRaw(Integer pausedsensRaw) {
		this.pausedsensRaw = pausedsensRaw;
	}

	public String getUnusualsens() {
		return unusualsens;
	}

	public void setUnusualsens(String unusualsens) {
		this.unusualsens = unusualsens == null ? null : unusualsens.trim();
	}

	public Integer getUnusualsensRaw() {
		return unusualsensRaw;
	}

	public void setUnusualsensRaw(Integer unusualsensRaw) {
		this.unusualsensRaw = unusualsensRaw;
	}

	public String getUndefinedsens() {
		return undefinedsens;
	}

	public void setUndefinedsens(String undefinedsens) {
		this.undefinedsens = undefinedsens == null ? null : undefinedsens.trim();
	}

	public Integer getUndefinedsensRaw() {
		return undefinedsensRaw;
	}

	public void setUndefinedsensRaw(Integer undefinedsensRaw) {
		this.undefinedsensRaw = undefinedsensRaw;
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

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent == null ? null : alarmContent.trim();
	}

	public Integer getSensorError() {
		return sensorError;
	}

	public void setSensorError(Integer sensorError) {
		this.sensorError = sensorError;
	}

	public Integer getSensorTotal() {
		return sensorTotal;
	}

	public void setSensorTotal(Integer sensorTotal) {
		this.sensorTotal = sensorTotal;
	}
}