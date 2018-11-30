package com.zfsoft.sjzx.jhpz.wspz.db.util;



import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
	
	Object mappingRow(ResultSet rs) throws SQLException;

}
