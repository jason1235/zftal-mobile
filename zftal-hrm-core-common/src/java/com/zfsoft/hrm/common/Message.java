package com.zfsoft.hrm.common;

import java.io.Serializable;

/**
 * 操作结果提示信息实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-28
 * @version V1.0.0
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = -1916711541716633072L;

	private boolean success = true;	//操作是否成功
	
	private String text;		//消息内容
	
	/**
	 * (空)构造函数
	 */
	public Message() {
		this(true, null);
	}
	
	/**
	 * 构造函数
	 * @param success 操作是否成功
	 * @param text 消息内容
	 */
	public Message(boolean success, String text) {
		this.success = success;
		this.text = text;
	}

	public String getHtml() {
		StringBuffer out = new StringBuffer();
		String style = success ? "img img_smile" : "img img_fail";
		String content = success ? "操作成功！" : "操作失败！";
		
		
		
		out.append("<div class=\"open_prompt\">");
		out.append("  <table width=\"100%\" border=\"0\" class=\"table01\">");
		out.append("    <tr>");
		out.append("      <td width=\"109\"><div class=\""+ style + "\"></div></td>");
		out.append("      <th width=\"197\"><p>" + ( text == null || "".equals(text) ? content : text ) + "</p></th>");
		out.append("    </tr>");
		out.append("    <tr>");
		out.append("      <td colspan=\"2\" align=\"center\" class=\"btn01\">");
		out.append("        <input id=\"window-sure\" type=\"button\" name=\"确定 \" class=\"button\" value=\"确 定\" />");
		out.append("      </td>");
		out.append("    </tr>");
		out.append("  </table>");
		out.append("</div>");
		
		return out.toString();
	}
	
	/**
	 * 返回操作是否成功
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置操作是否成功
	 * @param success 操作是否成功
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 返回消息内容
	 */
	public String getText() {
		if( text == null ) {
			text = "";
		}
		
		return text;
	}

	/**
	 * 设置消息内容
	 * @param text 消息内容
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}