package com.zfsoft.hrm.baseinfo.infoclass.action;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyViewService;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;

/** 
 * 信息属性展示配置
 * @author jinjj
 * @date 2012-11-13 下午03:02:53 
 *  
 */
public class InfoPropertyViewAction extends HrmAction {

	private static final long serialVersionUID = 9106062409160627806L;
	private IInfoPropertyViewService viewService;
	private InfoPropertyViewQuery query = new InfoPropertyViewQuery();
	private String classId;
	private String propertyId;
	private List<InfoPropertyView> configList = new ArrayList<InfoPropertyView>();
	
	
	public String list() throws Exception{
		//读取用户配置
		List<InfoProperty> viewAllowList = InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm());
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		if(clazz == null){
			clazz = InfoClassCache.getOverallInfoClass();
		}
		
		//计算字段显示状态
		List<InfoProperty> list = clazz.getViewables();
		for(InfoProperty p : list){
			InfoPropertyView view = new InfoPropertyView();
			view.setAllow(false);
			view.setPropertyId(p.getGuid());
			view.setPropertyName(p.getName());
			for(InfoProperty v :viewAllowList){
				if(p.getGuid().equals(v.getGuid())){
					view.setAllow(true);
				}
			}
			configList.add(view);
		}
		return "list";
	}
	
	public String save() throws Exception{
		if(StringUtil.isEmpty(classId)){
			throw new RuleException("信息类ID为空");
		}
		if(StringUtil.isEmpty(propertyId)){
			propertyId = "";
		}
		String[] pId = propertyId.split(",");
		for(String id:pId){
			InfoPropertyView view = new InfoPropertyView();
			view.setClassId(classId);
			view.setPropertyId(id.trim());
			view.setUsername(getUser().getYhm());
			configList.add(view);
		}
		if(configList.size()>0){
			viewService.save(configList);
		}else{
			query.setClassId(classId);
			viewService.deleteBatch(query);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public List<InfoPropertyView> getConfigList() {
		return configList;
	}

	public void setViewService(IInfoPropertyViewService viewService) {
		this.viewService = viewService;
	}
	
}
