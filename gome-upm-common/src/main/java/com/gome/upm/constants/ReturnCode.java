/**
 *
 */
package com.gome.upm.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author niulu
 *         <p/>
 *         2014年7月2日 下午12:13:59
 */
public enum ReturnCode {

    ACTIVE_EXCEPTION(-1, "系统异常"),
    ACTIVE_SUCCESS(01, "操作成功"),
    ACTIVE_FAILURE(02, "操作失败"),
    
    
    ERROR_PARAMS_NOT_NULL(03, "参数不能为空"),
    ERROR_HEADER_NOT_NULL(04, "请求头不能为空"),
    ERROR_NAME_HAS_USED(05, "名称已被占用"),
    ERROR_NAME_NOT_USED(06, "名称未被占用"),
    ERROR_CHANNEL_HAS_FULL(07, "该频道下的广告位已满"),
    ERROR_CHANNEL_SORT_HAS_EXIST(10, "该频道下的对应位置已存在广告位"),
    ERROR_CHANNEL_NOT_EMPTY(11, "该频道下已存在广告位"),
    ERROR_CHANNEL_EMPTY(12, "该频道下不存在广告位"),
    ERROR_CATEGORY_NOT_EMPTY(13, "该分类不为空"),
    ERROR_CATEGORY_EMPTY(14, "该分类为空"),
    ERROR_AD1_EXIST(15, "广告1存在，广告2不存在"),
    ERROR_AD2_EXIST(16, "广告2存在，广告1不存在"),
    ERROR_AD1_AD2_NOT_EXIST(17, "广告1广告2都不存在"),
    ERROR_APP_TIMES_OUT(201, "心跳检测超时，手机不在线"),
    ERROR_DEVICE_TIMES_OUT(202  , "心跳检测超时，设备不在线"),
    ERROR_APP_STREAM_TIMES_OUT(203, "手机指令请求超时"),
    ERROR_DEVICE_STREAM_TIMES_OUT(204  , "设备指令请求超时"),
    ERROR_APP_STREAM_NO (205  , "手机没有发送控制指令"),
    ERROR_DEVICE_STREAM_NO (206  , "设备没有发送控制指令"),
    ERROR_APP_STREAM_EXCEPTION (207  , "手机查询指令异常"),
    ERROR_DEVICE_STREAM_EXCEPTION (208  , "设备查询指令异常"),
    ERROR_PARAMS(400, "请求参数错误"),
    ERROR_AUTH(401, "无权限"),
    ERROR_PARAMS_DECRYPT(402, "参数解密失败"),
    ERROR_WRONG(403, "用户无法使用云平台"),
    ERROR_RESOURCES(404, "请求资源不存在"),
    ERROR_SERVER(503, "系统错误"),
    ERROR_NO_LOGIN(1010, "用户未登录"),
    ERROR_LOGIN_TIMEOUT(1011, "登录用户超时，请重新登录"),
    ERROR_USER_MORE(1012, "此用户已在其他客户端登录"),
    ERROR_USER_NONE(1009, "用户不存在"),
    ERROR_USER_PASSWORD(1008, "用户名或密码不正确"),
    ERROR_USER_NAME_EMPTY(1007, "用户名为空"),
    ERROR_USER_PASSWORD_ENPTY(1006, "密码为空"),
    ERROR_USER_NAME_PASSWORD_EMPTY(1005, "用户名为空;密码为空"),
    ERROR_USER_LOCK_OR_DEL(1006,"帐号被锁定，请联系管理员"),
    ERROR_USER_OLD_PASSWORD_EMPTY(1100, "旧密码不能为空"),
    RETURN_REQUEST_SUCCESS(1101, "请求状态码"),
    RETURN_RESPONSE_SUCCESS(1102, "返回状态码"),
    ERROR_USER_CONFIRM_PASSWORD_DIFF(1103, "新密码与确认新密码不一致"),
    ERROR_USER_OLD_PASSWORD_ERROR(1104, "旧密码错误，请重新输入"),
    ERROR_USER_PASSWORD_CONFIRM_PASSWORD_DIFF(1105, "密码与确认密码不一致"),
    ERROR_USER_REGED_LOGIN(1106, "该手机号已注册，请登录"),
    ERROR_USER_TYPE_ERROR(1111, "用户类型参数错误"),
    ERROR_USER_AUTH_INTEFACE(1108, "限制的接口访问权限"),
    ERROR_DEVICE_NULL(1009, "设备编号为空"),
    ERROR_USER_AGENT(1013, "user-agent 请求参数错误"),
    ERROR_NO_SELLER(1014, "无法获取店铺信息"),
    ERROR_WX_AUTH_ERROR(1015, "微信授权失败"),
    ERROR_WX_CALLBACK_STATE_ERROR(1015, "微信授权state异常"),
    ERROR_WX_NO_APP_INFO(1015, "无法获取公众号信息"),
    ERROR_LOGIN_FAILURE(1015, "登录失败"),
    ERROR_REGIST_FAILURE(1015, "一键注册失败"),
    ERROR_VERIFYCODE(1015, "验证码不正确或验证码已过期"),
    ERROR_PHONE_MSG(1016, "手机验证码发送失败"),
    ERROR_PRODUCT_FAILURE(1017, "查询商品数据为空"),
    ERROR_UPDATE_FAILURE(1018, "数据不能更新"),
    ERROR_JW_FAILURE(1018, "数据不能更新"),
    ERROR_USER_LOCK(1019, "密码连续5次输入错误，请5分钟后重试"),
    ERROR_NO_RECORD(2001, "查询结果为空"),
    ERROR_COUNT_TOO_LONG(2002, "添加失败,请注意添加规则, 例如：最多只能添加 6 个"),
    ERROR_NO_PIC(2003, "请上传图片.."),
    ERROR_PIC_TYPE(2004, "上传图片仅支持png、jpg、jpeg、gif图片格式"),
    ERROR_PIC_SIZE(2005, "上传图片最大为1M"),
    ERROR_PIC_UPLOAD_IO(2006, "图片上传网络异常"),
    ERROR_PIC_UPLOAD_FILENAME_EMPTY(2007, "文件名为空或不规范"),
    ERROR_DEVICE_ACTIVATE(2008, "设备已经激活"),
    ERROR_DEVICE_CLOUD_URL(2009, "直连云中转设备URL未配置!"),
    ERROR_REGISTINFO_NOT_EMPTY(2010,"注册信息不能为空"),
    ERROR_VALIDCODE_NULL(2011,"验证码为空"),
    ERROR_GETVALDCODE(2012,"获取验证码失败"),
    ERROR_VALIDCODE_INCONSISTENT(2013,"验证码不一致"),
    ERROR_FORMAT_OF_TEL(2014,"手机号格式错误"),
    ERROR_Device_NAME(2015,"设备名称已存在"),
    ERROR_VALIDCODE_MORETHAN_COUNT(2016,"今天获取验证码次数达到"),
    ERROR_VALIDCODE_TIMEOUT(2017,"当前验证码超时"),
    ERROR_REGIST_NOSYNC(2018,"同步用户至国美在线失败"),
    ERROR_Device_result(2019,"获取厂商列表失败"),
    ERROR_ROLE_HASED_USER(2020,"该角色有关联用户不允许删除"),
    ERROR_OPERATOR_AUTH(2021,"权限不足，操作失败"),
    ERROR_USER_TOKEN_ERROR(2022,"token错误，请输入账号密码登录"),
    ERROR_USER_TOKEN_EXPIRES(2023,"token已过期");
    private int code;
    private String msg;

    private ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ReturnCode codeToEnum(int code) {

        ReturnCode[] values = ReturnCode.values();
        for (ReturnCode returnCode : values) {
            if (returnCode.code == code) {
                return returnCode;
            }
        }
        return ACTIVE_EXCEPTION;
    }

    public int code() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String msg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, ?> Map() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", this.code);
        hashMap.put("msg", this.msg);
        return hashMap;
    }

    public Map<String, ?> Map(int code) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", code);
        hashMap.put("msg", this.msg);
        return hashMap;
    }

    public Map<String, ?> Map(Object msg) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", this.code);
        hashMap.put("msg", msg);
        return hashMap;
    }

    public Map<String, ?> Map(int code, Object msg) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", code);
        hashMap.put("msg", msg);
        return hashMap;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"code\":").append(this.code).append(",");
        sb.append("\"msg\":\"").append(this.msg).append("\"}");

        return sb.toString();
    }

}
