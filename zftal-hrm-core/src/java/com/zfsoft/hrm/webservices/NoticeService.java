package com.zfsoft.hrm.webservices;

/**
 * 
 * @author ChenMinming
 * @date 2014-5-19
 * @version V1.0.0
 */
public interface NoticeService {
	/**
	 * 获取最近通知公告
	 * @param yhm 用户名
	 * @param noticeType 通知公告类型
	 * @param num 获取记录条数
	 * @param sign 验证签名	md5(密钥+用户名)
	 * @return
	 */
	String getLastNoticeList( String yhm,String noticeType ,String num ,String  sign);
	
	/**
	 * 获取最近待办工作列表
	 * @param yhm 用户名
	 * @param num 获取记录条数
	 * @param sign 验证签名 md5(密钥+用户名)
	 * @return
	 */
	String getLastTodoTaskList(String yhm, String num, String sign);
	
	/**
	 * 获取最近待办邮件
	 * @param yhm 用户名
	 * @param num 获取记录条数
	 * @param sign 验证签名 md5(密钥+用户名)
	 * @return
	 */
	String getLastTodoMailList(String yhm, String num, String sign);
	
}
