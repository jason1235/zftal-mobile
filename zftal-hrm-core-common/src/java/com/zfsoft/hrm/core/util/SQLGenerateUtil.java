package com.zfsoft.hrm.core.util;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

//import sun.tools.tree.StringExpression;

import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @author jinjj
 * @date 2013-1-5 下午01:45:49 
 *  
 */
public class SQLGenerateUtil {

	public static void appendEquals(StringBuilder sb,String column,String value){
		if(!StringUtils.isEmpty(value)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append(column);
			sb.append(" = '");
			sb.append(StringEscapeUtils.escapeSql(value.trim())+"'");
		}
	}
	
	public static void appendLike(StringBuilder sb,String column,String value){
		if(!StringUtils.isEmpty(value)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append(column);
			sb.append(" like '%");
			sb.append(StringEscapeUtils.escapeSql(value.trim())+"%'");
		}
	}
	
	public static void appendTime(StringBuilder sb,String column,String before,String end){
		if(!StringUtils.isEmpty(before)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append(column);
			sb.append(" >= ");
			sb.append("to_date('"+before.trim()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')");
		}
		
		if(!StringUtils.isEmpty(end)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append(column);
			sb.append(" <= ");
			sb.append("to_date('"+end.trim()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		}
	}
	
	public static void appendTime(StringBuilder sb,String column,Date before,Date end){
		if(before != null){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append(column);
			sb.append(" >= ");
			sb.append("to_date('"+TimeUtil.format(before, TimeUtil.yyyy_MM_dd_HH_mm_ss)+"','yyyy-MM-dd hh24:mi:ss')");
		}
		
		if(end != null){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append(column);
			sb.append(" <= ");
			sb.append("to_date('"+TimeUtil.format(end, TimeUtil.yyyy_MM_dd_HH_mm_ss)+"','yyyy-MM-dd hh24:mi:ss')");
		}
	}
}
