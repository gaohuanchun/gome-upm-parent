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
public class MshopRpcRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3243492968283757237L;

	public MshopRpcRuntimeException(String message) {
		super(message);
	}

	public MshopRpcRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
