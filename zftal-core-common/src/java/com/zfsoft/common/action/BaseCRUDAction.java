package com.zfsoft.common.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.query.ModelBase;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.common.service.BaseService;
import com.zfsoft.util.reflect.GenericsUtils;


/**
 * CRUD基类
 * 
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseCRUDAction<T> extends BaseAction implements
		ModelDriven<T> {

	private static final long serialVersionUID = -5366743510068033498L;
	protected static final String LIST_PAGE = "list"; // 列表页面
	protected static final String EDIT_PAGE = "edit"; // 编辑页面
	protected static final String RELOAD = "reload"; // 查看页面
	protected static final String DATA_PAGE = "data";// jqgrid列表页面
	
	protected static Log logger = LogFactory.getLog(BaseCRUDAction.class);

	/** * 持久类. */
	protected T entity;

	/** * 持久类类型. */
	protected Class entityClass;
	
	/** * 业务组件Id*/
	protected String id;

    /** * 业务名称*/
	protected String ywmc;
	
    /** * 业务表名称*/
	protected String tableName;
	
	/** * 操作模块*/
	protected String czmk;

	/** * 操作描述*/
	protected String czms;
	
	protected ValueStack valueStack = getValueStack();

	/** 抽象方法,子类中实现,得到实体的Service * */
	protected abstract BaseService getEntityService();

	/** 抽象方法,子类中实现,得到实体的Service * */
	protected abstract BaseLog getEntityLog();

	public BaseCRUDAction(String ywmc,String tableName,String czmk,String czms){
		this.entityClass = GenericsUtils.getSuperClassGenricType(this
				.getClass());		
		this.setYwmc(ywmc);
		this.setTableName(tableName);
		this.setCzmk(czmk);
		this.setCzms(czms);
	} 
	
	public T getModel() {
		try {
			if (entity == null) {
				entity = (T) entityClass.newInstance();
			}
		} catch (Exception ex) {
			logger.error(ex, ex);
		}
		return entity;
	}
	/**
	 * 添加
	 */
	public String add() {
		try {
			boolean result = getEntityService().insert(entity);
			String key = result ? "I99001" : "I99002";
			valueStack.set("result", getText(key));
			if (result) {
				// 记操作日志
				getEntityLog().insert(
						this.getUser(),
						getText("log.message.ywmc", new String[] {
								this.getYwmc(), this.getTableName() }),
						this.getCzmk(), "新增" + this.getCzms());
			}

		} catch (Exception e) {
			logException(e);
			return ERROR;

		}
		return RELOAD;
	}

	/**
	 * 修改
	 */
	public String edit() {
		try {
			boolean result = getEntityService().update(entity);
			String key = result ? "I99001" : "I99002";
			valueStack.set("result", getText(key));
			if (result) {
				// 记操作日志
				getEntityLog().insert(
						this.getUser(),
						getText("log.message.ywmc", new String[] { this.getYwmc(),
								this.getTableName() }), this.getCzmk(),
						"修改"+ this.getCzms());
			}

		} catch (Exception e) {
			logException(e);
			return ERROR;

		}
		return RELOAD;
	}

	/**
	 * 删除
	 * 
	 */
	public String delete() {
		try {
			String ids = getRequest().getParameter("ids");
			if (null != ids && !ids.equals("")) {
				String[] pks = ids.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < pks.length; i++) {

					idList.add(pks[i]);

				}
				boolean result = getEntityService().batchDelete(idList);
				String key = result ? "I99001" : "I99002";
				getValueStack().set("result", getText(key));
				if (result) {
					// 记操作日志
					getEntityLog().insert(
							this.getUser(),
							getText("log.message.ywmc", new String[] {
									this.getYwmc(), this.getTableName() }),
									this.getCzmk(), "删除"+ this.getCzms());
				}
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return RELOAD;
	}
	
	/**
	 * 查询单个实体
	 * 
	 */
	public String queryById() {
		try {
			T t = (T) getEntityService().getModel(id);
			BeanUtils.copyProperties(entity, t);
			valueStack.set("model", entity);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return EDIT_PAGE;
	}
	
	/**
	 * 查询单个实体
	 * @return
	 */
	public String queryByModel(){
		try {
			T t = (T) getEntityService().getModel(entity);
			BeanUtils.copyProperties(entity, t);
			valueStack.set("model", entity);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return EDIT_PAGE;
	}
		
	/**
	 *  分页查询
	 * @throws
	 */
	public String queryList() {
		try {
			QueryModel queryModel = ((ModelBase) entity).getQueryModel();
			queryModel.setItems(getEntityService().getPagedList(entity));
			getValueStack().set(DATA, queryModel);

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "list";
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getCzmk() {
		return czmk;
	}

	public void setCzmk(String czmk) {
		this.czmk = czmk;
	}

	public String getCzms() {
		return czms;
	}

	public void setCzms(String czms) {
		this.czms = czms;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	} 
	
}
