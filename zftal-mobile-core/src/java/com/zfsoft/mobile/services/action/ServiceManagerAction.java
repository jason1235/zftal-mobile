package com.zfsoft.mobile.services.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.userStatictisEntity;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.common.enums.FwbmEnum;
import com.zfsoft.mobile.common.enums.ProductEnum;
import com.zfsoft.mobile.common.enums.ServiceSourceEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.entity.VisitsServiceEntity;
import com.zfsoft.mobile.services.entity.fwbmVisitEntity;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.util.base.StringUtil;

public class ServiceManagerAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8375700429926574273L;

	private String op;
	
	private IServiceManagerService serviceManagerService;
	
	private IBusinessService businessService;
	
	private ServiceManager model = new ServiceManager();
	
	private ServiceManagerQuery query = new ServiceManagerQuery();
	private VisitsServiceEntity visitsQuery = new VisitsServiceEntity();
	
	private INewsService newsService;
	

	private String[] id;
	
	private IMobileCommonService mobileCommonService;
	
	
	public VisitsServiceEntity getVisitsQuery() {
		return visitsQuery;
	}

	public void setVisitsQuery(VisitsServiceEntity visitsQuery) {
		this.visitsQuery = visitsQuery;
	}

	public INewsService getNewsService() {
		return newsService;
	}
	
	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}
	
	
	public String visitsServiceStatistic(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//以下代码：统计页面首次进入时，默认显示当前日期前一个月的统计数据
		Date date = new Date();
    	Calendar endcal = Calendar.getInstance();
    	endcal.setTime(date);
    	Calendar startcal = Calendar.getInstance();
    	startcal.setTime(date);
    	startcal.add(Calendar.MONTH, -1);
    	Date startDate = startcal.getTime();
    	Date endDate = endcal.getTime();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String enddateTemp = StringUtil.isEmpty(visitsQuery.getEndDate()) ? 
    			formatter.format(endDate) : visitsQuery.getEndDate();
        String startdateTemp = StringUtil.isEmpty(visitsQuery.getStartDate()) ? 
        		formatter.format(startDate) : visitsQuery.getStartDate();
		
		//获取统计访问量，包括累计访问量和日均访问量
		//visitsQuery.setToPage(query.getToPage());
		visitsQuery.setPerPageSize(10);
		visitsQuery.setStartDate(startdateTemp);
		visitsQuery.setEndDate(enddateTemp);
		PageList<VisitsServiceEntity> statisticList = serviceManagerService.getAllVSStatis(visitsQuery);
		getValueStack().set("visitsQuery", visitsQuery);
		getValueStack().set("statisticList", statisticList);
		int size = visitsQuery.getPerPageSize()*(visitsQuery.getToPage()-1);
		getValueStack().set("total", size);
		
		//获取排行前10的访问服务
		VisitsServiceEntity topQuery = new VisitsServiceEntity();
		topQuery.setToPage(1);
		topQuery.setPerPageSize(10);
		topQuery.setStartDate(startdateTemp);
		topQuery.setEndDate(enddateTemp);
		topQuery.setSchoolCode(visitsQuery.getSchoolCode());
		topQuery.setFwmc(visitsQuery.getFwmc());
		PageList<VisitsServiceEntity> topList = serviceManagerService.getAllVSStatis(topQuery);
		getValueStack().set("topList", JSONUtils.obj2json(topList));
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long to = df.parse(enddateTemp).getTime();
		    long from = df.parse(startdateTemp).getTime();
		    long dayBetween = (to - from) / (1000 * 60 * 60 * 24)+1;
		    getValueStack().set("dayBetween", dayBetween);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String isPortal = Config.getString("isPortal");
		getValueStack().set("isPortal", isPortal);
		
		return "visitsServiceStatistic";
	}
	
	/**
	 * 获取各个模块访问统计
	 * @return
	 */
	public String getVisitsByFwbm(){
		try {
			
			HttpServletRequest request = ServletActionContext.getRequest();
			Date date = new Date();
	    	Calendar endcal = Calendar.getInstance();
	    	endcal.setTime(date);
	    	Calendar startcal = Calendar.getInstance();
	    	startcal.setTime(date);
	    	startcal.add(Calendar.MONTH, -1);
	    	
	    	Date startDate = startcal.getTime();
	    	Date endDate = endcal.getTime();
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    	String enddateTemp = StringUtil.isEmpty(request.getParameter("enddate")) ? 
	    			formatter.format(endDate) : request.getParameter("enddate");
	        String startdateTemp = StringUtil.isEmpty(request.getParameter("startdate")) ? 
	        		formatter.format(startDate) : request.getParameter("startdate");
	    	
    		List<List<String>> actualTotalList = new ArrayList<List<String>>();
			List<AppServiceEntity> fwbmList = serviceManagerService.getAllFwbm();
			List<String> fwbmmcList = new ArrayList<String>();
			List<String> actualList;
			List<String> dayList = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			for(AppServiceEntity fwbmEntity : fwbmList){
				fwbmmcList.add(fwbmEntity.getFwmc());
				fwbmVisitEntity entity = new fwbmVisitEntity();
				entity.setFwbm(fwbmEntity.getFwbm());
				
		    	entity.setStartdate(startdateTemp);
		    	entity.setEnddate(enddateTemp);
		    	List<fwbmVisitEntity> visitsList = serviceManagerService.getVisitsByFwbm(entity);
		    	Map<String, String> map = new HashMap<String, String>();  
				  for(fwbmVisitEntity visit : visitsList){  
		  		   if(visit==null){  
		  		    continue;  
		  		   }  
		  		   map.put(visit.getDatetime(), visit.getVisits());  
				  } 
				actualList = new ArrayList<String>();
				dayList= new ArrayList<String>();
		  		Date enddate = df.parse(entity.getEnddate());
		  		Date tempdate = (new SimpleDateFormat("yyyy-MM-dd")).parse(entity.getStartdate());
		  		int countInstalls = 0;
		  		while(tempdate.getTime() <= enddate.getTime()){
		  			String actualInstalls = "";
		  			fwbmVisitEntity  cumulatetemp = new fwbmVisitEntity();
		  			String actualDate = df.format(tempdate);
		  			if(map.containsKey(actualDate)){
		  				String aaa = map.get(actualDate);
		  				actualInstalls = map.get(actualDate);
		  				countInstalls += Integer.parseInt(map.get(actualDate));
		  			}else{
		  				actualInstalls = "0";
		  			}
		  			cumulatetemp.setVisits(Integer.toString(countInstalls));
		  			Calendar cal = Calendar.getInstance();
		  			cal.setTime(tempdate);
		  			cumulatetemp.setDatetime(Integer.toString(cal.get(Calendar.DATE)));
		  			dayList.add(Integer.toString(cal.get(Calendar.DATE)));
		  			actualList.add(actualInstalls);
		  			cal.add(Calendar.DATE, 1);
		  			tempdate = cal.getTime();
		  		}
		  		actualTotalList.add(actualList);
			}
	  		
	  		getValueStack().set("fwbmmcList", JSONUtils.obj2json(fwbmmcList));
	  		getValueStack().set("actualTotalList", JSONUtils.obj2json(actualTotalList));
	  		getValueStack().set("dayJson", JSONUtils.obj2json(dayList));
	  		getValueStack().set("startdate", df.format((new SimpleDateFormat("yyyy-MM-dd")).parse(startdateTemp)));
	  		getValueStack().set("enddate", df.format((new SimpleDateFormat("yyyy-MM-dd")).parse(enddateTemp)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "fwbmVisits";
		}
	
	/*public String getReVisitsByFwbm(){
		try {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String queryFwbm = StringUtil.isEmpty(request.getParameter("fwbm")) ? FwbmEnum.FWBM_TZGG.getKey() : request.getParameter("fwbm");
		getValueStack().set("queryFwbm", queryFwbm);
		fwbmVisitEntity entity = new fwbmVisitEntity();
		entity.setFwbm(queryFwbm);
		
		Date date = new Date();
    	Calendar endcal = Calendar.getInstance();
    	endcal.setTime(date);
    	Calendar startcal = Calendar.getInstance();
    	startcal.setTime(date);
    	startcal.add(Calendar.MONTH, -1);
    	
    	Date startDate = startcal.getTime();
    	Date endDate = endcal.getTime();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String enddateTemp = StringUtil.isEmpty(request.getParameter("enddate")) ? 
    			formatter.format(endDate) : request.getParameter("enddate");
        String startdateTemp = StringUtil.isEmpty(request.getParameter("startdate")) ? 
        		formatter.format(startDate) : request.getParameter("startdate");
    	
    	String enddateTemp = StringUtil.isEmpty(request.getParameter("enddate")) ? 
    							DateFormat.getDateInstance().format(endcal.getTime()) : request.getParameter("enddate");
    	String startdateTemp = StringUtil.isEmpty(request.getParameter("startdate")) ? 
    							DateFormat.getDateInstance().format(startcal.getTime()) : request.getParameter("startdate");
    	
    	entity.setStartdate(startdateTemp);
    	entity.setEnddate(enddateTemp);
		
		
		//entity.setStartdate(request.getParameter("startdate"));
		//entity.setEnddate(request.getParameter("enddate"));
		List<fwbmVisitEntity> visitsList = serviceManagerService.getVisitsByFwbm(entity);
		Map<String, String> map = new HashMap<String, String>();  
		  for(fwbmVisitEntity visit : visitsList){  
  		   if(visit==null){  
  		    continue;  
  		   }  
  		   map.put(visit.getDatetime(), visit.getVisits());  
		  } 
	    List<String> actualList = new ArrayList<String>();
  		List<String> cumulateList = new ArrayList<String>();
  		List<String> dayList = new ArrayList<String>();
  		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  		Date enddate = df.parse(entity.getEnddate());
  		Date tempdate = (new SimpleDateFormat("yyyy-MM-dd")).parse(entity.getStartdate());
  		
  		int countInstalls = 0;
  		while(tempdate.getTime() <= enddate.getTime()){
  			String actualInstalls = "";
  			fwbmVisitEntity  cumulatetemp = new fwbmVisitEntity();
  			String actualDate = df.format(tempdate);
  			if(map.containsKey(actualDate)){
  				String aaa = map.get(actualDate);
  				actualInstalls = map.get(actualDate);
  				countInstalls += Integer.parseInt(map.get(actualDate));
  			}else{
  				actualInstalls = "0";
  			}
  			cumulatetemp.setVisits(Integer.toString(countInstalls));
  			Calendar cal = Calendar.getInstance();
  			cal.setTime(tempdate);
  			cumulatetemp.setDatetime(Integer.toString(cal.get(Calendar.DATE)));
  			dayList.add(Integer.toString(cal.get(Calendar.DATE)));
  			actualList.add(actualInstalls);
  			cumulateList.add(Integer.toString(countInstalls));
  			cal.add(Calendar.DATE, 1);
  			tempdate = cal.getTime();
  		}
  		getValueStack().set("fwbmList", FwbmEnum.values());
  		getValueStack().set("actualjson", JSONUtils.obj2json(actualList));
  		getValueStack().set("cumulatejson", JSONUtils.obj2json(cumulateList));
  		getValueStack().set("dayJson", JSONUtils.obj2json(dayList));
  		getValueStack().set("startdate", df.format((new SimpleDateFormat("yyyy-MM-dd")).parse(entity.getStartdate())));
  		getValueStack().set("enddate", df.format((new SimpleDateFormat("yyyy-MM-dd")).parse(entity.getEnddate())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fwbmVisits";
	}*/

	public String list(){
		PageList<ServiceManager> list = new PageList<ServiceManager>();
		query.setClassDeleted("0");
		list = serviceManagerService.getList(query);
		this.getValueStack().set("list", list);
		getValueStack().set("ProductList", ProductEnum.values());
		BusinessQuery businessQuery = new BusinessQuery();
		businessQuery.setPerPageSize(10000);
		//判断是否存在移动后台的服务,即procode编码为999的业务系统
		BusinessQuery ydhtQuery = new BusinessQuery();
		ydhtQuery.setProcode("999");
		businessService.getYdht(ydhtQuery);
		
		getValueStack().set("YewuList", businessService.getList(businessQuery));
		getValueStack().set("ServiceTypeList", ServiceTypeEnum.values());
		getValueStack().set("ServiceSourceList", ServiceSourceEnum.values());
		return "list";
	}
	
    /**
     * getProcode
     * @return
     */
    public String getProcode() {
        String procodebm = businessService.getProcodeBm(model.getClassSsywxt());
        
        getValueStack().set(DATA, procodebm);
        return DATA;
    }
	
	public String toAdd(){
		getValueStack().set("fwbmList", FwbmEnum.values());
		BusinessQuery businessQuery = new BusinessQuery();
		businessQuery.setPerPageSize(10000);
		getValueStack().set("YewuList", businessService.getList(businessQuery));
		getValueStack().set("ProductList", ProductEnum.values());
		getValueStack().set("ServiceTypeList", ServiceTypeEnum.values());
		String imageHtml = ImageTagHtml.getImageHtml("fwtbid", Type.IMAGE, 256, 90, 90, model.getClassFwtbid(), true);
        getValueStack().set("imageHtml", imageHtml);
        
        String fileHtml = ImageTagHtml.getFileHtml("fileid", Type.FILE,"apk", 200, 180, 150,model.getFileId());
        getValueStack().set("fileHtml", fileHtml);
		op = "add";
		
		ArrayList<String> iconList = FileUntils.getImagesPathList(null,null);
		getValueStack().set("iconList", iconList);
		ArrayList<String> fileNameList = FileUntils.getImagesNameList(null,null);
		getValueStack().set("fileNameList", fileNameList);
		
		List<AppServiceEntity> AppFwList = serviceManagerService.getFwdyxt();
		getValueStack().set("AppFwList", JSONArray.fromObject(AppFwList));
		
		return "edit";
	}
	
	private String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	
	public String getImages(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String keyWord = request.getParameter("keyWord");
		String imageName = request.getParameter("imageName");
		ArrayList<String> iconList = FileUntils.getImagesPathList(null,keyWord);
		//getValueStack().set("iconList", JSONArray.fromObject(iconList));
		ArrayList<String> fileNameList = FileUntils.getImagesNameList(null,keyWord);
		//getValueStack().set("fileNameList", JSONArray.fromObject(fileNameList));
		
		StringBuilder imageHtml = new StringBuilder();
		if(iconList != null && iconList.size() > 0){
			for (int i = 0; i < iconList.size(); i++) {
				imageHtml.append("<li class='col-md-3 col-sm-3 col-xs-3 iconclass' style='white-space:nowrap;text-overflow:ellipsis;overflow:hidden;'>");
				imageHtml.append("<img style='height: 50px;width: 50px;' src='"+getImageHost()+iconList.get(i)+"'><h5>");
				imageHtml.append("<input type='radio' value='"+iconList.get(i)+"' name='icoPath'");
				if(!StringUtil.isEmpty(imageName) && imageName.equals(fileNameList.get(i))){
					imageHtml.append("checked='checked'");
				}
				imageHtml.append("/>"+fileNameList.get(i)+"</h5></li>");
			}
			
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("imageHtml", imageHtml.toString());
        getValueStack().set(DATA, data);
		return DATA;
	}
	
	public String toModify(){
		getValueStack().set("fwbmList", FwbmEnum.values());
		query.setClassDeleted("0");
		PageList<ServiceManager> list = serviceManagerService.getList(query);
		model = list.get(0);
		BusinessQuery businessQuery = new BusinessQuery();
		businessQuery.setPerPageSize(10000);
		getValueStack().set("YewuList", businessService.getList(businessQuery));
		getValueStack().set("ProductList", ProductEnum.values());
		getValueStack().set("ServiceTypeList", ServiceTypeEnum.values());
		String imageHtml = ImageTagHtml.getImageHtml("fwtbid", Type.IMAGE, 256, 90, 90, model.getClassFwtbid(), true);
        getValueStack().set("imageHtml", imageHtml);
        
        String fileHtml = ImageTagHtml.getFileHtml("fileid", Type.FILE,"apk", 200, 150, 150,model.getFileId());
        getValueStack().set("fileHtml", fileHtml);
		op = "modify";
		String imageName = model.getClassFwtbdz();
		if(!StringUtil.isEmpty(imageName)){
			imageName = imageName.substring(imageName.lastIndexOf("/")+1, imageName.length());
		}
		getValueStack().set("imageName", imageName);
		ArrayList<String> iconList = FileUntils.getImagesPathList(imageName,null);
		getValueStack().set("iconList", iconList);
		ArrayList<String> fileNameList = FileUntils.getImagesNameList(imageName,null);
		getValueStack().set("fileNameList", fileNameList);
		List<AppServiceEntity> AppFwList = serviceManagerService.getFwdyxt();
		getValueStack().set("AppFwList", JSONArray.fromObject(AppFwList));
		return "edit";
	}
	
	public String save() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		//下面这段逻辑是判断是否服务编码需要自动输入或者还是编辑状态进来的就不修改服务编码
		String code = getRequest().getParameter("code");
		if(!StringUtils.isEmpty(code)){
			if(code.equals("0")){
				int maxWebFwbm = serviceManagerService.getMaxWebFwbm();
				model.setClassFwbm(String.valueOf(maxWebFwbm));
			}else{
				model.setClassFwbm(code);
			}
		}
		
		if(!StringUtils.isEmpty(model.getClassSsywxt())){
			model.setClassSsywxt(model.getClassSsywxt().trim());
		}
		
		if(model.getIscommon().equals("1")){
			BusinessQuery businessQuery = new BusinessQuery();
			businessQuery.setClassId(model.getClassSsywxt());
			String otherFlag = businessService.getList(businessQuery).get(0).getOtherFlag();
			if(otherFlag.equals("1")){
				this.setErrorMessage("公众服务，所属业务系统不能归属于第三方");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
			
			if(model.getClassFwlx().equals("WEB_SERVICE") && model.getClassFwlj().indexOf("${uid}") != -1){
				this.setErrorMessage("公众服务为web服务时，服务i地址不能包含用户信息，请仔细检查");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}else if(model.getClassFwlx().equals("APP_SERVICE")){
				this.setErrorMessage("公众服务的服务类型不能app服务");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
		}
		
		String iconmethod = getRequest().getParameter("uploadMethod");
		String fwtbdz;
		if(iconmethod.equals("fromsomewhere")){//系统中选择的时候
			fwtbdz = getRequest().getParameter("icoPath");
			model.setClassFwtbid(null);
		}else{//自已上传的时候
			model.setClassFwtbid(getRequest().getParameter("fwtbid"));
			fwtbdz = mobileCommonService.getMinUploadImagePath(model.getClassFwtbid());
			
		}
		
		String fileid = getRequest().getParameter("fileid");
		model.setFileId(fileid);
		
		model.setClassFwtbdz(fwtbdz);
		
		String showway = request.getParameter("showway");
		showway = !StringUtils.isEmpty(showway) ? showway : "";
		model.setClassShowway(showway);
		
		//String fwtbdz = mobileCommonService.getUploadImagePath(model.getClassFwtbid());
		if(model.getClassId() == null){
			model.setClassFwly(ServiceSourceEnum.CUSTOM_SOURCE.getKey());
			String result = serviceManagerService.check(model);
			if(result.equals("success")){
				
				BusinessQuery businessQuery = new BusinessQuery();
				businessQuery.setClassId(model.getClassSsywxt());
				
				//Business businessEntity = businessService.getList(businessQuery).get(0);
				Business businessEntity = new Business();
				List<Business> businessList = businessService.getBusinessList(businessQuery);
				
				if( businessList!=null && businessList.size()>0 ){
					businessEntity = businessList.get(0);
				}
 				
				model.setClassFbzt(businessEntity.getClassSyzt()==null?"":businessEntity.getClassSyzt());
				
				serviceManagerService.insert(model);
				this.setSuccessMessage("成功插入数据！");
				getValueStack().set(DATA, getMessage());
			}else{
				this.setErrorMessage(result);
				getValueStack().set(DATA, getMessage());
			}
		}
		else{
			serviceManagerService.update(model);
			String newsid = model.getNewsid();
			if(!StringUtil.isEmpty(newsid)){
				NewsQuery newsQuery = new NewsQuery();
				newsQuery.setId(newsid);
				News newsModel = newsService.getPageList(newsQuery).get(0);
				newsQuery.setTitle(model.getClassFwmc());
				newsQuery.setIntro(model.getClassFwms());
				newsQuery.setAuthor(newsModel.getAuthor());
				newsQuery.setSource(newsModel.getSource());
				newsService.doSave(newsQuery);
			}
			
			this.setSuccessMessage("成功更新数据！");
			getValueStack().set(DATA, getMessage());
		}
	    
		return DATA;
	}
	
	
	public String recommend(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        for (String idCheck : id) {
        	tids.add(idCheck.trim());
        }
        param.put("ids", tids);
        param.put("recommendFlag", "1");
        serviceManagerService.updateRecommend(param);
        this.setSuccessMessage("操作成功！");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}
	
	public String unrecommend(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        for (String idCheck : id) {
        	tids.add(idCheck.trim());
        }
        param.put("ids", tids);
        param.put("recommendFlag", "0");
        serviceManagerService.updateRecommend(param);
        this.setSuccessMessage("操作成功！");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}
	
	public String remove(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        NewsQuery newsQuery = null;
        try {
	        for (String idCheck : id) {
	            tids.add(idCheck.trim());
	            ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
	            serviceQuery.setClassId(idCheck);
	            serviceQuery.setClassDeleted("0");
	            String newsid = serviceManagerService.getList(serviceQuery).get(0).getNewsid();
	            if(!StringUtil.isEmpty(newsid)){
	            	newsQuery = new NewsQuery();
	            	newsQuery.setId(newsid);
	            	newsQuery.setDeleted("1");
	            	newsService.controlNews(newsQuery);
	            }
	        }
        } catch (Exception e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        param.put("yhid", SessionFactory.getUser().getYhm());
        param.put("ids", tids);
        serviceManagerService.remove(param);
        
		//serviceManagerService.remove(model);
		this.setSuccessMessage("删除成功！");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}
	
	public String up() throws Exception{
		serviceManagerService.up(model);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
    public String down() throws Exception{
    	serviceManagerService.down(model);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
    
    /**
	 * 保存修改后的索引顺序Action
	 * @return
	 */
	public String updateIndex() {
		String[] ids = getRequest().getParameterValues("ids");
		String minStr = getRequest().getParameter("minIndex");
		//String maxStr = getRequest().getParameter("maxIndex");
		int min =0;
		if (minStr != null) {
			min = Integer.parseInt(minStr);
		}
		Map<String, String> map = null;
		if (ids != null) {
			try {
				for (int i =0; i <ids.length; i++) {
					map = new HashMap<String, String>();
					map.put("index", (i+min)+"");
					map.put("id", ids[i]);
					serviceManagerService.updateIndex(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list();
	}
    
    public String changeShow(){
    	HttpServletRequest request = ServletActionContext.getRequest();
		String showway = request.getParameter("showway").trim();
		String classId = request.getParameter("classId").trim();
		ServiceManager serviceModel = new ServiceManager();
		serviceModel.setClassId(classId);
		serviceModel.setClassShowway(showway);
		serviceManagerService.changeShow(serviceModel);
		
    	this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
    }
	
	public String fabu(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        for (String idCheck : id) {
            tids.add(idCheck.trim());
        }
        param.put("yhid", SessionFactory.getUser().getYhm());
        param.put("fbzt", "1");
        param.put("ids", tids);
		serviceManagerService.fabu(param);
		this.setSuccessMessage("发布成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String quxiao(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        for (String idCheck : id) {
            tids.add(idCheck.trim());
        }
        param.put("yhid", SessionFactory.getUser().getYhm());
        param.put("fbzt", "0");
        param.put("ids", tids);
		serviceManagerService.quxiao(param);
		this.setSuccessMessage("取消成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public void setServiceManagerService(IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}

	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}
	

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	

	public ServiceManagerQuery getQuery() {
		return query;
	}

	public void setQuery(ServiceManagerQuery query) {
		this.query = query;
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}

	public void setModel(ServiceManager model) {
		this.model = model;
	}

	public ServiceManager getModel() {
		return model;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getId() {
		return id;
	}

}
