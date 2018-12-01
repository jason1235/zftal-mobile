package com.backMh.notice.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/** 
 * WebService接口定义类. 
 *  
 * 使用@WebService将接口中的所有方法输出为Web Service. 
 * 可用annotation对设置方法、参数和返回值在WSDL中的定义. 
 */  
@WebService 
public interface INoticeTypeXmlService {

	/**
	 * <p>Description:获取首页标题类型的通知公告列表的xml值 </p>
	 * @param topCount
	 * @return
	 *
	 * @since Nov 6, 2012 10:26:14 AM
	 * @author huangzhaoxia
	 */
	public String getIndexNoticeListXml(
			@WebParam(name = "topCount") String topCount,
			@WebParam(name="apptoken") String apptoken
			);

	/**
	 * <p>Description:根据通知公告的id或者通知公告对象信息 </p>
	 * @param id
	 * @return
	 *
	 * @since Nov 6, 2012 10:26:23 AM
	 * @author huangzhaoxia
	 */
	public String getNoticeInfo(
			@WebParam(name = "id")  String id,
			@WebParam(name="apptoken") String apptoken
			);
  

}
