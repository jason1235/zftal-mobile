package com.zfsoft.hrm.baseinfo.infoclass.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.enums.VerifyType;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoPropertyType;

/**
 * 信息类属性实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoProperty implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 4375916271221225507L;
	
	private String guid;				//属性ID（唯一主键）

	private String name;				//属性名称（中文名称）
	
	private String description;			//中文描述
	
	private String fieldName;			//字段名称
	
	private String fieldType;			//字段类型（日期、数值、字符串、图片、文件等）

	private Boolean viewable = true;	//可显示
	
	private Boolean editable = true;	//可编辑
	
	private Boolean sourceInit = false;	//是否初始化字段
	
	private Boolean need = false;		//必填
	
	private String defaultValue = "";	//默认值
	
	private String displayFormula = "";	//显示表达式

	private Integer fieldLen = 32;		//字段长度
	
	private Integer digits = 0;			//小数位数

	private String codeId = "";			//代码编目ID
	
	private Boolean unique = false;		//唯一性标识
	
	private String syncToField = "";	//需要同步的字段
	
	private String syncCondition = "";	//同步条件
	
	private Boolean virtual = false;	//虚拟字段
	
	private String classId;
	
	private Integer index;				//显示顺序
	
	private VerifyType verifyType;			//验证字串
	
	private String refer="";
	
	private String referFunc="";
	
	private Integer width=200;				//宽
	
	private Integer height=30;				//高
	
	private Integer size=5;               //文件大小，默认5k,或5M
	
	private String classFiledName;
	
	private String viewStyle="";
	
	private String fileType;
	
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public VerifyType getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(VerifyType verifyType) {
		this.verifyType = verifyType;
	}

	/**
	 * (空) 构造函数
	 */
	public InfoProperty() {
	}
	
	/**
	 * 构造函数
	 * @param name 属性名称（中文名称）
	 * @param fieldName 字段名称
	 * @param fieldType 字段类型
	 * @param editable 是否可编辑
	 * @param viewable 是否可显示
	 */
	public InfoProperty(String name, String fieldName, String fieldType, boolean editable, boolean viewable) {
		this.name = name;
		this.description = name;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.editable = editable;
		this.viewable = viewable;
		dealVerify();
	}
	
	/**
	 * 返回字段类型信息
	 * @return
	 */
	public InfoPropertyType getTypeInfo() {
		
		return (InfoPropertyType) TypeFactory.getType( InfoPropertyType.class, fieldType );
	}

	/**
	 * 返回属性ID（唯一主键）
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置属性ID（唯一主键）
	 * @param guid 属性ID（唯一主键）
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 返回属性名称（中文名称）
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置属性名称（中文名称）
	 * @param name 属性名称（中文名称）
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 设置所属信息类
	 * @param class_ 所属信息类
	 */
	public void setClasz(InfoClass clasz) {
		this.classId = clasz.getGuid();
	}
	
	/**
	 * 返回代码编目ID
	 */
	public String getCodeId() {
		return codeId;
	}

	/**
	 * 设置代码编目ID
	 * @param codeId 代码编目ID
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	/**
	 * 返回中文描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置中文描述
	 * @param description 中文描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回显示表达式
	 */
	public String getDisplayFormula() {
		if(!StringUtils.isEmpty(referFunc)&&!StringUtils.isEmpty(refer)){
			displayFormula=referFunc+"("+refer+")";
			return displayFormula;
		}
		return "";
	}
	
	/**
	 * 根据指定的表别名返回显示表达式
	 */
	public String getDisplayFormula(String tableName) {
		if(!StringUtils.isEmpty(referFunc)&&!StringUtils.isEmpty(refer)){
			String propertyNameStyle = "[a-zA-Z][a-zA-Z0-9_]*";
			Pattern p = Pattern.compile(propertyNameStyle);
			Matcher m = p.matcher(refer);
			List<String> fields = new ArrayList<String>();
			while (m.find()) {
				fields.add(m.group());
			}
			String str = refer.replaceAll(propertyNameStyle,"#value");
			for (String string : fields) {
				str=str.replaceFirst("#value", tableName+"."+string);
			}
			return referFunc+"("+str+") ";
		}
		return "";
	}

	/**
	 * 设置显示表达式
	 * @param displayFormula 显示表达式
	 */
	public void setDisplayFormula(String displayFormula) {
		this.displayFormula = displayFormula;
		if(!StringUtils.isEmpty(displayFormula)){
			this.referFunc = StringUtils.substringBefore(displayFormula, "(");
			this.refer = StringUtils.substringBeforeLast(StringUtils.substringAfter(displayFormula, "("),")");
		}
	}

	/**
	 * 返回字段名称
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置字段名称
	 * @param fieldName 字段名称
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 返回字段类型（日期、数值、字符串、图片、文件等）
	 */
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * 设置字段类型（日期、数值、字符串、图片、文件等）
	 * @param fieldType 字段类型（日期、数值、字符串、图片、文件等）
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
		dealVerify();
	}

	private void dealVerify() {
		if(!Type.COMMON.equals(this.fieldType)&&
				!Type.NUMBER.equals(this.fieldType)){
			this.verifyType=null;
		}
	}
	/**
	 * 返回字段长度
	 */
	public Integer getFieldLen() {
		return fieldLen;
	}

	/**
	 * 设置字段长度
	 * @param fieldLen 字段长度
	 */
	public void setFieldLen(Integer fieldLen) {
		this.fieldLen = fieldLen;
	}

	/**
	 * 返回小数位数
	 */
	public Integer getDigits() {
		return digits;
	}

	/**
	 * 设置小数位数
	 * @param digits 小数位数
	 */
	public void setDigits(Integer digits) {
		if( digits == null ) {
			digits = 0;
		}
		
		this.digits = digits;
	}

	/**
	 * 返回默认值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 设置默认值
	 * @param defaultValue 默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 返回是否为虚拟字段
	 */
	public Boolean getVirtual() {
		return virtual;
	}

	/**
	 * 设置是否为虚拟字段
	 * @param virtual 是否为虚拟字段
	 */
	public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}

	/**
	 * 返回需要同步的字段
	 */
	public String getSyncToField() {
		return syncToField;
	}

	/**
	 * 设置需要同步的字段
	 * @param syncToField 需要同步的字段
	 */
	public void setSyncToField(String syncToField) {
		this.syncToField = syncToField;
	}

	/**
	 * 返回同步条件
	 */
	public String getSyncCondition() {
		return syncCondition;
	}

	/**
	 * 设置同步条件
	 * @param syncCondition 同步条件
	 */
	public void setSyncCondition(String syncCondition) {
		this.syncCondition = syncCondition;
	}

	/**
	 * 返回显示顺序
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * 设置显示顺序
	 * @param index 显示顺序
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * 返回是否可编辑
	 */
	public Boolean getEditable() {
		if(Type.CREATOR.equals(getFieldType())){
				return false;
		}
		return editable;
	}

	/**
	 * 设置是否可编辑
	 * @param editable 是否可编辑
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	/**
	 * 返回是否可现实
	 */
	public Boolean getViewable() {
		return viewable;
	}

	/**
	 * @return 文件大小
	 */
	public Integer getSize() {
		return size;
	}
	/**
	 * @param 文件大小 设置
	 */
	public void setSize(Integer size) {
		this.size = size;
	}
	/**
	 * 设置是否可现实
	 * @param viewablt 是否可现实
	 */
	public void setViewable(Boolean viewable) {
		this.viewable = viewable;
	}

	/**
	 * 返回必填
	 */
	public Boolean getNeed() {
		return need;
	}

	/**
	 * 设置必填
	 * @param need 必填
	 */
	public void setNeed(Boolean need) {
		this.need = need;
	}

	/**
	 * 返回唯一性标识
	 */
	public Boolean getUnique() {
		return unique;
	}

	/**
	 * 设置唯一性标识
	 * @param unique 唯一性标识
	 */
	public void setUnique(Boolean unique) {
		this.unique = unique;
	}
	
	@Override
	public InfoProperty clone() {
		try {
			return (InfoProperty)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public String getReferFunc() {
		return referFunc;
	}

	public void setReferFunc(String referFunc) {
		this.referFunc = referFunc;
	}
	/**
	 * 信息类批量管理时用
	 * @return
	 */
	public String getClassFiledName(){
		if(InfoClassCache.getInfoClass(classId)==null){
			return "";
		}
		String classFiledName=InfoClassCache.getInfoClass(classId).getIdentityName()+"."+this.getFieldName();
		if("dqztm".equals(this.getFieldName())){
			classFiledName=InfoClassCache.getOverallInfoClass().getIdentityName()+"."+this.getFieldName();
		}
		return classFiledName.toLowerCase();
	}
	
	public void setClassFiledName(String classFiledName) {
		this.classFiledName = classFiledName;
	}
	/**
	 * EditParse时使用 暂时只支持代码选择
	 */
	public String getViewStyle() {
		return viewStyle;
	}
	
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}
	
	public Boolean getSourceInit() {
		return sourceInit;
	}
	public void setSourceInit(Boolean sourceInit) {
		this.sourceInit = sourceInit;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(obj==this){
			return true;
		}else if(obj instanceof InfoProperty){
			if(this.guid==null){
				return false;
			}
			return this.guid.equals(((InfoProperty) obj).guid);
		}else{
			return false;
		}
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileType() {
		return fileType;
	}
	
	


}
