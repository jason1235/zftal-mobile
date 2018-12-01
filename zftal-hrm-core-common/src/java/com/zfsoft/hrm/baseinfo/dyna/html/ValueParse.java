package com.zfsoft.hrm.baseinfo.dyna.html;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.Date;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 动态类可视属性解析器,用于值设置类型转换
 * @ClassName: ValueParse 
 * @author jinjj
 * @date 2012-6-28 上午08:48:20 
 *
 */
public class ValueParse {
	/**
	 * 解析
	 * @return 解析后得到的内容
	 */
	public static Object parse(InfoProperty property, Object value) {

		Object result = "";
		
		if( property == null ) {
			return value.toString();
		}
		
		if ( Type.COMMON.equals( property.getFieldType() ) ) {
			result = commonParser(property,value);
		} else if ( Type.LONG_STR.equals( property.getFieldType() ) ) {
			result = longStrParser(property,value);
		} else if ( Type.CODE.equals( property.getFieldType() ) ) {
			result = commonParser(property,value);
		} else if (Type.DATE.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.MONTH.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.YEAR.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if ( Type.IMAGE.equals( property.getFieldType() ) ) {
			result = commonParser(property, value); 
		} else if ( Type.SIGLE_SEL.equals( property.getFieldType() ) ) {
			result = commonParser(property,value);
		} else if (Type.NUMBER.equals(property.getFieldType())){
			result = commonParser(property, value);
		} else {
			result = commonParser(property, value);//默认转换
		}
		
		return result;
	}
	
	/**
	 * 单行文本解析
	 * @param value 值
	 * @param value2 
	 * @return
	 */
	private static String commonParser( InfoProperty property,Object value ) {
		if ( value == null ) {
			value = "";
		}
		
		return value.toString();
	}
	
	/**
	 * 多行文本解析
	 * @param value 值
	 * @param value2 
	 * @return
	 */
	private static String longStrParser(InfoProperty property,Object value  ) {
		  if(value instanceof Clob)
			try {
				value = ClobToString((Clob)value);
			} catch (Exception e) {
				e.printStackTrace();
			}

		return commonParser(property,value);
	}
	
	private static String ClobToString(Clob clob) throws Exception  {
		String reString = "";
		BufferedReader br = null;
		try{
			Reader is = clob.getCharacterStream();// 得到流
			br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
		}catch(Exception e){
			throw new Exception();
		}finally{
			if(br!=null) 
				br.close();
		}
		return reString;
	}


	
	/**
	 * 日期解析
	 * @param value 值
	 * @param format 日期格式，如：yyyy-MM
	 * @return
	 */
	private static Object dateParser(InfoProperty property,Object value ) {
		
		if(value instanceof Date){
			return value;
		}
		
		String time = commonParser(property,value);
		
		if ( "".equals( time ) ) {
			return "";
		}
		
		return TimeUtil.toDate(time);
	}
	
}
