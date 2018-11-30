package com.zfsoft.fifa.statement;

import java.io.Serializable;
import java.util.Properties;

import com.zfsoft.fifa.business.Business;

/**
 * 语义类,维护数据类的所有语义信息.
 * 
 * <p>
 * 并非所有Statement都会被序列化到外部存储空间.只有基本类的Statement才会被序列化.
 * 多个基本类的Statement可以通过合并的方式完成如主库查询、日志库查询、快照库查询、审核的内容
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public interface Statement extends Serializable {
	
	/**
	 * 获取Statement的标识.用于缓存、查找之用，对应的是Identity类的getIdentity()方法
	 * @return 字符串，如：com.lansle.hrm.entity.Abroad;MAIN
	 */
	public String getGuid();
	
	/**
	 * 资源对应的数据类的名称.
	 * @return 字符串，如：com.lansle.hrm.entity.Abroad
	 */
	public String getBeanName();
	
	/**
	 * 返回页面显示名
	 * @return
	 */
	public String getTitle();
	
	/**
	 * 返回备注信息
	 * @return
	 */
	public String getRemark();

	/**
	 * 获取当前Statement的所有StatementElement
	 * @return 若无子元素返回：new StatementElement[0]
	 */
	public StatementElement[] getElements();
	
	/**
	 * 合并两Statement所有elements
	 * <p>合并不影响原有Statement，必须使用返回值获取合并后的结果</p>
	 * @param statement 待并入的Statement
	 * @return 返回组合后的Statement
	 */
	public Statement combineStatement( Statement statement );
	
	/**
	 * 返回Statement中Display属性为true的属性
	 * <p>
	 * 值为false的属性通常为globalid,staffid等，也可能是废弃的字段
	 * </p>
	 * @return 若无符合条件元素则返回：new StatementElement[0]
	 */
	public StatementElement[] displayElements();
	
	/**
	 * StatementElement查找方法
	 * @param elementId 元素ID
	 * @return elementid对应的元素,若未找到,返回null
	 */
	public StatementElement getElementById(String elementId);
	
	/**
	 * StatementElement查找方法
	 * @param elementName 元素名称
	 * @return elementName对应的元素，若未找到，返回null
	 */
	public StatementElement getElementByName(String elementName);
	
	/**
	 * 最少一个，用于删除时判断
	 */
	public boolean isMoreThanOne();
	
	/**
	 * 最多一个，用于增加时判断
	 * @return
	 */
	public boolean isLessThanOne();
	
	/**
	 * 是否有意义上的主记录.
	 * <p>
	 * 一个staff对应的记录中 特定字段取值特定的记录只能唯一.
	 * 若isRelative()取值为true,并且getElements()返回的elements中有isPramary()为true,
	 * 则在新增/修改操作中维护相关字段的意义完整性.
	 * </p>
	 */
	public boolean isRelative();
	
	/**
	 * 主表记录更新后，是否需要与OVERALL表同步
	 */
	public boolean isSyn();
	
	/**
	 * 是否有日期的时间限制
	 * <p>
	 * 若true,在一个Staff对应的各个记录的标识起始时间和终止时间的不能重叠。
	 * </p>
	 */
	public boolean isDateLimited();
	
	/**
	 * 附加属性，以备不时之需(never null)
	 */
	public Properties getAppendix();
	
	/**
	 * 生成对应的Business元数据,应对数据持久操作
	 * <p>
	 * 此方法有缓冲作用,若business已经生成过,直接返回business
	 * </p>
	 * @return
	 */
	public Business createBusiness();
	
	/**
	 * 强制生成对应的Business元数据,应对数据持久操作
	 * @return
	 */
	public Business createBusinessForce();
}
