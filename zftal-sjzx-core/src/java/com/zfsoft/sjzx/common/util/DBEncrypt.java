package com.zfsoft.sjzx.common.util;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.crypto.Cipher;

import org.springframework.beans.factory.FactoryBean;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.zfsoft.zfca.tp.cas.util.Tool;

public class DBEncrypt implements FactoryBean {

	private Properties properties;

	public Object getObject() throws Exception {
		return getProperties();
	}

	public Class getObjectType() {
		return java.util.Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("user");
		String originalPassword = properties.getProperty("password");
		if (originalUsername != null) {
			String newUsername = deEncryptUsername(originalUsername);
			properties.put("user", newUsername);
		}
		if (originalPassword != null) {
			String newPassword = deEncryptPassword(originalPassword);
			properties.put("password", newPassword);
		}
	}

	private String deEncryptUsername(String originalUsername) {
		return dCode(originalUsername.getBytes());
	}

	private String deEncryptPassword(String originalPassword) {
		return dCode(originalPassword.getBytes());
	}

	public static String eCode(String needEncrypt){
		byte result[] = null;
		try {
			Cipher enCipher = Cipher.getInstance("DES");
			javax.crypto.SecretKey key = Key.loadKey();
			enCipher.init(1, key);
			result = enCipher.doFinal(needEncrypt.getBytes());
			BASE64Encoder b = new BASE64Encoder();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			b.encode(result, bos);
			result = bos.toByteArray();
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return new String(result);
	}

	public static String dCode(byte result[]){
		String s = null;
		try {
			Cipher deCipher = Cipher.getInstance("DES");
			deCipher.init(2, Key.loadKey());
			BASE64Decoder d = new BASE64Decoder();
			result = d.decodeBuffer(new String(result));
			byte strByte[] = deCipher.doFinal(result);
			s = new String(strByte);
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return s;
	}
	/**
	 * <p>验证密钥是否正确</p>
	 * @param sign
	 * @return
	 */
	public static boolean isEncrypt(String sign) {	
		String meskey="zfsoft_sjzx_webservice";//默认mesKey 
		String signKey = eCode(meskey);
		if(!Tool.isNull(signKey)&&!Tool.isNull(sign)){
			if(signKey.equals(sign))return true;
		}
		return false;
	}
	public static void main(String[] args){
    	String s = "zfsoft_sjzx_webservice";
    	DBEncrypt p = new DBEncrypt();
    	String afterE = p.eCode(s);
    	System.out.println(afterE);
    	System.out.println(p.dCode("OXjBbsdJao4=".getBytes()));
    	System.out.println(p.dCode("6eoRbG6AJWQ=".getBytes()));
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          