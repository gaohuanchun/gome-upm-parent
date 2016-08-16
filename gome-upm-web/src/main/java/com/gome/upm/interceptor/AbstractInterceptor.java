/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */

package com.gome.upm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author niulu
 * @date 2013-11-22
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {


    protected boolean checkTicketFromRedis(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return true;
    }

}
