package com.zfsoft.hrm.baseinfo.search.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearch;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.query.CommonSearchQuery;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.ICommonSearchService;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.Direction;

/**
 * 通用查询
 * @ClassName: CommonSearchAction 
 * @author jinjj
 * @date 2012-7-5 上午10:50:10 
 *
 */
public class CommonSearchAction extends HrmAction implements ModelDriven<CommonSearch> {
	
	private static final long serialVersionUID = -1664087702758245170L;
	
	private CommonSearch model = new CommonSearch();
	
	private CommonSearchQuery query = new CommonSearchQuery();
	
	private ConditionQuery cquery= new ConditionQuery();
	
	private ICommonSearchService commonSearchService;
	
	private IConditionService conditionService;
	
	private List<Condition> list;
	
	private String conditionId;
	
	private PageList<CommonSearch> pageList;
	
	private String type;//type 条件类型区别,教师和学生
	
	/**
	 * 主页面
	 * @return
	 */
	public String primary() {
		try {
			model = commonSearchService.getCountData(model.getGuid());
			list = model.getConditions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "primary";
	}
	
	/**
	 * 列表页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		ConditionQuery  cquery=new ConditionQuery();
		String stype=getString("type");
		if(!"".equals(stype)){
			type=stype;
			query.setType(type);
			cquery.setType(type);
		}
		pageList = commonSearchService.getSearchsPagingInfo(query);
		
		list = conditionService.getCatalogList(cquery);
		return "list";
	}

	/**
	 * 编辑页面
	 * @return
	 */
	public String update() {
		model.setConditions(createConditionList());
		commonSearchService.modifySearch(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 新增
	 */
	public String input(){
		String stype=this.getRequest().getParameter("type");
		if(!"".equals(stype)){
			type=stype;
			cquery.setType(type);
		}
		list = conditionService.getCatalogList(cquery);
		return "edit";
	}
	
	/**
	 * 查询 
	 */
	public String query(){
		model = commonSearchService.getSearch(model.getGuid());
		StringBuilder sb = new StringBuilder();
		for(Condition con : model.getConditions()){
			if(sb.length()>0){
				sb.append(",");
			}
			sb.append(con.getGuid());
		}
		conditionId = sb.toString();
		return "edit";
	}
	
	/**
	 * 添加操作
	 * @return
	 */
	public String save() {
		model.setConditions(createConditionList());
		model.setType(query.getType());
		commonSearchService.addSearch(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	//解析条件配置编号序列
	private List<Condition> createConditionList(){
		List<Condition> list = new ArrayList<Condition>();
		if(!StringUtils.isEmpty(conditionId)){
			String[] ids = conditionId.split(",");
			for(String id : ids){
				Condition con = new Condition();
				con.setGuid(id.trim());
				list.add(con);
			}
		}
		return list;
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public String remove() {
		commonSearchService.removeSearch( model.getGuid() );
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 查询二级信息
	 * @return
	 */
	public String subs(){
		List<Condition> list = commonSearchService.getSubs(conditionId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", list);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	/**
	 * 向上移动
	 * @return
	 * @throws Exception
	 */
	public String moveUp() throws Exception{
		commonSearchService.doMenuMove(model.getGuid(), Direction.UP);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 向下移动
	 * @return
	 * @throws Exception
	 */
	public String moveDown() throws Exception{
		commonSearchService.doMenuMove(model.getGuid(), Direction.DOWN);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	@Override
	public CommonSearch getModel() {
		return model;
	}

	public void setCommonSearchService(ICommonSearchService commonSearchService) {
		this.commonSearchService = commonSearchService;
	}

	public CommonSearchQuery getQuery() {
		return query;
	}

	public void setQuery(CommonSearchQuery query) {
		this.query = query;
	}

	public List<Condition> getList() {
		return list;
	}

	public void setConditionService(IConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getConditionId() {
		return conditionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PageList<CommonSearch> getPageList() {
		return pageList;
	}

}
