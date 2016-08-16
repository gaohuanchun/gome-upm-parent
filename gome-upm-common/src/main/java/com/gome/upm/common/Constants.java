package com.gome.upm.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明
 */
public class Constants {

    public static int          TIMEOUT                                 = 5000;
    public static final int    SMART_PRODUCT_PLATFORM                  = -1;
    public static final int    HEALTH_PRODUCT_PLATFORM                 = -2;
    public static final int    AUTO_PRODUCT_PLATFORM                   = -3;

    /** 数据类型 血糖 运动 睡眠 */
    public static final String HEALTH_BLOOD_SUGAR_TYPE                 = "bloodSugar";
    public static final String HEALTH_SPORT_TYPE                       = "sport";
    public static final String HEALTH_SLEEP_TYPE                       = "sleep";
    public static final String HEALTH_BODY_FAT_TYPE                    = "physique";
    public static final String HEALTH_BLOOD_PRESSURE_TYPE                ="bloodPressure";//新增血压数据类型
    public static final String HEALTH_TREADMILL_TYPE                ="run";//新增跑步机类型

    /**
     * sport 运动数据 bloodSugar 血糖数据
     */

    /** 血糖的判断结果： 1低血糖 2理想 3偏高 4超标 */
    public static final int    HEALTH_BLOOD_SUGAR_LOW                  = 1;
    public static final int    HEALTH_BLOOD_SUGAR_NORMAL               = 2;
    public static final int    HEALTH_BLOOD_SUGAR_HIGH                 = 3;
    public static final int    HEALTH_BLOOD_SUGAR_SUPER                = 4;

    // sleep 睡眠数据 0清醒 =清醒时间; 3意识睡眠 =入睡时间 ; 1浅睡 =浅睡时间 ; 2深睡 =深睡时间
    public static final int    HEALTH_SLEEP_AWAKE                      = 0;
    public static final int    HEALTH_SLEEP_DROP                       = 3;
    public static final int    HEALTH_SLEEP_LIGHT                      = 1;
    public static final int    HEALTH_SLEEP_DEEP                       = 2;

    /***
     * 家居返回字段
     */
    public static final String PIN                                     = "pin";
    public static final String SMART_PRO_PRODUCT_ID                    = "product_id";
    public static final String SMART_PRO_PRODUCT_SECRET                = "product_secret";
    public static final String SMART_PRO_PRODUCT_NAME                  = "product_name";
    public static final String SMART_PRO_DESCRIPTION                   = "p_description";
    public static final String SMART_PRO_IMG_URL                       = "p_img_url";

    public static final String SMART_DEVICE_FEED_ID                    = "feed_id";
    public static final String SMART_DEVICE_DEVICENAME                 = "device_name";
    public static final String SMART_DEVICE_DEVICE_ID                  = "device_id";               // 序列号
    public static final String SMART_DEVICE_ACCESS_KEY                 = "access_key";
    public static final String SMART_DEVICE_STATUS                     = "status";
    public static final String SMART_DEVICE_ACTIVE_TIME                = "active_time";             // 设备激活时间
    public static final String SMART_DEVICE_GMTCREATE                  = "gmtCreate";               // 设备绑定时间

    public static final String SMART_PRO_PRO_TYPE                      = "pro_type";
    public static final String SMART_TYPE_TYPENAME                     = "type_name";
    public static final String SMART_TYPE_TYPEDESC                     = "type_desc";

    public static final String SMART_STREAM_PKEY                       = "stream_id";
    public static final String SMART_STREAM_NAME                       = "stream_name";
    public static final String SMART_STREAM_TYPE                       = "stream_type";
    public static final String SMART_STREAM_VALUE_DES                  = "value_des";
    public static final String SMART_STREAM_CURRENT_VALUE              = "current_value";
    public static final String SMART_STREAM_MASTER_FLAG                = "master_flag";
    public static final String SMART_STREAM_TIME_AT                    = "at";
    public static final String SMART_STREAM_UNITS                      = "units";

