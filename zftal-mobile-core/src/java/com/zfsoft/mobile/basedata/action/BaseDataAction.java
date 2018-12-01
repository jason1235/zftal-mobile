package com.zfsoft.mobile.basedata.action;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javassist.expr.Instanceof;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.apache.log4j.Logger;

import com.common.Config;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.daointerface.ILoginDao;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.schedule.ScheduleControlService;
import com.zfsoft.hrm.schedule.util.QuartzTriggerUtil;
import com.zfsoft.mobile.basedata.entity.InfoClazzMenuEntity;
import com.zfsoft.mobile.basedata.entity.InfoClazzServiceEntity;
import com.zfsoft.mobile.basedata.entity.ProcedureEntity;
import com.zfsoft.mobile.basedata.entity.ViewPropertyEntity;
import com.zfsoft.mobile.basedata.service.IBaseDataService;
import com.zfsoft.mobile.common.enums.ExecuteCycleEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.webservices.cxf.service.impl.WSSerServiceImpl;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author Administrator
 *
 */
public class BaseDataAction extends HrmAction implements Job {

    /**
     * 
     */
	private static Logger logger = Logger.getLogger(BaseDataAction.class);
    private static final long serialVersionUID = 8278043594823104988L;
    private IBaseDataService baseDataService;
    private ScheduleControlService scheduleControlService;
    private PageList<DynaBean> pageList = new PageList<DynaBean>();
    private DynaBeanQuery query;
    private String classId;
    private String procedureId;
    private String catalogId;
    private ProcedureEntity model;
    private InfoClazzServiceEntity icsem;
    private ViewPropertyEntity  viewPropertyEntity;
    private int showMore = -1;
    private String asc;
    private String sortFieldName;
    private final static String PROCEDURE_REGULAR_NAME = "PR_";
    private boolean fixedWin = true;
    private ViewPropertyEntity vpModel;
    private InfoClazzMenuEntity menuModel;
    private ILoginDao loginDao;
    private IMobileCommonService mobileCommonService;
    private IInfoPropertyService infoPropertyService;
    
    public void setInfoPropertyService(IInfoPropertyService infoPropertyService) {
		this.infoPropertyService = infoPropertyService;
	}
    
    public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	/**
     * @return the baseDataService
     */
    public IBaseDataService getBaseDataService() {
        return baseDataService;
    }
    
