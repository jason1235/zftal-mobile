package com.zfsoft.sjzx.jhpz.sjkpz.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.transaction.support.TransactionTemplate;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.sjkpz.dao.SjkpzDao;
import com.zfsoft.sjzx.jhpz.sjkpz.model.Sjkpz;
import com.zfsoft.sjzx.jhpz.sjkpz.model.SjkpzWrapper;
import com.zfsoft.sjzx.jhpz.sjkpz.service.SjkpzService;

/**
 * 数据库配置业务处理
 * @author cuitongxin
 *
 */
@SuppressWarnings("unchecked")
public class SjkpzServiceImpl implements SjkpzService{

	private Logger log = Logger.getLogger(SjkpzServiceImpl.class);

	private SjkpzDao sjkpzDao;

	//用来缓存数据
	private Cache cache;

	private List<Map<String,Object>> list ;

	@SuppressWarnings("unused")
	private TransactionTemplate transactionTemplate;

	public Object getSjkpz(Sjkpz sjkpz){

		return this.sjkpzDao.getSjkpzOne(sjkpz).get(0);
	}

	public void insertSjkpz(Sjkpz sjkpz){

		this.sjkpzDao.insertSjkpz(sjkpz);
	}

	/**
	 * 更新一条数据库配置信息,加入事务控制
	 * @return
	 * @param final sjkpz
	 */
	public void updateSjkpz(final Sjkpz sjkpz) {

		sjkpzDao.updateSjkpz(sjkpz);
	}

	public void deleteSjkpz(Sjkpz sjkpz){

		this.sjkpzDao.deleteSjkpz(sjkpz);
	}

	/**
	 * 查询带有分页信息的数据库配置信息
	 */
	public PageList<SjkpzWrapper> getPagingInfo(Sjkpz sjkpz){

		PageList<SjkpzWrapper> pageList = new PageList<SjkpzWrapper>();
		Paginator paginator = new Paginator();
		if(sjkpz!=null){
			paginator.setItemsPerPage(sjkpz.getPerPageSize());
			paginator.setPage((Integer)sjkpz.getToPage());
			paginator.setItems(sjkpzDao.getPagingInfoCount(sjkpz));
			pageList.setPaginator(paginator);

			if(paginator.getBeginIndex() <= paginator.getItems()){
				sjkpz.setStartRow(paginator.getBeginIndex());
				sjkpz.setEndRow(paginator.getEndIndex());
				List<SjkpzWrapper> list = sjkpzDao.getPagingInfo(sjkpz);
				pageList.addAll(list);
			}
		}
		return pageList;

		//return this.sjkpzDao.getPagingInfo(sjkpz);
	}

	/**
	 * 获取所有的数据库类型信息
	 */
	public List<Map<String, Object>> getSjklx() {

		//return (List<Map<String, Object>>) this.sjkpzDao.getSjklxList();
		this.initCache("sjklx");
		return list;
	}

	/**
	 * 获取所有的数据库连接类型信息
	 */
	public List<Map<String,Object>> getSjkljlx(){

		this.initCache("sjkljlx");

		return list;
	}

	/**
	 * 初始化缓存的数据,把需要查询的数据在第一次查询时就放在缓存中,下次再次查询时可以直接从缓存中获取
	 */
	private void initCache(String key){

		try{

			Element element = this.cache.get(key);

			if(element == null){

				if(key.equals("sjklx")){
					element = new Element(key,(Serializable)this.sjkpzDao.getSjklxList());
				}else {
					element = new Element(key,(Serializable)this.sjkpzDao.getSjkljlxList());
				}
				this.cache.put(element);
			}

			list = (List<Map<String,Object>>)element.getValue();

		}catch(Exception e){
			e.printStackTrace();
			log.error("数据读取缓存时失败",e);
		}

	}

	public Map<String, Object> getSjklxAndLjlx(Map<String, Object> map) {

		return (Map<String, Object>)this.sjkpzDao.getSjklxAndLjlx(map);
	}

	public boolean sjkfwqIfUsed(int pzxh) {

		int n = (Integer)this.sjkpzDao.sjkfwqIfUsed(pzxh);

		if(n>=1){
			return true;
		}

		return false;
	}

	public void setSjkpzDao(SjkpzDao sjkpzDao){

		this.sjkpzDao = sjkpzDao;
	}

	/**
	 * @param transactionTemplate the transactionTemplate to set
	 */
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public List getSjkpz() {
		return this.sjkpzDao.getSjkpz();
	}

	public SjkpzWrapper getSjkpzDetail(String pzxh) {
		log.error("----------------" + pzxh +"------------------------");
		List<SjkpzWrapper> wrapperList = this.sjkpzDao.getSjkpzDetail(pzxh);
		if (wrapperList != null && wrapperList.size() > 0) {
			return wrapperList.get(0);
		}
		return new SjkpzWrapper();
		//return this.sjkpzDao.getSjkpzDetail(pzxh).get(0);
	}
	/**
	 * @param cache the cache to set
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public List<SjkpzWrapper> getAllJKSjkpz() {
		return this.sjkpzDao.getAllJKSjkpz();
	}

}