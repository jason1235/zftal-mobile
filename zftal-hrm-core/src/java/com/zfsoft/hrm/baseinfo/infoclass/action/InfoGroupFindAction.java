package com.zfsoft.hrm.baseinfo.infoclass.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoGroupCondition;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoGroupConditionService;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.baseinfo.search.constants.SearchConstants;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

/**
 * 信息类组合查询
 * 
 * @author 沈鲁威
 * @since 2012-7-14
 * @version V1.0.0
 */
public class InfoGroupFindAction extends HrmAction {

	private static final long serialVersionUID = -26333673428473318L;

	private IInfoGroupConditionService infoGroupConditionService;
	
	private IDynaBeanService dynaBeanService;

	private String[] conditionItems;//前台返回的已选中的项目序列
	
	private List<Item> itemBeans;//已选中的项目
	
	private Map<String,Item> selectedItemMap=new HashMap<String, Item>();
	
	private Map<String,String> tableNameFieldNameMap=new HashMap<String, String>();//代码表和字段的映射
	
	private Map<String,String> catalogNameMap=new HashMap<String, String>();//代码表目录名称映射
	
	private Date birthDateStart;
	
	private Date birthDateEnd;
	
	private String deptId;
	
	private PageList pageList;
	
	private DynaBeanQuery query = initQuery();
	private String classId;//字段配置使用
	
	private String sortFieldName=null;
	private String asc="up";
	
	private DynaBeanQuery initQuery(){
		DynaBeanQuery query = new DynaBeanQuery(null);
		query.setPerPageSize(15);//默认15条
		return query;
	}
	
	private void getConditions() {
		//将已选中的属性写入itemBeans以及建立fieldName和代码表的映射
		setItemsFromConditionItems();
		List<InfoGroupCondition> conditions = infoGroupConditionService.getAllInfoGroupCondition();
		LinkedHashMap<String, List<InfoGroupCondition>> typeMap=new LinkedHashMap<String, List<InfoGroupCondition>>();
		for (InfoGroupCondition condition : conditions) {
			condition.setValues(CodeUtil.getChildrenForVisable( condition.getCodeTableName(), null) );
			catalogNameMap.put(condition.getCodeTableName(), condition.getName());
			if(typeMap.get(condition.getCatalogName())==null){
				typeMap.put(condition.getCatalogName(), new ArrayList<InfoGroupCondition>());
			}
			typeMap.get(condition.getCatalogName()).add(condition);
		}
		this.getValueStack().set("keyset",typeMap.keySet());
		this.getValueStack().set("typeMap", typeMap);
	}
	
