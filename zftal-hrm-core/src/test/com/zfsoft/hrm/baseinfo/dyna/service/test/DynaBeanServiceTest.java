package com.zfsoft.hrm.baseinfo.dyna.service.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.exception.DynaBeanException;
import com.zfsoft.hrm.baseinfo.dyna.service.impl.DynaBeanServiceImpl;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.search.exception.SearchException;

/**
 * {@link DynaBeanServiceImpl }的测试类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-20
 * @version V1.0.0
 */
public class DynaBeanServiceTest extends TestCase implements IDynaBeanService {

	private IDynaBeanService service;
	
	@Override
	public void setUp() throws Exception {
		
		service = (IDynaBeanService)ServiceFactory.getService("baseDynaBeanService");
	}
	
	@Test
	public void test() {
		try {
			//启动代码库
			CodeUtil.initialize();
			
			addPerson("2012011");
			
			InfoClass infoClass = InfoClassCache.getInfoClass( "C393FE11C4DC8E46E040007F01003F39" );
			
			DynaBean bean = new DynaBean( infoClass );
			
			bean.setValue( "gh", "2012011" );
			bean.setValue( "xm", "赵轩良" );
			bean.setValue( "ymxm", "ZhaoXuanliang");
			bean.setValue( "csrq", "1988-11-25" );
			bean.setValue( "jg", "330722" );
			bean.setValue( "dwh", "010000" );
			addRecord( bean );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addBean(DynaBean bean) throws DynaBeanException {

		service.addBean( bean );
	}

	@Override
	public void addPerson(String staffid) {

		service.addPerson( staffid );
	}

	@Override
	public boolean addRecord(DynaBean bean) throws DynaBeanException {

		return service.addRecord( bean );
	}

	@Override
	public void deletePerson(String staffid) {

		service.deletePerson( staffid );
	}

	@Override
	public DynaBean findById(DynaBean bean) throws DynaBeanException {

		return service.findById( bean );
	}

	@Override
	public List<DynaBean> findList(DynaBeanQuery query) throws DynaBeanException {

		return service.findList( query );
	}

	@Override
	public void modifyBean(DynaBean bean) throws DynaBeanException {
		
		service.modifyBean( bean ); 
	}

	@Override
	public boolean modifyRecord(DynaBean bean) throws DynaBeanException {
		
		return service.modifyRecord( bean );
	}

	@Override
	public void removeBean(DynaBean bean) throws DynaBeanException {

		service.removeBean( bean );
	}

	@Override
	public boolean removeRecord(DynaBean bean) throws DynaBeanException {
		
		return service.removeRecord( bean );
	}

	@Override
	public PageList<DynaBean> findPagingInfoList(DynaBeanQuery query)
			throws SearchException {
		// do nothing
		return null;
	}

	@Override
	public int findCount(DynaBeanQuery query) throws DynaBeanException {
		// do nothing
		return 0;
	}

	@Override
	public DynaBean findUniqueByParam(String paramName, Object value)
			throws DynaBeanException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addRecordNoCheckGh(DynaBean bean) throws DynaBeanException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyRecordNoChecked(DynaBean bean) throws DynaBeanException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeRecordNoCheckGh(DynaBean bean) throws DynaBeanException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doFpgh(String classId, String globalid, String gh) throws DynaBeanException {
		// TODO Auto-generated method stub
		
	}

}
