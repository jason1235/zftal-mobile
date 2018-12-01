package com.sjzx.jsInformation.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * <p>Description:空闲教室查询接口</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2014-4-3 下午02:30:55
 * @author yangz
 * @version 1.0
 */
@WebService
@XmlAccessorType(XmlAccessType.FIELD)
public interface IFreeClassroomXMLService {


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
	 * @param jslbdm 教室类别代码
	 * @param lh 楼号
	 * @param min_zws 最小座位数
	 * @param max_zws 最大座位数
	 * @param ch 层号
	 * @param count 记录数
	 * @param sendtime 发送时间
	 * @param strKey 密钥
	 * @param jslb 教室类别名称
	 * @return
	 *
	 * @since 2014-4-24 上午10:27:57
	 * @author yangz
	 */
	@WebMethod
	public String getTeachingSiteIdle(
	    @WebParam(name="xn")String xn,
	    @WebParam(name="xq")String xq,
	    @WebParam(name="role")String role,
	    @WebParam(name="kssj")String kssj,
	    @WebParam(name="jssj")String jssj,
	    @WebParam(name="xqj")String xqj,
	    @WebParam(name="ksjc")String ksjc,
	    @WebParam(name="skcd")String skcd,
	    @WebParam(name="dsz")String dsz,
	    @WebParam(name="xqdm")String xqdm,
	    @WebParam(name="xqmc")String xqmc,
	    @WebParam(name="jslbdm")String jslbdm,
	    @WebParam(name="jslb") String jslb,
	    @WebParam(name="lh")String lh,
	    @WebParam(name="min_zws")String min_zws,
	    @WebParam(name="max_zws")String max_zws,
	    @WebParam(name="ch")String ch,
	    @WebParam(name="count")String count,
	    @WebParam(name="sendtime")String sendtime,
	    @WebParam(name="strKey")String strKey,
	    @WebParam(name="apptoken") String apptoken
 	);
	
	
	/**
	 * <p>Description:教学楼接口 </p>
	 * @param strKey 加密后密钥
	 * @return
	 *
	 * @since 2014-4-10 上午10:02:24
	 * @author yangz
	 */
	@WebMethod
	public String getjxlxx(
	   	@WebParam(name="strKey")String strKey,
	   	@WebParam(name="apptoken") String apptoken
	);
	
	
	

}
