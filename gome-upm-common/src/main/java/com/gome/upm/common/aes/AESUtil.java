package com.gome.upm.common.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/** 
 * @Description aes加密解密类,加密解密指定seed，其中seed需要为8的倍数
 * @author anguangcan
 * @date 2015-4-27 上午10:51:22 
 * @version V1.0
 */ 
  	
public class AESUtil {

	/** 
	 * @Description 加密包装方法
	 * @author anguangcan
	 * @param seed 秘钥(字节数需要为8的倍数)
	 * @param content 待加密的内容 
	 * @return
	 * @throws Exception  
	 */
	  	
	public static String encrypt(String seed, String content)
			throws Exception {
		
		byte[] rawKey = seed.getBytes();
		byte[] result = encrypt(rawKey, content.getBytes());
		
		return toHex(result);
	}

	 
	/** 
	 * @Description 解密包装方法
	 * @author anguangcan
	 * @param seed 秘钥(需要为8的倍数)
	 * @param content
	 * @return
	 * @throws Exception  
	 */
	  	
	public static String decrypt(String seed, String content)
			throws Exception {
		
		byte[] rawKey = seed.getBytes();
		byte[] enc = toByte(content);
		byte[] result = decrypt(rawKey, enc);
		
		return new String(result);
	}

	 
	/** 
	 * @Description  加密方法
	 * @author anguangcan
	 * @param raw 字节型秘钥
	 * @param clear 加密内容
	 * @return
	 * @throws Exception  
	 */
	  	
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	 
	/** 
	 * @Description 解密方法
	 * @author anguangcan
	 * @param raw 字节型秘钥
	 * @param encrypted 解密内容
	 * @return
	 * @throws Exception  
	 */
	  	
	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	public static void main(String[] args) throws Exception {
		String str = "{\"valideCode\":\"4346\",\"confirmPassword\":\"1qaz2wsx\",\"password\":\"1qaz2wsx\",\"username\":\"13716348277\"}";
		//String str = "{\"password\":\"1qaz2wsx\",\"username\":\"13716348271\"}";
        System.out.println("加密前：" + str);  
  
        String key = "anguangcananguan";  
        System.out.println("加密密钥和解密密钥：" + key);  
		String text = AESUtil.encrypt(key, str);
		System.out.println(text);
		text = AESUtil.decrypt(key, text);
		System.out.println(text);
	}

}
