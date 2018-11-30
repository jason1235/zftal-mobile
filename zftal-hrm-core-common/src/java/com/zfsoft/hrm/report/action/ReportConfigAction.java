package com.zfsoft.hrm.report.action;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.hrm.report.file.XmlFileUtil;
import com.zfsoft.hrm.report.service.IReportService;
import com.zfsoft.hrm.report.xentity.Item;
import com.zfsoft.hrm.report.xentity.ReportContent;
import com.zfsoft.hrm.report.xentity.ReportFieldConfig;
import com.zfsoft.hrm.report.xentity.ReportViewField;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;
/**
 * 报表配置
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class ReportConfigAction  extends HrmAction{

	private static final long serialVersionUID = 7215513140769016822L;

	private static final String REPORT_ID="report_id";
	
	private IReportService reportService;
	
	private String id;
	
	private String index;
	
	private String itemType;
	
	private ReportXmlFile reportXmlFile=new ReportXmlFile();
	
	private Item item=new Item();
	
	private List<ReportViewField> fieldList = new ArrayList<ReportViewField>();
	
	private void init() {
		if(id!=null){
			this.getRequest().getSession().setAttribute(REPORT_ID,id);
		}else{
			if(this.getRequest().getSession().getAttribute(REPORT_ID)!=null)
				id=this.getRequest().getSession().getAttribute(REPORT_ID).toString();
		}
		List<ReportXmlFile> reportList=reportService.getReportAllList();
		this.getValueStack().set("reportList", reportList);
		if(id!=null){
			this.getValueStack().set("reportView",reportService.getNullViewById(id));
		}
	}
	
	public String revConfig() {
		reportXmlFile=reportService.getReportById(id);
		ReportFieldConfig c = JaxbUtil.getObjectFromXml(reportXmlFile.getFieldConfig(), ReportFieldConfig.class);
		if (c!=null){
			fieldList = c.getFields();
		}
		return "revConfig";
	}

	public String revConfig_save() {
		reportXmlFile=reportService.getReportById(id);
		ReportFieldConfig c = new ReportFieldConfig();
		c.setFields(fieldList);
		reportXmlFile.setFieldConfig(JaxbUtil.getXmlFromObject(c));
		reportService.modify(reportXmlFile);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String thinkFieldList(){
		reportXmlFile=reportService.getReportById(id);
		String fieldName = getRequest().getParameter("fieldName");
		String t = reportXmlFile.getSql().toUpperCase();
		if(t.indexOf("UPDATA ")!=-1
				||t.indexOf("DELETE ")!=-1||t.indexOf("INSERT ")!=-1){
			return  DATA;
		}
		try {
			SqlSessionFactory factory = SpringHolder.getBean("sqlSessionFactory",SqlSessionFactory.class);
			ResultSet set = factory.openSession().getConnection().
				createStatement().executeQuery("select * from ("+t+") where 1!=1");
			ResultSetMetaData md = set.getMetaData();
			List<InfoProperty> result = new ArrayList<InfoProperty>();
			for (int i = 0; i < md.getColumnCount(); i++) {
				String fName = md.getColumnName(i+1).toUpperCase();
				if((!StringUtil.isEmpty(fieldName))&&(fName.indexOf(fieldName.toUpperCase())==-1)){
					continue;
				}
				InfoProperty p = new InfoProperty();
				p.setFieldName(fName);
				p.setFieldLen(md.getColumnDisplaySize(i+1));
				p.setFieldType(md.getColumnTypeName(i+1));
				result.add(p);
			}
			getValueStack().set(DATA, result);
		} catch (SQLException e) {
		}
		return DATA;
	}
	
	public String list(){
		init();
		return LIST_PAGE;
	}
	
	public String pre_view(){
		init();
		return LIST_PAGE;
	}
	/**
	 * 增加报表
	 * @return
	 */
	public String add_report(){
		return "edit_report";
	}
	/**
	 * 修改报表
	 * @return
	 */
	public String modify_report(){
		reportXmlFile=reportService.getReportById(id);
		return "edit_report";
	}
	/**
	 * 删除报表
	 * @return
	 */
	public String delete_report(){
		reportService.remove(id);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	

	/**
	 * 保存报表
	 * @return
	 */
	public String save_report(){
		if(StringUtils.isEmpty(reportXmlFile.getReportId())){
			reportService.add(reportXmlFile);
		}else{
			reportService.modify(reportXmlFile);
		}
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 增加列
	 * @return
	 */
	public String add_column(){
		itemType="column";
		reportXmlFile=reportService.getReportById(id);
		if(!StringUtils.isEmpty(item.getFieldName())){
			ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
			item=rcontent.getColumn(item.getFieldName());
			index=""+(1+rcontent.getColumns().indexOf(item));
			item=new Item();
		}
		List<InfoClass> infoClasses =  new ArrayList<InfoClass>();
		infoClasses.add(InfoClassCache.getOverallInfoClass());
		infoClasses.addAll(InfoClassCache.getInfoClasses());
		getValueStack().set("infoClasses", infoClasses);
		return "add_item";
	}
	/**
	 * 修改列
	 * @return
	 */
	public String modify_column(){
		itemType="column";
		reportXmlFile=reportService.getReportById(id);
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		item=rcontent.getColumn(item.getFieldName());
		return "edit_item";
	}
	/**
	 * 删除列
	 * @return
	 */
	public String remove_column(){
		itemType="column";
		reportXmlFile=reportService.getReportById(id);
		reportService.removeColumn(reportXmlFile,item.getFieldName());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 保存列
	 * @return
	 */
	public String save_column(){
		reportXmlFile=reportService.getReportById(id);
		if(StringUtils.isEmpty(item.getFieldName())){
			reportService.addColumn(reportXmlFile,item,index);
		}else{
			reportService.modifyColumn(reportXmlFile,item);
		}
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 添加行
	 * @return
	 */
	public String add_row(){
		itemType="row";
		if(!StringUtils.isEmpty(item.getFieldName())){
			reportXmlFile=reportService.getReportById(id);
			ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
			item=rcontent.getRow(item.getFieldName());
			index=""+(1+rcontent.getRows().indexOf(item));
			item=new Item();
		}
		List<InfoClass> infoClasses =  new ArrayList<InfoClass>();
		infoClasses.add(InfoClassCache.getOverallInfoClass());
		infoClasses.addAll(InfoClassCache.getInfoClasses());
		getValueStack().set("infoClasses", infoClasses);
		return "add_item";
	}
	/**
	 * 修改行
	 * @return
	 */
	public String modify_row(){
		itemType="row";
		reportXmlFile=reportService.getReportById(id);
		ReportContent rcontent=XmlFileUtil.getXmlTitleSet(reportXmlFile.getContentStream());
		item=rcontent.getRow(item.getFieldName());
		return "edit_item";
	}
	/**
	 * 删除行
	 * @return
	 */
	public String remove_row(){
		itemType="row";
		reportXmlFile=reportService.getReportById(id);
		reportService.removeRow(reportXmlFile,item.getFieldName());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 保存行
	 * @return
	 */
	public String save_row(){
		reportXmlFile=reportService.getReportById(id);
		if(StringUtils.isEmpty(item.getFieldName())){
			reportService.addRow(reportXmlFile,item,index);
		}else{
			reportService.modifyRow(reportXmlFile,item);
		}
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 
	 * @return
	 */
	public String column_left(){
		reportService.columnLeft(id,item);
		init();
		return LIST_PAGE;
	}

	/**
	 * 
	 * @return
	 */
	public String column_right(){
		reportService.columnRight(id,item);
		init();
		return LIST_PAGE;
	}
	/**
	 * 
	 * @return
	 */
	public String row_up(){
		reportService.rowUp(id,item);
		init();
		return LIST_PAGE;
	}
	/**
	 * 
	 * @return
	 */
	public String row_down(){
		reportService.rowDown(id,item);
		init();
		return LIST_PAGE;
	}
	
	public IReportService getReportService() {
		return reportService;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ReportXmlFile getReportXmlFile() {
		return reportXmlFile;
	}

	public void setReportXmlFile(ReportXmlFile reportXmlFile) {
		this.reportXmlFile = reportXmlFile;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * 返回
	 */
	public List<ReportViewField> getFieldList() {
		return fieldList;
	}

	/**
	 * 设置
	 * @param fieldList 
	 */
	public void setFieldList(List<ReportViewField> fieldList) {
		this.fieldList = fieldList;
	}


}
