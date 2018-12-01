package com.backMh.introduce.service;

import javax.jws.WebParam;
import javax.jws.WebService;


/** 
 * WebService接口定义类. 
 *  
 * 使用@WebService将接口中的所有方法输出为Web Service. 
 * 可用annotation对设置方法、参数和返回值在WSDL中的定义. 
 */  
@WebService  
public interface ISchoolIntroduceXmlService {  

	/**
	 * <p>Description:获取简介根节点类型列表 </p>
	 * @return
	 *
	 * @since Nov 5, 2012 11:10:31 AM
	 * @author huangzhaoxia
	 */
	public String getRootIntroduceTypeList();

	/**
	 * <p>Description:获取父节点id的子简介类型列表 </p>
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Nov 5, 2012 11:10:49 AM
	 * @author huangzhaoxia
	 */
	public String getChildIntroduceTypeList(@WebParam(name = "id") String id,@WebParam(name = "start") String start,@WebParam(name = "size") String size);

	/**
	 * <p>Description: 获取某简介类型id的简介信息明细。</p>
	 * @param tid
	 * @return
	 *
	 * @since Nov 5, 2012 11:11:01 AM
	 * @author huangzhaoxia
	 */
	public String getIntroduceByTypeId(@WebParam(name = "tid") String tid);
	
	/**
	 * <p>Description:获取父节点id的子简介类型列表 （添加父节点）</p>
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Nov 5, 2012 11:10:49 AM
	 * @author huangzhaoxia
	 */
	public String getChildIntroduceTypeListAddPid(@WebParam(name = "id") String id,@WebParam(name = "start") String start,@WebParam(name = "size") String size);
	/**
	 * <p>Description:获取简介根节点类型列表 </p>
	 * @return
	 *
	 * @since Jan 18, 2013 4:08:25 PM
	 * @author huangzhaoxia
	 */
	public String getRootSchoolIntroduceTypeList();
	
	/**
	 * <p>Description: 获取简介类型下的内容标题列表</p>
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 *
	 * @since Jan 18, 2013 4:09:38 PM
	 * @author huangzhaoxia
	 */
	public String getSchoolIntroduceTitleList(@WebParam(name = "id") String id,@WebParam(name = "start") String start,@WebParam(name = "size") String size);
	
	/**
	 * <p>Description: 获取简介信息明细。</p>
	 * @param tid
	 * @return
	 *
	 * @since Jan 18, 2013 4:11:21 PM
	 * @author huangzhaoxia
	 */
	public String getSchoolIntroduceById(@WebParam(name = "tid") String tid);
	
}  
