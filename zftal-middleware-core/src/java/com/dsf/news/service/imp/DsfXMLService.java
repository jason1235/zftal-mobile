package com.dsf.news.service.imp;


import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.common.backMhConfig;
import com.dsf.news.service.IDsfXMLService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;

/**
 * <p>Description: 第三方新闻接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-12-23 上午09:48:04
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "dsfXMLService")
public class DsfXMLService implements IDsfXMLService{
	private static Logger logger = Logger.getLogger(DsfXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description: JCMS系统中网站的信息</p>
	 * @param strWebIds  网站id，如果有多个以逗号分隔；如果为NULL或空，返回所有网站
	 * @param strLoginId JCMS系统提供给第三方系统调用接口时检验的登陆名；
	 * @param strPwd     JCMS系统提供给第三方系统调用接口时检验的密码；如果加密种子为空，则视为明文；反之视其为密文
	 * @param strKey     MD5加密种子，建议取当前时间，可以为空，长度不可超过20
	 * @return
	 *
	 * @since 2014-12-22 下午02:52:15
	 * @author yangz
	 */
	@Override
	public String getWsGetWeb(String strWebIds, String strLoginId, String strPwd,
			String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			strWebIds  		= CodeUtil.decode(strWebIds, apptoken);
			strLoginId  	= CodeUtil.decode(strLoginId, apptoken);
			strPwd  		= CodeUtil.decode(strPwd, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// TODO JCMS系统中网站的信息 
		strLoginId=Config.getString("webservice.host.dsf.xw.username");
		strPwd=Config.getString("webservice.host.dsf.xw.password");
		strWebIds=Config.getString("webservice.host.dsf.xw.webid");
		if(infromation.equals("0")){
			logger.error("调用getWsGetWebJCMS系统中网站的信息："+"strWebIds="+strWebIds+"strLoginId="+strLoginId+"strPwd="+strPwd+"strKey="+strKey);
			}
		String tmp = null;
		String endpointURL = WebServiceConf.SERVICE_DSFXWSERVICE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE, "wsGetWeb");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { strWebIds,strLoginId,strPwd,strKey});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用wsGetWebJCMS系统中网站的信息" + e); // 测试
		}
		if(infromation.equals("0")){
			logger.error("调用getWsGetWebJCMS系统中网站的信息返回为："+tmp);
			}
		return tmp;
	}

	/**
	 * <p>Description:    第三方系统提供JCMS系统中指定网站下的栏目信息</p>
	 * @param strWebId    政府信息公开系统中节点id，只能是一个且不可为空
	 * @param strLoginId  CMS系统提供给第三方系统调用接口时检验的登陆名
	 * @param strPwd      JCMS系统提供给第三方系统调用接口时检验的密码；如果加密种子为空，则视为明文；反之视其为密文
	 * @return
	 *
	 * @since 2014-12-23 上午09:24:19
	 * @author yangz
	 */
	@Override
	public String getWsGetColumn(String strWebId, String strLoginId,
			String strPwd, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			strWebId  		= CodeUtil.decode(strWebId, apptoken);
			strLoginId  	= CodeUtil.decode(strLoginId, apptoken);
			strPwd  		= CodeUtil.decode(strPwd, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// TODO 第三方系统提供JCMS系统中指定网站下的栏目信息
		strLoginId=Config.getString("webservice.host.dsf.xw.username");
		strPwd=Config.getString("webservice.host.dsf.xw.password");
		strWebId=Config.getString("webservice.host.dsf.xw.webid");
		if(infromation.equals("0")){
			logger.error("调用getWsGetColumn第三方系统提供JCMS系统中指定网站下的栏目信息："+"strWebIds="+strWebId+"strLoginId="+strLoginId+"strPwd="+strPwd+"strKey="+strKey);
			}
		String tmp = null;
		String endpointURL = WebServiceConf.SERVICE_DSFXWSERVICE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE, "wsGetColumn");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { strWebId,strLoginId,strPwd,strKey});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getWsGetColumn" + e); // 测试
		}
		if(infromation.equals("0")){
			logger.error("调用getWsGetColumn第三方系统提供JCMS系统中指定网站下的栏目信息返回为："+tmp);
			}
		return tmp;
	}

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
	@Override
	public String getWsGetMultiInfos(String nCataId,
			 String nStart, String nEnd,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			nCataId  		= CodeUtil.decode(nCataId, apptoken);
			nStart  		= CodeUtil.decode(nStart, apptoken);
			nEnd  			= CodeUtil.decode(nEnd, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		// TODO 返回指定栏目下的信息（每次返回最多不超过20条）
		
	    String	strLoginId= backMhConfig.getString("webservice.host.dsf.xw.username");
	    String  strPwd= backMhConfig.getString("webservice.host.dsf.xw.password");
		if(infromation.equals("0")){
			logger.error("调用getWsGetMultiInfos返回指定栏目下的信息（每次返回最多不超过20条）："+"nCataId="+nCataId+"nStart="+nStart+"nEnd="+nEnd
			
			);
			}
		int nCataIds =Integer.valueOf(nCataId);
		int bRefs=Integer.valueOf(0);
		int iBase64s =Integer.valueOf(0);
		int nStarts=Integer.valueOf(nStart);
		int nEnds =Integer.valueOf(nEnd);
		int bAscs =Integer.valueOf(0);
		String tmp = null;
		String endpointURL = WebServiceConf.SERVICE_DSFXWSERVICE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE, "wsGetMultiInfos");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { nCataIds,bRefs,iBase64s,nStarts,nEnds,bAscs,"","",strLoginId,strPwd,""});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getWsGetMultiInfos" + e); // 测试
		}
		if(infromation.equals("0")){
			logger.error("调用getWsGetMultiInfos返回指定栏目下的信息（每次返回最多不超过20条）返回为："+tmp);
			}
		return tmp;
	}

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
	@Override
	public String getwsGetMultiInfosLink(String nCataId, String bRef,
			String nStart, String nEnd, String bAsc, String strStartCTime,
			String strEndCTime, String strLoginId, String strPwd, String strKey,String apptoken) {
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			nCataId  		= CodeUtil.decode(nCataId, apptoken);
			bRef  			= CodeUtil.decode(bRef, apptoken);
			nStart  		= CodeUtil.decode(nStart, apptoken);
			nEnd  			= CodeUtil.decode(nEnd, apptoken);
			bAsc  			= CodeUtil.decode(bAsc, apptoken);
			strStartCTime  	= CodeUtil.decode(strStartCTime, apptoken);
			strEndCTime  	= CodeUtil.decode(strEndCTime, apptoken);
			strLoginId  	= CodeUtil.decode(strLoginId, apptoken);
			strPwd  		= CodeUtil.decode(strPwd, apptoken);
			strKey  		= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO 返回指定栏目下的信息，包括标题、信息的文章页访问地址、作者、来源、创建时间，不包括内容、图片、附件（每次返回最多不超过50条）
		strLoginId=Config.getString("webservice.host.dsf.xw.username");
		strPwd=Config.getString("webservice.host.dsf.xw.password");
		nCataId=Config.getString("webservice.host.dsf.xw.cataid");
		if(infromation.equals("0")){
			logger.error("调用getwsGetMultiInfosLink返回指定栏目下的信息，包括标题、信息的文章页访问地址、作者、来源、创建时间，不包括内容、图片、附件（每次返回最多不超过50条）："+"nCataId="+nCataId+"bRef="+bRef+"nStart="+nStart+"nEnd="+nEnd
			+"bAsc="+bAsc+"strStartCTime="+strStartCTime+"strEndCTime="+strEndCTime+"strLoginId="+strLoginId+"strPwd="+strPwd+"strKey="+strKey		
			);
			}
		int nCataIds =Integer.valueOf(nCataId);
		int bRefs=Integer.valueOf(bRef);
		int nStarts=Integer.valueOf(nStart);
		int nEnds =Integer.valueOf(nEnd);
		int bAscs =Integer.valueOf(bAsc);
		String tmp = null;
		String endpointURL = WebServiceConf.SERVICE_DSFXWSERVICE;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE, "wsGetMultiInfosLink");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { nCataIds,bRefs,nStarts,nEnds,bAscs,"","",strLoginId,strPwd,""});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getwsGetMultiInfosLink" + e); // 测试
		}
		if(infromation.equals("0")){
			logger.error("调用getwsGetMultiInfosLink返回指定栏目下的信息，包括标题、信息的文章页访问地址、作者、来源、创建时间，不包括内容、图片、附件（每次返回最多不超过50条）返回为："+tmp);
			}
		return tmp;
	}

	

}
