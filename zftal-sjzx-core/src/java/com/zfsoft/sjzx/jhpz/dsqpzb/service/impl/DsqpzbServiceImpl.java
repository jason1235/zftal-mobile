package com.zfsoft.sjzx.jhpz.dsqpzb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.sjzx.common.util.DateTimeUtil;
import com.zfsoft.sjzx.jhpz.dsqpzb.dao.DsqpzbDao;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.Dsqpzb;
import com.zfsoft.sjzx.jhpz.dsqpzb.model.DsqpzbWrapper;
import com.zfsoft.sjzx.jhpz.dsqpzb.service.DsqpzbService;
import com.zfsoft.sjzx.jhpz.swhpz.dao.GzzpzDao;
import com.zfsoft.sjzx.jhpz.swhpz.model.Gzzpz;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;

public class DsqpzbServiceImpl implements DsqpzbService{

	private DsqpzbDao dsqpzbDao;

	private GzzpzDao gzzpzDao;

	private TransactionTemplate transactionTemplate;

	public Dsqpzb getDsqpzb(Dsqpzb dsqpzb){

		return this.dsqpzbDao.getDsqpzb(dsqpzb).get(0);
	}

	public Object insertDsqpzb(Dsqpzb dsqpzb){
		this.dsqpzbDao.insertDsqpzb(dsqpzb);
		return this.dsqpzbDao.getInsertDsqpzb();

		//return this.dsqpzbDao.insertDsqpzb(dsqpzb);
	}

