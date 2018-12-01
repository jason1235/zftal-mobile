package com.zfsoft.orcus.cache;


import javax.servlet.http.HttpServletRequest;

/**
 * 用于数据集合的数据模型
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-12
 * @version V1.0.0
 */
public interface DataModel {
	
	/**
	 * 返回所有可以用于显示的属性名，never null
	 * @return
	 */
	public String[] viewable();
	
	/**
	 * 将所有的属性值转变为可用于显示的文本 
	 * @param pName 属性名
	 * @param bean JavaBean对象
	 * @param index JavaBean对象在数组中的索引
	 * @return
	 */
	public String toViewText(String pName, Object bean, int index);
	
	/**
	 * 将属性的属性值转变为可用于显示的HTML片断
	 * @param pName 属性名
	 * @param bean JavaBean对象
	 * @param index JavaBean对象在数组中的索引
	 * @param request HTTP请求对象
	 * @return 可用于显示的HTML片断
	 */
	public String toViewHtml(String pName, Object bean, int index, HttpServletRequest request);
	
	/**
	 * 返回所有可以用于编辑的属性名，never null
	 * @return
	 */
	public String[] editable();

	/**
	 * 将属性转变为可用于编辑的HTML片断，never null
	 * @param pName 属性名
	 * @param bean JavaBean对象（可以为null）
	 * @param request HTTP请求对象 
	 * @return 可用于编辑的HTML片断
	 */
	public String toEditHtml(String pName, Object bean, HttpServletRequest request);
	
	/**
	 * 将属性转变为可用于编辑验证的JavaScript片断，never null
	 * @param pName 属性名
	 * @param request HTTP请求
	 * @return 可以用于编辑验证的JavaScript片段
	 */
	public String toEditValidateScript( String pName, HttpServletRequest request );
	
	/**
	 * 返回所有可以用于搜索的属性的属性名，never null
	 * @return
	 */
	public String[] searchable();
	
	/**
	 * 返回所有可以用于搜索的属性的属性名，never null
	 * @param beanName JavaBean类名
	 * @return
	 */
	public String[] searchable(String beanName);
	
	/**
	 * 将属性转变为可用于搜索的HTML片断(never null)
	 * @param pName 属性名
	 * @param bean JavaBean对象(可以为null)
	 * @return 可用于搜索的HTML片断
	 */
	public String toSearchHtml(String pName, Object bean, HttpServletRequest request);
	
	/**
	 * 返回属性的可用于显示的标题
	 * @param pName 属性名
	 * @return 标题
	 */
	public String titleOf(String pName);
	
	/**
	 * 返回属性的可用于显示的标题
	 * @param pName 属性名
	 * @param beanName
	 * @return 标题
	 */
	public String titleOf(String pName, String beanName);
	
	/**
	 * 返回属性是否是已废弃的属性
	 * @param pName 属性名
	 * @return true表示已废弃，false表示未废弃
	 */
	public boolean isDeprecated( String pName );
}
