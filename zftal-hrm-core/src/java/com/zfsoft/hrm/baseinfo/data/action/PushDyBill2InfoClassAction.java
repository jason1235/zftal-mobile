package com.zfsoft.hrm.baseinfo.data.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushEventConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushProperty;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.enums.DataPushEventOpType;
import com.zfsoft.hrm.dybill.enums.EntityType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushEventConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushRunService;
import com.zfsoft.hrm.dybill.service.ISpBillDefineCatchService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-4-28
 * @version V1.0.0
 */
public class PushDyBill2InfoClassAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3918781578125779957L;
	private XmlDefineCatch defineCatch;
	private SpBillDataPushEventConfig config;
	
	private ISpBillDataPushRunService spBillDataPushRunService;
	private ISpBillDataPushEventConfigService spBillDataPushEventConfigService;
	private ISpBillDefineCatchService spBillDefineCatchService;
	
	public String push(){
		getValueStack().set("btn_action", "doPush");
		return "push";
	}
	
	public String doPush(){
		int maxValidateError = 20;
		try{
			
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			DataProcessInfoUtil.setInfo("数据头校验开始...", null);
			config = spBillDataPushEventConfigService.getById(config.getId());
			if(config==null){
				throw new RuleException("要执行的表单事件不存在！");
			}
			DataProcessInfoUtil.setInfo("开始根据配置寻找符合条件的结果集", null);
			defineCatch.setUniqueField("''");
			List<DynaBean> list = spBillDefineCatchService.getListToView(defineCatch, null, null);
			if(list==null||list.size()==0){
				DataProcessInfoUtil.setInfo("未能找到符合条件的结果", InfoType.WARN);
			}
			int step = 0;
			int cnt = 0;
			for(DynaBean b:list){
				try{
				DataProcessInfoUtil.setStep("数据入库:", ++step, list.size());
				String gh = b.getValueString("gh");
				String instanceId = b.getValueString("instanceId");
				if(StringUtil.isEmpty(gh)){
					throw new RuleException("工号不存在");
				}
				if(StringUtil.isEmpty(gh)){
					throw new RuleException("表单实例编号不存在");
				}
				spBillDataPushRunService.pushData(config.getId(), gh, instanceId);
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+step+"  导入失败,原因:"+e.getMessage(), InfoType.ERROR);
						cnt++;
						if(cnt>=maxValidateError){
							throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");	
						}
					}else{
						e.printStackTrace();
						throw new RuleException("导入时发生未知异常",e);
					}
				}
			}
			DataProcessInfoUtil.setInfo(" 导入完成", null);
		}catch (Exception e) {
			DataProcessInfoUtil.setInfo(" "+e.getMessage(), InfoType.ERROR);
			DataProcessInfoUtil.setInfo(" 导入终止", null);
		}finally{
			DataProcessInfoUtil.setInfo("-1", null);
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DataProcessInfoUtil.clear();
		}
		return null;
	}
	
	
	public String pushBill(){
		getValueStack().set("btn_action", "doPushBill");
		return "push";
	}
	
	public String doPushBill(){
		int maxValidateError = 20;
		try{
			
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			DataProcessInfoUtil.setInfo("数据头校验开始...", null);
			config = spBillDataPushEventConfigService.getById(config.getId());
			if(config==null){
				throw new RuleException("要执行的表单事件不存在！");
			}
			if(StringUtil.isEmpty(config.getInfoClassId())){
				throw new RuleException("暂时只支持从信息类回填至表单类");
			}
			InfoClass clazz = InfoClassCache.getInfoClass(config.getInfoClassId());
			if(clazz == null){
				throw new RuleException("所关联的信息类不存在！");
			}
			
			DataProcessInfoUtil.setInfo("开始根据配置寻找要填充的表单类", null);
			ISpBillConfigService  spBillConfigService =SpringHolder.getBean("spBillConfigService", ISpBillConfigService.class);
			ISpBillInstanceService spBillInstanceService =SpringHolder.getBean("spBillInstanceService", ISpBillInstanceService.class);
			IDynaBeanService dynaBeanService = SpringHolder.getBean("baseDynaBeanService", IDynaBeanService.class);
			SpBillConfig spBillConfig =  spBillConfigService.getSpBillConfigById(config.getBillConfigId());
			XmlBillClass xmlBillClass = spBillConfig.getXmlBillClasses().getBillClassById(config.getBillClassId());
			if(xmlBillClass == null){
				throw new RuleException("所关联的表单类不存在！");
			}
			
			DataProcessInfoUtil.setInfo("开始根据配置寻找符合条件的结果集", null);
			defineCatch.setUniqueField("''");
			List<DynaBean> list = spBillDefineCatchService.getListToView(defineCatch, null, null);
			if(list==null||list.size()==0){
				DataProcessInfoUtil.setInfo("未能找到符合条件的结果", InfoType.WARN);
			}
			int step = 0;
			int cnt = 0;
			for(DynaBean b:list){
				try{
					boolean change = false;
					DataProcessInfoUtil.setStep("数据入库:", ++step, list.size());
					String gh = b.getValueString("gh");
					String instanceId = b.getValueString("instanceId");
					if(StringUtil.isEmpty(gh)){
						throw new RuleException("工号不存在");
					}
					if(StringUtil.isEmpty(instanceId)){
						throw new RuleException("表单实例编号不存在");
					}
					
					/**-------------------------------------------------------**/
					SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(config.getBillConfigId(), instanceId);
					XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
					if(xmlValueClasses == null){
						xmlValueClasses = new XmlValueClasses();
					}
					if(xmlValueClasses.getValueClasses() == null){
						xmlValueClasses.setValueClasses(new ArrayList<XmlValueClass>());
					}
					XmlValueClass xmlValueClass=xmlValueClasses.getValueClassByClassId(xmlBillClass.getId());
					//空则初始化
					if (xmlValueClass == null) {
						xmlValueClass = new XmlValueClass();
						xmlValueClass.setBillClassId(xmlBillClass.getId());
						//加入到信息类值对象序列
						xmlValueClasses.getValueClasses().add(xmlValueClass);
					}
					if(DataPushEventOpType.INSERT.equals(config.getEventOpType())||
							xmlValueClass.getValueEntities()==null){
						//初始化表单类内容
						xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
						change = true;
					}
					
					DynaBeanQuery query=new DynaBeanQuery(clazz);
					query.setHistory(true);
					String expression = "gh = #{params.gh}";
					if(!StringUtil.isEmpty(config.getWhereExpression())){
						expression += " and " + config.getWhereExpression();
					}
					query.setExpress(expression);
					query.setParam("gh", gh);
					
					List<DynaBean> dynaBeanList=dynaBeanService.findList(query);
					for (int i = 0;i<dynaBeanList.size();i++) {
						if(i>=xmlBillClass.getMaxLength()){
							break;
						}
						change = true;
						DynaBean dynaBean = dynaBeanList.get(i);
						while(xmlValueClass.getValueEntities().size()<=i){
							XmlValueEntity e = new XmlValueEntity();
							e.setId(System.currentTimeMillis());
							e.setEntityType(EntityType.SELFADD);
							e.setValueProperties(new ArrayList<XmlValueProperty>());
							xmlValueClass.getValueEntities().add(e);
						}
						XmlValueEntity xmlValueEntity = xmlValueClass.getValueEntities().get(i);
						xmlValueEntity.setId(System.currentTimeMillis());
						//将动态信息类字段转化为表单属性列
						XmlValueProperty xmlValueProperty;
						List<SpBillDataPushProperty>  pList = spBillDataPushEventConfigService.getPropertyByConfigId(config.getId());
						for (SpBillDataPushProperty spBillDataPushProperty : pList) {
							xmlValueProperty = xmlValueEntity.getValuePropertyById(spBillDataPushProperty.getBillPropertyId());
							if(xmlValueProperty ==null){
								xmlValueProperty = new XmlValueProperty();
								xmlValueProperty.setBillPropertyId(spBillDataPushProperty.getBillPropertyId());
								xmlValueEntity.getValueProperties().add(xmlValueProperty);
							}
							InfoProperty infoProperty = clazz.getPropertyById(spBillDataPushProperty.getLocalPropertyId());
							if(infoProperty == null)continue;
							Object value=dynaBean.getValue(infoProperty.getFieldName());
							if(value instanceof Date){
								XmlBillProperty xmlBillProperty = xmlBillClass.getBillPropertyById(spBillDataPushProperty.getBillPropertyId());
								xmlValueProperty.setValue(TimeUtil.format((Date)value,xmlBillProperty.getInfoProperty().getTypeInfo().getFormat()));
							}else{
								xmlValueProperty.setValue(value==null?"":value.toString());
							}
						}
					}
					if(change){
						//将填充完整的表单类填充到表单实例中
						spBillInstance.setXmlValueClasses(xmlValueClasses);
						//修改入数据库
						spBillInstanceService.modifySpBillInstance(spBillInstance);
					}
					/**-------------------------------------------------------**/
					
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+step+"  导入失败,原因:"+e.getMessage(), InfoType.ERROR);
						cnt++;
						if(cnt>=maxValidateError){
							throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");	
						}
					}else{
						e.printStackTrace();
						throw new RuleException("导入时发生未知异常",e);
					}
				}
			}
			DataProcessInfoUtil.setInfo(" 导入完成", null);
		}catch (Exception e) {
			DataProcessInfoUtil.setInfo(" "+e.getMessage(), InfoType.ERROR);
			DataProcessInfoUtil.setInfo(" 导入终止", null);
		}finally{
			DataProcessInfoUtil.setInfo("-1", null);
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DataProcessInfoUtil.clear();
		}
		return null;
	}
	

	/**
	 * 返回
	 */
	public SpBillDataPushEventConfig getConfig() {
		return config;
	}

	/**
	 * 设置
	 * @param config 
	 */
	public void setConfig(SpBillDataPushEventConfig config) {
		this.config = config;
	}

	/**
	 * 设置
	 * @param spBillDataPushRunService 
	 */
	public void setSpBillDataPushRunService(
			ISpBillDataPushRunService spBillDataPushRunService) {
		this.spBillDataPushRunService = spBillDataPushRunService;
	}

	/**
	 * 设置
	 * @param spBillDataPushEventConfigService 
	 */
	public void setSpBillDataPushEventConfigService(
			ISpBillDataPushEventConfigService spBillDataPushEventConfigService) {
		this.spBillDataPushEventConfigService = spBillDataPushEventConfigService;
	}

	/**
	 * 设置
	 * @param spBillDefineCatchService 
	 */
	public void setSpBillDefineCatchService(
			ISpBillDefineCatchService spBillDefineCatchService) {
		this.spBillDefineCatchService = spBillDefineCatchService;
	}

	/**
	 * 设置
	 * @param defineCatch 
	 */
	public void setDefineCatch(XmlDefineCatch defineCatch) {
		this.defineCatch = defineCatch;
	}

	/**
	 * 返回
	 */
	public XmlDefineCatch getDefineCatch() {
		return defineCatch;
	}
}