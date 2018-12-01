package com.common;


/**
 * <p>Description:webservice静态常量类 </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-4-19 上午10:35:12
 * @author xuxinghua
 * @version 1.0
 */
public class ConstantsSoa {
	/**
	 *编码
	 */
	public static String ENCODE = "UTF-8";
	/**
	 *首页最大显示新闻条数
	 */
	public static int INDEX_NEWSLIST_NUM = 50;
	/**
	 *版本更新状态--强制更新1
	 */
	public static Long VERSION_STATE1 = 1L;
	/**
	 *版本更新状态--非强制更新2
	 */
	public static Long VERSION_STATE2 = 2L;
	/**
	 *版本更新状态--不需要更新3
	 */
	public static Long VERSION_STATE3 = 3L;
	
	public static final String SCHOOL_INFO = Config.getString("school.info", "LYXY");
}
