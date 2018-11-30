package test.zfsoft.hrm.baseinfo.table.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.entities.Table;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;

/** 
 * @ClassName: OracleTableServiceTestCase 
 * @author jinjj
 * @date 2012-6-7 下午02:54:28 
 *  
 */
public class OracleTableServiceTestCase extends TestCase {

	private ITableService tableService;
	private Table table;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tableService = (ITableService)ServiceFactory.getService("oracleTableService");
		initEntity();
	}
	
	private void initEntity(){
		table = new Table();
		table.setTableName("example2");
		table.setComment("example2-comment");
		List<Column> columns = new ArrayList<Column>();
		for(int i=0;i<2;i++){
			Column column = new Column();
			column.setColumnName("id"+i);
			column.setComment("全局ID");
			column.setNewName("id"+i+i);
			column.setTableName("example2");
			column.setType("varchar2(10)");
			column.setDefaultV("a"+i);
			column.setNullable(false);
			columns.add(column);
		}
		table.setColumns(columns);
	}

	public void testCreate(){
		tableService.create(table);
	}
	
	public void testDrop(){
		tableService.drop(table);
	}
}
