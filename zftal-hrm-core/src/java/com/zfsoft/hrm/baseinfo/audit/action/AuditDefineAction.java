package com.zfsoft.hrm.baseinfo.audit.action;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.entity.AuditConfigInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;
import com.zfsoft.hrm.baseinfo.audit.service.IAuditDefineService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;

/** 
 * 审核定义
 * @author jinjj
 * @date 2012-9-28 下午04:39:36 
 *  
 */
public class AuditDefineAction extends HrmAction {

	private static final long serialVersionUID = -6401061447372006724L;

	private IAuditDefineService defineService;
	
	private List<AuditConfigInfo> infoList;
	private List<AuditDefine> defineList;

	private AuditDefineQuery query = new AuditDefineQuery();
	private AuditDefine define;
	private InfoClass clazz;
	
	public String list() throws Exception{
		infoList = defineService.getPagingList(query);
		return "list";
	}
	
	public String edit() throws Exception{
		clazz = InfoClassCache.getInfoClass(define.getClassId());
		return "edit";
	}
	
	public String info() throws Exception{
		defineList = defineService.getList(query);
		return "info";
	}
	
	public String save() throws Exception{
		defineService.save(define);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		defineService.delete(define.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public AuditDefineQuery getQuery() {
		return query;
	}

	public void setQuery(AuditDefineQuery query) {
		this.query = query;
	}

	public AuditDefine getDefine() {
		return define;
	}

	public void setDefine(AuditDefine define) {
		this.define = define;
	}

	public List<AuditConfigInfo> getInfoList() {
		return infoList;
	}

	public List<AuditDefine> getDefineList() {
		return defineList;
	}

	public InfoClass getClazz() {
		return clazz;
	}

	public void setDefineService(IAuditDefineService defineService) {
		this.defineService = defineService;
	}
	
}
