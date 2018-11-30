package com.zfsoft.hrm.baseinfo.infoclass.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.enums.VerifyType;
import com.zfsoft.util.base.StringUtil;

public class VerifyUtil {
	/**
	 * 字段验证
	 * @param property
	 * @param value
	 * @return
	 */
	public static String parse(InfoProperty property, Object value) {
		
		if (!Type.COMMON.equals(property.getFieldType())&&
				!Type.NUMBER.equals(property.getFieldType())){
			return "";
		} 
		
		if(Type.COMMON.equals(property.getFieldType())
				&&property.getVerifyType()==null){
			return "";
		}
		
		if((!property.getNeed())
				&&(value==null||StringUtil.isEmpty(value.toString()))){
			return "";
		}
		
		if(Type.NUMBER.equals(property.getFieldType())
				&&property.getVerifyType()==null){
			property.setVerifyType(VerifyType.POSITIVE_INTEGER);
		}
		
		
		VerifyType verifyType=property.getVerifyType();
		
		if(verifyType==null){
			return "";
		}else{
			if(verifyType==VerifyType.NOMAL){
				return "";
			}
			Pattern pattern = Pattern.compile(verifyType.getFormula());

			Matcher matcher = pattern.matcher(value.toString());

			if(matcher.matches()){
				return "";
			}else{
				return property.getName()+"格式错误，样例："+verifyType.getDemo();
			}

		}
	}
	
	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Boolean bParse(InfoProperty property, Object value) {
		String error=parse(property, value);
		if(StringUtil.isEmpty(error)){
			return true;
		}else{
			throw new RuntimeException(error);
		}
	}
}
