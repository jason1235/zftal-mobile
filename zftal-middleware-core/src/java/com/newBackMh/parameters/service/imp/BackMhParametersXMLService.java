package com.newBackMh.parameters.service.imp;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.JavaXML;
import com.common.XMLParser;
import com.common.backMhConfig;
import com.newBackMh.parameters.service.IBackMhParametersXMLService;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.util.base.StringUtil;


@Service
@Component(value = "backMhParametersXMLService")
public class BackMhParametersXMLService implements IBackMhParametersXMLService{
	private static Logger logger = Logger.getLogger(BackMhParametersXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");

	/**
	 * <p>Description: 获取门户后台选项卡名称</p>
	 * @return
	 *
	 * @since 2014-10-20 上午10:20:04
	 * @author yangz
	 */
	@Override
	public String getMobileBackMhTab(String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		
		
	    String TabName = backMhConfig.getString("backMhTabName");
	    String TabType = backMhConfig.getString("backMhTabType");
		String[] tabmane = TabName.split(";");
		String[] tabtype = TabType.split(";");
		String tabNames=null;
		if(TabName.equals("") && TabType.equals("")){
			
			StringBuffer msg=new StringBuffer();
			msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			msg.append("<zfsoft>");
			msg.append("</zfsoft>");
			tabNames=msg.toString();
		}
		else{
			List tabmanelist = Arrays.asList(tabmane);
			List tabtypelist = Arrays.asList(tabtype);
//			List list = XMLParser.ReadXMl();
			tabNames=JavaXML.BuildXMLDocBack(tabmanelist,tabtypelist);
		}
		
		if(infromation.equals("0")){
			logger.error("调用获取门户后台选项卡名称为："+tabNames+"apptoken="+apptoken);
			}
		return tabNames;
		
	}

}