    /**
     * @param baseDataService the baseDataService to set
     */
    public void setBaseDataService(IBaseDataService baseDataService) {
        this.baseDataService = baseDataService;
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
     * @return the procedureId
     */
    public String getProcedureId() {
        return procedureId;
    }

    /**
     * @param procedureId the procedureId to set
     */
    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    /**
     * @return the pageList
     */
    public PageList<DynaBean> getPageList() {
        return pageList;
    }

    /**
     * @param pageList the pageList to set
     */
    public void setPageList(PageList<DynaBean> pageList) {
        this.pageList = pageList;
    }

    /**
     * @return the query
     */
    public DynaBeanQuery getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(DynaBeanQuery query) {
        this.query = query;
    }

    /**
     * @return the catalogId
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * @param catalogId the catalogId to set
     */
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * @return the model
     */
    public ProcedureEntity getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(ProcedureEntity model) {
        this.model = model;
    }

    /**
     * @return the showMore
     */
    public int getShowMore() {
        return showMore;
    }

    /**
     * @param showMore the showMore to set
     */
    public void setShowMore(int showMore) {
        this.showMore = showMore;
    }

    /**
     * @return the asc
     */
    public String getAsc() {
        return asc;
    }

    /**
     * @param asc the asc to set
     */
    public void setAsc(String asc) {
        this.asc = asc;
    }

    /**
     * @return the sortFieldName
     */
    public String getSortFieldName() {
        return sortFieldName;
    }

    /**
     * @param sortFieldName the sortFieldName to set
     */
    public void setSortFieldName(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }

    /**
     * @return the scheduleControlService
     */
    public ScheduleControlService getScheduleControlService() {
        return scheduleControlService;
    }

    /**
     * @param scheduleControlService the scheduleControlService to set
     */
    public void setScheduleControlService(
            ScheduleControlService scheduleControlService) {
        this.scheduleControlService = scheduleControlService;
    }

    /**
     * @return the fixedWin
     */
    public boolean isFixedWin() {
        return fixedWin;
    }

    /**
     * @param fixedWin the fixedWin to set
     */
    public void setFixedWin(boolean fixedWin) {
        this.fixedWin = fixedWin;
    }

    /**
     * @return the vpModel
     */
    public ViewPropertyEntity getVpModel() {
        return vpModel;
    }

    /**
     * @param vpModel the vpModel to set
     */
    public void setVpModel(ViewPropertyEntity vpModel) {
        this.vpModel = vpModel;
    }

    /**
     * @return the icsem
     */
    public InfoClazzServiceEntity getIcsem() {
        return icsem;
    }

    /**
     * @param icsem the icsem to set
     */
    public void setIcsem(InfoClazzServiceEntity icsem) {
        this.icsem = icsem;
    }

    /**
     * 同步数据
     */
    public String synBaseData() {
        InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
        if (query == null) {
            query = new DynaBeanQuery();
        }
        
        ProcedureEntity p = baseDataService.getProceduresById("", classId);
        
        if (p != null) {
            procedureId = p.getProcedureId();
        } else {
            procedureId = "";
        }
        
        List<InfoProperty> viewables = new ArrayList<InfoProperty>();
        List<InfoProperty> conditions = new ArrayList<InfoProperty>();
        List<InfoProperty> tempview = clazz.getViewables();
        StringBuilder sbMoreConds = new StringBuilder();
        Map<String,Object> valuesMap = new HashMap<String, Object>();
        
        for (InfoProperty infoProperty : tempview) {
            if (!Type.PHOTO.equals(infoProperty.getFieldType()) && !Type.IMAGE.equals(infoProperty.getFieldType()) && !Type.FILE.equals(infoProperty.getFieldType())) {
                viewables.add(infoProperty);
                conditions.add(infoProperty);
                
                String fieldName = "t." + infoProperty.getFieldName();
                if (infoProperty.getVirtual()) {
                    fieldName = infoProperty.getDisplayFormula("t");
                }
                String[] values1 = getRequest().getParameterValues(infoProperty.getFieldName());
                if (
            				Type.DATE.equalsIgnoreCase(infoProperty.getFieldType()) || 
            				Type.MONTH.equalsIgnoreCase(infoProperty.getFieldType()) || 
            				Type.YEAR.equalsIgnoreCase(infoProperty.getFieldType())
    				){
                    String[] values = getRequest().getParameterValues(infoProperty.getFieldName());
                    if (values == null || (values != null && values.length > 0 && StringUtil.isEmpty(values[0]))) {
                        continue;
                    }
                    
                    valuesMap.put(infoProperty.getFieldName(), values);
                    if (StringUtils.isNotEmpty(values[0])) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append(">= to_date('");
                        sbMoreConds.append(values[0]);
                        sbMoreConds.append("','");
                        sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
                        sbMoreConds.append("')");
                    }
                    if (StringUtils.isNotEmpty(values[1])) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("<= to_date('");
                        sbMoreConds.append(values[1]);
                        sbMoreConds.append("','");
                        sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
                        sbMoreConds.append("')");
                    }
                } else {
                    String value = getRequest().getParameter(infoProperty.getFieldName());
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    valuesMap.put(infoProperty.getFieldName(), value);
                    if (
                		infoProperty.getTypeInfo() != null &&(
                				Type.CODE.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
                				Type.SIGLE_SEL.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
                				)
        				) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("='");
                        sbMoreConds.append(value);
                        sbMoreConds.append("'");
                    } else if (
                    		infoProperty.getTypeInfo() != null &&(
                    				Type.NUMBER.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
                    											  )
                			  ) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("=");
                        sbMoreConds.append(value);
                        sbMoreConds.append("");
                    } else {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append(" like '%");
                        sbMoreConds.append(value.replaceAll("'", "''"));
                        sbMoreConds.append("%'");
                    }
                }
            }
        }
        
        String orderStr = "";
        if (!StringUtil.isEmpty(sortFieldName)) {
            String fieldName = "t." + sortFieldName;
            if ("up".equals(asc)) {
                orderStr = fieldName;
            } else {
                orderStr = fieldName + " desc";
            }
        } else {
            orderStr = "t.bh";
        }
        
        String express = " (1 = 1) ";
        if (sbMoreConds.length() > 0) {
            express += sbMoreConds.toString();
        }
        
        query.setClazz(clazz);
        query.setExpress(express);
        query.setOrderStr(orderStr);
        pageList = baseDataService.getSynchronizedBaseData(query);
        
        getValueStack().set("viewables", viewables);
        getValueStack().set("conditions", conditions);
        getValueStack().set("valuesMap", valuesMap);
        
        return "synBaseData";
    }
    
    /**
     * 客户端数据展示
     * @throws UnsupportedEncodingException 
     */
    public String clientBaseData(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	String queryData = request.getParameter("queryData");
    	/*try {
			queryData = queryData == null ? queryData :new String(queryData.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("-------------clientBaseData------------"+e);
			e.printStackTrace();
		}*/
        InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
        if (query == null) {
            query = new DynaBeanQuery();
        }
        
        ProcedureEntity p = baseDataService.getProceduresById("", classId);
        String isLike = p.getIsOrNotLike();
        
        if (p != null) {
            procedureId = p.getProcedureId();
        } else {
            procedureId = "";
        }
        
        List<ViewPropertyEntity> lvpro = baseDataService.findViewPro(classId);
        Map<String, ViewPropertyEntity> pros = new HashMap<String, ViewPropertyEntity>();
        for (ViewPropertyEntity pro : lvpro) {
            pros.put(pro.getPropertyId(), pro);
        }
        
        //List<InfoProperty> viewables = new ArrayList<InfoProperty>();
        List<InfoProperty> titleViewables = new ArrayList<InfoProperty>();
        List<InfoProperty> commonViewables = new ArrayList<InfoProperty>();
        List<InfoProperty> conditions = new ArrayList<InfoProperty>();
        List<InfoProperty> tempview = clazz.getViewables();
        StringBuilder sbMoreConds = new StringBuilder();
        Map<String,Object> valuesMap = new HashMap<String, Object>();
        //Map<String,Object> descMap = new HashMap<String, Object>();
        
        for (InfoProperty infoProperty : tempview) {
        	String fieldName = "t." + infoProperty.getFieldName();
            if (!Type.PHOTO.equals(infoProperty.getFieldType()) && !Type.IMAGE.equals(infoProperty.getFieldType()) && !Type.FILE.equals(infoProperty.getFieldType())) {
                if (pros.isEmpty()) {
                    //viewables.add(infoProperty);
                    conditions.add(infoProperty);
                } else {
                    if (pros.containsKey(infoProperty.getGuid())) {
                        if ("b".equals(pros.get(infoProperty.getGuid()).getLviewProperty())) {
                            //viewables.add(infoProperty);
                        	titleViewables.add(infoProperty);
                        }
                        if ("c".equals(pros.get(infoProperty.getGuid()).getLviewProperty())) {
                            //viewables.add(infoProperty);
                        	commonViewables.add(infoProperty);
                        }
                        if ("y".equals(pros.get(infoProperty.getGuid()).getConditionStatus())) {
                            conditions.add(infoProperty);
                            if(!StringUtil.isEmpty(queryData)){
                            	if(isLike.equals("y")){
                            		if (infoProperty.getVirtual()) {
                            			fieldName = infoProperty.getDisplayFormula("t");
                            		}
                            			sbMoreConds.append("or ");
                            			sbMoreConds.append(fieldName);
                            			sbMoreConds.append(" like '%");
                            			sbMoreConds.append(queryData);
                            			sbMoreConds.append("%' ");
                                        
                            	}else{
                            		if (infoProperty.getVirtual()) {
                            			fieldName = infoProperty.getDisplayFormula("t");
                            		}
                            		sbMoreConds.append("or ");
                            		sbMoreConds.append(fieldName);
                            		sbMoreConds.append(" = '");
                            		sbMoreConds.append(queryData);
                            		sbMoreConds.append("' ");
                            	}
                            }
                            
                        }
                    }
                }
                
                if (infoProperty.getVirtual()) {
                    fieldName = infoProperty.getDisplayFormula("t");
                }
                if (infoProperty != null && infoProperty.getTypeInfo() != null && (
                		Type.DATE.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
                		Type.MONTH.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
                		Type.YEAR.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
                		)) {
                    String[] values = getRequest().getParameterValues(infoProperty.getFieldName());
                    if (values == null) {
                        continue;
                    }
                    
                    valuesMap.put(infoProperty.getFieldName(), values);
                    if (StringUtils.isNotEmpty(values[0])) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append(">= to_date('");
                        sbMoreConds.append(values[0]);
                        sbMoreConds.append("','");
                        sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
                        sbMoreConds.append("')");
                    }
                    if (StringUtils.isNotEmpty(values[1])) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("<= to_date('");
                        sbMoreConds.append(values[1]);
                        sbMoreConds.append("','");
                        sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
                        sbMoreConds.append("')");
                    }
                } else {
                    String value = getRequest().getParameter(infoProperty.getFieldName());
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    valuesMap.put(infoProperty.getFieldName(), value);
                    if (
                    		infoProperty.getTypeInfo() != null &&(
	                    		Type.CODE.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
	                    		Type.SIGLE_SEL.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
                    		)
                		) {
                        sbMoreConds.append(" or ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("='");
                        sbMoreConds.append(value);
                        sbMoreConds.append("'");
                    } else if (
                    		infoProperty.getTypeInfo() != null &&(
                    				Type.NUMBER.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
                    											  )
                    		  ) {
                        sbMoreConds.append(" or ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("=");
                        sbMoreConds.append(value);
                        sbMoreConds.append("");
                    } else {
                        sbMoreConds.append(" or ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append(" like '%");
                        sbMoreConds.append(value.replaceAll("'", "''"));
                        sbMoreConds.append("%'");
                    }
                }
            }
        }
        
        String orderStr = "";
       /* if (!StringUtil.isEmpty(sortFieldName)) {
            String fieldName = "t." + sortFieldName;
            if ("up".equals(asc)) {
                orderStr = fieldName;
            } else {
                orderStr = fieldName + " desc";
            }
        } else {
            orderStr = "t.bh";
        }*/
        if(!StringUtils.isEmpty(clazz.getPxsx())){
        	String pxsxList[] = clazz.getPxsx().split(",");
        	String pxfsList[] = clazz.getPxfs().split(",");
        	for (int i = 0; i < pxsxList.length; i++) {
        		orderStr += pxsxList[i] + " " + pxfsList[i];
        		if(i != pxsxList.length -1){
        			orderStr += ",";
        		}
			}
        	/*orderStr = clazz.getPxsx() + " " +  
        				(StringUtils.isEmpty(clazz.getPxfs()) ? "" : clazz.getPxfs());*/
        	orderStr = orderStr.trim();
        	orderStr.substring(0, orderStr.length()-2);
        }
        
        String express =  sbMoreConds.length() > 0 ? " (1 = 1) and (" : " (1 = 1) " ;
        if (sbMoreConds.length() > 3) {
            express += sbMoreConds.toString().substring(3, sbMoreConds.length()) + ")";
        }
        String tableName = clazz.getIdentityName();
        String allTable = Config.getString("tables", "m_yqnc-JXJHKCXXB");
        String zymc = null;
        String xxlall = StringUtil.isEmpty(p.getXxlall()) || p.getXxlall().equals("n") ? "n" : "y";
        if(tableName.equals("JXJHKCXXB") && StringUtil.isEmpty(queryData)){
        	String zgh = getUser().getYhm();
        	zymc = baseDataService.getZymcFromJw(zgh);
        }
        if(!StringUtils.isEmpty(type) && !allTable.contains(tableName) && !xxlall.equals("y")){
        	//System.out.println("------tableName-------"+tableName);
        	//System.out.println("------allTable-------"+allTable);
        	express += " and t.gh = '" + getUser().getYhm() + "'";
        }
        if(!StringUtils.isEmpty(type) && !StringUtil.isEmpty(zymc)){
        	express += " and t.ZYMC = '" + zymc + "'";
        }
        if(tableName.equals("M_YQNC")){
        	LoginModel model = new LoginModel();
        	model.setYhm(getUser().getYhm());
        	Map<String, String> map = loginDao.getTeaxx(model);
        	String rylx = map.get("RYLX");
        	String[] roleArray = rylx.split(",");
        	boolean containRole = false;
        	for(String roledm : roleArray){
        		//System.out.println("---------roledm---------"+roledm);
        		if(roledm.equals("master"))
        			containRole = true;
        	}
        	if(!containRole)
        		express += "and 1=0";
        }
        
        
        query.setClazz(clazz);
        query.setExpress(express);
        query.setOrderStr(orderStr);
        query.setPerPageSize(20);
        pageList = baseDataService.getSynchronizedBaseData(query);
        //descMap.put("fbsj", clazz.getPropertyByName("fbsj").getDescription());
        //descMap.put("fbsj1", clazz.getPropertyByName("fbsj").getDescription());
        
        //如果是搜索按钮，则局部刷新组件
        String submit = request.getParameter("submit");
        if(!StringUtil.isEmpty(submit)){
        	Map<String, Object> data = new HashMap<String, Object>();
            data.put("success", true);
            data.put("viewList", pageList);
            data.put("titleViewables", titleViewables);
            data.put("commonViewables", commonViewables);
            getValueStack().set(DATA, data);
    		return DATA;
        }
        
        
        String jsonStr =  JSONUtils.obj2json(pageList);
        getValueStack().set("viewList", JSONUtils.obj2json(pageList));
        //System.out.println(JSONUtils.obj2json(pageList));
        getValueStack().set("titleViewables", JSONUtils.obj2json(titleViewables));
        getValueStack().set("commonViewables", JSONUtils.obj2json(commonViewables));
        //getValueStack().set("viewables", viewables);
        getValueStack().set("conditions", conditions);
        StringBuilder sb = new StringBuilder();
        String conString = "";
        for(InfoProperty property : conditions){
        	sb.append(property.getName()).append("、");
        }
        if(sb.length() > 10)
        	conString = sb.substring(0, 10) + "...";
        else{
        	String queryString = sb.toString();
        	if(queryString.length() > 0){
        		conString = queryString.substring(0, queryString.length()-1);
        	}
        }
        conString = "请根据" + conString +"查询";
        getValueStack().set("conString", conString);
        
        
        getValueStack().set("valuesMap", valuesMap);
        //getValueStack().set("descMap", JSONUtils.obj2json(descMap));
        getValueStack().set("pageList", pageList);
        getValueStack().set("clazz", clazz);
        
        logger.error(express);
        
        return "clientBaseData";
    }
    
    /**
     * 信息类加载更多数据
     * @throws UnsupportedEncodingException 
     */
    public String getMore(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type)){
    		getValueStack().set("type", type);
    	}
    	String queryData = request.getParameter("queryData");
    	
    	try {
			queryData = queryData == null ? queryData :new String(queryData.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			System.out.println("-------------getMore------------"+e);
			e.printStackTrace();
		}
        InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
        if (query == null) {
            query = new DynaBeanQuery();
        }
        
        ProcedureEntity p = baseDataService.getProceduresById("", classId);
        String isLike = p.getIsOrNotLike();
        
        if (p != null) {
            procedureId = p.getProcedureId();
        } else {
            procedureId = "";
        }
        
        List<ViewPropertyEntity> lvpro = baseDataService.findViewPro(classId);
        Map<String, ViewPropertyEntity> pros = new HashMap<String, ViewPropertyEntity>();
        for (ViewPropertyEntity pro : lvpro) {
            pros.put(pro.getPropertyId(), pro);
        }
        
        //List<InfoProperty> viewables = new ArrayList<InfoProperty>();
        List<InfoProperty> titleViewables = new ArrayList<InfoProperty>();
        List<InfoProperty> commonViewables = new ArrayList<InfoProperty>();
        List<InfoProperty> conditions = new ArrayList<InfoProperty>();
        List<InfoProperty> tempview = clazz.getViewables();
        StringBuilder sbMoreConds = new StringBuilder();
        Map<String,Object> valuesMap = new HashMap<String, Object>();
        //Map<String,Object> descMap = new HashMap<String, Object>();
        
        for (InfoProperty infoProperty : tempview) {
        	String fieldName = "t." + infoProperty.getFieldName();
            if (!Type.PHOTO.equals(infoProperty.getFieldType()) && !Type.IMAGE.equals(infoProperty.getFieldType()) && !Type.FILE.equals(infoProperty.getFieldType())) {
                if (pros.isEmpty()) {
                    //viewables.add(infoProperty);
                    conditions.add(infoProperty);
                } else {
                    if (pros.containsKey(infoProperty.getGuid())) {
                        if ("b".equals(pros.get(infoProperty.getGuid()).getLviewProperty())) {
                            //viewables.add(infoProperty);
                        	titleViewables.add(infoProperty);
                        }
                        if ("c".equals(pros.get(infoProperty.getGuid()).getLviewProperty())) {
                            //viewables.add(infoProperty);
                        	commonViewables.add(infoProperty);
                        }
                        if ("y".equals(pros.get(infoProperty.getGuid()).getConditionStatus())) {
                            conditions.add(infoProperty);
                            if(!StringUtil.isEmpty(queryData)){
                            	if(isLike.equals("y")){
                            		if (infoProperty.getVirtual()) {
                            			fieldName = infoProperty.getDisplayFormula("t");
                            		}
                            			sbMoreConds.append("or ");
                            			sbMoreConds.append(fieldName);
                            			sbMoreConds.append(" like '%");
                            			sbMoreConds.append(queryData);
                            			sbMoreConds.append("%' ");
                                        
                            	}else{
                            		if (infoProperty.getVirtual()) {
                            			fieldName = infoProperty.getDisplayFormula("t");
                            		}
                            		sbMoreConds.append("or ");
                            		sbMoreConds.append(fieldName);
                            		sbMoreConds.append(" = '");
                            		sbMoreConds.append(queryData);
                            		sbMoreConds.append("' ");
                            	}
                            }
                            
                        }
                    }
                }
                
                if (infoProperty.getVirtual()) {
                    fieldName = infoProperty.getDisplayFormula("t");
                }
                if (
                		infoProperty.getTypeInfo() != null &&(
                				Type.DATE.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
                				Type.MONTH.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
                				Type.YEAR.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
        				)
    				) {
                    String[] values = getRequest().getParameterValues(infoProperty.getFieldName());
                    if (values == null) {
                        continue;
                    }
                    
                    valuesMap.put(infoProperty.getFieldName(), values);
                    if (StringUtils.isNotEmpty(values[0])) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append(">= to_date('");
                        sbMoreConds.append(values[0]);
                        sbMoreConds.append("','");
                        sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
                        sbMoreConds.append("')");
                    }
                    if (StringUtils.isNotEmpty(values[1])) {
                        sbMoreConds.append(" and ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("<= to_date('");
                        sbMoreConds.append(values[1]);
                        sbMoreConds.append("','");
                        sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
                        sbMoreConds.append("')");
                    }
                } else {
                    String value = getRequest().getParameter(infoProperty.getFieldName());
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    valuesMap.put(infoProperty.getFieldName(), value);
                    if (
                    		infoProperty.getTypeInfo() != null &&(
                    				Type.CODE.equalsIgnoreCase(infoProperty.getTypeInfo().getName()) || 
                    				Type.SIGLE_SEL.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
            				)
        				) {
                        sbMoreConds.append(" or ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("='");
                        sbMoreConds.append(value);
                        sbMoreConds.append("'");
                    } else if (
                    		infoProperty.getTypeInfo() != null &&(
                    				Type.NUMBER.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
                    											  )
                    		) {
                        sbMoreConds.append(" or ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append("=");
                        sbMoreConds.append(value);
                        sbMoreConds.append("");
                    } else {
                        sbMoreConds.append(" or ");
                        sbMoreConds.append(fieldName);
                        sbMoreConds.append(" like '%");
                        sbMoreConds.append(value.replaceAll("'", "''"));
                        sbMoreConds.append("%'");
                    }
                }
            }
        }
        
        String orderStr = "";
        /*if (!StringUtil.isEmpty(sortFieldName)) {
            String fieldName = "t." + sortFieldName;
            if ("up".equals(asc)) {
                orderStr = fieldName;
            } else {
                orderStr = fieldName + " desc";
            }
        } else {
            orderStr = "t.bh";
        }*/
        if(!StringUtils.isEmpty(clazz.getPxsx())){
        	String pxsxList[] = clazz.getPxsx().split(",");
        	String pxfsList[] = clazz.getPxfs().split(",");
        	for (int i = 0; i < pxsxList.length; i++) {
        		orderStr += pxsxList[i] + " " + pxfsList[i];
        		if(i != pxsxList.length -1){
        			orderStr += ",";
        		}
			}
        	orderStr = orderStr.trim();
        	orderStr.substring(0, orderStr.length()-2);
        }
        
        String express =  sbMoreConds.length() > 0 ? " (1 = 1) and (" : " (1 = 1) " ;
        if (sbMoreConds.length() > 3) {
            express += sbMoreConds.toString().substring(3, sbMoreConds.length()) + ")";
        }
        String tableName = clazz.getIdentityName();
        String allTable = Config.getString("tables", "m_yqnc,JXJHKCXXB");
        String zymc = null;
        String xxlall = StringUtil.isEmpty(p.getXxlall()) || p.getXxlall().equals("n") ? "n" : "y";
        if(tableName.equals("JXJHKCXXB") && StringUtil.isEmpty(queryData)){
        	String zgh = getUser().getYhm();
        	zymc = baseDataService.getZymcFromJw(zgh);
        }
        if(!StringUtils.isEmpty(type) && !allTable.contains(tableName) && !xxlall.equals("y")){
        	//System.out.println("------tableName-------"+tableName);
        	//System.out.println("------allTable-------"+allTable);
        	express += " and t.gh = '" + getUser().getYhm() + "'";
        }
        if(!StringUtils.isEmpty(type) && !StringUtil.isEmpty(zymc)){
        	express += " and t.ZYMC = '" + zymc + "'";
        }
        if(tableName.equals("M_YQNC")){
        	LoginModel model = new LoginModel();
        	model.setYhm(getUser().getYhm());
        	Map<String, String> map = loginDao.getTeaxx(model);
        	String rylx = map.get("RYLX");
        	String[] roleArray = rylx.split(",");
        	boolean containRole = false;
        	for(String roledm : roleArray){
//        		System.out.println("---------roledm---------"+roledm);
        		if(roledm.equals("master"))
        			containRole = true;
        	}
        	if(!containRole)
        		express += "and 1=0";
        }
        
        
        query.setClazz(clazz);
        query.setExpress(express);
        query.setOrderStr(orderStr);
        query.setPerPageSize(20);
        pageList = baseDataService.getSynchronizedBaseData(query);
        //descMap.put("fbsj", clazz.getPropertyByName("fbsj").getDescription());
        //descMap.put("fbsj1", clazz.getPropertyByName("fbsj").getDescription());
        
        //getValueStack().set("newviewList", JSONUtils.obj2json(pageList));
        //getValueStack().set("newtitleViewables", JSONUtils.obj2json(titleViewables));
        //getValueStack().set("newcommonViewables", JSONUtils.obj2json(commonViewables));
        //getValueStack().set("viewables", viewables);
        //getValueStack().set("conditions", conditions);
        //getValueStack().set("valuesMap", valuesMap);
        //getValueStack().set("descMap", JSONUtils.obj2json(descMap));
        //getValueStack().set("pageList", pageList);
        getValueStack().set("clazz", clazz);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("viewList", pageList);
        
        
        
        data.put("titleViewables", titleViewables);
        data.put("commonViewables", commonViewables);
        data.put("clazz", clazz);
        getValueStack().set(DATA, data);
		return DATA;
    }
    
    /**
     * 客户端详情数据验证
     */
    public String clientBaseDetailCheck(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String classId = request.getParameter("classId");
    	List<ViewPropertyEntity> lvpro = baseDataService.findViewPro(classId);
        ArrayList<String> cdbmList = new ArrayList<String>();
         for (ViewPropertyEntity entity : lvpro) {
        	for(String temp : entity.getDviewPropertyList()){
        		
        		boolean isHas = false;
	       		 for(String cdbmTemp : cdbmList){
	       			 if(cdbmTemp.equals(temp))
	       				 isHas = true;
	       		 }
	       		 if(!isHas)
	       			 cdbmList.add(temp);
        		
        		//if(!cdbmList.contains(temp))
        		//	cdbmList.add(temp);
        	}
        }
         
        if(cdbmList.size() == 0)
        	this.setErrorMessage("没有设置可以显示的详情数据!");
        else
        	this.setSuccessMessage("检测成功");
        getValueStack().set(DATA, getMessage());
		return DATA;
    }
    
    /**
     * 客户端详情数据展示
     * @return
     */
    public String clientBaseDetailData() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type)){
    		getValueStack().set("type", type);
    	}
    	String classId = request.getParameter("classId");
    	InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
    	String globalid = request.getParameter("globalid");
        if (query == null) {
            query = new DynaBeanQuery();
        }
        
        String express = " t.globalid = " + "'" + globalid + "'";
        String orderStr = "";
        query.setClazz(clazz);
        query.setExpress(express);
        query.setOrderStr(orderStr);
        query.setPerPageSize(10);
        pageList = baseDataService.getSynchronizedBaseData(query);
        
        
        List<ViewPropertyEntity> lvpro = baseDataService.findViewPro(classId);
        
        if(pageList == null || lvpro == null){
        	return "clientBaseDetailData";
        }
        String title = "";
        if (pageList.size() > 0) {
        	title = pageList.get(0).getValueString("Title");
        }
        
        ArrayList<String> cdbmList = new ArrayList<String>();
         for (ViewPropertyEntity entity : lvpro) {
        	for(String temp : entity.getDviewPropertyList()){
        		
        		boolean isHas = false;
	       		 for(String cdbmTemp : cdbmList){
	       			 if(cdbmTemp.equals(temp))
	       				 isHas = true;
	       		 }
	       		 if(!isHas){
	       			InfoClazzMenuEntity menuTempModel = new InfoClazzMenuEntity();
	       			menuTempModel.setClassCdbm(temp);
	       			ArrayList<InfoClazzMenuEntity> menuList = baseDataService.getMenuList(menuTempModel);
	       			if(menuList != null && menuList.size() > 0)
	       				cdbmList.add(temp);
	       				
	       		 }
        		
        		//if(!cdbmList.contains(temp))
        		//	cdbmList.add(temp);
        	}
        }
         ArrayList<String> cdbmOtherList = new ArrayList<String>();
         for(String cdbm : cdbmList){
        	 InfoClazzMenuEntity menuTempModel = new InfoClazzMenuEntity();
         	 menuTempModel.setClassCdbm(cdbm);
        	 InfoClazzMenuEntity menu = baseDataService.getMenuList(menuTempModel).get(0);
        	 String firstMenuId = menu.getFirstMenuId();
        	 String firstCdbm = null;
        	 InfoClazzMenuEntity firstmenu = new InfoClazzMenuEntity();
        	 menuTempModel = new InfoClazzMenuEntity();
        	 if(firstMenuId != null){
        		 menuTempModel.setClassCdid(firstMenuId);
        		 firstmenu = baseDataService.getMenuList(menuTempModel).get(0);
        		 firstCdbm = firstmenu.getClassCdbm();
        	 }
        	 if(!StringUtil.isEmpty(firstCdbm)){
        		 boolean isHas = false;
        		 for(String cdbmTemp : cdbmList){
        			 if(cdbmTemp.equals(firstCdbm))
        				 isHas = true;
        		 }
        		 if(!isHas)
        			 cdbmOtherList.add(firstCdbm);
        	 }
     	}
        cdbmList.addAll(cdbmOtherList);
        String cdbmString =  cdbmList.toString().substring(1, cdbmList.toString().length()-1);
        
        InfoClazzMenuEntity model = new InfoClazzMenuEntity();
        model.setInfoClassId(classId);
        //model.setClassCdbm(cdbmString);
        ArrayList<InfoClazzMenuEntity> firstMenuList = baseDataService.getFirstMenuList(model);
        ArrayList<InfoClazzMenuEntity> secondMenuList = baseDataService.getSecondMenuList(model);
        ArrayList<InfoClazzMenuEntity> firstOtherMenuList = new ArrayList<InfoClazzMenuEntity>();
        ArrayList<InfoClazzMenuEntity> secondOtherMenuList = new ArrayList<InfoClazzMenuEntity>();
        for(InfoClazzMenuEntity menu : firstMenuList){
        	boolean isHas = false;
        	for(String cdbm : cdbmList){
        		if(cdbm.equals(menu.getClassCdbm()))
   				 isHas = true;
        	}
        	if(!isHas)
        		firstOtherMenuList.add(menu);
        }
        for(InfoClazzMenuEntity menu : secondMenuList){
        	boolean isHas = false;
        	for(String cdbm : cdbmList){
        		if(cdbm.equals(menu.getClassCdbm()))
   				 isHas = true;
        	}
        	if(!isHas)
        		secondOtherMenuList.add(menu);
        }
        firstMenuList.removeAll(firstOtherMenuList);
        secondMenuList.removeAll(secondOtherMenuList);
        //ArrayList<InfoClazzMenuEntity> firstOtherMenuList = new ArrayList<InfoClazzMenuEntity>();
        //ArrayList<InfoClazzMenuEntity> secondOtherMenuList = new ArrayList<InfoClazzMenuEntity>();
        /*for(InfoClazzMenuEntity entity : secondMenuList){
        	String firstCdid = entity.getFirstMenuId();
        	InfoClazzMenuEntity menuTempModel = new InfoClazzMenuEntity();
        	menuTempModel.setClassCdid(firstCdid);
        	InfoClazzMenuEntity firstmenu = baseDataService.getMenuList(menuTempModel).get(0);
        	boolean isOrNot = false;
        	for(String cdbm : cdbmList){
        		if(cdbm.equals(firstmenu.getClassCdbm()))
        			isOrNot = true;
        	}
        	if(!isOrNot)
        		firstMenuList.add(baseDataService.getMenuList(menuTempModel).get(0));
        }*/
		
			for(ViewPropertyEntity entity : lvpro){
	        	String zdmc = baseDataService.findZdmc(entity.getPropertyId());
	        	String sxmc = baseDataService.findSxmc(entity.getPropertyId());
	        	for(String cdbm : entity.getDviewPropertyList()){
	        		for(InfoClazzMenuEntity menu : firstMenuList){
	        			if(menu.getClassCdbm().equals(cdbm)){
	        				Map<String, Object> sxdata = new TreeMap<String, Object>();
	        				Object data = pageList.get(0).getValue(zdmc);
	        				if(data instanceof Date){
	        					String dataString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(data);
	        					sxdata.put(sxmc, dataString);
	        				}else{
	        					sxdata.put(sxmc, data);
	        				}
	        				menu.getSxData().add(sxdata);
	        			}
	        		}
	        		for(InfoClazzMenuEntity menu : secondMenuList){
	        			if(menu.getClassCdbm().equals(cdbm)){
	        				Map<String, Object> sxdata = new HashMap<String, Object>();
	        				Object data = pageList.get(0).getValue(zdmc);
	        				if(data instanceof Date){
	        					String dataString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(data);
	        					sxdata.put(sxmc, dataString);
	        				}else{
	        					sxdata.put(sxmc, data);
	        				}
	        				menu.getSxData().add(sxdata);
	        			}
	        		}
	        	}
	        }
        
        
        
        
        
        
        /*ArrayList<InfoClazzMenuEntity> firstOtherList = new ArrayList<InfoClazzMenuEntity>();
        ArrayList<InfoClazzMenuEntity> secondOtherList = new ArrayList<InfoClazzMenuEntity>();
        */
        
        
        
        
        //getValueStack().set("viewList", JSONUtils.obj2json(pageList));
        
		
        getValueStack().set("firstMenuList", firstMenuList);
        getValueStack().set("secondMenuList", secondMenuList);
        //getValueStack().set("lvproList", JSONUtils.obj2json(lvpro));
        getValueStack().set("clazz", clazz);
        getValueStack().set("title", title);
    	return "clientBaseDetailData";
    }
    
    /**
     * 获取存储过程信息
     * @return
     */
    public String setProcedure() {
        List<Catalog> baseCatalog = baseDataService.getBaseCatalog();
        if (baseCatalog == null || baseCatalog.size() == 0) {
            return "setProcedure";
        }
        
        if (StringUtils.isEmpty(catalogId)) {
            catalogId = baseCatalog.get(0).getGuid();
        }
        List<ProcedureEntity> procedures = baseDataService.getProcedures(catalogId);
        getValueStack().set("catalogs", baseCatalog);
        getValueStack().set("procedures", procedures);
        return "setProcedure";
    }
    
    /**
     * 同步
     * @return
     */
    public String doSynchronized() {
        
        Map<String, String> retMessage = baseDataService.executeSynchronized(procedureId);
        getValueStack().set(DATA, retMessage);
        return DATA;
    }
    
    /**
     * 定时
     * @return
     */
    public String regular() {
        List<String> dbprocedures = baseDataService.getDBProcedures();
        if(!StringUtils.isEmpty(model.getProcedureId())) {
            model = baseDataService.getProceduresById(model.getProcedureId(), "");
        }
        
        getValueStack().set("dbprocedures", dbprocedures);
        getValueStack().set("executeCycs", ExecuteCycleEnum.values());
        return "regular";
    }

    /**
     * 定时
     * @return
     * @throws Exception 
     */
    public String doRegular() throws Exception {
        
        Trigger trigger = null;
        boolean hasRegular = false;
        String beginDayOfYear = TimeUtil.current("yyyy-01-01");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df.parse(beginDayOfYear);
        String TriggerName = PROCEDURE_REGULAR_NAME + model.getProcedureId();
        
        // 执行周期（0每月1每季度2每半年3每年）
        // 每月
        if (ExecuteCycleEnum.CYCLE_MONTH.equals(model.getExecuteCyc())) {
            hasRegular = true;
            trigger = QuartzTriggerUtil.getMyMonthTrigger(TriggerName, 1, startDate, null);
            // 每季度
        } else if (ExecuteCycleEnum.CYCLE_QUARTER.equals(model.getExecuteCyc())) {
            hasRegular = true;
            trigger = QuartzTriggerUtil.getMyMonthTrigger(TriggerName, 3, startDate, null);
            // 每半年
        } else if (ExecuteCycleEnum.CYCLE_HALFOFYEAR.equals(model.getExecuteCyc())) {
            hasRegular = true;
            trigger = QuartzTriggerUtil.getMyMonthTrigger(TriggerName, 6, startDate, null);
            // 每年
        } else if (ExecuteCycleEnum.CYCLE_YEAR.equals(model.getExecuteCyc())) {
            hasRegular = true;
            trigger = QuartzTriggerUtil.getMyYearTrigger(TriggerName, 1, startDate, null);
        } else {
            hasRegular = false;
        }
        // 测试用代码 start
//        hasRegular = true;
//        trigger = QuartzTriggerUtil.getMyMinuteTrigger(TriggerName, 1, new Date(), null);
        // 测试用代码 end
        JobDataMap map = trigger.getJobDataMap();
        map.put("ProcedureId", model.getProcedureId());
        trigger.setJobDataMap(map);
        scheduleControlService.removeTrigger(TriggerName);
        scheduleControlService.addTriggerToJob(TriggerName, trigger, BaseDataAction.class);
        if(!hasRegular || "off".equals(model.getRegularSwitch())){
            scheduleControlService.pauseTrigger(TriggerName);
        }
        
        if (StringUtils.isEmpty(model.getProcedureId())) {
            baseDataService.insertProcedure(model);
        } else {
            baseDataService.modifyProcedure(model);
        }
        getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    /**
     * 信息类设置
     * @return
     */
    public String setInfoClazz() {
        List<Catalog> baseCatalog = baseDataService.getBaseCatalog();
        if (baseCatalog == null || baseCatalog.size() == 0) {
            return "setInfoClazz";
        }
        
        if (StringUtils.isEmpty(catalogId)) {
            catalogId = baseCatalog.get(0).getGuid();
        }
        
        if (StringUtils.isEmpty(classId)) {
            List<InfoClass> icList = baseCatalog.get(0).getClasses();
            
            if (icList != null && icList.size() > 0) {
                classId = icList.get(0).getGuid();
            }
        }
        
        InfoClass infoClass = null;
        if (!StringUtils.isEmpty(classId)) {
            infoClass = baseDataService.getFullInfoClass(classId);
            
        }
        
        if (infoClass == null) {
            getValueStack().set("infoClass", null);
            getValueStack().set("isOrNot", "0");
        } else {
        	if(baseDataService.getProceduresById("", classId) == null){
            	ProcedureEntity procedureModel = new ProcedureEntity();
            	procedureModel.setClassId(classId);
            	procedureModel.setIsOrNotLike("y");
            	baseDataService.insertProcedure(procedureModel);
            }
            getValueStack().set("infoClass", infoClass);
            String isOrNot = infoClass.getCatalog().getName().equals("个人档案") ? "1" : "0";
            getValueStack().set("isOrNot", isOrNot);
            List<ViewPropertyEntity> lvpro = baseDataService.refreshViewPro(classId);
            Map<String, ViewPropertyEntity> mvpro = new HashMap<String, ViewPropertyEntity>();
            for (ViewPropertyEntity vp : lvpro) {
                mvpro.put(vp.getPropertyId(), vp);
            }
            
            getValueStack().set("vpro", mvpro);
            
            InfoClazzMenuEntity menuTempModel = new InfoClazzMenuEntity();
        	menuTempModel.setInfoClassId(classId);
        	ArrayList<InfoClazzMenuEntity> menuList = baseDataService.getMenuList(menuTempModel);
            getValueStack().set("menuList", menuList);
            
            
            
            String isOrNotLike = baseDataService.getProceduresById("", classId).getIsOrNotLike();
            String xxlall  = baseDataService.getProceduresById("", classId).getXxlall();
            getValueStack().set("isOrNotLike", isOrNotLike);
            getValueStack().set("xxlall", xxlall);
        }
        
        getValueStack().set("catalogs", baseCatalog);
        
        return "setInfoClazz";
    }
    
    /**
     * 信息类设置
     * @return
     */
    public String setInfoClazzNew() {
        List<Catalog> baseCatalog = baseDataService.getBaseCatalog();
        if (baseCatalog == null || baseCatalog.size() == 0) {
//        	return "setInfoClazz";
            return "setInfoClazzNew";
        }
        
        if (StringUtils.isEmpty(catalogId)) {
            catalogId = baseCatalog.get(0).getGuid();
        }
        
        if (StringUtils.isEmpty(classId)) {
            List<InfoClass> icList = baseCatalog.get(0).getClasses();
            
            if (icList != null && icList.size() > 0) {
                classId = icList.get(0).getGuid();
            }
        }
        
        InfoClass infoClass = null;
        if (!StringUtils.isEmpty(classId)) {
            infoClass = baseDataService.getFullInfoClass(classId);
            
        }
        
        if (infoClass == null) {
            getValueStack().set("infoClass", null);
            getValueStack().set("isOrNot", "0");
        } else {
        	if(baseDataService.getProceduresById("", classId) == null){
            	ProcedureEntity procedureModel = new ProcedureEntity();
            	procedureModel.setClassId(classId);
            	procedureModel.setIsOrNotLike("y");
            	baseDataService.insertProcedure(procedureModel);
            }
            getValueStack().set("infoClass", infoClass);
            String isOrNot = infoClass.getCatalog().getName().equals("个人档案") ? "1" : "0";
            getValueStack().set("isOrNot", isOrNot);
            List<ViewPropertyEntity> lvpro = baseDataService.refreshViewPro(classId);
            Map<String, ViewPropertyEntity> mvpro = new HashMap<String, ViewPropertyEntity>();
            for (ViewPropertyEntity vp : lvpro) {
                mvpro.put(vp.getPropertyId(), vp);
            }
            
            getValueStack().set("vpro", mvpro);
            
            InfoClazzMenuEntity menuTempModel = new InfoClazzMenuEntity();
        	menuTempModel.setInfoClassId(classId);
        	ArrayList<InfoClazzMenuEntity> menuList = baseDataService.getMenuList(menuTempModel);
            getValueStack().set("menuList", menuList);
            StringBuilder sb = new StringBuilder();
            for(InfoClazzMenuEntity menuEntity : menuList){
            	sb.append(menuEntity.getMenuName());
            }
            int footWidth = sb.length()*20+930;
            footWidth = footWidth > 1090 ? footWidth : 1090;
            getValueStack().set("footWidth", footWidth);
            
            String isOrNotLike = baseDataService.getProceduresById("", classId).getIsOrNotLike();
            String xxlall  = baseDataService.getProceduresById("", classId).getXxlall();
            getValueStack().set("isOrNotLike", isOrNotLike);
            getValueStack().set("xxlall", xxlall);
        }
        
        getValueStack().set("catalogs", baseCatalog);
        
        return "setInfoClazzNew";
    }
    
    /**
     * 修改信息类属性显示方式
     */
    public String changeShow(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String infoClassID = request.getParameter("infoClassID");
    	String propertyID = request.getParameter("propertyID");
    	String lviewProperty = request.getParameter("lviewProperty");
    	String conditionStatus = request.getParameter("conditionStatus");
    	String dviewProperty = request.getParameter("dviewProperty") == null ? "" : request.getParameter("dviewProperty");
    	String xxlmhcx = request.getParameter("xxlmhcx");
    	if(StringUtil.isEmpty(xxlmhcx)){
    		ViewPropertyEntity propertyEntity = new ViewPropertyEntity();
    		propertyEntity.setClassId(infoClassID);
    		propertyEntity.setPropertyId(propertyID);
    		propertyEntity.setLviewProperty(lviewProperty);
    		propertyEntity.setConditionStatus(conditionStatus);
    		propertyEntity.setDviewProperty(dviewProperty);
    		baseDataService.modifyViewPro(propertyEntity);
    		this.setSuccessMessage("修改属性显示方式成功！");
    		getValueStack().set(DATA, getMessage());
    		return DATA;
    	}else{
    		ProcedureEntity procedureModel = new ProcedureEntity();
    		procedureModel.setClassId(infoClassID);
    		procedureModel.setIsOrNotLike(xxlmhcx);
    		baseDataService.modifyProcedure(procedureModel);
    		this.setSuccessMessage("成功修改是否模糊查询！");
    		getValueStack().set(DATA, getMessage());
    		return DATA;
    	}
    }
    
    public String changeAll(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String infoClassID = request.getParameter("infoClassID");
    	String xxlall = request.getParameter("xxlall");
    	ProcedureEntity procedureModel = new ProcedureEntity();
		procedureModel.setClassId(infoClassID);
		procedureModel.setXxlall(xxlall);
		baseDataService.modifyProcedure(procedureModel);
		this.setSuccessMessage("成功修改是否模糊查询！");
		getValueStack().set(DATA, getMessage());
		return DATA;
		
    }
    
    /**
     * 增加显示属性
     * @return
     */
    public String modifyViewPro() {
        baseDataService.modifyViewPro(vpModel);
        getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    public String isPublishPicture(){
    	icsem = baseDataService.getPublishService(classId);
    	Map<String, Object> data = new HashMap<String, Object>();
    	if (icsem != null) {
            data.put("success", true);
        }else{
        	data.put("success", false);
        }
    	getValueStack().set(DATA, data);
    	return DATA;
    }
    
    /**
     * 发布服务
     * @return
     */
    public String publishService() {
    	InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
    	getValueStack().set("infoClassName", clazz.getName());
        icsem = baseDataService.getPublishService(classId);
        String imageHtml;
        if (icsem == null) {
            imageHtml = ImageTagHtml.getImageHtml("fwtbid", Type.IMAGE, 1024, 150, 150, "", true);
        } else {
            imageHtml = ImageTagHtml.getImageHtml("fwtbid", Type.IMAGE, 1024, 150, 150, icsem.getFwtbid(), true);
        }
        getValueStack().set("imageHtml", imageHtml);
        return "publishService";
    }
    
    /**
     * 信息类发布成数字档案logo图片上传
     * @return
     */
    public String publishPicture(){
    	model = baseDataService.getProceduresById("",classId);
    	String imageHtml = null;
    	if (model == null || (model != null && model.getDataid() == null)) {
            imageHtml = ImageTagHtml.getImageHtml("dataid", Type.IMAGE, 1024, 150, 150, "", true);
        }
    	if (model != null && model.getDataid() != null){
            imageHtml = ImageTagHtml.getImageHtml("dataid", Type.IMAGE, 1024, 150, 150, model.getDataid(), true);
        }
    	getValueStack().set("imageHtml", imageHtml);
        return "publishPicture";
    }
    
    /**
     * 保存信息类发布成数字档案图标
     * @return
     */
    public String savePicture(){
    	String classId = getRequest().getParameter("classId");
    	ProcedureEntity entity = baseDataService.getProceduresById("",classId);
    	model.setClassId(classId);
    	String dataid = getRequest().getParameter("dataid");
    	model.setDataid(dataid);
    	String datadz = mobileCommonService.getMinUploadImagePath(dataid);
    	model.setDatadz(datadz);
        if (entity == null) {
        	baseDataService.insertPicture(model);
        } else {
            baseDataService.modifyPicture(model);
        }
        this.setSuccessMessage("上传个人档案logo图标成功！");
        
        getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    
    /**
     * 增加信息类菜单
     * @return
     */
    public String addMenu(){
    	InfoClass infoClass = null;
        if (!StringUtils.isEmpty(classId)) {
            infoClass = baseDataService.getFullInfoClass(classId);
        }
        getValueStack().set("infoClass", infoClass);
        
        InfoClazzMenuEntity model = new InfoClazzMenuEntity();
        model.setInfoClassId(classId);
        ArrayList<InfoClazzMenuEntity> firstMenuList = baseDataService.getFirstMenuList(model);
        ArrayList<InfoClazzMenuEntity> secondMenuList = baseDataService.getSecondMenuList(model);
        getValueStack().set("firstMenuList", firstMenuList);
        getValueStack().set("secondMenuList", secondMenuList);
		return "addMenu";
	}
    
    /**
     * 跳转到添加信息类菜单页面
     * @return
     */
    public String addMenuItem(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String xxlid = request.getParameter("xxlid");
    	//getValueStack().set("xxlid", xxlid);
    	String yjcdId = request.getParameter("yjcdId");
    	menuModel = new InfoClazzMenuEntity();
    	menuModel.setInfoClassId(xxlid);
    	if(StringUtil.isEmpty(yjcdId))
    		return "addMenuItem";
    	else{
    		menuModel.setFirstMenuId(yjcdId);
    		//InfoClazzMenuEntity menuCondition = new InfoClazzMenuEntity();
    		//menuCondition.setInfoClassId(xxlid);
    		//menuCondition.setFirstMenuId(yjcdId);
    		//getValueStack().set("yjcdId", yjcdId);
    		return "addMenuItem";
    	}
    		
    	
    }
    /**
     * 编辑信息类菜单
     */
    public String editMenuItem(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String cdId = request.getParameter("cdId");
    	InfoClazzMenuEntity menuTempModel = new InfoClazzMenuEntity();
    	menuTempModel.setClassCdid(cdId);
    	menuModel = baseDataService.getMenuList(menuTempModel).get(0);
    	return "addMenuItem";
    }
    
    /**
     * 删除信息类菜单
     */
    public String deleteMenuItem(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String cdId = request.getParameter("cdId");
    	//String xxlid = request.getParameter("xxlid");
    	baseDataService.deleteMenuItem(cdId);
    	Map<String,String> map = new HashMap<String, String>();
    	map.put("cdid", cdId);
    	//map.put("xxlid", xxlid);
    	baseDataService.deleteMenuProperty(map);
    	this.setSuccessMessage("成功删除菜单！");
		getValueStack().set(DATA, getMessage());
		return DATA;
    }
    
    /**
     * 保存信息类菜单分类信息
     */
    public String saveMenuItem(){
    	if(StringUtil.isEmpty(menuModel.getMenuName())){
    		this.setErrorMessage("菜单名称不能为空!");
    		getValueStack().set(DATA, getMessage());
    		return DATA;
    	}
    	if(StringUtil.isEmpty(menuModel.getClassCdid()))
    		baseDataService.insertMenu(menuModel);
		else
			baseDataService.updateMenu(menuModel);
	    
	    this.setSuccessMessage("成功插入数据！");
		getValueStack().set(DATA, getMessage());
		return DATA;
    }
    
    /**
     * 保存服务
     * @return
     */
    public String savePublish() {
        icsem.setXxlid(classId);
        icsem.setFwtbid(getRequest().getParameter("fwtbid"));
        if (StringUtils.isEmpty(icsem.getGuid())) {
            baseDataService.insertPublish(icsem);
        } else {
            baseDataService.modifyPublish(icsem);
        }
        
        getValueStack().set(DATA, getMessage());
        return DATA;
    }

    /**
     * 定时
     * @return
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        baseDataService = (IBaseDataService)SpringHolder.getBean("baseDataService");
        JobDataMap map = context.getTrigger().getJobDataMap();
        baseDataService.executeSynchronized(map.getString("ProcedureId"));
    }

	public void setMenuModel(InfoClazzMenuEntity menuModel) {
		this.menuModel = menuModel;
	}

	public InfoClazzMenuEntity getMenuModel() {
		return menuModel;
	}

	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public ILoginDao getLoginDao() {
		return loginDao;
	}

	public void setViewPropertyEntity(ViewPropertyEntity viewPropertyEntity) {
		this.viewPropertyEntity = viewPropertyEntity;
	}

	public ViewPropertyEntity getViewPropertyEntity() {
		return viewPropertyEntity;
	}

}
