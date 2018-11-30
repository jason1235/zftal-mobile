package com.zfsoft.hrm.summary.roster.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2013-1-24 下午03:32:03 
 *  
 */
public class RosterValidUtil {

	private List<RosterConfig> configList;
	private Map<String,String[]> paramMap;
	
	public void validate(List<RosterConfig> configList,Map<String,String[]> paramMap){
		this.configList = configList;
		this.paramMap = paramMap;
		
		for(RosterConfig config:configList){
			validateProperty(config);
		}
	}
	
	private void validateProperty(RosterConfig config){
		String type = config.getInfoProperty().getTypeInfo().getName();
		if (Type.COMMON.equals(type)) {
			commonValid(config);
		} else if (Type.LONG_STR.equals(type)) {
			commonValid(config);
		} else if (Type.CODE.equals(type)) {
			codeValid(config);
		} else if (Type.DATE.equals(type)) {
			dateValid(config);
		} else if (Type.MONTH.equals(type)) {
			dateValid(config);
		} else if (Type.YEAR.equals(type)) {
			dateValid(config);
		} else if (Type.IMAGE.equals(type)) {
			throw new RuleException("无法支持图片类型字段的查询");
		} else if (Type.SIGLE_SEL.equals(type)) {
			throw new RuleException("无法支持单选类型字段的查询");
		} else if (Type.NUMBER.equals(type)){
			numValid(config);
		} else {
			commonValid(config);//默认转换
		}
	}
	
	private void commonValid(RosterConfig config){
		//暂不处理
	}
	
	private void codeValid(RosterConfig config){
		//暂不处理
	}
	
	private void dateValid(RosterConfig config){
		InfoProperty p = config.getInfoProperty();
		String format = p.getTypeInfo().getFormat();
		String[] values = paramMap.get(p.getGuid());
		for(String str : values){
			if(!StringUtil.isEmpty(str)){
				String res = TimeUtil.format(str, format);
				if(!res.equals(str)){
					throw new IllegalArgumentException(p.getName()+"参数格式不正确");
				}
			}
		}
	}
	
	private void numValid(RosterConfig config){
		InfoProperty p = config.getInfoProperty();
		String[] values = paramMap.get(p.getGuid());
		for(String str : values){
			if(!StringUtil.isEmpty(str)){
				boolean res = NumberUtils.isNumber(str);
				if(!res){
					throw new IllegalArgumentException(p.getName()+"参数格式不正确");
				}
			}
		}
	}
}
