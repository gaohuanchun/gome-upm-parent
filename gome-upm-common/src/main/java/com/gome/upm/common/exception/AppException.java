/*
 * Copyright (c) 2014 www.jd.com All rights reserved.
 * 本软件源代码版权归京东成都云平台所有,未经许可不得任意复制与传播.
 */
package com.gome.upm.common.exception;

/**
 * AppException
 * @author J-ONE
 * @since 2014-03-18
 */
public class AppException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private int code;
	
	public AppException() {
		super();
	}
	
	public AppException(String message) {
		super(message);
	}
	
	public AppException(String message, int errCode) {
		super(message);
		this.code = errCode;
	}
	
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AppException(Throwable cause) {
		super(cause);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
