package com.zfsoft.hrm.baseinfo.forminfo.service.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoType;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoMetadataService;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoTypeService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;

public class FormInfoServiceTest extends BaseTxTestCase{
	private IFormInfoTypeService formInfoTypeService;
	private IFormInfoMetadataService formInfoMetadataService;
	@Before
	public void setUp(){
		formInfoTypeService=(IFormInfoTypeService)this.applicationContext.getBean("formInfoTypeService");
		formInfoMetadataService=(IFormInfoMetadataService)this.applicationContext.getBean("formInfoMetadataService");
	}
	/**
	 * 增加修改删除测试
	 */
	@Test
	public void opFormInfoType(){
		
		FormInfoType model=new FormInfoType();
		model.setName("sssss2");
		formInfoTypeService.add(model);
		
		Assert.assertEquals(model.getName(),formInfoTypeService.getByGuid(model.getGuid()).getName());
		
		model.setName("sssss3");
		formInfoTypeService.modify(model);
		Assert.assertEquals(model.getName(),formInfoTypeService.getByGuid(model.getGuid()).getName());
		
		formInfoTypeService.remove(model.getGuid());
		Assert.assertEquals(null,formInfoTypeService.getByGuid(model.getGuid()));
	}
	
	@Test
	public void opFormInfoMetadata(){
		
		FormInfoType formInfoType=new FormInfoType();
		formInfoType.setName("sssss2");
		formInfoTypeService.add(formInfoType);
		
		
		FormInfoMetadata model= new FormInfoMetadata();
		model.setFormInfoTypeId(formInfoType.getGuid());
		model.setInfoProperty(InfoClassCache.getInfoClass("A13D53CE89309412E040007F01004732").getPropertyByName("staffid"));
		model.setEditable(false);
		model.setNeed(false);
		model.setViewable(true);
		model.setDefaultValue("");
		formInfoMetadataService.add(model);
		model=formInfoMetadataService.getByGuid(model.getGuid());
		model.setEditable(true);
		model.setDefaultValue("2");
		formInfoMetadataService.modify(model);
		formInfoMetadataService.getByInfoClassOfType(formInfoType.getGuid(), model.getInfoProperty().getClassId());
		formInfoMetadataService.remove(model.getGuid());
	}
	
	public void selectFormInfoType(){
		
	}
	
}
