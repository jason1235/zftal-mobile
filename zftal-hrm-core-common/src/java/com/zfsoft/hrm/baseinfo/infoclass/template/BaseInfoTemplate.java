//package com.zfsoft.hrm.baseinfo.infoclass.template;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
//
///**
// * 基础信息模板
// * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
// * @since 2012-5-29
// * @version V1.0.0
// */
//public class BaseInfoTemplate {
//	
//	private static List<InfoProperty> _list = new ArrayList<InfoProperty>();
//	
//	static {
//		_list.add( new InfoProperty("全局ID", "globalid", "COMMON", false, false) );
//		_list.add( new InfoProperty("职工号", "gh", "COMMON", false, true) );
//		_list.add( new InfoProperty("最后修改时间", "lastModifyTime", "COMMON", false, false) );
//	}
//	
//	/**
//	 * 返回模板
//	 * @return
//	 */
//	public static List<InfoProperty> getTemplate() {
//		return _list;
//	}
//
//}
