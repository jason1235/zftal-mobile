package com.zfsoft.hrm.baseinfo.forminfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface.IFormInfoMetadataDao;
import com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface.IFormInfoTypeDao;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoClass;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoType;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoTypeService;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
/**
 * 登记类service
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoTypeServiceImpl implements IFormInfoTypeService{
	
	private IFormInfoTypeDao formInfoTypeDao;
	private IFormInfoMetadataDao formInfoMetadataDao;
	private IMenuBusiness menuBusiness;

	public void setFormInfoMetadataDao(IFormInfoMetadataDao formInfoMetadataDao) {
		this.formInfoMetadataDao = formInfoMetadataDao;
	}

	public void setFormInfoTypeDao(IFormInfoTypeDao formInfoTypeDao) {
		this.formInfoTypeDao = formInfoTypeDao;
	}

	@Override
	public void add(FormInfoType model) {
		if(model==null)return;//保证model非空
		model.setName(model.getName().trim());//去空格
		model.setSeq(getList().size()+1);
		try{
			formInfoTypeDao.insert(model);//插入
		}catch(DuplicateKeyException e){//捕获唯一异常
			throw new RuntimeException("已存在相同的登记表名称！");
		}
		IndexModel parentModel = menuBusiness.getById(IConstants.FORMINFO_ROOT_MENU);
		if(parentModel == null){
			throw new RuleException("上级菜单不存在，操作终止");
		}
		IndexModel indexModel = new IndexModel();
		indexModel.setFjgndm(parentModel.getGnmkdm());
		indexModel.setGnmkmc(model.getName());
		indexModel.setDyym("/baseinfo/forminfoutil_list.html?newSession=true&formInfoTypeId="+model.getGuid());
		indexModel.setIsAuto("1");
		menuBusiness.addMenu(indexModel);
		indexModel.setXssx(model.getSeq()+"");
		menuBusiness.modify(indexModel);
	}

	@Override
	public void modify(FormInfoType model) {
		FormInfoType old = formInfoTypeDao.findByGuid(model.getGuid());
		try{
			formInfoTypeDao.update(model);
		}catch(DuplicateKeyException e){//捕获唯一异常
			throw new RuntimeException("已存在相同的登记表名称！");
		}
		if(!model.getName().equals(old.getName())){
			IndexModel indexModel = new IndexModel();
			indexModel.setFjgndm(IConstants.FORMINFO_ROOT_MENU);
			indexModel.setGnmkmc(old.getName());
			indexModel = menuBusiness.getByName(indexModel);
			indexModel.setGnmkmc(model.getName());
			menuBusiness.modify(indexModel);
		}
	}

	@Override
	public void remove(String guid) {
		FormInfoType model = formInfoTypeDao.findByGuid(guid);
		if(model == null) return;
		List<FormInfoMetadata> list = formInfoMetadataDao.findByFormInfoTypeId(guid);
		for (FormInfoMetadata formInfoMetadata : list) {
			formInfoMetadataDao.delete(formInfoMetadata.getGuid());
		}
		formInfoTypeDao.delete(guid);
		IMenuBusiness menuBusiness = SpringHolder.getBean("menuBusiness",IMenuBusiness.class);
		IndexModel indexModel = new IndexModel();
		indexModel.setFjgndm(IConstants.FORMINFO_ROOT_MENU);
		indexModel.setGnmkmc(model.getName());
		indexModel = menuBusiness.getByName(indexModel);
		if(indexModel == null) return;
		menuBusiness.remove(indexModel.getGnmkdm());
		
	}

	@Override
	public List<FormInfoType> getList() {
		return formInfoTypeDao.findList();
	}

	@Override
	public FormInfoType getByGuid(String guid) {
		return formInfoTypeDao.findByGuid(guid);
	}
	
	@Override
	public FormInfoClass getFormInfoClass( String guid,List<InfoClass> infoClazzes ) {
		FormInfoClass fc=new FormInfoClass();
		fc.setFormInfoTypeId(guid);
		
		for( InfoClass infoclass : infoClazzes ) {
			infoclass=infoclass.clone();
			List<FormInfoMetadata> metadatas = formInfoMetadataDao.findByInfoClassOfType(guid,infoclass.getGuid());
			
			if(metadatas==null||metadatas.size()==0){
				infoclass.setProperties(new ArrayList<InfoProperty>());
				fc.getInfoClazzes().add(infoclass);
				continue;
			}
			
			List<InfoProperty> propertys=new ArrayList<InfoProperty>();
			
			for(FormInfoMetadata metadata:metadatas){
				propertys.add( infoclass.getPropertyByName( metadata.getInfoProperty().getFieldName() ) );
			}
			
			infoclass.setProperties(propertys);
			fc.getInfoClazzes().add(infoclass);
		}
		
		return fc;
	}

	/**
	 * 设置
	 * @param menuBusiness 
	 */
	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}
}
