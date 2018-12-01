package com.zfsoft.hrm.baseinfo.finfo.action;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberService;
import com.zfsoft.hrm.common.HrmAction;

/**
 * 信息维护-成员定义Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-25
 * @version V1.0.0
 */
public class FormInfoMemberAction extends HrmAction implements ModelDriven<FormInfoMember> {

	private static final long serialVersionUID = -8332086127195075587L;
	
	private FormInfoMember member = new FormInfoMember();
	
	private IFormInfoMemberService formInfoMemberService;
	
	private String[] classIds;

	/**
	 * 成员列表页面
	 */
	public String list() {
		
		FormInfoMember[] members = formInfoMemberService.getMembers( member.getName(),member.getBatch() );
		
		getValueStack().set( "members", members );
		
		return LIST_PAGE;
	}

	/**
	 * 成员编辑页面
	 */
	public String edit() {
		member = formInfoMemberService.getMember( member.getName(), member.getClassId(),member.getBatch() );
		
		return EDIT_PAGE;
	}

	/**
	 * 成员保存操作
	 */
	public String save() {
		
		try {
			formInfoMemberService.saveMember( member );
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage( "操作失败！<br />提示：" + e.getMessage() );
		}
		
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}
	
	/**
	 * 开放/关闭成员操作
	 */
	public String open() {
		try {
			formInfoMemberService.modifyMemberOpen( member );
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("操作失败！");
		}
		
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}
	
	/**
	 * 上移/下移
	 */
	public String swap() {
		try {
			formInfoMemberService.modifySwapMemberIndex( member.getName(), classIds,member.getBatch() );
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("操作失败！");
		}
		
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}

	public void setFormInfoMemberService(
			IFormInfoMemberService formInfoMemberService) {
		this.formInfoMemberService = formInfoMemberService;
	}
	
	@Override
	public FormInfoMember getModel() {
		return member;
	}

	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}
}
