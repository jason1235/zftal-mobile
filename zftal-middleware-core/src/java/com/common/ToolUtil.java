/**
 * 
 */
package com.common;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * @author Administrator lwz
 *
 */
public class ToolUtil {
	
	
	private final static String[] hex = { "00", "01", "02", "03", "04", "05",
		"06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
		"11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
		"1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
		"27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
		"32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
		"3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
		"48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
		"53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
		"5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
		"69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
		"74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
		"7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
		"8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
		"95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
		"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
		"AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
		"B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
		"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
		"CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
		"D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
		"E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
		"ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
		"F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
		0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
		0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	/**
	 * 
	* @Title: isNull
	* @Description: (判断字符串是否为空)
	* @param @param p
	* @param @return    设定文件[wen jian]
	* @author  lwz
	* @version 创建时间(修改时间)：2012-6-29 上午09:08:48
	* @return boolean    返回类型[lei xing][fan hui lei xing]
	* @throws
	 */
	public static boolean isNull(String p) {
		if (p == null || p.equalsIgnoreCase("")) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	* @Title: eCode
	* @Description: TODO(将字符串加密)
	* @param @param needEncrypt
	* @param @return    设定文件[wen jian]
	* @author  lwz
	* @version 创建时间(修改时间)：2012-6-29 下午02:41:20
	* @return String    返回类型[lei xing][fan hui lei xing]
	* @throws
	 */
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
	
	/**
	 * 
	* @Title: dCode
	* @Description: (解密)
	* @param @param result
	* @param @return    设定文件[wen jian]
	* @author  lwz
	* @version 创建时间(修改时间)：2012-6-29 下午02:41:43
	* @return String    返回类型[lei xing][fan hui lei xing]
	* @throws
	 */
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
	
	public static String escape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			if (ch == ' ') { // space : map to '+'
				sbuf.append('+');
			} else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
				sbuf.append((char) ch);
			} else if (ch == '-'
					|| ch == '_' // unreserved : as it was
					|| ch == '.' || ch == '!' || ch == '~' || ch == '*'
					|| ch == '\'' || ch == '(' || ch == ')') {
				sbuf.append((char) ch);
			} else if (ch <= 0x007F) { // other ASCII : map to %XX
				sbuf.append('%');
				sbuf.append(hex[ch]);
			} else { // unicode : map to %uXXXX
				sbuf.append('%');
				sbuf.append('u');
				sbuf.append(hex[(ch >>> 8)]);
				sbuf.append(hex[(0x00FF & ch)]);
			}
		}
		return sbuf.toString();
	}
	
	public static String getNowDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String yemp = simpleDateFormat.format(new java.util.Date());
		return yemp;
	}
	
	public static String getNowTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String yemp = simpleDateFormat.format(new java.util.Date());
		return yemp;
	}
	
	public static String getId() {
		String recordid = "";
		java.util.Date dt = new java.util.Date();
		long lg = dt.getTime();
		Long ld = new Long(lg);
		String rand = new Integer(new java.util.Random().nextInt(90000) + 10000)
				.toString();
		recordid = ld.toString() + rand;
		return recordid;
	}
	
	
	/**
	 * 
	* @Title: getErrorInfo
	* @Description: 传入错误编号和错误信息，返回客户端错误格式信息
	* @param @param bh
	* @param @param info
	* @param @return    设定文件[wen jian]
	* @author  lwz
	* @version 创建时间(修改时间)：2012-7-3 上午11:52:52
	* @return String    返回类型[lei xing][fan hui lei xing]
	* @throws
	 */
	public static String getErrorInfo(String bh,String info){
		StringBuilder bf = new StringBuilder();
		bf.append("<ResultInfo>");
		bf.append("<code>"+bh+"</code>");
		bf.append("<message>"+info+"</message>");
		bf.append("</ResultInfo>");
		return bf.toString();
	}
	public static String getUrlInfo(String url){
		StringBuilder bf = new StringBuilder();
		bf.append("<ResultInfo>");
		bf.append("<url>"+url+"</url>");
		bf.append("</ResultInfo>");
		return bf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(ToolUtil.eCode("zfsoft000964"));
	}
}
