package test.zfsoft.hrm.baseinfo.code.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemService;

/** 
 * @ClassName: CatalogServiceTestCase 
 * @author jinjj
 * @date 2012-5-21 下午01:32:43 
 *  
 */
public class CatalogServiceTestCase extends TestCase {

	private ICatalogService catalogService;
	private IItemService itemService;
	private Catalog model = new Catalog();
	private Item item = new Item();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		catalogService = (ICatalogService)ServiceFactory.getService("baseCodeCatalogService");
		itemService = (IItemService)ServiceFactory.getService("baseCodeItemService");
		initEntity();
	}
	
	private void initEntity(){
		model.setGuid("test-catalog-0001");
		model.setName("测试编目");
		model.setIncludeParentNode(1);
		model.setDelimiter("1");
		model.setClassCode("1");
		model.setChooseExpr("0");
		model.setRemark("test");
		model.setType("0");
		model.setSource("0");
		
		item.setGuid("test-item-0001");
		item.setCatalogId("test-catalog-0001");
		item.setChecked(0);
		item.setComment("测试");
		item.setDescription("测试");
		item.setDumped(0);
		item.setHasParentNodeInfo(0);
		item.setOrder("1");
		item.setParentId("");
		item.setTip("测试");
		item.setVisible(0);
	}
	
	public void testInsert()throws Exception{
//		Catalog model = new Catalog();
		catalogService.insert(model);
	}
	
	public void testQuery()throws Exception{
		model = catalogService.getEntity(model);
		assertEquals("测试编目", model.getName());
	}
	
	public void testUpdate()throws Exception{
		model.setRemark("修改备注"+new Date());
		catalogService.update(model);
	}
	
	public void testLoad()throws Exception{
		long start = System.currentTimeMillis();
		List<Catalog> list = new ArrayList<Catalog>();
		for(int i=0;i<10000;i++){
			Catalog model = new Catalog();
			model.setGuid(i+"");
			list.add(model);
		}
		long end = System.currentTimeMillis();
		System.out.println("初始化"+(end-start)+"ms");
		Map<String,Catalog> map = new HashMap<String,Catalog>(list.size()+1);
		for (Catalog catalogModel : list) {
			map.put(catalogModel.getGuid(), catalogModel);
		}
		System.out.println(map.size());
		end = System.currentTimeMillis();
		System.out.println("共消耗"+(end-start)+"ms");
	}
	
	public void testDelete()throws Exception{
		catalogService.delete(model);
		Catalog obj = catalogService.getEntity(model);
		assertNull(obj);
	}
	
	public void testConstructData() throws Exception{
		int total=0;
		for(int i = 1;i<100;i++){
			model.setGuid(generateUid("catalog",i));
			catalogService.insert(model);
			
			int loop = new Random().nextInt(1000);
			total = total+loop;
			item.setCatalogId(model.getGuid());
			for(int j=1;j<loop;j++){
				item.setGuid(generateUid("item", j));
				itemService.insert(item);
			}
		}
		System.out.println("产生条目:"+total);
	}
	
	private String generateUid(String type,int num){
		StringBuffer sb = new StringBuffer();
		sb.append("test-");
		sb.append(type+"-");
		sb.append(String.format("%04d", num));
		return sb.toString();
	}
}
