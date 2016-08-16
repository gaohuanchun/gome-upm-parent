package com.gome.upm.common.util;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

	/**
	 *对象转换成json串
	 * @param obj
	 * @return
	 */
	public static String Object2Json(Object obj) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(obj);
	}
	
	/**
	 *json串转换成对象
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> Object Json2Object(String json, Class<T> clazz) {
		// TODO Auto-generated method stub
		return JSON.parseObject(json, clazz);
	}

}
