package com.zfsoft.hrm.baseinfo.table.service.impl;

import com.zfsoft.hrm.baseinfo.table.dao.daointerface.IColumnDao;
import com.zfsoft.hrm.baseinfo.table.dao.daointerface.ITableDao;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.entities.Table;
import com.zfsoft.hrm.baseinfo.table.exception.TableException;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;
import com.zfsoft.hrm.core.exception.RuleException;

/**
 * {@link ITableService}的Oracle数据库实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 * 
 * @author jjj
 * @date 2012-6-7
 */
public class OracleTableServiceImpl implements ITableService {

	private ITableDao tableDao;
	private IColumnDao columnDao;
	
	@Override
	public void create(Table table) throws TableException {
		if( isExist( table.getTableName() ) ) {
			throw new RuleException("表名"+table.getTableName()+"已存在！");
		}
		if( table.getColumns() == null || table.getColumns().size() == 0) {
			throw new RuleException("表字段为空！");
		}
		
		tableDao.create(table);
		
		comment( table );
		
		for( Column column : table.getColumns() ){
			columnDao.comment(column);
		}
		
	}

	@Override
	public void comment(Table table) throws TableException {

		tableDao.comment(table);
	}
	
	@Override
	public void drop(Table table) throws TableException {
		tableDao.drop(table);
	}

	@Override
	public boolean isExist(String name) throws TableException {
//		int cnt = tableDao.findCount(name);
		/**
		 * 将表名转换为大写（Oracle的user_tables中表名存储为大写）
		 */
		int cnt = tableDao.findCount(name.toUpperCase());
		if(cnt>0){
			return true;
		}else{
			return false;
		}
	}

	public void setTableDao(ITableDao tableDao) {
		this.tableDao = tableDao;
	}

	public void setColumnDao(IColumnDao columnDao) {
		this.columnDao = columnDao;
	}

}
