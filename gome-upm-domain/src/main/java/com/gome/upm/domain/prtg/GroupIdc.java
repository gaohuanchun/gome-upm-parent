package com.gome.upm.domain.prtg;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * prtg group
 * 
 * @author zhangzhixiang-ds
 *
 */
public class GroupIdc implements Serializable {

	private static final long serialVersionUID = -8404296395834293235L;

	@JSONField(name = "objid")
	private String objId;

	@JSONField(name = "probe")
	private String probe;

	@JSONField(name = "group")
	private String group;

	@JSONField(name = "name")
	private String name;
	
	@JSONField(name = "downsens")
	private String downsens;
	
	@JSONField(name = "partialdownsens")
	private String partialdownsens;
	
	@JSONField(name = "downacksens")
	private String downacksens;
	
	@JSONField(name = "upsens")
	private String upsens;
	
	@JSONField(name = "warnsens")
	private String warnsens;
	
	@JSONField(name = "pausedsens")
	private String pausedsens;
	
	@JSONField(name = "unusualsens")
	private String unusualsens;
	
	@JSONField(name = "undefinedsens")
	private String undefinedsens;

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getProbe() {
		return probe;
	}

	public void setProbe(String probe) {
		this.probe = probe;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getDownsens() {
		return downsens;
	}

	public void setDownsens(String downsens) {
		this.downsens = downsens;
	}

	public String getPartialdownsens() {
		return partialdownsens;
	}

	public void setPartialdownsens(String partialdownsens) {
		this.partialdownsens = partialdownsens;
	}

	public String getDownacksens() {
		return downacksens;
	}

	public void setDownacksens(String downacksens) {
		this.downacksens = downacksens;
	}

	public String getUpsens() {
		return upsens;
	}

	public void setUpsens(String upsens) {
		this.upsens = upsens;
	}

	public String getWarnsens() {
		return warnsens;
	}

	public void setWarnsens(String warnsens) {
		this.warnsens = warnsens;
	}

	public String getPausedsens() {
		return pausedsens;
	}

	public void setPausedsens(String pausedsens) {
		this.pausedsens = pausedsens;
	}

	public String getUnusualsens() {
		return unusualsens;
	}

	public void setUnusualsens(String unusualsens) {
		this.unusualsens = unusualsens;
	}

	public String getUndefinedsens() {
		return undefinedsens;
	}

	public void setUndefinedsens(String undefinedsens) {
		this.undefinedsens = undefinedsens;
	}

	@Override
	public String toString() {
		return "GroupIdc [objId=" + objId + ", probe=" + probe + ", group=" + group + ", name=" + name + "]";
	}

}
