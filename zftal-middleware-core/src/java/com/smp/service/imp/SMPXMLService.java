package com.smp.service.imp;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.smp.service.ISMPXMLService;


/**
 * <p>Description: ZFSMP实现方法</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2015-2-12 下午02:06:09
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "smpXMLService")
public class SMPXMLService implements ISMPXMLService{
	private static Logger logger = Logger.getLogger(SMPXMLService.class);
    private final String infromation = Config.getString("mobile.infromation");
	/**
	 * <p>Description: 获取待审新闻</p>
	 * @param userName 用户名
	 * @param strkey   秘钥
 	 * @return 
	 *
	 * @since 2015-2-13 上午09:22:03
	 * @author yangz
	 */
	@Override
	public String getReviewNews(String userName, String strkey) {
		// TODO Auto-generated method stub
		if (infromation.equals("0")) {
			logger.error("调用getReviewNews获取待审新闻：" + "username="+ userName + "strKey=" + strkey );
		}
		String tmp =null;
		String endpointURL = WebServiceConf.WEBSERVICE_ZFSMP_ZJJC;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();

		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZFSMP,"reviewNews");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { userName, strkey });
			System.out.print("调用getReviewNews获取待审新闻" + tmp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getReviewNews获取待审新闻Erro" + e); // 测试
		}
		
		if (infromation.equals("0")) {
			logger.error("调用getReviewNews获取待审新闻返回为：" + tmp);
		}
		return tmp;
	}
	
	
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
	@Override
	public String getNews(String xwbh, String userName, String strkey) {
		// TODO Auto-generated method stub
		if (infromation.equals("0")) {
			logger.error("调用getNews根据新闻ID获取新闻列表：" + "username="+ userName + "strKey=" + strkey +"xwbh="+xwbh);
		}
		String tmp =null;
		String endpointURL = WebServiceConf.WEBSERVICE_ZFSMP_ZJJC;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();

		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZFSMP,"getNews");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { xwbh,userName, strkey });
			System.out.print("调用getNews根据新闻ID获取新闻列表" + tmp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getNews根据新闻ID获取新闻列表Erro" + e); // 测试
		}
		
		if (infromation.equals("0")) {
			logger.error("调用getNews根据新闻ID获取新闻列表返回为：" + tmp);
		}
		return tmp;
	}


	/**
	 * <p>Description: 更新新闻状态</p>
	 * @param xwbh     新闻编号
	 * @param status   状态2:审核不通过 3：审核通过
	 * @param xwnr     新闻内容
	 * @param userName 用户名
	 * @param strkey   秘钥
	 * @return
	 *
	 * @since 2015-2-14 上午10:03:20
	 * @author yangz
	 */
	@Override
	public String updateNews(String xwbh, String status, String xwnr,
			String userName, String strkey) {
		// TODO Auto-generated method stub
		if (infromation.equals("0")) {
			logger.error("调用updateNews更新新闻状态：" +"xwbh="+""+"status="+status+"xwnr="+xwnr+"username="+ userName + "strKey=" + strkey +"xwbh="+xwbh);
		}
		String tmp =null;
		Boolean b;
		String endpointURL = WebServiceConf.WEBSERVICE_ZFSMP_ZJJC;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();

		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZFSMP,"getNews");
			call.setOperationName(opAddEntry);
			b = (Boolean) call.invoke(new Object[] {xwbh,status,xwnr,userName,strkey});
			tmp=String.valueOf(b);
			System.out.print("调用updateNews更新新闻状态" + tmp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用updateNews更新新闻状态Erro" + e); // 测试
		}
		
		if (infromation.equals("0")) {
			logger.error("调用updateNews更新新闻状态返回为：" + tmp);
		}
		return tmp;
	}

}
