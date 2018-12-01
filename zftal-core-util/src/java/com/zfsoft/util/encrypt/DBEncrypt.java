package com.zfsoft.util.encrypt;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.crypto.Cipher;

import org.springframework.beans.factory.FactoryBean;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
		String originalJdbcUrl = properties.getProperty("jdbcUrl");
		if (originalUsername != null) {
			String newUsername = deEncryptUsername(originalUsername);
			properties.put("user", newUsername);
		}
		if (originalPassword != null) {
			String newPassword = deEncryptPassword(originalPassword);
			properties.put("password", newPassword);
		}
//		if (originalJdbcUrl != null) {
//			String newJdbcUrl = deEncryptJdbcUrl(originalJdbcUrl);
//			properties.put("jdbcUrl", newJdbcUrl);
//		}
	}

	private String deEncryptUsername(String originalUsername) {
		return dCode(originalUsername.getBytes());
	}
	
	private String deEncryptJdbcUrl(String originalJdbcUrl) {
		return dCode(originalJdbcUrl.getBytes());
	}

	private String deEncryptPassword(String originalPassword) {
		return dCode(originalPassword.getBytes());
	}

	public String eCode(String needEncrypt){
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

	public String dCode(byte result[]){
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

	public static void main(String[] args){
		System.out.println("jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)" +
		            		"(HOST = 222.24.210.151)(PORT = 1521))(LOAD_BALANCE = yes)(FAILOVER = ON)" +
		            		"(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = oradb)(FAILOVER_MODE=(TYPE = SELECT)" +
		            		"(METHOD = BASIC)(RETIRES = 20)(DELAY = 15))))");
    	//String s = "zf_mobile";
    	String s1 = "zftal_mobile";
		String s = "jdbc:oracle:thin:@10.71.19.133:1521:orcl";
    	DBEncrypt p = new DBEncrypt();
    	String afterE1 = p.eCode(s1);
    	String afterE = p.eCode(s);
    	System.out.println(afterE);
    	System.out.println(afterE1);
    	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSRYguMAm9Sijs60p2ZfE8+wHkVsh0SdX85+Y60A4RnMl".getBytes()));
    	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSRYguMAm9Sijs60p2ZfE8+wHkVsh0SdX85+Y60A4RnMl".getBytes()));
    	System.out.println(p.dCode("2OuWvBYSOcaJ9bisxGRaSg==".getBytes()));
    	System.out.println(p.dCode("2OuWvBYSOcaJ9bisxGRaSg==".getBytes()));
    	System.out.println(p.eCode("jdbc:oracle:thin:@10.71.19.133:1521:orcl"));
    	System.out.println(p.dCode("ypZrmdJ2ZG/vHZQDG0MekQ==".getBytes()));
    	System.out.println(p.dCode("FZVMzpPtKQcM7pGIrEmBJQ==".getBytes()));
    	System.out.println(p.eCode("usercenter_ver"));
    	System.out.println(p.dCode(p.eCode("999").getBytes()));
    	System.out.println(p.eCode("0"));
    	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSZlQwyz/LTKrMOFAoTTPFj9qZ6DOxTfkgJyuN8CSuxrZ".getBytes()));
    	System.out.println(p.dCode("jLAwzgEPwVgfUvxQFthvNg==".getBytes()));
    	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSdx/LrVdLfHXDYhhwxJlUVFCnznLP2vBjdVy4V37G/fDVM3AyKHHyAsjIoH6J3I6W4NoPyql6U6ypSK7gMdZNmqkGwbxeECIvxtrFzyBRWLZ+WDQMqpg5cQGqEEOq/XQH0yqiLRU+322j4haD2y0lUYV0f8hD5h5RnOB4oL7zPunzHcyUed2KPMPYmctXMoXY+xpKKb2OQCC5GBX1rv5Wr7aSQ2KeyKfcGXdxt6RX99az+g7hWHi/7gbQlvTvll3uOuKbV9gEcW8V/ITyL7ce+Ry6JRwHjpgU4U4C8A5L8k/KKhqlRJ1eQZiR+kYOiH/ZAD9NHLthYoXR1yyEhmRuZuH8Pq/q4WrvqIoA/U6upc2Qa+3P5/pkT4=".getBytes()));
    	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSRYguMAm9SijiTRgt4xIyhwHkVsh0SdX85+Y60A4RnMl".getBytes()));
    	System.out.println(p.dCode("4QbI8ZQ2LGxgZteVVIaubA==".getBytes()));
    	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSdx/LrVdLfHXKhMsKnUppzcJrln4NBQnfEoSKuWC1Yii4l7SOAz8uVZSYAANwOkPUwCklSASJyV1T/Ys0UrueHyMlzQh/fDQdrWfvIDbd11mzljIkyOyPGjo2czHGAv9Qul+0JNYONKbDqunzMOmfP4Q0gDeN7BM4K0WwAi5pLZ1BIF9y8o+j9hDdHYJr/GRESe6g1VRuCcatUp7fmLBZx2RDQezyYw5qbOy/pFR1tb0g4ZnawpoGG1Ml3n9OSR6wecEwhabOkmGkNuIfH4fqubYHsQHaWVkqAaoQQ6r9dAfjKDslTdpRjaH4SZMluq2MksaaWY5JUwdi+fXmZ7b9ymWaeEtr8ZHXXrKYp9hGESXhhvv8cx/pTifzXTDCiJCUZW8XBEDTO3oLPF8Gqeiq+9bSEUQ2EaTnM/oO4Vh4v+4G0Jb075Zd7jXYymAZMPwvURkbeWGv2LrfRbgOYhWHxwitnJcRNqVylk4q14wCIuQXM7dzsIopY8kgDs7OjMXLtv8zRAGg/4J5n06xhZt03imDJGGbDF74kA9gIbC4NOtGItqNLh+NlE=".getBytes()));
    	System.out.println(p.dCode("jLAwzgEPwVjDdSICo6Ub6A==".getBytes()));
	}
}