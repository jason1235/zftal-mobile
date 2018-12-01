package com.zfsoft.hrm.config;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 模板工厂
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-3
 * @version V1.0.0
 */
public final class TempletFactory {
	
	// 信息类主库模板
	private static InfoClass TEMPLET_MAIN = new InfoClass();
	
	// 信息类日志库模板
	private static InfoClass TEMPLET_LOG = new InfoClass();
	
	// 信息类快照库模板
	private static InfoClass TEMPLET_SNAP = new InfoClass();
	
	/**
	 * 设置信息类主库模板
	 * @param templet 信息类主库模板
	 */
	public void setTempletMain( InfoClass templet ) {
		
		TEMPLET_MAIN = templet;
	}
	
	/**
	 * 设置信息类日志库模板
	 * @param templet 信息类日志库模板
	 */
	public void setTempletLog( InfoClass templet ) {
		
		TEMPLET_LOG = templet;
	}
	
	/**
	 * 设置信息类快照库模板
	 * @param templet 信息类主快照库模板
	 */
	public void setTempletSnap( InfoClass templet ) {
		
		TEMPLET_SNAP = templet;
	}
	
	/**
	 * 返回信息类主库模板
	 * @return
	 */
	public static InfoClass getTempletMain() {
		
		return TEMPLET_MAIN;
	}
	
	/**
	 * 返回信息类日志库模板
	 * @return
	 */
	public static InfoClass getTempletLog() {
	
		return TEMPLET_LOG;
	}
	
	/**
	 * 信息类快照库模板
	 * @return
	 */
	public static InfoClass getTempletSanp() {
		
		return TEMPLET_SNAP;
	}
}