	public String list() {
		getConditions();
		if (getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
        }
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		classId = clazz.getGuid();
		//处理字段显示配置
		clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
		query.setClazz(clazz);
		query.setExpress( SearchConstants.STATE_IN_SERVICE_1 );
		//数据授权
		String filterStr=DeptFilterUtil.getCondition(null, "dwm");
		if(filterStr!=null && !"".equals(filterStr)){
			query.setExpress(query.getExpress()+" and ("+filterStr+") ");
		}
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "pxm,gh" );
		}
		pageList = (PageList) dynaBeanService.findPagingInfoList( query );
		
		this.setInActionContext("paginator", pageList.getPaginator());
		return LIST_PAGE;
	}

	public String find() {
		getConditions();
		if (getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
        }

		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		classId = clazz.getGuid();
		//处理字段显示配置
		clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
		query.setClazz(clazz);
		
		if((conditionItems==null||conditionItems.length==0)
				&&(query.getFuzzyValue()==null||query.getFuzzyValue().trim().equals(""))){
			query.setExpress( SearchConstants.STATE_IN_SERVICE_1 + " and "+birthDayExpress() );
			
		}else{
			String condition=getConditionStr();
			query.setExpress(condition+" and "+birthDayExpress());
		}
		
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "pxm,gh" );
		}
		//数据授权
		String filterStr=DeptFilterUtil.getCondition(null, "dwm");
		if(StringUtils.isNotEmpty(filterStr)){
			query.setExpress(query.getExpress()+" and ("+filterStr+") ");
		}
		
		pageList = (PageList) dynaBeanService.findPagingInfoList( query );
		
		this.setInActionContext("paginator", pageList.getPaginator());
		
		return LIST_PAGE;
	}
	private String getConditionStr(){
		StringBuilder conditionResult=new StringBuilder();
		
		Map<String,String> fieldValueMap=new HashMap<String,String>();
		
		if( conditionItems == null ) {
			conditionItems = new String[0];
		}

		for(String condition:conditionItems){
			String[] strs=condition.split("&");
			if(!fieldValueMap.containsKey(strs[0])){
				if("zhxlm".equals(strs[0])){
					fieldValueMap.put(strs[0]," " + strs[0]+ " like '"+strs[2].substring(0,1)+"%' " );
				}else{
					fieldValueMap.put(strs[0]," " + strs[0]+ " like '"+strs[2]+"%' " );
				}
			}else{
				if("zhxlm".equals(strs[0])){
					fieldValueMap.put(strs[0],fieldValueMap.get(strs[0])+
							" or " + strs[0]+ " like '"+strs[2].substring(0,1)+"%' " );
				}else{
					fieldValueMap.put(strs[0],fieldValueMap.get(strs[0])+
							" or " + strs[0]+ " like '"+strs[2]+"%' " );
				}
				
			}
		}
		conditionResult.append(" 1=1 ");
		for(String r:fieldValueMap.values()){
			conditionResult.append(" and (");
			conditionResult.append(r);
			conditionResult.append(") ");
		}
		conditionResult.append(stumultiMatchExpress());
//		SearchUtil.siftByInService( conditionResult );
		return conditionResult.toString();
	}
	private String stumultiMatchExpress(){
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isBlank(query.getFuzzyValue())){
			sb.append( " and (gh like '%' || #{fuzzyValue} || '%'");
			sb.append( " or xm like '%' || #{fuzzyValue} || '%'");
			sb.append( " or xmpy like '%' || #{fuzzyValue} || '%' )");
		}
		return sb.toString();
	}
	
	private String birthDayExpress(){
		StringBuffer sb = new StringBuffer();
		
		sb.append(" 1=1 ");
		
		if(birthDateStart!=null){
			sb.append("and csrq >= to_date('"+DateTimeUtil.getFormatDate(this.birthDateStart, "yyyy-MM-dd")+"','yyyy-MM-dd')");
		}
		
		sb.append(" and 1=1 ");
		
		if(birthDateEnd!=null){
			sb.append("and csrq <= to_date('"+DateTimeUtil.getFormatDate(this.birthDateEnd, "yyyy-MM-dd")+"','yyyy-MM-dd')");
		}
		
		sb.append(" and 1=1 ");
		
		if(!StringUtil.isEmpty(deptId)){
			sb.append(" and dwm like '"+deptId+"%'");
		}
		
		return sb.toString();
	}

	private void setItemsFromConditionItems(){
		itemBeans=new ArrayList<Item>();
		if( conditionItems == null ) {
			return;
		}
		Item item;
		for(String condition:conditionItems){
			String[] strs=condition.split("&");
			item=CodeUtil.getItem(strs[1], strs[2]);
			itemBeans.add(item);
			tableNameFieldNameMap.put(strs[1], strs[0]);
			selectedItemMap.put(item.getGuid(), item);
		}
	}
	
	public String export() throws IOException, WriteException {
		getConditions();
		if (getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
        }

		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		classId = clazz.getGuid();
		//处理字段显示配置
		clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
		query.setClazz(clazz);
		
		if((conditionItems==null||conditionItems.length==0)
				&&(query.getFuzzyValue()==null||query.getFuzzyValue().trim().equals(""))){
			query.setExpress( SearchConstants.STATE_IN_SERVICE_1 + " and "+birthDayExpress() );
			
		}else{
			String condition=getConditionStr();
			query.setExpress(condition+" and "+birthDayExpress());
		}
		
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "pxm,gh" );
		}
		//数据授权
		String filterStr=DeptFilterUtil.getCondition(null, "dwm");
		if(filterStr!=null){
			query.setExpress(query.getExpress()+" and ("+filterStr+") ");
		}
		
		List<DynaBean> list=  dynaBeanService.findList( query );
		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,
				DateTimeUtil.getFormatDate(new Date(),"yyyy-MM-dd_HHmmss")+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		
		WritableSheet sheet1 = wwb.createSheet("组合查询结果", 0);
		Label label = null;
		int i=0;
		
		for (InfoProperty infoProperty : clazz.getProperties()) {
			if(infoProperty.getFieldType().equals(Type.FILE)||
					infoProperty.getFieldType().equals(Type.IMAGE)||
					infoProperty.getFieldType().equals(Type.PHOTO)){
				continue;
			}
			label=generateTheadLabel( i, 0, infoProperty.getName(), infoProperty.getFieldName());
			sheet1.addCell(label );
			i++;
		}
		i=1;
		int j=0;
		Object value;
		for (DynaBean dynaBean : list) {
			j=0;
			for(InfoProperty infoProperty : clazz.getProperties()){
				if(infoProperty.getFieldType().equals(Type.FILE)||
						infoProperty.getFieldType().equals(Type.IMAGE)||
						infoProperty.getFieldType().equals(Type.PHOTO)){
					continue;
				}
				value=dynaBean.getViewHtml().get(infoProperty.getFieldName());
				if(value!=null){
					label=new Label(j,i,value.toString(), getValueCellFormat()) ;
				}else{
					label=new Label(j,i,"", getValueCellFormat()) ;
				}
					sheet1.addCell(label );
//					System.out.println(i);
//					System.out.println(j);
//				}catch(Exception e){
//					System.out.println(i);
//					System.out.println(j);
//				}
				j++;
			}
			i++;
		}
		
		wwb.write();
		wwb.close();
		return null;
		
	}
	/**
	 * 表头样式
	 * @return
	 * @throws WriteException
	 */
	private CellFormat getTitleCellFormat() throws WriteException {
		WritableFont wf=new WritableFont(WritableFont.TAHOMA,11,WritableFont.BOLD,false);
		WritableCellFormat wcf=new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setBackground(Colour.GRAY_25);
		return wcf;
	}
	/**
	 * 组装表头，标注
	 * @param column
	 * @param row
	 * @param cellContext
	 * @param cellComment
	 * @return
	 * @throws WriteException 
	 */
	private Label generateTheadLabel(int column,int row, String cellContext,String cellComment) throws WriteException{
		WritableCellFeatures f = new WritableCellFeatures();
		f.setComment(cellComment);
		Label label = new Label(column,row,cellContext);
		label.setCellFormat(getTitleCellFormat());
		label.setCellFeatures(f);
		return label;
	}
	private CellFormat getValueCellFormat() throws WriteException {
		WritableFont wf=new WritableFont(WritableFont.TAHOMA,11,WritableFont.NO_BOLD,false);
		WritableCellFormat wcf=new WritableCellFormat(wf);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		return wcf;
	}
	public String[] getConditionItems() {
		return conditionItems;
	}

	public void setConditionItems(String[] conditionItems) {
		this.conditionItems = conditionItems;
	}

	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	public void setInfoGroupConditionService(
			IInfoGroupConditionService infoGroupConditionService) {
		this.infoGroupConditionService = infoGroupConditionService;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setPageList(PageList pageList) {
		this.pageList = pageList;
	}

	public List<Item> getItemBeans() {
		
		return itemBeans;
	}

	public void setItemBeans(List<Item> itemBeans) {
		this.itemBeans = itemBeans;
	}

	public Map<String, String> getTableNameFieldNameMap() {
		return tableNameFieldNameMap;
	}

	public void setTableNameFieldNameMap(Map<String, String> tableNameFieldNameMap) {
		this.tableNameFieldNameMap = tableNameFieldNameMap;
	}

	public Map<String, Item> getSelectedItemMap() {
		return selectedItemMap;
	}

	public void setSelectedItemMap(Map<String, Item> selectedItemMap) {
		this.selectedItemMap = selectedItemMap;
	}

	public Map<String, String> getCatalogNameMap() {
		return catalogNameMap;
	}

	public void setCatalogNameMap(Map<String, String> catalogNameMap) {
		this.catalogNameMap = catalogNameMap;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	public Date getBirthDateStart() {
		return birthDateStart;
	}

	public void setBirthDateStart(Date birthDateStart) {
		this.birthDateStart = birthDateStart;
	}

	public String getBirthDateEndString() {
		return DateTimeUtil.getFormatDate(birthDateEnd, "yyyy-MM-dd");
	}
	
	public String getBirthDateStartString() {
		return DateTimeUtil.getFormatDate(birthDateStart, "yyyy-MM-dd");
	}
	
	public Date getBirthDateEnd() {
		return birthDateEnd;
	}

	public void setBirthDateEnd(Date birthDateEnd) {
		this.birthDateEnd = birthDateEnd;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public String getClassId() {
		return classId;
	}

}