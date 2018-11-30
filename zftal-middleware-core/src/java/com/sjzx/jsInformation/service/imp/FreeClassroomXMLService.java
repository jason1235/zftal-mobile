package com.sjzx.jsInformation.service.imp;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.common.Config;
import com.common.WebServiceConf;
import com.jw.org.tempuri.IClassRoom;
import com.sjzx.jsInformation.service.IFreeClassroomXMLService;
import com.util.WebServiceUtil;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;


/**
 * <p>Description:空闲教室查询实现类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-3 下午02:28:29
 * @author yangz
 * @version 1.0
 */
@Service
@Component(value = "freeClassroomXMLService")
public class FreeClassroomXMLService implements IFreeClassroomXMLService{
	private static Logger logger = Logger.getLogger(FreeClassroomXMLService.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * 连接符
	 */
	private final String AND = "&";
	
	/**
	 * <p>Description:教学楼楼号查询</p>
	 * @param strKey 加密后密钥
	 * @return
	 *
	 * @since 2014-4-10 上午10:03:08
	 * @author yangz
	 */
	@Override
	public String getjxlxx(String strKey,String apptoken) { 
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			strKey  		= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
		logger.error("调用getjxlxx教学楼楼号查询："+"strKey="+strKey);
		}
		String status=Config.getString("mobile.jwtypes");
		if(status.equals("2")){
		String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_JXL;
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"getjxlxx");
		    call.setOperationName(opAddEntry);
		    tmp =  (String)call.invoke(new Object[] {strKey});//实际调用
			System.out.print("中央民族教学楼" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print("中央民族教学楼Erro" +  e); //测试
		  }
	    if(infromation.equals("0")){
	    logger.error("调用getjxlxx教学楼楼号查询返回为："+tmp);
	    }
	         return tmp;
		}
	    else if(status.equals("1")){
	    	String str = null;
	    	IClassRoom service = (IClassRoom) WebServiceUtil.createFactoryJw(
					WebServiceConf.SERVICE_JWSERVICE_CLASSROOMSEARCH, IClassRoom.class, "TeachingSiteInfo")
					.create();
	    	str=service.teachingSiteInfo();
	    	if(infromation.equals("0")){
	    	logger.error("调用getjxlxx教学楼楼号查询返回为："+str);
	    	}
			return str;
	    }
		return status;
	}

	

	/**
	 * <p>Description:空闲教室查询</p>
	 * @param xn 学年
	 * @param xq 学期
	 * @param role 角色
	 * @param kssj 开始时间 必填
	 * @param jssj 结束时间 必填
	 * @param xqj 星期几（数字）
	 * @param ksjc 开始节次 必填(sjdb中大节)
	 * @param skcd 上课长度
	 * @param dsz 单双周（单/双/空）
	 * @param xqdm 校区代码
	 * @param xqmc 校区名称
	 * @param jslbdm 教室类别代码
	 * @param jslb 教室类别名称
	 * @param lh 楼号
	 * @param min_zws 最小座位数
	 * @param max_zws 最大座位数
	 * @param ch 层号
	 * @param count 记录数
	 * @param sendtime 发送时间
	 * @param strKey 密钥
	 * @return
	 *
	 * @since 2014-4-24 上午10:27:57
	 * @author yangz
	 */
	@Override
	public String getTeachingSiteIdle(String xn, String xq,String role, String kssj,
			String jssj, String xqj, String ksjc, String skcd, String dsz,
			String xqdm,String xqmc, String jslbdm,String jslb,String lh, String min_zws,
			String max_zws, String ch, String count, String sendtime,
			String strKey,String apptoken) {
		// TODO Auto-generated method stub
		if(!ApptokenUtils.compare(apptoken))
			return "app_token error";
		try {
			xn  		= CodeUtil.decode(xn, apptoken);
			xq  		= CodeUtil.decode(xq, apptoken);
			role  		= CodeUtil.decode(role, apptoken);
			kssj  		= CodeUtil.decode(kssj, apptoken);
			jssj  		= CodeUtil.decode(jssj, apptoken);
			xqj  		= CodeUtil.decode(xqj, apptoken);
			ksjc  		= CodeUtil.decode(ksjc, apptoken);
			skcd  		= CodeUtil.decode(skcd, apptoken);
			dsz  		= CodeUtil.decode(dsz, apptoken);
			xqdm  		= CodeUtil.decode(xqdm, apptoken);
			xqmc  		= CodeUtil.decode(xqmc, apptoken);
			jslbdm  	= CodeUtil.decode(jslbdm, apptoken);
			jslb  		= CodeUtil.decode(jslb, apptoken);
			lh  		= CodeUtil.decode(lh, apptoken);
			min_zws  	= CodeUtil.decode(min_zws, apptoken);
			max_zws  	= CodeUtil.decode(max_zws, apptoken);
			ch  		= CodeUtil.decode(ch, apptoken);
			count  		= CodeUtil.decode(count, apptoken);
			sendtime  	= CodeUtil.decode(sendtime, apptoken);
			strKey  	= CodeUtil.decode(strKey, apptoken);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	//	String infromation=Config.getString("mobile.infromation");
		if(infromation.equals("0")){
			logger.error("调用getTeachingSiteIdle空闲教室查询："+"xn="+xn+"xq="+xq+"role="+role+"kssj="+kssj
					+"jssj="+jssj+"xqj="+xqj+"ksjc="+ksjc+"skcd="+skcd+"dsz="+dsz+"xqdm="+xqdm+"xqmc="+xqmc
					+"jslbdm="+jslbdm+"jslb="+jslb+"lh="+lh+"minZws="+min_zws+"maxZws="+max_zws+"ch="+ch+"count="+count
					+"sendtime="+sendtime+"strKey="+strKey);
		}
		String status=Config.getString("mobile.jwtypes");
		if(status.equals("2")){
		String endpointURL = WebServiceConf.WEBSERVICE_ZYMZ_KXJS;
		org.apache.axis.client.Service  service= new org.apache.axis.client.Service() ;
		String tmp = null;
		String sjzxstrKey = "nD9i2ZemQbYrY+EnX8V9OA1NxBO2HLhI";
	    try{
	        Call call;
			call = (Call) service.createCall();
		    call.setTargetEndpointAddress( new java.net.URL(endpointURL) );
	//      call.setOperationName("getSzdacx" );//设置对应方法 (原  
            //来方法中没有加入域名空间。如果直接调用以前的方法可能会报错。需要加入域
            //名指定方法//targetNamespace="http://impl.webservice.controlstage.zfsdc.zfsoft.com"
            QName opAddEntry = new QName(WebServiceConf.WEBSERVICE_NAMESPACE_ZYMZ,"getTeachingSiteIdle");
		    call.setOperationName(opAddEntry);
		    tmp =  (String)call.invoke(new Object[] {dsz,jslb,ksjc,sendtime,xn,xqmc,xqj,xq,sjzxstrKey});//实际调用
		    tmp = tmp.replace("data", "table").replace("msg", "row");
			System.out.print("中央民族大学空闲教室查询" +  tmp); //测试
	    }catch (Exception e) {
			// TODO: handle exception
	    	System.out.print("中央民族大学空闲教室查询Erro" +  e); //测试
		}
	    if(infromation.equals("0")){
	    	logger.error("调用getTeachingSiteIdle返回为："+tmp);
	    }
		return tmp;
		}
		else if(status.equals("1")){
		String str = null;	
		String parameterList=xn+ AND +xq + AND +kssj+ AND +jssj+ AND +xqj+ AND +ksjc+ AND +skcd+ AND +dsz+ AND +xqdm+ AND +jslbdm+ AND +lh+ AND +min_zws+ AND +min_zws+ AND +ch;   
		    	IClassRoom service = (IClassRoom) WebServiceUtil.createFactoryJw(
						WebServiceConf.SERVICE_JWSERVICE_CLASSROOMSEARCH, IClassRoom.class, "TeachingSiteIdleInfo")
						.create();
		    	str=service.teachingSiteIdleInfo(parameterList, role, count, strKey);
		    	 if(infromation.equals("0")){
		 	    	logger.error("调用getTeachingSiteIdle空闲教室查询返回为："+str);
		 	    }
				return str;
	   }
			return status;
	}
	
}
