package com.zfsoft.hrm.normal.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.zfsoft.dao.entities.AncdModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.custommenu.entity.Condition;
import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.service.ICustomMenuService;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.CookieUtil;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

/** 
 * @author jinjj
 * @date 2012-12-19 下午05:16:19 
 *  
 */
public class CatalogQueryAction extends HrmAction {

	private IDynaBeanService dynaBeanService;
	private PageList pageList;
	private DynaBeanQuery query = new DynaBeanQuery( null );
	private String menuId;
	private ICustomMenuService customMenuService;
	private String classId;
	private String sortFieldName=null;
	private String asc="up";
	
	public String list() throws Exception{
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		classId = clazz.getGuid();
		if(checkChanged()){
			clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
			String pageSize = getRequest().getParameter("query.perPageSize");
			if(StringUtils.isEmpty(pageSize))
				query.setPerPageSize(15);
		}
		query.setClazz(clazz);
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "pxm,gh" );
		}
		String express = menuExpress() + multiMatchExpress()+" and "+DeptFilterUtil.getCondition("", "dwm");
//		express = SearchUtil.siftByInService( express );
		query.setExpress( express );
		
		pageList = dynaBeanService.findPagingInfoList(query);
		String permission="";
		if (getUser().getJsdms().contains("admin")) {
			permission = "_zj_xg_sc_"; 
		}else{
			try{
				List<AncdModel> buttons = (List<AncdModel>)getValueStack().findValue("ancdModelList");
				if(buttons!=null){
					for(AncdModel m:buttons){
						permission += "_"+m.getCzdm()+"_";
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				permission = "";
			}
		}
		getValueStack().set("permission", permission);
		if(checkChanged()){
			return "list_style2";
		}else{
			return "list_style1";
		}
	}
	
	private String menuExpress(){
		if(StringUtils.isEmpty("menuId")){
			throw new RuleException("分类查询参数缺失");
		}
		CustomMenu custom = customMenuService.getById(menuId);
		List<Condition> conditions = getJsonList(custom.getConditions());
		StringBuilder sb = new StringBuilder();
		for(Condition condition : conditions){
			InfoProperty p = InfoClassCache.getOverallInfoClass().getPropertyById(condition.getName());
			String columnName = p.getFieldName();
			String value = condition.getValue();
			if("zhxlm".equals(columnName)){
				sb.append(columnName+" like '"+value.substring(0,1)+"%'"); //学历特殊处理
			}else{
				sb.append(columnName+" like '"+value+"%'"); 
			}
			sb.append(" and ");
		}
		sb.append("1=1");
		return sb.toString();
	}
	
	private List<Condition> getJsonList(List<Condition> conditions){
		List<Condition> list = new ArrayList<Condition>();
		if(conditions == null){
			return list;
		}
		for(Condition condition : conditions){
			if(StringUtil.isNotEmpty(condition.getName()) && StringUtil.isNotEmpty(condition.getValue())){
				list.add(condition);
			}
		}
		return list;
	}
	
	public String change() throws Exception{
		if(checkChanged()){
			CookieUtil.removeCookie(getResponse(), IConstants.VIEW_STYLE);//清除
		}else{
			CookieUtil.setCookie(getResponse(), IConstants.VIEW_STYLE, "2");//设置转换
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	private boolean checkChanged(){
		String cookie = CookieUtil.getCookie(getRequest(), IConstants.VIEW_STYLE);
		if(StringUtils.isEmpty(cookie))
			return false;//未转换
		else
			return true;//已转换
	}
	
	private String multiMatchExpress(){
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isBlank(query.getFuzzyValue())){
			sb.append( " and (gh like '%' || #{fuzzyValue} || '%'");
			sb.append( " or xm like '%' || #{fuzzyValue} || '%'");
			sb.append( " or xmpy like '%' || #{fuzzyValue} || '%' )");
		}
		return sb.toString();
	}
	
	public String export() throws Exception{
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		classId = clazz.getGuid();
		if(checkChanged()){
			clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
			String pageSize = getRequest().getParameter("query.perPageSize");
			if(StringUtils.isEmpty(pageSize))
				query.setPerPageSize(15);
		}
		query.setClazz(clazz);
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "gh" );
		}
		String express = menuExpress() + multiMatchExpress()+" and "+DeptFilterUtil.getCondition("", "dwm");
//		express = SearchUtil.siftByInService( express );
		query.setExpress( express );
		
		List<DynaBean> list = dynaBeanService.findList(query);
		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,
				DateTimeUtil.getFormatDate(new Date(),"yyyy-MM-dd_HHmmss")+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		
		WritableSheet sheet1 = wwb.createSheet("分类查询结果", 0);
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

	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	public void setCustomMenuService(ICustomMenuService customMenuService) {
		this.customMenuService = customMenuService;
	}

	public String getClassId() {
		return classId;
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
