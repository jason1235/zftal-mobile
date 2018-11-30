package com.zfsoft.sjzx.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.DBCache;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.zfsoft.framework.page.PageList;
import com.zfsoft.framework.page.Paginator;
import com.zfsoft.sjzx.jhpz.wspz.db.util.DbHelper;
import com.zfsoft.sjzx.jhpz.wspz.db.util.RowMapper;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhRzb;
import com.zfsoft.sjzx.ysj.util.Page;
import com.zfsoft.zfca.tp.cas.util.Tool;

public class DbUtil {
	private static final Logger log = LoggerFactory.getLogger(DbUtil.class);

	/**
	 * 装配数据库相关信息
	 * 
	 * @param poolName
	 * @param accessType
	 * @param dbType
	 * @param serviceName
	 * @param host
	 * @param port
	 * @param user
	 * @param pwd
	 * @return
	 */
	public static DatabaseMeta getPoolMeta(String poolName, String accessType,
			String dbType, String serviceName, String host, String port,
			String user, String pwd) {
		DatabaseMeta meta = new DatabaseMeta();
		meta.setName(poolName);
		meta.setDBName(serviceName);
		meta.setAccessType(DatabaseMeta.getAccessType(accessType));
		meta.setDatabaseType(dbType);
		meta.setHostname(host);
		meta.setDBPort(port);
		meta.setUsername(user);
		meta.setPassword(pwd);
		return meta;
	}

	/**
	 * 清空缓存
	 * 
	 * @param databaseMeta
	 */
	public final static void clearDBCache(DatabaseMeta databaseMeta) {
		if (databaseMeta != null) {
			DBCache.getInstance().clear(databaseMeta.getName());
		} else {
			DBCache.getInstance().clear(null);
		}
	}

	/**
	 * 获取主键字段
	 * 
	 * @param meta
	 * @param bmc
	 * @return
	 */
	public static List<String> getDBTablePk(DatabaseMeta meta, String bmc) {
		DbUtil.clearDBCache(meta);
		List<String> list = new ArrayList<String>();
		Database db = new Database(meta);

		try {
			db.connect();

			ResultSet rs = null;

			try {
				rs = db.getDatabaseMetaData().getPrimaryKeys(null, null,
						bmc.toUpperCase());
				String key = null;
				while (rs.next()) {
					key = rs.getString("COLUMN_NAME");
					key = key.toUpperCase();
					list.add(key);
				}
			} catch (SQLException e) {
				throw new KettleDatabaseException(e);
			} finally {
				db.closeQuery(rs);
				rs = null;
			}
		} catch (KettleDatabaseException e) {
			db.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			db.disconnect();
		}
		return list;
	}

