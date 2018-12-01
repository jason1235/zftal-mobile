package com.zfsoft.hrm.baseinfo.data.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.dataprivilege.util.DataFilterUtil;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.service.IInfoClassDataService;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.data.util.ImportDataValidator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig;

/** 
 * @author jinjj
 * @date 2012-10-25 下午04:05:33 
 *  
 */
public class InfoClassDataServiceImpl implements IInfoClassDataService {

	private IDynaBeanBusiness dynaBeanBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doDataImport(ImportDataValidator idv) throws Exception {
		int maxError = 20;
		String classId = idv.getClassId();
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		Map<String,HashMap<String,String>> dataMap = idv.getDataMap();
		int cnt = 0;
		DataProcessInfoUtil.setInfo("数据导入开始", null);
		int step=0;
		for(Entry entry : dataMap.entrySet()){
			HashMap<String,Object> data = (HashMap<String,Object>)entry.getValue();
			DynaBean bean = new DynaBean(clazz);
			bean.setValues(data);
			String gh = (String)data.get("gh");
			try{
				DataProcessInfoUtil.setStep("数据入库:", ++step, dataMap.size());
				String deptId="";
				if(classId.equals(IStatusUpdateConfig.BASEINFO_CLASS_ID)){
					dynaBeanBusiness.addPerson(gh);
					deptId = bean.getValueString("dwh");
				}else{
					DynaBean overall = dynaBeanBusiness.findUniqueByParam("gh", gh);
					if(overall == null){
						throw new RuleException("人员信息表[overall]中人员不存在,无法新增数据");
					}
					deptId = overall.getValueString("dwm");
				}
				if(!checkDeptFilter(deptId)){
					throw new RuleException("工号为"+gh+"的人员不在登录用户管辖的数据范围内！");
				}
				dynaBeanBusiness.addRecord(bean);
			}catch (RuleException e) {
				cnt++;
				DataProcessInfoUtil.setInfo("第"+step+"行，数据规则异常，"+e.getMessage(), InfoType.ERROR);
				if(cnt>=maxError){
					throw new RuleException("数据入库检测规则异常达到"+maxError+"次，入库操作终止");
				}
			}catch (Exception e) {
				throw e;
			}
		}
		DataProcessInfoUtil.setInfo("数据导入完成", null);
		//操作完成，异常回滚
//		throw new RuleException("操作测试完成，回滚");
	}
	
	@SuppressWarnings("unchecked")
	private boolean checkDeptFilter(String dwm){
		if(!"on".equals(SubSystemHolder.getPropertiesValue("org_scope_on_off"))){
			return true;
		};
		if(dwm==null) return false;
		List<DeptFilter> deptFilters = (List<DeptFilter>) DataFilterUtil.getCondition("bmgl");
		DeptFilter df = null;
		if(SessionFactory.getUser().getJsdms().indexOf("admin") != -1){
			return true;
		}else{
			for(DeptFilter deptFilter : deptFilters){
				if(SessionFactory.getUser().getJsdms().indexOf(deptFilter.getRoleId()) != -1){
					df = deptFilter;
				}
			}
			if(df!=null){
				if(DealDeptFilter.TYPE_SELF.equals(df.getDataType())){
					
					DynaBean selfInfo = DynaBeanUtil.getPerson(SessionFactory.getUser().getYhm());
					Assert.notNull(selfInfo,"无法获取当前登录的用户所在的部门");
					String depId=(String)selfInfo.getValue("dwm");
					if(dwm.equals(depId)) return true;
					List<Item> subList = CodeUtil.getChildren(ICodeConstants.DM_DEF_ORG, depId);
					if(subList!=null){
						for (Item item : subList) {
							if(dwm.equals(item.getGuid())) 
								return true;
						}
					}
				}
				else if(!DealDeptFilter.TYPE_ALL.equals(df.getDataType())){
					if(df.getOrgList().contains(dwm)){
						return true;
					}
					for (String org : df.getOrgList()) {
						List<Item> subList = CodeUtil.getChildren(ICodeConstants.DM_DEF_ORG, org);
						if(subList!=null){
							for (Item item : subList) {
								if(dwm.equals(item.getGuid())) 
									return true;
							}
						}
					}
				}
				else{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doDataImportNoCheckGh(ImportDataValidator idv) throws Exception {
		int maxError = 20;
		String classId = idv.getClassId();
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		Map<String,HashMap<String,String>> dataMap = idv.getDataMap();
		int cnt = 0;
		DataProcessInfoUtil.setInfo("数据导入开始", null);
		int step=0;
		for(Entry entry : dataMap.entrySet()){
			HashMap<String,Object> data = (HashMap<String,Object>)entry.getValue();
			DynaBean bean = new DynaBean(clazz);
			bean.setValues(data);
			try{
				DataProcessInfoUtil.setStep("数据入库:", ++step, dataMap.size());
				dynaBeanBusiness.addRecordNoCheckGh(bean);
			}catch (RuleException e) {
				cnt++;
				DataProcessInfoUtil.setInfo("第"+step+"行，数据规则异常，"+e.getMessage(), InfoType.ERROR);
				if(cnt>=maxError){
					throw new RuleException("数据入库检测规则异常达到"+maxError+"次，入库操作终止");
				}
			}catch (Exception e) {
				throw e;
			}
		}
		DataProcessInfoUtil.setInfo("数据导入完成", null);
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

}
