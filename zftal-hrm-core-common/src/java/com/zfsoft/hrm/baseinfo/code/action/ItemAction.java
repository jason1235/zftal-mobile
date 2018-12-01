package com.zfsoft.hrm.baseinfo.code.action;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.dataprivilege.util.DataFilterUtil;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.query.ItemQuery;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.IItemService;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.reflect.ConvertUtils;
import com.zfsoft.util.reflect.ReflectHelper;
import com.zfsoft.util.reflect.ReflectionUtils;

/**
 * 
 * @ClassName: ItemAction 
 * @Description: 条目Action
 * @author jinjj
 * @date 2012-5-21 上午08:26:13 
 *
 */
public class ItemAction extends HrmAction implements ModelDriven<Item>{

	private static final long serialVersionUID = 2068740431844188257L;
	
	private Item model = new Item();
	private List<Item> list;
	private Catalog catalog;
	private Map<String,Catalog> map;
	private File file;
	private String msg;
	private String suborgscope;
	private IItemService itemService;
	
	private ItemQuery query = new ItemQuery();
	/**
	 * 
	 * @Title: list 
	 * @Description: 条目列表
	 * @return
	 */
	public String list(){
		catalog = CodeUtil.getCatalog(query.getCatalogId());
		map = CodeUtil.getCatalogMap();
		list = itemService.getTreeList(query);
		return "list";
	}
	
	/**
	 * 
	 * @Title: query 
	 * @Description:查询单个条目 
	 * @return
	 */
	public String query(){
		model = itemService.getEntity(model);
		if(!StringUtils.isEmpty(model.getParentId())){
			Item item = CodeUtil.getItem(model.getCatalogId(), model.getParentId());
			query.setName(item.getDescription());
		}
		catalog = CodeUtil.getCatalog(model.getCatalogId());
		return "edit";
	}
	
