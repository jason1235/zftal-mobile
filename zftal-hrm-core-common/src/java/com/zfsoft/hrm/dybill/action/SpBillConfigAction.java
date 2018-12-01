package com.zfsoft.hrm.dybill.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewType;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoPropertyType;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.dybill.dto.ChoiceProperty;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillConfigVersion;
import com.zfsoft.hrm.dybill.entity.SpBillQuery;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfigData;
import com.zfsoft.hrm.seniorreport.enums.SeniorReportType;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;
/**
 * 审批表单配置
 * @author Patrick Shen
 */
public class SpBillConfigAction extends HrmAction {
	
	private static final long serialVersionUID = 6854631681595616817L;
	
	private Long copyBillClassId;
	private String copyBillConfigId;
	private String op="add";
	private SpBillQuery query = new SpBillQuery();
	private SpBillConfig spBillConfig;
	private PageList<SpBillQuery> spBillConfigList = new PageList<SpBillQuery>();
	private ISpBillConfigService spBillConfigService;
	private XmlBillClass xmlBillClassBean;	
	private XmlBillProperty xmlBillProperty;
	
	private String sortFieldName = null;
	private String asc = "up";
	private Integer version;
	
	private File file;
	/**
	 * 版本发布
	 * @methodName publish 
	 * @return String
	 */
	public String publish() {
		SpBillConfigVersion billConfigVersion=new SpBillConfigVersion();
		billConfigVersion.setBillConfigId(spBillConfig.getId());
		spBillConfigService.addSpBillConfigVersion(billConfigVersion);
		this.setSuccessMessage("发布版本"+spBillConfigService.getLastVersion(spBillConfig.getId())+"成功");
		this.getValueStack().set(DATA,  getMessage());
		return DATA;
	}
	public String check() {
		
		spBillConfig = spBillConfigService.getSpBillConfigById(spBillConfig.getId());
		ITableService tableService = SpringHolder.getBean("oracleTableService",ITableService.class);
		String msg="";
		
		for (XmlBillClass billClass : spBillConfig.getXmlBillClasses().getBillClasses()) {			
			if(!StringUtil.isEmpty(billClass.getClassId())){
				InfoClass ic = InfoClassCache.getInfoClass(billClass.getClassId());
				if(ic==null||!tableService.isExist(ic.getIdentityName())){
					msg+=billClass.getName()+" 所引用的信息类已经被删除。<br/>";
				}
			}
		}
		if(!StringUtil.isEmpty(msg)){
			this.setErrorMessage(msg);
		}
		this.getValueStack().set(DATA,  getMessage());
		return DATA;
	}
	
	public String add() {
		spBillConfig=new SpBillConfig();
		return EDIT_PAGE;
	}
	
