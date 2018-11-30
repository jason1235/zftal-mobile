package com.zfsoft.hrm.externaltea.base.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.externaltea.base.entity.WpjsDeclare;
import com.zfsoft.hrm.externaltea.base.query.WpjsDeclareQuery;
import com.zfsoft.hrm.externaltea.base.service.IWpjsDeclareService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;

/** 
 * @author xiaoyj
 * @date 2013-5-16 下午02:22:08 
 *  
 */
public class WpjsDeclareAction extends HrmAction {

	private static final long serialVersionUID = 6469394187549730522L;
	private IWpjsDeclareService wpjsDeclareService;
	private PageList<WpjsDeclare> pageList=new PageList<WpjsDeclare>();
	private WpjsDeclare declare = new WpjsDeclare();
	private WpjsDeclareQuery query = new WpjsDeclareQuery();

	private String sortFieldName = null;
	private String asc = "up";
	

	public IWpjsDeclareService getWpjsDeclareService() {
		return wpjsDeclareService;
	}

	public void setWpjsDeclareService(IWpjsDeclareService wpjsDeclareService) {
		this.wpjsDeclareService = wpjsDeclareService;
	}


	public void setPageList(PageList<WpjsDeclare> pageList) {
		this.pageList = pageList;
	}
	
	public String input() {
		return "input";
	}
	
	//外聘兼职教师列表页面
	public String page() throws Exception{	
		pageList = wpjsDeclareService.getPageList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		return "page";
	}
	
	//外聘兼职教师增加后保存操作
	public String save() throws Exception{
		declare.setCjsj((new Date()));
		declare.setXgsj((new Date()));				
		wpjsDeclareService.save(declare);
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	//外聘兼职教师删除操作
	public String delete() throws Exception{
		wpjsDeclareService.delete(declare.getId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	//外聘兼职教师点修改后的操作
	public String modify() throws Exception{
		declare = wpjsDeclareService.getById(declare.getId());
		return "modify";
	}
	//外聘兼职教师统计查看详细信息操作
	public String view() throws Exception{
		declare = wpjsDeclareService.getById(declare.getId());
		return "view";
	}
	
	//外聘兼职教师修改后的保存操作
	public String update() throws Exception{
		wpjsDeclareService.update(declare);
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	//外聘兼职教师分类查询操作
	public String search() throws Exception{	
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " XM" );
		}
		pageList = wpjsDeclareService.findWpjsDeclare(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex",beginIndex);
		return "page";
	}	
	
	//外聘兼职教师统计查询操作
	public String allSearch() throws Exception{	
		query.setBmdm(query.getBmdm());	
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " XM" );
		}
		pageList = wpjsDeclareService.findWpjsDeclareByTime(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex",beginIndex);
		return "list";
	}
	
	public String list() throws Exception{
		query.setXm(declare.getXm());
		query.setBmdm(declare.getBmdm());
		query.setZyjszw(declare.getZyjszw());
		query.setXl(declare.getXl());
		query.setXw(declare.getXw());
		
		pageList = wpjsDeclareService.getPageList(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		getValueStack().set("code", loadCodeInPage());
		return "list";
	}
	
	private Object loadCodeInPage() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("bmdm", ICodeConstants.DM_DEF_ORG);
		return map;
	}

	public WpjsDeclareQuery getQuery() {
		return query;
	}

	public void setQuery(WpjsDeclareQuery query) {
		this.query = query;
	}

	public PageList<WpjsDeclare> getPageList() {
		return pageList;
	}

	public void setFpaDeclareService(IWpjsDeclareService fpaDeclareService) {
		this.wpjsDeclareService = fpaDeclareService;
	}

	public WpjsDeclare getDeclare() {
		return declare;
	}

	public void setDeclare(WpjsDeclare declare) {
		this.declare = declare;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}	
}
