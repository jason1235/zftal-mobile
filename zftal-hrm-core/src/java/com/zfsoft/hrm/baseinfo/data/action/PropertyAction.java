package com.zfsoft.hrm.baseinfo.data.action;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.entity.Property;
import com.zfsoft.hrm.baseinfo.data.entity.Template;
import com.zfsoft.hrm.baseinfo.data.service.IPropertyService;
import com.zfsoft.hrm.baseinfo.data.service.ITemplateService;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.data.util.ImportDataValidator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;

public class PropertyAction extends HrmAction {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 5300539963669562332L;
	private String classId;
	private File file;
	private String fileContentType;
	private IInfoPropertyService infoPropertyService;
	private List<String> items = new ArrayList<String>();
	private PageList<Property>  pageList = new PageList<Property>();	
	private List<Property> pList = new ArrayList<Property>();
	private Property property = new Property();
	private IPropertyService propertyService;
	private Template template = new Template();
	private ITemplateService templateService;
	private IDynaBeanBusiness dynaBeanBusiness;
	
	/**
	 * 检查文件
	 * @throws Exception
	 */
	private void checkFile() throws Exception{
		if(file == null){
			throw new RuleException("文件没有接收成功");
		}
		if(file.length()==0){
			throw new RuleException("数据文件内容为空");
		}
		if(!fileContentType.equals("application/vnd.ms-excel")){
			throw new RuleException("请确保数据文件格式为excel文件");
		}
	}

