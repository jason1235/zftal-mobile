package com.zfsoft.hrm.baseinfo.snapshot.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyViewService;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotInfo;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotInfoQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotInfoService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.util.date.DateTimeUtil;

/**
 * 导出信息类表单
 * @author Administrator
 * @date 2014-04-16
 */
public class SnapshotInfoAction extends HrmAction {

    /**
     * 
     */
    private static final long serialVersionUID = 7604583759598438239L;
    
    private ICatalogService catalogService;
    private ISnapshotInfoService snapshotInfoService;
    private IInfoPropertyViewService viewService;
    private List<Catalog> infoList;
    private List<InfoPropertyView> configList = new ArrayList<InfoPropertyView>();
    private InfoClass defaultInfo;
    private String classId;
    private String showClassId;
    private String propertyId;
    private PageList<SnapshotInfo> pageList;
    private SnapshotInfoQuery query = new SnapshotInfoQuery(null);
    private InfoPropertyViewQuery infoPropertyViewQuery = new InfoPropertyViewQuery();
    private String hdnExpress;
    private Date snapTimeStart;
    private Date snapTimeEnd;
	private String dwm;
	private String gh;
	private String xm;

	/**
     * 所有信息类表单
     * @return 跳转对象
     * @throws Exception
     */
    public String allInfo() throws Exception {

        CatalogQuery query = new CatalogQuery();
        query.setType(IConstants.INFO_CATALOG_TYPE_DEFAULT);
        infoList = catalogService.getFullList(query);

        defaultInfo = InfoClassCache.getOverallInfoClass(IConstants.INFO_CATALOG_TYPE_DEFAULT);

        return "view";
    }
    
    /**
     * 取得信息类属性信息
     * @return
     * @throws Exception
     */
    public String property() throws Exception {
        
        // 取得信息类
        InfoClass infoClass = getInfoClass();
        //读取用户配置
        List<InfoProperty> viewAllowList = InfoPropertyViewCacheUtil.getViewList(infoClass.getGuid(), getUser().getYhm());
        
        //计算字段显示状态
        List<InfoProperty> list = infoClass.getViewables();
        for(InfoProperty p : list){
            InfoPropertyView view = new InfoPropertyView();
            view.setAllow(false);
            view.setPropertyId(p.getGuid());
            view.setPropertyName(p.getName());
            for(InfoProperty v : viewAllowList){
                if(p.getGuid().equals(v.getGuid())){
                    view.setAllow(true);
                }
            }
            configList.add(view);
        } 
        
        return "property";
    }
    
    /**
     * 取得快照表的数据
     * @return
     * @throws Exception
     */
    public String infoView() throws Exception {
        // 取得信息类
        InfoClass infoClass = getInfoClass();
        infoClass.setProperties(InfoPropertyViewCacheUtil.getViewList(infoClass.getGuid(), getUser().getYhm()));
        query.setClazz(infoClass);
        query.setExpress(hdnExpress);
        query.setParam("dwm", dwm);
        query.setParam("gh", gh);
        query.setParam("xm", xm);
        query.setSnapTimeStart(snapTimeStart);
        query.setSnapTimeEnd(snapTimeEnd);
        pageList = snapshotInfoService.getPagingList(query);
        return "info";
    }

    /**
     * 取得信息类
     * @return
     */
    private InfoClass getInfoClass() {
         // 取得信息类
        InfoClass infoClass = InfoClassCache.getInfoClass(classId);
        
        // 取不到信息类时，返回个人综合信息（只读）
        if(infoClass == null){
            infoClass = InfoClassCache.getOverallInfoClass();
        }
        
        return infoClass;
    }
    
    /**
     * 导出
     * @return
     * @throws Exception
     */
    public String export() throws Exception {
//    	query.setExpress(getExpress());
    	query.setPerPageSize(Integer.MAX_VALUE);
        infoView();
        return "export";
    }
    
    /**
     * 保存信息类属性
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        
        // 取得选中的项目
        String[] pId = propertyId.split(",");
        // 解析
        for(String id : pId) {
            InfoPropertyView view = new InfoPropertyView();
            view.setClassId(showClassId);
            view.setPropertyId(id.trim());
            view.setUsername(getUser().getYhm());
            configList.add(view);
        }
        if(configList.size() > 0) {
            viewService.save(configList);
        }else{
            infoPropertyViewQuery.setClassId(showClassId);
            viewService.deleteBatch(infoPropertyViewQuery);
        }
        getValueStack().set(DATA, getMessage());
        return "view";
    }
    
    /**
     * @param snapshotInfoService the snapshotInfoService to set
     */
    public void setSnapshotInfoService(ISnapshotInfoService snapshotInfoService) {
        this.snapshotInfoService = snapshotInfoService;
    }
    
    /**
     * @param catalogService the catalogService to set
     */
    public void setCatalogService(ICatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * @return the infoList
     */
    public List<Catalog> getInfoList() {
        return infoList;
    }

    /**
     * @return the defaultInfo
     */
    public InfoClass getDefaultInfo() {
        return defaultInfo;
    }

    /**
     * @return the classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * @return the configList
     */
    public List<InfoPropertyView> getConfigList() {
        return configList;
    }

    /**
     * @return the pageList
     */
    public PageList<SnapshotInfo> getPageList() {
        return pageList;
    }

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
	 * @return the query
	 */
	public SnapshotInfoQuery getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(SnapshotInfoQuery query) {
		this.query = query;
	}

	/**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @param viewService the viewService to set
     */
    public void setViewService(IInfoPropertyViewService viewService) {
        this.viewService = viewService;
    }

    /**
     * @return the showClassId
     */
    public String getShowClassId() {
        return showClassId;
    }

    /**
     * @param showClassId the showClassId to set
     */
    public void setShowClassId(String showClassId) {
        this.showClassId = showClassId;
    }
    
	/**
	 * @return the hdnExpress
	 */
	public String getHdnExpress() {
		return hdnExpress;
	}

	/**
	 * @param hdnExpress the hdnExpress to set
	 */
	public void setHdnExpress(String hdnExpress) {
		this.hdnExpress = hdnExpress;
	}
	
	public Date getSnapTimeStart() {
		return snapTimeStart;
	}

	public void setSnapTimeStart(Date snapTimeStart) {
		this.snapTimeStart = snapTimeStart;
	}

	public Date getSnapTimeEnd() {
		return snapTimeEnd;
	}

	public void setSnapTimeEnd(Date snapTimeEnd) {
		this.snapTimeEnd = snapTimeEnd;
	}
	
	public String getSnapTimeStartStr() {
		if(snapTimeStart==null){
			return null;
		}
		return DateTimeUtil.getFormatDate(snapTimeStart);
	}
	public String getSnapTimeEndStr() {
		if(snapTimeEnd==null){
			return null;
		}
		return DateTimeUtil.getFormatDate(snapTimeEnd);
	}
	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getDwm() {
		return dwm;
	}

	public void setDwm(String dwm) {
		this.dwm = dwm;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
}
