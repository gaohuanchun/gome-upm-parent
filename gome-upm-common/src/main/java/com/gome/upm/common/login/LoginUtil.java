package com.gome.upm.common.login;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class LoginUtil {

	/**
	 * 加密、解密key.
	 */
	private static final String PASSWORD_CRYPT_KEY = "gomedscn";

	public final static boolean checkLogin(String userId, String password) {

		//String address = "http://10.58.53.17:801/LoginService.asmx"; // 此处最好用系统参数
		String address = "http://10.58.50.212:8998/DomainService.asmx";
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
		bean.setAddress(address);
		bean.setServiceClass(DomainServiceSoap.class);
		DomainServiceSoap ws = (DomainServiceSoap) bean.create();
		try {

			String result = ws.validLogon(userId, DesUtil.encrypt(password,
			PASSWORD_CRYPT_KEY));
			if(result != null & result.equals("Y")){
			return true;
			}else {
			return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public static void main(String[] args) {
		boolean checkLogin = checkLogin("liuhaikun-ds", "1q2w3e4r@");
		System.out.println(checkLogin);
	}
}
