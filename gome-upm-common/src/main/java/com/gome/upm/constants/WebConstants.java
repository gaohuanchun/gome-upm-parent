package com.gome.upm.constants;

public class WebConstants {
	
	
	/** @Fields X_FORWARD_FOR: 
	 * 截取ip的key
	 * */
	  	
	public static final String X_FORWARD_FOR = "x-forwarded-for";
	
	public static final String KEY_CLOUD = "cloud";
	
	
	/** @Fields LOGIN_SUCCESS_CODE: 
	 * 登录成功状态码
	 * */
	  	
	public static final int LOGIN_SUCCESS_CODE = 200;
	
	
	/** @Fields THEME_TYPE: 
	 * 主题类型 1:内置
	 * */
	  	
	//public static final Byte THEME_TYPE = 1;
	
	
	/** @Fields DB_KEY_THEME_ID: 
	 * 主题id的key
	 * */
	  	
	public static final String DB_KEY_THEME_ID = "themeId";
	
	
	/** @Fields DB_KEY_SCENE_ID: 
	 * 场景id的key
	 * */
	  	
	public static final String DB_KEY_SCENE_ID = "sceneId";
	
	
	/** @Fields DB_KEY_USER_ID: 
	 * 用户id的key
	 * */
	  	
	public static final String DB_KEY_USER_ID = "userId";
	
	
	/** @Fields VALIDCODE_SEND_QTY: 
	 * 手机验证码发送次数
	 * */
	  	
	public static final Byte VALIDCODE_SEND_QTY = 4;
	
	
	/** @Fields VALIDCODE_TIMEOUT_MINIUTE: 
	 * 验证码超时时间
	 * */
	  	
	public static final int VALIDCODE_TIMEOUT_MINIUTE = 10;
	
	
	/** @Fields THEME_TYPE_BUILTIN: 
	 * 内置主题类型
	 * */
	  	
	public static final int THEME_TYPE_BUILTIN = 0;
	
	
	/** @Fields THEME_TYPE_NOT_BUILTIN: 
	 * 非内置主题类型
	 * */
	  	
	public static final int THEME_TYPE_NOT_BUILTIN = 1;
	
	public static final String SMS_RECORD = "验证码为【%s】。 感谢您使用国美云智。";
	
}
