package com.gome.upm.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author liyujian
 *
 */
public class RegexUtil {

	/**
	 * 手机号验证
	 * <b>移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 </b>
	 * <b>联通：130、131、132、152、155、156、185、186 </b>
	 * <b>电信：133、153、180、189、（1349卫通）</b>
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobileNum(String mobile) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
}