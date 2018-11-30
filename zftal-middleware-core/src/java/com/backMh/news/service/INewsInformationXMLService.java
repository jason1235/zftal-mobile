package com.backMh.news.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/** 
 * WebService接口定义类. 
 *  
 * 使用@WebService将接口中的所有方法输出为Web Service. 
 * 可用annotation对设置方法、参数和返回值在WSDL中的定义. 
 */  
@WebService 
public interface INewsInformationXMLService {

	/**
	 * <p>Description:获取所有新闻列表的xml值 </p>

	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:02:47 AM
	 * @author huangzhaoxia
	 */
	public String getIndexNewsList(
			@WebParam(name = "start") String start,
			@WebParam(name = "size") String size,
			@WebParam(name="apptoken") String apptoken
	);

	/**
	 * <p>Description:获取所有新闻类别列表的xml值 </p>
	 * @return
	 *
	 * @since Nov 6, 2012 10:02:58 AM
	 * @author huangzhaoxia
	 */
	public String getAllNewsTypeList(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description:获取id相应的新闻对象信息 </p>
	 * @param id
	 * @return
	 *
	 * @since Nov 6, 2012 10:03:14 AM
	 * @author huangzhaoxia
	 */
	public String getNewsInfo(@WebParam(name = "id") String id,@WebParam(name="apptoken") String apptoken);
	/**
	  * <p>Description:按资讯类别获取分页新闻列表 </p>

	 * @return
	 *
	 * @since Nov 6, 2012 10:03:24 AM
	 * @author huangzhaoxia
	 */
	public String getTypeNewsPageList(
			@WebParam(name = "newType") String newtype,
			@WebParam(name = "start") String start,
			@WebParam(name = "size") String size,
			@WebParam(name="apptoken") String apptoken
			);

	/**
	 * <p>Description:按资讯类别Id获取分页新闻列表 </p>
	 * @param newtypeid --资讯类别id
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:03:58 AM
	 * @author huangzhaoxia
	 */
	public String getTypeIdNewsPageList(
			@WebParam(name = "tid") String tid,
			@WebParam(name = "start") String start,
			@WebParam(name = "size") String size,
			@WebParam(name="apptoken") String apptoken
			);
	/**
	 * <p>Description:获取所有新闻列表的xml值(图片绝对地址) </p>

	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:02:47 AM
	 * @author huangzhaoxia
	 */
	public String getIndexNewsListNew(
			@WebParam(name = "start") String start,
			@WebParam(name = "size") String size,
			@WebParam(name="apptoken") String apptoken
		);
	/**
	  * <p>Description:按资讯类别获取分页新闻列表 (图片绝对地址)</p>

	 * @return
	 *
	 * @since Nov 6, 2012 10:03:24 AM
	 * @author huangzhaoxia
	 */
	public String getTypeNewsPageListNew(
			@WebParam(name = "newType") String newtype,
			@WebParam(name = "start") String start,
			@WebParam(name = "size") String size,
			@WebParam(name="apptoken") String apptoken
			);

	/**
	 * <p>Description:按资讯类别Id获取分页新闻列表 (图片绝对地址)</p>
	 * @param newtypeid --资讯类别id
	 * @param start --开始页
	 * @param size  --每页大小
	 * @return
	 *
	 * @since Nov 6, 2012 10:03:58 AM
	 * @author huangzhaoxia
	 */
	public String getTypeIdNewsPageListNew(
			@WebParam(name = "tid") String tid,
			@WebParam(name = "start") String start,
			@WebParam(name = "size") String size,
			@WebParam(name="apptoken") String apptoken
		);
	
}
