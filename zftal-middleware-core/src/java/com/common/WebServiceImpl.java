package com.common;

import javax.jws.WebService;
import javax.xml.rpc.ServiceException;


import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * webservice 
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2010-9-30下午04:28:53
 * @author:xxh
 * @version 1.0
 */
@WebService  
public class WebServiceImpl implements IWebService{
	private static WebServiceImpl instance = new WebServiceImpl();
	private static Service service = new Service();
	
	
	private WebServiceImpl(){
		
	}
	static{
		if(service==null) service = new Service();
	}
	
	/**
	 * <p>Description:获取webservice的call对象 </p>
		
	 * @since 2010-10-8 上午11:56:15
	 * @author:xxh
	 * @version 1.0
	 */
	public Call getCall() {
		try {
			return (Call)WebServiceImpl.getService().createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Autowired
	public static WebServiceImpl getInstance() {
		return instance;
	}

	
	
	public static Service getService() {
		if(service==null) service = new Service();
		return service;
	}
	public static void setService(Service service) {
		WebServiceImpl.service = service;
	}
}
