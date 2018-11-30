package com.zfsoft.hrm.seniorreport.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfigData;
import com.zfsoft.hrm.seniorreport.service.ISeniorReportService;
import com.zfsoft.hrm.seniorreport.entity.Col;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfig;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportView;
import com.zfsoft.hrm.seniorreport.entity.ViewItem;
import com.zfsoft.hrm.seniorreport.enums.SeniorReportType;
import com.zfsoft.hrm.seniorreport.enums.ViewItemType;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/**
 * 复合报表配置
 * @author ChenMinming
 * @date 2013-12-23
 * @version V1.0.0
 */
public class SeniorReportConfigAction extends HrmAction{
	
	private static final long serialVersionUID = 7215513140769016822L;

	private static final String REPORT_ID="senior_report_id";
	
	private ISeniorReportService  seniorReportService;
	
	private String id;
	
	private String pid;
	
	private String itemType;
	
	private File file;
	
	private String fileContentType;
	
	private SeniorReportType type;
	
	private SeniorReportConfigData config=new SeniorReportConfigData();
	
	private ViewItem item=new ViewItem();
	

	/**
	 * 复合报表表单内容展示方法【递归方法】
	 * @param left 复合报表的行标题
	 * @param buffer 用于填充表单内容的对象
	 * @param itemValueMaps 报表的内容
	 * @param loop 当前所在的子集层数（计算占用列时使用）     外部调用统一传1
	 * @return 最大子集层数
	 */
	private int fillBody(List<ViewItem> left,StringBuffer buffer,LinkedHashMap<ViewItem,List<Col>> itemValueMaps,int loop){
		int size = 0; //记录最大子集层数
		boolean flag=false;
		String key = "TEMP_"+loop+"_"+buffer.hashCode();//key 占用列替换值 最后需要根据最大子集层数进行替换
		
		for (ViewItem viewItem : left) {
			String temp=" ";
			if(flag){
				buffer.append("<tr>");
			}else{
				flag=true;
			}
			//如果不存在子集 因为不确定其他同层对象是否存在子集 所以先打上行替换值 最后根据统计结果来替换需要占用的列数目
			if(viewItem.getSubItem()==null||viewItem.getSubItem().isEmpty()){
				temp = " colspan = "+key;
			}
			buffer.append("<td rowspan="+viewItem.getNum()+temp+" >");
			buffer.append("<div name='row' class='tab_szcd' style='position: relative;float:none;display:block;' >");
			buffer.append("<input type='hidden' name='fieldName' value='"+viewItem.getItemId()+"' />");
			buffer.append("<a class='ico_sz' href='#'>"+viewItem.getName()+"</a>");
			buffer.append("</div>");
			buffer.append("</td>");
			//如果存在子集 则递归调用该方法来对子集进行操作转换成页面显示字符串
			if(viewItem.getSubItem()!=null&&!viewItem.getSubItem().isEmpty()){
				int a = fillBody(viewItem.getSubItem(),buffer,itemValueMaps,loop+1);
				if(a >= size)
					size = a;
			}
			//已经不存在子集  则开始填充数据单元格
			else{
				List<Col> cols = itemValueMaps.get(viewItem);
				for (Col c:cols) {
					buffer.append("<td>"+c.getValue()+"</td>");
				}
			}
			buffer.append("</tr>");
		}
		size++;
		return size;
	}
	/**
	 * 复合表单表头展示方法【递归方法】
	 * @param titles 表头（列标题）对象
	 * @param buffer 用于填充表单内容的对象
	 * @return 最大子集层数
	 */
	private int fillTitels(List<ViewItem> titles,StringBuffer buffer){
		int size = 1;
		List<ViewItem> subTitels = new ArrayList<ViewItem>();
		StringBuffer str=new StringBuffer();
		//遍历当前层所有的列标题对象 将子集记录到集合中用于递归调用使用
		for (ViewItem viewItem : titles) {
			if(viewItem.getSubItem()!=null&&!viewItem.getSubItem().isEmpty()){
				subTitels.addAll(viewItem.getSubItem());
			}
		}
		//搜集的子集集合不为空  则进行递归调用 并记录最大子集层数
		if(!subTitels.isEmpty()){
			size+=fillTitels(subTitels,str);
		}
		buffer.append("<tr>");
		for (ViewItem viewItem : titles) {
			//默认所占用的行数为1
			int rows=1;
			//如果不存在子集 则占用行数=最大子集层数
			if(viewItem.getSubItem()==null||viewItem.getSubItem().isEmpty()){
				rows = size;
			}
			buffer.append("<td style='word-break:break-all;' colspan="+viewItem.getNum()+" rowspan = "+rows+">");
			buffer.append("<div name='column' class='tab_szcd' style='position: relative;float:none;display:block;' >");
			buffer.append("<input type='hidden' name='fieldName' value='"+viewItem.getItemId()+"' />");
			buffer.append("<a class='ico_sz' href='#'>"+viewItem.getName()+"</a>");
			buffer.append("</div>");
			buffer.append("</td>");
		}
		buffer.append("</tr>");
		//将递归调用的子集对象转换后结果拼接到当前行转换结果后
		buffer.append(str);
		return size;
	}
	
