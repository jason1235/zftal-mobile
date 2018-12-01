package com.zfsoft.hrm.baseinfo.dyna.html;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.SearchCondition;
import com.zfsoft.orcus.lang.StringUtil;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-14
 * @version V1.0.0
 */
public class SearchParse {
	
	/**
	 * 解析
	 * @param property
	 * @param condition
	 * @param value
	 * @return
	 */
	public static String parse(InfoProperty property, SearchCondition condition,  Object value) {
		String result = "";
		
		if( property == null ) {
			return String.valueOf( value );
		}
		
		String fieldType = property.getFieldType();
		
		if( Type.COMMON.equals( fieldType ) 
				|| Type.LONG_STR.equals( fieldType )
				|| Type.NUMBER.equals( fieldType ) ) {
			 
			result = textParse( property, condition, value );
			
		} else if( Type.CODE.equals( fieldType ) ) {
			
			result = codeParse( property, condition, value );
			
		} else if( Type.DATE.equals( fieldType )
				|| Type.MONTH.equals( fieldType )
				|| Type.YEAR.equals( fieldType ) ) {
			
			result = dateParse( property, condition, value );
			
		} else if( Type.SIGLE_SEL.equals( fieldType ) ) {
			
			result = sigleParse( property, condition, value );
		}
		
		return result;
	}
	
	/**
	 * 单行文本解析
	 * @param property
	 * @param condition
	 * @param value
	 * @return
	 */
	private static String textParse( InfoProperty property, SearchCondition condition, Object value ) {
		if( value == null ) {
			value = "";
		}
		
		String pName = condition == null ? property.getFieldName() : condition.getName();
		
		String out = "<input type=\"text\" id=\"${pName}\" name=\"values.${pName}\" value=\"${value}\" />";
		
		out = StringUtil.replace( out, "${pName}", pName );
		out = StringUtil.replace( out, "${value}", String.valueOf(value) );
		
		return out;
	}
	
	/**
	 * 代码控件解析
	 * @param property
	 * @param condition
	 * @param value
	 * @return
	 */
	private static String codeParse( InfoProperty property, SearchCondition condition, Object value ) {
		if(StringUtils.isEmpty(property.getCodeId())){
			return "";
		}
		
		String pName = condition == null ? property.getFieldName() : condition.getName();
		String itemValue = "";
		
		if( value == null ) {
			String defValue = condition.getDefaultValue();
			value = defValue == null ? "" : defValue;
		}
		
		itemValue = CodeUtil.getItemValue(property.getCodeId(), String.valueOf(value));
		
		
		
		String out = "<input type=\"hidden\" id=\"${pName}\" name=\"values.${pName}\" value=\"${value}\" />";
		out += "<input type=\"text\" id=\"${pName}_info\" class=\"text_sel\" value=\"${itemValue}\" readonly=\"readonly\" onclick=\"codePicker(this, '${codeTable}');\" />";
		
		out = StringUtil.replace( out, "${pName}", pName );
		out = StringUtil.replace( out, "${value}", String.valueOf(value) );
		out = StringUtil.replace( out, "${itemValue}", itemValue == null ? "" : itemValue );
		out = StringUtil.replace( out, "${codeTable}", property.getCodeId() );
		
		return out;
	}
	
	/**
	 * 日期控件解析
	 * @param property
	 * @param condition
	 * @param value
	 * @return
	 */
	private static String dateParse( InfoProperty property, SearchCondition condition, Object value ) {
		if( value == null ) {
			value = "";
		}
		String format = property.getTypeInfo().getFormat();
		
		if( value instanceof Date ) {
			value = TimeUtil.format( (Date)value, format );
		}
		
		String pName = condition == null ? property.getFieldName() : condition.getName();
		
		String out = "<input id=\"${pName}\" name=\"values.${pName}\" class=\"Wdate\" value=\"${value}\""
				+ " onclick=\"WdatePicker({dateFmt:'${format}'})\" />";
		
		out = StringUtil.replace( out, "${pName}", pName );
		out = StringUtil.replace( out, "${value}", String.valueOf(value) );
		out = StringUtil.replace( out, "${format}",format );
		
		return out;
	}

	/**
	 * 单选解析
	 * @param property
	 * @param condition
	 * @param value
	 * @return
	 */
	private static String sigleParse( InfoProperty property, SearchCondition condition, Object value ) {
		
		return null;
	}

}
