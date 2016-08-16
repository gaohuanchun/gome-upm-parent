package com.gome.upm.domain;

/**
 * 
 * 服务器监控实体类Trigger.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年06月21日    liuhk    新建
 * </pre>
 */
public class Trigger {
	/** 主键ID */
	private Long id;
	//groupids
	private String priority;
	//hostId
	private String description;
	//host
	private String status;
	//graphids
	private String expression;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
}
