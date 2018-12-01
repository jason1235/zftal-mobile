package com.dsf.news.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IDsfXMLService {
	/**
	 * <p>
	 * Description: JCMS系统中网站的信息
	 * </p>
	 * 
	 * @param strWebIds
	 *            网站id，如果有多个以逗号分隔；如果为NULL或空，返回所有网站
	 * @param strLoginId
	 *            JCMS系统提供给第三方系统调用接口时检验的登陆名；
	 * @param strPwd
	 *            JCMS系统提供给第三方系统调用接口时检验的密码；如果加密种子为空，则视为明文；反之视其为密文
	 * @param strKey
	 *            MD5加密种子，建议取当前时间，可以为空，长度不可超过20
	 * @return
	 * 
	 * @since 2014-12-22 下午02:52:15
	 * @author yangz
	 */
	@WebMethod
	public String getWsGetWeb(@WebParam(name = "strWebIds") String strWebIds,
			@WebParam(name = "strLoginId") String strLoginId,
			@WebParam(name = "strPwd") String strPwd,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);

	/**
	 * <p>
	 * Description: 第三方系统提供JCMS系统中指定网站下的栏目信息
	 * </p>
	 * 
	 * @param strWebId
	 *            政府信息公开系统中节点id，只能是一个且不可为空
	 * @param strLoginId
	 *            CMS系统提供给第三方系统调用接口时检验的登陆名
	 * @param strPwd
	 *            JCMS系统提供给第三方系统调用接口时检验的密码；如果加密种子为空，则视为明文；反之视其为密文
	 * @return
	 * 
	 * @since 2014-12-23 上午09:24:19
	 * @author yangz
	 */
	@WebMethod
	public String getWsGetColumn(@WebParam(name = "strWebId") String strWebId,
			@WebParam(name = "strLoginId") String strLoginId,
			@WebParam(name = "strPwd") String strPwd,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken);
	

	/**   
	 * <p>Description:        返回指定栏目下的信息（每次返回最多不超过20条）</p>
	 * @param nCataId         栏目id
	 * @param bRef            0=排除引用信息 1=不排除引用信息
	 * @param iBase64         0=信息内容用明文；1=信息内容用Base64编码
	 * @param nStart          记录起始值，必须小于结束值且大于0
	 * @param nEnd            记录结束值，必须大于起始值，且与起始值的差必须小于20，值必须大于0
	 * @param bAsc            信息按创建时间排序 0=降序 1=升序
	 * @param strStartCTime   创建时间的起始时间，可以为空
	 * @param strEndCTime     创建时间的结束时间，可以为空
	 * @param strLoginId      CMS系统提供给第三方的用户登陆名
	 * @param strPwd          登陆密码 如果加密种子为空，则视为明文；反之视其为密文
	 * @param strKey          MD5加密种子，建议取当前时间字符串，现在暂时为空
	 * @return
	 *
	 * @since 2014-12-23 上午09:45:12
	 * @author yangz
	 */
	@WebMethod
	public String getWsGetMultiInfos(
			@WebParam(name = "nCataId") String nCataId,
			@WebParam(name = "nStart") String nStart,
			@WebParam(name = "nEnd") String nEnd,
			@WebParam(name="apptoken") String apptoken
		);
	
	/**
	 * <p>Description: 返回指定栏目下的信息，包括标题、信息的文章页访问地址、作者、来源、创建时间，不包括内容、图片、附件（每次返回最多不超过50条）</p>
	 * @param nCataId         栏目id
	 * @param bRef            0=排除引用信息 1=不排除引用信息
	 * @param nStart          记录起始值，必须小于结束值且大于0
	 * @param nEnd            记录结束值，必须大于起始值，且与起始值的差必须小于20，值必须大于0
	 * @param bAsc            信息按创建时间排序 0=降序 1=升序
	 * @param strStartCTime   创建时间的起始时间，可以为空
	 * @param strEndCTime     创建时间的结束时间，可以为空
	 * @param strLoginId      JCMS系统提供给第三方的用户登陆名
	 * @param strPwd          登陆密码 如果加密种子为空，则视为明文；反之视其为密文
	 * @param strKey          MD5加密种子，建议取当前时间字符串，现在暂时为空
	 * @return
	 *
	 * @since 2014-12-23 上午10:10:22
	 * @author yangz
	 */
	@WebMethod
	public String getwsGetMultiInfosLink(
			@WebParam(name = "nCataId") String nCataId,
			@WebParam(name = "bRef") String bRef,
			@WebParam(name = "nStart") String nStart,
			@WebParam(name = "nEnd") String nEnd,
			@WebParam(name = "bAsc") String bAsc,
			@WebParam(name = "strStartCTime") String strStartCTime,
			@WebParam(name = "strEndCTime") String strEndCTime,
			@WebParam(name = "strLoginId") String strLoginId,
			@WebParam(name = "strPwd") String strPwd,
			@WebParam(name = "strKey") String strKey,
			@WebParam(name="apptoken") String apptoken
			);

}
