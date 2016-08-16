package com.gome.upm.domain;

import java.util.Date;

/**
 * 端口监控记录实体类
 * @author caowei-ds1
 * @date 2016年6月23日
 */
public class PortRecord {
	/** 主键ID */
	private Long id;
	
	/** 访问时间 */
	private Date visitTime;
	
	/** 访问时间字符串 */
	private String visitTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
	/** 访问耗时（单位为毫秒） */
	private Integer visitTake;
	
	/** 是否存活（1：存活；0：不存活） */
	private Integer survival;
	
	/** 是否存活字符串 */
	private String survivalStr;
	
	/** 外键 */
	private Long pid;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
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

	public Integer getVisitTake() {
		return visitTake;
	}

	public void setVisitTake(Integer visitTake) {
		this.visitTake = visitTake;
	}

	public Integer getSurvival() {
		return survival;
	}

	public void setSurvival(Integer survival) {
		this.survival = survival;
	}

	public String getSurvivalStr() {
		return survivalStr;
	}

	public void setSurvivalStr(String survivalStr) {
		this.survivalStr = survivalStr;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}


	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}

	
}
