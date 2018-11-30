package com.zfsoft.fifa.statement;

import java.io.Serializable;
import java.util.Properties;

/**
 * 数据类中属性的语义解释
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface StatementElement extends Serializable {
	
	public static final int LEVEL_NOT_EFFECT = -1;
	
	/**
	 * 属性标识
	 */
	public String getGuid();
	
	/**
	 * 名字，为属性名
	 */
	public String getName();
	
	/**
	 * 属性语义类型，如：普通字段、长字段等等
	 * @return
	 */
	public String getType();
	
	/**
	 * 显示名
	 */
	public String getTitle();
	
	/**
	 * 备注内容，属性解释，方便输入提示，理解数据内容
	 */
	public String getRemark();
	
	/**
	 * 代码表名称（为代码类型和虚拟字段类型保留）
	 */
	public String getCodeTable();
	
	/**
	 * 默认值
	 */
	public String getDefValue();
	
	/**
	 * 长度，（字符串长度）
	 * @return
	 */
	public int getLength();
	
	/**
	 * 小数位数（为Double型使用）
	 * @return
	 */
	public int getDigits();
	
	/**
	 * 是否必填（新增、修改时使用）
	 */
	public boolean isNeed();
	
	/**
	 * 是否可以新增（若fasle，新增页面不显示）
	 */
	public boolean isAddable();
	
	/**
	 * 是否可以修改（若false，修改页面不显示）
	 */
	public boolean isEditable();
	
	/**
	 * 是否显示，属于默认设置，若对应的页面没有Layout或者Layout有误，使用此默认设置
	 */
	public boolean isDisplay();
	
	/**
	 * 修饰器名称
	 */
	public String getDecoratorName();
	
	/**
	 * 附件属性，以备不时之需（never null）
	 */
	public Properties getAppendix();
	
	//数据持久需要的信息
	
	/**
	 * 是否为主记录标识.
	 * 主记录定义为一个staffid对应的记录中此属性取值为"1"的记录数不超过1个.
	 * 主记录的取值通常为"0"和"1",页面显示"否"和"是"
	 */
	public boolean isPrimary();
	
	/**
	 * 主表记录变化后，是否需要与OVERALL表同步
	 * @return
	 */
	public boolean isSyn();
	
	/**
	 * 与OVERALL表同步的的条件(在何种情况下同步本属性).
	 * 若为null或空字符串,则将该staffid对应的第一条记录同步,
	 * 若取值为其它字段名称,则获取该字段取值为"1"的记录进行同步.
	 * 若取值不是以上两种情况,视为无效,不在同步字段之列.
	 */
	public String getSynCondition();
	
	/**
	 * 字段与OVERALL表中对应记录的哪一个字段进行同步
	 * @return
	 */
	public String getOverallPropertyName();
}
