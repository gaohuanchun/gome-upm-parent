/**
* Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
*/

package com.gome.upm.common.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author wangx
 * @date 2013-11-22
 */
public class ResponseUtils {
	private static final Logger log = Logger.getLogger(ResponseUtils.class);
	
	/**
	 * 发送文本。使用UTF-8编码。
	 * @param response	HttpServletResponse
	 * @param text		发送的字符串
	 */
	public static void renderHtml(HttpServletResponse response, String text) {
		render(response, "text/html;charset=UTF-8", text);
	}

	/**
	 * 发送文本。使用UTF-8编码。
	 * @param response	HttpServletResponse
	 * @param text		发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * @param response	HttpServletResponse
	 * @param text		 发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * @param response	HttpServletResponse
	 * @param text		 发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}

