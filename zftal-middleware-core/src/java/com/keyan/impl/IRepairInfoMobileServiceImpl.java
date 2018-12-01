package com.keyan.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.keyan.IRepairInfoMobileService;
import com.oa.service.imp.EmailInformationXMLService;
import com.util.WebServiceUtil;


@Service
@Component(value = "IRepairInfoMobileService")
public class IRepairInfoMobileServiceImpl implements IRepairInfoMobileService{

	private static Logger logger = Logger.getLogger(IRepairInfoMobileServiceImpl.class);
	
	
	@Override
	public String submitRepair(String arg0, String arg1, String arg2,
			String arg3, String arg4, String arg5, String arg6, String arg7,
			String arg8) {
		String str=null;
		try {
			str=WebServiceUtil.createServiceKeyan().submitRepair(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			logger.error("调用submitRepair返回为："+str);
		} catch (Exception e) {
			logger.error("submitRepair  -- error");
		}
		return str;
	}

	@Override
	public String getMobieRepairList(String arg0, String arg1, String arg2,
			String arg3) {
		String str=null;
		try {
			str=WebServiceUtil.createServiceKeyan().getMobieRepairList(arg0, arg1, arg2, arg3);
			logger.error("调用getMobieRepairList返回为："+str);
		} catch (Exception e) {
			logger.error("getMobieRepairList  -- error");
		}
		return str;
	}

	@Override
	public String getMobieRepairType(String arg0, String arg1) {
		String str=null;
		try {
			str=WebServiceUtil.createServiceKeyan().getMobieRepairType(arg0, arg1);
			logger.error("调用getMobieRepairType返回为："+str);
		} catch (Exception e) {
			logger.error("getMobieRepairType  -- error");
		}
		return str;
	}

	@Override
	public String getRepairAddress(String arg0, String arg1) {
		String str=null;
		try {
			str=WebServiceUtil.createServiceKeyan().getRepairAddress(arg0, arg1);
			logger.error("调用getRepairAddress返回为："+str);
		} catch (Exception e) {
			logger.error("getRepairAddress  -- error");
		}
		return str;
	}

}
