package com.sjzx.book.service.imp;

import javax.xml.namespace.QName;

import net.sf.json.JSONArray;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.JavaXML;
import com.common.WebServiceConf;
import com.sjzx.book.service.IBookXMLService;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

@Service
@Component(value = "bookXMLService")
public class BookXMLService implements IBookXMLService{
	private static Logger logger = Logger.getLogger(BookXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description: 图书馆借阅信息</p>
	 * @param readerbarcode 读者条码
	 * @param strKey        秘钥
	 * @return
	 *
	 * @since 2014-12-8 上午10:27:31
	 * @author yangz
	 */
	@Override
	public String jyxxb(String username, String strKey, String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(username, apptoken))
			return "app_token error";
		try {
			username  		= CodeUtil.decode(username, apptoken);
			strKey 	 		= CodeUtil.decode(strKey, apptoken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String tmp = null;
		String status = Config.getString("mobile.xytypes");
		if(infromation.equals("0")){
			logger.error("调用getyktjbxx图书馆借阅信息："+"username="+username+"strKey="+strKey);
			}
		if(status.equals("2")){
		
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_TSGJYXX;
			org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
			
		    try{
		        Call call;
				call = (Call) service.createCall();
			    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
		//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
	            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
	            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
	            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"tsgjyxxb");
			    call.setOperationName(opAddEntry);
			    tmp =  (String)call.invoke(new Object[] {username,strKey});
				System.out.print("调用getyktjbxx重庆第二师范学院图书馆借阅信息" +  tmp); //测试
		    }catch (Exception e) {
				// TODO: handle exception
		    	System.out.print("调用getyktxfxx重庆第二师范学院图书馆借阅信息Erro" +  e); //测试
			}
		    if(infromation.equals("0")){
		    logger.error("调用getyktjbxx重庆第二师范学院图书馆借阅信息返回为："+tmp);
		    }
			return tmp;
		}
		else if(status.equals("3")){
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_GZDXTSGJYXX;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			/*	QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "yktxfjl");*/
				call.setOperationName("getSzdacx");
				tmp = (String) call.invoke(new Object[] { username,"N01130302"});
				JSONArray array = JSONArray.fromObject(tmp);
				tmp =JavaXML.BuildXMLForJson(array);
				System.out.print("调用getyktjbxx贵州大学图书馆借阅信息" +  tmp); //测试
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("调用getyktxfxx贵州大学图书馆借阅信息Erro" + e); // 测试
			}
			return tmp;
		}
		else if(status.equals("1")){
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_BOOK;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();

			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,
						"jyxxb");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
				System.out.print("中央民族大学图书借阅信息" + tmp); // 测试
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("中央民族大学图书借阅信息Erro" + e); // 测试
			}
			return tmp;
		}
		else if(status.equals("4")){
			//山东英才图书查询
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_SDYCXYTSG;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getWHTS");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
				tmp = tmp.replace("jcsj", "wjsj").replace("xhsj", "yhsj").replace("ztm", "sm");
			} catch (Exception e) {
				// TODO: handle exception
			}
			return tmp;
		}
		
		if(infromation.equals("0")){
			logger.error("调用getyktjbxx图书馆借阅信息："+"tmp="+tmp);
			}
		return tmp;
	}

}
