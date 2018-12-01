package com.zfsoft.hrm.dybill.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.util.VerifyUtil;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;
/**
 * 审批表单配置
 * @author Patrick Shen
 */
public class BillValueUtil {

	private ISpBillConfigService spBillConfigService;
	
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public List<XmlValueProperty> getValuesAndCheck(String billConfigId,Integer version,Long classId,
			HttpServletRequest request) {
		//取得数据库配置对象
		SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigByVersion(billConfigId, version);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = JaxbUtil.getObjectFromXml(spBillConfig.getContent(),
				XmlBillClasses.class);
		//解析失败则新建xml映射对象
		if(xmlBillClasses==null){
			xmlBillClasses = new XmlBillClasses();
		}
		XmlBillClass xmlBillClass=xmlBillClasses.getBillClassById(classId);
		
		XmlValueProperty xmlValueProperty;
		
		List<XmlValueProperty> xmlValueProperties=new ArrayList<XmlValueProperty>();
		String error="";
		for (XmlBillProperty xmlBillProperty : xmlBillClass.getBillPropertys()) {
			xmlValueProperty=new XmlValueProperty();
			xmlValueProperty.setBillPropertyId(xmlBillProperty.getId());
			String value=request.getParameter(xmlBillProperty.getFieldName());
			String verifyStr="";
			if(StringUtil.isEmpty(value)){
				if(xmlBillProperty.getRequired()){
					error+=xmlBillProperty.getName()+"不能为空;<br>";
				}
				continue;
			}else{
				verifyStr=VerifyUtil.parse(xmlBillProperty.getInfoProperty(), value);
				if(!StringUtil.isEmpty(verifyStr)){
					error+=verifyStr+";<br>";
				}else{
					xmlValueProperty.setValue(value);
				}
			}
			xmlValueProperties.add(xmlValueProperty);
		}
		
		if(!StringUtil.isEmpty(error)){
			throw new RuntimeException(error);
		}
		
		return xmlValueProperties;
	}

	public List<XmlValueProperty> getValuesAndCheck(String billConfigId,Integer version, Long classId,
			DynaBean dybean) {
		//取得数据库配置对象
		SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigByVersion(billConfigId, version);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = JaxbUtil.getObjectFromXml(spBillConfig.getContent(),
				XmlBillClasses.class);
		//解析失败则新建xml映射对象
		if(xmlBillClasses==null){
			xmlBillClasses = new XmlBillClasses();
		}
		XmlBillClass xmlBillClass=xmlBillClasses.getBillClassById(classId);
		
		XmlValueProperty xmlValueProperty;
		
		List<XmlValueProperty> xmlValueProperties=new ArrayList<XmlValueProperty>();
		
		for (XmlBillProperty xmlBillProperty : xmlBillClass.getBillPropertys()) {
			xmlValueProperty=new XmlValueProperty();
			xmlValueProperty.setBillPropertyId(xmlBillProperty.getId());
			Object value=dybean.getValue(xmlBillProperty.getFieldName());
			if(value instanceof Date){
				xmlValueProperty.setValue(TimeUtil.format((Date)value,xmlBillProperty.getInfoProperty().getTypeInfo().getFormat()));
			}else{
				xmlValueProperty.setValue(value==null?"":value.toString());
			}
			
			xmlValueProperties.add(xmlValueProperty);
		}
		
		return xmlValueProperties;
	}

	public List<XmlValueProperty> getValuesAndCheckLocal(String billConfigId, Integer version,Long classId,XmlValueEntity valueEntity,
			HttpServletRequest request) {
		//取得数据库配置对象
		SpBillConfig spBillConfig=spBillConfigService.getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = JaxbUtil.getObjectFromXml(spBillConfig.getContent(),
				XmlBillClasses.class);
		//解析失败则新建xml映射对象
		if(xmlBillClasses==null){
			xmlBillClasses = new XmlBillClasses();
		}
		XmlBillClass xmlBillClass=xmlBillClasses.getBillClassById(classId);
		
		XmlValueProperty xmlValueProperty;
		
		String error="";
		for (XmlBillProperty xmlBillProperty : xmlBillClass.getBillPropertys()) {
			xmlValueProperty=valueEntity.getValuePropertyById(xmlBillProperty.getId());
			if(xmlValueProperty==null){
				xmlValueProperty=new XmlValueProperty();
				xmlValueProperty.setBillPropertyId(xmlBillProperty.getId());
				valueEntity.getValueProperties().add(xmlValueProperty);
			}
			String value=request.getParameter(xmlBillProperty.getFieldName());
			String verifyStr="";
			if(StringUtil.isEmpty(value)){
				if(xmlBillProperty.getRequired()){
					error+=xmlBillProperty.getName()+"不能为空;<br>";
				}
				if("".equals(value)){
					xmlValueProperty.setNewValue("");
				}
				continue;
			}else{
				verifyStr=VerifyUtil.parse(xmlBillProperty.getInfoProperty(), value);
				if(!StringUtil.isEmpty(verifyStr)){
					error+=verifyStr+";<br>";
				}else{
					if(!value.equals(xmlValueProperty.getValue())){
						xmlValueProperty.setNewValue(value);
					}else{
						xmlValueProperty.setValue(value);
						xmlValueProperty.setNewValue(null);
					}
				}
			}
		}
		
		if(!StringUtil.isEmpty(error)){
			throw new RuntimeException(error);
		}
		
		return valueEntity.getValueProperties();
	}
	
}
