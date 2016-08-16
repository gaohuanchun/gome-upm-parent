package com.gome.upm.common;
/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */


/**
 * 远程调用运行时异常
 * 
 * @author niulu
 * @date 2013-11-26
 */
public class MshopRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3243492968283757237L;

	public MshopRuntimeException(String message) {
		super(message);
	}

	public MshopRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
