/**
 *
 */
package com.zfsoft.mobile.services.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;


import com.zfsoft.mobile.MD5Util.MD5Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

import com.common.Config;
import com.common.WebServiceConf;
import com.jw.org.tempuri.ICourseSchedule;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;
import com.zfsoft.common.log.User;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.services.assetWebservice.AssetEntity;
import com.zfsoft.mobile.services.assetWebservice.WebServiceSoap;
import com.zfsoft.mobile.webservices.cxf.service.impl.WSSerServiceImpl;

/**
 * @author zhangxu
 * @description
 * @date 2017-11-3 上午11:29:28
 */
public class AssetAction extends HrmAction{

	private static Logger logger = Logger.getLogger(AssetAction.class);

	public String list(){
		logger.error("assetlist---start");
		HttpSession session = getSession();
        User user = getUser();
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WebServiceSoap.class);
		String address = Config.getString("assetWebservice","http://10.10.0.71/ais/images/WebService.asmx?WSDL");
		factory.setAddress(address);//接口地址
		factory.setServiceName(new QName("http://10.10.0.71/ais/images/WebService.asmx?WSDL", "getLastTodoTaskList"));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());


		WebServiceSoap service = (WebServiceSoap) factory.create();
		String key = "zfsoft" + user.getYhm();
		String result = null;
		try {
			result = service.getLastTodoTaskList(user.getYhm(), 5, MD5Util.md5Encode(key).toUpperCase());
			logger.error("result:"+result);
			result = JSONUtils.xml2json(result);
			JSONArray jsonArray = JSONArray.fromObject(result );
			if(jsonArray != null && jsonArray.size() > 0){
				List<AssetEntity> assetList = new ArrayList<AssetEntity>();
				AssetEntity assetEntity;
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonEntity = jsonArray.getJSONObject(i);
					assetEntity = new AssetEntity();
					assetEntity.setTitle(jsonEntity.getString("title"));
					assetEntity.setTime(jsonEntity.getString("time"));
					assetList.add(assetEntity);
				}
				getValueStack().set("assetList", assetList);
			}else{
				logger.error("nullFlag:true");
				getValueStack().set("nullFlag", true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.error("assetlist---end");
		return "list";
	}


	/**
	 * @author: zhangxu
	 * @Title: main
	 * @Description:
	 * @param @param args    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(WebServiceSoap.class);
		factory.setAddress("http://10.10.0.71/ais/images/WebService.asmx?WSDL");//接口地址
		factory.setServiceName(new QName("http://10.10.0.71/ais/images/WebService.asmx?WSDL", "getLastTodoTaskList"));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());


		WebServiceSoap service = (WebServiceSoap) factory.create();
		String result = service.getLastTodoTaskList("z10820060301", 5, "36538A128AECAA7C94A0FE05A0E8DC91");
		result = JSONUtils.xml2json(result);
		JSONArray jsonArray = JSONArray.fromObject(result );
		System.out.println(result);

	}

}
