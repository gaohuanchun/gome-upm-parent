package com.gome.upm.domain;

import java.sql.Timestamp;

public class SystemConfig {
	private int configId;
	private String confKey;
	private String confValue;
	private String valueType;
	private String valueDesc;
    private Timestamp modifyTime;
    private Timestamp createTime;
    private String sts;

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}

	public String getConfKey() {
		return confKey;
	}

	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}

	public String getConfValue() {
		return confValue;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}

	public String getValueDesc() {
		return valueDesc;
	}
}
