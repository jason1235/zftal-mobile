package com.zfsoft.hrm.dybill;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.ScanStyleType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;

public class SpBillConfigServiceTest extends BaseTxTestCase{
	
	private ISpBillConfigService spBillConfigService;
	
	@Before
	public void onSetup(){
		spBillConfigService=(ISpBillConfigService)this.applicationContext.getBean("spBillConfigService");
	}
	@Test
	@Rollback(false)
	public void billConfig(){
		//纯引用信息类
		SpBillConfig spBillConfig=new SpBillConfig();
		spBillConfig.setName("测试");
		spBillConfig.setStatus(BillConfigStatus.INITIALIZE);
		spBillConfigService.addSpBillConfig(spBillConfig);
		XmlBillClass xmBillClass=new XmlBillClass();
		xmBillClass.setId(System.currentTimeMillis());
		xmBillClass.setAppend(false);
		xmBillClass.setClassId("O14CAAC75489CDB5E040007F01001AC3");
		xmBillClass.setColNum(3);
		xmBillClass.setMaxLength(1);
		xmBillClass.setMinLength(1);
		xmBillClass.setScanStyle(ScanStyleType.TILE);//平铺模式
		spBillConfigService.addXmlBillClass(spBillConfig.getId(), xmBillClass);
		XmlBillProperty xmlBillProperty=new XmlBillProperty();
		xmlBillProperty.setId(System.currentTimeMillis());
		xmlBillProperty.setPropertyId("C2F43D63DEF27BD0E040007F01006041");
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(),
				xmBillClass.getId(), xmlBillProperty);
		xmlBillProperty=new XmlBillProperty();
		xmlBillProperty.setId(System.currentTimeMillis());
		xmlBillProperty.setPropertyId("C19D00A3C3F4A780E040007F01005C07");
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(), 
				xmBillClass.getId(), xmlBillProperty);
		
		//引用信息类和自增，自增的东西必需和当前引用的信息类规则一致
		xmBillClass=new XmlBillClass();
		xmBillClass.setId(System.currentTimeMillis());
		xmBillClass.setAppend(true);
		xmBillClass.setClassId("C393FE11C4DC8E46E040007F01003F39");
		xmBillClass.setColNum(3);
		xmBillClass.setMaxLength(1);
		xmBillClass.setMinLength(1);
		xmBillClass.setScanStyle(ScanStyleType.TILE);//平铺模式
		spBillConfigService.addXmlBillClass(spBillConfig.getId(), xmBillClass);
		
		xmlBillProperty=new XmlBillProperty();
		xmlBillProperty.setId(System.currentTimeMillis());
		xmlBillProperty.setPropertyId("C3940E8564094326E040007F01003F3B");
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(),
				xmBillClass.getId(), xmlBillProperty);
		xmlBillProperty=new XmlBillProperty();
		xmlBillProperty.setId(System.currentTimeMillis());
		xmlBillProperty.setPropertyId("C3940E85640C4326E040007F01003F3B");
		xmlBillProperty.setEditable(false);
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(),
				xmBillClass.getId(), xmlBillProperty);
		
		//纯自增，字段规则自定义
		xmBillClass=new XmlBillClass();
		xmBillClass.setId(System.currentTimeMillis());
		xmBillClass.setAppend(true);
		xmBillClass.setIdentityName("self_test");
		xmBillClass.setName("自定义表单");
		xmBillClass.setColNum(3);
		xmBillClass.setMaxLength(7);
		xmBillClass.setMinLength(1);
		xmBillClass.setScanStyle(ScanStyleType.LIST);//列表模式
		spBillConfigService.addXmlBillClass(spBillConfig.getId(), xmBillClass);
		
		xmlBillProperty=new XmlBillProperty();
		xmlBillProperty.setId(System.currentTimeMillis());
		xmlBillProperty.setFieldType(Type.COMMON);
		xmlBillProperty.setFieldName("self_f_name");
		xmlBillProperty.setName("姓名");
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(),
				xmBillClass.getId(), xmlBillProperty);
		xmlBillProperty=new XmlBillProperty();
		xmlBillProperty.setId(System.currentTimeMillis());
		xmlBillProperty.setFieldType(Type.DATE);
		xmlBillProperty.setFieldName("self_f_date");
		xmlBillProperty.setName("初始日期");
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(),
				xmBillClass.getId(), xmlBillProperty);
		
		
	}
}
