package com.zfsoft.hrm.normal.resume.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.zfsoft.hrm.baseinfo.audit.business.IAuditProcessBusiness;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.baseinfo.audit.util.AuditDefineCacheUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanLogBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.config.IOperationConfig;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.normal.resume.dto.StaffInfoDto;
import com.zfsoft.hrm.normal.resume.service.svcinterface.IStaffInfoService;
import com.zfsoft.orcus.util.Sequencer;
import com.zfsoft.util.base.StringUtil;

/** 
 * 教职工信息
 * @author jinjj
 * @date 2012-8-20 上午08:59:17 
 *  
 */
public class StaffInfoServiceImpl implements IStaffInfoService {

	private IDynaBeanBusiness dynaBeanBusiness;
	private IDynaBeanLogBusiness dynaBeanLogBusiness;
	private IAuditProcessBusiness processBusiness;
	
	@Override
	public DynaBean getById(DynaBean bean) {
		return dynaBeanBusiness.findById(bean);
	}

	@Override
	public void save(DynaBean bean) {
		if(detectAudit(bean)){
			AuditProcessQuery query = new AuditProcessQuery();
			String gh = (String)bean.getValue("gh");
			Assert.notNull(gh, "职工号不能为空");
			query.setClassId(bean.getClazz().getGuid());
			query.setStepType(0);
			query.setGh(gh);
			List<AuditProcess> list = processBusiness.getList(query);
			if(list.size()>0){
				if(!bean.getClazz().isMoreThanOne()){
					throw new RuleException("该信息类只允许单条记录，且已有信息提交审核中");
				}
			}
			
			bean.setValue("globalid", Sequencer.timeSequence());
			dynaBeanLogBusiness.doLog(bean, IOperationConfig.ADD);
			AuditProcess process = new AuditProcess();
			process.setClassId(bean.getClazz().getGuid());
			process.setGh((String)bean.getValue("gh"));
			process.setLogId((String)bean.getValue("logid"));
			process.setGlobalId((String)bean.getValue("globalid"));
			processBusiness.save(process);
		}else{
			dynaBeanBusiness.addRecord(bean);
		}
	}

	@Override
	public void update(DynaBean bean) {
		if(detectAudit(bean)){
			AuditProcessQuery query = new AuditProcessQuery();
			query.setClassId(bean.getClazz().getGuid());
			query.setGlobalId((String)bean.getValue("globalid"));
			query.setStepType(0);
			List<AuditProcess> list = processBusiness.getList(query);
			if(list.size()>0){
				throw new RuleException("该信息审核中,无法重复提交");
			}
			dynaBeanLogBusiness.doLog(bean, IOperationConfig.MODIFY);
			AuditProcess process = new AuditProcess();
			process.setClassId(bean.getClazz().getGuid());
			process.setGh((String)bean.getValue("gh"));
			process.setLogId((String)bean.getValue("logid"));
			process.setGlobalId((String)bean.getValue("globalid"));
			processBusiness.save(process);
		}else{
			dynaBeanBusiness.modifyRecord(bean);
		}
	}

	@Override
	public void remove(DynaBean bean) {
		if(detectAudit(bean)){
			AuditProcessQuery query = new AuditProcessQuery();
			query.setClassId(bean.getClazz().getGuid());
			query.setGlobalId((String)bean.getValue("globalid"));
			query.setStepType(0);
			List<AuditProcess> list = processBusiness.getList(query);
			if(list.size()>0){
				throw new RuleException("该信息审核中,无法重复提交");
			}
			bean = dynaBeanBusiness.findById(bean);
			dynaBeanLogBusiness.doLog(bean, IOperationConfig.REMOVE);
			AuditProcess process = new AuditProcess();
			process.setClassId(bean.getClazz().getGuid());
			process.setGh((String)bean.getValue("gh"));
			process.setLogId((String)bean.getValue("logid"));
			process.setGlobalId((String)bean.getValue("globalid"));
			processBusiness.save(process);
		}else{
			dynaBeanBusiness.removeRecord(bean);
		}
	}
	
