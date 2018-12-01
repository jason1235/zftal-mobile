package com.zfsoft.hrm.baseinfo.forminfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface.IFormInfoMetadataDao;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoMetadataService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
/**
 * 登记类元数据service
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoMetadataServiceImpl implements IFormInfoMetadataService{

	private IFormInfoMetadataDao formInfoMetadataDao;
	

	@Override
	public void add(FormInfoMetadata model) {
		formInfoMetadataDao.insert(model);
	}
	@Override
	public void add(String classId, String typeId, String... propNames) {
		FormInfoMetadata formInfoMetadata;
		InfoProperty infoProperty;
		for(String propName:propNames){
			formInfoMetadata=new FormInfoMetadata();
			formInfoMetadata.setFormInfoTypeId(typeId);
			infoProperty=InfoClassCache.getInfoClass(classId).getPropertyByName(propName);
			formInfoMetadata.setInfoProperty(infoProperty);
			formInfoMetadata.copyInfoPropertyValueToThis();
			formInfoMetadataDao.insert(formInfoMetadata);
			
		}		
	}
	@Override
	public void batchAdd(List<FormInfoMetadata> models) {
		for(FormInfoMetadata formInfoMetadata:models)
			formInfoMetadataDao.insert(formInfoMetadata);
	}

	@Override
	public void modify(FormInfoMetadata model) {
		
		formInfoMetadataDao.update(model);
	}

	@Override
	public void remove(String guid) {
		formInfoMetadataDao.delete(guid);
	}

	@Override
	public FormInfoMetadata getByGuid(String guid) {
		FormInfoMetadata model=formInfoMetadataDao.findByGuid(guid);
		model.setInfoProperty(InfoClassCache.getInfoClass(model.getInfoProperty().getClassId())
				.getPropertyByName(model.getInfoProperty().getFieldName()));
		model.copyToValueInfoProperty();
		return model;
	}
	
	@Override
	public List<FormInfoMetadata> getByInfoClassOfType(String formInfoTypeId,String infoClassId) {
		List<FormInfoMetadata> result = new ArrayList<FormInfoMetadata>();
		
		List<FormInfoMetadata> metadatas = formInfoMetadataDao.findByInfoClassOfType(formInfoTypeId,infoClassId);
		
		Map<String, FormInfoMetadata> map = new HashMap<String, FormInfoMetadata>();

		for (FormInfoMetadata metadata : metadatas) {
			map.put( metadata.getInfoProperty().getGuid(), metadata);
		}
		
		InfoClass clazz = InfoClassCache.getInfoClass( infoClassId );
		FormInfoMetadata metadata;
		for ( InfoProperty property : clazz.getProperties() ) {
			if( map.get( property.getGuid() ) != null ) {
				metadata=map.get(property.getGuid());
				metadata.setInfoProperty(property);
				metadata.copyToValueInfoProperty();
				metadata.setChecked(true);
			}else{
				metadata = new FormInfoMetadata();
				metadata.setFormInfoTypeId(formInfoTypeId);
				metadata.setInfoProperty(property);
			}
			
			result.add(metadata);
		}
		
		return result;
	}
	
	@Override
	public List<FormInfoMetadata> getByFormInfoTypeId(String formInfoTypeId) {
		return formInfoMetadataDao.findByFormInfoTypeId(formInfoTypeId);
	}
	
	public void setFormInfoMetadataDao(IFormInfoMetadataDao formInfoMetadataDao) {
		this.formInfoMetadataDao = formInfoMetadataDao;
	}
}
 