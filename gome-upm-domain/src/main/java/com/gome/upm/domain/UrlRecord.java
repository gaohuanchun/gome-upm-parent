package com.gome.upm.domain;

import java.util.Date;

/**
 * url监控记录实体类
 * @author caowei-ds1
 * @date 2016年6月21日
 */
public class UrlRecord {
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
	
	/** 状态码 */
	private String returnCode;
	/** 状态码中文说明*/
	private String returnCodeStr;
	
	/** 是否存活（1：存活；0：不存活） */
	private Integer survival;
	
	/** 是否匹配(1:匹配;0:不匹配)*/
	private Integer matching;
	
	/** 是否存活字符串 */
	private String survivalStr;
	
	/** 是否匹配字符串*/
	private String matchingStr;
	
	/** 外键 */
	private Long uid;
	
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

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}

	public Integer getMatching() {
		return matching;
	}

	public void setMatching(Integer matching) {
		this.matching = matching;
	}

	public String getMatchingStr() {
		return matchingStr;
	}

	public void setMatchingStr(String matchingStr) {
		this.matchingStr = matchingStr;
	}

	public String getReturnCodeStr() {
		if(returnCode!=null){
			if(returnCode.equals("200")){
				return "一切正常";
			}else if(returnCode.equals("301")){
				return "永久重定向";
			}else if(returnCode.equals("302")){
				return "暂时重定向";
			}else if(returnCode.equals("400")){
				return "请求无效";
			}else if(returnCode.equals("403")){
				return "禁止访问";
			}else if(returnCode.equals("404")){
				return "无法找到文件";
			}else if(returnCode.equals("500")){
				return "访问应用服务器错误";
			}else if(returnCode.equals("502")){
				return "网关错误";
			}else{
				return "访问失败";
			}
				
		}
		return returnCodeStr;
	}

	public void setReturnCodeStr(String returnCodeStr) {
		this.returnCodeStr = returnCodeStr;
	}
	
	

}
