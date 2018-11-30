package com.zfsoft.hrm.baseinfo.dyna.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * 动态bean验证
 * @author jinjj
 * @date 2012-8-9 上午01:29:37 
 *  
 */
public class DynaBeanValidateUtil {

	/**
	 * 根据信息类字段配置属性，进行数据校验
	 * @throws RuleException 校验失败抛出规则异常，并携带中文说明
	 * @param bean
	 */
	public static void validate(DynaBean bean){
		InfoClass clazz = bean.getClazz();
		List<InfoProperty> properties = clazz.getEditables();
		for(InfoProperty pro :properties){
			if(pro.getNeed()){
				Object val = bean.getValue(pro.getFieldName());
				if(val==null || StringUtils.isEmpty(val.toString())){
					throw new RuleException(pro.getDescription() + "不能为空");
				}
			}
		}
	}
}
