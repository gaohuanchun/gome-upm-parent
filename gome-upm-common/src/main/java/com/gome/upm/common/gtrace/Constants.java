package com.gome.upm.common.gtrace;

import java.util.HashMap;
import java.util.Map;

/**
 * henry.yu
 */
public class Constants {
	public static final String SESSION_LOGIN_INFO_KEY = "LOGIN_USER_INFO";

	public static class USR {
		public static final String ROLE_TYPE_USER = "user";
		public static final String STR_VAL_A = "A";
	}

	public static final String STATUS_CODE_9 = "9";

	public static final String TABLE_NAME_CHAIN = "gt-call-chain";
	public static final String CHAIN_DETAIL_TABLE_NAME = "gt-chain-detail";
	public static final String CID_MAPPING_TABLE_NAME = "gt-treeId-cid-mapping";
	public static final String _1MONTH_SUM_TABLE_NAME = "gt-chain-1month-summary";
	public static final String _1DAY_SUM_TABLE_NAME = "gt-chain-1day-summary";
	public static final String _1HOUR_SUM_TABLE_NAME = "gt-chain-1hour-summary";
	public static final char VAL_SPLIT_CHAR = '.';
	public static final String RPC_END_FLAG = "-S";

	public static Map<String, String> SPAN_TYPE_MAP = new HashMap<String, String>() {
		{
			put("M", "JAVA");
			put("J", "JDBC");
			put("W", "WEB");
			put("D", "DUBBO");
			put("U", "UNKNOWN");
		}
	};

	public static final String SPAN_TYPE_U = "U";

	public static Map<String, String> STATUS_CODE_MAP = new HashMap<String, String>() {
		{
			put("0", "OK");
			put("1", "FAIL");
			put("9", "MISSING");
		}
	};

	public static int MAX_SEARCH_SPAN_SIZE = 10000;

	public static int MAX_SHOW_SPAN_SIZE = 200;

	public static int MAX_ANALYSIS_RESULT_PAGE_SIZE = 10;
}