	@Override
	public DynaBean getStaffOverAllInfo(String gh) {
		DynaBean bean = dynaBeanBusiness.findUniqueByParam("gh", gh);
		return bean;
	}
	/**
	 * patrick shen
	 */
	@Override
	public List<StaffInfoDto> getStaffOverAllInfoPageList(String classId,
			DynaBeanQuery query,String modeType) {
		
		InfoClass overallClass = InfoClassCache.getOverallInfoClass();
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		
		query.setClazz(clazz);
		
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("query", query);
		
		if("left".equals(modeType)){
			paraMap.put("joinType", "right join");
		}else if("only".equals(modeType)){
			paraMap.put("joinType", "left join");
		}else{
			paraMap.put("joinType", "inner join");
		}
		
		int totalNum=dynaBeanBusiness.findCountNoUniqable(paraMap);
		
		query.setTotalItem(totalNum);
		int startRow=(query.getToPage()-1) * query.getPerPageSize();
		query.setStartRowSelf(startRow);
		query.setEndRowSelf(startRow+query.getPerPageSize());
		
		List<Map<String, Object>> list=dynaBeanBusiness.findListNoUniqable(paraMap);
		
		List<StaffInfoDto> staffInfoDtos=new ArrayList<StaffInfoDto>();
		
		for (Map<String, Object> map : list) {
			Map<String,Object> overallMap=new HashMap<String, Object>();
			overallMap.put("GH", map.get("OVERALL_GH"));
			overallMap.put("XM", map.get("OVERALL_XM"));
			overallMap.put("DWM", map.get("OVERALL_DWM"));
			overallMap.put("DQZTM", map.get("OVERALL_DQZTM"));
			overallMap.put("BZLBM", map.get("OVERALL_BZLBM"));
			DynaBean overall = new DynaBean(overallClass);
			overall.setValues( overallMap );
			
			DynaBean dynaBean =null;
			if(map.get("GH")!=null){
				map.remove("OVERALL_GH");
				map.remove("OVERALL_XM");
				map.remove("OVERALL_DWM");
				map.remove("OVERALL_DQZTM");
				map.remove("OVERALL_BZLBM");
				dynaBean = new DynaBean(query.getClazz());
				dynaBean.setValues(map);
			}
			
			staffInfoDtos.add(makeupStaffInfoDto(dynaBean, overall));
		}
		
		return staffInfoDtos;
	}
	
	
	
@Override
	public List<StaffInfoDto> getStaffOverAllInfoPageListNoCheckGh(
			String classId, DynaBeanQuery query, String modeType) {
	
		InfoClass overallClass = InfoClassCache.getOverallInfoClass();
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		
		query.setClazz(clazz);
		
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("query", query);
		
		if("left".equals(modeType)||StringUtil.isEmpty(modeType)){
			paraMap.put("joinType", "right join");
		}else if("only".equals(modeType)){
			paraMap.put("joinType", "left join");
		}else{
			paraMap.put("joinType", "inner join");
		}
		
		int totalNum=dynaBeanBusiness.findCountNoUniqable(paraMap);
		
		query.setTotalItem(totalNum);
		int startRow=(query.getToPage()-1) * query.getPerPageSize();
		query.setStartRowSelf(startRow);
		query.setEndRowSelf(startRow+query.getPerPageSize());
		
		List<Map<String, Object>> list=dynaBeanBusiness.findListNoUniqable(paraMap);
		
		List<StaffInfoDto> staffInfoDtos=new ArrayList<StaffInfoDto>();
		
		for (Map<String, Object> map : list) {
			Map<String,Object> overallMap=new HashMap<String, Object>();
			overallMap.put("DQZTM", map.get("DQZTM"));
			DynaBean overall = new DynaBean(overallClass);
			overall.setValues( overallMap );
			DynaBean dynaBean = new DynaBean(query.getClazz());
			dynaBean.setValues(map);
			
			staffInfoDtos.add(makeupStaffInfoDto(dynaBean, overall));
		}
		
		return staffInfoDtos;
	}

