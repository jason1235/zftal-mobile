package com.zfsoft.hrm.baseinfo.snapshot.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.snapshot.entities.Snapshot;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;

/** 
 * 快照数据
 * @author jinjj
 * @date 2012-11-6 上午10:33:54 
 *  
 */
public class SnapDataAction extends HrmAction {

	Logger log = LoggerFactory.getLogger(SnapDataAction.class);
	private static final long serialVersionUID = -3698650094069337430L;
	private SnapshotQuery query = new SnapshotQuery(null);
	private ISnapshotService snapshotService;
	private PageList<Snapshot> pageList;
	private List<InfoClass> infoList;
	private String gh;
	private String classId;
	
	public String page() throws Exception{
		infoList = InfoClassCache.getInfoClasses();
		InfoClass clazz = null;
		if(StringUtil.isEmpty(classId)){
			clazz = infoList.get(0);
		}else{
			for(InfoClass c : infoList){
				if(c.getGuid().equals(classId)){
					clazz = c;
					break;
				}
			}
		}
		if(clazz == null){
			log.info("信息类未找到，ID不匹配:"+classId);
			throw new RuleException("信息类未找到");
		}
		query.setClazz(clazz);
		query.setParam("gh", gh);
		pageList = snapshotService.getPagingList(query);
		return "page";
	}
	
	public String view() throws Exception{
		infoList = InfoClassCache.getInfoClasses();
		return "view";
	}

	public SnapshotQuery getQuery() {
		return query;
	}

	public void setQuery(SnapshotQuery query) {
		this.query = query;
	}

	public PageList<Snapshot> getPageList() {
		return pageList;
	}

	public List<InfoClass> getInfoList() {
		return infoList;
	}

	public void setSnapshotService(ISnapshotService snapshotService) {
		this.snapshotService = snapshotService;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
}
