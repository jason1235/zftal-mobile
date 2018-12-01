package com.zfsoft.hrm.baseinfo.org.action;

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
import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.org.entities.OrgSearch;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgSearchService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.CookieUtil;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

public class OrgSearchAction extends HrmAction {

	private static final long serialVersionUID = 4973955908067980429L;
	
	private OrgQuery searchQuery = new OrgQuery();
	
	private OrgSearch info = new OrgSearch();
	
	private List<OrgSearch> infoList = new ArrayList<OrgSearch>();
	
	private PageList pageList;
	
	private List<DynaBean> peopleList = new ArrayList<DynaBean>();
	
	private DynaBeanQuery query = new DynaBeanQuery( null );
	
	private String oid;
	
	private String searchByParent;
	
	private IOrgSearchService orgSearchService;
	
	private IDynaBeanService dynaBeanService;
	
	private final static String COOKIE_NAME = "VIEW_STYLE";
	
	private String sortFieldName=null;
	
	private String asc="up";
	
	public String list(){
		infoList = orgSearchService.getList(searchQuery);
		String type = searchQuery.getType();
//		getValueStack().set("peopleCount", orgSearchService.getPeopleCount());
		if(type != null && !"".equals(type)){
			getValueStack().set("peopleCount", orgSearchService.getPeopleCountByType(type));
		}
		return LIST_PAGE;
	}
	
	private void query(){
		if(StringUtils.isEmpty(oid)){
			oid = (String)SessionFactory.getSession().getAttribute("org_search_oid");
		}
		Assert.isTrue(!StringUtil.isEmpty(oid), "未选择任何部门");
		info = orgSearchService.getById(oid);
		//处理进入详细信息后，oid参数丢失问题
		SessionFactory.getSession().setAttribute("org_search_oid", oid);
		if(checkChanged()){
			String pageSize = getRequest().getParameter("dynaQuery.perPageSize");
			if(StringUtils.isEmpty(pageSize))
				query.setPerPageSize(15);
		}
		query.setClazz( InfoClassCache.getOverallInfoClass() );
		String exp = null;
		if(StringUtil.isEmpty(searchByParent) || "0".equals(searchByParent)){
			exp = "exists (select 1 from HRM_BZGL_ZZJGB z where z.bmdm=dwm and z.bmdm = '" + oid + "')";
		}
		else{
			exp = "exists (select 1 from HRM_BZGL_ZZJGB z where z.bmdm=dwm and (z.sjbmdm = '" + oid + "' or z.bmdm = '" + oid + "') )";
		}
		query.setExpress(exp + multiMatchExpress() + " and DQZTM like '1%'");
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "pxm,gh" );
		}		
	}
	
	public String detailList(){
		query();
		pageList = dynaBeanService.findPagingInfoList(query);
		if(checkChanged()){
			return "dtl_list_style2";
		}else{
			return "dtl_list";
		}
	}
	
	public String export() throws Exception{
		query();
		List<DynaBean> list = dynaBeanService.findList(query);
		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,
				DateTimeUtil.getFormatDate(new Date(),"yyyy-MM-dd_HHmmss")+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		
		WritableSheet sheet1 = wwb.createSheet("汇总查询结果", 0);
		Label label = null;
		int i=0;
		
		for (InfoProperty infoProperty : query.getClazz().getProperties()) {
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
			for(InfoProperty infoProperty : query.getClazz().getProperties()){
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
	
	public String change() throws Exception{
		if(checkChanged()){
			CookieUtil.removeCookie(getResponse(), COOKIE_NAME);//清除
		}else{
			CookieUtil.setCookie(getResponse(), COOKIE_NAME, "2");//设置转换
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	private boolean checkChanged(){
		String cookie = CookieUtil.getCookie(getRequest(), COOKIE_NAME);
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

	

	/**
	 * 返回
	 * @return 
	 */
	public List<OrgSearch> getInfoList() {
		return infoList;
	}

	/**
	 * 设置
	 * @param infoList 
	 */
	public void setInfoList(List<OrgSearch> infoList) {
		this.infoList = infoList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgQuery getSearchQuery() {
		return searchQuery;
	}

	/**
	 * 设置
	 * @param searchQuery 
	 */
	public void setSearchQuery(OrgQuery searchQuery) {
		this.searchQuery = searchQuery;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IOrgSearchService getOrgSearchService() {
		return orgSearchService;
	}

	/**
	 * 设置
	 * @param orgSearchService 
	 */
	public void setOrgSearchService(IOrgSearchService orgSearchService) {
		this.orgSearchService = orgSearchService;
	}

	/**
	 * 返回
	 * @return 
	 */
	public PageList getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList pageList) {
		this.pageList = pageList;
	}


	/**
	 * 返回
	 * @return 
	 */
	public DynaBeanQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置
	 * @param oid 
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IDynaBeanService getDynaBeanService() {
		return dynaBeanService;
	}

	/**
	 * 设置
	 * @param dynaBeanService 
	 */
	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgSearch getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info 
	 */
	public void setInfo(OrgSearch info) {
		this.info = info;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getSearchByParent() {
		return searchByParent;
	}

	/**
	 * 设置
	 * @param searchByParent 
	 */
	public void setSearchByParent(String searchByParent) {
		this.searchByParent = searchByParent;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<DynaBean> getPeopleList() {
		return peopleList;
	}

	/**
	 * 设置
	 * @param peopleList 
	 */
	public void setPeopleList(List<DynaBean> peopleList) {
		this.peopleList = peopleList;
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
