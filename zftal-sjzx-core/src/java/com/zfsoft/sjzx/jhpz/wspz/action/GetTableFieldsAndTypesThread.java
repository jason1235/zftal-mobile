package com.zfsoft.sjzx.jhpz.wspz.action;

import java.util.concurrent.Callable;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.database.DatabaseMeta;

import com.zfsoft.sjzx.common.util.DbUtil;

public class GetTableFieldsAndTypesThread implements Callable<String>{
	
	private DatabaseMeta dbMeta;
	private String tableName;
	private String tableType;
	
	public GetTableFieldsAndTypesThread(DatabaseMeta dbMeta,String tableName,String tableType){
		this.dbMeta = dbMeta;
		this.tableName = tableName;
		this.tableType = tableType;
	}
		
	public String call() throws Exception {
		String[] retu = DbUtil.getTableFieldsAndTypes(tableName,dbMeta,tableType);
		return StringUtils.substringBetween(ArrayUtils.toString(retu),"{","}");
	}

}
