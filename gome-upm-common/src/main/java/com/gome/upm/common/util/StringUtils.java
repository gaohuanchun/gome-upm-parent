/**
 * Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
 */

package com.gome.upm.common.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * StringUtils 字符串工具类
 * 
 * @author niulu
 * @date 2013-7-12
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	private static final String patternByMaps = "&";
	private static final String patternByKeyValues = "=";

	/**
	 * 将map中key,value值转换为字符串，Map.Entry之间默认分隔符<b> ${patternByMaps}
	 * </b>联接,key,value之间默认分隔符<b> ${patternByKeyValues} </b>联接.
	 * 
	 * @param params
	 * @param patternByMaps
	 * @param patternByKeyValues
	 * @return
	 * @author niulu
	 * @date 2013-7-12
	 */
	public static String map2String(Map<String, ? extends Object> params, String patternByMaps,
			String patternByKeyValues, boolean valueHasQuotationMarks) {
		int count = 0;
		StringBuilder builder = new StringBuilder();
		for (Entry<String, ? extends Object> entry : params.entrySet()) {
			if (count > 0) {
				builder.append(patternByMaps);
			}
			builder.append(entry.getKey());
			builder.append(patternByKeyValues);
			if (valueHasQuotationMarks) {
				builder.append("\"" + entry.getValue() + "\"");
			} else {
				builder.append(entry.getValue());
			}
			count++;
		}
		return builder.toString();
	}

	/**
	 * 将map中key,value值转换为字符串，Map.Entry之间默认分隔符<b> & </b>联接,key,value之间默认分隔符 <b> =
	 * </b>联接.
	 * 
	 * @param params
	 *            参数
	 * @return 字符串
	 * @author niulu
	 * @date 2013-7-12
	 */
	public static String map2String(Map<String, ? extends Object> params) {
		return map2String(params, patternByMaps, patternByKeyValues, false);
	}

	public static String collect2String(Collection<String> params) {
		if (null == params || params.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();

		Iterator<String> iterator = params.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			if (count == 0) {
				builder.append("; ");
			}
			builder.append(iterator.next());
			count++;
		}
		return builder.toString();
	}

	public static String replace(String str) {
		if (isBlank(str)) {
			return str;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '\r') {
				builder.append("\\r");
			} else if (c == '\n') {
				builder.append("\\n");
			} else if (c == '\t') {
				builder.append("\\t");
			} else if (c == '\b') {
				builder.append("\\b");
			} else if (c == '\f') {
				builder.append("\\f");
			} else if (c == '"') {
				builder.append("\\\"");
			} else if (c == '\\') {
				builder.append("\\\\");
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * 计算字符串长度，区别中英文，中文一个字符占2个长度，英文一个字符占1个长度
	 * 
	 * @param str
	 *            待计算字符串
	 * @return 字符串长度
	 * @author niulu
	 * @date 2013-9-25
	 */
	public static int lengthZH_cn(String str) {
		int count = 0;
		if (isEmpty(str)) {
			return count;
		}
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c >= '\u0391' && c <= '\uFFE5') {
				count += 2;
			} else {
				count += 1;
			}
		}
		return count;
	}

	/**
	 * 截取最大长度的字符串，中文一个字符占2个长度，英文一个字符占1个长度
	 * 
	 * @param str
	 *            待计算字符串
	 * @param maxLength
	 *            最大长度
	 * @return 字符串
	 * @author niulu
	 * @date 2013-9-25
	 */
	public static String subStringZH_cn(String str, int maxLength) {
		int count = 0;
		int endIndex = 1;

		if (isEmpty(str)) {
			return str;
		}
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c >= '\u0391' && c <= '\uFFE5') {
				count += 2;
			} else {
				count += 1;
			}

			if (count == maxLength) {
				endIndex = i + 1;
				break;
			} else if (count > maxLength) {
				endIndex = i;
				break;
			} else {
				endIndex = i + 1;
			}
		}
		return str.substring(0, endIndex);
	}

	/** 手机正则规则 */
	private static final String mobile = "1[0-9]{10}";

	/**
	 * 处理手机号码<br/>
	 * 如果是1开头的11位数字，即认为是手机号码，将返回<b>前三位 + ** + 后四位</b><br/>
	 * 否则原样返回。<br/>
	 * eg. 13892323232 >> 138**3232
	 * 
	 * @param str
	 *            输入参数
	 * @return
	 * @author niulu
	 * @date 2013-10-8
	 */
	public static String subStringByMobile(String str) {
		if (isEmpty(str)) {
			return str;
		}

		if (Pattern.matches(mobile, str)) {
			return str.substring(0, 3) + "**" + str.substring(7, 11);
		}
		return str;
	}

	/**
	 * 解析属性 ：属性值
	 * 
	 * @param attribute
	 * @return
	 */
	public static List<String> getAttributeName(String attribute) {
		List<String> attList = new ArrayList<String>();
		if (attribute == null || attribute.equals("")) {
			return attList;
		}
		String[] attributes = attribute.split(";");
		if (attributes == null || attributes.length == 0) {
			return attList;
		}
		String attributeName = "";
		for (int i = 0; i < attributes.length; i++) {
			String temp = attributes[i];
			String[] ids = temp.split(":");
			if (ids == null || ids.length != 4) {
				continue;
			}
			attributeName = intercepStr(temp, 2, ":");
			attList.add(attributeName);
		}
		return attList;
	}

	/**
	 * 截取字符串。按某字串截取，且截取指定次数
	 * 
	 * @param target
	 *            需要被截取的字符串
	 * @param intercepNo
	 *            截取次数
	 * @param intercepStr
	 *            被参照的子串
	 * @return
	 */
	public static String intercepStr(String target, int intercepNo, String intercepStr) {
		int len = intercepStr.length();
		for (int i = 0; i < intercepNo; i++) {
			int index = target.indexOf(intercepStr);
			if (index != -1) {
				target = target.substring(index + len);
			} else {
				target = null;
				break;
			}
		}
		return target;
	}

	public static String null2empty(String str) {
		return str == null ? "" : str;
	}

	public static void main(String[] args) {
		String string = "{" + "appversion = \"1.0\";" + "body = \"Adds\nDVDs\n\";"
				+ "channel = debug;" + "model = iPhone;" + "platform = iPhone;"
				+ "systemname = \"iPhone OS\";" + "systemversion = \"6.1.2\";" + "}";

		System.out.println(string);
		System.out.println(replace(string));
	}
	
	/***
     * 将一个对象的属性值赋给另一个对象的相同的属性 这两个对象必须都符合javaBean的标准
     * 
     * @param source
     *            要赋值的源对象
     * @param target
     *            被赋值的目标对象
     * @param ignoreProperties
     *            被忽略赋值的属性数组
     * @throws Exception
     *
     */
    public static void copyProperties(Object source, Object target,
            String ignoreProperties[]) throws Exception {
        Class targetClass = target.getClass();
        Class sourceClass = source.getClass();
 
        // 得到目标对象和源对象的属性数组
        PropertyDescriptor targetPds[] = Introspector.getBeanInfo(targetClass)
                .getPropertyDescriptors();
        PropertyDescriptor sourcetPds[] = Introspector.getBeanInfo(sourceClass)
                .getPropertyDescriptors();
        // 将忽略字段的数组转化为list
        List<String> ignoreList = ignoreProperties == null ? null : Arrays
                .asList(ignoreProperties);
        // 把源对象的所有属性放的map中
        Map<String, PropertyDescriptor> sourcePropertyMap = new HashMap<String, PropertyDescriptor>();
        for (int i = 0; i < sourcetPds.length; i++) {
            PropertyDescriptor pd = sourcetPds[i];
            sourcePropertyMap.put(pd.getName(), pd);
        }
        for (int i = 0; i < targetPds.length; i++) {
            PropertyDescriptor targetPd = targetPds[i];
 
            if (targetPd.getWriteMethod() == null || ignoreProperties != null
                    && ignoreList.contains(targetPd.getName()))
                continue;
            PropertyDescriptor sourcePd = sourcePropertyMap.get(targetPd
                    .getName());
            if (sourcePd == null || sourcePd.getReadMethod() == null)
                continue;
            try {
                // 得到源对象对应的属性值
                Method readMethod = sourcePd.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass()
                        .getModifiers()))
                    readMethod.setAccessible(true);
                Object value = readMethod.invoke(source, new Object[0]);
                // 将源对象的属性值赋值给目标对象对应的属性
                Method writeMethod = targetPd.getWriteMethod();
                if (!Modifier.isPublic(writeMethod.getDeclaringClass()
                        .getModifiers()))
                    writeMethod.setAccessible(true);
                writeMethod.invoke(target, new Object[] { value });
            } catch (Throwable ex) {
                throw new IllegalArgumentException(
                        "Could not copy properties from source to target", ex);
            }
        }
 
    }

}