	/**
	 * 下拉表单获取子节点数据
	 * @return
	 */
	public String getChildren(){
		list = CodeUtil.getChildren(model.getCatalogId(), model.getParentId());
		HashMap<String,Object> map = new HashMap<String,Object>();
		catalog = CodeUtil.getCatalog(model.getCatalogId());
    	String on_off =SubSystemHolder.getPropertiesValue("org_scope_on_off");
    	
    	if (!"off".equals(this.suborgscope)) {
			if(StringUtil.isNotEmpty(on_off) && "on".equalsIgnoreCase(on_off) && "DM_DEF_ORG".equals(model.getCatalogId())){
				list = getOneSelfDept(list);
			}
    	}
				
		map.put("success", true);
		map.put("result", list);
		map.put("catalog", catalog);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	private List<Item> getOneSelfDept(List<Item> list){
		if(getUser().getJsdms().indexOf("admin") != -1){
			return list;
		}
		
		List<Item> listTemp = new ArrayList<Item>();
		List<String> orgList = new ArrayList<String>();
		List<DeptFilter> deptFilters = (List<DeptFilter>) DataFilterUtil.getCondition("bmgl");
		for(DeptFilter deptFilter : deptFilters){
			if(getUser().getJsdms().indexOf(deptFilter.getRoleId()) != -1){
				if(DealDeptFilter.TYPE_SELF.equals(deptFilter.getDataType())){
					orgList.add(getOwnerOrg(getUser().getYhm()));
					break;
				}
				if(DealDeptFilter.TYPE_ALL.equals(deptFilter.getDataType())){
					return list;
				}
				orgList = deptFilter.getOrgList();
				break;
			}
		}
		
		if(orgList.size() == 0){
			orgList.add(getOwnerOrg(getUser().getYhm()));
		}
		
		if(orgList.contains("all")){
			return list;
		}
		
		for(Item orgInfo : list){
			if(orgList.contains(orgInfo.getGuid())){
				listTemp.add(orgInfo);
			}else{
				if(check(orgInfo.getChildren(),orgList)){
					listTemp.add(orgInfo);
				}
			}
		}
		
		return listTemp;
	}
	
	private boolean check(List<Item> list, List<String> orgList){
		boolean bol = false;
		
		if(list == null){
			return bol;
		}
		for(Item orgInfo : list){
			if(orgList.contains(orgInfo.getGuid())){
				bol = true;
			}else{
				bol = check(orgInfo.getChildren(),orgList);
			}
			if(bol){
				return bol;
			}
		}
		
		return bol;
	}

	/**
	 * 本部门
	 * @return
	 */
	private String getOwnerOrg(String userId) {
		String dwm="";
		DynaBean dynaBean=DynaBeanUtil.getPerson(userId);
		if(dynaBean==null||dynaBean.getValue("dwm")==null){
			dwm = "";
		}else{
			dwm = (String)dynaBean.getValue("dwm");
		}
		
		return dwm;
	}
	
	/**
	 * 
	 * @Title: input 
	 * @Description:跳转输入页面 
	 * @return
	 */
	public String input(){
		query.setCatalogId(model.getCatalogId());
		list = itemService.getTreeList(query);
		return "edit";
	}
	
	public String insert(){
		itemService.insert(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 
	 * @Title: update 
	 * @Description:更新条目 
	 * @return
	 */
	public String update(){
		itemService.update(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 更新条目顺序码，调整同一父节点下的所有直属子节点
	 * @return
	 */
	public String updateOrder(){
		List<Item> list = new ArrayList<Item>();
		String[] guid=model.getGuid().split(",");
		String[] order=model.getOrder().split(",");
		for(int i=0;i<guid.length;i++){
			Item item = new Item();
			item.setGuid(guid[i].trim());
			item.setOrder(order[i].trim());
			item.setCatalogId(model.getCatalogId());
			list.add(item);
		}
		itemService.updateOrder(list);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 
	 * @Title: delete 
	 * @Description:删除条目 
	 * @return
	 */
	public String delete(){
		List<Item> list = new ArrayList<Item>();
		String[] guid=model.getGuid().split(",");
		for(int i=0;i<guid.length;i++){
			Item item = new Item();
			item.setGuid(guid[i].trim());
			item.setCatalogId(model.getCatalogId());
			list.add(item);
		}
		itemService.delete(list);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 进入导入页面，和编目导入共享页面
	 * @return
	 */
	public String toImport(){
		String url = getRequest().getContextPath()+"/code/codeItem_importData.html";
		getValueStack().set("url", url);
		return "toFile";
	}
	
	/**
	 * 导入数据，根据表头标注进行实体属性映射
	 * @return
	 * @throws Exception
	 */
	public String importData()throws Exception{
		try{
		Workbook book = Workbook.getWorkbook(file);
		
		//获取第一个工作表对象
		Sheet sheet = book.getSheet(0);
		
		int columns = sheet.getColumns();
		int rows =  sheet.getRows();
		
		
		//标题
		Map<Integer,String> titles = new HashMap<Integer,String>();
		
		for( int i = 0; i < columns; i++ ) {
			Cell cell = sheet.getCell(i, 0);
			if(StringUtils.isBlank(cell.getCellFeatures().getComment())){
				throw new Exception("标题第"+i+"列信息缺失");
			}
			titles.put(i,cell.getCellFeatures().getComment());
		}
		
		//值
		List<Field> fields = ReflectionUtils.getAllDeclaredField(Item.class);
		Map<String,Field> fMap = new HashMap<String,Field>();
		List<Item> beans = new ArrayList<Item>(rows-1);
		for(Field f : fields){
			if(titles.containsValue(f.getName())){
				fMap.put(f.getName(), f);
			}
		}
		for( int i = 1; i < rows; i++ ) {
			Item bean = new Item();
			for( int j = 0; j < columns; j++ ) {
				Cell cell = sheet.getCell(j, i);
				String v = cell.getContents();
				Field f = fMap.get(titles.get(j));
				ReflectHelper.setValueByFieldName(bean, titles.get(j), ConvertUtils.convertStringToObject(v,f.getType() ));
			}
			beans.add(bean);
		}
		book.close();
		itemService.insertList(beans);
		
		getValueStack().set(DATA, getMessage());
		}catch (Exception e) {
			setErrorMessage("导入发生了异常，请联系管理员");
		//	log.error(e.getCause().getMessage());
			getValueStack().set(DATA, getMessage());
		}
		return "file";
	}
	
	public String exportData()throws Exception{
		list = itemService.getList(query);
		Catalog ca = CodeUtil.getCatalog(query.getCatalogId());
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		getResponse().setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(ca.getName()+"_条目集.xls", "utf-8"));
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet("主库", 0);
		
		//表头
		sheet1.addCell(generateTheadLabel(0,0,"条目编号","guid"));
		sheet1.addCell(generateTheadLabel(1,0,"条目所属编目编号","catalogId"));
		sheet1.addCell(generateTheadLabel(2,0,"条目信息","description"));
		sheet1.addCell(generateTheadLabel(3,0,"条目注释","comment"));
		sheet1.addCell(generateTheadLabel(4,0,"是否可见","visible"));
		sheet1.addCell(generateTheadLabel(5,0,"是否弃用","dumped"));
		sheet1.addCell(generateTheadLabel(6,0,"是否可选中","checked"));
		sheet1.addCell(generateTheadLabel(7,0,"是否包含父节点信息","hasParentNodeInfo"));
		sheet1.addCell(generateTheadLabel(8,0,"顺序码","order"));
		sheet1.addCell(generateTheadLabel(9,0,"提示","tip"));
		sheet1.addCell(generateTheadLabel(10,0,"条目父节点编号","parentId"));
		
		int i = 0;
		for(Item item:list){
			i++;
			sheet1.addCell( new Label( 0, i, item.getGuid() ) );
			sheet1.addCell( new Label( 1, i, item.getCatalogId() ) );
			sheet1.addCell( new Label( 2, i, item.getDescription() ) );
			sheet1.addCell( new Label( 3, i, item.getComment() ) );
			sheet1.addCell( new Label( 4, i, item.getVisible().toString() ) );
			sheet1.addCell( new Label( 5, i, item.getDumped().toString() ) );
			sheet1.addCell( new Label( 6, i, item.getChecked().toString() ) );
			sheet1.addCell( new Label( 7, i, item.getHasParentNodeInfo().toString() ) );
			sheet1.addCell( new Label( 8, i, item.getOrder() ) );
			sheet1.addCell( new Label( 9, i, item.getTip() ) );
			sheet1.addCell( new Label( 10, i, item.getParentId() ) );
		}
		
		wwb.write();
		wwb.close();
		//ExportToExlUtil.export(list, Item.class, attrNames, attrNames, new String[]{"ID"}, "test", 0, 0, op);
		return null;
	}
	
	/**
	 * 组装表头，标注
	 * @param column
	 * @param row
	 * @param cellContext
	 * @param cellComment
	 * @return
	 */
	private Label generateTheadLabel(int column,int row, String cellContext,String cellComment){
		WritableCellFeatures f = new WritableCellFeatures();
		f.setComment(cellComment);
		Label label = new Label(column,row,cellContext);
		label.setCellFeatures(f);
		return label;
	}
	
	public String reload(){
		CodeUtil.reloadDataByCatalogId(query.getCatalogId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public Item getModel() {
		return model;
	}
	public void setModel(Item model) {
		this.model = model;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	public ItemQuery getQuery() {
		return query;
	}

	public void setQuery(ItemQuery query) {
		this.query = query;
	}

	public List<Item> getList() {
		return list;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public Map<String, Catalog> getMap() {
		return map;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the suborgscope
	 */
	public String getSuborgscope() {
		return suborgscope;
	}

	/**
	 * @param suborgscope the suborgscope to set
	 */
	public void setSuborgscope(String suborgscope) {
		this.suborgscope = suborgscope;
	}
	
}
