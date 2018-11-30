package com.zfsoft.hrm.baseinfo.table.service.impl;

import com.zfsoft.hrm.baseinfo.table.dao.daointerface.IColumnDao;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.exception.TableException;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.IColumnService;
import com.zfsoft.hrm.core.exception.RuleException;

/**
 * 数据库字段操作-ORACLE实现
 * @ClassName: OracleColumnServiceImpl 
 * @author jinjj
 * @date 2012-6-7 下午02:19:26 
 *
 */
public class OracleColumnServiceImpl implements IColumnService {

	private IColumnDao columnDao;
	
	@Override
	public void add(Column column,boolean createIndex) throws TableException {
		if(isExist(column)){
			throw new RuleException("字段已存在,字段描述:"+column.getComment()+" 字段名称:"+column.getColumnName());
		}
		columnDao.add(column);
		columnDao.comment(column);
		if(createIndex){
			columnDao.addIndex(column);
		}
	}
	
	@Override
	public void add(Column column) throws TableException {
		add(column, false);
	}

	@Override
	public void comment(Column column) throws TableException {
		columnDao.comment(column);
	}

	@Override
	public void drop(Column column) throws TableException {
		try{
		columnDao.removeIndex(column);
		}catch (Exception e) {
		}
		columnDao.drop(column);
	}

	@Override
	public boolean isExist(Column column) throws TableException {
		int cnt = columnDao.findCount(column);
		if(cnt>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void modify(Column column) throws TableException {
		columnDao.modify(column);
	}

	@Override
	public void rename(Column column) throws TableException {
		columnDao.rename(column);
	}

	public void setColumnDao(IColumnDao columnDao) {
		this.columnDao = columnDao;
	}

}
