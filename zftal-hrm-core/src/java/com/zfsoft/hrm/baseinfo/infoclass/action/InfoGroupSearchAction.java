package com.zfsoft.hrm.baseinfo.infoclass.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.baseinfo.search.constants.SearchConstants;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-7-31
 * @version V1.0.0
 */
public class InfoGroupSearchAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -964081095867437005L;
	
	private IDynaBeanService dynaBeanService;

	private String[] conditionItems;//前台返回的已选中的项目序列
	private Map<String,String[]> viewMap;//页面展示用
	
	private Map<String,List<InfoProperty>> porMap;
	private Map<String,List<String>> valueMap;
	
	private List<Item> itemBeans;//已选中的项目
	
	private Map<String,Item> selectedItemMap=new HashMap<String, Item>();
	
	
	private Date birthDateStart;
	
	private Date birthDateEnd;
	
	private String deptId;
	
	private PageList<DynaBean> pageList;
	
	private DynaBeanQuery query = initQuery();
	private String classId;//字段配置使用
	
	private String sortFieldName=null;
	private String asc="up";
	
	private DynaBeanQuery initQuery(){
		DynaBeanQuery query = new DynaBeanQuery(null);
		query.setPerPageSize(15);//默认15条
		return query;
	}
	
	public String list() {
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
		
		if((conditionItems==null||conditionItems.length==0)
				&&(query.getFuzzyValue()==null||query.getFuzzyValue().trim().equals(""))){
			query.setExpress( SearchConstants.STATE_IN_SERVICE_1 + " and "+birthDayExpress() );
			
		}else{
			String condition=getConditionStr();
			query.setExpress(condition+" and "+birthDayExpress());
		}
		
		pageList =  dynaBeanService.findPagingInfoList( query );
		
		this.setInActionContext("paginator", pageList.getPaginator());
		List<InfoClass> infoClasses = new ArrayList<InfoClass>();
		infoClasses.add(InfoClassCache.getOverallInfoClass());
		infoClasses.addAll(InfoClassCache.getInfoClasses());
		this.setInActionContext("infoClasses", infoClasses);
		return LIST_PAGE;
	}

	public String findProperty(){
		InfoClass infoClass = InfoClassCache.getInfoClass(classId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("properties", infoClass.getViewables());
		getValueStack().set(DATA, map);
		return DATA;
	}

	private String getConditionStr(){
		StringBuilder conditionResult=new StringBuilder();
		if( conditionItems == null ) {
			conditionItems = new String[0];
		}
		fillMapFromConditionItems();
		conditionResult.append("1=1 ");
		conditionResult.append(stumultiMatchExpress());
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		for (String classId : porMap.keySet()) {
			StringBuilder tableSql=new StringBuilder();
			String tName;
			if(!clazz.getGuid().equals(classId)){
				InfoClass c = InfoClassCache.getInfoClass(classId);
				tableSql.append(" and exists (select 1 from ");
				tableSql.append(c.getIdentityName());
				tableSql.append(" temp where temp.gh = t.gh ");
				tName = "temp.";
			}else{
				tableSql.append(" and ( 1=1");
				tName = "";
			}
			List<InfoProperty> pList = porMap.get(classId);
			for (InfoProperty p : pList) {
				String sql="";
				for (String value : valueMap.get(p.getGuid())) {
					if(!StringUtil.isEmpty(sql))
						sql += "or ";
					String field = p.getFieldName();
					if(p.getVirtual()){
						field = StringUtil.isEmpty(tName)?p.getDisplayFormula():p.getDisplayFormula(tName);
					}
					if(Type.YEAR.equals(p.getFieldType())
							||Type.MONTH.equals(p.getFieldType())
							||Type.DATE.equals(p.getFieldType())){
						String[] dataStr = value.split("TO");
						if(!StringUtil.isEmpty(dataStr[0])){
							sql+= tName+field+"  >= to_date('"+dataStr[0]+"','"+p.getTypeInfo().getFormat()+"') and ";
						}
						if(dataStr.length>1&&!StringUtil.isEmpty(dataStr[1])){
							sql+= tName+field+"  <= to_date('"+dataStr[1]+"','"+p.getTypeInfo().getFormat()+"') and ";
						}
						sql+=" 1=1";
					}else{
						sql+= tName+field+" like '%"+value+"%' ";
					}
				}
				tableSql.append(" and ("+sql+")");
			}
			tableSql.append(")");
			conditionResult.append(tableSql);
		}

		return conditionResult.toString();
	}
	
	private void fillMapFromConditionItems(){
		porMap = new HashMap<String, List<InfoProperty>>();
		valueMap = new HashMap<String, List<String>>();
		viewMap = new HashMap<String, String[]>();
		for(String condition:conditionItems){
			String[] strs=condition.split("&");
			String[] viewStr = new String[3];
			if(strs.length==3){
				InfoClass clazz = InfoClassCache.getInfoClass(strs[0]);
				if(clazz!=null){
					viewStr[0]=clazz.getName();
					List<InfoProperty> pList = porMap.get(clazz.getGuid());
					if(pList==null){
						pList = new ArrayList<InfoProperty>();
						porMap.put(clazz.getGuid(), pList);
					}
					InfoProperty infoProperty = clazz.getPropertyById(strs[1]);
					if(infoProperty == null){
						continue;
					}
					viewStr[1]=infoProperty.getName();
					List<String> values = valueMap.get(infoProperty.getGuid());
					if(values==null){
						values = new ArrayList<String>();
						pList.add(infoProperty);
						valueMap.put(infoProperty.getGuid(), values);
					}
					values.add(strs[2]);
					if(Type.YEAR.equals(infoProperty.getFieldType())
							||Type.MONTH.equals(infoProperty.getFieldType())
							||Type.DATE.equals(infoProperty.getFieldType())){
						viewStr[2] = strs[2].replace("TO", "至");
					}
					else{
						viewStr[2] = ViewParse.parse(infoProperty, strs[2]);
					}
				}
				viewMap.put(condition, viewStr);
			}
		}
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
	
	public String export() throws IOException, WriteException {
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

	public PageList<DynaBean> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<DynaBean> pageList) {
		this.pageList = pageList;
	}

	public List<Item> getItemBeans() {
		
		return itemBeans;
	}

	public void setItemBeans(List<Item> itemBeans) {
		this.itemBeans = itemBeans;
	}


	public Map<String, Item> getSelectedItemMap() {
		return selectedItemMap;
	}

	public void setSelectedItemMap(Map<String, Item> selectedItemMap) {
		this.selectedItemMap = selectedItemMap;
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

	/**
	 * 设置
	 * @param classId 
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 返回
	 */
	public Map<String, String[]> getViewMap() {
		return viewMap;
	}


}
