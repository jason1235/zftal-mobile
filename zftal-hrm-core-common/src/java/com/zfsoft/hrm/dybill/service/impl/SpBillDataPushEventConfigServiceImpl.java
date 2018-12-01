package com.zfsoft.hrm.dybill.service.impl;

import java.util.List;

import com.zfsoft.hrm.dybill.dao.IPushTaskDao;
import com.zfsoft.hrm.dybill.dao.ISpBillDataPushEventConfigDao;
import com.zfsoft.hrm.dybill.dao.ISpBillDataPushPropertyDao;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushEventConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushProperty;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushEventConfigService;
/**
 * 数据推送配置服务
 * @className: SpBillDataPushConfigServiceImpl 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-9 下午02:22:11
 */
public class SpBillDataPushEventConfigServiceImpl implements ISpBillDataPushEventConfigService{

	private ISpBillDataPushEventConfigDao spBillDataPushEventConfigDao;
	private ISpBillDataPushPropertyDao spBillDataPushPropertyDao;
	private IPushTaskDao pushTaskDao;
	@Override
	public List<SpBillDataPushEventConfig> getList(
			SpBillDataPushEventConfig query) {
		return spBillDataPushEventConfigDao.findList(query);
	}

	@Override
	public SpBillDataPushEventConfig getById(String id) {
		SpBillDataPushEventConfig query=new SpBillDataPushEventConfig();
		query.setId(id);
		return spBillDataPushEventConfigDao.findById(query);
	}

	@Override
	public List<SpBillDataPushEventConfig> getByBillClassId(Long billClassId) {
		SpBillDataPushEventConfig query=new SpBillDataPushEventConfig();
		query.setBillClassId(billClassId);
		return spBillDataPushEventConfigDao.findList(query);
	}

	@Override
	public void addDataPushEvent(SpBillDataPushEventConfig config) {
		spBillDataPushEventConfigDao.insert(config);
		pushTaskDao.insert(config);
	}

	@Override
	public void modifyDataPushEvent(SpBillDataPushEventConfig config) {
		spBillDataPushEventConfigDao.update(config);
		pushTaskDao.update(config);
	}

	@Override
	public void removeDataPushEvent(String id) {
		SpBillDataPushEventConfig query=getById(id);
		if(query!=null){
			spBillDataPushEventConfigDao.delete(query);
			pushTaskDao.delete(query);
		}
	}

	@Override
	public void removeDataPushEvents(Long billClassId) {
		for(SpBillDataPushEventConfig query: getByBillClassId(billClassId)){
			spBillDataPushEventConfigDao.delete(query);
			pushTaskDao.delete(query);
		}
	}

	@Override
	public SpBillDataPushProperty getPropertyById(String propertyId) {
		SpBillDataPushProperty query=new SpBillDataPushProperty();
		query.setId(propertyId);
		return spBillDataPushPropertyDao.findById(query);
	}

	@Override
	public List<SpBillDataPushProperty> getPropertyByConfigId(String configId) {
		SpBillDataPushProperty query=new SpBillDataPushProperty();
		query.setEventConfigId(configId);
		return spBillDataPushPropertyDao.findList(query);
	}

	@Override
	public void addDataPushProperty(SpBillDataPushProperty property) {
		spBillDataPushPropertyDao.insert(property);
	}

	@Override
	public void modifyDataPushProperty(SpBillDataPushProperty property) {
		spBillDataPushPropertyDao.update(property);
	}

	@Override
	public void removeDataPushProperty(String propertyId) {
		SpBillDataPushProperty query=getPropertyById(propertyId);
		if(query!=null){
			spBillDataPushPropertyDao.delete(query);
		}
	}

	@Override
	public void removeDataPushPropertys(String configId) {
		for(SpBillDataPushProperty property:getPropertyByConfigId(configId)){
			spBillDataPushPropertyDao.delete(property);
		}
	}

	public void setSpBillDataPushEventConfigDao(
			ISpBillDataPushEventConfigDao spBillDataPushEventConfigDao) {
		this.spBillDataPushEventConfigDao = spBillDataPushEventConfigDao;
	}

	public void setSpBillDataPushPropertyDao(
			ISpBillDataPushPropertyDao spBillDataPushPropertyDao) {
		this.spBillDataPushPropertyDao = spBillDataPushPropertyDao;
	}

	public void setPushTaskDao(IPushTaskDao pushTaskDao) {
		this.pushTaskDao = pushTaskDao;
	}

	
}
