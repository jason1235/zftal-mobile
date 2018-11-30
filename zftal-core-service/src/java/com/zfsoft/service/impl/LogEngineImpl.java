package com.zfsoft.service.impl;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.OperateLogModel;
import com.zfsoft.service.svcinterface.ILogService;


/**
* 
* 类名称：OperateLogEngineImpl
* 类描述：日志记录入口
* 创建人：qph
* 创建时间：2012-4-20 
* 修改备注： modify by caozf
*
*/
public class LogEngineImpl extends BaseLog{
	
	private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogEngineImpl.class);

	private static LogEngineImpl instance = new LogEngineImpl(); 
	
	private LogEngineImpl() {
		
	}
	public static LogEngineImpl getInstance() {
		return instance;
	}
	/**
	 * 查询操作
	 */
	private static final String OP_SELECT = "select";
	/**
	 * 刪除 操作
	 */
	private static final String OP_DELETE = "delete";
	/**
	 * 更改操作
	 */
	private static final String OP_UPDATE = "update";
	/**
	 * 插入操作
	 */
	private static final String OP_INSERT = "insert";
	/**
	 * 登录操作
	 */
	private static final String OP_LOGIN = "login";
	/**
	 * 登出操作
	 */
	private static final String OP_LOGOUT = "logout";
	// ------------------------------

	
	/**
	 * 记录什么人，对什么业务 ,作了什么操作
	 * @param user 操作人
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czlx 操作类型
	 * @param czms 操作描述
	 */
	public void log(User user, String ywmc, String mkmc, String czlx,
			String czms) {
		try {
			OperateLogModel model = new OperateLogModel();
			model.setCzlx(czlx);
			model.setCzmk(mkmc);
			model.setCzms(czms);
			model.setCzr(user.getXm()+"【职工号："+user.getYhm()+"】");
			model.setYwmc(ywmc);
			String hostInfo[] = SessionFactory.getHostInfo();
			model.setIpdz(hostInfo[0]);
			model.setZjm(hostInfo[2]);

			ILogService sv = (ILogService) ServiceFactory.getService("logService");
			sv.insert(model);

		} catch (Exception e) {
			log.error("记日志出错!", e);
		}
	}

	
	/**
	 * 日志记录（查询）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void select(User user, String ywmc, String mkmc, String czms) {
		
		log(user, ywmc, mkmc, OP_SELECT, czms);
	}

	
	/**
	 * 日志记录（查询）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void select(String ywmc, String mkmc, String czms) {
		User user = SessionFactory.getUser();
		select(user, ywmc, mkmc,czms);
	}
	
	
	
	/**
	 * 日志记录（删除）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, OP_DELETE, czms);
	}
	
	
	/**
	 * 日志记录（删除）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void delete(String ywmc, String mkmc, String czms) {
		User user = SessionFactory.getUser();
		delete(user, ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（修改）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void update(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, OP_UPDATE, czms);
	}
	
	
	/**
	 * 日志记录（修改）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void update(String ywmc, String mkmc, String czms) {
		User user = SessionFactory.getUser();
		update(user, ywmc, mkmc, czms);
	}
	
	
	/**
	 * 日志记录（增加）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void insert(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, OP_INSERT, czms);
	}
	
	
	/**
	 * 日志记录（增加）
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void insert(String ywmc, String mkmc, String czms) {
		User user = SessionFactory.getUser();
		insert(user, ywmc, mkmc,czms);
	}
	
	
	/**
	 * 日志记录（登陆）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void login(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, OP_LOGIN, czms);
	}

	
	/**
	 * 日志记录（注销）
	 * @param user 用户对象
	 * @param ywmc 业务名称
	 * @param mkmc 模块名称
	 * @param czms 操作描述
	 */
	public void logout(User user, String ywmc, String mkmc, String czms) {
		log(user, ywmc, mkmc, OP_LOGOUT, czms);
	}
	
}
