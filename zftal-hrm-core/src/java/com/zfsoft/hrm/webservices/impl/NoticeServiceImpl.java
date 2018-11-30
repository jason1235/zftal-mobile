package com.zfsoft.hrm.webservices.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.query.NoticeQuery;
import com.zfsoft.hrm.notice.service.INoticeService;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairNoSessionService;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairService;
import com.zfsoft.hrm.webservices.NoticeService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.date.TimeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-5-19
 * @version V1.0.0
 */
public class NoticeServiceImpl implements NoticeService {

	private INoticeService service;
	private IMessageService mesService;
	private ILoginService loginService;
	private IPendingAffairService pendingAffairService;
	private IPendingAffairNoSessionService pendingAffairNoSessionService;
	
	@Override
	public String getLastNoticeList(String yhm, String noticeType, String num,
			String sign) {
		
		NoticeQuery query = new NoticeQuery();
		query.setStatus(1);
		int num_v = 6;
		try{
		num_v = Integer.valueOf(num);
		}catch (Exception e) {
		}
		query.setPerPageSize(num_v);
		query.setOrderStr( " ZDZT DESC,FBSJ DESC" );
		PageList<Notice> pageList = service.getPagingList(query);
		StringBuffer result = new StringBuffer();
		result.append("<taskList>");
		for (Notice notice : pageList) {
			result.append("<task><id>"+notice.getGuid()+"</id>");
			result.append("<title>"+notice.getTitle()+"</title>");
			result.append("<url>message/noticeView_info.html?notice.guid="+notice.getGuid()+"</url>");
			result.append("<time>"+TimeUtil.getDataTime(notice.getCreateTime().getTime(), "yyyy-MM-dd")+"</time></task>");
		}
		result.append("</taskList>");
		return result.toString();
	}
	
	/**
	 * 获取最近待办工作列表
	 */
	@Override
	public String getLastTodoTaskList(String yhm, String num, String sign) {
		
		// 查询用户信息
		LoginModel model = new LoginModel();
		model.setYhm(yhm);
		model.setMm(sign);
		User user = loginService.cxYhxx(model);
		// 系统配置登录类型（单角色、多角色）
		String login_type = SubSystemHolder.getPropertiesValue("login_type");
		// 登录者拥有的角色
		List<Role> allRoles = user.getAllRoles();
		
		// 没有角色
		if (allRoles == null || allRoles.size() == 0) {
			return "";
		}
		
		List<String> queryRoles = new ArrayList<String>();
		// 单角色
		if ("sole_role".equals(login_type)) {
			queryRoles.clear();
			// 上次登录角色判断
			if (StringUtils.isEmpty(user.getScdljsdm())) {
				queryRoles.add(allRoles.get(0).getJsdm());
			} else {
				//检测上次登陆的角色，该用户是否还拥有，如果不再拥有，则取第一个拥有的角色
				for (Role role : allRoles) {
					if(user.getScdljsdm().equals(role.getJsdm())){
						queryRoles.add(user.getScdljsdm());
						break;
					}
				}
				
				if(queryRoles.size() == 0){
    				queryRoles.add(allRoles.get(0).getJsdm());
    			}
			}
			user.setJsdms(queryRoles);
		} else {
			// 多角色
			queryRoles.clear();
			for(Role role : allRoles){
				queryRoles.add(role.getJsdm());
			}
			user.setJsdms(queryRoles);
		}
		
		List<PendingAffairInfo> retList = new ArrayList<PendingAffairInfo>();
		
		// 默认获取记录条数
		int num_v = 6;
		StringBuffer result = new StringBuffer();
		try {
			// 默认获取记录条数为空
			if (!StringUtils.isEmpty(num)) {
				num_v = Integer.valueOf(num);
			}
			
			// 按用户查询待办事宜
			List<PendingAffairInfo> listByUser = pendingAffairService.getListByUser(user);
			int size = listByUser.size();
			// 待办事宜数小于获取记录数
			if (size < num_v) {
				retList.addAll(listByUser);
				// 按角色查询待办事宜
				List<PendingAffairInfo> listByRoles = pendingAffairNoSessionService.getListByRoles(user);
				// 补充待办事宜，条数达到获取记录条数
				for (PendingAffairInfo info : listByRoles) {
					retList.add(info);
					if (retList.size() >= num_v) {
						break;
					}
				}
				// 待办事宜数等于获取记录条数，直接返回
			} else if (size == num_v) {
				retList.addAll(listByUser);
			} else {
				// 待办事宜大于获取记录条数，去掉多余数据
				for (PendingAffairInfo info : listByUser) {
					retList.add(info);
					if (retList.size() >= num_v) {
						break;
					}
				}
			}
			
			// 返回xml
			result.append("<taskList>");
			for (PendingAffairInfo info : retList) {
				result.append("<task>");
				result.append("<id>" + info.getId() + "</id>");
				result.append("<title>" + info.getAffairName() + "(" + info.getSumNumber() + ")条消息</title>");
				result.append("<url>/xtgl/index_initMenu.html?gnmkdm=" + info.getMenu() + "</url>");
				result.append("<time></time>");
				result.append("</task>");
			}
			result.append("</taskList>");
		} catch(Exception e) {
			return "";
		}

		return result.toString();
	}
	
	/**
	 * 获取最近待办邮件
	 */
	@Override
	public String getLastTodoMailList(String yhm, String num, String sign) {
		MessageQuery query = new MessageQuery ();
		// 默认获取记录条数
		int num_v = 6;
		StringBuffer result = new StringBuffer();
		try {
			if (!StringUtils.isEmpty(num)) {
				num_v = Integer.valueOf(num);
			}
			query.setStatus("0");
			query.setReceiver(yhm);
			query.setPerPageSize(num_v);
			query.setOrderStr(" FSSJ DESC ");
			
			PageList<Message> pageList = mesService.getPagingList(query);
			result.append("<taskList>");
			for (Message message : pageList) {
				result.append("<task>");
				result.append("<id>" + message.getGuid() + "</id>");
				result.append("<title>" + message.getTitle() + "</title>");
				result.append("<url>/xtgl/index_initMenu.html?gnmkdm=N70</url>");
				result.append("<time>" + TimeUtil.getDataTime(message.getSendTime().getTime(), "yyyy-MM-dd") + "</time>");
				result.append("</task>");
			}
			result.append("</taskList>");
		} catch(Exception e) {
			return "";
		}

		return result.toString();
	}
	
	/**
	 * 设置
	 * @param service 
	 */
	public void setService(INoticeService service) {
		this.service = service;
	}

	/**
	 * @param mesService the mesService to set
	 */
	public void setMesService(IMessageService mesService) {
		this.mesService = mesService;
	}

	/**
	 * @param loginService the loginService to set
	 */
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * @param pendingAffairService the pendingAffairService to set
	 */
	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}

	/**
	 * @param pendingAffairNoSessionService the pendingAffairNoSessionService to set
	 */
	public void setPendingAffairNoSessionService(
			IPendingAffairNoSessionService pendingAffairNoSessionService) {
		this.pendingAffairNoSessionService = pendingAffairNoSessionService;
	}

}
