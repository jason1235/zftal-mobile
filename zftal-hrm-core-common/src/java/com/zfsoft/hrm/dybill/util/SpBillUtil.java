package com.zfsoft.hrm.dybill.util;

import java.util.ArrayList;

import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.enums.EntityType;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;

/**
 * 
 * @author ChenMinming
 * @date 2015-1-19
 * @version V1.0.0
 */
public class SpBillUtil {

	public static boolean modifyEntity(String className,String fieldName,String value,
			XmlValueClasses xmlValueClasses,SpBillConfig config){
		XmlBillClass billClass = config.getXmlBillClasses().getBillClassByFieldName(className);
		if(billClass==null) return false;
		XmlBillProperty billProperty = billClass.getBillPropertyByFieldName(fieldName);
		if(billProperty==null) return false;
		
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClass.getId());
		//空则初始化
		if (xmlValueClass == null) {
			xmlValueClass = new XmlValueClass();
			xmlValueClass.setBillClassId(billClass.getId());
			//加入到信息类值对象序列
			xmlValueClasses.getValueClasses().add(xmlValueClass);
		}
		//空则初始化
		if(xmlValueClass.getValueEntities()==null){
			xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
		}
		XmlValueEntity xmlValueEntity = null;
		if(billClass.getMaxLength()==1&&xmlValueClass.getValueEntities().size()==1){
			xmlValueEntity = xmlValueClass.getValueEntities().get(0);
		}else{
			xmlValueEntity = new XmlValueEntity();
			xmlValueEntity.setEntityType(EntityType.SELFADD);
			xmlValueEntity.setId(System.currentTimeMillis());
			xmlValueClass.getValueEntities().add(xmlValueEntity);
			xmlValueEntity.setValueProperties(new ArrayList<XmlValueProperty>());
		}
		XmlValueProperty p = xmlValueEntity.getValuePropertyById(billProperty.getId());
		if(p==null){
			p = new XmlValueProperty();
			p.setBillPropertyId(billProperty.getId());
			xmlValueEntity.getValueProperties().add(p);
		}
		p.setValue(value);
		return true;
	}
	
}