	public String dataImport(){
		int maxValidateError = 20;
		
		try{
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			checkFile();
			Workbook wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			DataProcessInfoUtil.setInfo("数据头校验开始...", null);
			int columns = sheet.getColumns();
			List<Integer> fieldCol=new ArrayList<Integer>();
			List<InfoProperty> pList = new ArrayList<InfoProperty>();
			int ghCol=0;
			
			for (int i=1;i<columns;i++) {
				String field = getComment(sheet.getCell(i, 0));
				if(ghCol==0&&"gh".equals(field)){
					DataProcessInfoUtil.setInfo("已定位工号所在列：["+i+"]", null);
					ghCol=i;
				}else if(!StringUtil.isEmpty(field)){
					String[] strArray = field.split("\\.");
					if(strArray.length==2){
						InfoClass clazz = InfoClassCache.getInfoClassByName(strArray[0]);
						InfoProperty p =  clazz.getPropertyByName(strArray[1]);
						if(p==null)continue;
						DataProcessInfoUtil.setInfo("已定位要修改的字段：["+p.getName()+"(所属信息类:"+clazz.getName()+")],所在列：["+i+"]", null);
						fieldCol.add(i);
						pList.add(p);
					}
				}
			}
			if(ghCol==0){
				throw new RuleException("未能定位工号所在列!");
			}
			if(pList.isEmpty()){
				throw new RuleException("未能定位要修改的字段");
			}
			DataProcessInfoUtil.setInfo("数据头校验结束", null);
			DataProcessInfoUtil.setInfo("数据内容校验开始...", null);
			
			int rows = sheet.getRows();
			int cnt = 0;
			if(rows==1){
				DataProcessInfoUtil.setInfo("数据内容行数为0行", InfoType.WARN);
			}
			int step = 0;
			for(int m=1;m<rows;m++){
				try{
					DataProcessInfoUtil.setStep("数据入库:", ++step, rows-1);
					Cell cgh = sheet.getCell(ghCol,m);
					String gh = cgh.getContents();
					Map<String, DynaBean> beans = new HashMap<String, DynaBean>();
					
					//数据转换
					for (int i=0;i<fieldCol.size();i++) {
						Cell c = sheet.getCell(fieldCol.get(i), m);
						InfoProperty p = pList.get(i);
						DynaBean bean = beans.get(p.getClassId());
						if(bean==null){
							bean = new DynaBean(InfoClassCache.getInfoClass(p.getClassId()));
							beans.put(p.getClassId(), bean);
						}
						String v = c.getContents();
						if(c.getType().equals(CellType.DATE)){
							Date d = ((DateCell)c).getDate();
							v = TimeUtil.format(d, TimeUtil.yyyy_MM_dd);
						}
						if(v == null){
							v = "";
						}
						String value = ImportDataValidator.validateCell(v,pList.get(i));
						bean.setValue(p.getFieldName(), value);
					}
					DataProcessInfoUtil.setInfo("数据导入中...", null);
					dynaBeanBusiness.doImportData(new ArrayList<DynaBean>(beans.values()),gh,true);
				
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+m+"  导入失败,原因:"+e.getMessage(), InfoType.ERROR);
						cnt++;
						if(cnt>=maxValidateError){
							throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");	
						}
					}else{
						e.printStackTrace();
						throw new RuleException("导入时发生未知异常",e);
					}
				}
			}
			DataProcessInfoUtil.setInfo(" 导入完成", null);
		}catch (Exception e) {
			DataProcessInfoUtil.setInfo(" "+e.getMessage(), InfoType.ERROR);
			DataProcessInfoUtil.setInfo(" 导入终止", null);
		}finally{
			DataProcessInfoUtil.setInfo("-1", null);
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DataProcessInfoUtil.clear();
		}
		return null;
	}
	
	private String getComment(Cell c){
		if(c.getCellFeatures()!=null){
			return c.getCellFeatures().getComment();
		}else{
			return null;
		}
	}

	public String delete(){
		propertyService.delete(property.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String downloadTemplate() throws Exception{
		pList = propertyService.getByMbid(property.getMbid());
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		Template t = templateService.getById(property.getMbid());
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, t.getMbmc()+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet(t.getMbmc(), 0);
		
		int i=0;
		sheet1.addCell(generateTheadLabel(i,0,"序号",property.getMbid()));   //模版id
		i++;
		sheet1.addCell(generateTheadLabel(i,0,"职工号(必填)","gh"));
		for(Property p : pList){
			i++;
			String name = p.getSxmc();
/*			if(p.getNeed()){
				name += "(必填)";
			}*/
			InfoClass clazz = InfoClassCache.getInfoClass(p.getXxlid());
			sheet1.addCell(generateTheadLabel(i,0,name,clazz.getIdentityName()+"."+p.getZdmc()));
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

	public String getClassId() {
		return classId;
	}

	public File getFile() {
		return file;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}
	
	public IInfoPropertyService getInfoPropertyService() {
		return infoPropertyService;
	}
	
	public List<String> getItems() {
		return items;
	}
	
	public PageList<Property> getPageList() {
		return pageList;
	}
	
	public List<Property> getpList() {
		return pList;
	}
	
	public Property getProperty() {
		return property;
	}

	public IPropertyService getPropertyService() {
		return propertyService;
	}

	public Template getTemplate() {
		return template;
	}

	public ITemplateService getTemplateService() {
		return templateService;
	}

	public String list(){
		property.setMbid(template.getId());
		pageList = propertyService.getPagedProperty(property);
		this.getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		return LIST_PAGE;
	}
	
	public String save(){
		String mbid=property.getMbid();
		List<String> zdmcList= propertyService.getZdmc(mbid);  //根据模版id查找该模版下的所有属性
		for(String id : items){
			InfoProperty p = infoPropertyService.getProperty(id);   //要添加的属性
			if(zdmcList.contains(p.getFieldName())){
				continue;
			}else{
				property.setSxmc(p.getName());
				property.setZdmc(p.getFieldName());
				property.setZdlx(p.getFieldType());
				property.setZdcd(p.getFieldLen());
				this.propertyService.add(property);
			}
		}
		this.setSuccessMessage("添加成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String select(){
		if(classId==null||"".equals(classId)){
			classId="C393FE11C4DC8E46E040007F01003F39";
		}
		List<InfoProperty> infoPropertyList=InfoClassCache.getInfoClass(classId).getViewables();
		this.getValueStack().set("infoPropertyList", infoPropertyList);
		this.getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		this.getValueStack().set("mbid", property);
		return "select";
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setInfoPropertyService(IInfoPropertyService infoPropertyService) {
		this.infoPropertyService = infoPropertyService;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
	
	public void setPageList(PageList<Property> pageList) {
		this.pageList = pageList;
	}
	
	public void setpList(List<Property> pList) {
		this.pList = pList;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}
	
	public void setPropertyService(IPropertyService propertyService) {
		this.propertyService = propertyService;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}

	public String upload(){
		return "upload";
	}

	/**
	 * 设置
	 * @param dynaBeanBusiness 
	 */
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}
}
