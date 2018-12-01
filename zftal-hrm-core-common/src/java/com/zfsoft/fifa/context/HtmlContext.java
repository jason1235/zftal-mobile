package com.zfsoft.fifa.context;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.fifa.statement.StatementElement;

/**
 * 解析页面的上下文
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public interface HtmlContext extends Serializable {

	/**
	 * 页面显示类别
	 * @return
	 */
	public String getHtmlType();
	
	/**
	 * 属性StatementElement类型
	 * @return
	 */
	public String getDataType();
	
	/**
	 * 获取属性对应的语义信息
	 * @return
	 */
	public StatementElement getStatementElement();
	
	/**
	 * Http请求
	 * @return
	 */
	public HttpServletRequest getRequest();
	
	/**
	 * 当前操作的JavaBean实例
	 * @return
	 */
	public Object getBean();
	
	/**
	 * 属性名
	 * @return
	 */
	public String pName();
	
	/**
	 * 属性在JavaBean中的值
	 * @return
	 */
	public String getValue();
	
	/**
	 * 获得当前次序
	 * @return
	 */
	public int getIndex();
	
	/**
	 * 当前操作的JavaBean实例
	 * @param bean
	 */
	public void setBean( Object bean );
	
	/**
	 * 属性名
	 * @param pName
	 */
	public void setName( String pName );
	
	/**
	 * 属性在JavaBean中的值
	 * @param request
	 */
	public void setRequest( HttpServletRequest request );
}
