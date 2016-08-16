package com.gome.upm.constants;

public class SysConstants {
    public static final String DEFAULT_SPLIT = "; ";
    //redis
    public static final String REDIS_PREFIX="community";
    // 手机返回的成功信息。
    public static final String PHONE_MSG_SUCCESS_CODE="1";
    public static final String PHONE_MSG_TEMPLATE="验证码为%s（京东客服绝不会索取此验证码，切勿告知他人），请在页面中输入以完成验证";

	public static class UserInfoVo {
		public static final Integer USER_TYPE_CLIENT = 1;
		public static final Integer USER_TYPE_BUSINESS = 2;
		public static final Integer USER_TYPE_OPERATION = 3;
	}

	public static final String COOKIE_FORMAT = "%s|%s|%s|%s|%s";
}
