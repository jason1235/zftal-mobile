package com.backMh.schoolsights.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/** 
 * WebService接口定义类. 
 *  
 * 使用@WebService将接口中的所有方法输出为Web Service. 
 * 可用annotation对设置方法、参数和返回值在WSDL中的定义. 
 */  
@WebService  
public interface ISchoolSightsXMLService {

	/**
	 * <p>Description:获取所有校园风景分类列表 </p>
	 * @return
	 *
	 * @since Nov 6, 2012 11:17:29 AM
	 * @author huangzhaoxia
	 */
	public String getAllSchoolTypeList();

	/**
	 * <p>Description: 按校区  分页获取校园风景列表</p>
	 * @param tid
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 11:17:53 AM
	 * @author huangzhaoxia
	 */
	public String getTypeSchoolSightsPageList(@WebParam(name = "tid")  String tid,@WebParam(name = "start") String start,@WebParam(name = "size") String size);
	
	/**
	 * <p>Description: 按校区  分页获取校园风景列表(图片绝对地址)</p>
	 * @param tid
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 11:17:53 AM
	 * @author huangzhaoxia
	 */
	public String getTypeSchoolSightsPageListNew(@WebParam(name = "tid")  String tid,@WebParam(name = "start") String start,@WebParam(name = "size") String size);
	
}
