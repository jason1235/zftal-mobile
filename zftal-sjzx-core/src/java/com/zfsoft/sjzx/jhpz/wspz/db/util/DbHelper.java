package com.zfsoft.sjzx.jhpz.wspz.db.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

import org.pentaho.di.core.logging.LogWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;



public class DbHelper {

	private static final LogWriter log = LogWriter.getInstance();
	private static SqlMapClientTemplate sqlMapClientTemplate;

	public static  Connection getConnection() throws SQLException {
		return getConnection(true); //自动提交
	}

	public static  Connection getConnection(boolean isAutoCommit) throws SQLException {
		Connection con = sqlMapClientTemplate.getDataSource().getConnection();
		con.setAutoCommit(isAutoCommit); //设置是否自动提交
		return con;
	}



	public static SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public static void setSqlMapClientTemplate(	SqlMapClientTemplate sqlMapClientTemplate) {
		DbHelper.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public static Statement getStatement(Connection conn) throws SQLException {
		return conn.createStatement();
	}

	public static PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	public static void commit(Connection conn){
		try {
			if(conn != null)
				conn.commit();
		} catch (SQLException e) {
			log.logError("DbHelper异常", "commit方法",e);
		}
	}


	public static void rollback(Connection conn){
		try {
			if(conn != null)
				conn.rollback();
		} catch (SQLException e) {
			log.logError("DbHelper异常", "rollback方法",e);
		}
	}

	public static void close(Connection conn){
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			log.logError("DbHelper异常", "close连接方法",e);
		}finally{
			conn = null;
		}
	}

	public static void close(Statement stmt) {
		try {
			if(stmt != null)
				stmt.close();
		} catch (SQLException e) {
			log.logError("DbHelper异常", "closeStatement方法",e);
		}finally{
			stmt = null;
		}
	}

	public static void close(PreparedStatement pstmt){
		try {
			if(pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			log.logError("DbHelper异常", "closePreparedStatement方法",e);
		}finally{
			pstmt = null;
		}

	}

	public static void bindParameters(PreparedStatement pstmt, Object[] params) throws SQLException{
		for(int i=0; i<params.length; i++){
			setCustomObject(pstmt, i+1, params[i]);
		}
	}

	public static void bindParameter(PreparedStatement pstmt, Object param) throws SQLException{
		setCustomObject(pstmt, 1, param);
	}


	private static void setCustomObject(PreparedStatement pstmt, int i, Object obj) throws SQLException {
		if(obj == null) {
			pstmt.setString(i, "");
		}
		else if(obj instanceof String) {
			pstmt.setString(i, (String)obj);
		}
		else if(obj instanceof Integer){
			pstmt.setInt(i, ((Integer)obj).intValue());
		}
		else if (obj instanceof Byte) {
			pstmt.setByte(i, ((Byte)obj).byteValue());
		}
		else if (obj instanceof Short) {
			pstmt.setShort(1, ((Short)obj).shortValue());
		}
		else if (obj instanceof Long) {
			pstmt.setLong(i, ((Long)obj).longValue());
		}
		else if (obj instanceof Float) {
			pstmt.setFloat(i, ((Float)obj).floatValue());
		}
		else if (obj instanceof Double) {
			pstmt.setDouble(i, ((Double)obj).doubleValue());
		}
		else if (obj instanceof BigDecimal) {
			pstmt.setBigDecimal(i, (BigDecimal)obj);
		}
		else if (obj instanceof Boolean) {
			pstmt.setBoolean(i, ((Boolean)obj).booleanValue());
		}
		else if (obj instanceof Date) {
			pstmt.setDate(i, (Date)obj);
		}
		else if (obj instanceof Time) {
			pstmt.setTime(i, (Time)obj);
		}
		else if (obj instanceof Timestamp) {
			pstmt.setTimestamp(i, (Timestamp)obj);
		}
		else if(obj instanceof byte[]) {
			pstmt.setBytes(i, (byte[])obj);
		}
		else
			pstmt.setObject(i, obj);
	}

	private static RowMapper objArrayRow, mapArrayRow,stringMapper;

	public static RowMapper createObjectArrayRowMapper(){
		if(objArrayRow == null)
			objArrayRow = new ObjectArrayRowMapper();
		return objArrayRow;
	}

	public static RowMapper createMapArrayRowMapper(){
		if(mapArrayRow == null)
			mapArrayRow = new HashMapRowMapper();
		return mapArrayRow;
	}

	public static RowMapper createMapStringMapper(){
		if(stringMapper == null)
			stringMapper = new StringMapper();
		return stringMapper;
	}


}
