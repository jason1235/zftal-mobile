package com.zfsoft.hrm.dybillgrade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybillgrade.dao.ISpBillGradeConditionDao;
import com.zfsoft.hrm.dybillgrade.dao.ISpBillGradeConfigDao;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeCondition;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConditionQuery;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfigQuery;
import com.zfsoft.hrm.dybillgrade.service.ISpBillGradeConfigService;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-9
 * @version V1.0.0
 */
public class SpBillGradeConfigServiceImpl implements ISpBillGradeConfigService {
	
	private ISpBillGradeConfigDao spBillGradeConfigDao;
	private ISpBillGradeConditionDao spBillGradeConditionDao;

	@Override
	public void delete(String id) {
		SpBillGradeConfig query = new SpBillGradeConfig();
		query.setId(id);
		spBillGradeConfigDao.delete(query);
		SpBillGradeCondition condition = new SpBillGradeCondition();
		condition.setConfigId(id);
		spBillGradeConditionDao.delete(condition);
	}

	@Override
	public SpBillGradeConfig findFullSpBillGradeConfig(String id) {
		
		SpBillGradeConfig config=this.getById(id);
		config.setConditions(new ArrayList<SpBillGradeCondition>());
		SpBillGradeCondition query = new SpBillGradeCondition();
		query.setId(id);
		List<SpBillGradeCondition> conditionList = spBillGradeConditionDao.findList(query);
		if(conditionList==null||conditionList.isEmpty()){
			return config;
		}
		SpBillConfig billConfig=SpringHolder.getBean("spBillConfigService", ISpBillConfigService.class).getSpBillConfigLastVersion(config.getBillConfigId());
		List<XmlBillClass> classList = billConfig.getXmlBillClasses().getBillClasses();
//		Map<String, String> classNameMap=new HashMap<String, String>();
		Map<String, Map<Long, XmlBillProperty>> classPropertyMap=new HashMap<String, Map<Long, XmlBillProperty>>();
		for (SpBillGradeCondition spBillGradeCondition : conditionList) {
			String billClassId=spBillGradeCondition.getBillClassId();
			if(null==classPropertyMap.get(billClassId)){
				XmlBillClass billClass = null;
				for (XmlBillClass xmlBillClass : classList) {
					if(billClassId.equals(xmlBillClass.getId().toString())){
						billClass=xmlBillClass;
						break;
					}
				}
				if (null != billClass) {
//					classNameMap.put(billClassId, billClass.getName());
					Map<Long, XmlBillProperty> map = new HashMap<Long, XmlBillProperty>();
					for (XmlBillProperty p : billClass.getBillPropertys()) {
						map.put(p.getId(), p);
					}
					classPropertyMap.put(billClassId, map);
					spBillGradeCondition.fillProperties(map);
					config.getConditions().add(spBillGradeCondition);
				}
			}else{
				spBillGradeCondition.fillProperties(classPropertyMap.get(billClassId));
				config.getConditions().add(spBillGradeCondition);
			}
		}
		return config;
	}
	@Override
	public List<SpBillGradeConfig> findList(SpBillGradeConfig spBillGradeConfig) {
		return spBillGradeConfigDao.findList(spBillGradeConfig);
	}

	@Override
	public PageList<SpBillGradeConfig> findSpBillGradeConfigPageList(
			SpBillGradeConfigQuery query) {
		PageList<SpBillGradeConfig> pageList = new PageList<SpBillGradeConfig>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer) query.getToPage());

			paginator.setItems(spBillGradeConfigDao.getPagingCount(query));
			pageList.setPaginator(paginator);

			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SpBillGradeConfig> list = spBillGradeConfigDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public SpBillGradeConfig getById(String id) {
		SpBillGradeConfig query = new SpBillGradeConfig();
		query.setId(id);
		SpBillGradeConfig config = spBillGradeConfigDao.findById(query);
		if(config == null )return null;
		SpBillGradeCondition c = new SpBillGradeCondition();
		c.setConfigId(config.getId());
		config.setConditions(spBillGradeConditionDao.findList(c));
		return config;
	}

	@Override
	public void save(SpBillGradeConfig spBillGradeConfig) {
		spBillGradeConfigDao.insert(spBillGradeConfig);
	}

	@Override
	public void update(SpBillGradeConfig spBillGradeConfig) {
		spBillGradeConfigDao.update(spBillGradeConfig);
	}

	@Override
	public void deleteCondition(String id) {
		SpBillGradeCondition query = new SpBillGradeCondition();
		query.setId(id);
		spBillGradeConditionDao.delete(query);
	}

	@Override
	public PageList<SpBillGradeCondition> findSpBillGradeConditionPageList(
			SpBillGradeConditionQuery query) {
		PageList<SpBillGradeCondition> pageList = new PageList<SpBillGradeCondition>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer) query.getToPage());

			paginator.setItems(spBillGradeConditionDao.getPagingCount(query));
			pageList.setPaginator(paginator);

			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SpBillGradeCondition> list = spBillGradeConditionDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public SpBillGradeCondition getConditionById(String id) {
		SpBillGradeCondition query = new SpBillGradeCondition();
		query.setId(id);
		return spBillGradeConditionDao.findById(query);
	}

	@Override
	public void saveCondition(SpBillGradeCondition spBillGradeCondition) {
		spBillGradeConditionDao.insert(spBillGradeCondition);
	}

	@Override
	public void updateCondition(SpBillGradeCondition spBillGradeCondition) {
		spBillGradeConditionDao.update(spBillGradeCondition);
	}
	
	/**
	 * 设置
	 * @param spBillGradeConfigDao 
	 */
	public void setSpBillGradeConfigDao(ISpBillGradeConfigDao spBillGradeConfigDao) {
		this.spBillGradeConfigDao = spBillGradeConfigDao;
	}

	/**
	 * 设置
	 * @param spBillGradeConditionDao 
	 */
	public void setSpBillGradeConditionDao(
			ISpBillGradeConditionDao spBillGradeConditionDao) {
		this.spBillGradeConditionDao = spBillGradeConditionDao;
	}


}
