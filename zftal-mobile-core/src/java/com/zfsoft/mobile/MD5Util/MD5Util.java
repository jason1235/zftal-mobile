package com.zfsoft.mobile.MD5Util;


import java.security.MessageDigest;

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
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("000000");
        System.out.println("原始：" + str);
        System.out.println("MD5后：" + md5Encode("appid=wxbca820ce9f1988e8&mch_id=10059030&nonceStr=1146351368&package=prepay_id=wx20161208115652386ed336bf0150480388&signType=MD5&timeStamp=1481168796&key=87950B4E678946B0AC649C9092F8797D"));
        System.out.println(MD5Util.md5Encode("1211" + Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS")));
        System.out.println(MD5Util.md5Encode("Szh900806"));
        System.out.println("MD5后：" + md5Encode("zf123123"));
    }
}
