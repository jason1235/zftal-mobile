package com.zfsoft.mobile.index.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.index.entity.TsgJyxx;
import com.zfsoft.mobile.index.entity.YktJbxx;



public interface IndexInfoService {

	//查询余额
	public YktJbxx getYktInfo(String ryh);
	
	//查询教师最后消费地点
	Map<String, Object> getTeaSh(String kh);
	
	Map<String, Object> getStuSh(String kh);
	
	List<TsgJyxx> selectLibInfo(String ryh);
	
}