	public String addChoiceXmlBillProperty() {
		spBillConfigService.addXmlBillProperty(spBillConfig.getId(), xmlBillClassBean.getId(), xmlBillProperty);
		this.setSuccessMessage("选择成功");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("message", getMessage());
		map.put("xmlBillProperty", xmlBillProperty);
		this.getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String getDefInputStyle(){
		StringBuilder sb = new StringBuilder();
		if (!com.zfsoft.hrm.baseinfo.dyna.html.Type.FILE.equals(xmlBillProperty.getFieldType()) &&
				!com.zfsoft.hrm.baseinfo.dyna.html.Type.IMAGE.equals(xmlBillProperty.getFieldType())&&
				!com.zfsoft.hrm.baseinfo.dyna.html.Type.PHOTO.equals(xmlBillProperty.getFieldType())) {
			String str = xmlBillProperty.getFieldName();
			xmlBillProperty.setFieldName("xmlBillProperty.defaultValue");
			sb.append(EditParse.parse(xmlBillProperty.getInfoProperty(), xmlBillProperty.getDefaultValue()));
			xmlBillProperty.setFieldName(str);
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", sb.toString());
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String syncXmlBillProperty(){
		xmlBillClassBean=spBillConfigService.getXmlBillClassById(spBillConfig.getId(), xmlBillClassBean.getId());
		List<InfoProperty> infoPropertyList=InfoClassCache.getInfoClass(xmlBillClassBean.getClassId()).getViewables();
		List<XmlBillProperty> billPropertyList=spBillConfigService.getXmlBillPropertyList(
				spBillConfig.getId(), xmlBillClassBean.getId());
		List<XmlBillProperty> newBillPropertyList=new ArrayList<XmlBillProperty>();
		if(billPropertyList==null){
			billPropertyList=new ArrayList<XmlBillProperty>();
		}
		for (XmlBillProperty xmlBillProperty : billPropertyList) {
			for (InfoProperty infoProperty : infoPropertyList) {
				if (xmlBillProperty.getFieldName().equals(infoProperty.getFieldName())) {
					Boolean required=infoProperty.getNeed()||xmlBillProperty.getRequired();
					Boolean visable=infoProperty.getViewable()&&xmlBillProperty.getVisable();
					Boolean editable=infoProperty.getEditable()&&xmlBillProperty.getEditable();
					xmlBillProperty.setInfoProperty(infoProperty);
					xmlBillProperty.setPropertyId(infoProperty.getGuid());
					xmlBillProperty.setRequired(required);
					xmlBillProperty.setVisable(visable);
					xmlBillProperty.setEditable(editable);
					newBillPropertyList.add(xmlBillProperty);
					break;
				}
			}
		}
		xmlBillClassBean.setBillPropertys(newBillPropertyList);
		spBillConfigService.modifyXmlBillClassFull(spBillConfig.getId(), xmlBillClassBean);
		this.getValueStack().set("billClasses", 
				spBillConfigService.getXmlBillClassList(spBillConfig.getId()));
		return "configList";
	}
	
	public String addXmlBillClass() {
		xmlBillClassBean=new XmlBillClass();
		this.getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		return "classConfigEdit";
	}

	public String addXmlBillProperty() {
		xmlBillProperty=new XmlBillProperty();
		Type[] types = TypeFactory.getTypes( InfoPropertyType.class );
		this.getValueStack().set("types", types);
		this.getValueStack().set("viewStyles", ViewType.values());
		return "propertyConfigEdit";
	}

	//表单配置分页列表显示
	public String list() {
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " NAME" );
		}
		spBillConfigList = spBillConfigService.getPagingSpBillConfigList(query);
		int beginIndex = spBillConfigList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		return LIST_PAGE;
	}
	
	/**
	 * 获取导出模板【其存储文件名为表单id】
	 * @return 导出模板文件对象
	 */
	private File getTemplate(){
		String path = "WEB-INF" + File.separator + "classes" + File.separator
		+ "exportModel" + File.separator + "billConfig";
		path+=File.separator+spBillConfig.getId()+".xml";
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		
		File temp = new File(path);
		return temp;
	}
	
	/**
	 * 跳转到上传模板界面
	 * @return
	 * @throws Exception
 	 */
	public String toUpload() throws Exception{
		if(!StringUtil.isEmpty(spBillConfig.getId())){
			this.getValueStack().set("hasModel", getTemplate().exists());
		}else{
			this.getValueStack().set("hasModel", false);
		}
		return "file";
	}
	

	/**
	 * 下载原有的导出模板
	 * @return
	 */
	public String downloadTemplate() {
		try {
			File temp = getTemplate();
			spBillConfig=spBillConfigService.getSpBillConfigById(spBillConfig.getId());
			getResponse().setCharacterEncoding( IConstants.FILE_ENCODE );
			getResponse().setContentType( "application/file" );
			getResponse().setHeader( "Content-Disposition",  DownloadFilenameUtil.fileDisposition(getRequest().getHeader("user-agent"), spBillConfig.getName()+".xml") );
			
			OutputStream out = getResponse().getOutputStream();
			InputStream in  = new FileInputStream(temp);
			byte[] b = new byte[1024];
			int length = 0;
			while((length = in.read(b))!=-1){
				out.write(b,0,length);
			}
			in.close();
			out.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 上传导出模板
	 * @return
	 */
	public String saveTemplate(){
		FileOutputStream out = null;
		InputStream in = null;
		try {
			if(file == null){
				throw new RuleException("文件没有接收成功");
			}
			if(file.length()==0){
				throw new RuleException("数据文件内容为空");
			}
			File temp = getTemplate();
			if(!temp.exists())
				temp.createNewFile();
			out = new FileOutputStream(temp);
			in  = new FileInputStream(file);
			byte[] b = new byte[1024];
			int length = 0;
			while((length = in.read(b))!=-1){
				out.write(b,0,length);
			}
			in.close();
			out.flush();
		}catch ( Exception e ) {
			setErrorMessage(e.getMessage());
			getValueStack().set(DATA, getMessage());
			return "res";
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		getValueStack().set(DATA, getMessage());
		return "res";
	}
	public String choicePropertyList(){
		xmlBillClassBean=spBillConfigService.getXmlBillClassById(spBillConfig.getId(), xmlBillClassBean.getId());
		List<InfoProperty> infoPropertyList=InfoClassCache.getInfoClass(xmlBillClassBean.getClassId()).getViewables();
		List<XmlBillProperty> billPropertyList=spBillConfigService.getXmlBillPropertyList(
				spBillConfig.getId(), xmlBillClassBean.getId());
		if(billPropertyList==null){
			billPropertyList=new ArrayList<XmlBillProperty>();
		}
		List<ChoiceProperty> choicePropertyList=new ArrayList<ChoiceProperty>();
		for (InfoProperty infoProperty : infoPropertyList) {
			ChoiceProperty choice=new ChoiceProperty();
			choice.setInfoProperty(infoProperty);
			for (XmlBillProperty xmlBillProperty : billPropertyList) {
				if(xmlBillProperty.getPropertyId().equals(infoProperty.getGuid())){
					choice.setBillProperty(xmlBillProperty);
					choice.setChecked(true);
					continue;
				}
			}
			if(choice.getBillProperty()==null){
				choice.setBillProperty(new XmlBillProperty());
				choice.setInfoProperty(infoProperty);
			}
			choicePropertyList.add(choice);
		}
		
		this.getValueStack().set("choicePropertyList", choicePropertyList);
		
		return "propertyChoice";
	}
	
	public String copy() {
		op="copy";
		spBillConfig=spBillConfigService.getSpBillConfigById(spBillConfig.getId());
		return EDIT_PAGE;
	}
	public String copyXmlBillClass() {
		SpBillConfig query=new SpBillConfig();
		query.setBillType(null);
		query.setStatus(BillConfigStatus.USING);
		List<SpBillConfig> spbillconfigs=spBillConfigService.getSpBillConfigList(query);
		List<XmlBillClass> billClasses = null;
		if(spbillconfigs.size()>0){
			billClasses=spbillconfigs.get(0).getXmlBillClasses().getBillClasses();
		}
		this.getValueStack().set("spbillconfigs", spbillconfigs);
		this.getValueStack().set("billClasses", billClasses);
		return "copyBillClass";
	}
	public String defineCatchConfig()
	{
		xmlBillClassBean = spBillConfigService.getXmlBillClassById(spBillConfig.getId(), xmlBillClassBean.getId());
		xmlBillClassBean.getFullDefineCatch();
		return "defineCatchConfig";
	}
	public Long getCopyBillClassId() {
		return copyBillClassId;
	}
	public String getCopyBillConfigId() {
		return copyBillConfigId;
	}

	public String getInfoClass() {
		Map<String,Object> map=new HashMap<String, Object>();
		this.setSuccessMessage("获取成功");
		map.put("message", getMessage());
		map.put("infoClass", InfoClassCache.getInfoClass(xmlBillClassBean.getClassId()));
		this.getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String getInfoClassList() {
		String type = getRequest().getParameter("sourceType");
		if(type==null) type = "teacher";
		Map<String,Object> map=new HashMap<String, Object>();
		this.setSuccessMessage("获取成功");
		map.put("message", getMessage());
		map.put("classList", InfoClassCache.getInfoClasses(type));
		this.getValueStack().set(DATA, map);
		return DATA;
	}
		
	public String modify() {
		op="modify";
		spBillConfig=spBillConfigService.getSpBillConfigById(spBillConfig.getId());
		return EDIT_PAGE;
	}
	
	public String modifyChoiceXmlBillProperty() {
		spBillConfigService.modifyXmlBillProperty(spBillConfig.getId(), xmlBillClassBean.getId(), xmlBillProperty);
		this.setSuccessMessage("选择成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String modifyXmlBillClass() {
		op="modify";
		this.getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		xmlBillClassBean=spBillConfigService.getXmlBillClassById(spBillConfig.getId(), xmlBillClassBean.getId());
		return "classConfigEdit";
	}
	
	public String modifyXmlBillProperty() {
		op="modify";
		Type[] types = TypeFactory.getTypes( InfoPropertyType.class );
		this.getValueStack().set("viewStyles", ViewType.values());
		this.getValueStack().set("types", types);
		xmlBillProperty=spBillConfigService.getXmlBillPropertyById(spBillConfig.getId(),
				xmlBillClassBean.getId(), xmlBillProperty.getId());
		return "propertyConfigEdit";
	}
	public String remove() {
		spBillConfigService.removeSpBillConfig(spBillConfig.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String removeChoiceXmlBillProperty() {
		spBillConfigService.removeXmlBillProperty(spBillConfig.getId(), xmlBillClassBean.getId(), 
				xmlBillProperty.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String removeXmlBillClass() {
		spBillConfigService.removeXmlBillClass(spBillConfig.getId(), xmlBillClassBean.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String removeXmlBillProperty() {
		spBillConfigService.removeXmlBillProperty(spBillConfig.getId(), xmlBillClassBean.getId(),
				xmlBillProperty.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String save() {
		if("add".equals(op)){
			spBillConfigService.addSpBillConfig(spBillConfig);
		}else if("copy".equals(op)){
			spBillConfigService.copySpBillConfig(spBillConfig);
		}else{
			spBillConfigService.modifySpBillConfig(spBillConfig);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String saveCopyXmlBillClass() {
		spBillConfigService.saveCopyXmlBillClass(spBillConfig.getId(),copyBillConfigId, copyBillClassId,xmlBillClassBean.getIdentityName());
		this.setSuccessMessage("复制成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String saveDefineCatch(){
		XmlBillClass billClass = spBillConfigService.getXmlBillClassById(spBillConfig.getId(), xmlBillClassBean.getId());
		billClass.setDefineCatch(xmlBillClassBean.getDefineCatch());
		spBillConfigService.modifyXmlBillClass(spBillConfig.getId(), billClass);
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String saveXmlBillClass() {
		if("add".equals(op)){
			spBillConfigService.addXmlBillClass(spBillConfig.getId(), xmlBillClassBean);
		}else{
			XmlDefineCatch defineCatch = spBillConfigService.getXmlBillClassById(spBillConfig.getId(), xmlBillClassBean.getId()).getDefineCatch();
			xmlBillClassBean.setDefineCatch(defineCatch);
			spBillConfigService.modifyXmlBillClass(spBillConfig.getId(), xmlBillClassBean);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String saveXmlBillProperty() {
		if("add".equals(op)){
			spBillConfigService.addXmlBillProperty(spBillConfig.getId(), xmlBillClassBean.getId(), xmlBillProperty);
		}else{
			spBillConfigService.modifyXmlBillProperty(spBillConfig.getId(), xmlBillClassBean.getId(), xmlBillProperty);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String xmlBillClassList() {
		List<XmlBillClass> billClasses;
		List<Integer> versionList=spBillConfigService.getSpBillConfigVersionList(spBillConfig.getId());
		if(version!=null&&versionList.contains(version)){
			billClasses = spBillConfigService.getXmlBillClassListByVersion(spBillConfig.getId(), version);
		}else{
			billClasses = spBillConfigService.getXmlBillClassList(spBillConfig.getId());
		}
		Collections.reverse(versionList);
		this.getValueStack().set("billClasses", billClasses);
		this.getValueStack().set("versionList", versionList);
		return "configList";
	}
	
	public String revertVersion() {
		if(version==null){
			setErrorMessage("版本号不能为空");
		}
		spBillConfig = spBillConfigService.getSpBillConfigByVersion(spBillConfig.getId(), version);
		if(spBillConfig==null){
			setErrorMessage("指定的版本不存在");
		}else{
			spBillConfigService.modifySpBillConfig(spBillConfig);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	public String xmlBillClassMoveDown() {
		spBillConfigService.xmlBillClassMoveDown(spBillConfig.getId(), xmlBillClassBean.getId());
		this.getValueStack().set("billClasses", 
				spBillConfigService.getXmlBillClassList(spBillConfig.getId()));
		return "configList";
	}
	public String xmlBillClassMoveUp() {
		spBillConfigService.xmlBillClassMoveUp(spBillConfig.getId(), xmlBillClassBean.getId());
		this.getValueStack().set("billClasses", 
				spBillConfigService.getXmlBillClassList(spBillConfig.getId()));
		return "configList";
	}
	public String xmlBillPropertyMoveLeft() {
		spBillConfigService.xmlBillPropertyMoveLeft(spBillConfig.getId(), xmlBillClassBean.getId(),
				xmlBillProperty.getId());
		this.getValueStack().set("billClasses", 
				spBillConfigService.getXmlBillClassList(spBillConfig.getId()));
		return "configList";
	}
	public String xmlBillPropertyMoveRight() {
		spBillConfigService.xmlBillPropertyMoveRight(spBillConfig.getId(), xmlBillClassBean.getId(),
				xmlBillProperty.getId());
		this.getValueStack().set("billClasses", 
				spBillConfigService.getXmlBillClassList(spBillConfig.getId()));
		return "configList";
	}
	
	public String getFieldList(){
		String tableName = getRequest().getParameter("tableName");
		String fieldName = getRequest().getParameter("fieldName");
		if(StringUtil.isEmpty(tableName)){
			return DATA;
		}
		String t = tableName.toUpperCase();
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
	
	public String getXmlBillClasses() {
		this.getValueStack().set(DATA, spBillConfigService.getXmlBillClassList(spBillConfig.getId()));
		return DATA;
	}
	
	public void setCopyBillClassId(Long copyBillClassId) {
		this.copyBillClassId = copyBillClassId;
	}
	

	public void setCopyBillConfigId(String copyBillConfigId) {
		this.copyBillConfigId = copyBillConfigId;
	}
	public void setOp(String op) {
		this.op = op;
	}

	public void setQuery(SpBillQuery query) {
		this.query = query;
	}
	public void setSpBillConfig(SpBillConfig spBillConfig) {
		this.spBillConfig = spBillConfig;
	}

	public void setSpBillConfigList(PageList<SpBillQuery> spBillConfigList) {
		this.spBillConfigList = spBillConfigList;
	}
	
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public void setXmlBillClassBean(XmlBillClass xmlBillClass) {
		this.xmlBillClassBean = xmlBillClass;
	}

	public void setXmlBillProperty(XmlBillProperty xmlBillProperty) {
		this.xmlBillProperty = xmlBillProperty;
	}

	public String getOp() {
		return op;
	}
	
	public SpBillQuery getQuery() {
		return query;
	}
	public SpBillConfig getSpBillConfig() {
		return spBillConfig;
	}
	
	public PageList<SpBillQuery> getSpBillConfigList() {
		return spBillConfigList;
	}
	public XmlBillClass getXmlBillClassBean() {
		return xmlBillClassBean;
	}
	
	public XmlBillProperty getXmlBillProperty() {
		return xmlBillProperty;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 返回
	 */
	public File getFile() {
		return file;
	}
	/**
	 * 设置
	 * @param file 
	 */
	public void setFile(File file) {
		this.file = file;
	}
}
