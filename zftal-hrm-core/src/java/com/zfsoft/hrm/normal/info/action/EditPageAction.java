package com.zfsoft.hrm.normal.info.action;

import com.zfsoft.fifa.identity.Identity;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.orcus.beans.dyna.DynaBean;
import com.zfsoft.orcus.mast.servlet.Parameters;

/**
 * 编辑页面Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public class EditPageAction extends HrmAction {
	
	private static final long serialVersionUID = 1357887019221226881L;

	private Identity identity;
	
	public String list() {
		
		return LIST_PAGE;
	}
	
	public String edit() {
		DynaBean bean = null;
		try {
			new Parameters().getBean( getRequest(), bean);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return EDIT_PAGE;
	}
	
	public String save() {
		
		return DATA;
	}
	
	public String remove() {
		
		return DATA;
	}
	
	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

}
