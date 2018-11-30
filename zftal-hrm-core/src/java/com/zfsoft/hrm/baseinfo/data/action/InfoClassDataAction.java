package com.zfsoft.hrm.baseinfo.data.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.data.entity.ImportProcessInfo;
import com.zfsoft.hrm.baseinfo.data.entity.InfoType;
import com.zfsoft.hrm.baseinfo.data.service.IInfoClassDataService;
import com.zfsoft.hrm.baseinfo.data.util.DataProcessInfoUtil;
import com.zfsoft.hrm.baseinfo.data.util.ImportDataValidator;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.util.base.StringUtil;

/** 
 * 信息类数据action
 * @author jinjj
 * @date 2012-10-20 上午10:07:15 
 *  
 */
public class InfoClassDataAction extends HrmAction {

	private static final long serialVersionUID = -6692368038834683414L;
	private List<InfoClass> list;
	private String classId;
	private File file;
	private String fileFileName;
	private String fileContentType;
	private IInfoClassDataService infoClassDataService;
	
	/**
	 * 列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		list = InfoClassCache.getInfoClasses();
		List<List<InfoClass>> subList=new ArrayList<List<InfoClass>>();
		
		for (int i=0;i<list.size();i++){
			if(i%3==0){
				subList.add(new ArrayList<InfoClass>());
			}
			subList.get(subList.size()-1).add(list.get(i));
		}
		
		getValueStack().set("subList", subList);
		return "list";
	}
	
	/**
	 * 模板下载
	 * @return
	 * @throws Exception
	 */
	public String downloadTemplate() throws Exception{
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, clazz.getName()+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet(clazz.getName(), 0);
		
		int i=0;
		sheet1.addCell(generateTheadLabel(0,0,"序号",clazz.getGuid()));
		i++;
		sheet1.addCell(generateTheadLabel(i,0,"职工号(必填)","gh"));
		for(InfoProperty p:clazz.getEditables()){
			i++;
			String name = p.getName();
			if(p.getNeed()){
				name += "(必填)";
			}
			String fieldName=p.getFieldName();
			if(Type.CODE.equals(p.getTypeInfo().getName())){
				Catalog catalog= CodeUtil.getCatalog(p.getCodeId());
				if(catalog!=null){
					fieldName=new StringBuilder(fieldName).append(",").append(catalog.getName()).toString();
				}
			}
			sheet1.addCell(generateTheadLabel(i,0,name,fieldName));
//			CellView cv = new CellView();
//			cv.setAutosize(true);
//			sheet1.setColumnView(i, cv);
		}
		
		wwb.write();
		wwb.close();
		return null;
	}
	
	/**
	 * 模板下载,不默认包含工号列
	 * @return
	 * @throws Exception
	 */
	public String dTemplateNoCheckGh() throws Exception{
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, clazz.getName()+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet(clazz.getName(), 0);
		
		int i=0;
		sheet1.addCell(generateTheadLabel(0,0,"序号",clazz.getGuid()));
		for(InfoProperty p:clazz.getEditables()){
			i++;
			String name = p.getName();
			if(p.getNeed()){
				name += "(必填)";
			}
			String fieldName=p.getFieldName();
			if(Type.CODE.equals(p.getTypeInfo().getName())){
				Catalog catalog= CodeUtil.getCatalog(p.getCodeId());
				if(catalog!=null){
					fieldName=new StringBuilder(fieldName).append(",").append(catalog.getName()).toString();
				}
			}
			sheet1.addCell(generateTheadLabel(i,0,name,fieldName));
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
	
	/**
	 * 上传页面
	 * @return
	 * @throws Exception
	 */
	public String upload() throws Exception{
		return "upload";
	}
	
	/**
	 * 上传页面,工号可以为空
	 * @return
	 * @throws Exception
	 */
	public String uploadNoCheckGh() throws Exception{
		return "upload_nocheckgh";
	}
	
	/**
	 * 数据导入
	 * @return
	 * @throws Exception
	 */
	public String dataImport() {
		try{
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			checkFile();
			ImportDataValidator idv = new ImportDataValidator();
			idv.checkContent(file,classId);
			Map<String,HashMap<String,String>> dataMap = idv.getDataMap();
			DataProcessInfoUtil.setInfo(" 数据转换完成，共"+dataMap.size()+"条", null);
			infoClassDataService.doDataImport(idv);
			DataProcessInfoUtil.setInfo(" 导入完成", null);
		}catch (Exception e) {
			DataProcessInfoUtil.setInfo(" "+e.getMessage(), InfoType.ERROR);
			DataProcessInfoUtil.setInfo(" 导入终止", null);
			e.printStackTrace();
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
	
	/**
	 * 数据导入，工号可以为空
	 * @return
	 * @throws Exception
	 */
	public String dataImpNoCheckGh() {
		try{
			DataProcessInfoUtil.setInfo(" 导入开始，请耐心等待", null);
			checkFile();
			ImportDataValidator idv = new ImportDataValidator();
			idv.checkContentNoCheckGh(file);
			Map<String,HashMap<String,String>> dataMap = idv.getDataMap();
			DataProcessInfoUtil.setInfo(" 数据转换完成，共"+dataMap.size()+"条", null);
			infoClassDataService.doDataImportNoCheckGh(idv);
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
	
	public String info() throws Exception{
		return "info";
	}
	
	/**
	 * 处理信息
	 * @return
	 * @throws Exception
	 */
	public String process() throws Exception{
		ImportProcessInfo p = new ImportProcessInfo();
		StringBuilder sb = new StringBuilder();
		String info = null;
		do{
			info = DataProcessInfoUtil.getInfo();
			if(!StringUtil.isEmpty(info)){
				if(info.equals("-1")){
					p.setFinish(true);
				}else{
					sb.append(info);
				}
			}
		}while(info != null);
		p.setDescription(sb.toString());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", p);
		map.put("progress", DataProcessInfoUtil.getStep());
		getValueStack().set(DATA, map);
		return DATA;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public List<InfoClass> getList() {
		return list;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setInfoClassDataService(IInfoClassDataService infoClassDataService) {
		this.infoClassDataService = infoClassDataService;
	}

}
