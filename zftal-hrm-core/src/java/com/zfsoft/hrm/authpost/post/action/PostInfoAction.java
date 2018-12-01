package com.zfsoft.hrm.authpost.post.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.authpost.post.entities.PostInfo;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IPostInfoService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public class PostInfoAction extends HrmAction implements ModelDriven<PostInfo> {

	private static final long serialVersionUID = -6290791405619509382L;

	private PostInfo model=new PostInfo();
	
	private IPostInfoService postInfoService;
	
	private String opState="add";
	
	private void setTypeList(){
		getValueStack().set("code", loadCodeInPage());		
	}
	/**
	 * 加载页面中使用的代码编号
	 * @return
	 */
	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("typeCode", ICodeConstants.DM_DEF_WORKPOST);
		return map;
	}
	public String list() {
		if(model.getTypeCode()==null||model.getTypeCode().equals(""))
			model.setTypeCode("01");
		setTypeList();
		this.getValueStack().set("authInfoList", postInfoService.getListByType(model.getTypeCode()));
		return LIST_PAGE;
	}
	
	public String up(){
		postInfoService.sort("up",model.getId());
		this.setSuccessMessage("上移成功");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}
	public String down(){
		postInfoService.sort("down",model.getId());
		this.setSuccessMessage("下移成功");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}

	public String add() {
		opState="add";
		setTypeList();
		return EDIT_PAGE;
	}
	
	public String modify() {
		opState="modify";
		setTypeList();
		model=postInfoService.getById(model.getId());
		return EDIT_PAGE;
	}

	public String save() {
		if(opState.equals("modify")){
			postInfoService.modify(model);
		}else{
			postInfoService.add(model); 
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}

	public String delete(){
		postInfoService.remove(model.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}

	@Override
	public PostInfo getModel() {
		return model;
	}
	
	public void setPostInfoService(IPostInfoService postInfoService) {
		this.postInfoService = postInfoService;
	}
	public String getOpState() {
		return opState;
	}

	public void setOpState(String opState) {
		this.opState = opState;
	}
}
