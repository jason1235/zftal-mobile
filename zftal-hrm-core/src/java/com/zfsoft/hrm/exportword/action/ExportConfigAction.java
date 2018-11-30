package com.zfsoft.hrm.exportword.action;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.exportword.entity.ExportConfig;
import com.zfsoft.hrm.exportword.entity.ExportType;
import com.zfsoft.hrm.exportword.service.IExportConfigService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-25
 * @version V1.0.0
 */
public class ExportConfigAction extends HrmAction{

	private static final long serialVersionUID = -4741591719103761394L;

	private IExportConfigService exportConfigService;

	private ExportConfig exportConfig = new ExportConfig();
	private ExportType exportType = new ExportType();
	
	private File file;
	private String fileContentType;
	public final static String MODEL_DIR = "WEB-INF" + File.separator + "classes" + File.separator
	+ "exportModel" + File.separator + "exportConfig";

	public String page(){
		List<ExportConfig> configList = exportConfigService.getConfigList(exportConfig);
		List<ExportType> typeList = exportConfigService.getTypeList(null);
		getValueStack().set("configList", configList);
		getValueStack().set("typeList", typeList);
		return "page";
	}
	public String editType(){
		if(!StringUtil.isEmpty(exportType.getId())){
			exportType = exportConfigService.findTypeById(exportType.getId());
			if(exportType == null) exportType = new ExportType();
		}
		return "editType";
	}
	
	public String saveType(){
		exportConfigService.saveType(exportType);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String removeType(){
		exportType = exportConfigService.findTypeById(exportType.getId());
		String path = MODEL_DIR + File.separator + exportType.getDir();
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		File dir = new File(path);
		if(dir.exists()&&dir.listFiles().length==0){
			dir.delete();
		}
		exportConfigService.deleteType(exportType.getId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String editConfig(){
		boolean hasFile=false;
		if(!StringUtil.isEmpty(exportConfig.getId())){
			exportConfig = exportConfigService.findConfigById(exportConfig.getId());
			if(exportConfig == null) exportConfig = new ExportConfig();
			else{
				hasFile = getTemplate(exportConfig).exists();
			}
		}
		exportType = exportConfigService.findTypeById(exportConfig.getType());
		getValueStack().set("hasFile", hasFile);
		return "editConfig";
	}

	private File getTemplate(ExportConfig c){
		ExportType t = exportConfigService.findTypeById(c.getType());
		String path = MODEL_DIR + File.separator + t.getDir();
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
		}
		path+=File.separator + c.getId() +".xml";
		return new File(path);
	}
	
	public String downloadTemp(){
		InputStream in = null;
		try {
			exportConfig = exportConfigService.findConfigById(exportConfig.getId());
			File temp = getTemplate(exportConfig);
			getResponse().setCharacterEncoding( IConstants.FILE_ENCODE );
			getResponse().setContentType( "application/file" );
			getResponse().setHeader( "Content-Disposition",  DownloadFilenameUtil.fileDisposition(getRequest().getHeader("user-agent"), exportConfig.getName()+".xml") );
			OutputStream out = getResponse().getOutputStream();
			in  = new FileInputStream(temp);
			byte[] b = new byte[1024];
			int length = 0;
			while((length = in.read(b))!=-1){
				out.write(b,0,length);
			}
			close(in);
			out.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
			close(in);
		}
		return null;
	}

	public String saveConfig(){
		if(StringUtil.isEmpty(exportConfig.getId())&&file ==null){
			setErrorMessage("模板文件不能为空!");
			getValueStack().set(DATA, getMessage());
			return "res";
		}
		exportConfigService.saveConfig(exportConfig);
		if(file!=null){
			FileOutputStream out = null;
			InputStream in = null;
			try {
				File temp = getTemplate(exportConfig);
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
				close(in);
				close(out);
			}
		}
		getValueStack().set(DATA, getMessage());
		return "res";
	}
	
	private void close(Closeable close){
		if(close!=null)
			try {
				close.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public String removeConfig(){
		exportConfig = exportConfigService.findConfigById(exportConfig.getId());
		File f = getTemplate(exportConfig);
		f.delete();
		if(f.getParentFile().listFiles().length==0){
			f.getParentFile().delete();
		}
		exportConfigService.deleteConfig(exportConfig.getId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	

	public void setExportConfigService(IExportConfigService exportConfigService) {
		this.exportConfigService = exportConfigService;
	}
	public ExportConfig getExportConfig() {
		return exportConfig;
	}
	public void setExportConfig(ExportConfig exportConfig) {
		this.exportConfig = exportConfig;
	}
	public ExportType getExportType() {
		return exportType;
	}
	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	

}
