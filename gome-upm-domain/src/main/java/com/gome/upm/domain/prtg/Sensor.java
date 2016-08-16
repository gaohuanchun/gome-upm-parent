package com.gome.upm.domain.prtg;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * sesnsor实体类
 * @author zhangzhixiang-ds
 *
 */
public class Sensor implements Serializable {

	private static final long serialVersionUID = 6889495101021958778L;
	
	@JSONField(name = "objid")
	private String objId;
	
	@JSONField(name = "probe")
	private String probe;
	
	@JSONField(name = "group")
	private String group;
	
	@JSONField(name = "device")
	private String device;
	
	@JSONField(name = "sensor")
	private String sensor;
	
	@JSONField(name = "status")
	private String status;
	
	@JSONField(name = "status_raw")
	private String statusRaw;
	
	@JSONField(name = "message")
	private String message;
	
	@JSONField(name = "message_raw")
	private String messageRaw;
	
	@JSONField(name = "lastvalue")
	private String lastvalue;
	
	@JSONField(name = "lastvalue_raw")
	private String lastvalueRaw;
	
	@JSONField(name = "priority")
	private String priority;
	
	@JSONField(name = "favorite")
	private String favorite;
	
	@JSONField(name = "favorite_raw")
	private String favoriteRaw;

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getProbe() {
		return probe;
	}

	public void setProbe(String probe) {
		this.probe = probe;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusRaw() {
		return statusRaw;
	}

	public void setStatusRaw(String statusRaw) {
		this.statusRaw = statusRaw;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageRaw() {
		return messageRaw;
	}

	public void setMessageRaw(String messageRaw) {
		this.messageRaw = messageRaw;
	}

	public String getLastvalue() {
		return lastvalue;
	}

	public void setLastvalue(String lastvalue) {
		this.lastvalue = lastvalue;
	}

	public String getLastvalueRaw() {
		return lastvalueRaw;
	}

	public void setLastvalueRaw(String lastvalueRaw) {
		this.lastvalueRaw = lastvalueRaw;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getFavoriteRaw() {
		return favoriteRaw;
	}

	public void setFavoriteRaw(String favoriteRaw) {
		this.favoriteRaw = favoriteRaw;
	}

	@Override
	public String toString() {
		return "Sensor [objId=" + objId + ", probe=" + probe + ", group=" + group + ", device=" + device + ", sensor="
				+ sensor + ", status=" + status + ", statusRaw=" + statusRaw + ", message=" + message + ", messageRaw="
				+ messageRaw + ", lastvalue=" + lastvalue + ", lastvalueRaw=" + lastvalueRaw + ", priority=" + priority
				+ ", favorite=" + favorite + ", favoriteRaw=" + favoriteRaw + "]";
	}
	
	
	
	

}
