package com.gome.upm.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 版本实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月23日    caowei    新建
 * </pre>
 */
public class Version {
	
	DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	//版本ID
	private Long id;
	
	//软件包地址
	private String url;
	
	//版本号
	private String version;
	
	//创建时间
	private Date createTime;
	
	//创建人
	private String createUser;
	
	//操作账号
	private String operateUser;
	
	private String createTimeStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		if(createTime != null){
			this.createTimeStr = df.format(createTime);
		}
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
		
}
