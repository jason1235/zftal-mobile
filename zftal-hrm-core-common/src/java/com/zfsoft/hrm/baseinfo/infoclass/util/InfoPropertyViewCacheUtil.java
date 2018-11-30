package com.zfsoft.hrm.baseinfo.infoclass.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyViewService;
import com.zfsoft.common.spring.SpringHolder;

/** 
 * 信息类属性展示配置缓存工具类
 * @author jinjj
 * @date 2012-11-14 上午09:16:12 
 *  
 */
public class InfoPropertyViewCacheUtil {

	private static IInfoPropertyViewService viewService = (IInfoPropertyViewService)SpringHolder.getBean("infoPropertyViewService");
	private static Map<String,List<InfoPropertyView>> map = new HashMap<String,List<InfoPropertyView>>();
	private static int interval = 5*60*1000;//5分钟
	private static Date date;
	
	private static Logger log = LoggerFactory.getLogger(InfoPropertyViewCacheUtil.class);
	
	public static List<InfoProperty> getViewList(String classId,String username){
		List<InfoProperty> list = null;
		
		checkTime();
		List<InfoPropertyView> vList = getAllowList(classId,username);
		
		list = processViewList(classId,vList);
		return list;
	}
	
	private static void checkTime(){
		long now = new Date().getTime();
		if(date == null){
			date = new Date();
			log.info("信息类属性展示配置缓存初始化");
		}else if(now-date.getTime()>interval){
			clear();
		}
	}
	
	/**
	 * 获取不显示属性集合
	 * @param classId
	 * @param key
	 * @return
	 */
	private static List<InfoPropertyView> getAllowList(String classId,String username){
		String key = classId+"#"+username;
		List<InfoPropertyView> vList = map.get(key);
		
		if(vList == null){
			InfoPropertyViewQuery query = new InfoPropertyViewQuery();
			query.setClassId(classId);
			query.setUsername(username);
			vList = viewService.getList(query);
			map.put(key, vList);
			log.debug("信息类属性展示配置数据已缓存classId:"+classId);
		}
		
		return vList;
	}
	/**
	 * 处理展示属性列表
	 * @return
	 */
	private static synchronized List<InfoProperty> processViewList(String classId,List<InfoPropertyView> vList){
		List<InfoProperty> configList = new ArrayList<InfoProperty>();
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		if(clazz == null){
			clazz = InfoClassCache.getOverallInfoClass();
		}
		
		List<InfoProperty> list = clazz.getViewables();
		if(vList.size()==0){
			return list;//无配置，默认返回全部字段
		}
		for(InfoProperty p : list){
			boolean allow = false;
			for(InfoPropertyView v :vList){
				if(p.getGuid().equals(v.getPropertyId())){
					allow = true;
				}
			}
			if(allow){
				configList.add(p);
			}
		}
		return configList;
	}
	
	public static void clear(){
		map.clear();
		date = new Date();
		log.info("信息类属性展示配置缓存重置");
	}
}
