package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoClassDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

public class InfoClassServiceTest extends BaseTxTestCase{
	InfoClassServiceImpl infoClassService;
	IInfoClassDao iInfoClassDao;
	@Before
	public void setUp(){
		infoClassService=(InfoClassServiceImpl)this.applicationContext.getBean("baseInfoClassService");
		iInfoClassDao=(IInfoClassDao)this.applicationContext.getBean("baseInfoClassDao");
	}
//	@Test
//	@Rollback
	public void addInfoClass(){
		InfoClass infoClass=new InfoClass();
		Catalog catalog=new Catalog();
		infoClass.setCatalog(catalog);
		infoClass.setGuid("");
		infoClass.setIdentityName("");
		infoClass.setIndex(1);
		infoClass.setLessThanOne(false);
		infoClass.setProperties(new ArrayList<InfoProperty>());
		sameNameException(infoClass);
	}
	
	public void sameNameException(InfoClass infoClass){
		try{
//			infoClassService.add(infoClass);
			Assert.fail("未抛出\"已存在相同的标识名！\"异常");
		}catch(RuntimeException e){
			Assert.assertEquals("已存在相同的标识名！", e.getMessage());
		}
	}
	@Test
	public void alertCol(){
//		iInfoClassDao.alertTable("");
	}
}
