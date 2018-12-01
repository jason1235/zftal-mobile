package com.zfsoft.hrm.dagl.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.query.MaterialsAddItem;

/**
 * 
 * @author ZhangXu
 * @date 2014-10-21
 * @version V1.0.0
 */
public interface IMaterialsService {

	PageList<MaterialsAddItem> getList(MaterialsAddItem query);

	PageList<ArchiveItem> getArchiveItemList(MaterialsAddItem query);

	void insert(MaterialsAddItem materials);

	void removeData(String classId);

	void update(MaterialsAddItem materials);

}
