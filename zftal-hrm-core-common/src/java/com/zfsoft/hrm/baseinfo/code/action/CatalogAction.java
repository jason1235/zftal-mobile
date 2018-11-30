/**   
 * @Title: CatalogAction.java 
 * @Package com.zfsoft.hrm.baseinfo.code.action 
 * @author jinjj   
 * @date 2012-5-18 下午02:28:27 
 * @version V1.0   
 */
package com.zfsoft.hrm.baseinfo.code.action;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.entity.Selection;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.reflect.ConvertUtils;
import com.zfsoft.util.reflect.ReflectHelper;
import com.zfsoft.util.reflect.ReflectionUtils;

/** 
 * @ClassName: CatalogAction 
 * @Description: 编目action
 * @author jinjj
 * @date 2012-5-18 下午02:28:27 
 *  
 */
public class CatalogAction extends HrmAction implements ModelDriven<Catalog>{


	private static final long serialVersionUID = 2068740431844188257L;
	Log log = LogFactory.getLog(CatalogAction.class);
	
	private Catalog model = new Catalog();
	private PageList pageList;
	private CatalogQuery query = new CatalogQuery();
	private File file;
	
	private ICatalogService catalogService;
	
	private String sortFieldName = null;
	private String asc = "up";
	/**
	 * 
	 * @Title: list 
	 * @Description: 编目列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		if (getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
        }
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " BMID" );
		}
		pageList = catalogService.searchPageList(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "list";
	}
	
	/**
	 * 
	 * @Title: query 
	 * @Description:查看编目 
	 * @return
	 */
	public String query() {
		model = catalogService.getEntity(model);
		return "edit";
	}
	
	/**
	 * 
	 * @Title: input 
	 * @Description:进入新增页面 
	 * @return
	 */
	public String input() {
		return "edit";
	}
	
	/**
	 * 
	 * @Title: update 
	 * @Description:更新编目 
	 * @return
	 */
	public String update(){
		catalogService.update(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 
	 * @Title: insert 
	 * @Description:新增编目 
	 * @return
	 */
	public String insert(){
		catalogService.insert(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 
	 * @Title: delete 
	 * @Description:删除编目 
	 * @return
	 */
	public String delete(){
		catalogService.delete(model);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String load(){
		Map<String,Catalog> map = CodeUtil.getCatalogMap();
		List<Selection> list = new ArrayList<Selection>();
		for(Entry<String,Catalog> e : map.entrySet()){
			Catalog obj = e.getValue();
			Selection s = new Selection();
			s.setName(obj.getName());
			s.setValue(obj.getGuid());
			list.add(s);
		}
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("success", true);
		res.put("result", list);
		getValueStack().set(DATA, res);
		return DATA;
	}
	
	/**
	 * 进入导入页面，和条目导入共享页面
	 * @return
	 */
	public String toImport(){
		String url = getRequest().getContextPath()+"/baseinfo/codeCatalog_importData.html";
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
				throw new Exception("标题第"+i+"列数据未填");
			}
			titles.put(i,cell.getCellFeatures().getComment());
		}
		
		//值
		List<Field> fields = ReflectionUtils.getAllDeclaredField(Catalog.class);
		Map<String,Field> fMap = new HashMap<String,Field>();
		List<Catalog> beans = new ArrayList<Catalog>(rows-1);
		for(Field f : fields){
			if(titles.containsValue(f.getName())){
				fMap.put(f.getName(), f);
			}
		}
		for( int i = 1; i < rows; i++ ) {
			Catalog bean = new Catalog();
			for( int j = 0; j < columns; j++ ) {
//				try {
				Cell cell = sheet.getCell(j, i);
				String v = cell.getContents();
				Field f = fMap.get(titles.get(j));
				ReflectHelper.setValueByFieldName(bean, titles.get(j), ConvertUtils.convertStringToObject(v,f.getType() ));
//				} catch (Exception e) {
//					throw new Exception("数据格式解析出错,单元格位置：行 "+i+" 列 "+j,e);
//				}		
			}
			beans.add(bean);
		}
		book.close();
		catalogService.insertList(beans);
		getValueStack().set(DATA, getMessage());
		}catch (Exception e) {
			setErrorMessage("导入发生了异常，请联系管理员");
			log.error(e.getCause().getMessage());
			getValueStack().set(DATA, getMessage());
		}
		return "file";
	}
	
	public String exportData()throws Exception{
		List<Catalog> list = new ArrayList<Catalog>();
		list = catalogService.getList(query);
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		getResponse().setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("编目集.xls", "utf-8"));
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet("主库", 0);
		
		//表头
		sheet1.addCell(generateTheadLabel(0,0,"编目编号","guid"));
		sheet1.addCell(generateTheadLabel(1,0,"编目名称","name"));
		sheet1.addCell(generateTheadLabel(2,0,"是否包含父节点","includeParentNode"));
		sheet1.addCell(generateTheadLabel(3,0,"分隔符","delimiter"));
		sheet1.addCell(generateTheadLabel(4,0,"分级码","classCode"));
		sheet1.addCell(generateTheadLabel(5,0,"选中表达式","chooseExpr"));
		sheet1.addCell(generateTheadLabel(6,0,"备注","remark"));
		sheet1.addCell(generateTheadLabel(7,0,"代码来源","source"));
		sheet1.addCell(generateTheadLabel(8,0,"代码类型","type"));
		
		int i = 0;
		for(Catalog catalog:list){
			i++;
			sheet1.addCell( new Label( 0, i, catalog.getGuid() ) );
			sheet1.addCell( new Label( 1, i, catalog.getName() ) );
			sheet1.addCell( new Label( 2, i, catalog.getIncludeParentNode().toString() ) );
			sheet1.addCell( new Label( 3, i, catalog.getDelimiter() ) );
			sheet1.addCell( new Label( 4, i, catalog.getClassCode() ) );
			sheet1.addCell( new Label( 5, i, catalog.getChooseExpr() ) );
			sheet1.addCell( new Label( 6, i, catalog.getRemark() ) );
			sheet1.addCell( new Label( 7, i, catalog.getSource() ) );
			sheet1.addCell( new Label( 8, i, catalog.getType() ) );
		}
		
		wwb.write();
		wwb.close();
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
	
	public void setCatalogService(ICatalogService catalogService) {
		this.catalogService = catalogService;
	}
	public Catalog getModel() {
		return model;
	}
	public void setModel(Catalog model) {
		this.model = model;
	}

	public CatalogQuery getQuery() {
		return query;
	}

	public void setQuery(CatalogQuery query) {
		this.query = query;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public PageList getPageList() {
		return pageList;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

}
