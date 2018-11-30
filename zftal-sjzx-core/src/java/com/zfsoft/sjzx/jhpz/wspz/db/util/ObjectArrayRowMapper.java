package com.zfsoft.sjzx.jhpz.wspz.db.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ObjectArrayRowMapper implements RowMapper {

	public ObjectArrayRowMapper() {
	}

	public Object mappingRow(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();
		Object[] objs = new Object[columnCount];
		for (int i = 1; i <= columnCount; i++) {
			objs[i] = rs.getObject(i);
		}
		return objs;
	}

}
