package com.zfsoft.hrm.summary.roster.util;

import org.apache.commons.lang.StringEscapeUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.util.base.StringUtil;

/** 
 * 条件解析工具，用于花名册动态条件拼接
 * @author jinjj
 * @date 2012-9-7 下午01:39:51 
 *  
 */
public class ConditionParseUtil {
	
	private static final String ACCURATE = "1";
	private static final String LIKE = "2";
	private static final String RANGE ="3";

	public static String parse(RosterConfig config,String[] values){
		String result = "";
		String type = config.getInfoProperty().getTypeInfo().getName();

		if (Type.COMMON.equals(type)) {
			result = commonParser(config,values);
		} else if (Type.LONG_STR.equals(type)) {
			result = commonParser(config,values);
		} else if (Type.CODE.equals(type)) {
			result = codeParser(config,values);
		} else if (Type.DATE.equals(type)) {
			result = dateParser(config,values);
		} else if (Type.MONTH.equals(type)) {
			result = dateParser(config,values);
		} else if (Type.YEAR.equals(type)) {
			result = dateParser(config,values);
		} else if (Type.IMAGE.equals(type)) {
			//result = imageParser(property);
			throw new RuleException("无法支持图片类型字段的查询");
		} else if (Type.SIGLE_SEL.equals(type)) {
			//result = sigleParser(property);
			throw new RuleException("无法支持单选类型字段的查询");
		} else if (Type.NUMBER.equals(type)){
				result = numParser(config,values);
		} else {
			result = commonParser(config,values);//默认转换
		}

		return result;
	}
	
	private static String codeParser(RosterConfig config,String[] values){
		if(values == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if(values.length>1){
			sb.append("(");
		}
		for(String str : values){
			if(sb.length()>1){
				sb.append(" or ");
			}
			if(config.getQueryType().equals(LIKE)){
				sb.append(" ");
				sb.append(getColumnName(config));
				sb.append(" like '");
				sb.append(StringEscapeUtils.escapeSql(str));
				sb.append("%'");
			}else{
				sb.append(" ");
				sb.append(getColumnName(config));
				sb.append(" = '");
				sb.append(StringEscapeUtils.escapeSql(str));
				sb.append("'");
			}
		}
		if(values.length>1){
			sb.append(")");
		}
		return sb.toString();
	}
	
	private static String commonParser(RosterConfig config,String[] values){
		if(values == null){
			return null;
		}
		if(StringUtil.isBlank(values[0])){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if(config.getQueryType().equals(LIKE)){
			sb.append(" ");
			sb.append(getColumnName(config));
			sb.append(" like '%");
			sb.append(StringEscapeUtils.escapeSql(values[0]));
			sb.append("%'");
		}else{
			sb.append(" ");
			sb.append(getColumnName(config));
			sb.append(" = '");
			sb.append(StringEscapeUtils.escapeSql(values[0]));
			sb.append("'");
		}
		return sb.toString();
	}
	
	private static String dateParser(RosterConfig config,String[] values){
		if(values == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if(!StringUtil.isEmpty(values[0])){
			sb.append(" ");
			sb.append(getColumnName(config));
			sb.append(" >= ");
			sb.append("to_date('"+values[0]+"','"+config.getInfoProperty().getTypeInfo().getFormat()+"')");
		}
		if(!StringUtil.isEmpty(values[1])){
			if(sb.length()>0){
				sb.append(" and");
			}
			sb.append(" ");
			sb.append(getColumnName(config));
			sb.append(" <= ");
			sb.append("to_date('"+values[1]+"','"+config.getInfoProperty().getTypeInfo().getFormat()+"')");
		}
		
		return sb.toString();
	}
	
	private static String getColumnName(RosterConfig config){
		StringBuilder sb = new StringBuilder();
		InfoClass clazz = InfoClassCache.getInfoClass(config.getClassid());
		InfoProperty p = config.getInfoProperty();
		if(p.getVirtual()){
			sb.append(p.getDisplayFormula(clazz.getIdentityName()));
		}else{
			sb.append(clazz.getIdentityName()+".");
			sb.append(p.getFieldName());
		}
		return sb.toString();
	}
	
	private static String numParser(RosterConfig config,String[] values){
		if(!config.getQueryType().equals(RANGE)){
			throw new RuleException("无法支持的数字类型查询方式");
		}
		if(values == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if(!StringUtil.isEmpty(values[0])){
			sb.append(" ");
			sb.append(getColumnName(config));
			sb.append(" >= ");
			sb.append("'"+values[0]+"'");
		}
		if(!StringUtil.isEmpty(values[1])){
			if(sb.length()>0){
				sb.append(" and");
			}
			sb.append(" ");
			sb.append(getColumnName(config));
			sb.append(" <= ");
			sb.append("'"+values[1]+"'");
		}
		
		return sb.toString();
	}
}
