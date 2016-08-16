package com.gome.upm.common;
/*
 * Copyright 1998-2012 360buy.com All right reserved. This software is the
 * confidential and proprietary information of 360buy.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with 360buy.com.
 */


/**
 * 类ResponseUtil.java的实现描述：TODO 类实现描述 
 * RESTAPI状态码定义
 * @author qingting 2014-3-19 下午4:18:33
 */
public class RestApiCodes {
    public static  final int SUCCESS = 200;
    public static  final String SUCCESS_MSG = "ok";

    //权限或参数验证相关
    public static  final int UNAUTHORIZED = 1001;
    public static final String UNAUTHORIZED_MSG = "无权限或未授权";

    public static  final int ILLEGAL_PARAM = 1002;
    public static final String ILLEGAL_PARAM_MSG = "传入参数非法";

    public static  final int ILLEGAL_USER = 1003;
    public static final String ILLEGAL_USER_MSG = "非法用户";

    public static final int REGISTER_FAILED = 1004;
    public static final String REGISTER_FAILED_MSG = "用户注册失败";

    public static  final int BEENBIND = 1005;
    public static final String BEENBIND_MSG = "该设备已被绑定";

    /**设备已经被其它人绑定*/
    public static  final int BINDED = 1006;
    //数据相关
    public static final int NOT_FOUND_DATA = 2001;
    public static final String NOT_FOUND_DATA_MSG = "未找到数据";
    public static final int NOT_FOUND_PRODUCT_DATA = 2002;
    public static final String NOT_FOUND_PRODUCT_DATA_MSG = "未找到产品数据";
    public static final int NOT_FOUND_DEVICE_DATA = 2003;
    public static final String NOT_FOUND_DEVICE_DATA_MSG = "未找到设备数据";

    public static final int DEVICE_STATUS_OFFLINE = 2004;
    public static final String DEVICE_STATUS_OFFLINE_MSG = "设备不在线";

    public static final int SERVICE_UNAVAILABLE_BINDDEVICE_EXIST = 2005;
    public static final String SERVICE_UNAVAILABLE_BINDDEVICE_EXIST_MSG = "设备已绑定";

    public static final int DEVICE_RES_TIMEOUT = 2006;
    public static final String DEVICE_RES_TIMEOUT_MSG = "设备响应超时";


    public static final int NO_SHARE_DEVICE_TOKEN = 2007;
    public static final String NO_SHARE_DEVICE_TOKENT_MSG = "设备分享TOKEN不存在";

    public static final int CANNOT_SHARE_DEVICE = 2008;
    public static final String CANNOT_SHARE_DEVICE_MSG = "设备不可分享";

    public static final int NO_SHARE_DEVICE_RELATION = 2009;
    public static final String NO_SHARE_DEVICE_RELATION_MSG = "设备未分享给此用户";

    public static final int NOT_OWEN_DEVICE = 2010;
    public static final String NOT_OWEN_DEVICE_MSG = "用户对此设备没有分享权限";

    public static final int DEV_IN_IFTTT = 2011;
    public static final String DEV_IN_IFTTT_MSG = "设备参与了某个设备互联，请先删除参与的设备互联";

    public static final int NOT_FOUND_ARTICLE_DATA = 2012;
    public static final String NOT_FOUND_ARTICLE_MSG = "未找到文章信息";

    public static final int USER_BINDDEVICE_EXIST = 2013;
    public static final String USER_BINDDEVICE_EXIST_MSG = "设备未与用户绑定";

    public static final int NOT_FOUND_FIELD_DATA = 2014;
    public static final String NOT_FOUND_FIELD_DATA_MSG = "上报数据字段信息未找到";

    public static final int HEALTH_USER_INIT_DATA = 2015;
    public static final String HEALTH_USER_INIT_DATA_MSG = "health_user、health_device更新失败";

    public static final int DEVICE_ACTIVATE_ERROR = 2017;
    public static final String DEVICE_ACTIVATE_ERROR_MSG = "设备激活失败";

    public static final int DEVICE_BIND_ERROR = 2018;
    public static final String DEVICE_BIND_ERROR_MSG = "设备绑定失败";
    
