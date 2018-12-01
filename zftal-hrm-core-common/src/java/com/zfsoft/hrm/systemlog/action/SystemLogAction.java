package com.zfsoft.hrm.systemlog.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.systemlog.entities.FileModel;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/**
 * 
 * @author ChenMinming
 * @date 2013-11-4
 * @version V1.0.0
 */
public class SystemLogAction extends HrmAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 46545795626748L;
	private static String logPath = ""; 
	private static final int MAX_NUM = 20;
	private FileModel model;
	public String page(){
		if (StringUtil.isEmpty(logPath)) {
			logPath = SubSystemHolder.getPropertiesValue("logPath");
			if (StringUtil.isEmpty(logPath)||!new File(logPath).exists()) {
				String path=".."+File.separator+".."+File.separator+"logs";
				logPath = Struts2Utils.getSession().getServletContext().getRealPath(path);
			}
		}
		File logDir = new File(logPath);
		if (!(logDir.exists()&&logDir.isDirectory())) {
			throw new RuleException("无法找到对应的logs文件夹");
		}
		File[] files = logDir.listFiles();
		List<FileModel> list = new ArrayList<FileModel>();
		int fileNum = files.length;
		for (int i = 0; i < fileNum; i++) {
			if (i>=MAX_NUM) {
				break;
			}
			int num=0;
			for (int j = 0; j < files.length; j++) {
				if (files[j]==null) {
					continue;
				}
				if (!files[j].getName().matches("[0-9a-zA-Z-\\\\\\\\._-]+")) {
					fileNum--;
					files[j]=null;
					continue;
				}
				if (files[num]==null) {
					num = j;
				}
				if(files[num].lastModified()<files[j].lastModified()){
					num = j; 
				}
			}	
			list.add(new FileModel(files[num]));
			files[num]=null;
		}
		getValueStack().set("files", list);
		return "list";
	}
	
	public String download() throws Exception{
		if (StringUtil.isEmpty(logPath)) {
			String path=".."+File.separator+".."+File.separator+"logs";
			logPath = Struts2Utils.getSession().getServletContext().getRealPath(path);			
		}
		File logFile = new File(logPath+File.separator+model.getFileName());
		if (!(logFile.exists()&&logFile.isFile())) {
			new RuleException("文件不存在或已被删除");
		}
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/file");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,model.getFileName());
		getResponse().setHeader("Content-Disposition", disposition);
		FileInputStream in = new FileInputStream(logFile);
		byte[] bt = new byte[1024];
		int i;
		while ( (i=in.read(bt))>-1) {
			getResponse().getOutputStream().write(bt,0,i);
		}
		getResponse().getOutputStream().flush();
		return null;
	}

	/**
	 * 返回
	 */
	public FileModel getModel() {
		return model;
	}

	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(FileModel model) {
		this.model = model;
	}
	
}
