package com.zfsoft.fifa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.fifa.business.Business;
import com.zfsoft.fifa.identity.Identity;
import com.zfsoft.fifa.identity.PageType;
import com.zfsoft.fifa.statement.Statement;

/**
 * BeanMetadata工具类，对BeanMetadata进行管理，并提供访问接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class BeanMetadataManager implements Serializable {
	
	private static final long serialVersionUID = -1056437419897215051L;

	private static Log LOG = LogFactory.getLog( BeanMetadataManager.class );
	
	private static boolean started = false;
	
	private static Map<String, Statement> _statement = new HashMap<String, Statement>();
	
	private static Map<String, Business> _business = new HashMap<String, Business>();
	
	/**
	 * （私有化）构造函数
	 */
	private BeanMetadataManager() {
		org.quartz.impl.jdbcjobstore.JobStoreSupport df = null;
		//do nothing
	}
	
	/**
	 * 创建BeanMetadata工具类
	 * @return
	 * @throws Throwable
	 */
	public static BeanMetadataManager create() throws Throwable {
		BeanMetadataManager manager = new BeanMetadataManager();
		
		manager.init();
		
		return manager;
	}
	
	/**
	 * 加载所有元数据
	 */
	public void init() throws Throwable {
		List<Statement> statements = getStatements();
		fillMaps(statements);
		started = true;
	}
	
	/**
	 * 清空所有缓存内容，变为未加载状态
	 */
	public void destory() {
		_statement.clear();
		_business.clear();
		started = false;
	}
	
	/**
	 * 返回当前管理器状态
	 * @return true为启动；false为未启动
	 */
	public boolean isStated() {
		return started;
	}
	
	/**
	 * 
	 */
	private final static String[][] dynaMeta = {
		{ PageType.OVERALL	, "com.lansle.hrm.entities.Overall"},
		{ PageType.LOG		, "LOG"},
		{ PageType.SNAP		, "SNAP"},
		{ PageType.AUDIT	, "AUDIT"}
	};
	
	/**
	 * nessary[0] PageType.OVERALL
	 * nessary[1] PageType.LOG
	 * nessary[2] PageType.SNAP
	 * nessary[3] PageType.AUDIT
	 */
	private static Statement[] nessary = new Statement[4];
	
	/**
	 * 根据元数据数组填充两HashMap
	 * <p>
	 * 注：因需要动态加载OVERALL/LOG/SNAP/AUTHORITY的信息,所以此四类信息必须存在于meta中。<br>
	 * 若不存在则抛出NoSuchMetadataExcpetion
	 * </p>
	 * @param statements 元数据列表
	 */
	private void fillMaps(List<Statement> statements) {
		_business.clear();
		_statement.clear();
		
		// 获取必须的四项内容：OVERALL/LOG/SNAP/AUTHORITY
		for( int i=0; i<nessary.length; i++ ) {
			nessary[i] = null;
			String name = dynaMeta[i][1];
			
			for (Statement statement : statements) {
				if( statement != null && name.equals( statement.getBeanName() ) ) {
					nessary[i] = statement;
					statement = null;
					break;
				}
			}
			
			if( nessary[i] == null ) {
				LOG.error( "NoSuchMetadataException" + dynaMeta[i][0] );
				throw new NoSuchMetadataException( dynaMeta[i][0] );
			}
			
			_statement.put( dynaMeta[i][1], nessary[i] );
		}
		
		for (Statement statement : statements) {
			register( statement );
		}
	}
	
	/**
	 * 注册Statement
	 * <p>
	 * 系统初始化将自动注册所有Statement.
	 * 若运行中Statement发生更改,需要使用此方法重新注册.
	 * </p>
	 * @param statement
	 */
	public void register(Statement statement) {
		
		if( statement == null ) {
			return;
		}
		
		//无论通过主表对应的beanName还是日志表对应的beanName都可以找到对应的Business，
		Business business = statement.createBusinessForce();
		_business.put(business.getBeanName(), business );
		_business.put(business.getLogBeanName(), business);
		
		_statement.put(statement.getBeanName(), statement);
		_statement.put(statement.getBeanName() + ";" + PageType.MAIN, statement);
		
		initRelation(statement);
	}
	
	/**
	 * 运行时注销的Statement
	 * <p>
	 * 将无法在getStatements()中使用Identity查找已注销的Statement
	 * </p>
	 * @param statement
	 */
	public void deregister(Statement statement) {
		Business business = statement.createBusinessForce();
		
		_business.remove( business.getBeanName() );
		_business.remove( business.getLogBeanName() );
		
		_statement.remove( statement.getBeanName() );
		_statement.remove( statement.getBeanName() + ";" + PageType.MAIN );
		_statement.remove( statement.getBeanName() + ";" + PageType.OVERALL );
		_statement.remove( statement.getBeanName() + ";" + PageType.LOG );
		_statement.remove( statement.getBeanName() + ";" + PageType.SNAP );
		_statement.remove( statement.getBeanName() + ";" + PageType.AUDIT );
	}
	
	/**
	 * 返回identity对应的Business
	 * <p>
	 * 无论通过主表对应的beanName还是日志表对应的beanName都可以找到同一个的Business.
	 * com.lansle.hrm.entities.Abroad与com.lansle.hrm.entities.AbroadLog均可以查到对应的Business
	 * </p>
	 * @param identity 页面标识
	 * @return (never null)
	 */
	public Business getBusiness(Identity identity) {
		Business business = _business.get( identity.getBeanName() );
		
		if( business == null ) {
			LOG.error( "NoSuchMetadataException" + identity.getBeanName() );
			throw new NoSuchMetadataException( identity.getBeanName() );
		}
		
		return business;
	}
	
	/**
	 * 返回identity对应的Statement
	 * @param identity 页面标识
	 * @return (never null)
	 */
	public Statement getStatement( Identity identity ) {
		Statement statement = _statement.get( identity.getBeanName() );
		
		if( statement == null ) {
			LOG.error( "NoSuchMetadataException" + identity.getBeanName() );
			throw new NoSuchMetadataException( identity.getBeanName() );
		}
		
		return statement;
	}
	
	/**
	 * 处理主库查询、Log查询、Snap查询、审核
	 * @param statement
	 */
	private void initRelation( Statement statement ) {
		String beanName = statement.getBeanName();
		
		Statement mainsearh = nessary[0];
		Statement logsearch = nessary[1];
		Statement snapsearch = nessary[2];
		Statement authority = nessary[3];
		
		String key = null;
		Statement newStatement = null;
		
		// OVERALL
		key = beanName + ";" + PageType.OVERALL;
		newStatement = mainsearh.combineStatement(statement);
		_statement.put(key, newStatement);
		
		// LOG
		key = beanName + ";" + PageType.LOG;
		newStatement = mainsearh.combineStatement(statement);
		newStatement = newStatement.combineStatement(logsearch);
		_statement.put(key, newStatement);
		
		// SNAP
		key = beanName + ";" + PageType.SNAP;
		newStatement = snapsearch.combineStatement(mainsearh);
		newStatement = newStatement.combineStatement(statement);
		_statement.put(key, newStatement);
		
		// AUTHORITY
		key = beanName + ";" + PageType.AUDIT;
		newStatement = statement.combineStatement( authority );
		_statement.put( key, newStatement );
	}
	
	/**
	 * 获取所有定义的元数据
	 * @return
	 * @throws Throwable 如果操作出现异常
	 */
	private List<Statement> getStatements() throws Throwable {
		//TODO （未实现）获取所有定义的元数据
		List<Statement> statements = new ArrayList<Statement>(0);
		
//		MetadataFile file = new BeanMetadataFile();
		
//		statements = (List<Statement>) file.read();
		
		return statements;
	}
	
}