    public static final String SMART_GET_STREAMS_DEVICE                = "device";
    public static final String SMART_GET_STREAMS_PRODUCT               = "product";
    public static final String SMART_GET_STREAMS_STREAMS               = "streams";
    public static final String SMART_LIST_USERDEVICES_LIST             = "list";
    public static final String SMART_LIST_USERDEVICES_COUNT            = "count";
    public static final String SMART_LIST_USERDEVICES_MASTER_STREAM    = "stream";

    /**
     * 健康的返回字段
     */
    public static final String HEALTH_USERDETAIL_SEX                   = "sex";
    public static final String HEALTH_USERDETAIL_LABOR_TYPE            = "labor_type";
    public static final String HEALTH_USERDETAIL_BIRTHDAY              = "birthday";
    public static final String HEALTH_USERDETAIL_WEIGHT                = "weight";
    public static final String HEALTH_USERDETAIL_HEIGHT                = "height";
    public static final String HEALTH_USERDETAIL_SPORT_STEPS_GOAL      = "sport_steps_goal";
    public static final String HEALTH_USERDETAIL_EXIST                 = "exist";

    public static final String HEALTH_DEVICE_PROID                     = "proId";       // TODO
    public static final String HEALTH_DEVICE_DEVICEID                  = "deviceId";    // TODO
    public static final String HEALTH_DEVICE_DEVICENAME                = "devicename";  // TODO 获取用户设备列表
    public static final String HEALTH_DEVICE_DEVICE_NAME               = "deviceName";  // TODO 获取汇总信息。
    public static final String HEALTH_DEVICE_PRO_IMG_URL               = "img_url";     // TODO  获取汇总信息。 获取用户设备列表

    public static final String HEALTH_SPORT_STEPS_GOAL                 = "sport_steps_goal";
    public static final String HEALTH_SPORT_STEPS_DAY_TOTAL            = "sport_steps_day_total";
    public static final String HEALTH_SPORT_CALORIES_DAY_TOTAL         = "sport_calories_day_total";
    public static final String HEALTH_SPORT_METERS_DAY_TOTAL           = "sport_meters_day_total";
    public static final String HEALTH_SPORT_STEPS_PERCET               = "sport_steps_percet";
    public static final String HEALTH_METRIC_INTERVAL                  = "metric_interval";
    
    public static final String HEALTH_SPORT_DATA_TYPE                  = "data_type";
    public static final String HEALTH_SPORT_DATA_DAY                   = "data_day";
    public static final String HEALTH_SPORT_DATA_TYPE_STEPS            = "steps";
    public static final String HEALTH_SPORT_DATA_TYPE_METERS           = "meters";
    public static final String HEALTH_SPORT_DATA_TYPE_CALORIES         = "calories";
    
    public static final String HEALTH_SPORT_START_TIME                 = "sport_start_time";
    public static final String HEALTH_SPORT_END_TIME                   = "sport_end_time";
    public static final String HEALTH_SPORT_SPORT_TYPE                 = "sport_type";
    public static final String HEALTH_SPORT_TOTAL_TIME                 = "sport_total_time";
    public static final String HEALTH_SPORT_TYPE_SINGLE                = "sport_type_single";
    public static final String HEALTH_SPORT_STEPS_SINGLE               = "sport_steps_single";
    public static final String HEALTH_SPORT_CALORIES_SINGLE            = "sport_calories_single";
    public static final String HEALTH_SPORT_METERS_SINGLE              = "sport_meters_single";

    public static final String HEALTH_DATA_TIMESTAMP                   = "timestamp";//TODO  健康历程。hbase==times_tamp
    
    public static final String HEALTH_BLOOD_MEASURE_TIME               = "measure_time";
    public static final String HEALTH_BLOOD_MEASURE_TIME_TYPE          = "measure_time_type";
    public static final String HEALTH_BLOOD_GLUCOSE_VALUE              = "glucose_value";
    public static final String HEALTH_BLOOD_GLUCOSE_QUALITY            = "glucose_quality";
    
