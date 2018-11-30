package com.zfsoft.fifa.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.zfsoft.fifa.business.Business;
import com.zfsoft.fifa.business.GenericBusiness;
import com.zfsoft.fifa.business.SynCondition;
import com.zfsoft.orcus.lang.ArrayUtil;

/**
 * {@link Statement }的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class GenericStatement implements Statement {
	
	private static final long serialVersionUID = 5664016931846449155L;

	private String guid;			//Statement的标识.用于缓存、查找之用，对应的是Identity类的getIdentity()方法
	
	private String beanName;		//资源对应的数据类的名称
	
	private String title;			//页面显示名
	
	private String remark;			//备注信息
	
	private StatementElement[] elements = new StatementElement[0];			//Statement的所有StatementElement
	
	private Properties appendix;	//附加属性
	
	private StatementElement[] displayElements = new StatementElement[0];	//Statement中Display属性为true的属性
	
	private boolean moreThanOne;	//是否最多一条记录
	
	private boolean lessThanOne;	//是否最少一条记录
	
	private boolean relative;		//是否有意义上的主记录
	
	private boolean syn;			//主表记录更新后，是否需要与OVERALL表同步
	
	private boolean dateLimited;	//是否有日期的时间限制
	
	private Business business;
	
	@Override
	public Statement combineStatement(Statement statement) {
		GenericStatement newStatement = new GenericStatement();
		newStatement.setGuid( this.guid );
		newStatement.setTitle( this.title );
		
		StatementElement[] newElements = (StatementElement[]) ArrayUtil.addElement(
				this.elements, statement.getElements(), StatementElement.class );
		
		newStatement.setElements( newElements );
		
		return newStatement;
	}

	@Override
	public StatementElement getElementById(String elementId) {
		StatementElement result = null;
		
		for ( StatementElement element : elements ) {
			if( element.getGuid().equals(elementId) ) {
				result = element;
				break;
			}
		}

		return result;
	}
	
	@Override
	public StatementElement getElementByName(String elementName) {
		StatementElement result = null;
		
		for ( StatementElement element : elements ) {
			if( element.getGuid().equals(elementName) ) {
				result = element;
				break;
			}
		}

		return result;
	}
	
	/**
	 * 增加属性
	 * @param element 属性
	 */
	public void addElement( StatementElement element ) {
		this.elements = (StatementElement[]) ArrayUtil.addElement( 
				this.elements, element, StatementElement.class );
		
		displayElements = initDisplay();
	}
	
	@Override
	public String getGuid() {
		
		return guid;
	}

	/**
	 * 设置Statement的标识
	 * <p>
	 * 用于缓存、查找之用，对应的是Identity类的getIdentity()方法
	 * </p>
	 * @param guid Statement的标识，如：com.lansle.hrm.entity.Abroad;MAIN
	 */
	public void setGuid(String guid) {
		
		this.guid = guid;
	}

	@Override
	public String getBeanName() {
		
		return beanName;
	}

	/**
	 * 设置资源对应的数据类的名称
	 * @param beanName 资源对应的数据类的名称,如：com.lansle.hrm.entity.Abroad
	 */
	public void setBeanName(String beanName) {
		
		this.beanName = beanName;
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * 设置页面显示名
	 * @param title 页面显示名
	 */
	public void setTitle(String title) {
		
		this.title = title;
	}

	@Override
	public String getRemark() {
		
		return remark;
	}

	/**
	 * 设置备注信息
	 * @param remark 备注信息
	 */
	public void setRemark(String remark) {
		
		this.remark = remark;
	}

	@Override
	public StatementElement[] getElements() {
		
		return elements;
	}

	/**
	 * 设置当前Statement的所有StatementElement
	 * @param elements 当前Statement的所有StatementElement
	 */
	public void setElements( StatementElement[] elements) {
		this.elements = elements;
		displayElements = initDisplay();
	}

	@Override
	public Properties getAppendix() {
		if( this.appendix == null ) {
			appendix = new Properties();
		}
		
		return appendix;
	}

	/**
	 * 设置附加属性
	 * @param appendix 附加属性
	 */
	public void setAppendix(Properties appendix) {
		this.appendix = appendix;
	}

	@Override
	public StatementElement[] displayElements() {
		if( displayElements == null || displayElements.length == 0 ) {
			displayElements = initDisplay();
		}
		
		return displayElements;
	}

	@Override
	public boolean isMoreThanOne() {
		return moreThanOne;
	}

	/**
	 * 设置最少一个
	 * @param moreThanOne 最少一个
	 */
	public void setMoreThanOne(boolean moreThanOne) {
		this.moreThanOne = moreThanOne;
	}

	@Override
	public boolean isLessThanOne() {
		return lessThanOne;
	}

	/**
	 * 设置最少一个
	 * @param lessThanOne 最少一个
	 */
	public void setLessThanOne(boolean lessThanOne) {
		this.lessThanOne = lessThanOne;
	}

	@Override
	public boolean isRelative() {
		return relative;
	}

	/**
	 * 设置是否有意义上的主记录
	 * @param relative 是否有意义上的主记录
	 */
	public void setRelative(boolean relative) {
		this.relative = relative;
	}

	@Override
	public boolean isSyn() {
		return syn;
	}

	/**
	 * 设置主表记录更新后，是否需要与OVERALL表同步
	 * @param syn 主表记录更新后，是否需要与OVERALL表同步
	 */
	public void setSyn(boolean syn) {
		this.syn = syn;
	}

	@Override
	public boolean isDateLimited() {
		return dateLimited;
	}

	/**
	 * 设置是否有日期的时间限制 
	 * @param dateLimited 是否有日期的时间限制 
	 */
	public void setDateLimited(boolean dateLimited) {
		this.dateLimited = dateLimited;
	}
	
	/**
	 * 初始化Statement中Display属性为true的属
	 */
	private StatementElement[] initDisplay() {
		List<StatementElement> elements = new ArrayList<StatementElement>(0);
		
		for ( StatementElement element : this.elements ) {
			if( element.isDisplay() ) {
				elements.add(element);
			}
		}
		
		return elements.toArray( new StatementElement[0]);
	}

	@Override
	public Business createBusiness() {
		if( business == null ) {
			business = createBusinessForce();
		}
		
		return business;
	}

	@Override
	public Business createBusinessForce() {
		GenericBusiness genericBusiness = new GenericBusiness();
		
		genericBusiness.setBeanName( getBeanName() );
		genericBusiness.setLogBeanName( getBeanName() + "Log" );
		genericBusiness.setLessThanOne( isLessThanOne() );
		genericBusiness.setMoreThanOne( isMoreThanOne() );
		genericBusiness.setDateLimited( isDateLimited() );
		genericBusiness.setAppendix( getAppendix() );
		
		String[] relativeProperties = new String[0];
		
		for ( StatementElement element : elements ) {
			if( element.isPrimary() ) {
				relativeProperties = ( String[] )ArrayUtil.addElement(
						relativeProperties, element, String.class );
			}
		}
		
		genericBusiness.setRelative( relativeProperties.length > 0 );
		genericBusiness.setRelativeProperties( relativeProperties );
		
		SynCondition[] synProperties = new SynCondition[0];
		
		for ( StatementElement element : elements ) {
			if( element.isSyn() ) {
				SynCondition con = null;
				
				for (SynCondition property : synProperties) {
					String synConditionProperty = element.getSynCondition();
					
					if( synConditionProperty == null ) {
						synConditionProperty = "";
					} else if( synConditionProperty.equals( property.getCondition() ) ) {
						con = property;
						break;
					}
				}
				
				if( con != null ) {
					String[][] old = con.getProperties();
					String[][] now = new String[old.length][2];
					
					for (int i = 0; i < old.length; i++) {
						now[i][0] = old[i][0];
						now[i][1] = old[i][1];
					}
					
					now[old.length][0] = element.getName();
					now[old.length][1] = element.getOverallPropertyName();
					
					con.setProperties(now);
				} else {
					con = new SynCondition();
					con.setCondition( element.getSynCondition() );
					String[][] properties = new String[1][2];
					properties[0][0] = element.getName();
					properties[0][1] = element.getOverallPropertyName();
					con.setProperties(properties);
					
					synProperties = (SynCondition[]) ArrayUtil.addElement(
							synProperties, element, SynCondition.class );
				}
			}
		}
		
		genericBusiness.setSynProperties( synProperties );
		genericBusiness.setSyn( synProperties.length > 0 );
		
		return genericBusiness;
	}

}
