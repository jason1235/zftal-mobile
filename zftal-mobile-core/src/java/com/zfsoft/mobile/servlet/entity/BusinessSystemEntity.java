package com.zfsoft.mobile.servlet.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BusinessSystemEntity {

	public String id;
	public String systemCode;
	public String systemName;
	public String procode;
	public String otherFlag;
	public List<ServiceEntity> serviceEntityList;
	@Override
	public String toString() {
		return "BusinessSystemEntity [id=" + id + ", systemCode=" + systemCode
				+ ", systemName=" + systemName + ", procode=" + procode
				+ ", otherFlag=" + otherFlag + ", serviceEntityList="
				+ serviceEntityList + "]";
	} 
	
	public static void main(String[] args) {
		BusinessSystemEntity entity = new BusinessSystemEntity();
		entity.id="123";
		ServiceEntity serviceEntity = new ServiceEntity();
		serviceEntity.androidUrl="456";
		entity.serviceEntityList = new ArrayList<ServiceEntity>();
		entity.serviceEntityList.add(serviceEntity);
		ServiceEntity serviceEntity2 = new ServiceEntity();
		serviceEntity2.androidUrl="456";
		entity.serviceEntityList.add(serviceEntity2);
		System.out.println(ReflectionToStringBuilder.toString(entity));
	}
	
	
}
