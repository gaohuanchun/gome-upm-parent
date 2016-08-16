package com.gome.upm.constants;

public enum UserCode {

	init(1, "初始化"), last(5, "最后一次机会"), lock(-1, "冻结");

	private int code;
	private String msg;

	private UserCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
