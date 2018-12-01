package com.zfsoft.fifa.statement;

import java.util.Properties;

/**
 * {@link StatementElement }的实现类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class GenericElement implements StatementElement {

	private static final long serialVersionUID = -1108113448216675935L;
	
	private String guid;				//标识
	
	private String name;				//名字
	
	private String title;				//显示标题
	
	private String remark;				//备注内容
	
	private String type;				//Element类型
	
	private String defValue;			//默认值
	
	private String codeTable;			//代码表名称
	
	private int length;					//字符串长度
	
	private int digits;					//小数位数
	
	private boolean need;				//是否必填
	
	private boolean addable;			//是否可新增
	
	private boolean editable;			//是否可编辑
	
	private boolean display;			//是否显示

	private String decoratorName;		//修饰器类名
	
	private Properties appendix;		//实体的附属数据
	
	private boolean primary;			//是否为判断主记录的属性
	
	private boolean syn;				//是否需要与OVERALL同步
	
	private String synCondition;		//筛选与OVERALL同步的记录的条件
	
	private String overallPropertyName;	//与OVERALL对应的那个字段进行同步

	@Override
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置属性标识
	 * @param guid  属性标识
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * 设置名字，为属性名
	 * @param name 名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * 设置显示名
	 * @param title 显示名
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注内容
	 * @param remark 备注内容
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getType() {
		return type;
	}

	/**
	 * 设置属性语义类型
	 * @param type 属性语义类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getDefValue() {
		return defValue;
	}

	/**
	 * 设置默认值
	 * @param defValue 默认值
	 */
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	
	@Override
	public String getCodeTable() {
		return codeTable;
	}

	/**
	 * 设置代码表名称
	 * @param codeTable 代码表名称
	 */
	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	@Override
	public int getLength() {
		return length;
	}

	/**
	 * 设置字符串长度
	 * @param length 字符串长度
	 */
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int getDigits() {
		return digits;
	}

	/**
	 * 设置小数位数
	 * @param digits 小数位数
	 */
	public void setDigits(int digits) {
		this.digits = digits;
	}

	@Override
	public boolean isNeed() {
		return need;
	}

	/**
	 * 设置是否必填
	 * @param need 是否必填
	 */
	public void setNeed(boolean need) {
		this.need = need;
	}

	@Override
	public boolean isAddable() {
		return addable;
	}

	/**
	 * 设置是否可以新增
	 * @param addable 是否可以新增
	 */
	public void setAddable(boolean addable) {
		this.addable = addable;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	/**
	 * 设置是否可以修改
	 * @param editable 是否可以修改
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public boolean isDisplay() {
		return display;
	}

	/**
	 * 设置是否显示
	 * @param display 是否显示
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}

	@Override
	public String getDecoratorName() {
		return decoratorName;
	}

	/**
	 * 设置修饰器名称
	 * @param decoratorName 修饰器名称
	 */
	public void setDecoratorName(String decoratorName) {
		this.decoratorName = decoratorName;
	}

	@Override
	public Properties getAppendix() {
		return appendix;
	}

	/**
	 * 设置附件属性
	 * @param appendix 附件属性
	 */
	public void setAppendix(Properties appendix) {
		this.appendix = appendix;
	}

	@Override
	public boolean isPrimary() {
		return primary;
	}

	/**
	 * 设置
	 * @param primary 
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	@Override
	public boolean isSyn() {
		return syn;
	}

	/**
	 * 设置
	 * @param syn 
	 */
	public void setSyn(boolean syn) {
		this.syn = syn;
	}

	@Override
	public String getSynCondition() {
		return synCondition;
	}

	/**
	 * 设置
	 * @param synCondition 
	 */
	public void setSynCondition(String synCondition) {
		this.synCondition = synCondition;
	}

	@Override
	public String getOverallPropertyName() {
		return overallPropertyName;
	}

	/**
	 * 设置
	 * @param overallPropertyName 
	 */
	public void setOverallPropertyName(String overallPropertyName) {
		this.overallPropertyName = overallPropertyName;
	}


}
