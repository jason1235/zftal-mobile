package com.zfsoft.fifa.business;

import java.io.Serializable;
import java.util.Properties;

/**
 * 处理业务逻辑（新增、修改、删除、通过、拒绝等）需要的元数据文件
 * <p>
 * 描述了数据类处理时需要进行的验证等
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public interface Business extends Serializable {
	
	/**
	 * 返回资源对应的数据类的名称
	 * @return 字符串，如：com.lansle.hrm.entity.Abroad
	 */
	public String getBeanName();
	
	/**
	 * 返回资源对应的Log类名称
	 * @return 字符串，如：com.lansle.hrm.entity.AbroadLog
	 */
	public String getLogBeanName();
	
	/**
	 * 返回判断记录是否存在条件，用于判断LOG表中是否已存在对应的待审核记录
	 * 因改变staff_basic结构,现在均返回 globalid == globalid.?
	 */
	public String getPrimaryCondition();
	
	/**
	 * 是否最少一个
	 * <p>用于删除时判断</p>
	 */
	public boolean isMoreThanOne();
	
	/**
	 * 是否最多一个
	 * <p>用于新增是判断</p>
	 */
	public boolean isLessThanOne();
	
	/**
	 * 是否有意义上的主记录
	 * <p>
	 * 一个staff对应的记录中 特定字段取值特定的记录只能唯一.
	 * </p>
	 */
	public boolean isRelative();
	
	/**
	 * 返回标识主记录的特定字段及其对应的值
	 * @return 若没有则返回new String[0]
	 */
	public String[] getRelativeProperties();
	
	/**
	 * 是否同步
	 * <p>
	 * 主表记录更新后，是否需要与OVERALL表同步
	 * </p>
	 */
	public boolean isSyn();
	
	/**
	 * 返回同步的属性名
	 * <p>
	 * 主表记录更新后，需要与OVERALL表同步的属性名称
	 * </p>
	 */
	public SynCondition[] getSynProperties();
	
	/**
	 * 是否有日期的时间限制
	 * <p>
	 * 若true,在一个Staff对应的各个记录的标识起始时间和终止时间的不能重叠
	 * </p>
	 */
	public boolean isDateLimited();

	/**
	 * 附加属性，以备不时之需
	 * @return
	 */
	public Properties getAppendix();

}
