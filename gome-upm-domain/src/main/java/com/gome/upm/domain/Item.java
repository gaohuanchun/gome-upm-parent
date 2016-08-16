package com.gome.upm.domain;

import java.io.Serializable;

/**
 * 网络监控实体类
 * <item>
    <datetime>2016/7/4 9:00:00 - 10:00:00</datetime>
    <datetime_raw>42555.0833333333</datetime_raw>
    <value channel="通信量合计 (卷)">0 KB</value>
    <value_raw channel="通信量合计 (卷)">0.0000</value_raw>
    <value channel="通信量合计 (速度)">0 KB/秒</value>
    <value_raw channel="通信量合计 (速度)">0.0000</value_raw>
    <value channel="入站通信量 (卷)">0 KB</value>
    <value_raw channel="入站通信量 (卷)">0.0000</value_raw>
    <value channel="入站通信量 (速度)">0 KB/秒</value>
    <value_raw channel="入站通信量 (速度)">0.0000</value_raw>
    <value channel="出站通信量 (卷)">0 KB</value>
    <value_raw channel="出站通信量 (卷)">0.0000</value_raw>
    <value channel="出站通信量 (速度)">0 KB/秒</value>
    <value_raw channel="出站通信量 (速度)">0.0000</value_raw>
    <value channel="停机时间">0 %</value>
    <value_raw channel="停机时间">0.0000</value_raw>
    <coverage>100 %</coverage>
    <coverage_raw>0000009999</coverage_raw>
   </item>
 * @author caowei-ds1
 * @date 2016年6月22日
 */
public class Item implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3767367073253095803L;
	private String datetime;
	private String datetime_raw;
	/**
	 * 通信量合计 (卷)<value channel="通信量合计 (卷)">0 KB</value>
	 */
	private String communication_roll;
	/**
	 * 通信量合计 (卷)值 <value_raw channel="通信量合计 (卷)">0.0000</value_raw>
	 */
	private String communication_roll_value;
	/**
	 * 通信量合计 (速度)<value channel="通信量合计 (速度)">0 KB</value>
	 */
	private String communication_speed;
	/**
	 * 通信量合计 (速度)值 <value_raw channel="通信量合计 (速度)">0.0000</value_raw>
	 */
	private String communication_speed_value;
	/**
	 * <value channel="入站通信量 (卷)">0 KB</value>
	 */
	private String in_communication_roll;
	/**
	 *  <value_raw channel="入站通信量 (卷)">0.0000</value_raw>
	 */
	private String in_communication_roll_value;
	/**
	 *   <value channel="入站通信量 (速度)">0 KB/秒</value>
	 */
	private String in_communication_speed;
	/**
	 * <value_raw channel="入站通信量 (速度)">0.0000</value_raw>
	 */
	private String in_communication_speed_value;
	/**
	 *  <value channel="出站通信量 (卷)">0 KB</value>
	 */
	private String out_communication_roll;
	/**
	 *   <value_raw channel="出站通信量 (卷)">0.0000</value_raw>
	 */
	private String out_communication_roll_value;
	/**
	 *  <value channel="出站通信量 (速度)">0 KB/秒</value>
	 */
	private String out_communication_speed;
	/**
	 * <value_raw channel="出站通信量 (速度)">0.0000</value_raw>
	 */
	private String out_communication_speed_value;
	/**
	 * <value channel="停机时间">0 %</value>
	 */
	private String halt_time;
	/**
	 *  <value_raw channel="停机时间">0.0000</value_raw>
	 */
	private String halt_time_value;
	
	private String coverage;
	private String coverage_raw;
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getDatetime_raw() {
		return datetime_raw;
	}
	public void setDatetime_raw(String datetime_raw) {
		this.datetime_raw = datetime_raw;
	}
	public String getCommunication_roll() {
		return communication_roll;
	}
	public void setCommunication_roll(String communication_roll) {
		this.communication_roll = communication_roll;
	}
	public String getCommunication_roll_value() {
		return communication_roll_value;
	}
	public void setCommunication_roll_value(String communication_roll_value) {
		this.communication_roll_value = communication_roll_value;
	}
	public String getCommunication_speed() {
		return communication_speed;
	}
	public void setCommunication_speed(String communication_speed) {
		this.communication_speed = communication_speed;
	}
	public String getCommunication_speed_value() {
		return communication_speed_value;
	}
	public void setCommunication_speed_value(String communication_speed_value) {
		this.communication_speed_value = communication_speed_value;
	}
	public String getIn_communication_roll() {
		return in_communication_roll;
	}
	public void setIn_communication_roll(String in_communication_roll) {
		this.in_communication_roll = in_communication_roll;
	}
	public String getIn_communication_roll_value() {
		return in_communication_roll_value;
	}
	public void setIn_communication_roll_value(String in_communication_roll_value) {
		this.in_communication_roll_value = in_communication_roll_value;
	}
	public String getIn_communication_speed() {
		return in_communication_speed;
	}
	public void setIn_communication_speed(String in_communication_speed) {
		this.in_communication_speed = in_communication_speed;
	}
	public String getIn_communication_speed_value() {
		return in_communication_speed_value;
	}
	public void setIn_communication_speed_value(String in_communication_speed_value) {
		this.in_communication_speed_value = in_communication_speed_value;
	}
	public String getOut_communication_roll() {
		return out_communication_roll;
	}
	public void setOut_communication_roll(String out_communication_roll) {
		this.out_communication_roll = out_communication_roll;
	}
	public String getOut_communication_roll_value() {
		return out_communication_roll_value;
	}
	public void setOut_communication_roll_value(String out_communication_roll_value) {
		this.out_communication_roll_value = out_communication_roll_value;
	}
	public String getOut_communication_speed() {
		return out_communication_speed;
	}
	public void setOut_communication_speed(String out_communication_speed) {
		this.out_communication_speed = out_communication_speed;
	}
	public String getOut_communication_speed_value() {
		return out_communication_speed_value;
	}
	public void setOut_communication_speed_value(String out_communication_speed_value) {
		this.out_communication_speed_value = out_communication_speed_value;
	}
	public String getHalt_time() {
		return halt_time;
	}
	public void setHalt_time(String halt_time) {
		this.halt_time = halt_time;
	}
	public String getHalt_time_value() {
		return halt_time_value;
	}
	public void setHalt_time_value(String halt_time_value) {
		this.halt_time_value = halt_time_value;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public String getCoverage_raw() {
		return coverage_raw;
	}
	public void setCoverage_raw(String coverage_raw) {
		this.coverage_raw = coverage_raw;
	}
}
