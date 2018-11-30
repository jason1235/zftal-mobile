package com.zfsoft.orcus.lang;

import java.util.StringTokenizer;

/**
 * 字符串操作的工具类，提供附加的字符串操作功能；
 * <p>
 * 包括将字符串或字符串数组格式化为表格或网格，获取
 * 字符串字节数，字符串剪切、替代等等
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class StringUtil {

	/**
	 * 返回包含count个input字符串的字符串
	 * @param input 输入字符串
	 * @param count 重复次数
	 * @return
	 */
	public final static String repeat(String input, int count) {
		if( input == null || count < 0) {
			return "";
		}
		
		StringBuffer output = new StringBuffer("");
		
		for (int i = 0; i < count; i++) {
			output.append(input);
		}
		
		return output.toString();
	}
	
	/**
	 * 返回subString在input张出现的次数
	 * 
	 * @param input 字符串
	 * @param subString 子字符串
	 * @return
	 */
	public final static int appear(String input, String subString) {
		if( input == null || input.equals("") ) {
			return 0;
		}
		
		if( subString == null || "".equals(subString) ) {
			return 0;
		}
		
		int first = 0;
		int count = 0;
		
		while ( (first = input.indexOf(subString, first) + 1) >= 1 ) {
			++count;
		}
		
		return count;
	}
	
	/**
	 * 用newSubString替换input中包含的所有oldSubString
	 * @param input 字符串
	 * @param oldSubString 原子字符串
	 * @param newSubString 新子字符串
	 */
	public final static String replace( String input, String oldSubString, String newSubString ) {
		if( input == null || "".equals(input) ) return "";
		
		return replace(input, oldSubString, newSubString, 0);
	}
	
	/**
	 * 用newSubString替换input中包含的所有oldSubString
	 * @param input 字符串
	 * @param oldSubString 原子字符串
	 * @param newSubString 新子字符串
	 * @param start 在input中查找oldSubString的起始位置
	 */
	public final static String replace( String input, String oldSubString, String newSubString, int start ) {
		if( input == null || "".equals(input) ) return "";
		
		return replace( input, oldSubString, newSubString, start, input.length() );
	}
	
	/**
	 * 用newSubString替换input中包含的所有oldSubString
	 * @param input 字符串
	 * @param oldSubString 原子字符串
	 * @param newSubString 新子字符串
	 * @param start 在input中查找oldSubString的起始位置
	 * @param end 在input中查找oldSubString的结束位置
	 */
	public final static String replace( String input, String oldSubString, String newSubString, int start, int end ) {
		if( input == null || "".equals(input) ) return "";
		if( oldSubString == null || "".equals(oldSubString) ) return "";
		if( newSubString == null ) return "";
		
		int len1 = input.length();
		int len2 = oldSubString.length();
		
		if( start < 0 ) start = 0;
		if( end >= len1 ) end = len1 - 1;
		if( ( end + 1 - start ) < len2 ) return input;
		
		StringBuffer out = new StringBuffer( input.substring( 0, start ) );;
		
		int i = start;
		while ( i <= end + 1 - len2 ) {
			if( input.substring( i, i + len2 ).equals( oldSubString ) ) {
				out.append(newSubString);
				i += len2;
			} else {
				out.append( input.charAt(i) );
				++i;
			}
		}
		
		out.append( input.substring(i, len1) );
		
		return out.toString();
	}
	
	/**
	 * 分割字符串
	 * @param input 将被分隔的字符串
	 * @param delimiter 分隔符，delimiter中的每个字符都将做为分隔符
	 * @return 如果input == null,返回null;如果delimiter == null || delimiter.equals( "" ), 返回String[]{ input };
	 */
	public final static String[] split( String input, String delimiter ) {
		return split( input, delimiter, false );
	}
	
	/**
	 * 分割字符串
	 * @param input 将被分隔的字符串
	 * @param delimiter 分隔符，delimiter中的每个字符都将做为分隔符
	 * @param returnDelims flag indicating whether to return the delimiters as tokens.
	 * @return 如果input == null,返回null;如果delimiter == null || delimiter.equals( "" ), 返回String[]{ input };
	 */
	public final static String[] split( String input, String delimiter, boolean returnDelims ) {
		if( input == null ) {
			return null;
		}
		
		if( delimiter == null || delimiter.length() == 0 ) {
			return new String[]{input};
		}
		
		StringTokenizer tokenizer = new StringTokenizer(input, delimiter, returnDelims);
		
		String[] tokens = new String[tokenizer.countTokens()];
		
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokenizer.nextToken();
		}
		
		return tokens;
	}
	
}
