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

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.data.util.ImportDataValidator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;


/**
 * 
 * @author ChenMinming
 * @date 2013-11-27
 * @version V1.0.0
 */
public class InfoClassCheckAction extends HrmAction{
	
	private static final long serialVersionUID = 456779859976874L;
	private IDynaBeanBusiness dynaBeanBusiness;
	private PageList<DynaBean> list = new PageList<DynaBean>();
	private DynaBeanQuery query = new DynaBeanQuery();
	private InfoProperty property = new InfoProperty();
	private File file;
	private String fileContentType;
	private String dqztm="11";
	private String sortFieldName = null;
	private String asc = "up";
	
	public String list(){
		InfoClass clazz;
		List<InfoProperty> properties=new ArrayList<InfoProperty>();
		//默认初始查询为基本信息表的第一个字段
		if (property==null||StringUtil.isEmpty(property.getClassId())) {
			clazz=InfoClassCache.getInfoClassByName("JBXXB");
			for (InfoProperty infoProperty : clazz.getViewables()) {
				if(!infoProperty.getVirtual()){
					property=infoProperty;break;
				}
			}
			properties.add(property);
		}else{
			clazz=InfoClassCache.getInfoClass(property.getClassId());
			if(StringUtils.isEmpty(property.getGuid())){
				for (InfoProperty infoProperty : clazz.getViewables()) {
					if(!infoProperty.getVirtual()){
						property=infoProperty;break;
					}
				}
				properties.add(property);
			}else{
				String[] guids=property.getGuid().split(",");
				for (String guid : guids) {
					InfoProperty p=clazz.getPropertyById(StringUtils.trim(guid));
					if(p==null){
						continue;
					}
					properties.add(p);
				}
			}
		}
		query.setClazz(clazz);
		//设置history为true（history默认为false，false时将自动过滤历史信息类（如非最高学历最高学位的学历信息等））
		query.setHistory(true);
		//根据字段的不同拼接相应的SQL非空条件查询语句
		String express = fillSQLString(properties);
		if(!StringUtil.isEmpty(dqztm)){
			express+="and EXISTS(SELECT 1 from overall dqzt where dqzt.dqztm='"+dqztm+"' and dqzt.gh = t.gh)";
		}
		query.setExpress(express);
		String orderStr=null;
		if("o.xm".equals(sortFieldName)){
			orderStr=sortFieldName;
		}
		else if (clazz.getPropertyByName(sortFieldName)!=null) {
			orderStr = "t."+sortFieldName;
		}
		if(!StringUtil.isEmpty(orderStr)){
			if(asc.equals("up")){
				query.setOrderStr( orderStr );
			}else{
				query.setOrderStr( orderStr +" desc");
			}
		}else{
			query.setOrderStr( " t.gh" );
		}
		list = dynaBeanBusiness.findPagingInfoLeftJoinOverall(query);
		getValueStack().set("clazz", clazz);
		getValueStack().set("clazzList", InfoClassCache.getInfoClasses());
		this.getValueStack().set("heightOffset", (clazz.getEditables().size()/2)*30);
		return "list";
	}
	
	/**
	 * 上传页面
	 * @return
	 * @throws Exception
	 */
	public String upload() throws Exception{
		
		return "upload";
	}
	
