package test.zfsoft.hrm.baseinfo.table.service;

import junit.framework.TestCase;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.IColumnService;

/** 
 * @ClassName: OracleColumnServiceTestCase 
 * @author jinjj
 * @date 2012-6-7 下午05:21:30 
 *  
 */
public class OracleColumnServiceTestCase extends TestCase {

	private IColumnService columnService;
	private Column column;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		columnService = (IColumnService)ServiceFactory.getService("oracleColumnService");
		initEntity();
	}

	private void initEntity(){
		column = new Column();
		column.setColumnName("id3");
		column.setComment("全局ID");
		column.setNewName("id33");
		column.setTableName("example2");
		column.setType("varchar2(10)");
		column.setDefaultV("a"+3);
		column.setNullable(false);
	}
	
	public void testAdd(){
		columnService.add(column);
	}
	
	public void testModify(){
		column.setDefaultV("bb");
		columnService.modify(column);
	}
	
	public void testComment(){
		column.setComment("comment");
		columnService.comment(column);
	}
	
	public void testRename(){
		//columnService.rename(column);
	}
	
	public void testDrop(){
		columnService.drop(column);
	}
}
