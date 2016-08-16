package com.gome.upm.domain;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 报警日志实体类
 * @author caowei-ds1
 * @date 2016年6月22日
 */
public class AlarmRecord {
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Properties props = new Properties();
	
	{
		try {
//			System.out.println("path:" + AlarmRecord.class.getClassLoader().getResource("alarm.properties").getPath());
			InputStream in = AlarmRecord.class.getClassLoader().getResourceAsStream("alarm.properties");
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 主键ID */
	private Long id;
	
	/** 类型 */
	private String type;
	
	/** 类型描述 */
	private String typeStr;
	
	/** 报警内容 */
	private String content;
	
	/** 关联ID(url、port表主键id) */
	private Long pid;
	
	/** 报警时间 */
	private Date sendTime;
	
	/** 报警时间字符串 */
	private String sendTimeStr;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.typeStr = props.getProperty(type);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
		if(sendTime != null){
			this.sendTimeStr = df.format(sendTime);
		}
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
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

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

}
