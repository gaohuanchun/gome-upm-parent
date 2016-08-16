package com.gome.upm.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @Description: Url监控点
 * @author caowei-ds1
 * @date 2016年6月17日 上午10:55:57
 * @version V1.0
 *
 */
public class UrlMonitor {

	/** 主键ID */
	private Long id;
	
	/** key */
//	private String key;
	
	/** 描述 */
//	private String desc;
	
	/** url */
    @XmlElement(name = "url地址")
	private String url;
	
	/** 截取的url*/
	private String shortUrl;
	
	/** 访问频率(单位：分钟) */
	@XmlElement(name = "访问频率(5,10,15)分钟")
	private Integer frequency;
	
	/** 访问超时时间(单位：秒) */
	private Integer timeout;
	
	/** 超时次数  */
	@XmlElement(name = "超时次数(5,10,15)分钟")
	private Integer overtimes;
	
	/** 报警时间间隔(单位：分钟) */
	private Integer interval;
	
	/** 请求方式(目前只支持GET、POST) */
	private String requestMethod;
	
	/** POST请求参数 */
	private String postParameter;
	
	/** 匹配响应内容方式(include：包含；exclude：不包含) */
	@XmlElement(name = "匹配方式(包含匹配内容include,不包含exclude)")
	private String matchMethod;
	
	/** 需要匹配的内容 */
	@XmlElement(name = "匹配内容(可空)")
	private String matchContent;
	
	/** 返回码  */
	@XmlElement(name = "URL请求返回码(空为200,其它请输入)")
	private String returnCode;
	
	/** 报警方式(no：不报警；mail：邮件) */
	@XmlElement(name = "报警方式(no,email)")
	private String alarmMethod;
	
	/** 报警时间 */
	private Date alarmTime;
	
	/** 报警时间字符串 */
	private String alarmTimeStr;
	
	/** 配置状态（0：禁用；1：启用） */
	private Integer status;
	
	/** 配置状态字符串 */
	private String statusStr;
	
	/** 存活状态（0：不存活；1：存活;2:匹配异常） */
	private Integer survival;
	
	/** 存活状态字符串 */
	private String survivalStr;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 创建时间字符串 */
	private String createTimeStr;
	
	/** 更新时间 */
	private Date updateTime;
	
	/** 更新时间字符串 */
	private String updateTimeStr;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;

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

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(Integer overtimes) {
		this.overtimes = overtimes;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getMatchMethod() {
		return matchMethod;
	}

	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}

	public String getMatchContent() {
		return matchContent;
	}

	public void setMatchContent(String matchContent) {
		this.matchContent = matchContent;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getAlarmMethod() {
		return alarmMethod;
	}

	public void setAlarmMethod(String alarmMethod) {
		this.alarmMethod = alarmMethod;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSurvival() {
		return survival;
	}

	public void setSurvival(Integer survival) {
		this.survival = survival;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getSurvivalStr() {
		return survivalStr;
	}

	public void setSurvivalStr(String survivalStr) {
		this.survivalStr = survivalStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getAlarmTimeStr() {
		return alarmTimeStr;
	}

	public void setAlarmTimeStr(String alarmTimeStr) {
		this.alarmTimeStr = alarmTimeStr;
	}
	
	

	public String getPostParameter() {
		return postParameter;
	}

	public void setPostParameter(String postParameter) {
		this.postParameter = postParameter;
	}

	
	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@Override
	public String toString() {
		return "UrlMonitor [id=" + id + ", url=" + url + ", frequency=" + frequency + ", timeout=" + timeout
				+ ", overtimes=" + overtimes + ", interval=" + interval + ", requestMethod=" + requestMethod
				+ ", postParameter=" + postParameter + ", matchMethod=" + matchMethod + ", matchContent=" + matchContent
				+ ", returnCode=" + returnCode + ", alarmMethod=" + alarmMethod + ", alarmTime=" + alarmTime
				+ ", alarmTimeStr=" + alarmTimeStr + ", status=" + status + ", statusStr=" + statusStr + ", survival="
				+ survival + ", survivalStr=" + survivalStr + ", createTime=" + createTime + ", createTimeStr="
				+ createTimeStr + ", updateTime=" + updateTime + ", updateTimeStr=" + updateTimeStr + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

	

}
