package com.zfsoft.hrm.baseinfo.table.util;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.entities.Table;

/**
 * 数据库表操作工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-7
 * @version V1.0.0
 */
public class TableUtil {

	/**
	 * 创建信息类的基础表结构
	 * @param infoClass 信息类
	 * @return
	 */
	public static Table createBaseInfoTable( InfoClass infoClass ) {
		Column column = new Column("DELETED", "删除标记,是否已删除 0为删除 1已删除", "VARCHAR2(2)", infoClass.getIdentityName() );
		column.setDefaultV( "0" );

		List<Column> columns = new ArrayList<Column>(0);
		columns.add( column );
		
		Table table = new Table();
		
		table.setTableName( infoClass.getIdentityName() );
		table.setColumns( columns );
		table.setComment( infoClass.getName() );
		
		return table;
	}
	
	/**
	 * 创建信息类日志的基础表结构
	 * @param infoClass 信息类
	 * @return
	 */
	public static Table createLogInfoTable( InfoClass infoClass ) {
		String tableName = infoClass.getIdentityName() + "_LOG";
		
		List<Column> columns = new ArrayList<Column>(0);
		
		columns.add( new Column("LOGID", "日志全局ID", "VARCHAR2(50)", tableName ) );
		columns.add( new Column("OPERATOR_", "操作人", "VARCHAR2(20)", tableName ) );
		columns.add( new Column("OPERATION_", "操作", "VARCHAR2(20)", tableName ) );
		columns.add( new Column("OPERATION_TIME_", "操作时间", "date", tableName ) );
		columns.add( new Column("DELETED", "删除标记,是否已删除 0为删除 1已删除", "VARCHAR2(2)", tableName ) );
		
		Table table = new Table();
		
		table.setTableName( tableName );
		table.setColumns( columns );
		table.setComment( infoClass.getName() + "_日志表" );
		
		return table;
	}
	
	/**
	 * 创建信息类快照的基础表结构
	 * @param infoClass 信息类
	 * @return
	 */
	public static Table createSnapInfoTable( InfoClass infoClass ) {
		String tableName = infoClass.getIdentityName() + "_SNAP";
		
		List<Column> columns = new ArrayList<Column>(0);
		
		columns.add( new Column("SNAP_TIME", "时间戳", "date", tableName ) );
		columns.add( new Column("OPERATOR_", "操作人", "VARCHAR2(20)", tableName ) );
		columns.add( new Column("OPERATION_TIME_", "操作时间", "date", tableName ) );
		columns.add( new Column("DELETED", "删除标记,是否已删除 0为删除 1已删除", "VARCHAR2(2)", tableName ) );
		
		Table table = new Table();
		
		table.setTableName( tableName );
		table.setColumns( columns );
		table.setComment( infoClass.getName() + "_快照表" );
		
		return table;
	}
	
	/**
	 * 将信息类转化成表
	 * <p>
	 * 在对信息类转化的表的同时将信息类属性转化成表字段
	 * </p>
	 * @param infoClass 信息类信息
	 * @return
	 */
	public static Table infoClassToTable(InfoClass infoClass) {
		Table table = new Table();
		List<Column> columns = new ArrayList<Column>(0);
		
		for ( InfoProperty property : infoClass.getProperties() ) {
			columns.add( infoPropertyToColumn(property) );
		}
		
		table.setTableName( infoClass.getIdentityName() );
		table.setColumns( columns );
		table.setComment( infoClass.getName() );
		
		return table;
	}
	
	/**
	 * 将信息类属性转化成表字段信息
	 * @param property 信息类属性
	 * @return
	 */
	public static Column infoPropertyToColumn(InfoProperty property) {
		
		return infoPropertyToColumn( property, "" );
	}
	
	/**
	 * 将信息类属性转化成表字段信息
	 * @param property 信息类属性
	 * @param suffix 表名后缀,如："_LOG"
	 * @return
	 */
	public static Column infoPropertyToColumn(InfoProperty property, String suffix ) {
		Column column = new Column();
		
		String type =  property.getTypeInfo().getDataType();
		
		if( property.getTypeInfo().hasMaxLen() ) {
			type += "(" + property.getFieldLen() + ")";
		}
		InfoClass clasz=InfoClassCache.getInfoClass(property.getClassId());
		column.setTableName( clasz.getIdentityName() + suffix );
		column.setColumnName( property.getFieldName() );
		column.setComment( property.getDescription() );
		//column.setNullable( nullable );
		column.setNullable( true );//不由数据库控制为空，由程序控制
		column.setDefaultV( property.getDefaultValue() );
		column.setType( type );
		
		return column;
	}
}
