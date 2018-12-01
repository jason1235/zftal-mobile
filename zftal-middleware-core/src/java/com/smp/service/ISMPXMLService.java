package com.smp.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * <p>Description: ZFSMP接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-2-12 下午01:49:18
 * @author yangz
 * @version 1.0
 */
@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface ISMPXMLService {
	
	
	/**
	 * <p>Description: 获取待审新闻</p>
	 * @param userName 用户名
	 * @param strkey   秘钥
	 * @return
	 *
	 * @since 2015-2-13 上午09:20:16
	 * @author yangz
	 */
	@WebMethod
	public String getReviewNews(@WebParam(name="userName") String userName,@WebParam(name="strkey") String strkey);
	
	
	
	/**
	 * <p>Description:  根据新闻ID获取新闻列表</p>
	 * @param xwbh      新闻编号
	 * @param userName  当前登陆用户名
	 * @param strkey    秘钥
	 * @return
	 *
	 * @since 2015-2-13 上午10:37:50
	 * @author yangz
	 */
	@WebMethod
	public String getNews(@WebParam(name="xwbh") String xwbh,@WebParam(name="userName") String userName,@WebParam(name="strkey") String strkey);
	
	
	/**
	 * <p>Description: 更新新闻状态</p>
	 * @param xwbh     新闻编号
	 * @param status   状态2:审核不通过 3：审核通过
	 * @param xwnr     新闻内容
	 * @param userName 用户名
	 * @param strkey   秘钥
	 * @return
	 *
	 * @since 2015-2-14 下午02:43:49
	 * @author yangz
	 */
	@WebMethod
	public String updateNews(@WebParam(name="xwbh") String xwbh,@WebParam(name="status") String status,@WebParam(name="xwnr") String xwnr,@WebParam(name="userName") String userName,@WebParam(name="strkey") String strkey);
	

}
