package com.zfsoft.hrm.dybill;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.enums.BillInstanceStatus;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;

public class SpBillInstanceServiceTest extends BaseTxTestCase{
	
	private ISpBillInstanceService spBillInstanceService;
	
	@Before
	public void onSetup(){
		spBillInstanceService=(ISpBillInstanceService)this.applicationContext.getBean("spBillInstanceService");
	}
	@Test
	@Rollback(false)
	public void billInstance(){
		SpBillInstance spBillInstance=new SpBillInstance();
		spBillInstance.setStatus(BillInstanceStatus.INITIALIZE);
		spBillInstance.setBillConfigId("D9720D0E867E0A24E040007F010025E4");
		spBillInstanceService.addSpBillInstance(spBillInstance);
		
		List<XmlValueProperty> values=new ArrayList<XmlValueProperty>();
		XmlValueProperty valueProperty=new XmlValueProperty();
		valueProperty.setBillPropertyId(1364978850505L);
		valueProperty.setValue("super man");
		values.add(valueProperty);
		valueProperty=new XmlValueProperty();
		valueProperty.setBillPropertyId(1364978850583L);
		valueProperty.setValue("male");
		values.add(valueProperty);
		spBillInstanceService.addXmlValueInstances(spBillInstance.getBillConfigId(),
				spBillInstance.getId(), 1364977907945L, values);
		
		values=new ArrayList<XmlValueProperty>();
		valueProperty=new XmlValueProperty();
		valueProperty.setBillPropertyId(1364978850739L);
		valueProperty.setValue("super man");
		values.add(valueProperty);
		valueProperty=new XmlValueProperty();
		valueProperty.setBillPropertyId(1364978850817L);
		valueProperty.setValue("male");
		values.add(valueProperty);
		spBillInstanceService.addXmlValueInstances(spBillInstance.getBillConfigId(),
				spBillInstance.getId(), 1364978850661L, values);
		
		values=new ArrayList<XmlValueProperty>();
		valueProperty=new XmlValueProperty();
		valueProperty.setBillPropertyId(1364978850985L);
		valueProperty.setValue("super man");
		values.add(valueProperty);
		valueProperty=new XmlValueProperty();
		valueProperty.setBillPropertyId(1364978851078L);
		valueProperty.setValue("2013-04-30");
		values.add(valueProperty);
		spBillInstanceService.addXmlValueInstances(spBillInstance.getBillConfigId(),
				spBillInstance.getId(), 1364978850902L, values);
		spBillInstanceService.addXmlValueInstances(spBillInstance.getBillConfigId(),
				spBillInstance.getId(), 1364978850902L, values);
	}
}
