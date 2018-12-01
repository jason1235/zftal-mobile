/**
 * 
 */
package com.zfsoft.mobile.accessStatistics.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.zfsoft.mobile.accessStatistics.dao.IVisitDao;
import com.zfsoft.mobile.accessStatistics.entity.VisitForDay;
import com.zfsoft.mobile.accessStatistics.entity.VisitForHour;
import com.zfsoft.mobile.accessStatistics.service.IVisitService;

/**
 * @author zhangxu
 * @description 
 * @date 2017-6-13 下午03:58:34
 */
public class VisitServiceImpl implements IVisitService {
	private CacheManager cacheManager;
	private IVisitDao visitDao;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}
	
	/**
	 * 
	* @author: zhangxu
	* @Title: visitByUserId 
	* @Description: app访问统计
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Override
	public void visitByUserId(String userId){
		Cache visitDayCache = cacheManager.getCache("visitDayCache");
		Date today = new Date();  
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String key = userId + sdf.format(today);//统计每天的app使用人次，以userid+当前日期为key值放入缓存
		Element element = visitDayCache.get(key);
		if(element == null){//当前日期未访问过
			VisitForDay visitForDay = new VisitForDay(userId,sdf.format(today));
			element = new Element(key, visitForDay);
			visitDayCache.put(element);
			VisitForDay todayVisit = visitDao.getTodayVisit();
			if(todayVisit == null){//今天还未有人访问过app时
				visitDao.insertTodayVisit();
			}else{//今天已经有人访问过app
				visitDao.updateTodayVisit(todayVisit.getUserVisitCount()+1);
			}
		}
		
		Cache visitHourCache = cacheManager.getCache("visitHourCache");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		key = userId + sdf.format(today);//统计每天的app使用人次，以userid+当前日期为key值放入缓存
		element = visitHourCache.get(key);
		if(element == null){//当前日期此小时内未访问过
			VisitForHour visitForHour = new VisitForHour(userId, sdf.format(today));
			element = new Element(key, visitForHour);
			visitHourCache.put(element);
			visitDao.deleteHourVisit();
			VisitForHour todayHourVisit = visitDao.getTodayHourVisit();
			if(todayHourVisit == null){//今天还未有人访问过app时
				visitDao.insertTodayHourVisit();
			}else{//今天已经有人访问过app
				visitDao.updateTodayHourVisit(todayHourVisit.getUserVisitCount()+1);
			}
		}
	}
	  
	
	
	
	  
	@Override
	public void testCache(){
        Cache levelOneCache = cacheManager.getCache("visitDayCache");
        String cacheObject = null;
        for (int i = 0; i < 10; i++) {
            Element element = levelOneCache.get("key");

            if (element == null) {
                cacheObject ="test";
                element = new Element("key", cacheObject);
                levelOneCache.put(element);
                System.out.println("cacheObject[" + cacheObject + "]" + ",无法从缓存中取到");
            } else {
                cacheObject = (String) element.getValue();
                System.out.println("cacheObject[" + cacheObject + "]" + ",从缓存中取到");
            }
        }
	}

	public void setVisitDao(IVisitDao visitDao) {
		this.visitDao = visitDao;
	}

	public IVisitDao getVisitDao() {
		return visitDao;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.accessStatistics.service.IVisitService#getTodayVisitByMouth(java.lang.String)
	 */
	@Override
	public List<VisitForDay> getTodayVisitByMouth(String dateStr) {
		return visitDao.getTodayVisitByMouth(dateStr);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.accessStatistics.service.IVisitService#getTopTodayVisitByMouth()
	 */
	@Override
	public List<VisitForDay> getTopTodayVisitByMouth() {
		return visitDao.getTopTodayVisitByMouth();
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.accessStatistics.service.IVisitService#getTopHourVisitByDay()
	 */
	@Override
	public List<VisitForHour> getTopHourVisitByDay() {
		return visitDao.getTopHourVisitByDay();
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.accessStatistics.service.IVisitService#getHourVisitByDay(java.lang.String)
	 */
	@Override
	public List<VisitForHour> getHourVisitByDay(String chooesHourDate) {
		return visitDao.getHourVisitByDay(chooesHourDate);
	}
}
