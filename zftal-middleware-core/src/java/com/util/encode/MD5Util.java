package com.util.encode;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;


import com.anysoft.webservicetest.encoder.BASE64Encoder;
import com.common.Config;

/** 
 * 采用MD5加密
 * @datetime 2015-7-27 
 */
public class MD5Util {
    /*** 
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    /**
	 *对传递进来的字符串进行MD5计算,并进行base64位编码 
	 * @param str
	 * @return String
	 * @author yangbilin
	 * @exception NoSuchAlgorithmException,UnsupportedEncodingException
	 */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	//确定计算方法
    	MessageDigest md5=MessageDigest.getInstance("MD5");
    	BASE64Encoder base64en = new BASE64Encoder();
        if(StringUtils.isNotBlank(str)){
        	//加密后的字符串
        	String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        	return newstr;
        }
		return "";
	}

    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("hrmadmin");
        System.out.println("原始：" + str);
        System.out.println("MD5后：" + md5Encode(str));
        System.out.println(MD5Util.md5Encode("1211" + Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS")));
    }
}
