package com.zfsoft.hrm.dagl.dao;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dagl.query.MaterialsAddItem;

public interface IMaterialsDao {

	PageList<MaterialsAddItem> getList(MaterialsAddItem query);

	void insert(MaterialsAddItem materials);

	void remove(String classId);

	int getMaterialsCount(MaterialsAddItem query);

	void update(MaterialsAddItem materials);


}