	private void init() {
		//沿用简单表单逻辑 记录当前配置的表单id 展开时默认展开该表单
		if(id!=null){
			this.getRequest().getSession().setAttribute(REPORT_ID,id);
		}else{
			if(this.getRequest().getSession().getAttribute(REPORT_ID)!=null)
				id=this.getRequest().getSession().getAttribute(REPORT_ID).toString();
		}
		//获取所有的表单列表  页面选择表单时使用
		List<SeniorReportConfigData> reportList=seniorReportService.getReportList(new SeniorReportConfigData());
		this.getValueStack().set("reportList", reportList);
		//展示表单逻辑
		if(id!=null){
			//获取不带具体数据的空表单用于展现表单样式
			SeniorReportView view = seniorReportService.getNullViewById(id);
			//容错代码 对象为空则不继续进行操作
			if (view==null) {
				id="";
				return;
			}
			//转换表单表头对象获取展现的html字符串
			StringBuffer buffer = new StringBuffer();
			int x = fillTitels(view.getTitles(),buffer)+1;
			this.getValueStack().set("reportView", view);
			//转换表单内容对象获取展现的html字符串
			StringBuffer bodybuffer = new StringBuffer("<tr>");
			int y = fillBody(view.getLeft(),bodybuffer,view.getItemValueMaps(),1);
			String body = bodybuffer.toString();
			//根据最终的占用行数统计来替换掉原来的临时占用行替换字符串
			for (int i = y; i > 0; i--) {
				body = body.replaceAll("TEMP_"+i+"_"+bodybuffer.hashCode(), (y-i+1)+"");
			}
			
			this.getValueStack().set("Body", body);
			//拼接上表单左上角的【项目】单元格 
			//其占用列 = 表单行标题层数 占用行=表单列标题层数+1（其本身占用一行）
			this.getValueStack().set("Title", "<tr><td rowspan ="+x+" colspan = "+y+" >"+view.getStartTitle()+"</td></tr>"+buffer);
			this.getValueStack().set("reportView",view);
		}
	}
	/**
	 * 获取导出模板【其存储文件名为表单id】
	 * @return 导出模板文件对象
	 */
	private File getTemplate(){
		String path = "WEB-INF" + File.separator + "classes" + File.separator
		+ "exportModel" + File.separator + "seniorreport";
		path+=File.separator+id+".xls";
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
		if(!StringUtil.isEmpty(id)){
			this.getValueStack().set("hasModel", getTemplate().exists());
		}else{
			this.getValueStack().set("hasModel", false);
		}
		if("config".equals(itemType)){
			this.getValueStack().set("link", "summary/seniorreportconfig_saveCfg.html");
			getValueStack().set("types", SeniorReportType.values());
		}else{
			this.getValueStack().set("link", "summary/seniorreportconfig_saveTemplate.html");
		}
		return "file";
	}
	
