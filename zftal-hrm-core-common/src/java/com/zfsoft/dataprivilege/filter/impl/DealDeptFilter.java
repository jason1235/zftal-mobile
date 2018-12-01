package com.zfsoft.dataprivilege.filter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dataprivilege.dao.IDataPrivilegeDao;
import com.zfsoft.dataprivilege.dto.AbstractFilter;
import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.entity.DataPrivilege;
import com.zfsoft.dataprivilege.filter.IDealFilter;
import com.zfsoft.dataprivilege.util.ResourceUtil;
import com.zfsoft.dataprivilege.xentity.Context;
import com.zfsoft.dataprivilege.xentity.Param;
import com.zfsoft.dataprivilege.xentity.Resource;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;
import com.zfsoft.common.spring.SpringHolder;
/**
 * 处理部门过滤的对象
 * @author Patrick Shen
 */
public class DealDeptFilter implements IDealFilter{
	public static final String OTHERNAME="otherName";//别名
	public static final String DEPTFIELD="deptField";//部门字段
	public static final String TYPE_SELF="self";//本部门
	public static final String TYPE_IN="in";//包含
	public static final String TYPE_ALL="all";//全部
	public static final String CONTEXT_ID="bmgl";//过滤编码Id
	
	@Override
	public AbstractFilter getObjectFromXml(String value) {
		return (DeptFilter)JaxbUtil.getObjectFromXml(value, DeptFilter.class);
	}

	@Override
	public String getXmlFromObject(AbstractFilter obj) {
		return JaxbUtil.getXmlFromObject(obj);
	}

	@Override
	public String getCondition(Map<String, Object> paramMap) {
		Resource resource=new Resource();
		resource.setParam(new ArrayList<Param>());
		Param param=new Param();
		param.setName("otherName");
		if(paramMap.get("otherName")!=null){
			param.setValue(paramMap.get("otherName").toString());
		}
		resource.getParam().add(param);
		param=new Param();
		param.setName("deptField");
		param.setValue(paramMap.get("deptField").toString());
		resource.getParam().add(param);
		return getCondition(ResourceUtil.getContextById(CONTEXT_ID), resource);
	}
	
	@Override
	public List<DeptFilter> getCondition() {
		IDataPrivilegeDao dataPrivilegeDao=(IDataPrivilegeDao)SpringHolder.getBean("dataPrivilegeDao");
		DataPrivilege query=new DataPrivilege();
		query.setUserId(SessionFactory.getUser().getYhm());
		query.setFilterId(CONTEXT_ID);
		List<DeptFilter> deptFilters=new ArrayList<DeptFilter>();
		List<DataPrivilege> dataPrivileges=dataPrivilegeDao.findByQuery(query);
		DeptFilter filter;
		for (DataPrivilege dataPrivilege : dataPrivileges) {
			filter=JaxbUtil.getObjectFromXml(dataPrivilege.getXmlValue(),DeptFilter.class);
			deptFilters.add(filter);
		}
		return deptFilters;
	}
	@Override
	public String getCondition(Context context,Resource resource) {
		//取得部门
		List<DataPrivilege> dataPrivilegesAll = getDataPrivilege(context.getId());
		Object dwm=null;
		DynaBean dynaBean=DynaBeanUtil.getPerson(SessionFactory.getUser().getYhm());
		if(dynaBean==null||dynaBean.getValue("dwm")==null){
			dwm = null;
		}else{
			dwm = dynaBean.getValue("dwm");
		}
		
		
		if(dataPrivilegesAll.size()<=0){//默认查询的是本部门
			
			if(dwm==null){//部门为空则返回不成立表达式
				return " 1=2 ";
			}
			List<String> strs=new ArrayList<String>();
			strs.add("'"+dwm.toString()+"'");
			return getChildCondition(resource, strs);
		}
		List<DataPrivilege> dataPrivileges = new ArrayList<DataPrivilege>();
		for (DataPrivilege dataPrivilege : dataPrivilegesAll) {
			if (SessionFactory.getUser().getJsdms().indexOf(dataPrivilege.getRoleId())!=-1) {
				dataPrivileges.add(dataPrivilege);
			}
		}
		if(dataPrivileges.size()==0){
			if(dwm==null){//部门为空则返回不成立表达式
				return " 1=2 ";
			}
			List<String> strs=new ArrayList<String>();
			strs.add("'"+dwm.toString()+"'");
			return getChildCondition(resource, strs);
		}
		//开始查看权限，并组装数据权限
		String condition="";
		int i=0;
		for (DataPrivilege dataPrivilege : dataPrivileges) {
			DeptFilter deptFilter=(DeptFilter)getObjectFromXml(dataPrivilege.getXmlValue());//将xml解析成对象
			
			String typeObj=deptFilter.getDataType();//取得部门过滤类型
			Object objlist=deptFilter.getOrgList();//取得组织机构代码列表
			
			String childcondition = "";
			if(typeObj!=null){
				if(DealDeptFilter.TYPE_SELF.equalsIgnoreCase(typeObj.toString())){//本部门
					if(dwm==null&&dataPrivileges.size()<=1){
						return " 1=2 ";
					}
					List<String> strs=new ArrayList<String>();
					strs.add("'"+dwm.toString()+"'");
					childcondition = getChildCondition(resource, strs);
				}else if(DealDeptFilter.TYPE_IN.equalsIgnoreCase(typeObj.toString())){//多个部门
					if(objlist!=null){
						@SuppressWarnings("unchecked")
						List<String> list=(List<String>)objlist;
						int index=0;
						for (String string : list) {
							list.set(index, "'"+string+"'");
							index++;
						}
						childcondition=getChildCondition(resource, list);
					}else{
						childcondition="1=2";
					}
				}else{//全部
					return " 1=1 ";
				}
			}
			if(i<dataPrivileges.size()-1){
				condition=condition+" ("+childcondition+") or ";
			}else{
				condition="("+condition+" ("+childcondition+") )";
			}
			i++;
		}
		
		return condition;
	}

	private List<DataPrivilege> getDataPrivilege(String contextId) {
		Context context=ResourceUtil.getContextById(contextId);
		IDataPrivilegeDao dataPrivilegeDao=(IDataPrivilegeDao)SpringHolder.getBean("dataPrivilegeDao");
		DataPrivilege query=new DataPrivilege();
		query.setUserId(SessionFactory.getUser().getYhm());
		query.setFilterId(context.getId());
		List<DataPrivilege> dataPrivileges=dataPrivilegeDao.findByQuery(query);
		return dataPrivileges;
	}

	private String getChildCondition(Resource resource, List<String> list) {
		String childCondition="";
		if(!StringUtil.isEmpty(resource.getParamValue("otherName"))){
			childCondition+=resource.getParamValue("otherName")+".";
		}
		String str=list.toString();
		
//		IOrgDao orgDao=(IOrgDao)SpringHolder.getBean("baseOrgDao");
//		for (String string : list) {
//			
//		}
		
		if(list.size()>0){
			return childCondition+resource.getParamValue("deptField")+" in ("+str.substring(1, str.length()-1)+")";
		}else{
			return " 1=2 ";
		}
		
	}

	@Override
	public String getConditionForApr(String contextId,String aslisName,String fieldName) {
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put(DealDeptFilter.OTHERNAME, aslisName);
		paramMap.put(DealDeptFilter.DEPTFIELD, fieldName);
		return getCondition(paramMap);
	}

}
