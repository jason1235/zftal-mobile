package com.mh.test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import com.mh.CXFServe.service.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.common.Config;
import com.common.ConstantsSoa;
import com.common.WebServiceConf;
import com.jw.org.tempuri.IConfigurationInfo;
import com.mh.CXFServe.service.ICaService;
import com.mh.CXFServe.service.MobileBean;
import com.util.SelectItems;
import com.util.encode.XmltoString;

public class MobileManageXMTEST {
	private final static String LYXY = "LYXY";
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		/*String usename="admin";
		String password="std2011";
		//	    List<String> xtdms=null;
		String remoteURL ="";
		String zfxml = "";
		ArrayList<String> xtdms=new ArrayList<String>();
		xtdms.add("007");
		xtdms.add("015");

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();

		MobileBean oMobileBean = new MobileBean();

		oMobileBean = (MobileBean) service.getTicket2(usename, password, xtdms, remoteURL);
		String[] output = null;
		@SuppressWarnings("rawtypes")
		List indexlist = new ArrayList();
		if(oMobileBean==null || !oMobileBean.isCacheck()){
			output = new String[]{"code","message"};//需要输出的字段
			indexlist.add(oMobileBean);
			zfxml =  XmltoString.xmlToStringForError(output, SelectItems.getReflectObjPropertyValue(indexlist, MobileBean.class,output), "ResultInfo");

		} else {
			output = new String[]{"cacheck","jsName","xm","bm","dqxn","dqxq","ticket","zjhm","yhm","xtmd"};//需要输出的字段

		}

		if(ConstantsSoa.SCHOOL_INFO.equals(LYXY)){//旅游学院调换用户名和职工号码数据
			String workerCode = oMobileBean.getZjhm();
			String username = oMobileBean.getYhm();
			oMobileBean.setZjhm(username);
			oMobileBean.setYhm(workerCode);
		}
		String status=Config.getString("mobile.jwtypes");

		//判断状态status:1为教务接口，2为数据中心
		if(status.equals("2")){
			if (oMobileBean.getDqxn()== null){
				//如果当前学年为空，则使用数据中心获取

				String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_DQXNXQ;
				org.apache.axis.client.Service  sjzxservice= new org.apache.axis.client.Service() ;
				String tmp = null;
				String strKey ="nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
				try{
					Call sjzxcall=null;
					sjzxcall = (Call) sjzxservice.createCall();
					sjzxcall.setTargetEndpointAddress( new java.net.URL(endpointURL) );
					//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
					//来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
					//名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
					QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"dqxnxq");
					sjzxcall.setOperationName(opAddEntry);
					tmp =  (String)sjzxcall.invoke(new Object[] {strKey});//实际调用
					System.out.print("中央民族当前学年学期" +  tmp); //测试
					SAXReader reader = new SAXReader();
					ByteArrayInputStream stream = new ByteArrayInputStream(tmp.getBytes());
					Document doc = null;
					try {
						doc = reader.read(stream);
						Element root = doc.getRootElement();

						for (Iterator<?> i = root.elementIterator("msg"); i.hasNext();) {
							Element foo = (Element) i.next();
							String xqxn = foo.elementText("dqxnxq").toString();
							oMobileBean.setDqxn(xqxn.substring(0,xqxn.lastIndexOf("-")));
							System.out.print("中央民族当前学年" +  xqxn.substring(0,xqxn.lastIndexOf("-"))); //测试
							oMobileBean.setDqxq(xqxn.substring(xqxn.lastIndexOf("-")).replace("-", ""));
							System.out.print("中央民族当前学期" + xqxn.substring(xqxn.lastIndexOf("-")).replace("-", "")); //测试
						}
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}catch (Exception e) {
					// TODO: handle exception
					System.out.print("中央民族当前学年学期Erro" +  e); //测试
				}
			}
		}
		else if(status.equals("1")){

			//如果当前学年为空，则使用教务接口获取
			if (oMobileBean.getDqxn()== null){

				try {
					String xml="";
					JaxWsProxyFactoryBean factory1 = new JaxWsProxyFactoryBean();
					factory1.setServiceClass(IConfigurationInfo.class);
					factory1.setAddress(WebServiceConf.SERVICE_JWSERVICE_CONFIG);//接口地址
					factory1.setServiceName(new QName(WebServiceConf.WEBSERVICE_NAMESPACE_JW, "ConfigurationInfo"));
					factory1.getInInterceptors().add(new LoggingInInterceptor());
					factory1.getOutInterceptors().add(new LoggingOutInterceptor());
					IConfigurationInfo service1 = (IConfigurationInfo) factory1.create();

					xml = service1.configurationInfo();

					SAXReader reader = new SAXReader();
					ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
					Document doc = reader.read(stream);
					Element root = doc.getRootElement();
					for (Iterator<?> i = root.elementIterator("row"); i.hasNext();) {
						Element foo = (Element) i.next();
						String xqxn = foo.elementText("dqxnxq").toString();
						oMobileBean.setDqxn(xqxn.substring(0,xqxn.lastIndexOf("-")));
						oMobileBean.setDqxq(xqxn.substring(xqxn.lastIndexOf("-")).replace("-", ""));
					} 
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		}
		indexlist.add(oMobileBean);
		ArrayList<String> xtd=new ArrayList<String>();
		xtd.add("007");
		xtd.add("015");
		xtd.add("001");
		xtd.add("016");
		for(int i = 0 ; i<xtd.size() ; i++){
			String  s=xtd.get(i);
			System.out.println(s);
//			Iterator iter = hashMap.entrySet().iterator();
//			while (iter.hasNext()) {
//				Map.Entry entry = (Map.Entry) iter.next();
//				Object key = entry.getKey();
//				Object val = entry.getValue();
			}

//		}


		
		list =SelectItems.getReflectObjPropertyValue2(indexlist, MobileBean.class,output);
		//			list.addAll(x);
		zfxml =  XmltoString.xmlToStringNew(output, list, "MH");*/
		String usename="admin";
		String password="std2011";
		String xtdms ="007,015";
		String remoteURL ="";
		String str=null;
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICaService.class);
		factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		ICaService service = (ICaService) factory.create();
		
		str =service.getTicket2(usename, password, xtdms, remoteURL);
		

	}


}
