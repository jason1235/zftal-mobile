package com.zfsoft.service.common.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.Subsystem;
import com.zfsoft.common.log.User;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.entities.SjfwzModel;
import com.zfsoft.dataprivilege.util.DataFilterUtil;
import com.zfsoft.service.svcinterface.ISjfwzService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.reflect.ReflectHelper;
import com.zfsoft.utility.InterceptSrcForSubsystem;
import com.zfsoft.utility.Value2Entity;




@Intercepts({
	@Signature(type=StatementHandler.class,method="prepare",args={Connection.class}),
	@Signature(type=Executor.class,method="update",args={MappedStatement.class,Object.class }),
	@Signature(type=Executor.class,method="query",args={MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class}),
	})
public class SqlPlugin implements Interceptor {

	private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SqlPlugin.class);

	private static String dialect = "";	   //数据库方言
	private static String pageSqlId = "";  //分页Id,mapper.xml中需要拦截的ID(正则匹配)
	private static String scopeId = "";    //数据权限Id,以*byScope*开头的ID,都会被匹配到
	private static final String reqParm = " 1=2" ;//返回不成功则，则条件为1=2
	private static final String pasParm = " 1=1" ;//如果bmdm_id=-1或bmdm_id=-2或bmdm_id=-3,则直接返回成功
	private ISjfwzService sjfwzService; //数据范围组Service
	
	public Object intercept(Invocation ivk) throws Throwable {
		//子系统拦截开始
		if(ivk.getTarget() instanceof Executor){
			ActionContext actionContext=ActionContext.getContext();
			Map<String, Object> session=null;
			if(actionContext!=null){
				session=ActionContext.getContext().getSession();
			}
			Subsystem subsystem=null;
			if(session!=null){
				subsystem=(Subsystem)session.get("subsystem");
			}
			if(subsystem!=null){
				Value2Entity[] entities=InterceptSrcForSubsystem.getSrcList();
				if(entities!=null){
					MappedStatement mappedStatement = (MappedStatement)ivk.getArgs()[0];
					String sqlId = mappedStatement.getId();
					String className = sqlId.substring(0, sqlId.lastIndexOf("."));
					String methodName = sqlId.substring(sqlId.lastIndexOf(".") + 1);
					String typeMethodName = ivk.getMethod().getName();  
					if(typeMethodName.equals("update")||typeMethodName.equals("query")){
						for (Value2Entity value2Entity : entities) {
							if(!StringUtils.equals(className, value2Entity.getValue1())){
								continue;
							}
							if(StringUtils.isEmpty(value2Entity.getValue2())){
								continue;
							}
							String[] methods=value2Entity.getValue2().split(",");
							boolean isExists=false;
							for (String method : methods) {
								if(method.equals(methodName)){
									Object parameter = ivk.getArgs()[1];
									if(parameter==null){
										continue;
									}else if(parameter instanceof Map){
										((Map)parameter).put("sysCode", subsystem.getSysCode());
									}else{
										ReflectHelper.setValueByFieldName(parameter, "sysCode",subsystem.getSysCode());
									}
									isExists=true;
									break;
								}
							}
							if(isExists){
								break;
							}
						}
					}
				}
			}
		}
		//子系统拦截结束
		
		if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			
			String str = mappedStatement.getId();
			String className = str.substring(0, str.lastIndexOf("."));
			String methodName = str.substring(str.lastIndexOf(".") + 1);
			BoundSql boundSql = delegate.getBoundSql();
			
			//子系统拦截开始
			ActionContext actionContext=ActionContext.getContext();
			Map<String, Object> session=null;
			if(actionContext!=null){
				session=ActionContext.getContext().getSession();
			}
			Subsystem subsystem=null;
			if(session!=null){
				subsystem=(Subsystem)session.get("subsystem");
			}
			if(subsystem!=null){
				Value2Entity[] entities=InterceptSrcForSubsystem.getSrcList2();
				if(entities!=null){
					for (Value2Entity value2Entity : entities) {
						if(value2Entity.getValue1().equals(str)){
							String nSql=new StringBuilder(boundSql.getSql()).append(value2Entity.getValue2()).append(" SYSCODE='").append(subsystem.getSysCode()).append("'").toString();
							ReflectHelper.setValueByFieldName(boundSql, "sql", nSql);
							break;
						}
					}
				}
			}
			//子系统拦截结束
			
			if(str.matches(scopeId)){
				String sql = boundSql.getSql();
				if (sql.indexOf("{") > 0 && sql.indexOf("}") > 0 && sql.indexOf("}") > sql.indexOf("{")) {
					StringBuilder builder = new StringBuilder();
					builder.append(sql.substring(0,sql.indexOf("{")));
					String rsSql = sql.substring(sql.indexOf("{") + 1, sql.indexOf("}"));
					String querySql = matchSql(rsSql);
					builder.append(querySql);
					builder.append(sql.substring(sql.indexOf("}") + 1 ,sql.length()));
					ReflectHelper.setValueByFieldName(boundSql, "sql", builder.toString());
				}
			 }
			
			String sql = boundSql.getSql();
			boundSql.getParameterObject();
			String strategyCondition = "";
			String newSql ="";
			if(className.indexOf("com.zfsoft.dao")<0&&
					className.indexOf("com.zfsoft.dataprivilege.dao")<0){
				if(className.indexOf("com.zfsoft.hrm.appraisal.") != -1 && DataFilterUtil.isMathMethod(className,methodName) ){
					strategyCondition = DataFilterUtil.getConditionForApr("bmgl","A","DEPARTMENT"); 
					int index = -1;
					if("findCollectResult".equals(methodName)){
						index = sql.indexOf("A.XZN".toUpperCase());
						String str1 = sql.substring(0, index);
						String str2 = sql.substring(index);
						sql =str1 + " (" + strategyCondition+ ") AND " + str2;
					}else{
						if("getPagedListByAuditing2".equals(methodName) || "getPagedListByMoritor".equals(methodName)){
							index = sql.indexOf("ORDER BY A.CARD_NO".toUpperCase());
						}else{
							index = sql.indexOf("group by".toUpperCase());
						}
						if(index == -1){
							
							index = sql.indexOf("order by".toUpperCase());
						}
						if(index == -1){
							sql += " AND (" + strategyCondition + ")";
						}else{
							String str1 = sql.substring(0, index);
							String str2 = sql.substring(index);
							if("findMonitorResult".equals(methodName)){
								index = str2.indexOf("WHERE".toUpperCase()) + "WHERE".length();
								String str3 = str2.substring(0, index);
								String str4 = str2.substring(index);
								str2 =str3 + " (" + strategyCondition+ ") AND " + str4;
							}
							sql =str1 + " AND (" + strategyCondition+ ") " + str2;
						}
					} 
					newSql = sql;
					
				}else{
					strategyCondition=DataFilterUtil.getCondition(className, methodName);
					if(!StringUtil.isEmpty(strategyCondition)){
						newSql= makeUpSql(sql, strategyCondition);
					}
				}
				
			}
			
			if("".equals(newSql)){
				newSql = sql;
			}
			ReflectHelper.setValueByFieldName(boundSql, "sql", newSql);
			if(mappedStatement.getId().matches(pageSqlId)){
				makeUpPagedSql(ivk, newSql);
			}
		}
		return ivk.proceed();
	}
	
	/**
	 * 分页处理方法
	 * @param ivk
	 * @param sql
	 * @throws Throwable
	 */
	private void makeUpPagedSql(Invocation ivk, String sql) throws Throwable{
		RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
		BoundSql boundSql = delegate.getBoundSql();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement countStmt = null;
		Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
		if(parameterObject==null){
			log.error("parameterObject尚未实例化！");
		}else{
			try{
				connection = (Connection) ivk.getArgs()[0];
				sql = boundSql.getSql();
				String countSql = "select count(0) as tmp_count from (" + sql+ ")  "; //记录统计
				countStmt = connection.prepareStatement(countSql);
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
				setParameters(countStmt,mappedStatement,countBS,parameterObject);
				rs = countStmt.executeQuery();
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				QueryModel pageModel = null;
				if(parameterObject instanceof QueryModel){	//参数就是Page实体
					pageModel = (QueryModel) parameterObject;
					pageModel.setEntityOrField(true);	 //
					pageModel.setTotalResult(count);
				}else{	//参数为某个实体，该实体拥有Page属性
					Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"queryModel");
					if(pageField!=null){
						pageModel = (QueryModel) ReflectHelper.getValueByFieldName(parameterObject,"queryModel");
						if(pageModel==null)
							pageModel = new QueryModel();
						pageModel.setEntityOrField(false); //见com.flf.entity.Page.entityOrField 注释
						pageModel.setTotalResult(count);
						
						Field field = ReflectHelper.getFieldByFieldName(parameterObject,"queryModel");
						field.set(parameterObject, pageModel);
					}else{
						log.error(parameterObject.getClass().getName()+"不存在 queryModel 属性！");
					}
				}
				String pageSql = generatePageSql(sql,pageModel);
				ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将分页squeal语句反射回BoundSql.

			} catch (Exception e) {
				log.error("程序出错！");
			} finally {
				rs.close();
				countStmt.close();
			}
		}
	}

	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql,QueryModel page){
		if(page!=null && !StringUtil.isEmpty(dialect)){
			StringBuffer pageSql = new StringBuffer();
			if("oracle".equals(dialect)){
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				
				if (!StringUtil.isEmpty(page.getSortName())){
					pageSql.append(" order by ");
					pageSql.append(page.getSortName());
					pageSql.append(" ");
					pageSql.append(StringUtil.isEmpty(page.getSortOrder()) ? "asc" : page.getSortOrder());
				}
				
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(page.getCurrentResult()+page.getShowCount());
				pageSql.append(") where row_id>");
				pageSql.append(page.getCurrentResult());
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	/**
	 * 分解SQL语句
	 * @param sql
	 * @return
	 */
	public Map<String,String> resolve(String sql) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		try {
			int begin = sql.indexOf("(");
			int end = sql.indexOf(")");
			String subSql = sql.substring(begin + 1, end);
			String str[] = StringUtil.split(subSql, ";");
			if(str!=null && str.length!=4){
				log.error("输入参数不符合规则！");
			}
			map.put("tableName", str[0].replace("\"", ""));
			map.put("matchField", str[1].replace("\"", ""));
			map.put("tableField", str[2].replace("\"", ""));
			map.put("filterField", str[3].replace("\"", ""));
			map.put("requireField", sql.substring(0, sql.indexOf("func")));
		} catch (Exception e) {
			log.error("程序出错！");
		}
		return map;
	}
	
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtil.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtil.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		scopeId = p.getProperty("scopeId");
		if (StringUtil.isEmpty(scopeId)) {
			try {
				throw new PropertyException("scopeId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String makeUpSql(String sql, String strategyCondition){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append(sql);
		sb.append(") t where ");
		sb.append(strategyCondition);
		return sb.toString();
	}
	
	/**
	 * 验证数据权限
	 * @param sql
	 * @param page
	 * @return
	 */
	public String matchSql(String sql) throws Exception{
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap = this.resolve(sql);
		String tableName  = hashMap.get("tableName");   //表名或视图名
		String matchField = hashMap.get("matchField");  //匹配字段
		String tableField = hashMap.get("tableField");  //表字段
		String filterField= hashMap.get("filterField");  //数据范围需过滤的条件字段
		String requireField = hashMap.get("requireField");//前置条件字段
		String tableFields[] = StringUtil.split(tableField, ",");
		String filterFields[] = StringUtil.split(filterField, ",");
		StringBuilder builder = null;
		StringBuilder sqlBuilder = null;
		if(tableFields.length==1 || filterFields.length==1 || tableFields.length != filterFields.length){
			log.error("表字段与数据范围过滤条件字段不一致！");
			return reqParm;
		}
        HttpSession session	= SessionFactory.getSession();
        User u =  (User) session.getAttribute("user"); 
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yh_id", u.getYhm());
        map.put("list", u.getJsdms());
        try {
        	sjfwzService = (ISjfwzService) ServiceFactory.getService("sjfwzService");//获取范围组Service
        	List<SjfwzModel> fwz = sjfwzService.cxSjfwzYhjs(map);
			if (fwz != null && fwz.size() > 0) {
				builder = new StringBuilder();
				String f1 = "", f2 = "" ,f3 = "", f4="";
				for (int i = 0; i < filterFields.length; i++) {
					//双重循环判断是否存在二维的数据范围定义
					for (int j = i + 1; j < filterFields.length; j++) {
						f1 = filterFields[i];f2 = filterFields[j];
						f3 = tableFields[i]; f4 = tableFields[j];
						for (SjfwzModel model : fwz) {
							String temp = model.getSjfwztj();
							if (temp.contains(",")) {
								temp = temp.replace(",", " and ");
								if (temp.contains(f1) && temp.contains(f2)) {
									String filed1 = StringUtil.split(f1,"=")[0];
									String filed2 = StringUtil.split(f2, "=")[0];
									temp = temp.replaceAll(filed1,f3);
									temp = temp.replaceAll(filed2, f4);
									builder.append("(" + temp + ")" + " or ");
								}

							}
						}

					}
					//循环获得一维数据范围定义
					boolean flag = false;
					for (SjfwzModel model : fwz) {
						String temp = model.getSjfwztj();
						//如果部门代码为-1则为超级管理员权限,为-2则为全校权限,为-3则为全学院权限,直接返回条件成功。
						if("bmdm_id=-1".equals(temp) ||"bmdm_id=-2".equals(temp) || "bmdm_id=-3".equals(temp)){
							flag = true;
							break;
						}
						if (!temp.contains(",")) {
							if (temp.contains(filterFields[i])) {
								String field = StringUtil.split(temp,"=")[0];
								builder.append(temp.replaceAll(field, tableFields[i]) + " or ");
							}
						}
					}
					if(flag){
						return pasParm;
					}
				}

        	}else{
        		log.error("未定义数据范围组！");
        		return reqParm;
        	}
        	
        	sqlBuilder = new StringBuilder(); //返回SQL 
        	if(builder!=null && builder.length()>0){
        		String reSql = builder.toString();
        		reSql = reSql.substring(0,reSql.lastIndexOf(" or "));
        		sqlBuilder.append(requireField + " ( select " + matchField + " from " + tableName + " where " + reSql + ")");
        		log.debug(sqlBuilder.toString());
        		return sqlBuilder.toString();
        	}
        	
		} catch (Exception e) {
			log.error("程序出错！");
			return reqParm;
		}
		return reqParm;
	}

}
