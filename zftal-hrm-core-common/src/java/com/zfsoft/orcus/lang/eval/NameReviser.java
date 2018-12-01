package com.zfsoft.orcus.lang.eval;

import com.zfsoft.orcus.lang.StringUtil;

/**
 * 变量名修订器
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public class NameReviser {
	
	public final static String[] CHARS = new String[] {
		"~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=",
		"+", "{", "}", "[", "]", "|", "\\", ":", ";", "'", "\"", "<", ">", ",",
		" ", "?", "/", "！", "·", "￥", "……", "—", "（", "）", "——", "、", "：",
		"；", "‘", "’", "“", "”", "《", "》", "，", "。", "？"
	};
	
	public static String reviser( String name ) {
		if( name == null || name.length() == 0 ) {
			return name;
		}
		
		String out = name;
		
		for ( String str : CHARS ) {
			out = StringUtil.replace( out, str, "_" );
		}
		
		out = StringUtil.replace( out, "０", "0" );
		out = StringUtil.replace( out, "１", "1" );
		out = StringUtil.replace( out, "２", "2" );
		out = StringUtil.replace( out, "３", "3" );
		out = StringUtil.replace( out, "４", "4" );
		out = StringUtil.replace( out, "５", "5" );
		out = StringUtil.replace( out, "６", "6" );
		out = StringUtil.replace( out, "７", "7" );
		out = StringUtil.replace( out, "８", "8" );
		out = StringUtil.replace( out, "９", "9" );
		
		char c = out.charAt( 0 );
		
		if( c > '0' && c <= '9' ) {
			out = (char) ( c + 10 ) + out.substring( 1 );
		}
		
		return out;
	}

}
