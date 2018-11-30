package com.zfsoft.wechat.login.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.util.base.StringUtil;
import com.zfsoft.wechat.login.dao.daointerface.IWeChatLoginDao;
import com.zfsoft.wechat.login.entity.WeChatBinding;
import com.zfsoft.wechat.login.service.IWeChatLoginService;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.daointerface.IYhglDao;
import com.zfsoft.dao.entities.JsglModel;
/**
 * 
 * @author Administrator
 *
 */
public class WeChatLoginServiceImpl implements IWeChatLoginService {
    
    private IWeChatLoginDao wechatLoginDao;
    private IYhglDao yhglDao;

    /**
     * @return the wechatLoginDao
     */
    public IWeChatLoginDao getWechatLoginDao() {
        return wechatLoginDao;
    }

    /**
     * @param wechatLoginDao the wechatLoginDao to set
     */
    public void setWechatLoginDao(IWeChatLoginDao wechatLoginDao) {
        this.wechatLoginDao = wechatLoginDao;
    }

	@Override
	public void bindOpenId(WeChatBinding weChatBinding){
		wechatLoginDao.remove(weChatBinding);
		wechatLoginDao.insert(weChatBinding);
	}

	@Override
	public String findByOpenId(String openId) {
//		User user = null;
		String account = wechatLoginDao.findUserByOpenId(openId);
		if(StringUtil.isEmpty(account)) return null;
		return account;
//		user = new User();
//		if (!"1".equals((String)map.get("SFQY"))) {
//			user.setSfqy("0");
//			return user;
//		} 
//		user.setYhm(map.get("ZGH"));
//		user.setXm(map.get("XM"));
//		user.setYhlx("teacher");
//		user.setBmmc(map.get("BMMC"));
//		user.setBmdm_id(map.get("BMDM_ID"));
//		user.setGwjbdm(map.get("GWJBDM"));
//		user.setGwjbmc(map.get("GWJBMC"));
//		user.setScdljsdm(map.get("SCDLJSDM"));
//		JsglModel jsglModel  = new JsglModel();
//		jsglModel.setZgh(map.get("ZGH"));
//		List<Role> allRoles = new ArrayList<Role>();
//		List<JsglModel> jsglModels = yhglDao.cxJsdm(jsglModel);
//		for(JsglModel m:jsglModels){
//			Role r=new Role();
//			r.setJsdm(m.getJsdm());
//			r.setJsmc(m.getJsmc());
//			r.setSfejsq(m.getSfejsq());
//			allRoles.add(r);
//		}
//		user.setAllRoles(allRoles);
//		return user;
	}

	/**
	 * 设置
	 * @param yhglDao 
	 */
	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}
    
}
