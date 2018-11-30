package com.dsf.news.test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;

import com.common.Config;
import com.common.JavaXML;
import com.common.WebServiceConf;
import com.common.backMhConfig;

public class testIDsfXMLService {

	/**
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param args
	 * 
	 * @since 2014-12-22 下午02:56:59
	 * @author yangz
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * String tmp = null; String endpointURL =
		 * WebServiceConf.SERVICE_DSFXWSERVICE; org.apache.axis.client.Service
		 * service = new org.apache.axis.client.Service(); try { Call call; call
		 * = (Call) service.createCall(); call.setTargetEndpointAddress(new
		 * java.net.URL(endpointURL)); // call.setOperationName("getSzdacx"
		 * );//设置对应方法 (原 // 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域 //
		 * 名指定方法//targetNamespace
		 * ="http://impl.webservice.controlstage.zfsdc.zfsoft.com" QName
		 * opAddEntry = new QName( WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE,
		 * "wsGetWeb"); call.setOperationName(opAddEntry); tmp = (String)
		 * call.invoke(new Object[] { "1","zygzxwhq","zygz123456",""});
		 * System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息" + tmp); // 测试 } catch
		 * (Exception e) { System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息Erro" +
		 * e); // 测试 }
		 */

		/*
		 * String
		 * strLoginId=Config.getString("webservice.host.dsf.xw.username");
		 * String strPwd=Config.getString("webservice.host.dsf.xw.password");
		 * String nCataId=Config.getString("webservice.host.dsf.xw.cataid");
		 * 
		 * int nCataIds =Integer.valueOf(nCataId); int bRefs=Integer.valueOf(0);
		 * int iBase64s =Integer.valueOf(0); int nStarts=Integer.valueOf(1); int
		 * nEnds =Integer.valueOf(20); int bAscs =Integer.valueOf(1); String tmp
		 * = null; String endpointURL = WebServiceConf.SERVICE_DSFXWSERVICE;
		 * org.apache.axis.client.Service service = new
		 * org.apache.axis.client.Service(); try { Call call; call = (Call)
		 * service.createCall(); call.setTargetEndpointAddress(new
		 * java.net.URL(endpointURL)); // call.setOperationName("getSzdacx"
		 * );//设置对应方法 (原 // 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域 //
		 * 名指定方法//targetNamespace
		 * ="http://impl.webservice.controlstage.zfsdc.zfsoft.com" QName
		 * opAddEntry = new QName( WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE,
		 * "wsGetMultiInfos"); call.setOperationName(opAddEntry); tmp = (String)
		 * call.invoke(new Object[] {
		 * nCataIds,bRefs,iBase64s,nStarts,nEnds,bAscs
		 * ,"","","03007","wlzx2728gly",""}); System.out.print(tmp); } catch
		 * (Exception e) { // TODO: handle exception
		 * System.out.print("调用getWsGetMultiInfos" + e); // 测试 }
		 */

		String strLoginId = backMhConfig
				.getString("webservice.host.dsf.xw.username");
		String strPwd = backMhConfig
				.getString("webservice.host.dsf.xw.password");
		int nCataIds = Integer.valueOf(32);
		int bRefs = Integer.valueOf(0);
		int iBase64s = Integer.valueOf(0);
		int nStarts = Integer.valueOf(1);
		int nEnds = Integer.valueOf(19);
		int bAscs = Integer.valueOf(0);
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
					WebServiceConf.WEBSERVICE_NAMESPACE_DSFXE,
					"wsGetMultiInfos");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { nCataIds, bRefs,
					iBase64s, nStarts, nEnds, bAscs, "", "", strLoginId,
					strPwd, "" });
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getWsGetMultiInfos" + e); // 测试
		}
		StringBuffer sb =new StringBuffer();
		sb.append(tmp);
		for(int i =0;i<sb.length();i++){
		sb.delete(tmp.indexOf("<data>"),tmp.indexOf("</data>"));
		}
		String temp=sb.toString();
		System.out.print("调用getWsGetMultiInfos" + temp);
	}
}
