package com.util;

import java.net.URLEncoder;

import com.anysoft.webservicetest.encoder.DBEncrypt;
import com.common.ToolUtil;


/**
 * <p>Description: 得到业务系统的秘钥</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-2-3 上午10:54:50
 * @author yangz
 * @version 1.0
 */
public class MiddleWareUtil {
	/**
	 * 加密KEY
	 */
	private final static String encryptKey = "zfsoft";
	
	/**
	 * 教务签名key
	 */
	private static String jwstrKey = "DAFF8EA19E6BAC86E040007F01004EA";
	
	
	/**
	 * 推送秘钥
	 */
	private static String tsstrKey = "zfsoft_ydxy";
	
	/**
	 * <p>Description: 得到OA秘钥</p>
	 * @param sign
	 * @param username
	 * @return
	 *
	 * @since 2015-2-3 上午11:08:05
	 * @author yangz
	 */
	public static String getSign(String username) {
		String oasign =null;
		if (!"".equals(username)&&!"null".equals(username)) {
			DBEncrypt p = new DBEncrypt();
			oasign = p.eCode(encryptKey + username);
		}
		return oasign;
	}
	
	/**
	 * <p>Description: 得到教务秘钥</p>
	 * @param str
	 * @return
	 *
	 * @since 2015-2-3 上午11:08:50
	 * @author yangz
	 */
	public static String getJWSign(String str) {
		String temp = URLEncoder.encode(str).toUpperCase().replace("-", "%2D") + jwstrKey;
		String jwsign = MD5Utilmobile.getMD5ofStr(temp);
		return jwsign;
	}
	
	/**
	 * <p>Description: 得到学工秘钥</p>
	 * @param username
	 * @return
	 *
	 * @since 2015-2-3 上午11:17:54
	 * @author yangz
	 */
	public static String getXGSign(String username){
		String xgsign=ToolUtil.eCode(encryptKey+username);
		return xgsign;
	}
	
	
	/**
	 * <p>Description: 得到ZFSMP秘钥</p>
	 * @param str
	 * @return
	 *
	 * @since 2015-2-11 下午02:27:20
	 * @author yangz
	 */
	public static String getSMPSign(String str) {
	//	String temp = URLEncoder.encode(str).toUpperCase();
		String smpsign = MD5Utilmobile.getMD5ofStr(str);
		return smpsign;
	}
	
	
    /**
     * <p>Description: 得到推送秘钥</p>
     * @param str
     * @return
     *
     * @since 2015-5-12 下午05:02:05
     * @author yangz
     */
    public static String getRSSign(String str){
    	String rspsign = Encrypt.encrypt(str+tsstrKey);
		return rspsign;
    }

}