	public static Map<String, Object> sqlTest(DatabaseMeta meta, String sql) {
		DbUtil.clearDBCache(meta);
		Map<String, Object> map = new HashMap<String, Object>();
		Database db = new Database(meta);
		boolean success = false;
		String msg = null;
		try {
			db.connect();
			db.openQuery(sql);
			success = true;
			msg = "测试成功";
		} catch (KettleDatabaseException e1) {
			success = false;
			msg = "测试失败：" + e1.getMessage();
			log.debug(msg);
		} finally {
			db.disconnect();
		}
		map.put("success", success);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 获取ORACLE的表字段的注释
	 * 
	 * @param meta
	 * @param bmc
	 * @return
	 */
	public static Map<String, String> getDBTableFieldComments(
			DatabaseMeta meta, String bmc) {
		DbUtil.clearDBCache(meta);
		Map<String, String> map = new HashMap<String, String>();
		Database db = new Database(meta);
		try {
			db.connect();

			StringBuffer sb = new StringBuffer();
			if (meta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_ORACLE) {
				sb.append("SELECT COLUMN_NAME, nvl(comments,column_name) REMARKS FROM user_col_comments WHERE table_name ='");
				sb.append(bmc.toUpperCase());
				sb.append("'");
			} else if (meta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_MYSQL) {
				sb.append("SELECT COLUMN_NAME, COLUMN_COMMENT REMARKS FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name ='");
				sb.append(bmc.toUpperCase());
				sb.append("'");

				String schemaname = null;
				if (meta.useSchemaNameForTableList())
					schemaname = meta.environmentSubstitute(meta.getUsername())
							.toUpperCase();
				if (schemaname != null) {
					sb.append(" AND table_schema ='");
					sb.append(schemaname);
					sb.append("'");
				}
			}

			ResultSet rs = null;
			try {
				rs = db.openQuery(sb.toString());
				String column_name = null;
				String remarks = null;
				while (rs.next()) {
					column_name = rs.getString(1);
					column_name = column_name.toUpperCase();
					remarks = rs.getString(2);
					map.put(column_name, remarks);
				}
			} catch (SQLException e) {
				log.debug(e.getMessage());
				throw new KettleDatabaseException(e);
			} finally {
				db.closeQuery(rs);
			}

		} catch (KettleDatabaseException e1) {
			db.disconnect();
			e1.printStackTrace();
			log.debug(e1.getMessage());
			return null;
		} finally {
			db.disconnect();
		}
		return map;
	}

	/**
	 * 获取sql语句的字段属性
	 * 
	 * @param meta
	 *            数据库配置信息
	 * @param sql
	 *            表或者语句
	 * @param isTable
	 *            是否表
	 * @return
	 */
	public static List<Map<String, String>> getSQLTableFields(
			DatabaseMeta meta, String sql, boolean isTable) {
		DbUtil.clearDBCache(meta);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Database db = new Database(meta);
		try {
			db.connect();
			RowMetaInterface rmi = null;
			if (isTable)
				rmi = db.getTableFields(sql);
			else
				rmi = db.getQueryFields(sql, false);
			Map<String, String> map = null;
			for (int i = 0, len = rmi.size(); i < len; i++) {
				map = new HashMap<String, String>();
				ValueMetaInterface vm = rmi.getValueMeta(i);
				// ZDM VARCHAR2(20) Y 字段名
				String zdm = vm.getName().toUpperCase();
				map.put("COLUMN_NAME", zdm);
				map.put("TYPE", String.valueOf(vm.getType()));
				map.put("TRIMTYPE", String.valueOf(vm.getTrimType()));
				// ZDLXMS VARCHAR2(20) Y 字段类型描述
				map.put("TYPEDESC", vm.getTypeDesc());
				// ZDCD NUMBER(6) Y 字段长度
				map.put("LENGTH", String.valueOf(vm.getLength()));
				map.put("ORIGIN", vm.getOrigin());
				map.put("ORIGINALCOLUMNTYPE",
						String.valueOf(vm.getOriginalColumnType()));
				// ZDLX VARCHAR2(20) Y 字段类型
				map.put("ORIGINALCOLUMNTYPENAME",
						vm.getOriginalColumnTypeName());
				map.put("ORIGINALPRECISION",
						String.valueOf(vm.getOriginalPrecision()));
				map.put("ORIGINALSCALE", String.valueOf(vm.getOriginalScale()));
				// ZDJD NUMBER(6) Y 字段精度
				map.put("PRECISION", String.valueOf(vm.getPrecision()));
				// SFWK NUMBER(1) Y 是否为空
				map.put("NULLABLE", String.valueOf(vm.isOriginalNullable()));
				list.add(map);
			}
		} catch (KettleDatabaseException e) {
			db.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			db.disconnect();
		}
		//log.debug("getSQLTableFields:{}", list);
		return list;
	}

	public static List<Map<String, String>> getDBTableFields(DatabaseMeta meta,
			String bmc) {
		DbUtil.clearDBCache(meta);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> commmap = getDBTableFieldComments(meta, bmc);
		List<String> pklist = getDBTablePk(meta, bmc);
		Database db = new Database(meta);
		try {
			db.connect();
			RowMetaInterface rmi = db.getTableFields(bmc);
			Map<String, String> map = null;
			for (int i = 0, len = rmi.size(); i < len; i++) {
				map = new HashMap<String, String>();
				ValueMetaInterface vm = rmi.getValueMeta(i);
				/*
				 * System.out.println("getType ="+vm.getType());; //ZDLXMS
				 * VARCHAR2(20) Y 字段类型描述
				 * System.out.println("getTypeDesc ="+vm.getTypeDesc());;
				 * System.out.println("getTrimType ="+vm.getTrimType());;
				 * System.
				 * out.println("getOriginalColumnType ="+vm.getOriginalColumnType
				 * ());; //ZDCD NUMBER(6) Y 字段长度
				 * System.out.println("getLength ="+vm.getLength()); //ZDM
				 * VARCHAR2(20) Y 字段名
				 * System.out.println("getName ="+vm.getName());;
				 * System.out.println("getOrigin ="+vm.getOrigin());; //ZDLX
				 * VARCHAR2(20) Y 字段类型
				 * System.out.println("getOriginalColumnTypeName ="
				 * +vm.getOriginalColumnTypeName());
				 * System.out.println("getOriginalPrecision ="
				 * +vm.getOriginalPrecision());;
				 * System.out.println("getOriginalScale ="
				 * +vm.getOriginalScale());; //ZDJD NUMBER(6) Y 字段精度
				 * System.out.println("getPrecision ="+vm.getPrecision());;
				 * //SFWK NUMBER(1) Y 是否为空
				 * System.out.println("isOriginalNullable ="
				 * +vm.isOriginalNullable());; System.out.println(
				 * "----------------------------------------------");
				 */
				String zdm = vm.getName().toUpperCase();
				map.put("COLUMN_NAME", zdm);
				map.put("TYPEDESC", vm.getTypeDesc());
				map.put("REMARKS", commmap.get(zdm));
				map.put("NULLABLE", String.valueOf(vm.isOriginalNullable()));
				if (pklist.contains(zdm)) {
					map.put("PK", "1");
				} else {
					map.put("PK", "0");
				}
				list.add(map);
			}
		} catch (KettleDatabaseException e) {
			db.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			db.disconnect();
		}
		return list;
	}

	public static List<Map<String, String>> getDBTables(DatabaseMeta meta,
			String bm, Page page) {
		DbUtil.clearDBCache(meta);
		Database db = new Database(meta);
		ResultSet rs = null;
		boolean includeSchema = false;
		String schemaname = null;
		String schemaTable = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> tabmap = null;
		PreparedStatement sel_stmt = null;
		try {
			db.connect();

			if (meta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_ORACLE) {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT t.table_name TABLE_NAME,nvl(t.comments,t.table_name) REMARKS FROM user_tab_comments t ");
				if (bm != null) {
					sb.append(" WHERE table_name Like'%");
					sb.append(bm.toUpperCase());
					sb.append("%'");
				}
				String sql = meta.stripCR(sb.toString());
				try {
					sel_stmt = db.getConnection().prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					;
					if (meta.isFetchSizeSupported()
							&& (sel_stmt.getMaxRows() > 0
									|| meta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_POSTGRES || (meta
									.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_MYSQL && meta
									.isStreamingResults()))) {
						int fs = Const.FETCH_SIZE <= sel_stmt.getMaxRows() ? sel_stmt
								.getMaxRows() : Const.FETCH_SIZE;
						if (meta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_MYSQL
								&& meta.isStreamingResults()) {
							sel_stmt.setFetchSize(Integer.MIN_VALUE);
						} else {
							sel_stmt.setFetchSize(fs);
						}
						sel_stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
					}
					rs = sel_stmt.executeQuery(sql);
				} catch (SQLException e) {
					throw new KettleDatabaseException(e);
				}

				// rs = db.openQuery(sb.toString());

			} else {
				if (meta.useSchemaNameForTableList())
					schemaname = meta.environmentSubstitute(meta.getUsername())
							.toUpperCase();
				try {
					rs = db.getDatabaseMetaData().getTables(null, schemaname,
							null, meta.getTableTypes());
				} catch (SQLException e) {
					throw new KettleDatabaseException(e);
				}

			}

			try {
				int pager = page.getPageTo();
				int pageSize = page.getPerPageSize();

				if (pager < 1)
					pager = 1;
				rs.last();
				int totRowCount = rs.getRow();
				if (totRowCount > 0) {
					// 记算总页数
					int PageCount = (totRowCount + pageSize - 1) / pageSize;
					page.setPageCount(PageCount);
					// 调整待显示的页码
					if (pager > PageCount)
						pager = PageCount;

					int row = (pager - 1) * pageSize + 1;
					rs.absolute(row);
					int i = 0;
					boolean bmNull = bm == null || bm.equals("");
					String bmc = bm.toUpperCase();
					String tab = null;
					while (i < pageSize && !rs.isAfterLast()) {
						String table = rs.getString("TABLE_NAME");
						if (includeSchema) {
							String schema = rs.getString("TABLE_SCHEM");
							if (Const.isEmpty(schema))
								schema = rs.getString("TABLE_CAT"); // retry for
																	// the
																	// catalog.
							schemaTable = meta.getQuotedSchemaTableCombination(
									schema, table);
						} else {
							schemaTable = table;
						}
						tab = schemaTable.toUpperCase();
						if (bmNull || schemaTable.indexOf(bm) > -1
								|| tab.startsWith(bmc) || tab.endsWith(bmc)) {
							tabmap = new HashMap<String, String>();
							tabmap.put("TABLE_NAME", schemaTable);

							String comments = rs.getString("REMARKS");
							if (null == comments || "".equals(comments)) {
								comments = schemaTable;
							}
							tabmap.put("REMARKS", comments);
							list.add(tabmap);
							i++;
						}
						rs.next();

					}
				}

			} catch (SQLException e) {
				log.debug(e.getMessage());
				throw new KettleDatabaseException(e);
			} finally {
				db.closeQuery(rs);
				if (sel_stmt != null) {
					try {
						sel_stmt.close();
					} catch (SQLException e) {
						throw new KettleDatabaseException(e);
					}
					sel_stmt = null;
				}
			}

		} catch (KettleDatabaseException e) {
			db.disconnect();
			log.debug(e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			db.disconnect();
		}
		return list;
	}

	/**
	 * 获取指定数据库的所有表
	 * 
	 * @param databaseMeta
	 * @return
	 */
	public static String[] getDBTables(DatabaseMeta databaseMeta) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			String[] tables = null;
			if (databaseMeta.getHostname() != null
					&& databaseMeta.getHostname().startsWith("t3")
					&& databaseMeta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_ORACLE) {
				ResultSet rs = null;
				try {
					rs = database
							.openQuery("SELECT table_name FROM user_tab_comments order by table_name ");
					List<String> list = new ArrayList<String>();
					while (rs.next()) {
						list.add(rs.getString(1));
					}
					tables = list.toArray(new String[list.size()]);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (rs != null) {
							rs.close();
							rs = null;
						}
						database.closeQuery(rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				tables = database.getTablenames();
			}
			return tables;
		} catch (KettleDatabaseException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			database.disconnect();
		}
	}

	/**
	 * 获取指定数据库的所有视图
	 * 
	 * @param databaseMeta
	 * @return
	 */
	public static String[] getDBViews(DatabaseMeta databaseMeta) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			database.getDatabaseMetaData();
			String[] views = null;
			if (databaseMeta.getHostname() != null
					&& databaseMeta.getHostname().startsWith("t3")
					&& databaseMeta.getDatabaseType() == DatabaseMeta.TYPE_DATABASE_ORACLE) {
				ResultSet rs = null;
				try {
					rs = database.openQuery("SELECT view_name FROM user_views");
					List<String> list = new ArrayList<String>();
					while (rs.next()) {
						list.add(rs.getString(1));
					}
					views = list.toArray(new String[list.size()]);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (rs != null) {
							rs.close();
							rs = null;
						}
						database.closeQuery(rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				views = database.getViews();
			}
			return views;
		} catch (KettleDatabaseException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			database.disconnect();
		}
	}

	/**
	 * 获取指定表中的属性值
	 * 
	 * @param databaseMeta
	 * @return
	 */
	public static List<Map<String, String>> getValueByTable(
			DatabaseMeta databaseMeta, String sql,
			List<WsjhParamsConfig> wpcList, WsjhRzb rzb) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			database.connect();
			database.getDatabaseMetaData();
			rs = database.openQuery(sql);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (WsjhParamsConfig wpc : wpcList) {
					if (wpc != null && !isFilterFields(wpc)) {
						map.put(wpc.getCszd(),
								getValueByType(wpc.getCszdt(), wpc.getCsz(), rs));
					}
					if (wpc != null && isFilterFields(wpc)) {
						map.put(wpc.getCszd(), wpc.getCsz());
					}
				}
				list.add(map);
			}
		} catch (Exception e) {
			rzb.setErrors((rzb.getErrors() == null ? 0 : rzb.getErrors()) + 1);
			rzb.setLog(rzb.getLog() + " " + e.getMessage());
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				database.closeQuery(rs);
				database.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	/**
	 * <p>
	 * 判断是否过滤字段
	 * </p>
	 * 
	 * @param wpc
	 * @return
	 */
	private static boolean isFilterFields(WsjhParamsConfig wpc) {
		if (!Tool.isNull(wpc.getCszd())
				&& (wpc.getCszd().equals("PAGE")
						|| wpc.getCszd().equals("PAGESIZE") || wpc.getCszd()
						.equals("SIGN")))
			return true;
		return false;
	}

	private static String getValueByType(String type, String name, ResultSet rs)
			throws SQLException {
		if ("String".equals(type))
			return rs.getString(name) == null ? "" : rs.getString(name);
		else if ("Number".equals(type))
			return String.valueOf(rs.getObject(name) == null ? "" : rs
					.getObject(name));
		else if ("Date".equals(type))
			return rs.getDate(name) == null ? "" : rs.getDate(name).toString();
		else if ("Boolean".equals(type))
			return String.valueOf(rs.getBoolean(name));
		else if ("Integer".equals(type))
			return String.valueOf(rs.getInt(name));
		else if ("BigNumber".equals(type))
			return String.valueOf(rs.getBigDecimal(name) == null ? "" : rs
					.getBigDecimal(name));
		else if ("Binary".equals(type))
			return String.valueOf(rs.getBinaryStream(name) == null ? "" : rs
					.getBinaryStream(name));
		return rs.getString(name);
	}

	public static String[] getTableFields(String tablename,
			DatabaseMeta databaseMeta) {
		Database db = new Database(databaseMeta);
		try {
			db.connect();
			clearDBCache(databaseMeta);
			RowMetaInterface rmi = db.getTableFields(tablename);
			String[] fieldNames = rmi.getFieldNames();
			return fieldNames;
		} catch (KettleDatabaseException e) {
			db.disconnect();
			log.debug(e.getMessage());
			e.printStackTrace();
		} finally {
			db.disconnect();
		}
		return null;
	}

	public static String getIsNotNullFields(String tablename,
			DatabaseMeta databaseMeta) {
		Database db = new Database(databaseMeta);
		try {
			db.connect();
			clearDBCache(databaseMeta);
			RowMetaInterface rmi = db.getTableFields(tablename);
			// int size = rmi.size();
			String fields = "";
			for (ValueMetaInterface v : rmi.getValueMetaList()) {
				if (v.isOriginalNullable() == 0) {
					fields += v.getName() + ",";
				}
			}
			return StringUtils.substringBeforeLast(fields, ",");
		} catch (KettleDatabaseException e) {
			db.disconnect();
			log.debug(e.getMessage());
			e.printStackTrace();
			return "";
		} finally {
			db.disconnect();
		}
	}

	/**
	 * 获取表的字段名
	 * 
	 * @param tablename
	 * @param databaseMeta
	 * @param flag
	 * @return
	 */
	public static String[] getTableFields(String tablename,
			DatabaseMeta databaseMeta, String tableType) {
		Database db = new Database(databaseMeta);
		try {
			db.connect();
			clearDBCache(databaseMeta);
			RowMetaInterface rmi = null;
			if ("1".equalsIgnoreCase(tableType)) {
				rmi = db.getTableFields(tablename);
			} else {
				rmi = db.getQueryFields(tablename, false);
			}

			String[] fieldNames = rmi.getFieldNames();
			return fieldNames;
		} catch (KettleDatabaseException e) {
			db.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			db.disconnect();
		}

	}

	/**
	 * 获取表的字段名和字段类型
	 * 
	 * @param tablename
	 * @param databaseMeta
	 * @param flag
	 * @return
	 */
	public static String[] getTableFieldsAndTypes(String tablename,
			DatabaseMeta databaseMeta, String tableType) {
		if (Tool.isNull(tablename))
			return null;
		Database db = new Database(databaseMeta);
		try {
			db.connect();
			clearDBCache(databaseMeta);
			RowMetaInterface rmi = null;
			if ("1".equalsIgnoreCase(tableType)) {
				rmi = db.getTableFields(tablename);
			} else {
				rmi = db.getQueryFields(tablename, false);
			}
			int size = rmi.size();
			String[] field = new String[size];
			ValueMetaInterface v = null;
			for (int i = 0; i < size; i++) {
				v = rmi.getValueMeta(i);
				field[i] = v.getName() + ":" + v.getTypeDesc() + ":"
						+ v.getType() + ":" + v.getLength();
			}
			// 对数组内的元素进行排序
			Arrays.sort(field);
			return field;
		} catch (KettleDatabaseException e) {
			db.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			db.disconnect();
		}

	}

	/**
	 * <p>
	 * 执行更新语句
	 * </p>
	 * 
	 * @param updateSQL
	 */
	public static void execUpdateSQL(DatabaseMeta databaseMeta, String updateSQL) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);

