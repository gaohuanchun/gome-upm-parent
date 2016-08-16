package com.gome.upm.common.util;

import java.security.MessageDigest;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基础加密组件
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public abstract class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}
	
	
	/**
	 * 生成随即密码
	 * @param pwd_len 生成的密码的总长度
	 * @return 密码的字符串
	*/
	 public static String genRandomNum(int pwd_len){
	//35是因为数组是从0开始的，26个字母+10个数字
	 final int maxNum = 36;
	 int i; //生成的随机数
	 int count = 0; //生成的密码的长度
	 char[] str = { 'a', 'b', 'c',  'g', 'h', 'i', 'j', 'k',
			        'A','B','C','D','E','F','G', 'd', 'e', 'f',
			        '2', '3', '4', '5', 't', 'u', 'v', 'w',
			         'g', 'h','H','I','J','K','L','M','N','O',
			        'P','Q','R','S','T','U','V','W','X','Y','Z' ,
					'a', 'b', 'c',  'g', 'h', 'i', 'j', 'k',
					'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
					'x', 'y', 'z', '0', '1', '6', '7', '8', '9'};

	 StringBuffer pwd = new StringBuffer("");
	 Random r = new Random();
	 while(count < pwd_len){
	//生成随机数，取绝对值，防止生成负数，

	 i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1

	 if (i >= 0 && i < str.length) {
		 pwd.append(str[i]);
		 count ++;
	 }
	}

	 return pwd.toString();
	}
}
