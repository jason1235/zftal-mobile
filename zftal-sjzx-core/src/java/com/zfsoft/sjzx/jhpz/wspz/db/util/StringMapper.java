package com.zfsoft.sjzx.jhpz.wspz.db.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class StringMapper implements RowMapper {
	
	public StringMapper() {
	}

	public Object mappingRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();
		String[] values = new String[columnCount];
		for (int i = 1,j=0; i <= columnCount; i++,j++) {
			Object colValue = rs.getObject(i);
			if(colValue != null){
				values[j] = colValue.toString();
			}else{
				values[j] = "";
			}
		}
		return values;
	}

}
