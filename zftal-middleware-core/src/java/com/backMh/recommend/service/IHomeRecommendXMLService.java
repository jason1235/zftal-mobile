package com.backMh.recommend.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/** 
 * WebService接口定义类. 
 *  
 * 使用@WebService将接口中的所有方法输出为Web Service. 
 * 可用annotation对设置方法、参数和返回值在WSDL中的定义. 
 */  
@WebService
public interface IHomeRecommendXMLService {

	/**
	 * <p>Description: 获取门户推荐区信息分页</p>
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Nov 6, 2012 11:02:34 AM
	 * @author huangzhaoxia
	 */
	public String getMhRecommendPage(
			@WebParam(name = "start")String start,
			@WebParam(name = "size")String size,
			@WebParam(name="apptoken") String apptoken
			);

	/**
	 * <p>Description: 获取分页新闻列表</p>
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Nov 6, 2012 11:02:42 AM
	 * @author huangzhaoxia
	 */
	public String getMhRecommendList(
			@WebParam(name = "start")String start,
			@WebParam(name = "size")String size,
			@WebParam(name="apptoken") String apptoken
			);
	/**
	 * <p>Description: 根据Id查找对应的推荐信息</p>
	 * @param inroduceType_Id
	 * @return
	 *
	 * @since Apr 12, 2012 1:53:01 PM
	 * @author 
	 */
	public String getRecommendInfo(
			@WebParam(name = "id")String id,
			@WebParam(name="apptoken") String apptoken
			);
	
}
