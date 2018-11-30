package com.zfsoft.mobile.index.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.index.entity.TsgJyxx;
import com.zfsoft.mobile.index.entity.YktJbxx;


public interface IndexInfoDao {

	public YktJbxx getYktInfo(@Param("ryh") String ryh);
	
	Map<String, Object> getTeaSh(@Param("kh") String kh);
	
	Map<String, Object> getStuSh(@Param("kh") String kh);
	
	List<TsgJyxx> selectLibInfo(@Param("ryh") String ryh);
	
}
