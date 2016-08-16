package com.gome.upm.constants;

import java.util.HashMap;
import java.util.Map;

public enum FunctionCodeEnum {

	DEVICE_FUNCTION_VOICE(2,"声音"),
	DEVICE_FUNCTION_CHANNEL(1,"频道"),
	DEVICE_FUNCTION_COLOR(3,"颜色"),
	DEVICE_FUNCTION_VOLICE_CONTROL(4,"音量控制"),
	DEVICE_FUNCTION_MOULDE(5,"模式"),
	DEVICE_FUNCTION_IS_SWEPT(6,"是否扫风"),
	DEVICE_FUNCTION_SWEPT_SET(7,"风速级别设置"),
	DEVICE_FUNCTION_WENDU(8,"温度设置"),
	DEVICE_FUNCTION_WIND_SET(9,"风类设置"),
	DEVICE_FUNCTION_NO_VIOCE(10,"是否静音"),
	DEVICE_FUNCTION_SWING(11,"是否摆头"),
	DEVICE_FUNCTION_THROW_RATIO(12,"投影比例"),
	DEVICE_FUNCTION_CD(13,"CD播放"),
	Scene_VALUE_L1(1,"慵懒早上"),
	Scene_VALUE_L2(2,"舒心晚安"),
	Scene_VALUE_L3(3,"休闲时光"),
	Scene_VALUE_L4(4,"欢乐影院"),
	Scene_VALUE_L5(5,"泡沫剧场"),
	Scene_VALUE_L6(6,"留声岁月"),
	Scene_VALUE_L7(7,"舒适时光");
//	Scene_VALUE_L8(8,"慵懒早上"),
//	Scene_VALUE_L9(9,"慵懒早上"),
//	Scene_VALUE_L10(10,"慵懒早上"),
//	Scene_VALUE_L11(11,"慵懒早上");
	
	 private int code;
	    private String msg;

	    private FunctionCodeEnum(int code, String msg) {
	        this.code = code;
	        this.msg = msg;
	    }

	    public static FunctionCodeEnum codeToEnum(int code) {

	    	FunctionCodeEnum[] values = FunctionCodeEnum.values();
	        for (FunctionCodeEnum returnCode : values) {
	            if (returnCode.code == code) {
	                return returnCode;
	            }
	        }
	        return null;
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
