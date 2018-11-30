package test.zfsoft.hrm.baseinfo.code.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.query.ItemQuery;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemService;

/** 
 * @ClassName: CatalogServiceTestCase 
 * @author jinjj
 * @date 2012-5-21 下午01:32:43 
 *  
 */
public class ItemServiceTestCase extends TestCase {

	private IItemService itemService;
	private Item model = new Item();
	private ItemQuery query = new ItemQuery();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		itemService = (IItemService)ServiceFactory.getService("baseCodeItemService");
		initEntity();
	}
	
	private void initEntity(){
		model.setGuid("test-item-0001");
		model.setCatalogId("test-catalog-0001");
		model.setChecked(0);
		model.setComment("测试");
		model.setDescription("测试");
		model.setDumped(0);
		model.setHasParentNodeInfo(0);
		model.setOrder("1");
		model.setParentId("");
		model.setTip("测试");
		model.setVisible(0);
		
		query.setCatalogId("test-catalog-0003");
	}
	
	public void testInsert()throws Exception{
		itemService.insert(model);
	}
	
	public void testQuery()throws Exception{
		model = itemService.getEntity(model);
		assertEquals("测试", model.getDescription());
	}
	
	public void testUpdate()throws Exception{
		model.setComment(""+new Date());
		itemService.update(model);
	}
	
	@SuppressWarnings("unchecked")
	public void testDelete()throws Exception{
		List list = new ArrayList();
		list.add(model);
		itemService.delete(list);
		Item obj = itemService.getEntity(model);
		assertNull(obj);
	}
	
	public void testTreeList()throws Exception{
		
		List<Item> list = itemService.getTreeList(query);
		System.out.println(list.size());
	}
	
//	public void testLoadCode() throws Exception{
//		Map<String,LinkedHashMap<String,Item>> map = itemService.loadCode();
//		Item item = map.get("test-catalog-0001").get("test-item-0001");
//		assertEquals("测试", item.getComment());
//	}
}