	/**
	 * 下载原有的配置模板
	 * @return
	 */
	public String downloadCfg() {
		try {
			SeniorReportConfigData data = seniorReportService.getReportById(id);
			getResponse().setCharacterEncoding( IConstants.FILE_ENCODE );
			getResponse().setContentType( "text/file" );
			getResponse().setHeader( "Content-Disposition",  DownloadFilenameUtil.fileDisposition(getRequest().getHeader("user-agent"), data.getReportName()+".config") );
			OutputStream out = getResponse().getOutputStream();
			String cont = URLEncoder.encode(data.getContent(), "utf-8");
			out.write(cont.getBytes());
//			out.write(data.getContent().getBytes());
			out.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 下载原有的导出模板
	 * @return
	 */
	public String downloadTemplate() {
		try {
			File temp = getTemplate();
			SeniorReportConfigData data = seniorReportService.getReportById(id);
			getResponse().setCharacterEncoding( IConstants.FILE_ENCODE );
			getResponse().setContentType( "application/vnd.ms-excel" );
			getResponse().setHeader( "Content-Disposition",  DownloadFilenameUtil.fileDisposition(getRequest().getHeader("user-agent"), data.getReportName()+".xls") );
			
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
	 * 上传配置模板
	 * @return
	 * @throws Exception 
	 */
	public String saveCfg() throws Exception{
		InputStream in = null;
		StringBuffer sbf = new StringBuffer();
		try {
			if(file == null){
				throw new RuleException("文件没有接收成功");
			}
			if(file.length()==0){
				throw new RuleException("数据文件内容为空");
			}
			in  = new FileInputStream(file);
			byte[] b = new byte[1024];
			int length = 0;
			while((length = in.read(b))!=-1){
				sbf.append(new String(b, 0, length,IConstants.FILE_ENCODE));
			}
			in.close();
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
		}
		String cont = URLDecoder.decode(sbf.toString(), "utf-8");
		config.setContent(cont);
		SeniorReportConfig cfg = config.getSeniorReportConfig();
		seniorReportService.add(cfg,type);
		getValueStack().set(DATA, getMessage());
		return "res";
	}
	
	/**
	 * 上传导出模板
	 * @return
	 */
	public String saveTemplate(){
		FileOutputStream out = null;
		InputStream in = null;
		try {
			checkFile();
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
	
	/**
	 * 检查文件
	 * @throws Exception
	 */
	private void checkFile(){
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
	
	public String list(){
		init();
		getValueStack().set("types", SeniorReportType.values());
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
		getValueStack().set("types", SeniorReportType.values());
		return "edit_report";
	}
	/**
	 * 修改报表
	 * @return
	 */
	public String modify_report(){
		config =seniorReportService.getReportById(id);
		SeniorReportConfig c= config.getSeniorReportConfig();
		config.setSql(c.getSql());
		config.setHistorySql(c.getHistorySql());
		getValueStack().set("types", SeniorReportType.values());
		return "edit_report";
	}
	/**
	 * 删除报表
	 * @return
	 */
	public String delete_report(){
		seniorReportService.remove(id);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 保存报表
	 * @return
	 */
	public String save_report(){
		try {
			seniorReportService.tryToExecSqL(config, item);
		} catch (Exception e) {
			this.setErrorMessage("语句配置有误！无法执行"+e.getMessage());
			this.getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(StringUtils.isEmpty(config.getReportId())){
			seniorReportService.add(config);
		}else{
			seniorReportService.modify(config);
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
		System.out.println(pid);
		itemType="column";
		getValueStack().set("viewTypes", ViewItemType.values());
		return "edit_item";
	}
	/**
	 * 修改列
	 * @return
	 */
	public String modify_column(){
		itemType="column";
		config=seniorReportService.getReportById(id);
		item=config.getSeniorReportConfig().findViewItemById(item.getItemId(), true);
		getValueStack().set("viewTypes", ViewItemType.values());
		return "edit_item";
	}
	/**
	 * 删除列
	 * @return
	 */
	public String remove_column(){
		itemType="column";
		config=seniorReportService.getReportById(id);
		seniorReportService.removeColumn(config,item.getItemId());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 保存列
	 * @return
	 */
	public String save_column(){
		config=seniorReportService.getReportById(id);
		try {
			seniorReportService.tryToExecSqL(config, item);
		} catch (Exception e) {
			this.setErrorMessage("语句配置有误！无法执行"+e.getMessage());
			this.getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(StringUtils.isEmpty(item.getItemId())){
			seniorReportService.addColumn(config,item,pid);
		}else{
			seniorReportService.modifyColumn(config,item);
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
		getValueStack().set("viewTypes", ViewItemType.values());
		return "edit_item";
	}
	/**
	 * 修改行
	 * @return
	 */
	public String modify_row(){
		itemType="row";
		config=seniorReportService.getReportById(id);
		item=config.getSeniorReportConfig().findViewItemById(item.getItemId(), false);
		getValueStack().set("viewTypes", ViewItemType.values());
		return "edit_item";
	}
	/**
	 * 删除行
	 * @return
	 */
	public String remove_row(){
		itemType="row";
		config=seniorReportService.getReportById(id);
		seniorReportService.removeRow(config,item.getItemId());
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 保存行
	 * @return
	 */
	public String save_row(){
		config=seniorReportService.getReportById(id);
		try {
			seniorReportService.tryToExecSqL(config, item);
		} catch (Exception e) {
			this.setErrorMessage("语句配置有误！无法执行"+e.getMessage());
			this.getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(StringUtils.isEmpty(item.getItemId())){
			seniorReportService.addRow(config,item,pid);
		}else{
			seniorReportService.modifyRow(config,item);
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
		seniorReportService.columnLeft(id,item);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 
	 * @return
	 */
	public String column_right(){
		seniorReportService.columnRight(id,item);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 
	 * @return
	 */
	public String row_up(){
		seniorReportService.rowUp(id,item);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 
	 * @return
	 */
	public String row_down(){
		seniorReportService.rowDown(id,item);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public SeniorReportConfigData getConfig() {
		return config;
	}

	public void setConfig(SeniorReportConfigData config) {
		this.config = config;
	}

	public ViewItem getItem() {
		return item;
	}

	public void setItem(ViewItem item) {
		this.item = item;
	}

	public void setSeniorReportService(ISeniorReportService seniorReportService) {
		this.seniorReportService = seniorReportService;
	}

	/**
	 * 返回
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * 设置
	 * @param pid 
	 */
	public void setPid(String pid) {
		this.pid = pid;
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
	/**
	 * 返回
	 */
	public SeniorReportType getType() {
		return type;
	}
	/**
	 * 设置
	 * @param type 
	 */
	public void setType(SeniorReportType type) {
		this.type = type;
	}
	
	
}
