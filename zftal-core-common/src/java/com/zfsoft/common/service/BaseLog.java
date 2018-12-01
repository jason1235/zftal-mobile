package com.zfsoft.common.service;

import com.zfsoft.common.log.User;

/**
 * 用户日志抽象类<br>
 * 创建时间：2012-5-3
 * @param <T>
 */
public abstract class BaseLog {

	public void log(User user, String ywmc, String mkmc, String czlx,
			String czms) {

	}

	/**
	 * 日志记录（查询）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void select(User user, String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（查询）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void select(String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（删除）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void delete(User user, String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（删除）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void delete(String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（修改）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void update(User user, String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（修改）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void update(String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（增加）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void insert(User user, String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（增加）
	 * 
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void insert(String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（登陆）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void login(User user, String ywmc, String mkmc, String czms) {

	}

	/**
	 * 日志记录（注销）
	 * 
	 * @param user
	 *            用户对象
	 * @param ywmc
	 *            业务名称
	 * @param mkmc
	 *            模块名称
	 * @param czms
	 *            操作描述
	 */
	public void logout(User user, String ywmc, String mkmc, String czms) {

	}

}
