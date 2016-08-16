package com.gome.upm.domain;

import java.sql.Timestamp;

/**
 * 业务线实体类
 * @author zhouyaliang
 */
public class BusinessLine {
	
    private String bcode;
    private String bname;
    private int data_order;
    private Timestamp updateTime;
    private Timestamp createTime;
    private String operator;
    private String bdesc;
    
    public BusinessLine(){}
    
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}

	public int getData_order() {
		return data_order;
	}

	public void setData_order(int data_order) {
		this.data_order = data_order;
	}

	public Timestamp getUpdateTime() {
		if(updateTime == null)
			return new Timestamp(System.currentTimeMillis());
		return updateTime;
	}
	public String getBcode() {
		return bcode;
	}

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	public String getBdesc() {
		return bdesc;
	}

	public void setBdesc(String bdesc) {
		this.bdesc = bdesc;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getCreateTime() {
		if(createTime == null)
			return new Timestamp(System.currentTimeMillis());
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
