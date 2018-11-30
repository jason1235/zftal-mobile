package com.newBackMh.noticelisttype.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IBackMhNoticeListTypeXMLService {
	/**
	 * <p>Description: 通知公告类别</p>
	 * @return
	 *
	 * @since 2014-11-10 下午02:21:02
	 * @author 阳章
	 */
	@WebMethod
	public String getMobileBackMhNoticeListType(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 通知公告新类别</p>
	 * @param userName
	 * @param role
	 * @param bak
	 * @param sign
	 * @return
	 *
	 * @since 2015-2-14 上午10:24:07
	 * @author yangz
	 */
	@WebMethod
	public String getMobileBackMhNewNoticeListType(@WebParam(name = "userName") String userName,@WebParam(name = "role") String role,@WebParam(name = "bak") String bak,@WebParam(name = "sign") String sign,@WebParam(name="apptoken") String apptoken);
	
	

	/**
	 * <p>Description: 通知公告新列表类别</p>
	 * @param userName
	 * @param role
	 * @param parentTypeId
	 * @param bak
	 * @param sign
	 * @return
	 *
	 * @since 2015-2-14 上午10:25:56
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeListNewTableType(@WebParam(name = "userName") String userName,@WebParam(name = "role") String role,@WebParam(name = "parentTypeId") String parentTypeId,@WebParam(name = "bak") String bak,@WebParam(name = "sign") String sign,@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 通知公告列表类别</p>
	 * @return
	 *
	 * @since 2015-2-14 上午10:32:35
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeListTableType(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 通知公告教务类别</p>
	 * @return
	 *
	 * @since 2014-11-11 上午11:11:39
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeJwType(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 通知公告学工类别</p>
	 * @return
	 *
	 * @since 2014-11-12 上午10:42:47
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeXgType(@WebParam(name="apptoken") String apptoken);
	
	
	/**
	 * <p>Description: 通知公告迎新类型</p>
	 * @return
	 *
	 * @since 2014-11-14 上午09:43:24
	 * @author yangz
	 */
	@WebMethod
	public String getNoticeYxType(@WebParam(name="apptoken") String apptoken);
	
	/**
	 * <p>Description: 通知公告离校类型</p>
	 * @return
	 *
	 * @since 2014-11-14 上午09:43:43
	 * @author yangjinlian
	 */
	@WebMethod
	public String getNoticeLxType(@WebParam(name="apptoken") String apptoken);

}