	/*	*//**
	 * patrick shen
	 *//*
	private List<StaffInfoDto> getStaffOverAllInfoPageListLeft(String classId,
			DynaBeanQuery query, String modeType) {
		List<StaffInfoDto> staffInfoDtos=new ArrayList<StaffInfoDto>();
		//先取得人
		InfoClass overallClass = InfoClassCache.getOverallInfoClass();
		query.setClazz(overallClass);
		List<DynaBean> overallbeans=dynaBeanBusiness.findListNoUniqable(query);
		
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		query.setClazz(clazz);
		List<DynaBean> beanList = dynaBeanBusiness.findListNoUniqable(query);
		
		Map<String,List<DynaBean>> dynaBeanMap=new LinkedHashMap<String, List<DynaBean>>();
		
		String gh;
		int errorNum=0;
		for (DynaBean dynaBean : beanList) {
			if(dynaBean.getValue("gh")==null){
				errorNum++;
				continue;
			}
			gh=dynaBean.getValue("gh").toString();
			if(dynaBeanMap.get(gh)==null){
				dynaBeanMap.put(gh, new ArrayList<DynaBean>());
			}
			dynaBeanMap.get(gh).add(dynaBean);
		}
		int exPersonNum=dynaBeanMap.size();
		
		int totalNum=(overallbeans.size()-exPersonNum)+beanList.size()-errorNum;
		
		List<DynaBean> personDybeanList=null;
		for (DynaBean overall : overallbeans) {
			personDybeanList=dynaBeanMap.get(overall.getValue("gh"));
			if(personDybeanList!=null&&personDybeanList.size()>0){
				for (DynaBean dynaBean : personDybeanList) {
					staffInfoDtos.add(makeupStaffInfoDto( dynaBean, overall));
				}
			}else{
				staffInfoDtos.add(makeupStaffInfoDto( null, overall));
			}
		}
		
		query.setTotalItem(totalNum);
		int startRow=(query.getToPage()-1) * query.getPerPageSize();
		query.setStartRowSelf(startRow);
		query.setEndRowSelf(startRow+query.getPerPageSize());
		return staffInfoDtos.subList(query.getStartRow(), query.getEndRow());
		
	}
	
	*//**
	 * patrick shen
	 *//*
	private List<StaffInfoDto> getStaffOverAllInfoPageListRight(String classId,
			DynaBeanQuery query, String modeType) {
		
		StopWatch sw=new StopWatch();
		
		List<StaffInfoDto> staffInfoDtos=new ArrayList<StaffInfoDto>();
		//先取得人
		InfoClass overallClass = InfoClassCache.getOverallInfoClass();
		query.setClazz(overallClass);

		sw.start();
		
		List<DynaBean> overallbeans=dynaBeanBusiness.findListNoUniqable(query);
		
		System.out.println(sw.toString());
		
		Map<String,DynaBean> personMap=new LinkedHashMap<String, DynaBean>();
		String gh;
		
		sw.reset();
		sw.start();
		
		for (DynaBean dynaBean : overallbeans) {
			gh=dynaBean.getValue("gh").toString();
			if(personMap.get(gh)==null){
				personMap.put(gh, dynaBean);
			}
		}
		
		System.out.println(sw.toString());
		
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		query.setClazz(clazz);
		
		sw.reset();
		sw.start();
		
		List<DynaBean> beanList = dynaBeanBusiness.findListNoUniqable(query);
		
		System.out.println(sw.toString());
		
		sw.reset();
		sw.start();
		
		for (DynaBean dynaBean : beanList) {
			gh=dynaBean.getValue("gh").toString();
			if(personMap.get(gh)!=null){
				staffInfoDtos.add(makeupStaffInfoDto( dynaBean, personMap.get(gh)));
			}
		}
		
		System.out.println(sw.toString());
		
		query.setTotalItem(beanList.size());
		int startRow=(query.getToPage()-1) * query.getPerPageSize();
		query.setStartRowSelf(startRow);
		query.setEndRowSelf(startRow+query.getPerPageSize());
		
		sw.reset();
		sw.start();
		
		List<StaffInfoDto> rtnList= staffInfoDtos.subList(query.getStartRow(), query.getEndRow());
		
		System.out.println(sw.toString());
		return rtnList;
	}*/
	/**
	 * patrick shen
	 */
	private StaffInfoDto makeupStaffInfoDto(DynaBean dynaBean, DynaBean overall) {
		StaffInfoDto staffInfoDto;
		staffInfoDto=new StaffInfoDto();
		staffInfoDto.setOverall(overall);
		staffInfoDto.setDynaBean(dynaBean);
		return staffInfoDto;
	}

	@Override
	public List<DynaBean> queryBeans(DynaBeanQuery query) {
		List<DynaBean> list = dynaBeanBusiness.queryBeans(query);
		return list;
	}
	
	private boolean detectAudit(DynaBean bean){
		List<AuditDefine> list = AuditDefineCacheUtil.getDefine(bean.getClazz().getGuid());
		if(list==null || list.size()==0){
			return false;
		}
		return true;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	public void setDynaBeanLogBusiness(IDynaBeanLogBusiness dynaBeanLogBusiness) {
		this.dynaBeanLogBusiness = dynaBeanLogBusiness;
	}

	public void setProcessBusiness(IAuditProcessBusiness processBusiness) {
		this.processBusiness = processBusiness;
	}

}
