package com.zfsoft.hrm.exportword.action;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.dataorigin.service.IDataOriginService;
import com.zfsoft.hrm.exportword.entity.ExportConfig;
import com.zfsoft.hrm.exportword.entity.ExportType;
import com.zfsoft.hrm.exportword.query.ExportConfigQuery;
import com.zfsoft.hrm.exportword.service.IExportConfigService;
import com.zfsoft.hrm.util.ExportMap;
import com.zfsoft.hrm.util.WordExportUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-27
 * @version V1.0.0
 */
public class ExportAction extends HrmAction{

	private static final long serialVersionUID = -3119199378269660309L;
	private String staffId;
	private ExportConfig exportConfig = new ExportConfig();
	private IExportConfigService exportConfigService;
	private IDataOriginService dataOriginService;
	private IDynaBeanService dynaBeanService;
	private PageList<ExportConfig> pageList = new PageList<ExportConfig>();;
	private ExportConfigQuery query = new ExportConfigQuery();
	
	public String page(){
		query.setOpen("Y");
		pageList = exportConfigService.getPageList(query);
		List<ExportType> typeList = exportConfigService.getTypeList(null);
		getValueStack().set("typeList", typeList);
		return "page";
	}
	
	public String managepage(){
		query.setOpen("Y");
		pageList = exportConfigService.getPageList(query);
		List<ExportType> typeList = exportConfigService.getTypeList(null);
		getValueStack().set("typeList", typeList);
		getSession().setAttribute("export_managepage", true);
		getValueStack().set("managepage", true);
		return "page";
	}
	
	public String choose(){
		query.setOpen("Y");
		pageList = exportConfigService.getPageList(query);
		List<ExportType> typeList = exportConfigService.getTypeList(null);
		getValueStack().set("typeList", typeList);
		return "choose";
	}
	
	public String export(){
		if((!Boolean.TRUE.equals(getSession().getAttribute("export_managepage")))||
				StringUtil.isEmpty(staffId)){
			staffId=getUser().getYhm();
		}
		exportConfig = exportConfigService.findConfigById(exportConfig.getId());
		ExportType type = exportConfigService.findTypeById(exportConfig.getType());
		String path =  ExportConfigAction.MODEL_DIR + File.separator + type.getDir()
			+ File.separator + exportConfig.getId() +".xml";
		path = Struts2Utils.getSession().getServletContext().getRealPath(path);
		File temp = new File(path);
		if(!temp.exists()){
			getValueStack().set("errorMessage", "导出模板不存在");
			return "prompt";
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		// 默认抓取
		if ("0".equals(exportConfig.getOrigin())) {
			Map<String,Object> info = new ExportMap();
			List<InfoClass> infoClasses=InfoClassCache.getInfoClasses();
			infoClasses.add(InfoClassCache.getOverallInfoClass());// 填充OVERALL信息类
			for (InfoClass infoClass : infoClasses) {
				DynaBeanQuery query=new DynaBeanQuery(infoClass);
				query.setHistory(true);
				String express = "gh='"+staffId+"'";
				query.setExpress(express);
				List<DynaBean> dynaBeanList=dynaBeanService.findList(query);
				if(dynaBeanList==null||dynaBeanList.size()==0)
					continue;
				if(infoClass.isMoreThanOne()){
					Integer num = 1;
					for (DynaBean dynaBean : dynaBeanList) {
						Map<String, String>  map = WordExportUtil.getValueMapForExport(dynaBean, num);
						info.putAll(map);
						num++;
					}
				}else{
					Map<String, String>  map = WordExportUtil.getValueMapForExport(dynaBeanList.get(0), null);
					info.putAll(map);
				}
			}
			data.put("info", info);
		// 自定义抓取
		} else {
			Map<String, Object> condition = new HashMap<String, Object>();
			Map<String, String> billIds = new HashMap<String, String>();
//			billIds.put(model.getSpBillConfigId(), model.getSpBillInstanceId());
			condition.put("ywId", exportConfig.getId());
			condition.put("gh", staffId);
			data.put("info", new ExportMap(dataOriginService.getOutPutData(condition, billIds)));
		}
		
		try {
			getResponse().setCharacterEncoding( IConstants.FILE_ENCODE );
			getResponse().setContentType( "application/msword" );
			getResponse().setHeader( "Content-Disposition",  DownloadFilenameUtil.
					fileDisposition(getRequest().getHeader("user-agent"), exportConfig.getName()+".doc") );
			OutputStream out = getResponse().getOutputStream();
			WordExportUtil.getInstance(ExportConfigAction.MODEL_DIR)
				.exportTable(out, type.getDir()+ File.separator + exportConfig.getId() +".xml", data);
		} catch (Exception e) {
			e.printStackTrace();
			getValueStack().set("errorMessage", e.getMessage());
			return "prompt";
		}
		return null;
	}

	public ExportConfig getExportConfig() {
		return exportConfig;
	}

	public void setExportConfig(ExportConfig exportConfig) {
		this.exportConfig = exportConfig;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public void setExportConfigService(IExportConfigService exportConfigService) {
		this.exportConfigService = exportConfigService;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	/**
	 * 返回
	 */
	public ExportConfigQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(ExportConfigQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 */
	public PageList<ExportConfig> getPageList() {
		return pageList;
	}

	/**
	 * @param dataOriginService the dataOriginService to set
	 */
	public void setDataOriginService(IDataOriginService dataOriginService) {
		this.dataOriginService = dataOriginService;
	}
	
}
