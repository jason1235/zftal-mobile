package com.zfsoft.orcus.mast.servlet;

import com.zfsoft.orcus.lang.StringUtil;

/**
 * 字符串过滤器，用于HTML
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-26
 * @version V1.0.0
 */
public class StringFilter {

	public static final StringFilter DEFAULT = new StringFilter();
	
	/**
	 * 过滤用于在&lt;script&gt;元素中使用的字符串
	 * @param input 字符串
	 * @return 过滤后的字符串
	 */
	public String toScript( String input ) {
		
		return toScript(input, "'");
	}
	
	/**
	 * 过滤用于在&lt;script&gt;元素中使用的字符串
	 * @param input 字符串
	 * @param literalPrefix js中字符串的限定符
	 * @return 过滤后的字符串
	 */
	public String toScript( String input, String literalPrefix ) {
		if( input == null ) return null;
		
		String output = StringUtil.replace( input, "\\", "\\\\" );
		
		output = StringUtil.replace( output, "\t", "\\t" );
		output = StringUtil.replace( output, "\"", "\\\"" );
		output = StringUtil.replace( output, "\'", "\\\'" );
		output = StringUtil.replace( output, "\r", "\\r" );
		output = StringUtil.replace( output, "\n", "\\n" );
		output = StringUtil.replace( output, "<script>", "<script" + literalPrefix + "+" + literalPrefix + ">" );
		output = StringUtil.replace( output, "</script>", "</script" + literalPrefix + "+" + literalPrefix + ">" );
		output = StringUtil.replace( output, "<SCRIPT>", "<SCRIPT" + literalPrefix + "+" + literalPrefix + ">" );
		output = StringUtil.replace( output, "<SCRIPT>", "<SCRIPT" + literalPrefix + "+" + literalPrefix + ">" );
		
		return output;
	}
	
	/**
	 * 过滤用于直接在HTML文档中显示的字符串
	 * <p>same as toHtml( input, false )</p>
	 * @param input 字符串
	 * @return 过滤后的字符串
	 * @see #toHtml(String, boolean)
	 */
	public String toHtml( String input) {
		return toHtml( input, false );
	}
	
	/**
	 * 过滤用于直接在HTML文档中显示的字符串
	 * @param input 字符串
	 * @param fill
	 * 			<ul>
	 * 				<li>true	当input为空时返回&nbsp;</li>
	 * 				<li>false	当input为空时返回""</li>
	 * 			</ul>
	 * @return
	 */
	public String toHtml( String input, boolean fill ) {
		if( input == null || input.length() == 0 ) {
			return fill ? "&nbsp;" : "";
		}
		
		String output = StringUtil.replace( input, "&", "&amp;" );
		
		output = StringUtil.replace( output, "\"", "&quot;" );
		output = StringUtil.replace( output, "<", "&lt;" );
		output = StringUtil.replace( output, ">", "&gt;" );
		output = StringUtil.replace( output, " ", "&nbsp;" );
		
		output = StringUtil.replace( output, "\n\r", "<br/>" );
		output = StringUtil.replace( output, "\r\n", "<br/>" );
		output = StringUtil.replace( output, "\r", "<br/>" );
		output = StringUtil.replace( output, "\n", "<br/>" );
		output = StringUtil.replace( output, "\t", StringUtil.repeat( "&nbsp", 8 ) );
		
		return output;
	}
	
	/**
	 * 过滤用于HTML标签属性的字符串
	 * @param input 字符串
	 * @return 过滤后的字符串
	 */
	public String toProperty( String input ) {
		if( input == null ) {
			return "";
		}
		
		String output = StringUtil.replace( input, "&", "&amp;" );
		
		output = StringUtil.replace( output, "\"", "&quot;");
		output = StringUtil.replace( output, "<", "&lt;");
		output = StringUtil.replace( output, ">", "&gt;");
		
		return output;
	}
	
	
}
