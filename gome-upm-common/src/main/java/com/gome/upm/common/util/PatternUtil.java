package com.gome.upm.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @Description 正则工具类
 * @author anguangcan
 * @date 2015-4-27 下午3:56:58 
 * @version V1.0
 */ 
  	
public class PatternUtil {
	
	private static final String TEL_REGEX = "^[1][\\d]{10}";
	
	private static final String DIGIT_REGEX = "[0-9]+";
	
	private static final String SAMEWORD_REGEX = "^(.)\\1+$";
	
	 
	/** 
	 * @Description 简单验证手机号是否符合规则
	 * @author anguangcan
	 * @param tel
	 * @return  
	 */
	  	
	public static boolean validTel(String tel) {
		return executor(TEL_REGEX, tel);
	}
	
	 
	/** 
	 * @Description 是否全是数字
	 * @author anguangcan
	 * @param str
	 * @return  
	 */
	  	
	public static boolean isAllDigit(String str) {
		return executor(DIGIT_REGEX, str);
	}
	
	 
	/** 
	 * @Description 是否为同一字符
	 * @author anguangcan
	 * @param str
	 * @return  
	 */
	  	
	public static boolean isSameWord(String str) {
		return executor(SAMEWORD_REGEX, str);
	}
	
	 
	/** 
	 * @Description 正则执行器
	 * @author anguangcan
	 * @param regex
	 * @param data
	 * @return  
	 */
	  	
	public static boolean executor(String regex , String data) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		
		boolean result = matcher.matches();
		
		return result;
	}
	
	public static void main(String[] args) {
		boolean result = isSameWord("22222232");
		System.out.println(result);
	}

}
