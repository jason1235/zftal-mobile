package com.zfsoft.fifa.decorator;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.fifa.context.HtmlContext;

/**
 * ViewParser解析页面时,若页面元素需要特殊的显示,则可以定义对应的Decorator<br>
 * 如Image类型,需要显示为缩小的图片;如年龄虚拟字段,需要根据参数日期进行计算当前年龄
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public interface Decorator extends Serializable {

	/**
	 * 
	 * @param context
	 */
	public void setContext( HtmlContext context );
	
	/**
	 * 将属性的属性值转变为可用于显示的文本
	 * @param pName 属性名
	 * @param bean JavaBean对象
	 * @param index JavaBean对象在对象列表中的索引(base 0)
	 * @return 可用于显示的文本,若为null，则Decorator不提供该类型的特殊解析
	 */
	public String toViewText( String pName, Object bean, int index );
	
	/**
	 * 
	 * @param pName 属性名
	 * @param bean JavaBean对象
	 * @param index JavaBean对象在对象列表中的索引(base 0)
	 * @param request HTTP请求对象
	 * @return 可用于显示的HTML片断,若为null，则Decorator不提供该类型的特殊解析
	 */
	public String toViewHtml( String pName, Object bean, int index, HttpServletRequest request );
	
	/**
	 * 将属性转变为可用于编辑的HTML片断(never null)
	 * @param pName 属性名
	 * @param bean JavaBean对象(可以为null)
	 * @param request request HTTP请求对象
	 * @return 可用于编辑的HTML片断,若为null，则Decorator不提供该类型的特殊解析
	 */
	public String toEditHtml( String pName, Object bean, HttpServletRequest request );
	
	/**
	 * 将属性转变为可用于搜索的HTML片断(never null)
	 * @param pName 属性名
	 * @param bean JavaBean对象(可以为null)
	 * @param request HTTP请求对象
	 * @return 可用于搜索的HTML片断,若为null，则Decorator不提供该类型的特殊解析
	 */
	public String toSearchHtml( String pName, Object bean, HttpServletRequest request );
	
}