	public Object updateDsqpzb(final Dsqpzb dsqpzb){

		return this.transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus ts) {
				Object object = null;
				try{
					Gzzpz gzzpz = new Gzzpz();
					gzzpz.setGzzbh(dsqpzb.getDxlsh());
					gzzpz.setXgsj(dsqpzb.getXgsj());
					gzzpz.setXgr(dsqpzb.getXgr());
					gzzpzDao.updateGzz(gzzpz);
					dsqpzbDao.updateDsqpzb(dsqpzb);
					object = "定时器配置表修改成功";
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
					return null;
				}
				return object;
			}
		});
	}

	public Object deleteDsqpzb(final Dsqpzb dsqpzb){

		return this.transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus ts) {
				Object object = null;
				try{
					Gzzpz gzzpz = new Gzzpz();
					gzzpz.setGzzbh(dsqpzb.getDxlsh());
					gzzpz.setXgsj(dsqpzb.getXgsj());
					gzzpz.setXgr(dsqpzb.getXgr());
					gzzpzDao.updateGzz(gzzpz);
					dsqpzbDao.deleteDsqpzb(dsqpzb);
					object = "定时器删除成功";
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
					return null;
				}
				return object;
			}
		});
	}
	public Object deleteDsqpzb1(final Dsqpzb dsqpzb){

		return this.transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus ts) {
				Object object = null;
				try{
					dsqpzbDao.deleteDsqpzb(dsqpzb);
					object = "定时器删除成功";
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
					return null;
				}
				return object;
			}
		});
	}
	public Object deleteDsqpzbEtl(Dsqpzb dsqpzb){
		dsqpzbDao.deleteDsqpzb(dsqpzb);
		return  "定时器删除成功";
	}
	public PageList<Dsqpzb> getPagingInfo(Dsqpzb dsqpzb){
		PageList<Dsqpzb> pageList = new PageList<Dsqpzb>();
		Paginator paginator = new Paginator();
		if(dsqpzb!=null){
			paginator.setItemsPerPage(dsqpzb.getPerPageSize());
			paginator.setPage((Integer)dsqpzb.getToPage());
			paginator.setItems(dsqpzbDao.getDsqpzbListCount(dsqpzb));
			pageList.setPaginator(paginator);

			if(paginator.getBeginIndex() <= paginator.getItems()){
				dsqpzb.setStartRow(paginator.getBeginIndex());
				dsqpzb.setEndRow(paginator.getEndIndex());
				List<Dsqpzb> list = dsqpzbDao.getDsqpzbList(dsqpzb);
				pageList.addAll(list);
			}
		}
		return pageList;

		//return this.dsqpzbDao.getPagingInfo(dsqpzb);
	}

	public void setDsqpzbDao(DsqpzbDao dsqpzbDao){

		this.dsqpzbDao = dsqpzbDao;
	}

	public Object saveDsqpzbBatch(List<Dsqpzb> list){
		for(Dsqpzb dsqpzb : list){
			this.dsqpzbDao.insertDsqpzb(dsqpzb);
		}
		return this.dsqpzbDao.getInsertDsqpzb();
		//return this.dsqpzbDao.saveDsqpzbBatch(list);
	}

	public List<DsqpzbWrapper> getRunSet(int jhpzxh) {
		return this.dsqpzbDao.getRunSet(jhpzxh);
	}

	public List<DsqpzbWrapper> getRunSetBywjbh(int wjbh) {
		return this.dsqpzbDao.getRunSetBywjbh(wjbh);
	}

	public void deleteDsqpzbByDxlsh(Dsqpzb dsqpzb){
		this.dsqpzbDao.deleteDsqpzbByDxlsh(dsqpzb);
	}

	/*public int getDsqxh() {
		return this.dsqpzbDao.getDsqxh();
	}*/

	public Object updataDsqpzStatus(Dsqpzb dsqpzb){
		this.dsqpzbDao.updataDsqpzStatus(dsqpzb);
		return dsqpzb.getDsqzt();
	}

	public int getDsqpzbByZt(Dsqpzb dsqpzb){
		return this.dsqpzbDao.getDsqpzbByZt(dsqpzb);
	}

	public Object closeDsq(Dsqpzb dsqpzb) {
		this.dsqpzbDao.closeDsq(dsqpzb);
		return "更新定时器状态成功";
	}

	/**
	 * @param transactionTemplate the transactionTemplate to set
	 */
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	/**
	 * @param gzzpzDao the gzzpzDao to set
	 */
	public void setGzzpzDao(GzzpzDao gzzpzDao) {
		this.gzzpzDao = gzzpzDao;
	}

	/**
	 * <p>Description: </p>
	 * @return
	 *
	 * @since 2012-12-12 上午10:20:53
	 * @author liuchaoyong
	 */
	public List<Dsqpzb> getRunTasks() {

		return this.dsqpzbDao.getRunTasks();
	}

	public void updateLastRunTime(Dsqpzb dsqpzb) {
		dsqpzb.setXgsj(DateTimeUtil.getCurrDateTimeStr());
		this.dsqpzbDao.updateLastRunTime(dsqpzb);
	}

	/*public PageList getRunTasksPagingInfo(Dsqpzb dsqpzb) {

		 return this.dsqpzbDao.getRunTasksPagingInfo(dsqpzb);
	}*/

	public Object updateDsqpzb1(final Dsqpzb dsqpzb) {


		return this.transactionTemplate.execute(new TransactionCallback<Object>(){
			public Object doInTransaction(TransactionStatus ts) {
				Object object = null;
				try{
					dsqpzbDao.updateDsqpzb(dsqpzb);
					object = "定时器配置表修改成功";
				}catch(Exception e){
					e.printStackTrace();
					ts.setRollbackOnly();
					return null;
				}
				return object;
			}
		});

	}

	public List getRunSetBydsqxh(int dsqxh) {

		return this.dsqpzbDao.getRunSetBydsqxh(dsqxh);
	}

	public List<Dsqpzb> getXtjkSjjhrzb() {

		return this.dsqpzbDao.getXtjkSjjhrzb();
	}

	public int updateDsqpzbIsjk(Dsqpzb dsqpzb) {

		this.dsqpzbDao.updateDsqpzbIsjk(dsqpzb);
		return 1;
	}
}