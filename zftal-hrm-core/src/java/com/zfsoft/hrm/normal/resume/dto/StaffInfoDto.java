package com.zfsoft.hrm.normal.resume.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.util.base.StringUtil;

public class StaffInfoDto {
	private DynaBean overall;
	private DynaBean dynaBean;
	private Map<String,String> valueMap;
	
	public static List<InfoProperty> getViewableProperties(InfoClass claszs){
		InfoClass overallClass=InfoClassCache.getOverallInfoClass();
		List<InfoProperty> viewables=new ArrayList<InfoProperty>();
		viewables.add(overallClass.getPropertyByName("gh"));
		viewables.add(overallClass.getPropertyByName("xm"));
		viewables.add(overallClass.getPropertyByName("dwm"));
		viewables.add(overallClass.getPropertyByName("dqztm"));
		viewables.add(overallClass.getPropertyByName("bzlbm"));
		for(InfoProperty infoProperty :viewables){
			infoProperty.setClasz(overallClass);
		}
		for(InfoProperty infoProperty :claszs.getViewables()){
			infoProperty.setClasz(claszs);
			if("gh".equals(infoProperty.getFieldName())
					||"xm".equals(infoProperty.getFieldName())
					||"dwm".equals(infoProperty.getFieldName())
					||"dwh".equals(infoProperty.getFieldName())
					||"dqztm".equals(infoProperty.getFieldName())
				    ||"bzlbm".equals(infoProperty.getFieldName())){
				continue;
			}
			viewables.add(infoProperty);
		}
		return viewables;
	}
	public Map<String,String> getValueMap(){
		if(valueMap!=null){
			return valueMap;
		}
		Map<String, String> valueMap=new HashMap<String, String>();
		valueMap.putAll(getValues(overall));
		valueMap.putAll(getValues(dynaBean));
		return valueMap;
	}

	private Map<String, String> getValues(DynaBean dynaBean) {
		Map<String, String> valueMap=new HashMap<String, String>();
		String identityName;
		if(dynaBean==null){
			return valueMap;
		}
		if(dynaBean.getClazz() == null){//排除修改表单模板后，实例对象中的历史数据，查询表单类为空的问题
			throw new RuntimeException("请先设置表单类");
		}
		
		identityName=dynaBean.getClazz().getIdentityName();
		if(dynaBean.getViewables()==null){
			return valueMap;
		}
		for (String fieldName : dynaBean.getViewHtml().keySet()) {
			String key = StringUtils.lowerCase(identityName+"."+fieldName);
			if(StringUtil.isEmpty(valueMap.get(key))){
				valueMap.put(key, dynaBean.getViewHtml().get(fieldName));
			}
		}
		
		return valueMap;
	}
	
//	private List<DynaBean> getLastValueEntity(){
//		if(clasz==null){
//			throw new RuntimeException("请先设置表单类");
//		}
//		List<DynaBean> result = new ArrayList<DynaBean>();
//		List<InfoProperty> markPropertiess=clasz.getMarkInfoProperties();
//		DynaBean dynaBean;
//		if(dynaBeanList!=null&&dynaBeanList.size()>=1){
//			if(markPropertiess!=null&&markPropertiess.size()>0){
//				if(dynaBeanList!=null&&dynaBeanList.size()>=1){
//					return dynaBeanList.subList(dynaBeanList.size()-1, dynaBeanList.size());
//				}
//			}else{
//				for (InfoProperty infoProperty : markPropertiess) {
//					dynaBean=getLastValueEntity(infoProperty.getFieldName());
//					if(dynaBean!=null){
//						result.add(dynaBean);
//					}
//				}
//				
//				if(result.size()==0){
//					return dynaBeanList.subList(dynaBeanList.size()-1,dynaBeanList.size());
//				}
//			}
//		}
//		return result;
//	}
//	
//	private DynaBean getLastValueEntity(String fieldName) {
//		for (DynaBean dynaBean : dynaBeanList) {
//			String value=dynaBean.getValue(fieldName).toString();
//			if("1".equals(value)||"true".equals(value)){
//				return dynaBean;
//			}
//		}
//		return null;
//	}

	/**
	 * @return the overall
	 */
	public DynaBean getOverall() {
		return overall;
	}
	/**
	 * @param overall the overall to set
	 */
	public void setOverall(DynaBean overall) {
		this.overall = overall;
	}
//	/**
//	 * @return the dynaBeanList
//	 */
//	public List<DynaBean> getDynaBeanList() {
//		return dynaBeanList;
//	}
//	/**
//	 * @param dynaBeanList the dynaBeanList to set
//	 */
//	public void setDynaBeanList(List<DynaBean> dynaBeanList) {
//		this.dynaBeanList = dynaBeanList;
//	}
	/**
	 * @return the dynaBean
	 */
	public DynaBean getDynaBean() {
		return dynaBean;
	}
	/**
	 * @param dynaBean the dynaBean to set
	 */
	public void setDynaBean(DynaBean dynaBean) {
		this.dynaBean = dynaBean;
	}
}
