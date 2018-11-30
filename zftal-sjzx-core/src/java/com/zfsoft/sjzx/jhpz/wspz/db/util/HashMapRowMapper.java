package com.zfsoft.sjzx.jhpz.wspz.db.util;



import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HashMapRowMapper implements RowMapper {

	public HashMapRowMapper() {
	}

	public Object mappingRow(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();
		Map row = new HashMap();
		for (int i = 1; i <= columnCount; i++) {
			String colName = meta.getColumnName(i).toLowerCase(); // 取得列名，
																	// 转换成小写。
			Object colValue = rs.getObject(i);
			if(colValue==null){
				colValue = "";
			}
			row.put(colName, colValue);
		}
		return row;
	}

}
