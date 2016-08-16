package com.gome.upm.constants;


public class Constants {

    public static final int CART_GOODS_EXPIRE_TIME = 24*60*60*3;
    public static final int ORDER_EXPIRE_TIME = 24*60*60*5 ;
    public static final String HTTP_HEADER = "GMSC-KEY" ;
    public static final int PAGE_NUMBER = 10;
    public static final int PRODUCT_UP = 2;//发布
    public static final int PRODUCT_SAVE = 1;//提交未发布  
    public static final int DEVICE_UNACTIVATE = 0;//未激活  
    public static final int DEVICE_ACTIVATE = 1;//激活  
    public static final int DEVICE_BIND = 2;//绑定  
    public static final int DEVICE_UNBIND = 3;//解除绑定  
    public static final int PASSWORD_GENERATE_10 = 10;//密码长度  
    public static final int PASSWORD_GENERATE_15 = 15;//密码长度  
    public static final int PASSWORD_GENERATE_20 = 20;//密码长度 
    public static final String PASSWORD_AES = "gomecloud.com.cn";// 
    public static final int STREAM_UNREAD = 0;//未执行
    public static final int STREAM_READ = 1;//已执行
    public static final int TIMES_OUT_HEARTDBEAT = 60;//心跳超时 
    public static final int TIMES_OUT_STREAM = 8;//读取指令超时 
    public static final String SERVER_IP_1 = "127.0.0.1:8443";
    public static final String SERVER_IP_2 = "127.0.0.1:8444";
    public static final Integer CLIENT_TYPE_APP = 1;
    public static final Integer CLIENT_TYPE_DEVICE = 2;
    public static final int WEB_PAGE_SIZE =5;
    public static final String COMMUNITY_BROADCAST_CACHE="COMMUNITY_BROADCAST_";
    public static final int COMMUNITY_BROADCAST_CACHE_LENGTH=30;
    public static final int SEARCH_SHOP_VALUE_EXISTS_TIME = 10*60;
    public static final String UTF8="UTF-8";
    //耿明柱
//    public static final String MOBILEURL="http://10.126.45.39:7028/mobile";
//    public static final String MOBILEURL="http://10.126.53.142:7058/mobile";
    //线上
    public static final String MOBILEURL="http://mobile.gome.com.cn/mobile";
    //唐红兵
//    public static final String MOBILEURL="http://10.126.53.14 2:7023";
    //用户信息cookie的key
	public static final String COOKIE_USER_KEY = "UserInfo";
   

}
