package com.zfsoft.mobile.xfyj.service;

import java.util.List;

import com.zfsoft.mobile.xfyj.entity.XfyjEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqQueryEntity;



public interface XfyjService {
	
	List<XfyjEntity> getList();

	List<XfyjxqEntity> getDetails(XfyjxqQueryEntity query);
	
}