	/**
	 * 列表导出
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception{
		//参数默认处理（与查询列表逻辑相同）
		InfoClass clazz;
		List<InfoProperty> properties=new ArrayList<InfoProperty>();
		//默认初始查询为基本信息表的第一个字段
		if (property==null||StringUtil.isEmpty(property.getClassId())) {
			clazz=InfoClassCache.getInfoClassByName("JBXXB");
			property = clazz.getViewables().get(0);
			properties.add(property);
		}else{
			clazz=InfoClassCache.getInfoClass(property.getClassId());
			if(StringUtils.isEmpty(property.getGuid())){
				property = clazz.getViewables().get(0);
				properties.add(property);
			}else{
				String[] guids=property.getGuid().split(",");
				for (String guid : guids) {
					InfoProperty p=clazz.getPropertyById(StringUtils.trim(guid));
					if(p==null){
						continue;
					}
					properties.add(p);
				}
			}
		}
		//response输出流处理
		//设置编码、类型、文件名
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, clazz.getName()+"_"+property.getName()+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet(clazz.getName(), 0);
		//头三行固定为 序号 工号 姓名
		int i=0;
		//序号批注设置为信息类guid 后续导入时使用该批注确定修改的信息类类型
		sheet1.addCell(generateTheadLabel(0,0,"序号",clazz.getGuid()));
		i++;
		//职工号批注设置为常量字符串guid 后续导入时将会查找该行确定工号所在列位置
		sheet1.addCell(generateTheadLabel(i,0,"职工号","guid"));
		i++;
		//姓名单纯导出时使用   便于用户查看记录
		sheet1.addCell(new Label(i,0,"姓名"));
		List<InfoProperty> propertyList=new ArrayList<InfoProperty>();
		for(InfoProperty p:clazz.getViewables()){
			//对于图片、照片、文件 暂时不支持导出 故先忽略
			if (Type.PHOTO.equals(p.getFieldType())
					||Type.IMAGE.equals(p.getFieldType())
					||Type.FILE.equals(p.getFieldType())) {
				continue;
			}
			i++;
			//对于导出的为空行 在相应字段上添加字段名批注 用于修改时确定修改值所在列
			if(properties.contains(p)){
				String name = p.getName();
				sheet1.addCell(generateTheadLabel(i,0,name,p.getFieldName()));
			}else{
				sheet1.addCell(new Label(i,0,p.getName()));
			}
			propertyList.add(p);
		}
		
		query.setClazz(clazz);
		//出于内存考虑 导出设置最大值  最大不能超过9000行 后续考虑配置该值
		query.setPerPageSize(9000);
		query.setHistory(true);
		String express = fillSQLString(properties);
		if(!StringUtil.isEmpty(dqztm)){
			express+="and EXISTS(SELECT 1 from overall dqzt where dqzt.dqztm='"+dqztm+"' and dqzt.gh = t.gh)";
		}
		query.setExpress(express);
		query.setOrderStr( " t.gh" );
		
		list = dynaBeanBusiness.findPagingInfoLeftJoinOverall(query);
		for (int j = 1; j <= list.size(); j++) {
			i=0;
			DynaBean bean = list.get(j-1);
			sheet1.addCell(new Label(i,j,j+""));
			i++;
			sheet1.addCell(generateTheadLabel(i,j,bean.getValueString("gh"),bean.getValueString("globalid")));
			i++;
			sheet1.addCell(new Label(i,j,bean.getValueString("overall_xm")));
			for(InfoProperty p:propertyList){
				i++;
				String value;
				if ( Type.CODE.equals( p.getFieldType() )||
						Type.YEAR.equals(p.getFieldType())||
						Type.DATE.equals(p.getFieldType())||
						Type.MONTH.equals(p.getFieldType())) {
					value = bean.getViewHtml().get(p.getFieldName());
				} else if ( Type.SIGLE_SEL.equals( p.getFieldType() ) ) {
					if ("1".equals(bean.getValueString(p.getFieldName()))) {
						value="是";
					}else{
						value="否";
					}
				}else{
					value= bean.getValueString(p.getFieldName());
				}
				sheet1.addCell(new Label(i,j,value));
			}
		}
		wwb.write();
		wwb.close();
		return null;
	}
	
	/**
	 * 数据导入
	 * @return
	 * @throws Exception
	 */
	public String dataImport() {
		
		int maxValidateError = 20;
		try{
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			if(file == null){
				throw new RuleException("文件没有接收成功");
			}
			if(file.length()==0){
				throw new RuleException("数据文件内容为空");
			}
			if(!fileContentType.equals("application/vnd.ms-excel")){
				throw new RuleException("请确保数据文件格式为excel文件");
			}
			Workbook wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			DataProcessInfoUtil.setInfo("数据头校验开始...", null);
			int columns = sheet.getColumns();
			Cell xh = sheet.getCell(0, 0);
			
			String classId = getComment(xh);
			if(StringUtils.isEmpty(classId)){
				throw new RuleException("信息类编号缺失，请校验数据表头");
			}
			InfoClass clazz = InfoClassCache.getInfoClass(classId);
			DataProcessInfoUtil.setInfo("数据格式解析，检定为["+clazz.getName()+"]", null);
			List<Integer> fieldCol=new ArrayList<Integer>();
			List<InfoProperty> pList = new ArrayList<InfoProperty>();
			int ghCol=0;
			
			for (int i=1;i<columns;i++) {
				String field = getComment(sheet.getCell(i, 0));
				if(ghCol==0&&"guid".equals(field)){
					DataProcessInfoUtil.setInfo("已定位工号所在列：["+i+"]", null);
					ghCol=i;
				}else if(!StringUtil.isEmpty(field)){
					property = clazz.getPropertyByName(field);
					if(property==null)continue;
					fieldCol.add(i);
					pList.add(property);
					DataProcessInfoUtil.setInfo("已定位要修改的字段：["+property.getName()+"],所在列：["+i+"]", null);
				}
			}
			if(ghCol==0){
				throw new RuleException("未能定位工号所在列：["+ghCol+"]");
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
				String globalid = getComment(cgh);
				String gh = cgh.getContents();
				if(StringUtil.isEmpty(globalid)){
					throw new RuleException("globalid缺失！");
				}
				Map<String, Object> values = new HashMap<String, Object>();
				for (int i=0;i<fieldCol.size();i++) {
					Cell c = sheet.getCell(fieldCol.get(i), m);
					String fieldName = pList.get(i).getFieldName();
					String v = c.getContents();
					if(c.getType().equals(CellType.DATE)){
						Date d = ((DateCell)c).getDate();
						v = TimeUtil.format(d, TimeUtil.yyyy_MM_dd);
					}
					if(v == null){
						v = "";
					}
					String value = ImportDataValidator.validateCell(v,pList.get(i));
					values.put(fieldName, value);
				}
				DynaBean bean = new DynaBean(clazz);
				bean.setValue("gh", gh);
				bean.setValue("globalid", globalid);
				bean=dynaBeanBusiness.findById(bean);
				dynaBeanBusiness.modifyRecord(bean, values, true);
				}catch (Exception e) {
					if(e instanceof RuleException){
						DataProcessInfoUtil.setInfo("序号:"+m+"  导入失败,原因:"+e.getMessage(), InfoType.ERROR);
						cnt++;
						if(cnt>=maxValidateError){
							throw new RuleException("校验失败次数达到上限"+maxValidateError+"次");	
						}
					}else{
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
	
	/**
	 * 组装带批注的Excel单元格
	 * @param column 所在行
	 * @param row 所在列
	 * @param cellContext 单元格内容
	 * @param cellComment 批注内容
	 * @return
	 */
	private Label generateTheadLabel(int column,int row, String cellContext,String cellComment){
		WritableCellFeatures f = new WritableCellFeatures();
		f.setComment(cellComment);
		Label label = new Label(column,row,cellContext);
		label.setCellFeatures(f);
		return label;
	}
	/**
	 * 根据信息类编号查询具体信息类
	 * 页面下拉查单切换信息类时使用 用于级联查询出信息类对应的字段列表
	 * @return
	 */
	public String findInfoClass(){
		InfoClass clazz=InfoClassCache.getInfoClass(property.getClassId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("clazz", clazz);
		getValueStack().set(DATA, map);
		return DATA;
	}
	/**
	 * 根据属性字段的不同获取相应的非空sql条件语句
	 * @param propertyItem
	 * @return
	 */
	private String fillSQLString(List<InfoProperty> list){
		//通用非空条件为字段为空或者空字符串
		String sql = " (#fieldName is null or #fieldName == ''";
		StringBuilder sb=new StringBuilder();
		for (InfoProperty propertyItem : list) {
			if(sb.length()>0){
				sb.append(" or ");
			}
			sb.append(sql);
			//如果类型为代码
			if (Type.CODE.equals(propertyItem.getFieldType())) {
				//代码来源为部门时候 则判断为空的条件附加上【 组织机构表】中不存在该部门
				if (ICodeConstants.DM_DEF_ORG.equals(propertyItem.getCodeId())) {
					sb.append(" or not EXISTS(SELECT 1 from HRM_BZGL_ZZJGB z where z.BMDM=#fieldName and z.SFFQ = '0')");
				}
				//其他代码来源则判断为空的条件附加上 【代码条目表】中不存在该条目
				else{
					sb.append(" or not EXISTS(SELECT 1 from HRM_XTGL_DMTMB z where z.SSBM='");
					sb.append(propertyItem.getCodeId());
					sb.append("' and z.TMID=#fieldName and z.SFQY = '0')");
				}
			}
			//如果类型为图片或者照片 则判断条件附加上 【人事系统照片表】中不存在对应的图片记录
			else if (Type.PHOTO.equals(propertyItem.getFieldType())
					||Type.IMAGE.equals(propertyItem.getFieldType())) {
				sb.append(" or not EXISTS(SELECT 1 from hrm_xtgl_zpb z where z.WJCCBBH=#fieldName)");
			}
			//如果类型为文件 则判断条件附加上 【人事系统附件信息表】中不存在对应的文件记录
			else if (Type.FILE.equals(propertyItem.getFieldType())) {
				sb.append(" or not EXISTS(SELECT 1 HRM_XTGL_FJXXB z where z.FJBH =#fieldName)");
			}
			//如果类型为是否 则判断条件附加上非合法字段（1:是   0:否）
			else if (Type.SIGLE_SEL.equals(propertyItem.getFieldType())){
				sb.append(" or (#fieldName != '0' and #fieldName != '1')");
			}
			sb.append(")");
			int i=0;
			while((i=sb.indexOf("#fieldName", i))>-1){
				sb.replace(i, i+10, "t."+propertyItem.getFieldName());
				i++;
			}
		}
		return "("+sb.toString()+") ";
	}

	/**
	 * 返回
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
	 */
	public InfoProperty getProperty() {
		return property;
	}

	/**
	 * 设置
	 * @param property 
	 */
	public void setProperty(InfoProperty property) {
		this.property = property;
	}

	/**
	 * 返回
	 */
	public PageList<DynaBean> getList() {
		return list;
	}

	/**
	 * 设置
	 * @param dynaBeanBusiness 
	 */
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
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
	/**
	 * 返回
	 */
	public String getDqztm() {
		return dqztm;
	}
	/**
	 * 设置
	 * @param dqztm 
	 */
	public void setDqztm(String dqztm) {
		this.dqztm = dqztm;
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

	/**
	 * 返回
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * 设置
	 * @param fileContentType 
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	
	

}