		try {
			database.connect();
			database.getDatabaseMetaData();
			database.execStatement(updateSQL);
			PreparedStatement ps = null;
			database.executeBatch(ps);
		} catch (Exception e) {
			database.disconnect();
			log.debug(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				database.disconnect();
			} catch (Exception e) {
				log.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public static void batchUpdateSQL(DatabaseMeta databaseMeta,
			List<String> updateSQLList) throws Exception {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			database.getDatabaseMetaData();
			Statement stat = database.getConnection().createStatement();
			for (String updateSQL : updateSQLList) {
				if (!Tool.isNull(updateSQL))
					stat.addBatch(updateSQL);
			}
			stat.executeBatch();
			stat.close();
			database.disconnect();
		} finally {
			database.disconnect();
		}
	}

	public static List<Map<String, String>> execQuerySQL(
			DatabaseMeta databaseMeta, String sql, Set<String> set) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		try {
			database.connect();
			database.getDatabaseMetaData();
			ResultSet rs = database.openQuery(sql);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (String s : set) {
					if (!Tool.isNull(s)) {
						Object obj = rs.getObject(s);
						obj = obj == null ? "" : obj;
						map.put(s, String.valueOf(obj));
					}
				}
				mapList.add(map);
			}
			rs.close();
		} catch (Exception e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			try {
				database.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mapList;
	}

	public static String getSjcType(String sjc, String tablename,
			DatabaseMeta databaseMeta) {
		String sql = "select DATA_TYPE from user_tab_cols where TABLE_NAME='"
				+ tablename.toUpperCase() + "' and COLUMN_NAME='"
				+ sjc.toUpperCase() + "' ";
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			database.getDatabaseMetaData();
			ResultSet rs = database.openQuery(sql);
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
		} finally {
			try {
				database.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String[] getDBTablesAndComments(DatabaseMeta databaseMeta) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			String[] tables = null;
			List<String> list = new ArrayList<String>();
			ResultSet rs = null;
			if ("MSSQL".equals(databaseMeta.getDatabaseTypeDesc())) {
				rs = database
						.openQuery("select name from sysobjects where xtype='U' order by name ");
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}else if("mysql".equalsIgnoreCase((databaseMeta.getDatabaseTypeDesc()))){/*增加了mysql的判断    dingsj  2015-05-12*/
				rs = database.openQuery("select table_name,table_comment from information_schema.tables where table_schema='"+databaseMeta.getDatabaseName()+"'");
				while (rs.next()) {
					String comments = rs.getString(2);
					String table = rs.getString(1);
					if (Tool.isNull(comments))
						list.add(table);
					else
						list.add(comments + "@" + table);
				}
			}else {
				rs = database
						.openQuery("SELECT distinct table_name,comments FROM user_tab_comments order by table_name ");
				while (rs.next()) {
					String comments = rs.getString(2);
					String table = rs.getString(1);
					if (Tool.isNull(comments))
						list.add(table);
					else
						list.add(comments + "@" + table);
				}
			}
			rs.close();
			tables = list.toArray(new String[list.size()]);
			return tables;

		} catch (SQLException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} catch (KettleDatabaseException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			database.disconnect();
		}
	}

	public static List getDBColumnAndComments(DatabaseMeta databaseMeta,
			String tableName) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			List list = new ArrayList();
			ResultSet rs = null;
			if ("MSSQL".equals(databaseMeta.getDatabaseTypeDesc())) {
				rs = database
						.openQuery("select NAME from syscolumns where id=object_id('"
								+ tableName + "')");
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("COLUMN_NAME", rs.getString("NAME"));
					list.add(map);
				}
				rs.close();
			}else if("mysql".equalsIgnoreCase((databaseMeta.getDatabaseTypeDesc()))){/*增加了mysql的判断    dingsj  2015-05-12*/
				rs = database.openQuery("select distinct t.COLUMN_NAME,IFNULL(t.COLUMN_COMMENT,t.COLUMN_NAME)COLUMN_COMMENT from information_schema.columns t where table_schema='"+databaseMeta.getDatabaseName()+"' "
						+ "and table_name='"+tableName+"' order by t.COLUMN_NAME");
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
					map.put("COMMENTS", rs.getString("COLUMN_COMMENT"));
					list.add(map);
				}
				rs.close();
			} else {
				rs = database
						.openQuery("SELECT distinct t.column_name COLUMN_NAME,nvl(t.comments,t.column_name) COMMENTS FROM user_col_comments t WHERE t.table_name ='"
								+ tableName + "' order by t.column_name");
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
					map.put("COMMENTS", rs.getString("COMMENTS"));
					list.add(map);
				}
				rs.close();
			}

			return list;

		} catch (SQLException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} catch (KettleDatabaseException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			database.disconnect();
		}
	}

	public static List execQuerySQL(DatabaseMeta databaseMeta, String sql,
			String[] objParams, RowMapper rowMapper) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		List mapList = new ArrayList();
		try {
			database.connect();
			database.getDatabaseMetaData();
			PreparedStatement pstmt = null;
			pstmt = database.getConnection().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (objParams != null)
				DbHelper.bindParameters(pstmt, objParams);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object rowObj = rowMapper.mappingRow(rs);
				mapList.add(rowObj);
			}
			rs.close();
		} catch (Exception e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			try {
				database.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mapList;
	}

	public static List execQuerySQL(DatabaseMeta databaseMeta, String sql,
			RowMapper rowMapper) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		List mapList = new ArrayList();
		try {
			database.connect();
			database.getDatabaseMetaData();
			ResultSet rs = database.openQuery(sql);
			while (rs.next()) {
				Object rowObj = rowMapper.mappingRow(rs);
				mapList.add(rowObj);
			}
			rs.close();
		} catch (Exception e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			try {
				database.disconnect();
			} catch (Exception e) {
				log.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		return mapList;
	}

	public static List getDbTables(DatabaseMeta databaseMeta) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		try {
			database.connect();
			// if(databaseMeta.getHostname() != null &&
			// databaseMeta.getHostname().startsWith("t3") &&
			// databaseMeta.getDatabaseType() ==
			// DatabaseMeta.TYPE_DATABASE_ORACLE){
			try {
				rs = database
						.openQuery("select table_name,comments from user_tab_comments   where  table_type='TABLE'");

				while (rs.next()) {
					Map map = new HashMap();
					String table_name = rs.getString("table_name");
					map.put("table_name", table_name);
					String comments = rs.getString("comments");
					if (comments != null && !comments.equalsIgnoreCase("")) {
						map.put("comments", comments);
					} else {
						map.put("comments", table_name);
					}
					list.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					database.closeQuery(rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// }else{
			// tables = database.getTablenames();
			// }
		} catch (KettleDatabaseException e) {
			e.printStackTrace();
		} finally {
			database.disconnect();
		}

		return list;
	}

	/**
	 * 用于ODI预警
	 * 
	 * @param databaseMeta
	 * @param param
	 * @return
	 */
	public static List<HashMap<String, String>> getDbTablesByParams(
			DatabaseMeta databaseMeta, String param) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		ResultSet rs = null;
		HashMap<String, String> hashmap;

		try {
			database.connect();
			StringBuilder sb = new StringBuilder();

			try {
				rs = database
						.openQuery("select table_name,comments from user_tab_comments   where  table_type='TABLE'"
								+ " AND REGEXP_LIKE (TABLE_NAME,'^[	"
								+ param
								+ "]\\$') order by table_name");

				while (rs.next()) {
					HashMap<String, String> map = new HashMap<String, String>();
					String table_name = rs.getString("table_name");
					map.put("table_name", table_name);
					sb.append("SELECT '" + table_name
							+ "' TABLE_NAME, COUNT(1) RECORD_NUM FROM "
							+ table_name + " UNION ALL ");
				}

				String sql = sb.toString();
				if (StringUtils.isBlank(sql)) {
					return list;
				}

				sql = sql.substring(0, sql.length() - 10);
				String sql2 = "select  N.TABLE_NAME, N.RECORD_NUM, U.COMMENTS from ("
						+ sql
						+ ")N,"
						+ "USER_TAB_COMMENTS U where N.TABLE_NAME = U.TABLE_NAME  AND record_Num>0 order by record_num desc ";
				rs = database.openQuery(sql2);
				while (rs.next()) {
					hashmap = new HashMap<String, String>();
					String table_name = rs.getString("table_name");
					String record_num = rs.getString("record_num");
					String comments = rs.getString("comments");
					if (StringUtils.isBlank(comments))
						hashmap.put("comments", table_name);
					else
						hashmap.put("comments", comments);
					hashmap.put("RECORD_NUM", record_num);
					hashmap.put("table_name", table_name);
					list.add(hashmap);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					database.closeQuery(rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (KettleDatabaseException e) {
			e.printStackTrace();
		} finally {
			database.disconnect();
		}

		return list;
	}

	public static List<Map> getTableData(String tableName,
			DatabaseMeta databaseMeta, String[] fieldArr) {

		ResultSet rs = null;
		List<Map> list = new ArrayList();

		DbUtil.clearDBCache(databaseMeta);
		Database db = new Database(databaseMeta);
		String sql = "select * from  " + tableName;
		try {
			db.connect();
			rs = db.openQuery(sql);
			while (rs.next()) {
				Map map = new HashMap();
				for (int j = 0; j < fieldArr.length; j++) {
					map.put(fieldArr[j], rs.getString(fieldArr[j]));
				}
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (KettleDatabaseException kde) {
			kde.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.disconnect();
		}

		return list;
	}

	public static PageList getTableDataPage(String tableName,
			DatabaseMeta databaseMeta, String[] fieldArr, int toPage) {

		ResultSet rs = null;

		DbUtil.clearDBCache(databaseMeta);
		Database db = new Database(databaseMeta);

		PageList pageList = new PageList();
		Paginator paginator = new Paginator();

		int count = 0;

		try {
			db.connect();
			rs = db.openQuery("select  count(*) as c  from  " + tableName);
			if (rs.next()) {
				count = rs.getInt("c");
			}
			// paginator.setItemsPerPage(perPageSize);
			paginator.setItems(count);
			paginator.setPage(toPage);

			rs.close();

			rs = db.openQuery("select * from (select t.*,rownum rn from "
					+ tableName + " t    ) where rn>= "
					+ paginator.getBeginIndex() + " and rn<="
					+ paginator.getEndIndex());
			while (rs.next()) {
				Map map = new HashMap();
				for (int j = 0; j < fieldArr.length; j++) {
					map.put(fieldArr[j], rs.getString(fieldArr[j]));
				}
				pageList.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (KettleDatabaseException kde) {
			kde.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.disconnect();
		}

		pageList.setPaginator(paginator);
		return pageList;
	}

	public static String getTableComment(String tableName,
			DatabaseMeta databaseMeta) {
		String table_comment = null;
		ResultSet rs = null;
		DbUtil.clearDBCache(databaseMeta);
		Database db = new Database(databaseMeta);
		String sql = "select * from user_tab_comments where table_name='"
				+ tableName.toUpperCase() + "'";
		try {
			db.connect();
			rs = db.openQuery(sql);
			if (rs.next()) {
				table_comment = rs.getString("comments");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (KettleDatabaseException kde) {
			kde.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			db.disconnect();
		}
		return table_comment;
	}

	public static String[] getDBTablesAndView(DatabaseMeta databaseMeta) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		try {
			database.connect();
			String[] tables = null;
			ResultSet rs = null;
			try {
				rs = database
						.openQuery("SELECT table_name FROM user_tab_comments  where table_name not like 'BIN$%=%' order by table_name ");
				List<String> list = new ArrayList<String>();
				while (rs.next()) {
					list.add(rs.getString(1));
				}
				tables = list.toArray(new String[list.size()]);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					database.closeQuery(rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return tables;
		} catch (KettleDatabaseException e) {
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			database.disconnect();
		}
	}

	public static String getLastDayBySQL(DatabaseMeta databaseMeta, String sql) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			database.connect();
			database.getDatabaseMetaData();
			rs = database.openQuery(sql);
			while (rs.next()) {
				list.add(String.valueOf(rs.getObject(1)));
			}
			rs.close();
		} catch (Exception e) {
			try {
				database.closeQuery(rs);
			} catch (KettleDatabaseException e1) {
				e1.printStackTrace();
			}
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			try {
				database.closeQuery(rs);
				database.disconnect();
			} catch (Exception e) {
				log.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		if (list != null && list.size() > 0)
			return list.get(list.size() - 1);
		return null;
	}

	public static List<Map<String, String>> getValueByTable(
			DatabaseMeta databaseMeta, String sql, WsjhRzb rzb,
			List<String> columnList) {
		clearDBCache(databaseMeta);
		Database database = new Database(databaseMeta);
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			database.connect();
			database.getDatabaseMetaData();
			rs = database.openQuery(sql);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				if (columnList != null && columnList.size() > 0) {
					for (String column : columnList) {
						if (column != null && !"".equals(column)) {
							map.put(column,
									String.valueOf(rs.getObject(column)));
						}
					}
				}
				list.add(map);
			}
		} catch (Exception e) {
			rzb.setErrors((rzb.getErrors() == null ? 0 : rzb.getErrors()) + 1);
			rzb.setLog(rzb.getLog() + " " + e.getMessage());
			database.disconnect();
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				database.closeQuery(rs);
				database.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