    public static final String HEALTH_BLOOD_HIGHBEFORMEAL              = "highBeforMeal";
    public static final String HEALTH_BLOOD_LOWBEFOREMEAL              = "lowBeforeMeal";
    public static final String HEALTH_BLOOD_HIGHAFTERMEAL              = "highAfterMeal";
    public static final String HEALTH_BLOOD_LOWAFTERMEAL               = "lowAfterMeal";
    public static final String HEALTH_BLOOD_HIGHNIGHT                  = "highNight";
    public static final String HEALTH_BLOOD_LOWNIGHT                   = "lowNight";

    public static final String HEALTH_GET_HEALTHINFO_DATALIST          = "datalist";

    public static final String HEALTH_SLEEP_START_TIME                 = "sleep_start_time";
    public static final String HEALTH_SLEEP_END_TIME                   = "sleep_end_time";
    public static final String HEALTH_SLEEP_TOTAL_MINUTES              = "sleep_total_minutes";
    public static final String HEALTH_SLEEP_DEEP_MINUTES               = "sleep_deep_minutes";
    public static final String HEALTH_SLEEP_LIGHT_MINUTES              = "sleep_light_minutes";
    public static final String HEALTH_SLEEP_DROP_MINUTES               = "sleep_drop_minutes";
    public static final String HEALTH_SLEEP_DETAIL                     = "sleep_detail";
    public static final String HEALTH_SLEEP_QUALITY                    = "sleep_quality";
    public static final String HEALTH_SLEEP_AWAKE_MINUTES              = "sleep_awake_minutes";
    
    public static final String HEALTH_GET_HEALTHSUMINFO_TIME           = "time";
    public static final String HEALTH_GET_HEALTHSUMINFO_BLOOD_DATAINFO = "blood_dataInfo";
    public static final String HEALTH_GET_HEALTHSUMINFO_SLEEP_DATAINFO = "sleep_dataInfo";
    public static final String HEALTH_GET_HEALTHSUMINFO_SPORT_DATAINFO = "sport_dataInfo";

    public static final String HEALTH_SPORT_GOALDATA_GOALSTEP           = "goalStep";
    public static final String HEALTH_SPORT_GOALDATA_GOALMETER          = "goalMeter";
    public static final String HEALTH_SPORT_GOALDATA_GOALCALORY         = "goalCalory";
    public static final String HEALTH_SPORT_GOALDATA_GOALTYPE           = "goalType";
    public static final String HEALTH_SPORT_GOALDATA_GOALWIEGHT         = "goalWieght";

    public static final String HEALTH_BODY_GOALDATA_WIEGHT              = "wieght";
    public static final String HEALTH_BODY_GOALDATA_HIGHT               = "hight";
    /**
     * method for smart
     */
    public static final String SAF_METHOD_JOS_SMART_BINDDEVICE           = "josbindDevice";
    public static final String SAF_METHOD_JOS_SMART_LISTUSERDEVICES           =  "joslistUserDevices";
    public static final String SAF_METHOD_JOS_SMART_UNBINDDEVICES           =  "josunbindDevices";
    /**
     * 
     */
    public static final String DOMAIN_SMART_PRODUCT_PARAM_PUBLIC_FLAG   = "public_flag";
    public static final int DOMAIN_SMART_PRODUCT_PUBLIC_FLAG_PRIVATE    = 0;
    public static final int DOMAIN_SMART_PRODUCT_PUBLIC_FLAG_PUBLIC     = 1;
    
    
    public static final String DOMAIN_SMART_PRODUCT_PARAM_SHARE_FLAG    = "share_flag";
    public static final int DOMAIN_SMART_PRODUCT_SHARE_FLAG_NOSHARE     = 0; //不可分享
    public static final int DOMAIN_SMART_PRODUCT_SHARE_FLAG_SHARE       = 1;//私有设备可分享。
    
