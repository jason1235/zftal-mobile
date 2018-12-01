package com.zfsoft.hrm.dybill.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.dybill.dao.ISpBillDefineCatchDao;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillDefineCatchService;
import com.zfsoft.hrm.dybill.service.ISpBillExportService;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-7-18
 * @version V1.0.0
 */
public class SpBillDefineCatchServiceImpl implements ISpBillDefineCatchService {
	private ISpBillDefineCatchDao spBillDefineCatchDao;
	private ISpBillExportService spBillExportService;
	
	private final static String IN_BILL = "\\$\\{[^\\}]*\\}";
	
	private String getFullExpress(String expresstion,SpBillConfig spBillConfig, SpBillInstance spBillInstance){
		if(expresstion == null || "".equals(expresstion.trim())){
			return " 1=1";
		}
		if(spBillConfig == null || spBillInstance == null){
			return expresstion;
		}
		
		List<String> billField = getFields(expresstion, IN_BILL);
		String reString = expresstion; 
		if(!billField.isEmpty()){
			Map<String, String> map= spBillExportService.getValueMap(spBillConfig, spBillInstance, false);
			for (String str : billField) {
				if(str.length()<3) continue;
				String key = str.substring(2, str.length()-1);
				String value = map.get(key);
				if(StringUtil.isEmpty(value)){
					value="";
				}
				reString = reString.replaceAll(Pattern.quote(str), value);
			}
		}
		
		return reString;
	}
	
	private static List<String> getFields(String expresstion,String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(expresstion);
		List<String> fields = new ArrayList<String>();
		while (m.find()) {
			fields.add(m.group());
		}
		return fields;
	}

	@Override
	public List<DynaBean> getListToView(XmlDefineCatch defineCatch,SpBillConfig spBillConfig, SpBillInstance spBillInstance) {
		
		defineCatch.setExpression(getFullExpress(defineCatch.getExpression(), spBillConfig, spBillInstance));
		List<HashMap<String, Object>> list = spBillDefineCatchDao.findList(defineCatch);
		List<DynaBean> dynaBeans = new ArrayList<DynaBean>();
		for (HashMap<String, Object> values : list) {
			DynaBean bean = new DynaBean(defineCatch.getInfoClass());
			bean.setValues(values);
			dynaBeans.add(bean);
		}
		return dynaBeans;
	}

	/**
	 * 返回
	 */
	public ISpBillDefineCatchDao getSpBillDefineCatchDao() {
		return spBillDefineCatchDao;
	}

	/**
	 * 设置
	 * @param billDefineCatchDao 
	 */
	public void setSpBillDefineCatchDao(ISpBillDefineCatchDao billDefineCatchDao) {
		this.spBillDefineCatchDao = billDefineCatchDao;
	}

	/**
	 * 返回
	 */
	public ISpBillExportService getSpBillExportService() {
		return spBillExportService;
	}

	/**
	 * 设置
	 * @param spBillExportService 
	 */
	public void setSpBillExportService(ISpBillExportService spBillExportService) {
		this.spBillExportService = spBillExportService;
	}
	

}
