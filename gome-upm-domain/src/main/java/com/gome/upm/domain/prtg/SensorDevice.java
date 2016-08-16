package com.gome.upm.domain.prtg;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 网络监控设备实体类
 * @author zhangzhixiang-ds
 *
 */
public class SensorDevice implements Serializable {

	private static final long serialVersionUID = 3839409841588277153L;
	
	@JSONField(name = "objid")
	private String objId;
	
	@JSONField(name = "probe")
	private String probe;
	
	@JSONField(name = "group")
	private String group;
	
	@JSONField(name = "device")
	private String device;
	
	@JSONField(name = "host")
	private String host;
	
	@JSONField(name = "downsens")
	private String downsens;
	
	@JSONField(name = "downsens_raw")
	private String downsensRaw;
	
	@JSONField(name = "downacksens")
	private String downacksens;
	
	@JSONField(name = "downacksens_raw")
	private String downacksensRaw;
	
    @JSONField(name = "upsens")
	private String upsens;
	
	@JSONField(name = "upsens_raw")
	private String upsensRaw;
	
	@JSONField(name = "pausedsens")
	private String pausedsens;
	
	@JSONField(name = "pausedsens_raw")
	private String pausedsensRaw;
	
	@JSONField(name = "unusualsens")
	private String unusualsens;
	
	@JSONField(name = "unusualsens_raw")
	private String unusualsensRaw;
	
	@JSONField(name = "undefinedsens")
	private String undefinedsens;
	
	@JSONField(name = "undefinedsens_raw")
	private String undefinedsensRaw;
	
	@JSONField(name = "warnsens")
	private String warnsens;
	
	@JSONField(name = "warnsens_raw")
	private String warnsensRaw;
	
	@JSONField(name = "partialdownsens")
	private String partialdownsens;
	
	@JSONField(name = "partialdownsens_raw")
	private String partialdownsensRaw;
	
	/**
	 * 设备状态
	 */
	private String status;
	
	/**
	 * 设备优先级
	 */
	private int priority;
	
	/**
	 * 设备下正常的传感器数量
	 */
	private int normalSensor;
	
	/**
	 * 设备下总的传感器数量
	 */
	private int totalSensor;
	
	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDownacksensRaw() {
		return downacksensRaw;
	}

	public void setDownacksensRaw(String downacksensRaw) {
		this.downacksensRaw = downacksensRaw;
	}

	public String getDownacksens() {
		return downacksens;
	}

	public void setDownacksens(String downacksens) {
		this.downacksens = downacksens;
	}

	public String getUpsens() {
		return upsens;
	}

	public void setUpsens(String upsens) {
		this.upsens = upsens;
	}

	public String getUpsensRaw() {
		return upsensRaw;
	}

	public void setUpsensRaw(String upsensRaw) {
		this.upsensRaw = upsensRaw;
	}

	public String getPausedsens() {
		return pausedsens;
	}

	public void setPausedsens(String pausedsens) {
		this.pausedsens = pausedsens;
	}

	public String getPausedsensRaw() {
		return pausedsensRaw;
	}

	public void setPausedsensRaw(String pausedsensRaw) {
		this.pausedsensRaw = pausedsensRaw;
	}

	public String getUnusualsens() {
		return unusualsens;
	}

	public void setUnusualsens(String unusualsens) {
		this.unusualsens = unusualsens;
	}

	public String getUndefinedsensRaw() {
		return undefinedsensRaw;
	}

	public void setUndefinedsensRaw(String undefinedsensRaw) {
		this.undefinedsensRaw = undefinedsensRaw;
	}

	public String getProbe() {
		return probe;
	}

	public void setProbe(String probe) {
		this.probe = probe;
	}

	public String getDownsensRaw() {
		return downsensRaw;
	}

	public void setDownsensRaw(String downsensRaw) {
		this.downsensRaw = downsensRaw;
	}

	public String getWarnsensRaw() {
		return warnsensRaw;
	}

	public void setWarnsensRaw(String warnsensRaw) {
		this.warnsensRaw = warnsensRaw;
	}

	public String getWarnsens() {
		return warnsens;
	}

	public void setWarnsens(String warnsens) {
		this.warnsens = warnsens;
	}

	public String getPartialdownsensRaw() {
		return partialdownsensRaw;
	}

	public void setPartialdownsensRaw(String partialdownsensRaw) {
		this.partialdownsensRaw = partialdownsensRaw;
	}

	public String getUnusualsensRaw() {
		return unusualsensRaw;
	}

	public void setUnusualsensRaw(String unusualsensRaw) {
		this.unusualsensRaw = unusualsensRaw;
	}

	public String getPartialdownsens() {
		return partialdownsens;
	}

	public void setPartialdownsens(String partialdownsens) {
		this.partialdownsens = partialdownsens;
	}

	public String getDownsens() {
		return downsens;
	}

	public void setDownsens(String downsens) {
		this.downsens = downsens;
	}

	public String getUndefinedsens() {
		return undefinedsens;
	}

	public void setUndefinedsens(String undefinedsens) {
		this.undefinedsens = undefinedsens;
	}

	
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

	public int getNormalSensor() {
		return normalSensor;
	}

	public void setNormalSensor(int normalSensor) {
		this.normalSensor = normalSensor;
	}

	public int getTotalSensor() {
		return totalSensor;
	}

	public void setTotalSensor(int totalSensor) {
		this.totalSensor = totalSensor;
	}

	@Override
	public String toString() {
		return "SensorDevice [objId=" + objId + ", group=" + group + ", device=" + device + ", host=" + host
				+ ", downacksensRaw=" + downacksensRaw + ", downacksens=" + downacksens + ", upsens=" + upsens
				+ ", upsensRaw=" + upsensRaw + ", pausedsens=" + pausedsens + ", pausedsensRaw=" + pausedsensRaw
				+ ", unusualsens=" + unusualsens + ", undefinedsensRaw=" + undefinedsensRaw + ", probe=" + probe
				+ ", downsensRaw=" + downsensRaw + ", warnsensRaw=" + warnsensRaw + ", warnsens=" + warnsens
				+ ", partialdownsensRaw=" + partialdownsensRaw + ", unusualsensRaw=" + unusualsensRaw
				+ ", partialdownsens=" + partialdownsens + ", downsens=" + downsens + ", undefinedsens=" + undefinedsens
				+ "]";
	}
	
	
}