    /**
     *inner  health method type
     */
    public static final String SAF_HEALTH_METHOD_REQUEST_TYPE         ="req_type";
    public static final String INNER_HEALTH_METHOD_GET_SPORT_DEVICE_DETAIL_DATA  ="getSportDeviceDetailData";
    public static final String INNER_HEALTH_METHOD_GET_BODYFAT_DEVICE_DETAIL_DATA  ="getBodyFatDeviceData";
    public static final String INNER_HEALTH_METHOD_GET_HEALTH_USER_RELATION_INFO   ="getHealthUserRelationInfo";
    public static final String INNER_HEALTH_METHOD_PARAM_DATE_START   =  "start_date";
    public static final String INNER_HEALTH_METHOD_PARAM_DATE_END     =  "end_date";


    /***
     * health Blood Pressure
     */
    public static final String HEALTH_BLOOD_PRESSURE_HIGH_PRESSURE                   ="high_pressure";
    public static final String HEALTH_BLOOD_PRESSURE_LOW_PRESSURE          = "low_pressure";
    public static final String HEALTH_BLOOD_PRESSURE_HEART_RATE           = "heart_rate";
    /**
     * body Temperature
     */
    public static final String HEALTH_BODY_TEMPERATURE           = "body_temperature";


    /***
     * health body fat
     */
    public static final String HEALTH_BODY_FAT_WEIGHT                   ="weight";
    public static final String HEALTH_BODY_FAT_BODY_MASS_INDEX          = "body_mass_index";
    public static final String HEALTH_BODY_FAT_BODY_FAT_RATIO           = "body_fat_ratio";
    public static final String HEALTH_BODY_FAT_SUBCUTANEOUS_FAT_RATIOT  = "subcutaneous_fat_ratio";
    public static final String HEALTH_BODY_FAT_VISCERAL_FAT_INDEX       = "visceral_fat_index";
    public static final String HEALTH_BODY_FAT_MUSCLE_RATIO             = "muscle_ratio";
    public static final String HEALTH_BODY_FAT_BASAL_METABOLIC_RATE     = "basal_metabolic_rate";
    public static final String HEALTH_BODY_FAT_SKELETAL_WEIGHT     = "skeletal_weight";
    public static final String HEALTH_BODY_FAT_WATER_RATIO         = "water_ratio";
    public static final String HEALTH_BODY_FAT_AGE                 = "age";
    public static final String HEALTH_BODY_FAT_PROTEIN_RATIO       = "protein_ratio";
    public static final String HEALTH_USER_RELATIONSHIP            ="relationship";
    public static final String HEALTH_USER_RELATIONSHIP_LIST       ="others";


    
    public static final String IFTTT_TRIGGER_STATE = "ifttt_trigger_state";
    public static final String DUMMY_TRIGGER_KEY_1 = "dummy_trigger_key_1";
    public static final String DUMMY_TRIGGER_KEY_2 = "dummy_trigger_key_2";
    public static final String DUMMY_TRIGGER_KEY_3 = "dummy_trigger_key_3";
    public static final String DUMMY_TRIGGER_KEY_4 = "dummy_trigger_key_4";
    public static final Long DUMMY_FEED_ID_1 = 200001L;
    public static final Long DUMMY_FEED_ID_2 = 2130L; // iKair
    public static final String DUMMY_ACCESS_KEY = "72667c986e22d02db4d9333148b096cb";
    public static final String DUMMY_STREAM_ID_1 = "sensor5";
    public static final String DUMMY_STREAM_ID_2 = "sensor1";
    public static final String DUMMY_STREAM_ID_3 = "sensor2";
    public static final int DUMMY_CONDITION_1 = 15000;
    public static final int DUMMY_CONDITION_2 = 32;
    public static final int DUMMY_CONDITION_3 = 50;
    public static final Long DUMMY_TARGET_FEED_ID_1 = 2137L;  // 美的空气净化器
    public static final Long DUMMY_TARGET_FEED_ID_2 = 214778L;  // TCL智能空调
    public static final Long DUMMY_TARGET_FEED_ID_3 = 214766L;  // 阳光照明
    public static final Long DUMMY_TARGET_FEED_ID_4 = 2151L;  // 美颜空气净化器
    
    public static int SEND_MAIL_MAX_RETRY = 3;//发送邮件重试次数
    public static String SEND_MAIL_SUBJECT_VERIFY = "京东智能硬件云开发者申请";//
    public static int MAIL_CODE_REDIS_TIMEOUT =  60 * 60;// 默认缓存过期时间60分钟
    
