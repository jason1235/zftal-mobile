package com.zfsoft.hrm.usermanage.action;

import java.util.List;

import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.usermanage.service.IUserManageService;
import com.zfsoft.service.impl.LogEngineImpl;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-13
 * @version V1.0.0
 */
public class UserManageAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6549874684623213L;
	private BaseLog baseLog = LogEngineImpl.getInstance();
	private IUserManageService userManageService;
	private String gh;
	public String delete(){
		User user = getUser();
		List<String> list = userManageService.doReallyDelete(gh);
			// 记操作日志
		String opDesc = getText("log.message.czms", new String[] {
					"彻底删除用户", "职工号", gh });
		baseLog.delete(user, getText("log.message.ywmc", new
			  String[] { "用户管理", "XG_XTGL_YHB" }), "系统管理",
			  opDesc);
		if(list==null||list.isEmpty()){
			getValueStack().set(DATA, "删除成功");
		}else{
			getValueStack().set(DATA, "删除完毕，以下信息类未能成功清除，请后台手动操作："+list);
		}
		
		return DATA;
	}
	/**
	 * 设置
	 * @param userManageService 
	 */
	public void setUserManageService(IUserManageService userManageService) {
		this.userManageService = userManageService;
	}
	/**
	 * 设置
	 * @param gh 
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	
}
