package com.zfsoft.fifa.context;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.fifa.statement.StatementElement;
import com.zfsoft.orcus.beans.dyna.DynaBean;
import com.zfsoft.orcus.lang.converters.ConvertUtil;

/**
 * {@link HtmlContext }的实现类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class GenericContext implements HtmlContext {
	
	private static final long serialVersionUID = -4926959724516978233L;

	private Object bean;					//当前操作的JavaBean实例
	
	private String pName;					//属性名
	
	private int index;						//当前次序
	
	private String htmlType;				//页面显示类别
	
	private HttpServletRequest request;		//Http请求
	
	private StatementElement element;		//属性对应的语义信息

	@Override
	public Object getBean() {

		return bean;
	}

	@Override
	public String getDataType() {

		return element.getType();
	}

	@Override
	public String getHtmlType() {

		return htmlType;
	}

	@Override
	public int getIndex() {
		
		return index;
	}

	@Override
	public HttpServletRequest getRequest() {

		return request;
	}

	@Override
	public StatementElement getStatementElement() {

		return element;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getValue() {
		if( bean == null ) {
			return "";
		}
		
		Object value = null;
		
		if( bean instanceof DynaBean ) {
			value = ((DynaBean)bean).get( pName );
		} else if( bean instanceof Map ) {
			value = ((Map)bean).get( pName );
		}

		return ConvertUtil.convert(value);
	}

	@Override
	public String pName() {
		
		return pName;
	}

	@Override
	public void setBean(Object bean) {

		this.bean = bean;
	}

	/**
	 * 获得当前次序
	 * @param index 获得当前次序
	 */
	public void setIndex( int index) {
		this.index = index;
	}
	
	@Override
	public void setName(String pName) {

		this.pName = pName;
	}

	@Override
	public void setRequest(HttpServletRequest request) {

		this.request = request;
	}
	
	/**
	 * 设置属性对应的语义信息
	 * @param element 属性对应的语义信息
	 */
	public final void setElement( StatementElement element ) {
		
		this.element = element;
	}
	
	/**
	 * 设置页面显示类别
	 * @param htmlType 页面显示类别
	 */
	public final void setHtmlType(String htmlType ) {
		this.htmlType = htmlType;
	}
	

}
