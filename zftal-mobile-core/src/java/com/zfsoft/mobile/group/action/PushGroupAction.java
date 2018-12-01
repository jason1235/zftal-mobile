package com.zfsoft.mobile.group.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.group.entity.PushGroup;
import com.zfsoft.mobile.group.entity.PushGroupMember;
import com.zfsoft.mobile.group.query.PushGroupQuery;
import com.zfsoft.mobile.group.service.IPushGroupService;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-21
 * @version V1.0.0
 */
public class PushGroupAction extends HrmAction{
	private static final long serialVersionUID = -5471857286235169700L;
	
	private IPushGroupService pushGroupService;
	private PushGroupQuery query = new PushGroupQuery();
	private PushGroup model = new PushGroup();
	private PushGroupMember member = new PushGroupMember();
	private PushGroupMember yimember = new PushGroupMember();
	private PageList<PushGroup> pageList = new PageList<PushGroup>();
	private PageList<PushGroupMember> wfpMemberPageList = new PageList<PushGroupMember>();
	private List<PushGroupMember> yfpMemberPageList = new PageList<PushGroupMember>();
	private IJsglService jsglService;
	String[] weiuserId;
	String[] yiuserId;
	
	/**
	 * 为当前角色增加门户服务选项
	 * @return
	 */
	public String addUser(){
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> tids = new ArrayList<String>();
        for (String idCheck : weiuserId) {
            tids.add(idCheck.trim());
            
        }
        param.put("tids", tids);
        param.put("modelid", model.getId());
        pushGroupService.insertUser(param);
    	this.setMessage(true, "成功为当前角色分配服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }
	
	/**
	 * 删除当前角色某些服务
	 * @return
	 */
    public String removeUser(){
    	Map<String, Object> param = new HashMap<String, Object>();
		List<String> tids = new ArrayList<String>();
        for (String idCheck : yiuserId) {
            tids.add(idCheck.trim());
            
        }
        param.put("tids", tids);
        param.put("modelid", model.getId());
        pushGroupService.deleteUser(param);
    	this.setMessage(true, "成功为当前角色删除服务");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }
	
	
	
	public IPushGroupService getPushGroupService() {
		return pushGroupService;
	}

	public String[] getWeiuserId() {
		return weiuserId;
	}

	public void setWeiuserId(String[] weiuserId) {
		this.weiuserId = weiuserId;
	}

	public String[] getYiuserId() {
		return yiuserId;
	}

	public void setYiuserId(String[] yiuserId) {
		this.yiuserId = yiuserId;
	}

	public String list(){
		pageList = pushGroupService.getPageList(query);
		return "list";
	}
	
	public String user(){
		if(StringUtil.isNotEmpty(model.getId())){
			model = pushGroupService.findById(model.getId());
		}
		return "user";
	}
	
	
	
	public String edit(){
		if(StringUtil.isNotEmpty(model.getId())){
			model = pushGroupService.findById(model.getId());
		}
		return "edit";
	}
	public String remove() {
		pushGroupService.delete(model);
		getValueStack().set(DATA,getMessage());
		return DATA;
	}
	
	public String addAll(){
		try {
			
			if(!StringUtil.isEmpty(member.getAcademy())){
				member.setAcademy(new String(member.getAcademy().getBytes("iso-8859-1"),"utf-8"));
			}
			if(!StringUtil.isEmpty(member.getClassgrade())){
				member.setClassgrade(new String(member.getClassgrade().getBytes("iso-8859-1"),"utf-8"));
			}
			if(!StringUtil.isEmpty(member.getGrade())){
				member.setGrade(new String(member.getGrade().getBytes("iso-8859-1"),"utf-8"));
			}
			if(!StringUtil.isEmpty(member.getUserName())){
				member.setUserName(new String(member.getUserName().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		member.setDirectly(true);
		member.setGroupId(model.getId());
		pushGroupService.insertAllMember(member);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("member", member);
		getValueStack().set(DATA,data);
//		this.setSuccessMessage("成功更新数据！");
//		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String saveFp() {
		member.setDirectly(true);
		PushGroupMember m = pushGroupService.insertMember(member);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("member", m);
		getValueStack().set(DATA,data);
//		this.setSuccessMessage("成功更新数据！");
//		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String removeFp() {
		member.setGroupId(model.getId());
		try {
			if(!StringUtil.isEmpty(member.getAcademy())){
				member.setAcademy(new String(member.getAcademy().getBytes("iso-8859-1"),"utf-8"));
			}
			if(!StringUtil.isEmpty(member.getClassgrade())){
				member.setClassgrade(new String(member.getClassgrade().getBytes("iso-8859-1"),"utf-8"));
			}
			if(!StringUtil.isEmpty(member.getGrade())){
				member.setGrade(new String(member.getGrade().getBytes("iso-8859-1"),"utf-8"));
			}
			if(!StringUtil.isEmpty(member.getUserName())){
				member.setUserName(new String(member.getUserName().getBytes("iso-8859-1"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PushGroupMember m = pushGroupService.deleteMember(member);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("member", m);
		getValueStack().set(DATA,data);
//		this.setSuccessMessage("成功更新数据！");
//		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	
	public String save(){
		pushGroupService.save(model);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("model", model);
		getValueStack().set(DATA,data);
		return DATA;
	}
	
	public String reuser(){
		if(StringUtil.isNotEmpty(model.getId())){
			model = pushGroupService.findById(model.getId());
			member.setGroupId(model.getId());
			member.setPerPageSize(10);
//			try {
//				if(!StringUtil.isEmpty(member.getAcademy())){
//					member.setAcademy(new String(member.getAcademy().getBytes("iso-8859-1"),"utf-8"));
//				}
//				if(!StringUtil.isEmpty(member.getClassgrade())){
//					member.setClassgrade(new String(member.getClassgrade().getBytes("iso-8859-1"),"utf-8"));
//				}
//				if(!StringUtil.isEmpty(member.getGrade())){
//					member.setGrade(new String(member.getGrade().getBytes("iso-8859-1"),"utf-8"));
//				}
//				if(!StringUtil.isEmpty(member.getUserName())){
//					member.setUserName(new String(member.getUserName().getBytes("iso-8859-1"),"utf-8"));
//				}
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			wfpMemberPageList = pushGroupService.getPagedListReWfpYh(member);
			/*yimember.setGroupId(member.getGroupId());
			yimember.setPerPageSize(10);
			yimember.setAcademy(member.getAcademy());
			yimember.setClassgrade(member.getClassgrade());
			yimember.setGrade(member.getGrade());
			yimember.setGroupId(member.getGroupId());
			yimember.setGroupName(member.getGroupName());
			yimember.setSex(member.getSex());
			yimember.setUserId(member.getUserId());
			yimember.setUserName(member.getUserName());
			yfpMemberPageList = pushGroupService.getPagedListYfpYh(yimember);*/
			yfpMemberPageList = pushGroupService.getPagedListYfpYh(member);
			List<NewJsglModel> jsList = jsglService.getAllJsglist(new NewJsglModel());
			getValueStack().set("jsList", jsList);
		}
		return "reuser";
	}
	
	
	/**
     * 
     * 方法描述: 角色分配用户，未分配用户列表
     * 
     * @throws
     */
	/*public String fpyhWfpYhxx() throws Exception {
		QueryModel queryModel = member.getQueryModel();
		try {
			queryModel.setItems(pushGroupService.getPagedListWfpYh(member));
		} catch (Exception e) {
			logException(e);
			e.printStackTrace();
		}
		getValueStack().set(DATA, queryModel);
		return DATA;
	}*/
	
   /*public String fpyhYfpYhxx() throws Exception{
       QueryModel queryModel = member.getQueryModel();
       queryModel.setItems(pushGroupService.getPagedListYfpYh(member));
       getValueStack().set(DATA, queryModel);
       return DATA;
   }*/
	
	public void setPushGroupService(IPushGroupService pushGroupService) {
		this.pushGroupService = pushGroupService;
	}
	/**
	 * 返回
	 */
	public PushGroupQuery getQuery() {
		return query;
	}
	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(PushGroupQuery query) {
		this.query = query;
	}
	/**
	 * 返回
	 */
	public PushGroup getModel() {
		return model;
	}
	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(PushGroup model) {
		this.model = model;
	}


	/**
	 * 返回
	 */
	public PageList<PushGroup> getPageList() {
		return pageList;
	}


	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<PushGroup> pageList) {
		this.pageList = pageList;
	}
	/**
	 * 返回
	 */
	public PushGroupMember getMember() {
		return member;
	}
	/**
	 * 设置
	 * @param member 
	 */
	public void setMember(PushGroupMember member) {
		this.member = member;
	}

	public void setWfpMemberPageList(PageList<PushGroupMember> wfpMemberPageList) {
		this.wfpMemberPageList = wfpMemberPageList;
	}

	public PageList<PushGroupMember> getWfpMemberPageList() {
		return wfpMemberPageList;
	}

	public void setYfpMemberPageList(List<PushGroupMember> yfpMemberPageList) {
		this.yfpMemberPageList = yfpMemberPageList;
	}

	public List<PushGroupMember> getYfpMemberPageList() {
		return yfpMemberPageList;
	}

	public void setYimember(PushGroupMember yimember) {
		this.yimember = yimember;
	}

	public PushGroupMember getYimember() {
		return yimember;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	/*public void setMemberPageList(PageList<PushGroupMember> memberPageList) {
		this.memberPageList = memberPageList;
	}

	public PageList<PushGroupMember> getMemberPageList() {
		return memberPageList;
	}*/
	
	
	
}
