package com.zfsoft.hrm.common;

import javax.servlet.http.HttpSession;

import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.service.svcinterface.IIndexService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.zfca.tp.cas.client.ZfssoBean;
import com.zfsoft.zfca.tp.cas.client.ZfssoSetsessionService;

/**
 * 用户登录信息session类（单点登录）
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-9-4
 * @version V1.0.0
 */
public class SsoSetSession implements ZfssoSetsessionService {
	
	private ILoginService loginService;
	
	private IIndexService indexService;

	@Override
	public Boolean chkUserSession(ZfssoBean zfssoBean) {
		boolean res = false;
		User user = null;
		
		//校验用户session是否存在，存在返回true，失败返回false
		HttpSession session = zfssoBean.getSession();
		
		String yhm = zfssoBean.getYhm();
		user = (User)session.getAttribute("user");
		
		if( user != null && yhm.equalsIgnoreCase( user.getYhm() ) ) {
			res = true;
		}
		
		return res;
	}

	@Override
	public Boolean setUserSession(ZfssoBean zfssoBean) {
		boolean res = false;
		
		try {
			//查询业务系统数据库中是否存在次用户
			HttpSession session = zfssoBean.getSession();
			String yhm = zfssoBean.getYhm();
			String yhlx = zfssoBean.getYhlx();
			User user = null;
			
			LoginModel model = new LoginModel();
			model.setYhm( yhm );
			model.setYhlx( yhlx );
			user = getLoginService().cxYhxxSso( model, session);
			
			if( user != null ) {
				session.setAttribute( "user", user );
//				session.setAttribute( "fsUser", getIndexService().fsYh( user ) );
				session.setAttribute( user.getYhm(), getIndexService().cxJsxxLb( user ) );
				res = true;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return res;
	}

	public IIndexService getIndexService() {
		if( indexService == null ) {
			indexService = (IIndexService) SpringHolder.getBean( "indexService" );
		}
		
		return indexService;
	}

	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}

	public ILoginService getLoginService() {
		if( loginService == null ) {
			loginService = ( ILoginService ) SpringHolder.getBean( "loginService" );
		}
		
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

}
