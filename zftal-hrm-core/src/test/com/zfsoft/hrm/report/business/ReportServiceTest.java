package com.zfsoft.hrm.report.business;

import net.sf.json.JSONObject;

import org.junit.Test;


import base.BaseTxTestCase;

import com.zfsoft.hrm.report.entity.ReportView;


public class ReportServiceTest extends BaseTxTestCase{
	
	@Test
	public void ss(){
		IReportXmlFileBusiness reportXmlFileBusiness=(IReportXmlFileBusiness)this.applicationContext.getBean("reportXmlFileBusiness");
		
		ReportView reportView=reportXmlFileBusiness.getView("1","");
		
		JSONObject json=JSONObject.fromObject(reportView);
		
		System.out.println(json.toString());
	}
}
