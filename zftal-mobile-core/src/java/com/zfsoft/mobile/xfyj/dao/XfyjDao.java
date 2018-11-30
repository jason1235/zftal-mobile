package com.zfsoft.mobile.xfyj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.xfyj.entity.XfyjEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqQueryEntity;



public interface XfyjDao {

	List<XfyjEntity> getList();

	List<XfyjxqEntity> getDetails(XfyjxqQueryEntity query);

	int count(XfyjxqQueryEntity query);

}
