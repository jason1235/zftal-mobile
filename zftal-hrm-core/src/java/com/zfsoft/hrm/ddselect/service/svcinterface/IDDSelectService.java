package com.zfsoft.hrm.ddselect.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.ddselect.entities.DDSelect;
import com.zfsoft.hrm.ddselect.entities.Fields;
import com.zfsoft.hrm.ddselect.entities.Indexs;
import com.zfsoft.hrm.ddselect.entities.Keys;
import com.zfsoft.hrm.ddselect.entities.PFkeyRelation;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public interface IDDSelectService {
	public PageList<DDSelect> getPagedTable(DDSelect ddSelect) throws RuntimeException;
	public List<Fields> getField(String tableName) throws RuntimeException;
	public List<Keys> getKey(String tableName) throws RuntimeException;
	public List<Indexs> getIndex(String tableName) throws RuntimeException;
	public List<PFkeyRelation> getPFkeyRelation(String tableName) throws RuntimeException;
}
