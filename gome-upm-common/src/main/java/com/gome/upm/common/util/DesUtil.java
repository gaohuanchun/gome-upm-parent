/**
 * 
 */
package com.gome.upm.common.util;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author niulu
 * 
 *         2014年7月2日 下午3:45:45
 */
public class DesUtil {

	private final static String DES = "DES";

	public static void main(String[] args) throws Exception {
		String data = "123";
		String key = "8B6697227CBCA902B1A0925D40FAA00B353F2DF4359D2099";
		System.err.println(encrypt(data, key));
		System.err.println(decrypt(encrypt(data, key), key));

		byte[] bytes = key.getBytes();
		System.out.println(bytes.length);

	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = new String(new Base64().encode(bt));
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 */
	public static String decrypt(String data, String key) throws IOException, Exception {
		if (data == null)
			return null;

		byte[] buf = Base64.decodeBase64(data.getBytes());
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt);
	}

	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		return makeData(data, key, Cipher.ENCRYPT_MODE);
	}

	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		return makeData(data, key, Cipher.DECRYPT_MODE);
	}

	private static byte[] makeData(byte[] data, byte[] key, int opmode) throws Exception {

		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES);

		cipher.init(opmode, securekey, sr);

		return cipher.doFinal(data);
	}

}