    public static int DEV_APP_TYPE_TOBE_DEVELOPER = 1;
    public static int PARAM_LENGTH_LIMIT_64   =64;
    public static int PARAM_LENGTH_LIMIT_11   =11;
    public static int PARAM_LENGTH_LIMIT_20   =20;
    public static int PARAM_LENGTH_LIMIT_255   =255;
    public static int PARAM_LENGTH_LIMIT_500   =500;
    public static int PARAM_LENGTH_LIMIT_300   =300;
    
    
    public static long PARAM_FILE_LENGTH_LIMIT_50M   =1024 *1024 * 50;//50M
    
    
    public static final int DEV_VERIFY_STATUS_UNCOMMIT =0;//审核状态  未提交-0 未审核-1  审核通过-2 审核不通过-3
    public static final int DEV_VERIFY_STATUS_DOVERIFY =1;
    public static final int DEV_VERIFY_STATUS_VERIFY_SUCCESS =2;
    public static final int DEV_VERIFY_STATUS_VERIFY_UNSUCCESS =3;
    
    // 2014/9前生产的设备
	public final static Float OLD_DEVICE_VERSION = 0.1f;
	// 2014/9后生产的设备
	public static final Float DEVICE_VERSION = 1.0f;
    public static final String  USER_IMG_SNSPIC_PREFIX  = "http://snspic.360buy.com";
    public static final String  USER_IMG_SNSPIC_SUFFIX  = "_mid_.jpg";
    public static final  String USER_IMG_IMGURL="http://i.360buy.com/commons/img/no-img_mid_.jpg";
    //调用jos的jingdong.area.ip.query API 需要的参数
    public static final String SERVER_URL =  "http://gw.api.jd.local/routerjson";
    public static final String APP_KEY =  "E559AA5FFF16CF4AE9D0F9C3493995AD";
    public static final String APP_SECRET = "2b5d182e12a54671aac301024117bca8";

    
    public static final Integer NORMAL_HANDLE_TYPE = 0; //指通常的設備處理模式
    public static final Integer HAIER_HANDLE_TYPE = 1; //指海爾的設備處理模式
    
    public static final String THIRD_PARTY_CLOUD = "thirdPartyCloud";
    public static final String SHARED = "shared";
    
    public static final String ONLINE = "1";
    public static final String OFFLINE = "0";

    public static final Integer HAIER_HANDLETYPE = 1;
    public static final Integer MIDEA_HANDLETYPE = 2;

    // 移动设备激活、绑定等待默认时间窗口,3分
    public static final int DEFAULT_TIME_WINDOW =  60 * 3;
    
    public static final String HAIER_USER_THIRDPART_TOKEN_PREFIX = "H_U_T_TOKEN_";

    
    /**有效数据*/
    public static final int VALID_DATA = 1;
    /**删除数据*/
    public static final int DELETED_DATA = 2;
    
    /**默认数据*/
    public static final int IS_DEFAULT = 1;
    
    /**蓝牙设备*/
    public static final String DEVICE_TYPE_BLE = "1";
    /**wifi设备*/
    public static final String DEVICE_TYPE_WIFI = "2";

    //网络菜谱相关
    /** 任务下发 **/
    public static final Integer TASK_SEND = 1050;
    /** 设备执行任务回复 **/
    public static final Integer TASK_DEVICE_REPLY = 150;
    /** 设备执行任务状态上报 **/
    public static final Integer TASK_DEVICE_UPDATE = 151;
    /** 设备执行任务状态上报，服务端回复 **/
    public static final Integer TASK_SERVICE_REPLY = 1051;
    


    //消息中心Hbase表