    public static final int DEVICE_P2P_INFO_NOT_EXIST = 2016;
    public static final String DEVICE_P2P_INFO_NOT_EXIST_MSG = "设备p2p信息不存在";

    public static final int TEST_PRODUCT_EXPIRED = 2019;
    public static final String TEST_PRODUCT_EXPIRED_TEXT = "测试产品已过期";

    public static final int TEST_PRODUCT_COUNT_OVER = 2020;
    public static final String TEST_PRODUCT_COUNT_OVER_TEXT = "测试产品数量超过最大值";

    public static final int DEVICE_ACTIVATED_ERROR = 2021;
    public static final String DEVICE_ACTIVATED_ERROR_MSG = "设备已被激活";
    
    //服务相关
    public static final int SERVICE_UNAVAILABLE = 3001;
    public static final String SERVICE_UNAVAILABLE_MSG = "服务不可用";

    public static final int SERVICE_UNAVAILABLE_PRODUCT_PUBLISHED = 3002;
    public static final String SERVICE_UNAVAILABLE_PRODUCT_PUBLISHED_MSG = "产品已发布,不更新";

    public static final int SERVICE_UNAVAILABLE_PRODUCT_EXIST = 3003;
    public static final String SERVICE_UNAVAILABLE_PRODUCT_EXIST_MSG = "该产品名称和型号已存在";

    public static final int SERVICE_PROCESSING = 3004;
    public static final String SERVICE_PROCESSING_MSG = "设备繁忙，请稍后";


    public static final int SERVICE_UNAVAILABLE_DEVICE_EXIST = 3005;
    public static final String SERVICE_UNAVAILABLE_DEVICE_EXIST_MSG = "用户该设备名称已存在";

    public static final int NO_QR_CODE_FOUND = 3006;
    public static final String NO_QR_CODE_FOUND_MSG = "需先扫描二维码再激活设备";

    //服务器内部错误
    public static final int INTERNAL_SERVER_ERROR = 5001;
    public static final String INTERNAL_SERVER_ERROR_MSG = "服务器错误或异常";

    public static final int CALLBACK_ERROR = 9001;
    public static final String CALLBACK_ERROR_MSG = "回调失败";

    //数据库错误
    public static final int HBASE_OPERATE_ERROR = 5002;
    public static final String HBASE_OPERATE_ERROR_MSG = "Hbase数据库错误";
    
    //运动数据上报相关
    public static final int UNAUTHORIZED_PUBLIC_DEVICE = 6001;
    public static final String UNAUTHORIZED_PUBLIC_DEVICE_MSG = "未授权公用设备，数据丢弃";
    public static final int SPORT_DATA_UPLOAD_ERROR = 6002;
    public static final String SPORT_DATA_UPLOAD_ERROR_MSG = "运动数据存储失败";
    public static final int TASK_DATA_UPLOAD_ERROR = 6003;
    public static final String TASK_DATA_UPLOAD_ERROR_MSG = "任务数据存储失败";
    
    public static final int NO_CMD_FOUND = 7001;
    public static final String NO_CMD_FOUND_MSG = "没有找到匹配的控制命令";
    public static final String NO_QUERY_RESULT_MSG = "没有查询到您想要的结果";
    public static final String NO_DAILY_CONVERSATION_RESULT_MSG = "对不起，我没有理解您的意思";
    public static int RECOGNATION_COMMAND_CODE = 7002;
    public static String RECOGNATION_COMMAND_MSG = "识别到准确控制指令";
    public static int HAS_SAME_TYPE_CODE = 7003;
    public static String HAS_SAME_TYPE_MSG = "存在同类设备,您想操作哪一个呢?";
    public static int RECOGNATION_QUERY_CODE = 7004;
    public static String RECOGNATION_QUERY_MSG = "识别到查询结果";
    public static int HAS_SAME_NAME_CODE = 7005;
    public static String HAS_SAME_NAME_MSG = "存在同名设备";
    public static int RECOGNATION_DAILY_CONVERSATION_CODE= 7006;
    public static String RECOGNATION_DAILY_CONVERSATION_MSG = "识别到日常对话";
}