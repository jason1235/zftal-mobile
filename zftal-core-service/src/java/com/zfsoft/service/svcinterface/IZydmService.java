package com.zfsoft.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.entities.ZydmModel;

public interface IZydmService {
	/**
	 * 
	* 方法描述: 根据部门代码,查询专业列表
	* 参数 @return 参数说明
	* 返回类型  List<BmdmModel>  返回类型
	*/
	public List<ZydmModel> queryModel(Map<String,String> map) ;
}
