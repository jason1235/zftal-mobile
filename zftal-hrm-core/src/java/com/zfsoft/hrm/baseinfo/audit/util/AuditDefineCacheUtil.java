package com.zfsoft.hrm.baseinfo.audit.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;
import com.zfsoft.hrm.baseinfo.audit.service.IAuditDefineService;
import com.zfsoft.common.spring.SpringHolder;

/**
 * 审核定义缓存工具
 * @author jinjj
 * @date 2012-10-9 下午01:59:35 
 *
 */
public class AuditDefineCacheUtil {

	private static Log log = LogFactory.getLog(AuditDefineCacheUtil.class);
	
	private static Map<String,List<AuditDefine>> map = new HashMap<String,List<AuditDefine>>();
	private static IAuditDefineService defineService = (IAuditDefineService)SpringHolder.getBean("baseAuditDefineService");
	private static Date date;
	private static final long interal = 5*60*1000;
	
	public static List<AuditDefine> getDefine(String classId){
		initData();
		List<AuditDefine> list = (List<AuditDefine>)map.get(classId);
		return list;
	}
	
	private synchronized static void initData(){
		long now = new Date().getTime();
		if(date == null || now-date.getTime()>interal){
			map.clear();
			List<AuditDefine> allList = defineService.getList(new AuditDefineQuery());
			for(AuditDefine ad : allList){
				List<AuditDefine> data = map.get(ad.getClassId());
				if(data == null){
					data = new ArrayList<AuditDefine>();
				}
				data.add(ad);
				map.put(ad.getClassId(), data);
			}
			log.debug("audit define info cache refresh, "+allList.size()+" object loaded");
			date = new Date();
		}
	}
	
	/**
	 * 清理缓存
	 */
	public static void clear(){
		date = null;
	}
}
