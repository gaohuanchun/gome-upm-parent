package com.gome.upm.controler.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gome.upm.common.des.Des3;
import com.gome.upm.common.util.CookieUtil;
import com.gome.upm.constants.WebConstants;

/**
 * @Description 基础controller类
 * @author anguangcan
 * @date 2015-4-23 下午4:51:59
 * @version V1.0
 */

public class BaseController {

	/**
	 * @Description 获取客户端ip
	 * @author anguangcan
	 * @param request
	 * @return
	 */
	
	@Autowired
	private CookieUtil cookieUtil;

	protected String getRemortIP(HttpServletRequest request) {

		String ip = request.getHeader(WebConstants.X_FORWARD_FOR);

		return ip == null ? request.getRemoteAddr() : ip;
	}

	/**
	 * @Description 获取本地ip
	 * @author anguangcan
	 * @return
	 */

	protected String getLocalIp() {

		InetAddress netAddress = getInetAddress();
		if (netAddress == null)
			return null;

		return netAddress.getHostAddress();
	}

	/**
	 * @Description 获取本地主机名
	 * @author anguangcan
	 * @return 主机名称
	 */

	protected String getLocalHostName() {

		InetAddress netAddress = getInetAddress();

		if (netAddress == null)
			return null;

		return netAddress.getHostName();
	}

	/**
	 * @Description 获取InetAddress对象
	 * @author anguangcan
	 * @return
	 */

	public static InetAddress getInetAddress() {

		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("unknown host!");
		}
		return null;

	}

	/**
	 * @Description 初始化验证码
	 * @author anguangcan
	 * @return 验证码code
	 */

	protected String initValidCode() {
		Random rand = new Random();
		int validCode = rand.nextInt(9000) + 1000;
		String codeStr = String.valueOf(validCode);

		return codeStr;
	}

	/**
	 * @Description session存储
	 * @author anguangcan
	 * @param session
	 * @param key
	 * @param value
	 */

	protected void putSession(HttpSession session, String key, Object value) {
		session.setMaxInactiveInterval(600);
		session.setAttribute(key, value);
	}

	/**
	 * @Description 向session中put值
	 * @author anguangcan
	 * @param session
	 * @param key
	 *            session 值对应的key
	 * @param value
	 *            对应的value值
	 * @param interval
	 *            间隔时间(超时时间)
	 */

	protected void putSession(HttpSession session, String key, Object value,
			int interval) {
		session.setMaxInactiveInterval(interval);
		session.setAttribute(key, value);
	}

	/**
	 * @Description 获取session信息
	 * @author anguangcan
	 * @param session
	 *            session对象
	 * @param key
	 *            session中的key
	 * @return
	 */

	@SuppressWarnings("unchecked")
	protected <T> T getSession(HttpSession session, String key) {

		T t = (T) session.getAttribute(key);
		return t;
	}

	/**
	 * @Description 获取cookie数据
	 * @author liyujian
	 * @param request
	 * 
	 * @param name
	 *            cookie名称
	 * @return
	 */
	protected String getUserInfo(HttpServletRequest request, String param) {
		String result = cookieUtil.getCookie(request, param);
		try {
			if (StringUtils.isNotEmpty(result)) {
				result = Des3.decode(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	protected boolean setUserInfo(String name, String value, String domain, int maxAge, String path, HttpServletResponse response) {
		try {
			cookieUtil.addCookie(name, value, domain, maxAge, path, response);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected boolean deleteUserInfo(String name, String domain, HttpServletRequest request, HttpServletResponse response) {
		try {
			cookieUtil.removeCookie(name, domain, request, response);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
