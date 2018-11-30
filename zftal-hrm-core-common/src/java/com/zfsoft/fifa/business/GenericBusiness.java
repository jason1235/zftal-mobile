package com.zfsoft.fifa.business;

import java.util.Properties;

/**
 * {@link Business }的实现类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class GenericBusiness implements Business {

	private static final long serialVersionUID = -8370632439068336136L;

	private String beanName;					//数据类名称，如：com.lansle.hrm.entity.Abroad
	
	private String logBeanName;					//日志类名称，如：com.lansle.hrm.entity.AbroadLog
	
	private boolean moreThanOne;				// 至少一条记录
	
	private boolean lessThanOne;				// 最多一条记录
	
	private boolean relative;					// 是否存在关联的维护数据一致性的操作(某个属性的值比如最高学位，取值为1的每个教职工只能有1个)
	
	private String[] relativeProperties;		// 一维数组。存放标识主记录的字段名
	
	private boolean syn;						// 是否需要同步数据（与OVERALL同步）
	
	private SynCondition[] synProperties;		// 需要同步的字段
	
	private boolean dateLimited;	 			//是否有日期的时间限制，若true,在一个Staff对应的各个记录的标识起始时间和终止时间的不能重叠
	
	private Properties appendix;				//实体的附属数据

	@Override
	public final String getBeanName() {
		return beanName;
	}

	/**
	 * 设置资源对应的数据类的名称
	 * @param beanName 资源对应的数据类的名称
	 */
	public final void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public final String getLogBeanName() {
		return logBeanName;
	}

	/**
	 * 设置资源对应的Log类名称
	 * @param logBeanName 资源对应的Log类名称
	 */
	public final void setLogBeanName(String logBeanName) {
		this.logBeanName = logBeanName;
	}

	@Override
	public boolean isMoreThanOne() {
		return moreThanOne;
	}

	/**
	 * 设置是否最少一个
	 * <p>用于删除时判断</p>
	 * @param moreThanOne 是否最少一个
	 */
	public final void setMoreThanOne(boolean moreThanOne) {
		this.moreThanOne = moreThanOne;
	}

	@Override
	public final boolean isLessThanOne() {
		return lessThanOne;
	}

	/**
	 * 设置是否最多一个
	 * @param lessThanOne 是否最多一个
	 */
	public final void setLessThanOne(boolean lessThanOne) {
		this.lessThanOne = lessThanOne;
	}

	@Override
	public final boolean isRelative() {
		
		return relative;
	}

	/**
	 * 设置是否有意义上的主记录
	 * @param relative 是否有意义上的主记录
	 */
	public final void setRelative(boolean relative) {
		
		this.relative = relative;
	}

	@Override
	public final String[] getRelativeProperties() {
		if( this.relativeProperties == null ) {
			return new String[0];
		}
		
		return relativeProperties;
	}

	/**
	 * 设置标识主记录的字段名数组
	 * @param relativeProperties 标识主记录的字段名数组
	 */
	public final void setRelativeProperties(String[] relativeProperties) {
		
		this.relativeProperties = relativeProperties;
	}

	@Override
	public final boolean isSyn() {
		return syn;
	}

	/**
	 * 设置是否需要同步
	 * @param syn 是否需要同步
	 */
	public final void setSyn(boolean syn) {
		this.syn = syn;
	}

	@Override
	public final SynCondition[] getSynProperties() {
		
		return synProperties;
	}

	/**
	 * 设置需要同步的字段
	 * @param synProperties 需要同步的字段
	 */
	public final void setSynProperties(SynCondition[] synProperties) {
		
		this.synProperties = synProperties;
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

	@Override
	public final Properties getAppendix() {
		return appendix;
	}

	/**
	 * 设置附属数据
	 * @param appendix 附属数据
	 */
	public final void setAppendix(Properties appendix) {
		this.appendix = appendix;
	}

	@Override
	public final String getPrimaryCondition() {
		return "globalid == globalid.?";
	}
	
}
