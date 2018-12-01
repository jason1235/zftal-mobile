/**
 * 
 */
package com.common;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;

/**
 * @author Administrator lwz
 *
 */
public class Key {
	private static String encryptKey = "7EV/Zzutjzg=";

	public Key()
	{
	}

	public static SecretKey loadKey()
		throws Exception
	{
		/*SecretKey key = null;
		java.io.InputStream fis = (com.zfsoft.common.util.encrypt.Key.class).getResourceAsStream("encrypt.key");
		if (fis != null)
		{
			BASE64Decoder d = new BASE64Decoder();
			//byte b[] = d.decodeBuffer(fis);
			byte b[] = d.decodeBuffer(encryptKey);
			DESKeySpec dks = new DESKeySpec(b);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			key = keyFactory.generateSecret(dks);
		}
		return key;*/
		
		BASE64Decoder d = new BASE64Decoder();
		//byte b[] = d.decodeBuffer(fis);
		byte b[] = d.decodeBuffer(encryptKey);
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		return  keyFactory.generateSecret(dks);
	}
}
