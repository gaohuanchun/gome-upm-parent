package com.gome.upm.domain;

/**
 * 报警日志实体类
 * @author caowei-ds1
 * @date 2016年6月22日
 */
public class ServerAlarmRecord {
	/** 主键ID */
	private Long id;
	
	/** 类型 */
	private String type;
	
	/** host */
	private String host;
	
	/** 服务器组 */
	private String groupName;
	
	/** 类型描述 */
	private String typeStr;
	/** 监控值 */
	private String alarmValue;
	
	/** 报警内容 */
	private String content;
	
	/** 报警时间 */
	private String alarmTime;
	/** 报警时间 */
	private String updateAlarmTime;
	/** 报警状态 */
	private String status;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	/** 监控项 */
	private String key_;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(String alarmValue) {
		this.alarmValue = alarmValue;
	}

	public String getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(String updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public String getKey_() {
		return key_;
	}

	public void setKey_(String key_) {
		this.key_ = key_;
	}
}
