package com.zfsoft.mobile.helpguide.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.helpguide.entity.HelpGuideEntity;
import com.zfsoft.mobile.helpguide.query.HelpGuideQuery;
import com.zfsoft.mobile.helpguide.service.IHelpGuideService;
import com.zfsoft.mobile.news.entity.Counts;

public class HelpGuideAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MAX_HELPGUIDES_HOT = "max_helpg_hot";
	
	private String op;
	private HelpGuideEntity model=new HelpGuideEntity();
	private HelpGuideQuery query=new HelpGuideQuery();
	private IHelpGuideService helpGService;
	@Autowired
	private INewsConfigService newsConfigService;
	private PageList<HelpGuideEntity> pageList;
	
	public String list(){
		pageList=helpGService.getPageList(query);
		return "list";
	}
	
	public String toAdd(){
		op = "add";
		return "edit";
	}
	
	public String save(){
		if(StringUtils.isBlank(query.getId())){
			this.setSuccessMessage("成功插入数据！");
		}else{
			this.setSuccessMessage("成功修改数据！");
		}
		helpGService.saveOrUpdate(query);
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String toModify(){
		List<HelpGuideEntity> list = helpGService.getPageList(query);
		if(list!=null&&list.size()>0){
			op="modify";
			model = list.get(0);
		}
		return "edit";
	}
	
	
	public String remove(){
		if(StringUtils.isNotBlank(query.getId())){
			helpGService.remove(query.getId());
			this.setSuccessMessage("成功删除数据！");
		}
		this.getValueStack().set("data", this.getMessage());
		return "data";
	}
	
	public String updateIndex(){
		String[] ids = getRequest().getParameterValues("ids");
		String minStr = getRequest().getParameter("minIndex");
		int min =0;
		if (StringUtils.isNotBlank(minStr)) {
			min = Integer.parseInt(minStr);
		}
		Map<String, String> map = null;
		if (ids != null) {
			try {
				for (int i =0; i <ids.length; i++) {
					map = new HashMap<String, String>();
					map.put("index", (i+min)+"");
					map.put("id", ids[i]);
					helpGService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list();
	}
	
	public String control(){
		Counts counts = new Counts();
		if ("1".equals(query.getIsHot())){
			if(StringUtils.equals("0", query.getIsPlayed())){
				this.setErrorMessage("当前该帮助说明为停用状态，请先启用");
				this.getValueStack().set("data", getMessage());
				return "data";
			}
			counts = helpGService.getRmdCount(query);
			int maxHot=getMax(MAX_HELPGUIDES_HOT);
			if(counts.getRmdCount() >= maxHot){
				this.setErrorMessage("热门推荐数不能超过最大值"+maxHot);
				this.getValueStack().set("data", getMessage());
				return "data";
			}
		}
		int res = helpGService.guideControl(query);
		if(res>0){
			this.setSuccessMessage("操作成功");
		}else{
			this.setErrorMessage("操作失败");
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	private int getMax(String key) {
		NewsConfigQuery configQuery = new NewsConfigQuery();
		configQuery.setKey(key);
		NewsConfig config = newsConfigService.findByKey(configQuery);
		int maxTop = 9999;
		if (config != null) {
			String maxTopValue = config.getValue();
			if (maxTopValue != null) {
				try {
					maxTop = Integer.parseInt(maxTopValue);
				} catch (Exception e) {
				}
				
			}
		}
		return maxTop;
	}

	public String toView(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List<HelpGuideEntity> hotList = new ArrayList<HelpGuideEntity>();
		String searchParam = request.getParameter("search");
		String queryData = request.getParameter("title");
		if(StringUtils.isNotBlank(queryData)){
			query.setTitle(queryData);
		}
		
		pageList=helpGService.getPageList(query);
		
		if(StringUtils.equals("1", searchParam)){
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("success", true);
			data.put("viewList", pageList);
			//getValueStack().set("viewList", JSONUtils.obj2json(pageList));
			getValueStack().set(DATA, data);
			searchParam=null;
			return DATA;
		}else{
			if(pageList!=null&&pageList.size()>0){
				for (HelpGuideEntity helpGuideEntity : pageList) {
					if(StringUtils.equals("1", helpGuideEntity.getIsHot())){
						hotList.add(helpGuideEntity);
					}
				}
			}
		}
		
		getValueStack().set("hotList", JSONUtils.obj2json(hotList));
		getValueStack().set("viewList", JSONUtils.obj2json(pageList));
		this.getValueStack().set("data", getMessage());
		return "view";
	}
	
	public String toViewmore(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String queryData = request.getParameter("title");
		if(StringUtils.isNotBlank(queryData)){
			query.setTitle(queryData);
		}
		query.setPerPageSize(20);
		
		pageList=helpGService.getPageList(query);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("viewList2", pageList);
		getValueStack().set(DATA, data);
		return DATA;
	}
	
	public String toViewCheck(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			query.setId(id);
		}
		HelpGuideEntity entity=helpGService.findById(query);
		if(entity==null){
			this.setErrorMessage("没有设置可以显示的详情数据!");
		}else{
			this.setSuccessMessage("检测成功");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toViewdetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			query.setId(id);
		}
		model=helpGService.findById(query);
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String a1=dateformat1.format(model.getCreateTime());
		getValueStack().set("createtime", a1);
		getValueStack().set(DATA, getMessage());
		return "viewdetail";
	}
	
	public HelpGuideEntity getModel() {
		return model;
	}

	public void setModel(HelpGuideEntity model) {
		this.model = model;
	}

	public HelpGuideQuery getQuery() {
		return query;
	}

	public void setQuery(HelpGuideQuery query) {
		this.query = query;
	}

	public IHelpGuideService getHelpGService() {
		return helpGService;
	}

	public void setHelpGService(IHelpGuideService helpGService) {
		this.helpGService = helpGService;
	}

	public PageList<HelpGuideEntity> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<HelpGuideEntity> pageList) {
		this.pageList = pageList;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}


}
