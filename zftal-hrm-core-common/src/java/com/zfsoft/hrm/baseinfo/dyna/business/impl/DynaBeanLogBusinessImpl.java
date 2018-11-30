package com.zfsoft.hrm.baseinfo.dyna.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanLogBusiness;
import com.zfsoft.hrm.baseinfo.dyna.dao.daointerface.IDynaBeanDao;
import com.zfsoft.hrm.baseinfo.dyna.dao.daointerface.IDynaBeanLogDao;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanValidateUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.IOperationConfig;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @author jinjj
 * @date 2012-10-8 下午06:46:51 
 *  
 */
public class DynaBeanLogBusinessImpl implements IDynaBeanLogBusiness {

	private IDynaBeanLogDao dynaBeanLogDao;
	private IDynaBeanDao dynaBeanDao;
	
	@Override
	public DynaBean findById(DynaBean bean) {
		Map<String, Object> obj = dynaBeanLogDao.getById( bean );
		DynaBean newBean = new DynaBean(bean.getClazz());
		newBean.setValues( obj );
		//数据修正，避免服务中连续调用findById后，modify及delete发生mybatis字段空错误
		for (InfoProperty property : newBean.getClazz().getEditables()) {
			Object value = newBean.getValue(property.getFieldName());
			if(value == null){
				newBean.setValue(property.getFieldName(), "");
			}
		}
		return newBean;
	}
	
	private DynaBean findOldBeanById(DynaBean bean){
		Map<String, Object> obj = dynaBeanDao.findById( bean );
		DynaBean newBean = new DynaBean(bean.getClazz());
		if(obj==null){
			return null;
		}
		newBean.setValues( obj );
		//数据修正，避免服务中连续调用findById后，modify及delete发生mybatis字段空错误
		for (InfoProperty property : newBean.getClazz().getEditables()) {
			Object value = newBean.getValue(property.getFieldName());
			if(value == null){
				newBean.setValue(property.getFieldName(), "");
			}
		}
		return newBean; 
	}

	@Override
	public void submitRecord(DynaBean bean) {
		String operate = (String)bean.getValue("operation_");
		boolean validate = false;
		if(StringUtils.isEmpty(operate)){
			throw new RuleException("数据操作类型缺失");
		}
		if(IOperationConfig.ADD.equals(operate)){
			validate = true;
			dynaBeanDao.insert(bean);
		}
		if(IOperationConfig.MODIFY.equals(operate)){
			validate = true;
			dynaBeanDao.update(bean);
		}
		if(IOperationConfig.REMOVE.equals(operate)){
			validate = true;
			dynaBeanDao.delete(bean);
		}
		if(!validate){
			throw new RuleException("非法的数据操作类型");
		}
	}

	@Override
	public void doLog(DynaBean bean, String operate) {
		boolean validate = false;
		DynaBean old = null;
		if(IOperationConfig.ADD.equals(operate)){
			validate = true;
			if(!createBusiness(bean).isAdd()){
				throw new RuleException("该信息类最多保存一条记录，无法增加！");
			}
			old = bean;
		}
		if(IOperationConfig.MODIFY.equals(operate)){
			validate = true;
			if(!createBusiness(bean).isModify()){
				throw new RuleException("该记录不允许修改！");
			}
			//原数据的基础上覆盖新修改的数据，产生完整的数据日志
			old = findOldBeanById(bean);
			if(old==null){
				return;
			}
			for (InfoProperty property : bean.getClazz().getEditables()) {
				Object value = bean.getValue(property.getFieldName());
				if(value != null){
//					old.setValue(property.getFieldName(), value);不需要类型转换，故直接调用对象
					old.getValues().put(property.getFieldName(), value);
				}
			}
		}
		if(IOperationConfig.REMOVE.equals(operate)){
			validate = true;
			if(!createBusiness(bean).isRemove()){
				throw new RuleException("该信息类最少保流一条记录，无法删除！");
			}
			old = findOldBeanById(bean);
		}
		if(!validate){
			throw new RuleException("非法的数据操作类型");
		}
		
//		DynaBeanValidateUtil.validate(old);
		old.setValue( "operator_", SessionFactory.getUser().getYhm() );
		old.setValue( "operation_", operate );
		dynaBeanLogDao.insert(old);
		bean.setValue("logid", old.getValue("logid"));
//		bean.setValue( "operator_", old.getValue("operator_") );
//		bean.setValue( "operation_", old.getValue("operation_") );
	}
	
	private DynaBeanBusiness createBusiness(DynaBean bean) {
		String staffid = (String)bean.getValue("gh");
		if( staffid == null || "".equals(staffid) ) {
			throw new RuleException("职工号不得为空！");
		}
		
		DynaBeanBusiness business = new DynaBeanBusiness( bean.getClazz(), existPerson( bean.getClazz(), staffid ) );
		return business;
	}
	
	/**
	 * 判断教职工是否已存在于指定的信息类
	 * @param clazz
	 * @param staffid
	 * @return
	 */
	private boolean existPerson( InfoClass clazz, String staffid ) {
		DynaBeanQuery query = new DynaBeanQuery( clazz );
		
		query.setDeleted( null );
		query.setExpress("gh == #{params.staffid}");
		query.setParam( "staffid", staffid );
		query.setDeleted(false);
		
		return dynaBeanDao.findCount(query) > 0 ? true : false;
	}
	
	public PageList<DynaBean> getPagingList(DynaBeanQuery query){
		PageList<DynaBean> pageList = new PageList<DynaBean>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems( dynaBeanLogDao.getPagingCount(query) );
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				//获得DynaBean集合
				List<DynaBean> beans = new ArrayList<DynaBean>();
				List<Map<String, Object>> list = dynaBeanLogDao.getPagingList( query );
				for (Map<String, Object> map : list) {
					DynaBean bean = new DynaBean( query.getClazz() );
					bean.setValues( map );
					beans.add(bean);
				}
				pageList.addAll(beans);
			}
		}
		return pageList;
	}
	
	public void removeById(DynaBean logBean){
		dynaBeanLogDao.removeById(logBean);
	}

	public void setDynaBeanLogDao(IDynaBeanLogDao dynaBeanLogDao) {
		this.dynaBeanLogDao = dynaBeanLogDao;
	}

	public void setDynaBeanDao(IDynaBeanDao dynaBeanDao) {
		this.dynaBeanDao = dynaBeanDao;
	}

}
