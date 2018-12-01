package com.zfsoft.hrm.normal.info.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.normal.info.entity.OverallInfo;


/**
 * 个人综合信息业务操作类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface IOverallInfoService {
	public List<OverallInfo> userListThink(String searchStr);
	public List<OverallInfo> userListThink(String searchStr,String expression);
}
