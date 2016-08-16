package com.gome.upm.common;
/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */


/**
 * 因为参数校验，产生的异常
 * 
 * @author niulu
 * @date 2013-11-28
 */
public class MshopRpcUnCheckedException extends RuntimeException {

	private static final long serialVersionUID = 7332735613207702460L;

	public MshopRpcUnCheckedException(String message) {
		super(message);
	}

	public MshopRpcUnCheckedException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
