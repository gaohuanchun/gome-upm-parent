package com.gome.upm.common.util;

import java.util.UUID;

public class GenerateUUIDUtil {

	public static String randomUUid(){
	     String uuidString = UUID.randomUUID().toString();
		 return uuidString;
	}
	public static void main(String[] args) {
		System.out.println(GenerateUUIDUtil.randomUUid().length());
		System.out.println(GenerateUUIDUtil.randomUUid());
		System.out.println(GenerateUUIDUtil.randomUUid());
		System.out.println(GenerateUUIDUtil.randomUUid());
	}
}
