package com.zfsoft.fifa.identity;

import java.io.Serializable;

/**
 * 标记类，标识了页面资源和显示、逻辑处理元数据的映射关系
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public class Identity implements Serializable {
	
	private static final long serialVersionUID = 2713095612601428689L;

	private String pageType;		//页面类型
	
	private String beanName;		//数据类名称

	/**
	 * 返回对应的页面类型
	 * @see PageType
	 */
	public String getPageType() {
		return pageType;
	}

	/**
	 * 返回数据类名称
	 * 如：com.zfsoft.hrm.entities.Abroad
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * 设置数据类名称
	 * @param beanName 数据类名称
	 */
	private void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	/**
	 * 设置页面类型
	 * @param pageType 页面类型
	 */
	private void setPageType(String pageType) {

		if( pageType == null ) {
			throw new RuntimeException("parameter [pageType] is required; it cannot be null.");
		}
		
		if( pageType.equals( PageType.OVERALL ) ) {
			
			this.pageType = PageType.OVERALL;
			
		} else if( pageType.equals( PageType.MAIN ) ) {
			
			this.pageType = PageType.MAIN;
			
		} else if( pageType.equals( PageType.LOG ) ) {
			
			this.pageType = PageType.LOG;
			
		} else if( pageType.equals( PageType.SNAP ) ) {
			
			this.pageType = PageType.SNAP;
			
		} else if( pageType.equals( PageType.AUDIT ) ) {
			
			this.pageType = PageType.AUDIT;
			
		} else if( pageType.equals( PageType.EMPTY ) ) {
			
			this.pageType = PageType.EMPTY;
		
		} else {
			throw new WrongFormatException("Wrong format " + pageType);
		}
	}
	
	/**
	 * 返回Identity的描述信息
	 * @return
	 */
	public String getIdentity() {
		if( pageType.equals( PageType.EMPTY ) ) {
			return this.beanName;
		}
		
		return this.beanName + ";" + this.pageType;
	}
	
	/**
	 * 若obj为String类型的对象，则需要比较该字符串与getIdentity()方法的返回值是否一致
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj == null ) {
			
			return false;

		} else if( obj instanceof String ) {
			
			return this.getIdentity().equals(obj);
			
		} else if( obj instanceof Identity) {
			
			Identity identity = (Identity) obj;
			
			if( identity.getBeanName() == null || identity.getPageType() == null ){
				return false;
			}
			
			if( identity.getBeanName().equals( this.beanName ) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 根据identity字符串创建新的Identity
	 * @param name 字符串格式: "com.lansle.hrm.entities.Abroad;MAIN"
	 * @return
	 */
	public static Identity createIdentity(String name) {
		if( name == null ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		Identity identity = new Identity();

		String[] para = name.split(";");
		
		if( para.length == 1 ) {
			identity.setBeanName( para[0] );
			identity.setPageType( PageType.EMPTY );
		} else if( para.length == 2 ) {
			identity.setBeanName( para[0] );
			identity.setPageType( para[1] );
		} else {
			throw new WrongFormatException( "Wrong format " + name );
		}
		
		return identity;
	}
	
	

}
