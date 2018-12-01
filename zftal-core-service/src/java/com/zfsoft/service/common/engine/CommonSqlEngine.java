package com.zfsoft.service.common.engine;

import java.util.List;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.dao.entities.BjdmModel;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.dao.entities.NjdmModel;
import com.zfsoft.dao.entities.ZydmModel;
import com.zfsoft.service.common.ICommonSqlService;

/**
 * 
 * 类名称：CommonSqlEngine 
 * 类描述： 公共服务引擎类 
 * 创建人：caozf 
 * 创建时间：2012-6-26 上午08:41:27
 * @version
 * 
 */
public class CommonSqlEngine {

	private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(CommonSqlEngine.class);

	private static CommonSqlEngine commonSqlEngine;
	
	private static ICommonSqlService service;

	private CommonSqlEngine() {

	}

	public static CommonSqlEngine getInstance() {

		return commonSqlEngine;
	}
	
	public static ICommonSqlService getService(){
		if (service == null) {
			service = (ICommonSqlService) ServiceFactory.getService("commonSqlService");
		}
		return service;
	}
	
	/**
	 * 静态方法: 获取所有学院(部门)列表
	 * 参数 @return 参数说明
	 * 返回类型  List<BmdmModel>  返回类型
	*/
	public static List<BmdmModel> getAllXy(){
		List<BmdmModel> bmdms = null;
		try {
			bmdms = getService().queryAllXy();
		} catch (Exception e) {
			log.error("获取所有学院信息失败",e);
		}
		return bmdms;
	}
	
	/**
	 * 静态方法: 获取所有专业列表
	 * 参数 @return 参数说明
	 * 返回类型  List<ZydmModel>  返回类型
	*/
	public static List<ZydmModel> getAllZy(){
		List<ZydmModel> zydms = null;
		try {
			zydms = getService().queryAllZy();
		} catch (Exception e) {
			log.error("获取所有专业信息失败",e);
		}
		return zydms;
	}

	/**
	 * 静态方法: 获取所有年级列表
	 * 参数 @return 参数说明
	 * 返回类型  List<NjdmModel>  返回类型
	*/
	public static List<NjdmModel> getAllNj(){
		List<NjdmModel> njdms = null;
		try {
			njdms = getService().queryAllNj();
		} catch (Exception e) {
			log.error("获取所有学院信息失败",e);
		}
		return njdms;
	}
	
	public static String getAllNjJson(){
		StringBuilder builder = new StringBuilder();
		String str = "";
		builder.append("[");
		List<NjdmModel> njdms = CommonSqlEngine.getAllNj();
		for(NjdmModel njdm:njdms){
			builder.append("{");
			builder.append("'").append("njdm_id").append("'");
			builder.append(":");
			builder.append("'"+njdm.getNjdm_id()+"'");
			builder.append(",");
			builder.append("'").append("njmc").append("'");
			builder.append(":");
			builder.append("'"+njdm.getNjmc()+"'");
			builder.append("}");
			builder.append(",");
		}
		str = builder.toString();
		str = str.substring(0,str.length()-1);
		str = str + "]";
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(CommonSqlEngine.getAllNjJson());
	}
	
	/**
	 * 静态方法: 获取所有班级列表
	 * 参数 @return 参数说明
	 * 返回类型  List<BjdmModel>  返回类型
	*/
	public static List<BjdmModel> getAllBj(){
		List<BjdmModel> bjdms = null;
		try {
			bjdms = getService().queryAllBj();
		} catch (Exception e) {
			log.error("获取所有班级信息失败",e);
		}
		return bjdms;
	}
}
