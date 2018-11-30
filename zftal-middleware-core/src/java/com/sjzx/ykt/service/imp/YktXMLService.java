package com.sjzx.ykt.service.imp;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.sjzx.ykt.service.IYktXMLService;

/**
 * <p>
 * Description:一卡通信息实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since 2014-4-2 上午09:08:50
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "yktXMLService")
public class YktXMLService implements IYktXMLService {
	private static Logger logger = Logger.getLogger(YktXMLService.class);
	private final String infromation = Config.getString("mobile.infromation");

	/**
	 * <p>
	 * Description: 中央民族大学一卡通交易信息
	 * </p>
	 * 
	 * @param stuempno
	 *            用户名
	 * @param sendtime
	 *            当前时间
	 * @param sign
	 *            加密后的密文
	 * @param startData
	 *            查询起始时间
	 * @param endData
	 *            查询结束时间
	 * @return
	 * 
	 * @since 2014-4-2 上午11:30:58
	 * @author yangz
	 */
	@Override
	public String getIcCardConsumeInfo(String username, String strKey,
			String sendtime, String endData, String startData) {
		// TODO Auto-generated method stub
		// String infromation=Config.getString("mobile.infromation");
		if (infromation.equals("0")) {
			logger.error("调用getIcCardConsumeInfo一卡通交易信息：" + "username="
					+ username + "strKey=" + strKey + "sendtime=" + sendtime
					+ "endData=" + endData + "startData=" + startData);
		}
		String tmp = null;
		String status = Config.getString("mobile.xytypes");
		if (status.equals("1")) {
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_YKT;
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
						"getIcCardConsumeInfo");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
				System.out.print("中央民族大学一卡通交易信息" + tmp); // 测试
			} catch (Exception e) {
				System.out.print("中央民族大学一卡通交易信息Erro" + e); // 测试
			}
		}
		else if(status.equals("2")){
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_XFXX;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "yktxfjl");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
				tmp = tmp.replace("amount", "xfje").replace("occurtime", "xfsj").replace("areaname", "xfdd").replace("name", "xm").replace("cardno", "kh").replace("cardvalue", "xfhye").replace("employeeno", "rybh");
				System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息" + tmp); // 测试
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息Erro" + e); // 测试
			}
		} else if(status.equals("4")){
			//山东英才一卡通消费明细
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_SDYCXYYKTXFMX;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getYKTXFLS");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
				tmp = tmp.replace("mc", "xfdd").replace("jysj", "xfsj").replace("jye", "xfje").replace("ye", "xfhye");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("调用getYKTYE山东英才一卡通余额Erro" + e); // 测试
			}
			
		}
		if (infromation.equals("0")) {
			logger.error("调用getIcCardConsumeInfo一卡通交易信息返回为：" + tmp);
		}
		return tmp;
	}

	/**
	 * <p>
	 * Description: 中央民族大学一卡通余额
	 * </p>
	 * 
	 * @param username
	 *            用户名
	 * @param strKey
	 *            加密后的密钥
	 * @return
	 * 
	 * @since 2014-4-4 上午10:48:32
	 * @author yangz
	 */
	@Override
	public String getyktye(String username, String strKey) {
		// TODO Auto-generated method stub
		// String infromation=Config.getString("mobile.infromation");
		if (infromation.equals("0")) {
			logger.error("调用getyktye一卡通余额：" + "username=" + username
					+ "strKey=" + strKey);
		}

		String tmp = null;
		String status = Config.getString("mobile.xytypes");
		if (status.equals("1")) {
			String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_YKTYE;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();

			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getyktye");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });// 实际调用
				System.out.print(" 中央民族大学一卡通余额" + tmp); // 测试
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print(" 中央民族大学一卡通余额Erro" + e); // 测试
			}
		} else if (status.equals("2")) {
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_JBXX;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "yktjbxx");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
				tmp = tmp.replace("name", "xm").replace("cardno", "kh")
						.replace("employeeno", "rybh");
				System.out.print("调用getyktjbxx重庆第二师范学院一卡通基本信息" + tmp); // 测试
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("调用getyktxfxx重庆第二师范学院一卡通基本信息Erro" + e); // 测试
			}
		} else if(status.equals("4")){
			//山东英才一卡通余额
			String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_SDYCXYYKTYE;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			try {
				Call call;
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(endpointURL));
				// call.setOperationName("getSzdacx" );//设置对应方法 (原
				// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
				// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
				QName opAddEntry = new QName(
						WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "getYKTYE");
				call.setOperationName(opAddEntry);
				tmp = (String) call.invoke(new Object[] { username, strKey });
			/*	tmp = tmp.replace("name", "xm").replace("cardno", "kh")
						.replace("employeeno", "rybh");*/
				System.out.print("调用getYKTYE山东英才一卡通余额" + tmp); // 测试
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print("调用getYKTYE山东英才一卡通余额Erro" + e); // 测试
			}
			
		}

		if (infromation.equals("0")) {
			logger.error("调用getyktye一卡通余额返回为：" + tmp);
		}
		return tmp;
	}

	/**
	 * <p>
	 * Description: 重庆第二师范学院一卡通消费信息
	 * </p>
	 * 
	 * @param username
	 *            用户名
	 * @return
	 * 
	 * @since 2014-12-8 上午08:45:03
	 * @author yangz
	 */
	@Override
	public String getyktxfxx(String username, String strKey) {
		// TODO Auto-generated method stub
		if (infromation.equals("0")) {
			logger.error("调用getyktxfxx重庆第二师范学院一卡通消费信息：" + "username="
					+ username + "strKey=" + strKey);
		}
		String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_XFXX;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		String tmp = null;
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "yktxfjl");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { username, strKey });
			System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息" + tmp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getyktxfxx重庆第二师范学院一卡通消费信息Erro" + e); // 测试
		}
		if (infromation.equals("0")) {
			logger.error("调用getyktxfxx重庆第二师范学院一卡通消费信息返回为：" + tmp);
		}
		return tmp;
	}

	/**
	 * <p>
	 * Description: 重庆第二师范学院一卡通基本信息
	 * </p>
	 * 
	 * @param username
	 *            用户名
	 * @param strKey
	 *            秘钥
	 * @return
	 * 
	 * @since 2014-12-8 上午09:56:21
	 * @author yangz
	 */
	@Override
	public String getyktjbxx(String username, String strKey) {
		// TODO Auto-generated method stub
		if (infromation.equals("0")) {
			logger.error("调用getyktjbxx重庆第二师范学院一卡通基本信息：" + "username="
					+ username + "strKey=" + strKey);
		}
		String endpointURL = WebServiceConf.WEBSERVICE_CQDRSF_JBXX;
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		String tmp = null;
		try {
			Call call;
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			// call.setOperationName("getSzdacx" );//设置对应方法 (原
			// 来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
			// 名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
			QName opAddEntry = new QName(
					WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ, "yktjbxx");
			call.setOperationName(opAddEntry);
			tmp = (String) call.invoke(new Object[] { username, strKey });
			System.out.print("调用getyktjbxx重庆第二师范学院一卡通基本信息" + tmp); // 测试
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("调用getyktxfxx重庆第二师范学院一卡通基本信息Erro" + e); // 测试
		}
		if (infromation.equals("0")) {
			logger.error("调用getyktjbxx重庆第二师范学院一卡通基本信息返回为：" + tmp);
		}
		return tmp;
	}

}
