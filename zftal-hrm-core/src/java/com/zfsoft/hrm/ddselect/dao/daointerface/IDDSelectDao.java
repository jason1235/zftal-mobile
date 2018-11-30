package com.zfsoft.hrm.ddselect.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

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
public interface IDDSelectDao {
	public List<DDSelect> getPagedTable(DDSelect ddSelect) throws DataAccessException;
	public List<Fields> getField(String tableName) throws DataAccessException;
	public List<Keys> getKey(String tableName) throws DataAccessException;
	public List<Indexs> getIndex(String tableName) throws DataAccessException;
	public List<PFkeyRelation> getPFkeyRelation(String tableName) throws DataAccessException;
}
