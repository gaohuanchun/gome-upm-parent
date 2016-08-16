package com.gome.upm.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.gome.upm.common.des.Des3;


/**
 * CookieUtil用来操作cookie的存取
 * 
 * @author liyujian
 *
 */
public class CookieUtil {
	/**
	 * 添加cookie,添加前先进行des加密再写进cookie
	 * 
	 * @param name
	 *            cookie的key
	 * @param value
	 *            cookie的value
	 * @param domain
	 *            domain
	 * @param path
	 *            path
	 * @param maxage
	 *            最长存活时间 单位为秒
	 * @param response
	 */
	public void addCookie(String name, String value, String domain,
			int maxage, String path, HttpServletResponse response) {
		try {
			// 加密
			if (value != null) {
				value = Des3.encode(value);
			}
			
			// 写cookie
			Cookie cookie = new Cookie(name, value);
			if (domain != null) {
				cookie.setDomain(domain);
			}
			cookie.setMaxAge(maxage);
			cookie.setPath(path);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 往根下面存一个cookie 
	 * @param name cookie的key
	 * 
	 * @param value
	 *            cookie的value
	 * @param domain
	 *            domain
	 * @param maxage
	 *            最长存活时间 单位为秒
	 * @param response
	 */
	public void addCookie(String name, String value, String domain,
			int maxage, HttpServletResponse response) {
		addCookie(name, value, domain, maxage, "/", response);
	}

	/**
	 * 从cookie值返回cookie值，如果没有返回 null
	 * 
	 * @param req
	 * @param name
	 * @return cookie的值
	 */
	public  String getCookie(HttpServletRequest request, String name) {
		
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(name)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}
	
	/**
	 * 从cookie值返回cookie值，如果没有返回 null
	 * 
	 * @param req
	 * @param name
	 * @return cookie的值
	 */
	public  String getCookieValues(HttpServletRequest request, String name) {
		
		String result = getCookie(request, name);
		try {
			if (StringUtils.isNotEmpty(result)) {
				result = Des3.decode(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public  void removeCookie(String name, String domain,
			HttpServletRequest request, HttpServletResponse response) {
		String cookieVal = getCookie(request, name);
		if (cookieVal != null) {
			addCookie(name, null, domain, 0, response);
		}
	}
}
