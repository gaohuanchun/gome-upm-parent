package com.gome.upm.domain.prtg;

import java.util.Date;

public class MoNetHistory {
    private Integer sensorId;

    private String dateTime;

    private String dateTimeRaw;

    private String communicationRoll;

    private String communicationRollValue;

    private String communicationSpeed;

    private String communicationSpeedValue;

    private String inCommunicationRoll;

    private String inCommunicationRollValue;

    private String inCommunicationSpeed;

    private String inCommunicationSpeedValue;

    private String outCommunicationRoll;

    private String outCommunicationRollValue;

    private String outCommunicationSpeed;

    private String outCommunicationSpeedValue;

    private String haltTime;

    private String haltTimeValue;

    private String coverage;

    private String coverageRaw;

    private String cpuMemVal;

    private String cpuMemValRaw;

    private String type;
    private Date createTime;
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }

    public String getDateTimeRaw() {
        return dateTimeRaw;
    }

    public void setDateTimeRaw(String dateTimeRaw) {
        this.dateTimeRaw = dateTimeRaw == null ? null : dateTimeRaw.trim();
    }

    public String getCommunicationRoll() {
        return communicationRoll;
    }

    public void setCommunicationRoll(String communicationRoll) {
        this.communicationRoll = communicationRoll == null ? null : communicationRoll.trim();
    }

    public String getCommunicationRollValue() {
        return communicationRollValue;
    }

    public void setCommunicationRollValue(String communicationRollValue) {
        this.communicationRollValue = communicationRollValue == null ? null : communicationRollValue.trim();
    }

    public String getCommunicationSpeed() {
        return communicationSpeed;
    }

    public void setCommunicationSpeed(String communicationSpeed) {
        this.communicationSpeed = communicationSpeed == null ? null : communicationSpeed.trim();
    }

    public String getCommunicationSpeedValue() {
        return communicationSpeedValue;
    }

    public void setCommunicationSpeedValue(String communicationSpeedValue) {
        this.communicationSpeedValue = communicationSpeedValue == null ? null : communicationSpeedValue.trim();
    }

    public String getInCommunicationRoll() {
        return inCommunicationRoll;
    }

    public void setInCommunicationRoll(String inCommunicationRoll) {
        this.inCommunicationRoll = inCommunicationRoll == null ? null : inCommunicationRoll.trim();
    }

    public String getInCommunicationRollValue() {
        return inCommunicationRollValue;
    }

    public void setInCommunicationRollValue(String inCommunicationRollValue) {
        this.inCommunicationRollValue = inCommunicationRollValue == null ? null : inCommunicationRollValue.trim();
    }

    public String getInCommunicationSpeed() {
        return inCommunicationSpeed;
    }

    public void setInCommunicationSpeed(String inCommunicationSpeed) {
        this.inCommunicationSpeed = inCommunicationSpeed == null ? null : inCommunicationSpeed.trim();
    }

    public String getInCommunicationSpeedValue() {
        return inCommunicationSpeedValue;
    }

    public void setInCommunicationSpeedValue(String inCommunicationSpeedValue) {
        this.inCommunicationSpeedValue = inCommunicationSpeedValue == null ? null : inCommunicationSpeedValue.trim();
    }

    public String getOutCommunicationRoll() {
        return outCommunicationRoll;
    }

    public void setOutCommunicationRoll(String outCommunicationRoll) {
        this.outCommunicationRoll = outCommunicationRoll == null ? null : outCommunicationRoll.trim();
    }

    public String getOutCommunicationRollValue() {
        return outCommunicationRollValue;
    }

    public void setOutCommunicationRollValue(String outCommunicationRollValue) {
        this.outCommunicationRollValue = outCommunicationRollValue == null ? null : outCommunicationRollValue.trim();
    }

    public String getOutCommunicationSpeed() {
        return outCommunicationSpeed;
    }

    public void setOutCommunicationSpeed(String outCommunicationSpeed) {
        this.outCommunicationSpeed = outCommunicationSpeed == null ? null : outCommunicationSpeed.trim();
    }

    public String getOutCommunicationSpeedValue() {
        return outCommunicationSpeedValue;
    }

    public void setOutCommunicationSpeedValue(String outCommunicationSpeedValue) {
        this.outCommunicationSpeedValue = outCommunicationSpeedValue == null ? null : outCommunicationSpeedValue.trim();
    }

    public String getHaltTime() {
        return haltTime;
    }

    public void setHaltTime(String haltTime) {
        this.haltTime = haltTime == null ? null : haltTime.trim();
    }

    public String getHaltTimeValue() {
        return haltTimeValue;
    }

    public void setHaltTimeValue(String haltTimeValue) {
        this.haltTimeValue = haltTimeValue == null ? null : haltTimeValue.trim();
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage == null ? null : coverage.trim();
    }

    public String getCoverageRaw() {
        return coverageRaw;
    }

    public void setCoverageRaw(String coverageRaw) {
        this.coverageRaw = coverageRaw == null ? null : coverageRaw.trim();
    }

    public String getCpuMemVal() {
        return cpuMemVal;
    }

    public void setCpuMemVal(String cpuMemVal) {
        this.cpuMemVal = cpuMemVal == null ? null : cpuMemVal.trim();
    }

    public String getCpuMemValRaw() {
        return cpuMemValRaw;
    }

    public void setCpuMemValRaw(String cpuMemValRaw) {
        this.cpuMemValRaw = cpuMemValRaw == null ? null : cpuMemValRaw.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}