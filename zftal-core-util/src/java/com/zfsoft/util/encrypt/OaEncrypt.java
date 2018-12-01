/**
 * 正方加密算法
 */
package com.zfsoft.util.encrypt;

public class OaEncrypt {
	private String key;

	public OaEncrypt() {
		// 被始化KEY
		this.key = "Encrypt01";
	}

	/**
	 * 
	 * 功能描述：将明码加密 作者:正方; 日期:2008-6-30 日期:上午10:11:59 方法名:encrypt 访问类:Encrypt
	 * 
	 * @param PlainStr
	 * @return String
	 * 
	 */
	public String encrypt(String PlainStr) {
		String PStr, KeyStr, NewStr;
		char PChar, KeyChar;
		int Pos, i;
		String Side1, Side2;
		NewStr = "";
		Pos = 0;
		for (i = 0; i < PlainStr.length(); i++) {
			PStr = PlainStr.substring(i, i + 1);
			KeyStr = key.substring(Pos, Pos + 1);
			PChar = PStr.charAt(0);
			KeyChar = KeyStr.charAt(0);

			if (((PChar ^ KeyChar) < 32) || ((PChar ^ KeyChar) > 126)
					|| ((PChar) < 0) || ((PChar) > 255)) {
				NewStr = NewStr + PStr;
			} else {

				PChar = (char) (PChar ^ KeyChar);
				NewStr = NewStr + String.valueOf(PChar);
			}
			Pos++;
			if (Pos == key.length()) {
				Pos = 0;
			}
		}
		if ((NewStr.length() % 2) == 0) {
			Side1 = NewStr.substring(0, NewStr.length() / 2);
			Side2 = NewStr.substring(NewStr.length() / 2, NewStr.length());
			StringBuffer s1 = new StringBuffer(Side1);
			s1.reverse();
			Side1 = String.valueOf(s1);
			StringBuffer s2 = new StringBuffer(Side2);
			s2.reverse();
			Side2 = String.valueOf(s2);
			NewStr = Side1 + Side2;
		}
		return NewStr;
	}

	/**
	 * 
	 * 功能描述： 解密 作者:正方; 日期:2008-6-30 日期:上午10:13:14 方法名:decrypt 访问类:Encrypt
	 * 
	 * @param PlainStr
	 * @return String
	 * 
	 */
	public String decrypt(String PlainStr) {
		String PStr, KeyStr, NewStr;
		char PChar, KeyChar;
		int Pos, i;
		String Side1, Side2;
		NewStr = "";
		Pos = 0;
		if ((PlainStr.length() % 2) == 0) {
			Side1 = PlainStr.substring(0, PlainStr.length() / 2);
			Side2 = PlainStr
					.substring(PlainStr.length() / 2, PlainStr.length());
			StringBuffer s1 = new StringBuffer(Side1);
			s1.reverse();
			Side1 = String.valueOf(s1);
			StringBuffer s2 = new StringBuffer(Side2);
			s2.reverse();
			Side2 = String.valueOf(s2);
			PlainStr = Side1 + Side2;
		}

		for (i = 0; i < PlainStr.length(); i++) {
			PStr = PlainStr.substring(i, i + 1);
			KeyStr = key.substring(Pos, Pos + 1);
			PChar = PStr.charAt(0);
			KeyChar = KeyStr.charAt(0);
			if (((PChar ^ KeyChar) < 32) || ((PChar ^ KeyChar) > 126)
					|| ((PChar) < 0) || ((PChar) > 255)) {
				NewStr = NewStr + PStr;
			} else {
				PChar = (char) (PChar ^ KeyChar);
				NewStr = NewStr + String.valueOf(PChar);
			}
			Pos++;
			if (Pos == key.length()) {
				Pos = 0;
			}
		}
		return NewStr;
	}

	public static void main(String[] args) {
		OaEncrypt e = new OaEncrypt();
		System.out.println(e.encrypt("0"));//加密
		System.out.println(new DBEncrypt().eCode("oa@123"));//加密
	}
}