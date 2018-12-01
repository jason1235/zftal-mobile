package com.zfsoft.hrm.dybillgrade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybillgrade.dao.ISpBillGradeResultDao;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeCondition;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeLevel;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeResult;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeResultCondition;
import com.zfsoft.hrm.dybillgrade.enums.GradeBusinessEnums;
import com.zfsoft.hrm.dybillgrade.service.ISpBillGradeConfigService;
import com.zfsoft.hrm.dybillgrade.service.ISpBillGradeService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-12
 * @version V1.0.0
 */
public class SpBillGradeServiceImpl implements ISpBillGradeService {
	
	private ISpBillGradeResultDao spBillGradeResultDao;
	private ISpBillInstanceService spBillInstanceService;
	private ISpBillGradeConfigService spBillGradeConfigService;

	@Override
	public SpBillGradeResult doGrade(String configId, String billInstanceId, boolean again) {
		SpBillGradeResult spBillGradeResult = new SpBillGradeResult();
		spBillGradeResult.setConfigId(configId);
		spBillGradeResult.setBillInstanceId(billInstanceId);
		spBillGradeResult = grade(spBillGradeResult);
		List<SpBillGradeResult> list = spBillGradeResultDao.findList(spBillGradeResult);
		if((!again)||list==null||list.isEmpty())
			spBillGradeResultDao.insert(spBillGradeResult);
		else{
			spBillGradeResult.setId(list.get(0).getId());
			spBillGradeResultDao.update(spBillGradeResult);
		}
		return spBillGradeResult;
	}

	@Override
	public SpBillGradeConfig getGradeConfig(String billConfigId,
			GradeBusinessEnums gradeBusinessEnums) {
		SpBillGradeConfig query = new SpBillGradeConfig();
		query.setBillConfigId(billConfigId);
		query.setBusinessCode(gradeBusinessEnums.getKey());
		List<SpBillGradeConfig>  list = spBillGradeConfigService.findList(query);
		if(list==null||list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	public SpBillGradeResult getGradeResult(String id) {
		SpBillGradeResult result = new SpBillGradeResult();
		result.setId(id);
		result = spBillGradeResultDao.findById(result);
		SpBillGradeConfig config = spBillGradeConfigService.getById(result.getConfigId());
		for (SpBillGradeLevel level : config.getLevelConfig().getLevelList()) {
			if(result.getSumScore()>=level.getScorePoint()){
				result.setSpBillGradeLevel(level);
				break;
			}
		}
		return result;
	}
	
	@Override
	public void delete(String id) {
		SpBillGradeResult query = new SpBillGradeResult();
		query.setId(id);
		spBillGradeResultDao.delete(query);
	}

	@Override
	public SpBillGradeResult doGradeAgain(String id) {
		SpBillGradeResult result =getGradeResult(id);
		if (result == null) {
			return null;
		}
		result = grade(result);
		spBillGradeResultDao.update(result);
		return result;
	}
	
	private SpBillGradeResult grade(SpBillGradeResult spBillGradeResult){
		SpBillGradeConfig config = spBillGradeConfigService.findFullSpBillGradeConfig(spBillGradeResult.getConfigId());
		if(config == null) return null;
		spBillGradeResult.setBillConfigId(config.getBillConfigId());
		spBillGradeResult.setSpBillGradeResultConditions(new ArrayList<SpBillGradeResultCondition>());
		SpBillInstance instance = spBillInstanceService.getSpBillInstanceById(config.getBillConfigId(), spBillGradeResult.getBillInstanceId());
		if(instance == null) return null;
		List<XmlValueClass> valueClassList = instance.getXmlValueClasses().getValueClasses();
		Map<Long, XmlValueClass> valueClassMap = new HashMap<Long, XmlValueClass>();
//		SpBillGradeResult spBillGradeResult = new SpBillGradeResult();
		int sumScore = 0;
		for (SpBillGradeCondition condition : config.getConditions()) {
			if(StringUtil.isEmpty(condition.getBillClassId())) continue;
			Long classId = Long.valueOf(condition.getBillClassId());
			XmlValueClass valueClass = valueClassMap.get(classId);
			if(valueClass == null){
				for (XmlValueClass xmlValueClass : valueClassList) {
					if(classId.equals(xmlValueClass.getBillClassId())){
						valueClassMap.put(classId, xmlValueClass);
						valueClass=xmlValueClass;
						break;
					}
				}
			}
			if(valueClass!=null){
				List<XmlValueEntity> eList = new ArrayList<XmlValueEntity>();
				for (XmlValueEntity entity : valueClass.getValueEntities()) {
					if(condition.runCondition(true,entity.getValueMap())){
						sumScore+=Integer.valueOf(condition.getScore());
						eList.add(entity);
					};
				}
				if(!eList.isEmpty()){
					SpBillGradeResultCondition resultCondition = new SpBillGradeResultCondition();
					resultCondition.setXmlValueEntities(eList);
					resultCondition.setConditionText(condition.getText());
					resultCondition.setScore(condition.getScore());
					resultCondition.setBillClassId(classId);
					resultCondition.setConditionId(condition.getId());
					spBillGradeResult.addSpBillGradeResultCondition(resultCondition);
				}
			}
		}
		for (SpBillGradeLevel level : config.getLevelConfig().getLevelList()) {
			if(sumScore>=level.getScorePoint()){
				spBillGradeResult.setSpBillGradeLevel(level);
				break;
			}
		}
		spBillGradeResult.setSumScore(sumScore);
		return spBillGradeResult;
	}

	/**
	 * 设置
	 * @param spBillGradeResultDao 
	 */
	public void setSpBillGradeResultDao(ISpBillGradeResultDao spBillGradeResultDao) {
		this.spBillGradeResultDao = spBillGradeResultDao;
	}

	/**
	 * 设置
	 * @param spBillInstanceService 
	 */
	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	/**
	 * 设置
	 * @param spBillGradeConfigService 
	 */
	public void setSpBillGradeConfigService(
			ISpBillGradeConfigService spBillGradeConfigService) {
		this.spBillGradeConfigService = spBillGradeConfigService;
	}
	
	

}
