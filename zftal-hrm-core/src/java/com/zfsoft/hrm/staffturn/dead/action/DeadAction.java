package com.zfsoft.hrm.staffturn.dead.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.staffturn.dead.entities.DeadInfo;
import com.zfsoft.hrm.staffturn.dead.query.DeadInfoQuery;
import com.zfsoft.hrm.staffturn.dead.service.svcinterface.IDeadService;
import com.zfsoft.util.base.StringUtil;

/**
 * 离世
 * @author   沈鲁威 Patrick Shen
 * @since   2012-7-30
 * @version   V1.0.0
 */
public class DeadAction extends HrmAction implements ModelDriven<DeadInfo>{

	private static final long serialVersionUID = -4074331425973887767L;
	
	private IDeadService deadService;

	private DeadInfo model=new DeadInfo();
	
	private DeadInfoQuery query=new DeadInfoQuery();
	
	private String sortFieldName=null;
	private String asc="up";
	

	/**
	 * 初始化选中框的数据源
	 */
	private void initList() {
		getValueStack().set("code", loadCodeInPage());		
	}
	/**
	 * 加载页面中使用的代码编号
	 * @return
	 */
	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("deptCode", ICodeConstants.DM_DEF_ORG);
		map.put("sexCode", ICodeConstants.SEX);
		map.put("dutyCode", ICodeConstants.ADMIN_DUTY_LEVEL);
		map.put("retireTypeCode", ICodeConstants.RETIRE_TYPE);
		// 20140422 add start
		map.put("ryztCode", ICodeConstants.DM_GB_LSRYZTDMB);
		// 20140422 add end
		return map;
	}
	/**
	 * 查询，展示离世查询界面
	 * @return
	 */
	public String list() {
		initList();
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "userId" );
		}
		this.getValueStack().set("deadInfoList",deadService.getDeadInfoList(query));
		return LIST_PAGE;
	}

	/**
	 * 增加动作，展现工号输入界面，展现退休信息修改界面
	 * @return
	 */
	public String add() {
		query.setOpType("add");
		//展现输入
		return "add";
	}

	/**
	 * 修改动作，展现退休信息修改界面
	 * @return
	 */
	public String modify() {
		query.setOpType("modify");
		model=deadService.getDeadInfoByUserId(query.getUserId());
		return EDIT_PAGE;
	}

	/**
	 * 保存动作，返回保存结果
	 * @return
	 */
	public String save() {
		deadService.saveDeadInfo(model, query.getOpType());
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 离世人员删除
	 * @return
	 */
	public String delete() {
		query.setQueryClass(DeadInfo.class);
		deadService.removeDeadInfo(query);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	@Override
	public DeadInfo getModel() {
		return model;
	}

	public DeadInfoQuery getQuery() {
		return query;
	}

	public void setQuery(DeadInfoQuery query) {
		this.query = query;
	}
	
	public void setDeadService(IDeadService deadService) {
		this.deadService = deadService;
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
