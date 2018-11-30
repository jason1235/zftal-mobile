package com.zfsoft.hrm.baseinfo.snapshot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface.ISnapshotDao;
import com.zfsoft.hrm.baseinfo.snapshot.entities.ProgressInfo;
import com.zfsoft.hrm.baseinfo.snapshot.entities.Snapshot;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLog;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLogDetail;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogDetailService;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogService;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotService;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;
import com.zfsoft.hrm.baseinfo.table.util.TableUtil;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * 快照服务实现
 * @ClassName: SnapshotServiceImpl 
 * @author jinjj
 * @date 2012-7-18 上午10:16:44 
 *  
 */
public class SnapshotServiceImpl implements ISnapshotService {

	private ISnapshotDao snapshotDao;
	private ISnapshotLogService snapshotLogService;
	private ISnapshotLogDetailService snapshotLogDetailService;
	
	@Override
	public void doSnapshot(Date snapTime) {
		try{
			checkExisted(snapTime);
			List<InfoClass> list = InfoClassCache.getInfoClasses( IConstants.INFO_CATALOG_TYPE_DEFAULT );
			list.add(InfoClassCache.getOverallInfoClass(IConstants.INFO_CATALOG_TYPE_DEFAULT));
			if(isManual()){
				HttpSession s = SessionFactory.getSession();
				ProgressInfo progress = new ProgressInfo();
				progress.setTotalStep( list.size() );
				s.setAttribute(IConstants.SESSION_PROGRESS, progress);
			}
			int cnt = 0;
			ITableService tableService = SpringHolder.getBean("oracleTableService",ITableService.class);
			for(InfoClass clazz:list){
				//判断信息类是否存在快照表
				if(!tableService.isExist(clazz.getIdentityName()+"_snap")){
					continue;
				}
				Snapshot snap = new Snapshot(clazz);
				snap.setSnapTime(snapTime);
				int count = snapshotDao.insert(snap);
				SnapshotLogDetail detail = new SnapshotLogDetail();
				detail.setClazz(clazz);
				detail.setTableSize(count);
				detail.setSnapTime(snapTime);
				detail.setOperateTime(new Date());
				snapshotLogDetailService.save(detail);
				
				try {
					Thread.sleep(200L);
				} catch (InterruptedException e) {
				}
				cnt++;
				if(isManual()){
					HttpSession s = SessionFactory.getSession();
					ProgressInfo progress = (ProgressInfo)s.getAttribute(IConstants.SESSION_PROGRESS);
					progress.setStepIndex(cnt);
					progress.setDescription(clazz.getName());
					s.setAttribute(IConstants.SESSION_PROGRESS, progress);
				}
			}
			doLog(snapTime);
		}catch (Exception e) {
			if(isManual()){
				HttpSession s = SessionFactory.getSession();
				ProgressInfo progress = (ProgressInfo)s.getAttribute(IConstants.SESSION_PROGRESS);
				progress.setStepIndex(-1);
				if(e instanceof RuleException)
					progress.setDescription(e.getMessage());
				else
					progress.setDescription("系统异常");
				s.setAttribute(IConstants.SESSION_PROGRESS, progress);
			}
			e.printStackTrace();
		}finally{
			clearProcessInfo();
		}
	}

	/** 
	 * 清理此用户session中的进度条信息
	 */
	private void clearProcessInfo() {
		try {
			Thread.sleep(500L);//清理SESSION
			if(isManual()){
				HttpSession s = SessionFactory.getSession();
				s.setAttribute(IConstants.SESSION_PROGRESS,new ProgressInfo());
			}
		} catch (InterruptedException e) {
		}
	}
	
	private void checkExisted(Date snapTime){
		SnapshotLogQuery query = new SnapshotLogQuery();
		query.setSnapTime(snapTime);
		List<SnapshotLog> logList = snapshotLogService.getList(query);
		if(logList.size()>0){
			throw new RuleException("该快照已存在");
		}
	}
	
	private void doLog(Date snapTime){
		SnapshotLog log = new SnapshotLog();
		if(isManual()){
			User user = SessionFactory.getUser();
			if(user != null){
				log.setOperator(user.getXm());
			}
		}
		log.setSnapTime(snapTime);
		snapshotLogService.save(log);
	}
	
	private boolean isManual(){
		try{//SESSION不为空时为手动执行
			SessionFactory.getUser();
			return true;
		}catch (Exception e) {
			//当系统自动执行时，无SESSION
			return false;
		}
	}

	@Override
	public void delete(Date snapTime) {
		List<InfoClass> list = InfoClassCache.getInfoClasses( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		list.add(InfoClassCache.getOverallInfoClass(IConstants.INFO_CATALOG_TYPE_DEFAULT));
		for(InfoClass clazz:list){
			Snapshot snap = new Snapshot(clazz);
			snap.setSnapTime(snapTime);
			snapshotDao.delete(snap);
		}
		snapshotLogService.delete(snapTime);
		snapshotLogDetailService.delete(snapTime);
	}
	
	public PageList<Snapshot> getPagingList(SnapshotQuery query){
		PageList<Snapshot> pageList = new PageList<Snapshot>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems( snapshotDao.getPagingCount(query) );
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				//获得DynaBean集合
				List<Snapshot> beans = new ArrayList<Snapshot>();
				List<Map<String, Object>> list = snapshotDao.getPagingList( query );
				for (Map<String, Object> map : list) {
					Snapshot bean = new Snapshot( query.getClazz() );
					bean.setValues( map );
					bean.setSnapTime((Date)map.get("SNAP_TIME"));
					beans.add(bean);
				}
				pageList.addAll(beans);
			}
		}
		return pageList;
	}

	public void setSnapshotDao(ISnapshotDao snapshotDao) {
		this.snapshotDao = snapshotDao;
	}

	public void setSnapshotLogService(ISnapshotLogService snapshotLogService) {
		this.snapshotLogService = snapshotLogService;
	}

	public void setSnapshotLogDetailService(
			ISnapshotLogDetailService snapshotLogDetailService) {
		this.snapshotLogDetailService = snapshotLogDetailService;
	}
	
}
