package com.gome.upm.controler.base;

/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gome.upm.common.CommunityRuntimeException;
import com.gome.upm.common.MshopRpcRuntimeException;
import com.gome.upm.common.MshopRpcUnCheckedException;
import com.gome.upm.common.MshopRuntimeException;
import com.gome.upm.constants.ReturnCode;

/**
 * 全局异常处理
 *
 * @author niulu
 * @date 2013-10-20
 */
@Controller
public abstract class GlobalController {

    private static final Logger logger = Logger.getLogger(GlobalController.class);

    /**
     * 前端传入的参数，转换为后端的Controller绑定参数类型时，发生类型转换错误时，触发。<br/>
     * eg. 前端传入id=10000000000000000000000,后端绑定参数为Integer类型时，入参超过了Integer值范围， 将会触发该异常。
     *
     * @return
     * @author niulu
     * @date 2013-10-20
     */
    @ResponseBody
    @ExceptionHandler({TypeMismatchException.class})
    public Map<String, ? extends Object> typeMismatchException(TypeMismatchException e) {
        logger.error(e.getMessage(), e.getCause());
        this.printStackTrace(e);
        Map<String, Object> returnResult = new HashMap<String, Object>();
        returnResult.put("code", ReturnCode.ERROR_PARAMS.code());
        returnResult.put("attach", "值为 '" + e.getValue() + "' 参数错误！");
        return returnResult;
    }

    /**
     * saf远程调用系统异常
     */
    @ResponseBody
    @ExceptionHandler({MshopRpcRuntimeException.class})
    public Map<String, ? extends Object> eccRpcRuntimeException(MshopRpcRuntimeException e) {
        logger.error(e.getMessage(), e.getCause());
        printStackTrace(e);
        Map<String, Object> map = (Map<String, Object>) ReturnCode.ERROR_SERVER.Map();
        map.put("attach", map.get("msg"));
        map.remove("msg");
        return map;
    }

    /**
     * saf远程调用系统异常
     *
     * @return
     * @author niulu
     * @date 2013-10-20
     */
    @ResponseBody
    @ExceptionHandler({MshopRpcUnCheckedException.class})
    public Map<String, ? extends Object> eccRpcUnCheckedException(MshopRpcUnCheckedException e) {
        logger.error(e.getMessage(), e.getCause());
        this.printStackTrace(e);
        Map<String, Object> map = (Map<String, Object>) ReturnCode.ERROR_PARAMS.Map(e.getMessage());
        map.put("attach", map.get("msg"));
        map.remove("msg");
        return map;
    }

    /**
     * 参数为单个基本数据类型，且未传入参数时，抛该异常
     */
    @ResponseBody
    @ExceptionHandler({IllegalStateException.class})
    public Map<String, ? extends Object> illegalStateException(IllegalStateException e) {
        logger.error(e.getMessage(), e.getCause());
        printStackTrace(e);
        Map<String, Object> map = (Map<String, Object>) ReturnCode.ERROR_PARAMS_NOT_NULL.Map();
        map.put("attach", map.get("msg"));
        map.remove("msg");
        return map;
    }

    /**
     * 参数为单个基本数据类型，且未传入参数时，抛该异常
     */
    @ResponseBody
    @ExceptionHandler({MshopRuntimeException.class})
    public Map<String, ? extends Object> eccRuntimeException(MshopRuntimeException e) {
        logger.error(e.getMessage(), e.getCause());
        printStackTrace(e);
        Map<String, Object> map = (Map<String, Object>) ReturnCode.ERROR_SERVER.Map();
        map.put("attach", map.get("msg"));
        map.remove("msg");
        return map;
    }

    /**
     * 参数为单个基本数据类型，且未传入参数时，抛该异常
     */
    @ResponseBody
    @ExceptionHandler({CommunityRuntimeException.class})
    public Map<String, ? extends Object> eccRuntimeException(CommunityRuntimeException e) {
        logger.error(e.getMessage(), e.getCause());
        printStackTrace(e);
        Map<String, Object> map = (Map<String, Object>) ReturnCode.ERROR_SERVER.Map();
        map.put("attach", e.getResponsesDTO().getAttach());
        map.put("code", e.getResponsesDTO().getCode());
        map.remove("msg");
        return map;
    }

    /**
     * 所有其他异常
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Map<String, ? extends Object> exception(Exception e) {
        logger.error(e.getMessage(), e.getCause());
        logger.error(e.getClass());
        printStackTrace(e);
        Map<String, Object> map = (Map<String, Object>) ReturnCode.ERROR_SERVER.Map();
        map.put("attach", map.get("msg"));
        map.remove("msg");
        return map;
    }

    /**
     * 打印异常堆栈信息
     *
     * @param e
     * @author niulu
     * @date 2013-12-2
     */
    private void printStackTrace(Exception e) {
        StringWriter w = new StringWriter();
        PrintWriter pw = new PrintWriter(w);
        e.printStackTrace(pw);
        pw.flush();
        logger.error(w.toString());
    }

}
