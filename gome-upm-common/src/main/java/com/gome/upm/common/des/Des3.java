package com.gome.upm.common.des;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author liyujian
 *
 */
public class Des3 {
	// 密钥   
    private final static String secretKey = "0123456789ABCDEFGHIGKLMN";   
    // 向量   
    private final static String iv = "01234567";   
    // 加解密统一使用的编码方式   
    private final static String encoding = "utf-8";   
  
    /**  
     * 3DES加密  
     *   
     * @param plainText 普通文本  
     * @return  
     * @throws Exception   
     */  
    public static String encode(String plainText) throws Exception {   
        Key deskey = null;   
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());   
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");   
        deskey = keyfactory.generateSecret(spec);   
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");   
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());   
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);   
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));   
        return Base64.encode(encryptData);   
    }   
  
    /**  
     * 3DES解密  
     *   
     * @param encryptText 加密文本  
     * @return  
     * @throws Exception  
     */  
    public static String decode(String encryptText) throws Exception {   
        Key deskey = null;   
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());   
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");   
        deskey = keyfactory.generateSecret(spec);   
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");   
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());   
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);   
  
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));   
  
        return new String(decryptData, encoding);   
    }   
    
    public static void main(String[] args) throws Exception {
    	String encode = Des3.encode("lvzjane");
    	System.out.println(encode);
    	String decode = Des3.decode(encode);
    	System.out.println(decode);
		/*String str = "{\"password\":\"mm123456\",\"userName\":\"13811960215\"}";
		String data = encode(str);
		System.out.println( "**************" + data);
    	//String data = "ILm86EYqfJ8b6nDpd3if0ANRchitobTl0a1bU18CWr9N0QVndEV/F98DiMkJJJ8k6B/asbIsCTw=";
		String res = decode(data);
		
		System.out.println(res);
		String url = "http://localhost/login/dologin";
		
		Map<String, String> sParaTemp = new HashedMap();
		sParaTemp.put("content", "OzFXWARiLNNNPJ1DcX8tz9II34lSQXBxeYu+K5I29w4KCJC+lD62O3Xqv9I7 qRpXivlpKqSCdpE=");
		
		String resp = HttpClientUtils.post(url, sParaTemp );
		System.out.println(resp);*/
	}

}
