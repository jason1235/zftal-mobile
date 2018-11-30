package com.zfsoft.hrm.baseinfo.search.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @ClassName: ConditionDefinedAction 
 * @author jinjj
 * @date 2012-6-18 下午06:56:37 
 *  
 */
public class ConditionDefinedAction extends HrmAction implements ModelDriven<Condition> {

	private static final long serialVersionUID = -3362331841598892783L;
	private Condition model = new Condition();
	private Condition root = new Condition();
	private ConditionQuery query= new ConditionQuery();
	private List<Condition> list = new ArrayList<Condition>();
	
	private String id;//guid字符串
	private String order;//index字符串 （index为int型）
	private String type;//type 条件类型区别,教师和学生
	
	private IConditionService conditionService;
	
	@SuppressWarnings("unchecked")
	public String rootList(){
		String stype=getString("type");
		if(!"".equals(stype)){
			type=stype;
			query.setType(type);
		}
		query.setParentId( IConstants.ROOT );
		PageList pageList = conditionService.getPagingInfo(query);
		list=pageList;
		return "root_list";
	}
	
	public String rootEdit()throws Exception{
		model = conditionService.getById(model.getGuid());
		return "root_edit";
	}
	
	public String rootInput()throws Exception{
		String stype=this.getRequest().getParameter("type");
		if(!"".equals(stype)){
			type=stype;
			model.setType(type);
		}
		return "root_edit";
	}
	
	public String save()throws Exception{
		String s=query.getType();
		if(!"".equals(s)){
		 model.setType(s);
		}
		
		if(IConstants.ROOT.equals(model.getParentId())){
			conditionService.addCatalog(model);
		}else{
			if(StringUtils.isEmpty(model.getParentId())){
				model.setParentId(query.getParentId());
			}
			conditionService.addItem(model);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String itemEdit()throws Exception{
		model = conditionService.getById(model.getGuid());
		root = conditionService.getById(model.getParentId());
		return "item_edit";
	}
	
	public String itemInput()throws Exception{
		root = conditionService.getById(query.getParentId());
		return "item_edit";
	}
	
	public String update()throws Exception{
		conditionService.modifyItem(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateOrder()throws Exception{
		//TODO 更新序列
		if(StringUtils.isEmpty(id))
			throw new RuleException("条件编号未接收到");
		if(StringUtils.isEmpty(order))
			throw new RuleException("条件序号未接收到");
		String[] guid = id.split(",");
		String[] index = order.split(",");
		if(guid.length!=index.length)
			throw new RuleException("序列数据检测不匹配，移动失败");
		for(int i=0;i<guid.length;i++){
			Condition con = new Condition();
			con.setGuid(guid[i].trim());
			con.setIndex(Integer.valueOf(index[i].trim()));
			list.add(con);
		}
		conditionService.updateOrder(list);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String itemList()throws Exception{
		//TODO 查询列表
		
		list = conditionService.getFullList(query);
		//root = conditionService.getById(query.getParentId());
		return "item_list";
	}
	
	public String getChildren()throws Exception{
		list = conditionService.getFullList(query);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", list);
		//map.put("root", root);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String delete()throws Exception{
		conditionService.removeItem(model.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	@Override
	public Condition getModel() {
		return model;
	}

	public ConditionQuery getQuery() {
		return query;
	}

	public void setQuery(ConditionQuery query) {
		this.query = query;
	}

	public void setModel(Condition model) {
		this.model = model;
	}

	public void setConditionService(IConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public List<Condition> getList() {
		return list;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Condition getRoot() {
		return root;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
