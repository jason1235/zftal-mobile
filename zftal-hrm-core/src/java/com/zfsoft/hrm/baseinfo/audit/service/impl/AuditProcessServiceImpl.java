package com.zfsoft.hrm.baseinfo.audit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.business.IAuditInfoBusiness;
import com.zfsoft.hrm.baseinfo.audit.business.IAuditProcessBusiness;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditInfoQuery;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.baseinfo.audit.service.IAuditProcessService;
import com.zfsoft.hrm.baseinfo.audit.util.AuditDefineCacheUtil;
import com.zfsoft.hrm.baseinfo.audit.util.AuditRecordHtmlGenerator;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanLogBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.IOperationConfig;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @author jinjj
 * @date 2012-10-10 上午10:13:21 
 *  
 */
public class AuditProcessServiceImpl implements IAuditProcessService {

	private IAuditProcessBusiness processBusiness;
	private IAuditInfoBusiness infoBusiness;
	private IDynaBeanLogBusiness dynaBeanLogBusiness;
	private IDynaBeanBusiness dynaBeanBusiness;
	
	@Override
	public void doPass(AuditInfo info){
		AuditProcess process = new AuditProcess();
		process = processBusiness.getById(info.getGuid());
		int step = process.getStep()+1;
		List<AuditDefine> list = AuditDefineCacheUtil.getDefine(process.getClassId());
		for(AuditDefine ad : list){
			if(ad.getOrder() == step){
				info.setRoleId(ad.getRoleId());
			}
		}
		int totalStep = 0;
		if(list != null){
			totalStep = list.size();
		}
		if(step>=totalStep){
			step = 99;
			//提交审核数据
			InfoClass clazz = InfoClassCache.getInfoClass(info.getClassId());
			DynaBean bean = new DynaBean(clazz);
			bean.setValue("logid", process.getLogId());
			bean = dynaBeanLogBusiness.findById(bean);
			dynaBeanLogBusiness.submitRecord(bean);
			dynaBeanBusiness.doPass(bean);
		}
		process.setStep(step);
		processBusiness.update(process);
		
		info.setStatus(1);
		infoBusiness.save(info);
	}
	
	@Override
	public void doReject(AuditInfo info){
		AuditProcess process = new AuditProcess();
		process = processBusiness.getById(info.getGuid());
		int step = process.getStep()+1;
		process.setStep(-1);
		processBusiness.update(process);
		//废弃日志
		InfoClass clazz = InfoClassCache.getInfoClass(info.getClassId());
		DynaBean bean = new DynaBean(clazz);
		bean.setValue("logid", process.getLogId());
		bean = dynaBeanLogBusiness.findById(bean);
		dynaBeanLogBusiness.removeById(bean);
		
		List<AuditDefine> list = AuditDefineCacheUtil.getDefine(process.getClassId());
		for(AuditDefine ad : list){
			if(ad.getOrder() == step){
				info.setRoleId(ad.getRoleId());
			}
		}
		info.setStatus(0);
		infoBusiness.save(info);
	}
	
	@Override
	public PageList<AuditProcess> getPagingList(AuditProcessQuery query) {
		return processBusiness.getPagingList(query);
	}
	
	@Override
	public PageList<AuditProcess> getAuditPagingList(AuditProcessQuery query) {
		List<String> roleList = query.getRoleId();
		List<AuditDefine> dList = new ArrayList<AuditDefine>();
		List<InfoClass> classList = InfoClassCache.getInfoClasses();
		for(InfoClass clazz : classList){
			List<AuditDefine> defineList = AuditDefineCacheUtil.getDefine(clazz.getGuid());
			if(defineList == null){
				continue;
			}
			for(AuditDefine d : defineList){
				for(String roleId : roleList){
					if(d.getRoleId().equals(roleId)){
						dList.add(d);
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		DynaBean bean = dynaBeanBusiness.findUniqueByParam("gh", query.getGh());
		if(bean == null){
			throw new RuleException("人员综合信息表中无当前用户信息，无法继续操作");
		}
		String deptId = (String)bean.getValue("dwm");
		String depts = getAllDutyDepart(deptId);
		for(AuditDefine d : dList){
			if(sb.length()==0){
				sb.append("(");
			}
			if(sb.length()>1){
				sb.append(" or ");
			}
			sb.append("(");
			sb.append("shdy.classid ='").append(d.getClassId()).append("'");
			sb.append(" and shdy.roleid ='").append(d.getRoleId()).append("'");
			if(d.getScope().equals("depart")){
				sb.append(" and o.dwm in (").append(depts).append(")");
			}
			sb.append(")");
		}
		if(sb.length()>0){
			sb.append(")");
		}
		if(sb.length()==0){//当过滤条件无时，则视为用户没有权限
			sb.append(" 1=0");
		}
		query.setExpress(sb.toString());
		return processBusiness.getAuditPagingList(query);
	}
	
	private String getAllDutyDepart(String deptId){
		StringBuilder sb = new StringBuilder();
		sb.append("'"+deptId+"'");
		List<Item> list = CodeUtil.getChildren(ICodeConstants.DM_DEF_ORG, deptId);
		for(Item item : list){
			sb.append(",");
			sb.append(getAllDutyDepart(item.getGuid()));
		}
		return sb.toString();
	}
	
	public String getRecordDetailHtml(String processId){
		AuditProcess process = processBusiness.getById(processId);
		InfoClass clazz = InfoClassCache.getInfoClass(process.getClassId());
		DynaBean bean = new DynaBean(clazz);
		bean.setValue("logid", process.getLogId());
		DynaBean logBean = dynaBeanLogBusiness.findById(bean);
		String operate = (String)logBean.getValue("operation_");
		String html = "";
		if(IOperationConfig.ADD.equals(operate)){
			html = new AuditRecordHtmlGenerator().parseSaveHtml(logBean);
		}
		if(IOperationConfig.REMOVE.equals(operate)){
			html = new AuditRecordHtmlGenerator().parseDeleteHtml(logBean);
		}
		if(IOperationConfig.MODIFY.equals(operate)){
			DynaBean old = null;
			if(process.getStep()<99&&process.getStep()>-1){
				old = dynaBeanBusiness.findById(logBean);
			}
			html = new AuditRecordHtmlGenerator().parseUpdateHtml(logBean, old);
		}
		if(StringUtils.isEmpty(html)){
			throw new RuleException("日志操作类型非法，无法解析");
		}
		return html;
	}
	
	//保存 test用
	public void save(AuditProcess process){
		processBusiness.save(process);
	}
	
	public AuditProcess getById(String guid){
		return processBusiness.getById(guid);
	}
	
	public List<AuditInfo> getInfoList(String guid){
		AuditInfoQuery query = new AuditInfoQuery();
		query.setGuid(guid);
		return infoBusiness.getList(query);
	}

	public void setProcessBusiness(IAuditProcessBusiness processBusiness) {
		this.processBusiness = processBusiness;
	}

	public void setInfoBusiness(IAuditInfoBusiness infoBusiness) {
		this.infoBusiness = infoBusiness;
	}

	public void setDynaBeanLogBusiness(IDynaBeanLogBusiness dynaBeanLogBusiness) {
		this.dynaBeanLogBusiness = dynaBeanLogBusiness;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

}
