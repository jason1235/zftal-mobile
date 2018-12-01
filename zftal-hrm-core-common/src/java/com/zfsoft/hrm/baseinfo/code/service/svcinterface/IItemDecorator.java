package com.zfsoft.hrm.baseinfo.code.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

/** 
 * 代码库装饰者接口
 * @author jinjj
 * @date 2012-7-25 上午11:05:05 
 *  
 */
public interface IItemDecorator {

	/**
	 * 将目标数据集合以代码库对象的格式获取
	 * @return
	 */
	public List<Item> getItemList();

	List<OrgInfo> getDisusedList(OrgQuery query);
}
