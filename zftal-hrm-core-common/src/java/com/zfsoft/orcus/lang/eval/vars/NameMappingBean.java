package com.zfsoft.orcus.lang.eval.vars;

/**
 * JavaBean属性名和变量名的映射Bean
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public class NameMappingBean implements NameMapping, Cloneable {

	private static final long serialVersionUID = -3924975196143363386L;
	
	private String propertyName;
	
	private String variableName;
	
	/**
	 * （空）构造函数
	 */
	public NameMappingBean() {
		// nothing
	}
	
	/**
	 * 构造函数
	 * @param propertyName JavaBean属性名
	 * @param variableName 变量名
	 */
	public NameMappingBean( String propertyName, String variableName ) {
		this.propertyName = propertyName;
		this.variableName = variableName;
	}
	
	@Override
	public String propertyName() {

		return propertyName;
	}

	@Override
	public String variableName() {

		return variableName;
	}
	
	/**
	 * 返回变量名
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 设置JavaBean属性名
	 * @param propertyName JavaBean属性名
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * 返回变量名
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * 设置变量名
	 * @param variableName 变量名 
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	@Override
	protected Object clone() {
		return new NameMappingBean( getPropertyName(), getVariableName() );
	}

}