    public static final String SM_PUSHMSG_TOTAL = "sm_pushmsg_total" ;//推送消息总表
    public static final String SM_PUSHMSG_FEEDID = "sm_pushmsg_feedid" ;//纯设备推送消息表
    //public static final String SM_PUSHMSG_NOFEEDID = "sm_pushmsg_nofeedid";//非设备消息推送表
    public static final String SM_PUSHMSG_NOSENDING = "sm_pushmsg_nosending";//用户待发送消息表
    public static final String SM_PUSHMSG_READED_STAT = "sm_pushmsg_readed_stat";//用户已读消息统计表
    public static final String SM_PUSHMSG_GROUP = "sm_pushmsg_group";//用户群发消息表
    
    
    //推送消息类型  01:纯设备消息；02：ifttt; 03:定时消息；04：促销；05：系统升级；06：一般通知
    public static final Integer DEVICE_MSG_TYPE = 1;
    public static final Integer IFTTT_MSG_TYPE = 2;
    public static final Integer TIMMER_MSG_TYPE = 3;
    public static final Integer BIZ_MSG_TYPE = 4;
    public static final Integer SYS_MSG_TYPE = 5;
    public static final Integer NORMALINFOR_MSG_TYPE = 6;


    //回调标识位

    //控制回调位
    public static final Integer CALLBAK_CONTROL=5;
    //修改设备名位
    public static final Integer CALLBAK_RENAME=6;
   
    
    public static Map<Integer, String> MSG_PRIORITYMAP = new HashMap<Integer, String>();
    static{
    	MSG_PRIORITYMAP.put(0, "提醒");
    	MSG_PRIORITYMAP.put(1, "告警");
    }
    
    public static Map<Integer, String> MSG_PRIORITYMAP_ENG = new HashMap<Integer, String>();
    static{
    	MSG_PRIORITYMAP_ENG.put(0, "info");
    	MSG_PRIORITYMAP_ENG.put(1, "warn");
    }
    
    public static Map<String, String> MSG_FEELLEVERMAP = new HashMap<String, String>();
    static{
    	MSG_FEELLEVERMAP.put("1-1", "a,b,c");  //设备告警类消息
    	MSG_FEELLEVERMAP.put("1-0", "b,c");		//设备提醒类消息
    	MSG_FEELLEVERMAP.put("2-0", "b,c");  //互联触发消息
    	MSG_FEELLEVERMAP.put("4-0", "b,d");  //促销类消息
    	MSG_FEELLEVERMAP.put("5-0", "d");	 //系统通知
    }

    public static int MSG_PRIORITYMAP_INFO = 0;
    public static int MSG_PRIORITYMAP_WARN = 1;
    
    public static Map<String, Integer> MSG_SET_KEY_MSGTYPE_MAPPING = new HashMap<String, Integer>();
    static{
    	MSG_SET_KEY_MSGTYPE_MAPPING.put("sys_info", 5);
    	MSG_SET_KEY_MSGTYPE_MAPPING.put("biz_info", 4);
    }
    
    public static Map<Integer, String> MSG_SET_MSGTYPE_KEY_MAPPING = new HashMap<Integer, String>();
    static{
    	MSG_SET_MSGTYPE_KEY_MAPPING.put(5, "sys_info");
    	MSG_SET_MSGTYPE_KEY_MAPPING.put(4, "biz_info");
    }
    
    public static int ANDROID_TYPE = 1;
    public static int IOS_TYPE = 2;
    
    public static String SM_TIMETASK_REDIS_PREFIX = "SM_TT_";
    public static String SM_UNCOMPLETED_DATEPOINTS = "SM_UNCOMPLETED_DATEPOINTS";
    
    public static String HBASE_TIMER_TASK_EXE_RESULT_TABLENAME = "sm_timer_task"; //hbase 定时任务执行结果表
    public static String HBASE_TIMER_TASK_FEED_EXE_RESULT_TABLENAME = "sm_timer_task_feed_result"; //hbase  设备定时任务日志表
    
    private static String domain;

	public static String getDomain() {
		return domain;
	}

	public static void setDomain(String domain) {
		Constants.domain = domain;
	}

    //设备类型  0 一般设备；1 主设备；2从设备
    public static final Integer DEVICE_COMMON = 0;
    public static final Integer DEVICE_MAIN = 1;
    public static final Integer DEVICE_SUB = 2;
}
