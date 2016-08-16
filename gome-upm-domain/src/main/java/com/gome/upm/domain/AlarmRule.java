package com.gome.upm.domain;

import java.sql.Timestamp;

/**
 * 告警实体类目前暂不使用
 * @author zhouyaliang
 *
 */
public class AlarmRule {
    private String ruleId;
    private String appId;
    private String uid;
    private String configArgs;
    private String isGlobal;
    private String todoType;
    private Timestamp createTime;
    private String sts;
    private Timestamp modifyTime;
    
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getConfigArgs() {
		return configArgs;
	}

	public void setConfigArgs(String configArgs) {
		this.configArgs = configArgs;
	}

	public void setIsGlobal(String isGlobal) {
        this.isGlobal = isGlobal;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getTodoType() {
        return todoType;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getCreateTime() {
        if (createTime == null)
            return new Timestamp(System.currentTimeMillis());
        return createTime;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getSts() {
        return sts;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Timestamp getModifyTime() {
        if (modifyTime == null)
            return new Timestamp(System.currentTimeMillis());
        return modifyTime;
    }


    public String getUid() {
        return uid;
    }

    public String getIsGlobal() {
        return isGlobal;
    }

    @Override
    public String toString() {
        return "AlarmRule{" +
                "ruleId='" + ruleId + '\'' +
                ", appId='" + appId + '\'' +
                ", uid='" + uid + '\'' +
                ", configArgs=" + configArgs +
                ", isGlobal='" + isGlobal + '\'' +
                ", todoType='" + todoType + '\'' +
                ", createTime=" + createTime +
                ", sts='" + sts + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }

    public String getRuleId() {
        return ruleId;
    }
}
