package com.zfsoft.mobile.webservices.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.anysoft.webservicetest.encoder.DBEncrypt;
import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jw.org.tempuri.ILogin;
import com.mh.CXFServe.service.ICaService;
import com.newmobile.login.service.imp.MobileLoginXMLService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.util.MiddleWareUtil;
import com.util.WebServiceUtil;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.service.svcinterface.IAttachementService;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.basedata.service.IBaseDataService;
import com.zfsoft.mobile.canteenfood.entity.Canteen;
import com.zfsoft.mobile.canteenfood.entity.Canteenaddress;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;
import com.zfsoft.mobile.canteenfood.entity.Foodorder;
import com.zfsoft.mobile.canteenfood.entity.Orderofcanteen;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;
import com.zfsoft.mobile.interfaceManager.dao.query.InterfaceManagerQuery;
import com.zfsoft.mobile.interfaceManager.entity.InterfaceManager;
import com.zfsoft.mobile.interfaceManager.service.IInterfaceManagerService;
import com.zfsoft.mobile.module.entity.Module;
import com.zfsoft.mobile.module.service.IModuleService;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.service.IMyPortalService;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.news.service.INewsService;
import com.zfsoft.mobile.qrcode.entity.QRcode;
import com.zfsoft.mobile.qrcode.service.IQRcodeService;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ExamDyYhEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;
import com.zfsoft.mobile.services.entity.GaoDeMaoEntity;
import com.zfsoft.mobile.services.entity.LossObjectEntity;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.services.service.IGaoDeMapService;
import com.zfsoft.mobile.services.service.ILossObjectService;
import com.zfsoft.mobile.services.service.IQuestionService;
import com.zfsoft.mobile.servlet.entity.FailureEntity;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.suggest.service.ISuggestService;
import com.zfsoft.mobile.version.common.Constants;
import com.zfsoft.mobile.version.entity.VersionCommon;
import com.zfsoft.mobile.version.entity.VersionManager;
import com.zfsoft.mobile.version.service.IVersionManagerService;
import com.zfsoft.mobile.version.utils.DateUtil;
import com.zfsoft.mobile.version.utils.SelectItems;
import com.zfsoft.mobile.version.utils.XmltoString;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.entity.InformationListEntity;
import com.zfsoft.mobile.webservices.entity.MemoCatalog;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.NoticeInfoEntity;
import com.zfsoft.mobile.webservices.entity.NumberDepartment;
import com.zfsoft.mobile.webservices.entity.PersonEntity;
import com.zfsoft.mobile.webservices.entity.Program;
import com.zfsoft.mobile.webservices.entity.Questionnaire;
import com.zfsoft.mobile.webservices.entity.ScalendarEntity;
import com.zfsoft.mobile.webservices.entity.SuggestEntity;
import com.zfsoft.mobile.webservices.entity.ThirdPartyEntity;
import com.zfsoft.mobile.webservices.entity.TopicForSubmit;
import com.zfsoft.mobile.webservices.entity.VoteActivity;
import com.zfsoft.mobile.webservices.entity.WeiBoEntity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;
import com.zfsoft.mobile.webservices.query.PersonQuery;
import com.zfsoft.mobile.webservices.service.IWSSerService;
import com.zfsoft.mobile.xfyj.entity.XfyjEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqQueryEntity;
import com.zfsoft.mobile.xfyj.service.XfyjService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;
import com.zfsoft.weibo.weibo4j.Account;
import com.zfsoft.weibo.weibo4j.Timeline;
import com.zfsoft.weibo.weibo4j.Users;
import com.zfsoft.weibo.weibo4j.model.Status;
import com.zfsoft.weibo.weibo4j.model.StatusWapper;
import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONException;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;

/**
 * 服务WEB Service 实现类
 * @author Administrator
 *
 */
public class WSSerServiceImpl implements IWSSerService {
	
	private static Logger logger = Logger.getLogger(MobileLoginXMLService.class);
	private IQRcodeService QRcodeService;
	private static final String SERVICE_FRONT = "service_front";
	private static final String SHARE_ADDRESS = "share_address";
	private static final String TT_NAME = "tt_name";
	
    private IMobileCommonService mobileCommonService;
    private INewsCatalogService newsCatalogService;
    private INewsService newsService;
    private IModuleService moduleService;
    private IInterfaceManagerService interfaceManagerService;
    private ILoginService loginService;
    private IBusinessService businessService;
    private IMyPortalService myPortalService;
    private INewsConfigService newsConfigService;

	private IBaseDataService baseDataService;
    private IInfoPropertyService infoPropertyService;
    private IAttachementService fileAttachementService;
    private IQuestionService questionService;
    private IGaoDeMapService gaoDeMapService;
    private ILossObjectService lossObjectService;
    private ISuggestService suggestService;
    
    private XfyjService xfyjService;
    
    public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public IAttachementService getFileAttachementService() {
		return fileAttachementService;
	}

	public void setFileAttachementService(IAttachementService fileAttachementService) {
		this.fileAttachementService = fileAttachementService;
	}

	public IInfoPropertyService getInfoPropertyService() {
		return infoPropertyService;
	}

	public void setInfoPropertyService(IInfoPropertyService infoPropertyService) {
		this.infoPropertyService = infoPropertyService;
	}
    
    public IBaseDataService getBaseDataService() {
		return baseDataService;
	}

	public void setBaseDataService(IBaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

	/**
     * @return the mobileCommonService
     */
    public IMobileCommonService getMobileCommonService() {
        return mobileCommonService;
    }

    /**
     * @param mobileCommonService the mobileCommonService to set
     */
    public void setMobileCommonService(IMobileCommonService mobileCommonService) {
        this.mobileCommonService = mobileCommonService;
    }

    public INewsCatalogService getNewsCatalogService() {
		return newsCatalogService;
	}

	public void setNewsCatalogService(INewsCatalogService newsCatalogService) {
		this.newsCatalogService = newsCatalogService;
	}

	public INewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
	
	

	public IInterfaceManagerService getInterfaceManagerService() {
		return interfaceManagerService;
	}

	public void setInterfaceManagerService(
			IInterfaceManagerService interfaceManagerService) {
		this.interfaceManagerService = interfaceManagerService;
	}

	public IModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}
	

	public INewsConfigService getNewsConfigService() {
		return newsConfigService;
	}

	public void setNewsConfigService(INewsConfigService newsConfigService) {
		this.newsConfigService = newsConfigService;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public IMyPortalService getMyPortalService() {
		return myPortalService;
	}

	public void setMyPortalService(IMyPortalService myPortalService) {
		this.myPortalService = myPortalService;
	}
	
	
	
	public XfyjService getXfyjService() {
		return xfyjService;
	}

	public void setXfyjService(XfyjService xfyjService) {
		this.xfyjService = xfyjService;
	}

	@Override
	public String getMhRecommendPage(int size){
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setStatus("1");
		newsQuery.setPerPageSize(size);
		newsQuery.setToPage(1);
		newsQuery.setRecommend("1");
		PageList<News> newsList = newsService.getPageList(newsQuery);
		if (newsList.size() <= 0) {
			return "暂无推荐资讯";
		}
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
        String url = getImageHost();
        String goUrl = "";
        for (News news : newsList) {
        	goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
        	sb.append("<mhrecommend>");
        	sb.append("<id>").append(news.getId()).append("</id>");
        	sb.append("<title>").append(news.getTitle()).append("</title>");
        	String isremotelogo = news.getIsremotelogo();
        	if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
            	sb.append("<logopath><logo><![CDATA[" + news.getRemotelogourl() + "]]></logo></logopath>");
            }else{
            	sb.append("<logopath>");
            	if(!StringUtil.isEmpty(news.getPicId()) && !StringUtil.isEmpty(news.getPicUrl())){
            		String[] picIds = news.getPicId().split(",");
            		String[] picUrls = news.getPicUrl().split(",");
            		boolean check = false;
            		for (int i = 0; i < picIds.length; i++) {
            			
            			try {
            				check = mobileCommonService.checkImage(picUrls[i], picIds[i]);
            			} catch (IOException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}
            			if(check)
            				sb.append("<logo><![CDATA[" + url + picUrls[i] + "]]></logo>");
            			else
            				sb.append("<logo><![CDATA["  + url + "upload/default_image.jpg" + "]]></logo>");
            			
            		}
            	}else{
            		sb.append("<logo></logo>");
            	}
            	sb.append("</logopath>");
            	
            	
            	/*String[] picIds = news.getPicId().split(",");
            	String[] picUrls = news.getPicUrl().split(",");
            	sb.append("<logopath>");
            	boolean check = false;
            	for (int i = 0; i < picIds.length; i++) {
            		
            		try {
            			check = mobileCommonService.checkImage(picUrls[i], picIds[i]);
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            		if(check)
            			sb.append("<logo><![CDATA[" + url + picUrls[i] + "]]></logo>");
            		else
            			sb.append("<logo><![CDATA["  + url + "upload/default_image.jpg" + "]]></logo>");
					
				}
            	sb.append("</logopath>");*/
            }
        	if(Config.getString("newsUrl","no").equals("yes")){
        		if(!StringUtil.isEmpty(news.getNewsURL()))
        			sb.append("<url><![CDATA[").append(news.getNewsURL()).append("]]></url>");
        		else
        			sb.append("<url><![CDATA[").append(goUrl).append("]]></url>");
        			
        	}else{
        		sb.append("<url><![CDATA[").append(goUrl).append("]]></url>");
        	}
        	
        	
        	
        	sb.append("</mhrecommend>");
        	
        }
        sb.append("</zfsoft>");
		return sb.toString();
		
	}
	
	/*public String getMhRecommendPage(int size){
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setStatus("1");
		newsQuery.setPerPageSize(size);
		newsQuery.setToPage(1);
		newsQuery.setRecommend("1");
		PageList<News> newsList = newsService.getPageList(newsQuery);
		if (newsList.size() <= 0) {
			return "暂无推荐资讯";
		}
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
        String url = getImageHost();
        String goUrl = "";
        for (News news : newsList) {
        	goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
        	sb.append("<mhrecommend>");
        	sb.append("<id>").append(news.getId()).append("</id>");
        	sb.append("<title>").append(news.getTitle()).append("</title>");
        	String isremotelogo = news.getIsremotelogo();
        	if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
            	sb.append("<logopath><![CDATA[ " + news.getRemotelogourl() + "]]></logopath>");
            }else{
            	boolean check = false;
            	try {
            		check = mobileCommonService.checkImage(news.getPicUrl(), news.getPicId());
            	} catch (IOException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
            	if(check)
            		sb.append("<logopath><![CDATA[" + url + news.getPicUrl() + "]]></logopath>");
            	else
            		sb.append("<logopath><![CDATA["  + url + "upload/default_image.jpg" + "]]></logopath>");
            }
        	if(Config.getString("newsUrl","no").equals("yes")){
        		if(!StringUtil.isEmpty(news.getNewsURL()))
        			sb.append("<url><![CDATA[").append(news.getNewsURL()).append("]]></url>");
        		else
        			sb.append("<url><![CDATA[").append(goUrl).append("]]></url>");
        			
        	}else{
        		sb.append("<url><![CDATA[").append(goUrl).append("]]></url>");
        	}
        	
        	
        	
        	sb.append("</mhrecommend>");
        	
        }
        sb.append("</zfsoft>");
		return sb.toString();
		
	}*/
	
	@Override
	public String personDocumentInformation(String userName,String informationName, String informationId,String strKey){
		LoginModel model = new LoginModel();
		model.setYhm(userName);
		User user = loginService.cxYhxx(model);
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(user.getJsdms(), informationId);
		InfoPropertyQuery propertyQuery = new InfoPropertyQuery();
		propertyQuery.setClassId( clazz.getGuid() );
		List<InfoProperty> properties = infoPropertyService.getInfoProperties( propertyQuery );
		DynaBeanQuery query = new DynaBeanQuery();
		query.setClazz(clazz);
		String express = " t.gh = '" + informationName + "'";
        query.setExpress(express);
        query.setPerPageSize(1);
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><table>");
        List<DynaBean> list = baseDataService.getSynchronizedBaseData(query);
        if(list != null && list.size() > 0){
        	for(DynaBean dynaBean : list){
        		sb.append("<item>");
        		Map<String, Object> map = dynaBean.getValues();
        		for(InfoProperty property : properties){
            		if(property.getViewable()){
            			for(Map.Entry<String, Object> entry: map.entrySet()) {
            				if(property.getFieldName().equals(entry.getKey())){
            					sb.append("<data>")
            					  .append("<name>").append(property.getDescription()).append("</name>");
            					/*if(clazz.getIdentityName().toLowerCase().equals("m_jbxx") && property.getFieldName().equals("bm")){
            						String bmvalue = null;
            						Map<String, String> bmmap = new HashMap<String, String>();
            						bmmap.put("dpId", entry.getValue().toString());
            						List<NumberDepartment> departmentList = mobileCommonService.getAllDepartment(bmmap);
            						if(departmentList != null && departmentList.size() == 1){
            							bmvalue = departmentList.get(0).getBmmc();
            						}else{
            							try {
											throw new Exception("获取部门名称失败！");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
            						}
            						sb.append("<value>").append(bmvalue).append("</value>");
            					}else{*/
            						sb.append("<value>").append(entry.getValue()).append("</value>");
            					//}
            					sb.append("</data>");
            				}
            			}
            		}
            	}
        		sb.append("</item>");
        	}
        	
        }
        sb.append("</table>");
        
        return sb.toString();
	}
	@Override
	public String personDocumentInformationList(String userName,String strKey){
		List<InformationListEntity> informationList = mobileCommonService.getAllInformationList();
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ArchivesPersonnelItemList>");
        if(informationList != null){
        	for (InformationListEntity information : informationList) {
        		sb.append("<module>");
        		sb.append("<informationid>" + information.getInformationid() + "</informationid>");
        		sb.append("<informationname>" + information.getInformationname() + "</informationname>");
        		
        		String url = getImageHost();
        		boolean check = false;
        		try {
        			check = mobileCommonService.checkImage(
        					information.getInformationico(),
        					null);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        		if (check)
        			sb.append("<informationico>" + url + information.getInformationico() + "</informationico>");
        		else
        			sb.append("<informationico>" + url + "upload/default_image.jpg" + "</informationico>");
        		
        		sb.append("</module>");
        	}
        }
        sb.append("</ArchivesPersonnelItemList>");
        return sb.toString();
	}
	
	

	@Override
	public String votelike(String userName, String jmid, String tag,
			String strKey) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", userName);
		map.put("jmid", jmid);
		if(tag.equals("1")){
			mobileCommonService.deleteTPByUserAndJmid(map);
			mobileCommonService.insertTPByUserAndJmid(map);
		}else{
			mobileCommonService.deleteTPByUserAndJmid(map);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>");
		return sb.toString();
	}

	@Override
	public String voteRanklist(String userName, String tphdid, String strKey) {
		// TODO Auto-generated method stub
		List<Program> programList = mobileCommonService.getRankListByTphdid(tphdid);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><votelist>");
		if(programList.size() > 0){
			for(Program program : programList){
				sb.append("<vote>");
				sb.append("<title>" + program.getTitle() + "</title>");
				sb.append("<ps>" + program.getPs() + "</ps>");
				sb.append("</vote>");
			}
		}
		sb.append("</votelist>");
		return sb.toString();
	}
	
	@Override
	public String votelist(String userName, String tphdid, String strKey) {
		// TODO Auto-generated method stub
		List<VoteActivity> VoteList = mobileCommonService.getVoteList(tphdid);
		
		VoteActivity voteActivity = new VoteActivity();
		if(VoteList != null && VoteList.size() == 1){
			voteActivity = VoteList.get(0);
		}
		List<Program> programList = new ArrayList<Program>();
		if(voteActivity.getTphdid() != null){
			programList = mobileCommonService.getProgramList(voteActivity.getTphdid());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><votelist>");
		if(programList.size() > 0){
			for(Program program : programList){
				
				sb.append("<vote>");
				sb.append("<jmid>" + program.getJmid() + "</jmid>");
				sb.append("<jmbh>" + program.getJmbh() + "</jmbh>");
				sb.append("<title>" + program.getTitle() + "</title>");
				sb.append("<dp>" + program.getDp() + "</dp>");
				sb.append("<yy>" + program.getYy() + "</yy>");
				String url = getImageHost() + "/file/file_image.html?fileGuid=" + program.getJmtpid();
				sb.append("<url>" + url + "</url>");
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", userName);
				map.put("jmid", program.getJmid());
				int count = mobileCommonService.getTPByUserAndJmid(map);
				String tag = count > 1 ? "1" : "0";
				sb.append("<tag>" + count + "</tag>");
				sb.append("</vote>");
			}
		}
		sb.append("<zt><ztys>").append(voteActivity.getZtys()).append("</ztys>");
		String zturl = getImageHost() + "/file/file_image.html?fileGuid=" + voteActivity.getZttpid();
		sb.append("<zturl>").append(zturl).append("</zturl>").append("</zt></votelist>");
		return sb.toString();
	}
	@Override
	public String digitalArchivesDpList(String userName, String strKey) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("dpId", "");
		map.put("userName", userName);
		List<NumberDepartment> departmentList = mobileCommonService.getAllDepartment(map);
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ArchivesItemList>");
        for (NumberDepartment department : departmentList) {
            sb.append("<module>");
            sb.append("<archivesdpid>" + department.getBmbm() + "</archivesdpid>");
            sb.append("<archivesdpname>" + department.getBmmc() + "</archivesdpname>");
    		sb.append("</module>");
        }
        sb.append("</ArchivesItemList>");
        return sb.toString();
	}
	
	@Override
	public String digitalArchivesPersonnelList(String userName, String dpId,
			String strKey, String searchName, int start, int size) {
		// TODO Auto-generated method stub
		PersonQuery personQuery = new PersonQuery();
		personQuery.setDpId(dpId);
		personQuery.setUserName(userName);
		personQuery.setSearchName(searchName);
		if (size > 0) {
			personQuery.setPerPageSize(size);
		}
		if (start >=0) {
			personQuery.setToPage(start);
		}
		PageList<PersonEntity> personList = mobileCommonService.getPersonList(personQuery);
		if (personList.size() <= 0) {
			return "暂无相关人员";
		}
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ArchivesPersonnelItemList pagesize=\"");
        sb.append(personList.getPaginator().getPages() + "\" start=\"" + start + "\" size=\"" + personList.size() + "\">");
        for(PersonEntity person : personList){
        	sb.append("<module>");
            sb.append("<ryid>" + person.getRyid() + "</ryid>");
            sb.append("<ryname>" + person.getRyname() + "</ryname>");
            sb.append("<pybs>" + (StringUtil.isEmpty(person.getPybs()) ? "" : person.getPybs()) + "</pybs>");
            sb.append("<lxdh>" + person.getLxdh() + "</lxdh>");
            String url = getImageHost();
            boolean check = false;
			try {
				check = mobileCommonService.checkImage(
						person.getRyico(),
						person.getRyicoid());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (check)
				sb.append("<ryico>" + url + person.getRyico() + "</ryico>");
			else
				sb.append("<ryico>" + url + "upload/default_image.jpg" + "</ryico>");
			
    		sb.append("</module>");
        }
        sb.append("</ArchivesPersonnelItemList>");
        return sb.toString();
	}
	
	/*@Override*/
	/*public String digitalArchivesPersonnelList(String userName, String dpId,
			String strKey) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("dpId", dpId);
		List<NumberDepartment> department = mobileCommonService.getAllDepartment(map);
		String departmentName = null;
		if(department != null && department.size() == 1){
			departmentName = department.get(0).getBmmc();
		}else{
			try {
				throw new Exception("部门名称选择失败！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<PersonEntity> personList = mobileCommonService.getAllPerson(departmentName); 
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("dpId", dpId);
		List<PersonEntity> personList = mobileCommonService.getAllPerson(map);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ArchivesPersonnelItemList>");
        for(PersonEntity person : personList){
        	sb.append("<module>");
            sb.append("<ryid>" + person.getRyid() + "</ryid>");
            sb.append("<ryname>" + person.getRyname() + "</ryname>");
            sb.append("<pybs>" + person.getPybs() + "</pybs>");
            sb.append("<lxdh>" + person.getLxdh() + "</lxdh>");
            String url = getImageHost();
            boolean check = false;
			try {
				check = mobileCommonService.checkImage(
						person.getRyico(),
						person.getRyicoid());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (check)
				sb.append("<ryico>" + url + person.getRyico() + "</ryico>");
			else
				sb.append("<ryico>" + url + "upload/default_image.jpg" + "</ryico>");
			
    		sb.append("</module>");
        }
        sb.append("</ArchivesPersonnelItemList>");
        return sb.toString();
	}*/
	@Override
	public String getCommonService(){
		List<ServiceManager> services = mobileCommonService.getCommonService();
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppTypeItemList>");
        for (ServiceManager sm : services) {
            sb.append("<module>");
            sb.append("<id>" + sm.getClassId() + "</id>");
            sb.append("<type>" + sm.getClassFwlx() + "</type>");
            sb.append("<name>" + sm.getClassFwmc() + "</name>");
            sb.append("<icon>" + getImageHost() + sm.getClassFwtbdz() + "</icon>");
            if (StringUtils.isEmpty(sm.getAppUrl())) {
            	sb.append("<url> </url>");
            } else {
				sb.append("<url><![CDATA[" + sm.getAppUrl() + "]]></url>");
            }
            sb.append("<androidurl>" + sm.getClassAndroidUrl() + "</androidurl>");
            sb.append("<iosurl>" + sm.getClassIosUrl() + "</iosurl>");
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	sb.append("<wxurl> </wxurl>");
            } else {
				sb.append("<wxurl><![CDATA[" + sm.getWebUrl() + "]]></wxurl>");
            }
            sb.append("<fwbm>" + sm.getClassFwbm() + "</fwbm>");
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
            					getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
            sb.append("<apkdownurl><![CDATA[" + apkDownURL + "]]></apkdownurl>");
           /* String apkfilename = !StringUtil.isEmpty(sm.getFileId()) ? 
					fileAttachementService.getById(sm.getFileId()).getName() : "";*/
			sb.append("<apkfilename>" + 
					(StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename()) +
					 "</apkfilename>");
            sb.append("<apkpackage>" + sm.getClassAppyydz() + "</apkpackage>");
            sb.append("<URLScheme>" + sm.getIosURLScheme() + "</URLScheme>");
            sb.append("<URLiTunes><![CDATA[" + sm.getIosURLiTunes() + "]]></URLiTunes>");
            sb.append("<procode>" + sm.getProcode() + "</procode>");
            sb.append("<otherFlag>" + sm.getOtherFlag() + "</otherFlag>");
            sb.append("<iscommon>" + sm.getIscommon() + "</iscommon>");
//            sb.append("<iconId>" + sm.getClassFwtbid() + "</iconId>");
//            sb.append("<appurl>" + sm.getClassAppyydz() + "</appurl>");
    		sb.append("<bak>" + "</bak>");
    		sb.append("<moduletype>" + sm.getClassSsywxt() + "</moduletype>");
    		sb.append("</module>");
        }
        sb.append("</AppTypeItemList>");
        return sb.toString();
	}
	

	/**
     * 根据帐号取得该帐号的所有服务
	 * @throws IOException 
     */
    @Override
    public String CommonAllFunction(String userId,String strKey) {
        List<ServiceManager> services = mobileCommonService.getAllServiceByUser(userId, "");
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppTypeItemList>");
        for (ServiceManager sm : services) {
            sb.append("<module>");
            sb.append("<id>" + sm.getClassId() + "</id>");
            sb.append("<type>" + sm.getClassFwlx() + "</type>");
            sb.append("<name>" + sm.getClassFwmc() + "</name>");
            sb.append("<icon>" + getImageHost() + sm.getClassFwtbdz() + "</icon>");
            if (StringUtils.isEmpty(sm.getAppUrl())) {
            	sb.append("<url> </url>");
            } else {
				sb.append("<url><![CDATA[" + sm.getAppUrl() + "]]></url>");
            }
            sb.append("<androidurl>" + sm.getClassAndroidUrl() + "</androidurl>");
            sb.append("<iosurl>" + sm.getClassIosUrl() + "</iosurl>");
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	sb.append("<wxurl> </wxurl>");
            } else {
				sb.append("<wxurl><![CDATA[" + sm.getWebUrl() + "]]></wxurl>");
            }
            sb.append("<fwbm>" + sm.getClassFwbm() + "</fwbm>");
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
            					getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
            sb.append("<apkdownurl><![CDATA[" + apkDownURL + "]]></apkdownurl>");
           /* String apkfilename = !StringUtil.isEmpty(sm.getFileId()) ? 
					fileAttachementService.getById(sm.getFileId()).getName() : "";*/
			sb.append("<apkfilename>" + 
					(StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename()) +
					 "</apkfilename>");
            sb.append("<apkpackage>" + sm.getClassAppyydz() + "</apkpackage>");
            sb.append("<URLScheme>" + sm.getIosURLScheme() + "</URLScheme>");
            sb.append("<URLiTunes><![CDATA[" + sm.getIosURLiTunes() + "]]></URLiTunes>");
            sb.append("<procode>" + sm.getProcode() + "</procode>");
            sb.append("<otherFlag>" + sm.getOtherFlag() + "</otherFlag>");
            sb.append("<iscommon>" + sm.getIscommon() + "</iscommon>");
//            sb.append("<iconId>" + sm.getClassFwtbid() + "</iconId>");
//            sb.append("<appurl>" + sm.getClassAppyydz() + "</appurl>");
    		sb.append("<bak>" + "</bak>");
    		sb.append("<moduletype>" + sm.getClassSsywxt() + "</moduletype>");
    		sb.append("</module>");
        }
        sb.append("</AppTypeItemList>");
        return sb.toString();
    }
    
    /**
     * 根据帐号取得该帐号没有添加的所有服务
     * @throws IOException 
     */
    @Override
    public String CommonOtherFunction(String userId,String strKey){
    	String url = getImageHost();
    	//List<ServiceManager> allServices = mobileCommonService.getAllServiceByUser(userId, "");
    	//List<ServiceManager> commonServices = mobileCommonService.getFrequentlyServiceByUser(userId);
    	//allServices.removeAll(commonServices);
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("userId", userId);
    	
    	List<ServiceManager> allServices = mobileCommonService.getOtherServiceByUser(params);
    	
    	StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppTypeItemList>");
        long starttime = System.currentTimeMillis();
        logger.error("--------starttime---------"+starttime);
        for (ServiceManager sm : allServices) {
            sb.append("<module>");
            sb.append("<id>" + sm.getClassId() + "</id>");
            sb.append("<type>" + sm.getClassFwlx() + "</type>");
            sb.append("<name>" + sm.getClassFwmc() + "</name>");
            sb.append("<icon>" + url + sm.getClassFwtbdz() + "</icon>");
            if (StringUtils.isEmpty(sm.getAppUrl())) {
            	sb.append("<url> </url>");
            } else {
				sb.append("<url><![CDATA[" + sm.getAppUrl() + "]]></url>");
            }
            sb.append("<androidurl>" + sm.getClassAndroidUrl() + "</androidurl>");
            sb.append("<iosurl>" + sm.getClassIosUrl() + "</iosurl>");
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	sb.append("<wxurl> </wxurl>");
            } else {
				sb.append("<wxurl><![CDATA[" + sm.getWebUrl() + "]]></wxurl>");
            }
            sb.append("<fwbm>" + sm.getClassFwbm() + "</fwbm>");
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
            sb.append("<apkdownurl><![CDATA[" + apkDownURL + "]]></apkdownurl>");
			sb.append("<apkfilename>" + 
					(StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename()) +
					 "</apkfilename>");
            sb.append("<apkpackage>" + sm.getClassAppyydz() + "</apkpackage>");
            sb.append("<URLScheme>" + sm.getIosURLScheme() + "</URLScheme>");
            sb.append("<URLiTunes><![CDATA[" + sm.getIosURLiTunes() + "]]></URLiTunes>");
            sb.append("<procode>" + sm.getProcode() + "</procode>");
            sb.append("<otherFlag>" + sm.getOtherFlag() + "</otherFlag>");
            sb.append("<iscommon>" + sm.getIscommon() + "</iscommon>");
//            sb.append("<iconId>" + sm.getClassFwtbid() + "</iconId>");
//            sb.append("<appurl>" + sm.getClassAppyydz() + "</appurl>");
            sb.append("<bak>" + "</bak>");
            sb.append("<moduletype>" + sm.getClassSsywxt() + "</moduletype>");
            sb.append("</module>");
        }
        long endtime = System.currentTimeMillis();
        logger.error("--------endtime---------"+endtime);
        logger.error("--------时间差---------"+(endtime - starttime));
        sb.append("</AppTypeItemList>");
        return sb.toString();
    }
   
    
    /**
     * 根据帐号、业务ID取得该帐号的所有服务
     * @throws IOException 
     */
    @Override
    public String CommonBrushFunction(String userId,String ywId,String strKey){
//        if (StringUtils.isEmpty(ywId)) {
//            List<Business> xtyws = mobileCommonService.getAllXtYwByUser(userId);
//            if (xtyws == null || xtyws.size() == 0) {
//                return "";
//            } else {
//                ywId = xtyws.get(0).getClassXtbm();
//            }
//        }
//        
    	List<ServiceManager> services;
    	if ("init".equals(ywId)) {
    		services = mobileCommonService.getFrequentlyServiceByUser(userId);
    	} else {
    		services = mobileCommonService.getAllServiceByUser(userId, ywId);
    	}
    	String url = getImageHost();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppTypeItemList>");
        for (ServiceManager sm : services) {
            sb.append("<module>");
            sb.append("<id>" + sm.getClassId() + "</id>");
            sb.append("<type>" + sm.getClassFwlx() + "</type>");
            sb.append("<name>" + sm.getClassFwmc() + "</name>");
            sb.append("<icon><![CDATA[" + url + sm.getClassFwtbdz() + "]]></icon>");
            if (StringUtils.isEmpty(sm.getAppUrl())) {
            	sb.append("<url> </url>");
            } else {
				sb.append("<url><![CDATA[" + sm.getAppUrl() + "]]></url>");
            }
            sb.append("<androidurl>" + sm.getClassAndroidUrl() + "</androidurl>");
            sb.append("<iosurl>" + sm.getClassIosUrl() + "</iosurl>");
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	sb.append("<wxurl> </wxurl>");
            } else {
				sb.append("<wxurl><![CDATA[" + sm.getWebUrl() + "]]></wxurl>");
            }
            sb.append("<fwbm>" + sm.getClassFwbm() + "</fwbm>");
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
            sb.append("<apkdownurl><![CDATA[" + apkDownURL + "]]></apkdownurl>");
           /* String apkfilename = !StringUtil.isEmpty(sm.getFileId()) ? 
					fileAttachementService.getById(sm.getFileId()).getName() : "";*/
            sb.append("<apkfilename>" + 
					(StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename()) +
					 "</apkfilename>");
            sb.append("<apkpackage>" + sm.getClassAppyydz() + "</apkpackage>");
            sb.append("<URLScheme>" + sm.getIosURLScheme() + "</URLScheme>");
            sb.append("<URLiTunes><![CDATA[" + sm.getIosURLiTunes() + "]]></URLiTunes>");
            sb.append("<procode>" + sm.getProcode() + "</procode>");
            sb.append("<otherFlag>" + sm.getOtherFlag() + "</otherFlag>");
            sb.append("<iscommon>" + sm.getIscommon() + "</iscommon>");
//            sb.append("<iconId>" + sm.getClassFwtbid() + "</iconId>");
//            sb.append("<appurl>" + sm.getClassAppyydz() + "</appurl>");
        		sb.append("<bak>" + "</bak>");
        		sb.append("<moduletype>" + sm.getClassSsywxt() + "</moduletype>");
        		sb.append("</module>");
        }
        sb.append("</AppTypeItemList>");
        return sb.toString();
    }

    /**
     * 根据帐号取得该帐号的常用服务
     * @throws IOException 
     */
    @Override
    public String Commonfunction(String userId,String strKey){
        List<ServiceManager> services = mobileCommonService.getFrequentlyServiceByUser(userId);
        StringBuilder sb = new StringBuilder();
        String url = getImageHost();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppTypeItemList>");
        for (ServiceManager sm : services) {
            sb.append("<module>");
            sb.append("<id>" + sm.getClassId() + "</id>");
            sb.append("<type>" + sm.getClassFwlx() + "</type>");
            sb.append("<name>" + sm.getClassFwmc() + "</name>");
			sb.append("<icon><![CDATA[" + url + sm.getClassFwtbdz() + "]]</icon>");
            if (StringUtils.isEmpty(sm.getAppUrl())) {
            	sb.append("<url> </url>");
            } else {
				sb.append("<url><![CDATA[" + sm.getAppUrl() + "]]></url>");
            }
            sb.append("<androidurl>" + sm.getClassAndroidUrl() + "</androidurl>");
            sb.append("<iosurl>" + sm.getClassIosUrl() + "</iosurl>");
            if (StringUtils.isEmpty(sm.getWebUrl())) {
            	sb.append("<wxurl> </wxurl>");
            } else {
				sb.append("<wxurl><![CDATA[" + sm.getWebUrl() + "]]></wxurl>");
            }
            sb.append("<fwbm>" + sm.getClassFwbm() + "</fwbm>");
            String apkDownURL = !StringUtil.isEmpty(sm.getFileId()) ? 
								getImageHost()+"/file/attachement_download.html?model.guId="+sm.getFileId() : "";
            sb.append("<apkdownurl><![CDATA[" + apkDownURL + "]]></apkdownurl>");
           /* String apkfilename = !StringUtil.isEmpty(sm.getFileId()) ? 
					fileAttachementService.getById(sm.getFileId()).getName() : "";
			sb.append("<apkfilename>" + apkfilename + "</apkfilename>");*/
            sb.append("<apkfilename>" + 
					(StringUtils.isEmpty(sm.getApkfilename()) ? "" : sm.getApkfilename()) +
					 "</apkfilename>");
            sb.append("<apkpackage>" + sm.getClassAppyydz() + "</apkpackage>");
            sb.append("<URLScheme>" + sm.getIosURLScheme() + "</URLScheme>");
            sb.append("<URLiTunes><![CDATA[" + sm.getIosURLiTunes() + "]]></URLiTunes>");
            sb.append("<procode>" + sm.getProcode() + "</procode>");
            sb.append("<otherFlag>" + sm.getOtherFlag() + "</otherFlag>");
            sb.append("<iscommon>" + sm.getIscommon() + "</iscommon>");
//            sb.append("<iconId>" + sm.getClassFwtbid() + "</iconId>");
//            sb.append("<appurl>" + sm.getClassAppyydz() + "</appurl>");
            sb.append("<bak>" + "</bak>");
            sb.append("<moduletype>" + sm.getClassSsywxt() + "</moduletype>");
            sb.append("</module>");
        }
        sb.append("</AppTypeItemList>");
        return sb.toString();
    }

    /**
     * 取得帐号的所有业务系统
     */
    @Override
    public String getAllXtYwByUser(String userId) {
        List<Business> xtyws = mobileCommonService.getAllXtYwByUser(userId);
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppYwxtList>");
        for (Business xtyw : xtyws) {
            sb.append("<ywinfo>");
            sb.append("<xtid>" + xtyw.getClassId() + "</xtid>");
            sb.append("<xtbm>" + xtyw.getClassXtbm() + "</xtbm>");
            sb.append("<xtmc>" + xtyw.getClassXtmc() + "</xtmc>");
            sb.append("<procode>" + xtyw.getProcode() + "</procode>");
            sb.append("<otherflag>" + xtyw.getOtherFlag() + "</otherflag>");
            sb.append("</ywinfo>");
        }
        sb.append("</AppYwxtList>");
        return sb.toString();
    }

    /**
     * 添加常用服务
     */
    @Override
    public String SubmitCommonFunction(String userId, String fwBm, boolean isadd) {
    	StringBuilder sb = new StringBuilder();
    	if (fwBm != null && !",".equals(fwBm)) {
    		mobileCommonService.deleteAllFrequentlyService(userId);
    		String[] codes = fwBm.split(",");
    		for (int i = 0; i < codes.length; i++) {
    			mobileCommonService.insertFrequentlyService(userId, codes[i].trim(), isadd);
    		}
    		sb.append("<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>");
    	}
    	
        return sb.toString();
    }

    /**
     * 删除常用服务
     */
    @Override
    public String deleteFrequentlyService(String userId, String strKey, String fwBm) {
        mobileCommonService.deleteFrequentlyService(userId, fwBm);
        StringBuilder sb = new StringBuilder();
        sb.append("<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>");
        return sb.toString();
    }
    
    /**
     * 增加常用服务
     */
    @Override
    public String insertFrequentlyService(String userId, String strKey, String fwBm, boolean isadd) {
        mobileCommonService.insertFrequentlyService(userId, fwBm, isadd);
        StringBuilder sb = new StringBuilder();
        sb.append("<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>");
        return sb.toString();
    }

    /**
     * 更新常用服务
     */
    @Override
    public void modifyFrequentlyService(String userId, String fwBm, String type, int toMark) {
        mobileCommonService.modifyFrequentlyService(userId, fwBm, type, toMark);
    }

    /**
     * 取得资讯类别
     */
	@Override
	public String getNewsTab(String userId, String strKey) {
		String url = getImageHost();
		NewsConfigQuery configQuery = new NewsConfigQuery();
		configQuery.setKey(SERVICE_FRONT);
		NewsConfig config = newsConfigService.findByKey(configQuery);
		String serviceIds = null;
		if (config != null) {
			serviceIds = config.getValue();
		}
		//String serviceIds = MobileSystemHolder.getPropertiesValue("fwids");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("isAll", "true");
		List<NewsCatalog> catalogs = newsCatalogService.getAllCatalog(queryMap);
		StringBuilder sb = new StringBuilder();
		configQuery.setKey(TT_NAME);
		config = newsConfigService.findByKey(configQuery);
		String ttName = "头条";
		if (config != null) {
			ttName = config.getValue();
		}
		//String ttName = MobileSystemHolder.getPropertiesValue("tt_name");
		//if (ttName == null) {
		//	ttName = "头条";
		//}
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
		if (!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(serviceIds)) {
			// List<ServiceManager> services =
			// mobileCommonService.getFrequentlyServiceByUser(userId);
			LoginModel loginModel = new LoginModel();
			loginModel.setYhm(userId);
			User user = loginService.cxYhxx(loginModel);
			String role = user.getYhlx();
			if (!"student".equals(role)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fwids", serviceIds.split(","));
				map.put("userId", userId);
				List<ServiceManager> services = mobileCommonService
						.getLoginFw(map);
				for (ServiceManager service : services) {
					sb.append("<tabname>");
					sb.append("<id>" + service.getClassFwbm() + "</id>");
					sb.append("<catalog>" + service.getClassSsywxt()
							+ "</catalog>");
					sb.append("<name>" + service.getClassFwmc() + "</name>");
					sb.append("<webUrl>" + service.getWebUrl() + "</webUrl>");
					sb.append("<type> </type>");
					sb.append("<listType> </listType>");
					boolean check = false;
					try {
						check = mobileCommonService.checkImage(
								service.getClassFwtbdz(),
								service.getClassFwtbid());
					} catch (IOException e) {
						e.printStackTrace();
					}

					if (check)
						sb.append("<logo>" + url + service.getClassFwtbdz()
								+ "</logo>");
					else
						sb.append("<logo>" + url + "upload/default_image.jpg"
								+ "</logo>");
					sb.append("</tabname>");
				}
			}
		}
		if(!StringUtil.isEmpty(ttName)){
			sb.append("<tabname>");
			sb.append("<id></id>");
			sb.append("<name>"+ttName+"</name>");
			sb.append("<webUrl>web_index.html</webUrl>");
			sb.append("<catalog>TT</catalog>");
			sb.append("<type>" + ShowTypeEnum.TELETEXT_SHOW + "</type>");
			sb.append("<listType>1</listType>");
			sb.append("<logo></logo>");
			sb.append("</tabname>");
		}
		for (NewsCatalog catalog : catalogs) {
			sb.append("<tabname>");
			sb.append("<id>" + catalog.getCatalogId() + "</id>");
			sb.append("<catalog>" + catalog.getCatalogCode() + "</catalog>");
			sb.append("<name>" + catalog.getCatalogName() + "</name>");
			sb.append("<webUrl>web_index.html?catalogId="
					+ catalog.getCatalogCode() + "&amp;type="
					+ catalog.getShowType() + "&amp;listType="
					+ catalog.getListType() + "</webUrl>");
			sb.append("<type>" + catalog.getShowType() + "</type>");
			sb.append("<listType>" + catalog.getListType() + "</listType>");
			boolean check = false;
			try {
				check = mobileCommonService.checkImage(catalog.getLogoUrl(),
						catalog.getLogoId());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (check)
				sb.append("<logo>" + url + catalog.getLogoUrl() + "</logo>");
			else
				sb.append("<logo>" + url + "upload/default_image.jpg"
						+ "</logo>");

			// sb.append("<logo>" + catalog.getLogoUrl() + "</logo>");
			sb.append("</tabname>");
		}

		sb.append("</zfsoft>");
		return sb.toString();
	}

	/**
	 * 根据资讯类别取得推荐资讯
	 * @throws IOException 
	 */
	/*@Override
	public String getMhRecommendPage(String categoryId, int start, int size){
		NewsQuery newsQuery = new NewsQuery();
		if (!StringUtil.isEmpty(categoryId)) {
			newsQuery.setCatalogCode(categoryId);
		}
		newsQuery.setToPage(start);
		newsQuery.setPerPageSize(size);
		PageList<News> news = newsService.getPageList(newsQuery);
		  StringBuilder sb = new StringBuilder();
	        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
	        for (News n : news) {
	            sb.append("<mhrecommend>");
	            sb.append("<id>" + n.getId() + "</id>");
	            sb.append("<title>" + n.getTitle() + "</title>");
	            
	            boolean check = false;
				try {
					check = mobileCommonService.checkImage(n.getPicUrl(), n.getPicId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String url = getImageHost();
				if(check)
					sb.append("<logopath>" + url + n.getPicUrl() + "</logopath>");
				else
					sb.append("<logopath>"  + url + "upload/default_image.jpg" + "</logopath>");
	            
	            //sb.append("<logopath>" + n.getPicUrl() + "</logopath>");
	            sb.append("<url></url>");
	            sb.append("</mhrecommend>");
	        }
	        sb.append("</zfsoft>");
	        return sb.toString();
	}*/

	/**
	 * 根据资讯类别取得资讯（除推荐资讯）
	 * @throws IOException 
	 */
	@Override
	public String getNewsByCategory(String categoryId){
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setCatalogCode(categoryId);
		List<News> news = newsService.getUnRmdByCatalogCode(newsQuery);
		  StringBuilder sb = new StringBuilder();
	        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><table>");
	        for (News n : news) {
	            sb.append("<zxinfo>");
	            sb.append("<data><name>资讯ID</name><value>" + n.getId() + "</value></data>");
	            sb.append("<data><name>资讯标题</name><value>" + n.getTitle() + "</value></data>");
	            sb.append("<data><name>资讯来源</name><value>" + n.getSource() + "</value></data>");
	            sb.append("<data><name>作者</name><value>" + n.getAuthor() + "</value></data>");
	            sb.append("<data><name>资讯简介</name><value>" + n.getIntro() + "</value></data>");
	            sb.append("<data><name>资讯内容</name><value>" + n.getContent() + "</value></data>");
	            sb.append("<data><name>资讯类别</name><value>" + n.getCatalogName() + "</value></data>");
	            String isremotelogo = n.getIsremotelogo();
	        	if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
	            	sb.append("<logopath>" + n.getRemotelogourl() + "</logopath>");
	            }else{
	            	boolean check = false;
	            	try {
	            		check = mobileCommonService.checkImage(n.getPicUrl(), n.getPicId());
	            	} catch (IOException e) {
	            		// TODO Auto-generated catch block
	            		e.printStackTrace();
	            	}
	            	if(check)
	            		sb.append("<data><name>资讯logo</name><value>" + n.getPicUrl() + "</value></data>");
	            	else
	            		sb.append("<data><name>资讯logo</name><value>" + "upload/default_image.jpg" + "</value></data>");
	            }
	            
	            //sb.append("<data><name>资讯logo</name><value>" + n.getPicUrl() + "</value></data>");
	            sb.append("</zxinfo>");
	        }
	        sb.append("</table>");
	        return sb.toString();
	}

	@Override
	public String getMobileAppType(){
		//PageList<Module> modules = moduleService.getPageList(new ModuleQuery());
		List<Module> modules = moduleService.getAllModules();
		String appName = MobileSystemHolder.getPropertiesValue("app_name");
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft><AppType><AppName>").append(appName).append("</AppName></AppType>");
		if ("mh".equals(appName)) {
			for (Module module : modules) {
				sb.append("<ModuleType>");
				sb.append("<BottomTypeID>").append(module.getModuleId()).append("</BottomTypeID>");
				sb.append("<BottomTypeName>").append(module.getName()).append("</BottomTypeName>");
				sb.append("<BottomTypeAddress>").append(module.getClassPath()).append("</BottomTypeAddress>");
				
				boolean check = false;
				try {
					check = mobileCommonService.checkImage(module.getIconUrl(), module.getIcon());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String url = getImageHost();
				if(check)
					sb.append("<BottomTypeLogo>" + url + module.getIconUrl() + "</BottomTypeLogo>");
				else
					sb.append("<BottomTypeLogo>" + url + "upload/default_image.jpg" + "</BottomTypeLogo>");
				
				//sb.append("<BottomTypeLogo>").append(module.getIconUrl()).append("</BottomTypeLogo>");
				sb.append("<BottomTypeUrl>").append(module.getModuleId()).append("</BottomTypeUrl>");
				sb.append("</ModuleType>");
			}
		}
		sb.append("</zfsoft>");
		return sb.toString();
	}

	@Override
	public String getNewsList(String type, int start, int size) throws IOException{
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setStatus("1");
		if ("TT".equals(type)) {
			newsQuery.setHeadline("1");
		} else {
			newsQuery.setCatalogCode(type);
		}
		if (size > 0) {
			newsQuery.setPerPageSize(size);
		}
		if (start >=0) {
			newsQuery.setToPage(start);
		}
		PageList<News> newsList = newsService.getPageList(newsQuery);
		if (newsList.size() <= 0) {
			return "暂无资讯";
		}
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft pagesize=\"");
        sb.append(newsList.getPaginator().getPages() + "\" start=\"" + start + "\" size=\"" + newsList.size() + "\">");
        String url = getImageHost();
        String goUrl = "";
        String vedioUrl = "";
        for (News news : newsList) {
        	goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
        	vedioUrl = url + news.getVedioUrl();
        	sb.append("<news>");
        	sb.append("<id>").append(news.getId()).append("</id>");
        	sb.append("<timecreate>").append(DateTimeUtil.getFormatDate(news.getCreateTime())).append("</timecreate>");
        	//sb.append("<timecreate>").append(news.getTime()).append("</timecreate>");
        	sb.append("<title><![CDATA[").append(news.getTitle()).append("]]></title>");
        	String isremotelogo = news.getIsremotelogo();
        	if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
            	sb.append("<logopath><logo><![CDATA[" + news.getRemotelogourl() + "]]></logo></logopath>");
            	sb.append("<picPath>" + news.getRemotelogourl() + "</picPath>");
            	sb.append("<blogopath><![CDATA[" + news.getRemotelogourl() + "]]></blogopath>");
            }else{
            	
            	sb.append("<logopath>");
            	if(!StringUtil.isEmpty(news.getPicId()) && !StringUtil.isEmpty(news.getPicUrl())){
            		String[] picIds = news.getPicId().split(",");
            		String[] picUrls = news.getPicUrl().split(",");
            		boolean check = false;
            		for (int i = 0; i < picIds.length; i++) {
            			
            			try {
            				logger.error("images:"+picUrls[i]+","+picIds[i]);
            				if(picIds[i].equals("1A894ADC7E504C6D97A6DCEC7E0DC196")){
            				}
            				check = mobileCommonService.checkImage(picUrls[i], picIds[i]);
            			} catch (IOException e) {
            				e.printStackTrace();
            			}finally{
            				
            			}
            			if(check)
            				sb.append("<logo><![CDATA[" + url + picUrls[i] + "]]></logo>");
            			else
            				sb.append("<logo><![CDATA["  + url + "upload/default_image.jpg" + "]]></logo>");
            			
            		}
            	}
            	sb.append("</logopath>");
            	
            }
        	
        	//sb.append("<logopath>").append(news.getPicUrl()).append("</logopath>");
        	sb.append("<introduce>").append(news.getIntro()).append("</introduce>");
        	sb.append("<catalog>").append(news.getCatalogCode()).append("</catalog>");
        	
        	if(Config.getString("newsUrl","no").equals("yes")){
        		if(!StringUtil.isEmpty(news.getNewsURL()))
        			sb.append("<url><![CDATA[").append(news.getNewsURL()).append("]]></url>");
        		else
        			sb.append("<url><![CDATA[").append(goUrl).append("]]></url>");
        			
        	}else{
        		sb.append("<url><![CDATA[").append(goUrl).append("]]></url>");
        	}
        	sb.append("<author>").append(news.getAuthor()==null?"":news.getAuthor()).append("</author>");
        	sb.append("<vedioUrl>").append(vedioUrl+".mp4").append("</vedioUrl>");
        	sb.append("<vedioImage>").append(vedioUrl+".png").append("</vedioImage>");
        	sb.append("</news>");
        	
        }
        sb.append("</zfsoft>");
		return sb.toString();
	}
	
	
	/*public String getNewsList(String type, int start, int size) throws IOException{
		NewsQuery newsQuery = new NewsQuery();
		newsQuery.setStatus("1");
		if ("TT".equals(type)) {
			newsQuery.setHeadline("1");
		} else {
			newsQuery.setCatalogCode(type);
		}
		if (size > 0) {
			newsQuery.setPerPageSize(size);
		}
		if (start >=0) {
			newsQuery.setToPage(start);
		}
		PageList<News> newsList = newsService.getPageList(newsQuery);
		if (newsList.size() <= 0) {
			return "暂无资讯";
		}
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft pagesize=\"");
        sb.append(newsList.getPaginator().getPages() + "\" start=\"" + start + "\" size=\"" + newsList.size() + "\">");
        String url = getImageHost();
        String goUrl = "";
        for (News news : newsList) {
        	goUrl = url + "mobile/web_content.html?newsId=" + news.getId();
        	sb.append("<news>");
        	sb.append("<id>").append(news.getId()).append("</id>");
        	sb.append("<timecreate>").append(DateTimeUtil.getFormatDate(news.getCreateTime())).append("</timecreate>");
        	sb.append("<title><![CDATA[").append(news.getTitle()).append("]]></title>");
        	String isremotelogo = news.getIsremotelogo();
        	if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
            	sb.append("<logopath>" + news.getRemotelogourl() + "</logopath>");
            	sb.append("<picPath>" + news.getRemotelogourl() + "</picPath>");
            	sb.append("<blogopath><![CDATA[" + news.getRemotelogourl() + "]]></blogopath>");
            }else{
            	boolean check = false;
            	try {
            		check = mobileCommonService.checkImage(news.getPicUrl(), news.getPicId());
            	} catch (IOException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
            	if(check)
            		sb.append("<logopath><![CDATA[" + url + news.getPicUrl() + "]]></logopath>");
            	else
            		sb.append("<logopath><![CDATA["  + url + "upload/default_image.jpg" + "]]></logopath>");
            	if(check)
    				sb.append("<picPath>" + url + news.getPicUrl() + "</picPath>");
    			else
    				sb.append("<picPath>"  + url + "upload/default_image.jpg" + "</picPath>");
            	String path = mobileCommonService.getUploadImagePath(news.getPicId());
            	boolean checkYuanTu =  mobileCommonService.checkImage(path, news.getPicId());
            	if(checkYuanTu)
    				sb.append("<blogopath><![CDATA[" + url + path + "]]></blogopath>");
    			else
    				sb.append("<blogopath><![CDATA["  + url + "upload/default_image.jpg" + "]]></blogopath>");
            }
        	
        	//sb.append("<logopath>").append(news.getPicUrl()).append("</logopath>");
        	sb.append("<introduce>").append(news.getIntro()).append("</introduce>");
        	sb.append("<catalog>").append(news.getCatalogCode()).append("</catalog>");
        	
        	if(Config.getString("newsUrl","no").equals("yes")){
        		if(!StringUtil.isEmpty(news.getNewsURL()))
        			sb.append("<url>").append(news.getNewsURL()).append("</url>");
        		else
        			sb.append("<url>").append(goUrl).append("</url>");
        			
        	}else{
        		sb.append("<url>").append(goUrl).append("</url>");
        	}
        	sb.append("<author>").append(news.getAuthor()==null?"":news.getAuthor()).append("</author>");
        	sb.append("</news>");
        	
        }
        sb.append("</zfsoft>");
		return sb.toString();
	}*/

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
	
	
	/**
	 * 对ca认证过来的信息进行xml解析判断是否登录成功
	 * @throws DocumentException 
	 */
	public boolean containErrorCode(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
        Element elementTemplate = document.getRootElement();  
        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo"); 
        Element code = (Element)elementTemplate.selectSingleNode("//code"); 

		if(ResultInfo != null && code != null){
			String errorCode = elementTemplate.elementText("code");
			if(errorCode != null && !errorCode.equals(""))
				return true;
		}
		
		return false;
	}
	
	public boolean containBindErrorCode(String xml){
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
	        Element elementTemplate = document.getRootElement();  
	        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo"); 
	        Element code = (Element)elementTemplate.selectSingleNode("//code"); 
	
			if(ResultInfo != null && code != null ){
				String errorCode = elementTemplate.elementText("code");
				if(errorCode != null && !errorCode.equals("") && errorCode.equals("1"))
					return true;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public String getErrorCode(String xml) throws DocumentException{
			Document document = DocumentHelper.parseText(xml);
	        Element elementTemplate = document.getRootElement();  
	        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo"); 
	        Element code = (Element)elementTemplate.selectSingleNode("//code"); 
	        String errorCode = null;
			if(ResultInfo != null && code != null ){
				errorCode = elementTemplate.elementText("message");
			}
			return errorCode;
	}
	
	public boolean containRightCode(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
        Element elementTemplate = document.getRootElement();  
        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//ResultInfo"); 
        Element code = (Element)elementTemplate.selectSingleNode("//code"); 

		if(ResultInfo != null && code != null){
			String errorCode = elementTemplate.elementText("code");
			if(errorCode != null && !errorCode.equals("") && errorCode.equals("201"))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 对ca认证过来的信息进行xml解析map返回
	 * @throws DocumentException 
	 */
	public Map<String, String> getMapValue(String xml) throws DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
        Element elementTemplate = document.getRootElement();  
        Element bm = (Element)elementTemplate.selectSingleNode("//BM"); 

		if(bm != null && bm.getText() != null){
				map.put("BM", bm.getText());
		}
		
		return map;
		
		
	}
	
	
	/**
	 * 直接根据用户名userid获取头像路径方法
	 * @param userName
	 * @return
	 */
	public String getHeadPictureByUserId(String userName){
		String headPath = null;
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			return headPath;
		}
			
		String path = image.getPath();
		byte[] content = image.getFileContent();
		String headname = image.getFileName();
		String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(content == null && StringUtil.isEmpty(path)){
			return headPath;
		}else{
			String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
			String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
			File newFile = new File(pathurl);
			if (!newFile.exists()) {
				newFile.mkdir();
			}
			String url = request.getSession().getServletContext().getRealPath("/") + path;
			url = url.replace("\\", "/");
			File outFile = new File(url);
			if (!outFile.exists()) {
					try {
						outFile.createNewFile();
					
						if(content == null){
							return headPath;
						}
						ImageIO.setUseCache(false);
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
						BufferedImage newImage = ImageIO.read(byteArrayInputStream);
						ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} 
			headPath = getImageHost()+path;
		}
		return headPath;
	}
	
	/**
	 * 阿里百川是否注册验证，没注册则注册，主要为移动端IM聊天功能服务
	 * @param userId
	 * @param name
	 */
	public void signUp(String userId, String name){
		try {
		TaobaoClient client = new DefaultTaobaoClient(
				Config.getString("alibaiUrl"), 
				Config.getString("alibaiappkey"), 
				Config.getString("alibaisecret")
				);
		boolean exits = false;
		//验证是否已经在阿里百川存在此帐号
		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
		req.setUserids(userId);
		OpenimUsersGetResponse rsp;
			rsp = client.execute(req);
			String bodyData = rsp.getBody();
			logger.error("阿里百川是否存在用户数据：userId="+userId+",response="+bodyData);
			net.sf.json.JSONObject object =  net.sf.json.JSONObject.fromObject(bodyData);
			net.sf.json.JSONObject userinfos = object.optJSONObject("openim_users_get_response").optJSONObject("userinfos");
			if(userinfos != null || userinfos.size() == 1 ){
				exits = true;
			}
			//System.out.println(userinfos == null || userinfos.size() == 0 ? 0 : 1);
			//如果已经存在，则不走下面的接口，不存在则注册
			TaobaoClient clientAdd = new DefaultTaobaoClient(
					Config.getString("alibaiUrl"), 
					Config.getString("alibaiappkey"), 
					Config.getString("alibaisecret")
			);
			OpenimUsersAddRequest reqAdd = new OpenimUsersAddRequest();
			List<Userinfos> list2 = new ArrayList<Userinfos>();
			Userinfos obj3 = new Userinfos();
			list2.add(obj3);
			obj3.setNick(name);
			String headPicturePath = getHeadPictureByUserId(userId);
			if(!StringUtil.isEmpty(headPicturePath)){
				obj3.setIconUrl(headPicturePath);
			}
			obj3.setUserid(userId);
			obj3.setPassword(Config.getString("alibaiPassword"));
			obj3.setName(name);
			reqAdd.setUserinfos(list2);
			OpenimUsersAddResponse rspAdd;
			rspAdd = clientAdd.execute(reqAdd);
			logger.error(rspAdd.getBody());	
			String bodyDataAdd = rspAdd.getBody();
			net.sf.json.JSONObject objectAdd = net.sf.json.JSONObject.fromObject(bodyDataAdd);
			net.sf.json.JSONObject userinfosAdd = objectAdd.optJSONObject("openim_users_add_response").optJSONObject("uid_succ");
			if(userinfosAdd != null || userinfosAdd.size() == 1){
				logger.error("阿里百川帐号注册成功-success：userId="+userId+",response="+bodyDataAdd);
			}
			logger.error(userinfosAdd == null || userinfosAdd.size() == 0 ? 0 : 1);
		} catch (ApiException e) {
			logger.error("阿里百川异常-alibaichuan：", e);
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public String login(String userName, String password, String sign) {
		StringBuilder sb = new StringBuilder();
		if (Authentication.authenticate(sign)) {
			LoginModel model = new LoginModel();
			model.setYhm(userName);
			model.setMm(password);
			Map<String, String> map = new HashMap<String, String>();
			
			
			if(!StringUtil.isEmpty(Config.getString("webservice.host.mh")) 
					&& StringUtil.isEmpty(Config.getString("qymm"))){//旧认证登录接口，进行兼容
				String zfxml = "";
				try {
					JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
					factory.setServiceClass(ICaService.class);
					factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
					factory.getInInterceptors().add(new LoggingInInterceptor());
					factory.getOutInterceptors().add(new LoggingOutInterceptor());
					ICaService service = (ICaService) factory.create();
					String Xtmdlst = Config.getString("BusinessSystemNum"); 
					zfxml =service.getTicket2(userName, password, Xtmdlst, "");
				} catch (Exception e) {
					logger.error(e);
					sb.append("<ResultInfo><code>404</code><message>认证接口登录异常！</message></ResultInfo>");
					return sb.toString();
				}
					boolean canLogin = false;
					try {
						canLogin = containErrorCode(zfxml);
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					if(canLogin){
						sb.append("<ResultInfo><code>404</code><message>帐号或密码不正确！</message></ResultInfo>");
						return sb.toString();
					}else{
						try {
							map = getMapValue(zfxml);//获取ca认证返回过来的值
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				//System.out.println("--------setMm(null)---------");
				if(!StringUtil.isEmpty(Config.getString("webservice.host.mh")) || 
						!StringUtil.isEmpty(Config.getString("qymm"))){
					logger.error("-----------enter setmm null------------");
					model.setMm(null);
					
				}else{
					DBEncrypt p = new DBEncrypt();
					model.setMm(p.dCode(model.getMm().getBytes()));
				}
				User user = loginService.cxYhxx(model);
				
				YhglModel yhglModel = new YhglModel();
				yhglModel.setZgh(userName);
				String keyCode = null;
				try {
					keyCode = MD5Util.md5Encode(
											userName + 
											Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
											System.currentTimeMillis()
										);
					if(StringUtils.isEmpty(keyCode))
						throw new Exception("产生秘钥异常!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				yhglModel.setStrKey(keyCode);
				loginService.updateStrKey(yhglModel);
				
				
				
				
				if (user != null) {
					if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
						sb.append("<ResultInfo><code>404</code><message>帐号没有被启用！</message></ResultInfo>");
						return sb.toString();
					}
					String dqxnxq = "";
					String loginXML = "";
					if(!StringUtil.isEmpty(Config.getString("webservice.host.jw"))){
						try{
							final String AND = "&";
							final String alone = "N";
							String parameterList = userName + AND + password + AND + alone;
							String jwKey = MiddleWareUtil.getJWSign(parameterList);
							ILogin login = (ILogin) WebServiceUtil.createFactoryJw(
									WebServiceConf.SERVICE_JWSERVICE_LOGIN, ILogin.class, "Login")
									.create();
							loginXML = login.login(parameterList, user.getYhlx().equals("")||user.getYhlx().equals("teacher") ? "js" : "xs", jwKey);
							try {
								Document document = DocumentHelper.parseText(loginXML);
								Element elementTemplate = document.getRootElement();  
								Element xq = (Element)elementTemplate.selectSingleNode("//dqxnxq"); 
								
								if(xq != null && xq.getText() != null){
									dqxnxq = xq.getText();
								}
							} catch (DocumentException e) {
								
								e.printStackTrace();
							} 
						} catch (Exception e) {
							
						}
					}
					String appName = MobileSystemHolder.getPropertiesValue("app_name");
					
					sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft><mh>");
					sb.append("<xm>").append(user.getXm()).append("</xm>");
					if ("student".equals(user.getYhlx())) {
						sb.append("<role>XS</role>");
					} else {
						sb.append("<role>JS</role>");
					}
					if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
						sb.append("<bm>").append(map.containsKey("BM") ? map.get("BM") : null).append("</bm>");
					}else{
						sb.append("<bm>").append(user.getBmmc()).append("</bm>");
					}
					sb.append("<yhm>").append(user.getYhm()).append("</yhm>");
					sb.append("<appname>").append(appName).append("</appname>");
					sb.append("<xy></xy>");
					sb.append("<zydm></zydm>");
					sb.append("<zymc></zymc>");
					sb.append("<bj></bj>");
					sb.append("<nj></nj>");
					if(!StringUtil.isEmpty(Config.getString("webservice.host.jw"))){
						sb.append("<dqxnxq>"+dqxnxq+"</dqxnxq>");
					}else{
						sb.append("<dqxnxq></dqxnxq>");
					}
					sb.append("<app_token>" + keyCode + "</app_token>");
					
					NewsConfigQuery configQuery = new NewsConfigQuery();
					configQuery.setKey(SHARE_ADDRESS);
					NewsConfig config = newsConfigService.findByKey(configQuery);
					String share_address = null;
					if (config != null) {
						share_address = config.getValue();
					}
					sb.append("<share_address>" + share_address + "</share_address>");
					
					
					sb.append("</mh></zfsoft>");
				}else{
					if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
						sb.append("<ResultInfo><code>404</code><message>后台显示帐号为空，但认证登录正确！</message></ResultInfo>");
					}else{
						sb.append("<ResultInfo><code>404</code><message>此帐号密码与移动信息服务管理平台不匹配！</message></ResultInfo>");
					}
				}
				logger.error("--------end---------");
				
			//System.out.println("--------sb---------"+sb.toString()+"--------sb---------");
				
			if(!StringUtil.isEmpty(Config.getString("alibaichuan"))
					&& Config.getString("alibaichuan").equals("yes")){
				signUp(userName, user.getXm());//阿里百川注册帐号，为移动端IM模块服务
			}
			return sb.toString();
			
			
			/*User user = loginService.cxYhxx(model);
			
			if (user != null) {
				String appName = MobileSystemHolder.getPropertiesValue("app_name");
				
				sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft><mh>");
				sb.append("<xm>").append(user.getXm()).append("</xm>");
				if ("student".equals(user.getYhlx())) {
					sb.append("<role>XS</role>");
				} else {
					sb.append("<role>JS</role>");
				}
				sb.append("<bm>").append(user.getBmmc()).append("</bm>");
				sb.append("<yhm>").append(user.getYhm()).append("</yhm>");
				sb.append("<appname>").append(appName).append("</appname>");
				sb.append("<xy></xy>");
				sb.append("<zydm></zydm>");
				sb.append("<zymc></zymc>");
				sb.append("<bj></bj>");
				sb.append("<nj></nj>");
				sb.append("<dqxnxq>2014-2015-2</dqxnxq>");
				sb.append("</mh></zfsoft>");
			} else {
				sb.append("<ResultInfo><code>404</code><message>帐号名或密码不正确！</message></ResultInfo>");
			}
			return sb.toString();*/
		}
		return "对不起，您无权访问";
	}
	
	@Override
	public String submitSuggestion(String userName, String suggestion,
			String strKey) {
		Gson gson = new Gson();
		SuggestEntity entity = gson.fromJson(suggestion, SuggestEntity.class);
		// TODO Auto-generated method stub
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userName);
		param.put("schoolName", entity.getSchoolName());
		param.put("suggestContent", entity.getSuggestContent());
		param.put("version", entity.getVersion());
		mobileCommonService.submitSuggestion(param);
		StringBuilder sb = new StringBuilder();
        sb.append("<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>");
        return sb.toString();
		
	}
	
	@Override
	public String myPortalFunction(String userName, String strKey) {
		//TODO:暂时先不处理帐号角色
		List<MyPortal> apps = myPortalService.getAllMyPortal(userName);
		apps = mobileCommonService.getPortalUrl(apps, userName);
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userName);
		Map<String, Object> retMap = null ;
		/*try {
			retMap = mobileCommonService.getPortalInfo(param);
		} catch (Exception e) {
			logger.error(e);
		}*/
		String url = getImageHost();
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><AppTypeItemList>");
		if (apps != null && apps.size() > 0) {
			for (MyPortal app : apps) {
				sb.append("<module><id>").append(app.getId()).append("</id>");
				String type = app.getType();
				type = type.equals("1") ? "WEB_SERVICE" : 
					   type.equals("0") ? "APP_SERVICE" : " FROM_OTHER";		  
				sb.append("<type>").append(type).append("</type>");
				sb.append("<name>").append(app.getName()).append("</name>");
				/*if ("903".equals(app.getCode())) {
					if(retMap!=null && retMap.get("WHS") != null){
						sb.append("<itemName>未还图书</itemName>");
						sb.append("<itemValue>").append(retMap.get("WHS").toString()).append("</itemValue>");
						sb.append("<itemUnit>本</itemUnit>");
					}else{
						sb.append("<itemName>未还图书</itemName>");
						sb.append("<itemValue>0</itemValue>");
						sb.append("<itemUnit>本</itemUnit>");
					}
				}else if ("506".equals(app.getCode())) {
					if(retMap!=null && retMap.get("YUE") != null){
						sb.append("<itemName>余额</itemName>");
						sb.append("<itemValue>").append(retMap.get("YUE").toString()).append("</itemValue>");
						sb.append("<itemUnit>元</itemUnit>");
					}else{
						sb.append("<itemName>余额</itemName>");
						sb.append("<itemValue>0</itemValue>");
						sb.append("<itemUnit>元</itemUnit>");
					}
					
				} else if ("904".equals(app.getCode())) {
					if(retMap!=null && retMap.get("YUE") != null){
						sb.append("<itemName>余额</itemName>");
						sb.append("<itemValue>").append(retMap.get("YUE").toString()).append("</itemValue>");
						sb.append("<itemUnit>元</itemUnit>");
					}else{
						sb.append("<itemName>余额</itemName>");
						sb.append("<itemValue>0</itemValue>");
						sb.append("<itemUnit>元</itemUnit>");
					}
					
				} 
				else if ("906".equals(app.getCode()) && retMap!=null && retMap.get("SFGZ") != null) {
					sb.append("<itemName>收入</itemName>");
					sb.append("<itemValue>").append(retMap.get("SFGZ").toString()).append("</itemValue>");
					sb.append("<itemUnit>元</itemUnit>");
					sb.append("<itemName></itemName>");
					sb.append("<itemValue>").append("</itemValue>");
					sb.append("<itemUnit></itemUnit>");
				} 
				else {
					sb.append("<itemName> </itemName>");
					sb.append("<itemValue> </itemValue>");
				}*/
				sb.append("<itemName> </itemName>");
				sb.append("<itemValue> </itemValue>");
				sb.append("<icon><![CDATA[").append(url + app.getTburl()).append("]]></icon>");
				sb.append("<url><![CDATA[").append(app.getAddr()).append("]]></url>");
				sb.append("<androidurl> </androidurl>");
				sb.append("<iosurl> </iosurl>");
				sb.append("<wxurl> </wxurl>");
				sb.append("<fwbm>").append(app.getCode()).append("</fwbm>");
				sb.append("<bak> </bak>");
				sb.append("<moduletype> </moduletype></module>");
			}
			
		}
		sb.append("</AppTypeItemList>");
		return sb.toString();
	}
	
	
	
	
	@Override
	public String uploadMyPicture(String userName, String fileName, byte[] content) {
		// TODO Auto-generated method stub
		
		/*File destFile1 = new File("C:\\Users\\Administrator\\Desktop\\1255405379250.jpg");
		try {
			FileOutputStream bbb = new FileOutputStream(destFile1);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] aaaaaaaaaaaaaaaa = Byte_File_Object.getBytesFromFile(destFile1);*/
		
		
		ImageDB ImageDB=new ImageDB();
		ImageDB.setFileName(fileName);
		//ImageDB.setFileContent(aaaaaaaaaaaaaaaa);
		ImageDB.setFileContent(content);
		ImageDB.setOpUser(userName);
		
		String path = BaseHolder.getPropertiesValue("MyPicture","headPicture");
		ImageDB.setPath(path+"/"+fileName);
		
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			mobileCommonService.insertMyPicture(ImageDB);
		}else{
			mobileCommonService.updateMyPicture(ImageDB);
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getSession().getServletContext().getRealPath("/") + path;
		File newFile = new File(url);
		if (!newFile.exists()) {
			newFile.mkdir();
		}
		
		File outFile = new File(url, fileName);
		try {
				if (!outFile.exists()) {
						outFile.createNewFile();
				} else {
					outFile.delete();
					outFile.createNewFile();
				}
				ImageIO.setUseCache(false);
				
				//ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aaaaaaaaaaaaaaaa);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
				BufferedImage newImage = ImageIO.read(byteArrayInputStream);
		        File destFile = new File(url + "/" + fileName);
		        if(destFile.exists()){
		        	destFile.delete();
		        }
				
				ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
        sb.append("<ResultInfo><code>201</code><message>上传头像成功</message></ResultInfo>");
        return sb.toString();
	}
	
	@Override
	public String getMyPicturePath(String userName){
		StringBuilder sb = new StringBuilder();
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			sb.append("<ResultInfo><code>404</code><message>数据库图片不存在，路径也不存在！</message></ResultInfo>");
			return sb.toString();
		}
			
		String path = image.getPath();
		byte[] content = image.getFileContent();
		String headname = image.getFileName();
		String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		/*//下面一段代码看部署路径中的headPicture文件夹中是否存在图片，不存在而数据库中存在则在项目部署文件夹中生成图片
		ImageIO.setUseCache(false);
		String pathUrl = request.getSession().getServletContext().getRealPath("/") + path;
		pathUrl = pathUrl.replace("\\", "/");
		try {
		
			//byte[] aaaaaaaaaaaaaaaa = content;
			ByteArrayInputStream byteArrayInputStreammmmmmmm = new ByteArrayInputStream(content);
			BufferedImage newImageeee = ImageIO.read(byteArrayInputStreammmmmmmm);
	        File destFile = new File(pathUrl);
	        if (!destFile.exists()) {
	        	destFile.createNewFile();
			} else {
				destFile.delete();
				destFile.createNewFile();
			}
			
			ImageIO.write(newImageeee, "jpg", destFile);
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
		if(content == null && StringUtil.isEmpty(path)){
			sb.append("<ResultInfo><code>404</code><message>数据库图片不存在，路径也不存在！</message></ResultInfo>");
		}else{
			String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
			String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
			File newFile = new File(pathurl);
			if (!newFile.exists()) {
				newFile.mkdir();
			}
			String url = request.getSession().getServletContext().getRealPath("/") + path;
			url = url.replace("\\", "/");
			File outFile = new File(url);
			if (!outFile.exists()) {
					try {
						outFile.createNewFile();
					
						if(content == null){
							sb.append("<ResultInfo><code>404</code><message>数据库图片不存在，路径也不存在！</message></ResultInfo>");
							return sb.toString();
						}
						ImageIO.setUseCache(false);
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
						BufferedImage newImage = ImageIO.read(byteArrayInputStream);
						ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} 
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ResultInfo>");
			sb.append("<Path>" + getImageHost()+path  + "</Path></ResultInfo>");
		}
		
		return sb.toString();
		
	}
	
	
	@Override
	public String getPortalInfo(String userId, String strKey) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		Map<String, Object> retMap = mobileCommonService.getPortalInfo(param);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><portal>");
		sb.append("<tsg><whs><name>未还数</name><value>" + (retMap.get("WHS") == null?" ":retMap.get("WHS").toString()) + "</value></whs></tsg>");
		sb.append("<ykt><yue><name>余额</name><value>" + (retMap.get("YUE") == null?" ":retMap.get("YUE").toString()) + "</value></yue></ykt>");
		sb.append("<gz><sfgz><name>实发工资</name><value>" + (retMap.get("SFGZ") == null?" ":retMap.get("SFGZ").toString()) + "</value></sfgz></gz>");
		sb.append("</portal>");
		return sb.toString();
	}
	
	@Override
	public String sign(String code) {
		//return Authentication.sign(code);
		return null;
	}
	
	@Override
	public String getTicket(String userName, String password, String xtdms,
			String remoteUrl, String sign) {
		if (Authentication.authenticate(sign)) {
			try {
				Client client = new Client(new URL(WebServiceConf.SERVICE_MHSERVICE));
				Object[] results = client.invoke("getTicket2", new Object[]{userName,password,xtdms,remoteUrl});
				if (results != null && results.length > 0) {
					return (String)results[0];
				} else {
					return "error";
				}
			} catch (Exception e) {
				return "error";
			}
		}
		return "对不起，您无权访问";
	}
    
    @Override
    public String callInterface(String userName, String code,
    		String sign) {
    	if (Authentication.authenticate(sign)) {
    		InterfaceManagerQuery query = new InterfaceManagerQuery();
    		if (!StringUtil.isEmpty(code)) {
    			query.setClassJkbm(code);
    			InterfaceManager manager = interfaceManagerService.getInterfaceByBM(query);
        		if (manager != null) {
        			return manager.getClassJkdz();
        		}
    		}
    		
    	}
    	return "对不起，您无权访问";
    }

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}

	@Override
	public String getocdetail(String detailtype, String ocid, String startdate,
			String enddate, String pageindex, String pagesize, String strkey) {
		CardBusinessQuery businessQuery = new CardBusinessQuery();
		businessQuery.setOcid(ocid);
		//businessQuery.setStartdate(startdate.trim()+" 00:00:00");
		//businessQuery.setEnddate(enddate.trim()+" 23:59:59");
		businessQuery.setToPage(Integer.parseInt(pageindex));
		businessQuery.setPerPageSize(Integer.parseInt(pagesize));
		
		int count = 0;
		PageList<CardBusinessEntity> list = new PageList<CardBusinessEntity>();
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><OcDetail>");
        
        String isXiBei = Config.getString("isxibei");
		if(!StringUtil.isEmpty(isXiBei) && isXiBei.equals("yes")){
			if(detailtype.equals("1")){
				businessQuery.setDetailtype("1");
				count = mobileCommonService.getdetailCountByXB(businessQuery);//这里开始
				list = mobileCommonService.getdetailListByXB(businessQuery);
			}
			if(detailtype.equals("0")){
				businessQuery.setDetailtype("0");
				count = mobileCommonService.getdetailCountByXB(businessQuery);
				list = mobileCommonService.getdetailListByXB(businessQuery);
			}
		}else{
			if(detailtype.equals("1")){
				count = mobileCommonService.getodetailCount(businessQuery);
				list = mobileCommonService.getodetailList(businessQuery);
			}
			if(detailtype.equals("0")){
				count = mobileCommonService.getcdetailCount(businessQuery);
				list = mobileCommonService.getcdetailList(businessQuery);
			}
		}
		if(list != null && list.size() > 0){
			for(CardBusinessEntity entity : list){
				sb.append("<Item>").
				append("<consumeaspect>").append(entity.getClassSh()).append("</consumeaspect>").
				append("<consumetime>").
				append((new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm")).format(entity.getClassJysj())).
				append("</consumetime>").
				append("<balance>").
				append(entity.getClassYe()).
				append("</balance>");
				/*if(!StringUtil.isEmpty(isXiBei) && isXiBei.equals("yes")){
					sb.append("<outlay>").append(entity.getClassJye()).append("</outlay>");
					
				}else{*/
					sb.append("<outlay>").
					append((detailtype.equals("1")? "-" : "+") + entity.getClassJye()).
					append("</outlay>");
				//}
				sb.append("</Item>");
			}
		}
		
		int pagecount = count / Integer.parseInt(pagesize);

		if ((count % Integer.parseInt(pagesize)) != 0) {
			pagecount++;
		}
		sb.append("<Page>").
		append("<pageindex>").append(pageindex).append("</pageindex>").
		append("<pagesize>").append(pagesize).append("</pagesize>").
		append("<pagecount>").append(pagecount).append("</pagecount>").
		append("</Page></OcDetail>");
		
		return sb.toString();
	}

	@Override
	public String getocbalance(String userid, String strkey) {
		String isXiBei = Config.getString("isxibei");
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><OcBalance>");
		if(!StringUtil.isEmpty(isXiBei) && isXiBei.equals("yes")){
			if(mobileCommonService.getCardKHByXB(userid).size() > 0){
				sb.append("<ocid>" + mobileCommonService.getCardKHByXB(userid).get(0).getClassZh() +"</ocid>");
				sb.append("<balance>" + mobileCommonService.getCardKHByXB(userid).get(0).getClassYe() +"</balance>");
			}else{
				return null;
			}
		}else{
			sb.append("<ocid>" + mobileCommonService.getCardKH(userid) +"</ocid>");
			sb.append("<balance>" + mobileCommonService.getCardNumber(userid) +"</balance>");
		}
        sb.append("</OcBalance>");
        return sb.toString();
	}

	

	


	

	

	

	
	
	
//	public String getNewsTab(){
//		List<NewsCatalog> catalogs = newsCatalogService.getAllCatalog();
//		  StringBuilder sb = new StringBuilder();
//	        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
//	        sb.append("<tabname>");
//            sb.append("<id></id>");
//            sb.append("<name>头条</name>");
//            sb.append("<catalog>TT</catalog>");
//            sb.append("<type>"+ShowTypeEnum.TELETEXT_SHOW+"</type>");
//            sb.append("<listType>1</listType>");
//            sb.append("<logo></logo>");
//            sb.append("</tabname>");
//	        for (NewsCatalog catalog : catalogs) {
//	            sb.append("<tabname>");
//	            sb.append("<id>" + catalog.getCatalogId() + "</id>");
//	            sb.append("<catalog>" + catalog.getCatalogCode() + "</catalog>");
//	            sb.append("<name>" + catalog.getCatalogName() + "</name>");
//	            sb.append("<type>"+catalog.getShowType()+"</type>");
//	            sb.append("<listType>"+catalog.getListType()+"</listType>");
//	            
//	            boolean check = false;
//				try {
//					check = mobileCommonService.checkImage(catalog.getLogoUrl(), catalog.getLogoId());
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String url = getImageHost();
//				if(check)
//					sb.append("<logo>" + url +catalog.getLogoUrl() + "</logo>");
//				else
//					sb.append("<logo>"  + url + "upload/default_image.jpg" + "</logo>");
//	            
//	            //sb.append("<logo>" + catalog.getLogoUrl() + "</logo>");
//	            sb.append("</tabname>");
//	        }
//	        sb.append("</zfsoft>");
//	        return sb.toString();
//	}

	
	/**
	 * 鏍规嵁璧勮绫诲埆鍙栧緱鎺ㄨ崘璧勮
	 * @throws IOException 
	 */
	@Override
	public String getMhRecommendPage(String categoryId, int start, int size){
		NewsQuery newsQuery = new NewsQuery();
		if (!StringUtil.isEmpty(categoryId)) {
			newsQuery.setCatalogCode(categoryId);
		}
		newsQuery.setToPage(start);
		newsQuery.setPerPageSize(size);
		PageList<News> news = newsService.getPageList(newsQuery);
		  StringBuilder sb = new StringBuilder();
	        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
	        for (News n : news) {
	            sb.append("<mhrecommend>");
	            sb.append("<id>" + n.getId() + "</id>");
	            sb.append("<title>" + n.getTitle() + "</title>");
	            String isremotelogo = n.getIsremotelogo();
	            if(!StringUtil.isEmpty(isremotelogo) && isremotelogo.equals("1")){
	            	sb.append("<logopath>" + n.getRemotelogourl() + "</logopath>");
	            }else{
	            	boolean check = false;
	            	try {
	            		check = mobileCommonService.checkImage(n.getPicUrl(), n.getPicId());
	            	} catch (IOException e) {
	            		e.printStackTrace();
	            	}
	            	String url = getImageHost();
	            	if(check)
	            		sb.append("<logopath>" + url + n.getPicUrl() + "</logopath>");
	            	else
	            		sb.append("<logopath>"  + url + "upload/default_image.jpg" + "</logopath>");
	            }
	            
	            //sb.append("<logopath>" + n.getPicUrl() + "</logopath>");
	            sb.append("<url></url>");
	            sb.append("</mhrecommend>");
	        }
	        sb.append("</zfsoft>");
	        return sb.toString();
	}
	
	/**
	 * 获取微博列表
	 */

	@Override
	public String getWeiBoList(int pageindex, int size) {
		// TODO Auto-generated method stub
		WeiBoEntity query = new WeiBoEntity();
		query.setPerPageSize(size);
		query.setToPage(pageindex);
		PageList<WeiBoEntity> list = mobileCommonService.getWeiBoList(query);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
        for(WeiBoEntity entity : list){
        	sb.append("<WeiBoEntity>");
        	sb.append("<wbbh>").append(entity.getWbbh()).append("</wbbh>");
        	sb.append("<wbmc>").append(entity.getWbmc()).append("</wbmc>");
        	if(StringUtil.isEmpty(entity.getAccesstoken())){
    					//设置微博过期标识
    		}
        	Timeline tm = new Timeline();
    		tm.client.setToken(entity.getAccesstoken());
    		StatusWapper status = null;
    		try {
    			status = tm.getUserTimeline();//通过微博接口获取微博发表详细信息
    			/*System.out.println(status.getNextCursor());
    			System.out.println(status.getPreviousCursor());
    			System.out.println(status.getTotalNumber());
    			System.out.println(status.getHasvisible());*/
    		} catch (WeiboException e) {
    			e.printStackTrace();
    		}
    		sb.append("<pagecount>").append(status.getTotalNumber()/20+1).append("</pagecount>");
    		
    		Account am = new Account();
    		am.client.setToken(entity.getAccesstoken());
    		try {
    			JSONObject uidJson = am.getUid();
    			String uid = uidJson.getString("uid").toString().trim();//通过调用微博接口获取uid
    			Users um = new Users();
    			um.client.setToken(entity.getAccesstoken());
    				com.zfsoft.weibo.weibo4j.model.User weiboUser = um.showUserById(uid);
			sb.append("<profile_image_url>").append(weiboUser.getProfileImageUrl()).append("</profile_image_url>");	
			sb.append("<description>").append(weiboUser.getDescription()).append("</description>");	
			sb.append("<background>").append(getImageHost()+ "upload/weibo.jpg").append("</background>");	
    		} catch (WeiboException e) {
    			e.printStackTrace();
    		}catch (JSONException e) {
    			e.printStackTrace();
    		}
        	sb.append("</WeiBoEntity>");
        }
        sb.append("</zfsoft>");
        return sb.toString();
	}
	
	/**
	 * 根据微博编号获取此微博帐号发表过的微博
	 * @param wbbh
	 * @param pageindex
	 * @return
	 */
	@Override
	public String getUserTimeLine(String wbbh,long pageindex) {
		WeiBoEntity query = new WeiBoEntity();
		query.setWbbh(wbbh);
		WeiBoEntity entity = mobileCommonService.getWeiBoList(query).get(0);
		if(StringUtil.isEmpty(entity.getAccesstoken())){
			//设置微博过期标识
		}
		Timeline tm = new Timeline();
		tm.client.setToken(entity.getAccesstoken());
		StatusWapper status = null;
		try {
			status = tm.getUserTimeline();
			/*for(Status s : status.getStatuses()){
				Log.logInfo(s.toString());
			}*/
			/*System.out.println(status.getNextCursor());
			System.out.println(status.getPreviousCursor());
			System.out.println(status.getTotalNumber());
			System.out.println(status.getHasvisible());*/
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		ArrayList<Status> statusList = (ArrayList<Status>) status.getStatuses();
		long totalUnmber = status.getTotalNumber();
		long pagecount =  totalUnmber/20+1;
		if(pageindex > pagecount){
			return null;
		}
		List<Status> newList=new ArrayList<Status>();
		long totalCount = pageindex*20 < totalUnmber ? pageindex*20 : totalUnmber ;
		for(long i=(pageindex-1)*20;i<totalCount;i++){
			newList.add(statusList.get((int) i));
		}
		net.sf.json.JSONArray jsonArr = net.sf.json.JSONArray.fromObject(newList.toArray());
		return jsonArr.toString();
	}

	/**
	 * 根据服务编码对应用做统计
	 * @param fwbm
	 * @return
	 */
	@Override
	public String visitsByFwbm(String fwbm) {
		mobileCommonService.visitsByFwbm(fwbm);
		return "<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>";
	}
	
	/**
	 * 根据服务编码对应用做统计,连接到正方的正式服务器插入操作
	 * @param fwbm
	 * @return
	 */
	@Override
	public String insertVisitsToZFByFwbm(String fwbm,String schoolCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fwbm", fwbm);
		map.put("schoolCode", schoolCode);
		mobileCommonService.insertVisitsToZFByFwbm(map);
		return "<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>";
	}
	
	

	/**
	 * 获取通知公告分类表
	 * @return
	 */
	@Override
	public String getNoticeListNewTableType() {
		List<String> list = mobileCommonService.getNoticeListNewTableType();
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft>");
        for(String type : list){
        	sb.append("<tabname>");
        	sb.append("<name>").append(type).append("</name>");
        	sb.append("</tabname>");
        }
        sb.append("</zfsoft>");
		return sb.toString();
	}

	/**
	 * 获取通知公告列表
	 * @param yhm帐号名
	 * @param type通知公告类型
	 * @param start页码
	 * @param size每页size
	 * @return
	 */
	@Override
	public String getNoticeList(String yhm, String type, String start,
			String size) {
		NoticeInfoEntity query = new NoticeInfoEntity();
		query.setPerPageSize(Integer.parseInt(size));
		query.setToPage(Integer.parseInt(start));
		//query.setContent_sort(type);//西北工业大需要这一行代码,不需要这一行是是教务的通知公告移动我们移动后台来类型不确定
		PageList<NoticeInfoEntity> list = mobileCommonService.getNoticeList(query);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><noticelist>");
        for(NoticeInfoEntity entity : list){
        	sb.append("<notice>");
        	sb.append("<id>").append(entity.getResource_id()).append("</id>");
        	sb.append("<title>").append(entity.getTitle()).append("</title>");
        	sb.append("<time>").append(entity.getCreate_time()).append("</time>");
        	sb.append("<istop>").append(StringUtil.isEmpty(entity.getTopi()) ? "" : entity.getTopi()).append("</istop>");
        	sb.append("<lybm>").append(entity.getBelong_unit()).append("</lybm>");
        	sb.append("</notice>");
        }
        sb.append("<page><sum>").append(list.getPaginator().getPages()).append("</sum></page>");
        sb.append("</noticelist>");
		return sb.toString();
	}

	/**
	 * 获取通知公告详情
	 * @param yhm帐号名
	 * @param id通知公告id
	 * @return
	 */
	@Override
	public String getNoticeInfo(String resource_id) {
		NoticeInfoEntity entity = mobileCommonService.getNoticeInfo(resource_id).get(0);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft><noticeinfoentity>");
        sb.append("<id>").append(entity.getResource_id()).append("</id>");
        sb.append("<zt>").append(entity.getTitle()).append("</zt>");
        sb.append("<time>").append(entity.getCreate_time()).append("</time>");
		//sb.append("<zw><![CDATA[").append(getContent(entity.getResource_id())).append("]]></zw>");
		try {
			String content = entity.getContent() == null ?  "" : new String(entity.getContent(), "GB2312");
			sb.append("<zw><![CDATA[").append(content).append("]]></zw>");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sb.append("<fbbm>").append(entity.getBelong_unit()).append("</fbbm>");
        sb.append("<fjlb>").append("</fjlb>");
        sb.append("<fjid>").append("</fjid>");
        sb.append("<fjsize>").append("</fjsize>");
        sb.append("</noticeinfoentity></zfsoft>");
		return sb.toString();
	}
	
	
	/**
	  * 通过Clob对象返回字符串
	  * @param c
	  * @return
	  */
	 public static String getClobString(Clob c) {  
	        try { 

	            Reader reader=c.getCharacterStream();
	            if (reader == null) { 
	                return null; 
	            } 
	            StringBuffer sb = new StringBuffer(); 
	            char[] charbuf = new char[4096]; 
	            for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf)) { 
	                sb.append(charbuf, 0, i); 
	            } 
	            return sb.toString(); 
	        } catch (Exception e) { 
	            return ""; 
	        } 
	    }
	 
	 public String getContent(String resource_id){
			try {
		        Connection conn = null;
		        PreparedStatement stmt = null;
		        ResultSet rs = null;
		        BufferedReader reader = null;  
		 
		        try {
		            
		            
		            Class.forName("oracle.jdbc.driver.OracleDriver");  
		            //String url = Config.getString("TZGGURL", "jdbc:oracle:thin:@222.24.210.151:1521:oradb");
		            
		            String url = Config.getString("TZGGURL", "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)" +
		            		"(HOST = 222.24.210.151)(PORT = 1521))(LOAD_BALANCE = yes)(FAILOVER = ON)" +
		            		"(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = oradb)(FAILOVER_MODE=(TYPE = SELECT)" +
		            		"(METHOD = BASIC)(RETIRES = 20)(DELAY = 15))))");  
		            String username = Config.getString("TZGGUSER", "idc_u_cw");
		            String password = Config.getString("TZGGPASS", "ykt20160526");
		            conn = DriverManager.getConnection(url, username, password);
		            stmt = conn.prepareStatement("select t.content from dcp_apps.pim_info t  where t.resource_id=?");
		            stmt.setString(1, resource_id);
		            rs = stmt.executeQuery();
		            while (rs.next()) {
		                Clob clob = rs.getClob(1);// java.sql.Clob类型  
		                String clobString = getClobString(clob);
						return clobString;
		            }  
		        } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        finally
		        {							
		            if(rs!= null)
					
							rs.close();
						
		            if(stmt!= null) stmt.close();
		            if(conn != null) conn.close();
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	 }

	/*@Override
	public String getYearWeek(String code) {
		String xnstart = mobileCommonService.getYearWeekStart(code);
		String xnend   = mobileCommonService.getYearWeekEnd(code);
	}*/

	@Override
	public String getTermWeek(String ymd) {
		List<ScalendarEntity> entityList = mobileCommonService.getTermWeek(ymd);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><ScalendarList>");
        if(entityList != null && entityList.size() > 0){
        	for(ScalendarEntity entity : entityList){
        		sb.append("<ScalendarEntity>")
        		.append("<xqstart>").append(entity.getXqstart()).append("</xqstart>")
        		.append("<xqend>").append(entity.getXqend()).append("</xqend>")
        		.append("<stustart>").append(entity.getStustart()).append("</stustart>")
        		.append("<stuend>").append(entity.getStuend()).append("</stuend>")
        		.append("<teastart>").append(entity.getTeastart()).append("</teastart>")
        		.append("<teaend>").append(entity.getTeaend()).append("</teaend>")
        		.append("<count>").append(entity.getCount()).append("</count>")
        		.append("<code>").append(entity.getCode()).append("</code>")
        		.append("</ScalendarEntity>");
        	}
        }
        
        sb.append("</ScalendarList>");
		return sb.toString();
	}

	@Override
	public String getQRcode() {
		String url = getImageHost();
		List<QRcode> list = new PageList<QRcode>();
		QRcode query = new QRcode();
		list = QRcodeService.getList(query);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><QRcodeList>");
		if(list != null && list.size() > 0){
			for(QRcode qrcode : list){
				String imageid = getImageHost() + "/file/file_image.html?fileGuid=" + qrcode.getCodeimageid();
				sb.append("<QRcode>");
        		sb.append("<codename>").append(qrcode.getCodename()).append("</codename>");
        		sb.append("<codeimageid>").append(imageid).append("</codeimageid>");
        		sb.append("<introduction>").append(qrcode.getCodeintroduction()).append("</introduction>");
        		boolean check = false;
				try {
					check = mobileCommonService.checkImage(
							qrcode.getCodelogourl(),
							qrcode.getCodelogoid());
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (check)
					sb.append("<logourl>" + url + qrcode.getCodelogourl()
							+ "</logourl>");
				else
					sb.append("<logourl></logourl>");
        		sb.append("</QRcode>");
			}
		}
		sb.append("</QRcodeList>");
		return sb.toString();
	}
	
	
	@Override
	public String getExamList(String userId, int start,
			int size) {
		ExamPaperEntity query = new ExamPaperEntity();
		query.setPerPageSize(size);
		query.setToPage(start);
		query.setCreater(userId);
		//query.setPapermainid(papermainid);
		PageList<ExamPaperEntity> list = questionService.getExamList(query);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				ExamQuestionEntity questionQuery = new ExamQuestionEntity();
				questionQuery.setPapermainid(list.get(i).getPapermainid());
				List<ExamQuestionEntity>  questionList = questionService.getQuestionList(questionQuery);
				list.get(i).setQuestionList(questionList);
			}
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	/**
	 * 提交问卷答案
	 */
	@Override
	public String submitExamAnswer(String userId, String xml) {
		try {
		org.json.JSONArray array = new org.json.JSONArray(xml);
		String questionid = "";
		for (int i = 0; i < array.length(); i++) {
			
			ExamDyYhEntity entity = new ExamDyYhEntity();
			entity.setYhid(userId);
			entity.setQuestionid(array.getJSONObject(i).optString("key"));
			questionid = array.getJSONObject(i).optString("key");
			entity.setItemvalue(array.getJSONObject(i).optString("value"));
			questionService.insertAnswer(entity);
		}
		String papaermainid = questionService.getPapermainidByQes(questionid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("yhid", userId);
		map.put("papermainid", papaermainid);
		questionService.insertExamYh(map);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		} catch (org.json.JSONException e) {
			e.printStackTrace();
			return "false";
		}
		return "success"; 
	}
	/**
	 * 提交问卷发布
	 */
	@Override
	public String submitExam(String userId,String xml) {
		//System.out.println(xml);
		//xml = xml.replaceAll ("r\\\\n", "\\\\\\r\\\\\\\\\\n");
		//System.out.println(xml);
		 Gson gson = new Gson();
		 Questionnaire questionnaire = gson.fromJson(xml, Questionnaire.class);
		 String qn_intro   = questionnaire.getQn_intro();
		 String qn_marking = questionnaire.getQn_marking();
		 String qn_name = questionnaire.getQn_name();
		 String qn_owner = questionnaire.getQn_owner();
		 ExamPaperEntity entity = new ExamPaperEntity();
		 entity.setPapermainname(qn_name);
		 entity.setInstruction(qn_intro);
		 entity.setRemark(qn_marking);
		 entity.setCreater(userId);
		 entity.setCreatetime(new Date());
		 entity.setQn_owner(qn_owner);
		 questionService.insert(entity);
		 String papermainid = null;
		 papermainid = entity.getPapermainid();
		
		 ArrayList<TopicForSubmit>  list = questionnaire.getTopics();
			
		 for(int i = 0; i < list.size(); i++){
			 ExamQuestionEntity questionEntity = new ExamQuestionEntity();
			 questionEntity.setTitle(list.get(i).getTitle());
			 questionEntity.setType(String.valueOf(list.get(i).getType()));
			 questionEntity.setItems(list.get(i).getSelection());
			 questionEntity.setMaxItem(String.valueOf(list.get(i).getMaxSel()));
			//String maxSort = questionService.getMaxSort(papermainid);
			 questionEntity.setSort(String.valueOf(i+1));
			 questionEntity.setPapermainid(papermainid);
			 questionService.insertQuestion(questionEntity);
		 }
	
        return "success";
		/*try {
		    SAXReader reader = new SAXReader();
	        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
	        Document doc = reader.read(stream);
	        Element root = doc.getRootElement();
	        Element foo = null;
	        String papermainid = null;
	        for (Iterator<?> i = root.elementIterator("exampapermain"); i.hasNext();) {
	        	foo = (Element) i.next();
	        	ExamPaperEntity entity = new ExamPaperEntity();
	        	Element nameNode = (Element)foo.selectSingleNode("//papermainname"); 
				if(nameNode != null)
					entity.setPapermainname(foo.elementText("papermainname"));
				Element instructionNode = (Element)foo.selectSingleNode("//instruction"); 
				if(instructionNode != null)
					entity.setInstruction(foo.elementText("instruction"));
				Element remarkNode = (Element)foo.selectSingleNode("//remark"); 
				if(remarkNode != null)
					entity.setRemark(foo.elementText("remark"));
				entity.setCreater(userId);
				entity.setCreatetime(new Date());
				questionService.insert(entity);
				papermainid = entity.getPapermainid();
	        }
	        for (Iterator<?> i = root.elementIterator("exampaperqes"); i.hasNext();) {
	        	foo = (Element) i.next();
	        	ExamQuestionEntity entity = new ExamQuestionEntity();
	        	Element titleNode = (Element)foo.selectSingleNode("//title"); 
				if(titleNode != null)
					entity.setTitle(foo.elementText("title"));
				Element typeNode = (Element)foo.selectSingleNode("//type"); 
				if(typeNode != null)
					entity.setType(foo.elementText("type"));
				Element itemsNode = (Element)foo.selectSingleNode("//items"); 
				if(itemsNode != null)
					entity.setItems(foo.elementText("items"));
				String maxSort = questionService.getMaxSort(papermainid);
				entity.setSort(maxSort);
				entity.setPapermainid(papermainid);
				questionService.insertQuestion(entity);
	        }
	        
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		StringBuilder sb = new StringBuilder();
        sb.append("<ResultInfo><code>201</code><message>操作成功</message></ResultInfo>");
        return sb.toString();*/
	}
	
	
	
	public void setQRcodeService(IQRcodeService qRcodeService) {
		QRcodeService = qRcodeService;
	}

	public IQRcodeService getQRcodeService() {
		return QRcodeService;
	}

	@Override
	public String getMapList() {
		GaoDeMaoEntity query = new GaoDeMaoEntity();
		List<GaoDeMaoEntity> mapList = gaoDeMapService.getList(query);
		JSONArray jsonArray = JSONArray.fromObject(mapList);
		return jsonArray.toString();
	}

	public void setGaoDeMapService(IGaoDeMapService gaoDeMapService) {
		this.gaoDeMapService = gaoDeMapService;
	}

	public IGaoDeMapService getGaoDeMapService() {
		return gaoDeMapService;
	}

	@Override
	public String versionCompare(String imei, String imsi, String sysinfo,
			String ua, String phonum, String account, String v) {
		long mt_bgn = java.util.Calendar.getInstance().getTimeInMillis(); 
		String ret=  "";
		List indexlist = new ArrayList();
		VersionCommon vercom = new VersionCommon();
		String updateTpye= Constants.VERSION_STATE3;
		try {
			String[] vs = v.split("-");
			String ver = vs[3];//获取客户端提交版本号
			String ter = vs[2];//获取客户端平台类型
			String terrace = "";
			if(ter.equals(Constants.ANDROID)){
				terrace = "0";
			}else if(ter.equals(Constants.IPHONE)){
				terrace = "1";
			}else if(ter.equals(Constants.IPAD)){
				terrace = "2";
			}
			IVersionManagerService versionManagerService = (IVersionManagerService) SpringHolder.getBean("versionManagerService");
			VersionManager versionQuery = new VersionManager();
			versionQuery.setSchoolcode(vs[0]);
			versionQuery.setTypecode(vs[1]);
			versionQuery.setTerrace(terrace);
			VersionManager version = versionManagerService.getList(versionQuery).get(0);//根据学校编号、类型编号、平台获取对应版本号
			if(version==null || StringUtil.isEmpty(ver)) return ret = "<?xml version='1.0' encoding='UTF-8'?> <ZFSOFT>传值出错</ZFSOFT>";
			if(StringUtil.isEmpty(version.getVersionnum())) return ret = "<?xml version='1.0' encoding='UTF-8'?> <ZFSOFT>传值出错</ZFSOFT>";
			//获取客户端的版本号分解
			String clientVS = ver.substring(1);
			clientVS = StringUtil.replace(clientVS,".","#");
			String[] clientVersions= clientVS.split("#");
			//获取服务端最新的版本号分解
			String serveVS = version.getVersionnum().substring(1);
			serveVS = StringUtil.replace(serveVS,".","#");
			String[] serveVersions = serveVS.split("#");
			
			if(clientVS.equals(serveVS)){
				updateTpye = Constants.VERSION_STATE3;
			}else{
				if(Integer.parseInt(clientVersions[0])<Integer.parseInt(serveVersions[0])){//客户端大版本号小于服务器端版本号， 大版本强制更新
					updateTpye = Constants.VERSION_STATE1;
				} else if(clientVersions[0].equals(serveVersions[0])) {//客户端大版本号等于服务器端版本号
					if(Integer.parseInt(clientVersions[1])<Integer.parseInt(serveVersions[1])){//小版本推荐更新
						updateTpye = Constants.VERSION_STATE2;
					}
				} else {//客户端大版本号大于服务器端版本号
					updateTpye = Constants.VERSION_STATE3;
				}
			}
			
			//封装
			vercom.setPrompt(version.getUpgradeprompt());
			vercom.setServeAddress(version.getServeraddress());
			vercom.setUpdateTpye(updateTpye);
			vercom.setUrl(version.getFileaddress());
			indexlist.add(vercom);
			
			String[] output = new String[]{"url","prompt","updateTpye","serveAddress"};//需要输出的字段
		
			ret =  XmltoString.xmlToStringNew(output, SelectItems.getReflectObjPropertyValue(indexlist, VersionCommon.class,output), "VERSION");
			
			//计算调用服务的时间差
			long mt_end = java.util.Calendar.getInstance().getTimeInMillis(); 
			logger.error("["+DateUtil.toDateFormatString(new Date(),"yyyy-MM-dd HH:mm:ss")+"]WebService方法<versionCompare>执行时间差（毫秒）："+(mt_end - mt_bgn));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ret = "<?xml version='1.0' encoding='UTF-8'?> <ZFSOFT>传值出错</ZFSOFT>";
		}
		return ret;
	}

	
	
	

	public static void main(String[] args) {
		ArrayList<TopicForSubmit> topics = new  ArrayList<TopicForSubmit>();
		TopicForSubmit topicEntity = new TopicForSubmit("你要选哪个", 0, "选项1/n选项2/n选项3", 1);
		topics.add(topicEntity);
		Questionnaire entity = new Questionnaire("test", "test", "test", "1",topics);
		Gson gson = new Gson();
		logger.error(gson.toJson(entity));
	}

	public void setLossObjectService(ILossObjectService lossObjectService) {
		this.lossObjectService = lossObjectService;
	}

	public ILossObjectService getLossObjectService() {
		return lossObjectService;
	}

	@Override
	public String getLossObjectList(String userid, String title, String isover,int start, int size) {
		Gson gson = new Gson();
		LossObjectEntity query = new LossObjectEntity();
		query.setUsername(userid);
		query.setTitle(title);
		if(!StringUtil.isEmpty(isover))	query.setIspass("2");
		query.setIsover(isover);
		query.setToPage(start);
		query.setPerPageSize(size);
		query.setIspass("4");
		PageList<LossObjectEntity> list = lossObjectService.getList(query);
		return gson.toJson(list);
	}

	@Override
	public String publishLossObject(String pulishuser, String title,
			String place, String content,String flag) {
		LoginModel model = new LoginModel();
		model.setYhm(pulishuser);
		User user = loginService.cxYhxx(model);
		LossObjectEntity entity = new LossObjectEntity();
		entity.setUsername(pulishuser);
		entity.setName(user.getXm());
		entity.setContent(content);
		entity.setTitle(title);
		entity.setPlace(place);
		entity.setFlag(flag);
		lossObjectService.insert(entity);
		return "{\"success\":\"200\", \"msg\":\"发布成功\"}";
	}

	@Override
	public String getMyMemoList(String userId, int start, int size,
			String title, String memoCatalogName) {
		MemoDB memoQuery = new MemoDB();
		memoQuery.setToPage(start);
		memoQuery.setPerPageSize(size);
		memoQuery.setMemoTitle(title);
		memoQuery.setUserName(userId);
		memoQuery.setMemoCatalogName(memoCatalogName);
		PageList<MemoDB> memoPageList = mobileCommonService.getMyMemoList(memoQuery);
		for(int i = 0; i < memoPageList.size(); i++){
			if(mobileCommonService.checkMemo(memoPageList.get(i))){
				/*try {
					String s = new String(memoPageList.get(i).getMemoContent(), "UTF-8");
					System.out.println("content:" + s);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				memoPageList.get(i).setMemoContent(null);
				memoPageList.get(i).setMemoPath(getImageHost()+memoPageList.get(i).getMemoPath());
				
				mobileCommonService.checkMemoPictureEntity(memoPageList.get(i).getMemoFileName());
			}
		}
		Gson gson = new Gson();
		return gson.toJson(memoPageList);
	}
	

	@Override
	public String submitMemoCatalogList(String userId,
			String memoCatalogNameList, String memoCatalogColorList) {
		mobileCommonService.deleteAllmemoCatalogByUser(userId);
		String[] catalogNameList = memoCatalogNameList.split(",");
		String[] catalogColorList = memoCatalogColorList.split(",");
		MemoCatalog memoCatalog;
		List<MemoCatalog> memoCatalogList = null;
		if(StringUtil.isEmpty(memoCatalogNameList)){
			mobileCommonService.deleteAllmemoCatalogByUser(userId);
			memoCatalogList = new ArrayList<MemoCatalog>();
			memoCatalogList = mobileCommonService.getMemoCatalogList(userId);
			Gson gson = new Gson();
			return gson.toJson(memoCatalogList);
		}
		for(int i = 0; i < catalogNameList.length; i++){
			if(catalogNameList[i].equals("旅游") || catalogNameList[i].equals("个人") 
					|| catalogNameList[i].equals("生活") || catalogNameList[i].equals("工作")
					|| catalogNameList[i].equals("未标签"))	continue;
			memoCatalog = new MemoCatalog();
			memoCatalog.setCatalogColor(catalogColorList[i]);
			memoCatalog.setMemoCatalogName(catalogNameList[i]);
			memoCatalog.setUserName(userId);
			memoCatalog.setSortNumber(i+6);
			mobileCommonService.insertmemoCatalogByUser(memoCatalog);
		}
		
		memoCatalogList = new ArrayList<MemoCatalog>();
		memoCatalogList = mobileCommonService.getMemoCatalogList(userId);
		Gson gson = new Gson();
		return gson.toJson(memoCatalogList);
		//return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
	}

	@Override
	public String getMemoCatalogList(String userId) {
		List<MemoCatalog> memoCatalogList = new ArrayList<MemoCatalog>();
		memoCatalogList = mobileCommonService.getMemoCatalogList(userId);
		Gson gson = new Gson();
		return gson.toJson(memoCatalogList);
		
	}

	@Override
	public String deleteMyMemoList(String memoFileNameList) {
		String[] memoFileName = memoFileNameList.split(",");
		for(String memoEntityName : memoFileName){
			MemoDB memoQuery = new MemoDB();
			memoQuery.setMemoFileName(memoEntityName);
			mobileCommonService.deleteMyMemo(memoQuery);
		}
		return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
	}

	@Override
	public String thirdPartyLogin(String openId, String sign) {
		StringBuilder sb = new StringBuilder();
		String userId = null;
		Map<String, String> map = new HashMap<String, String>();
		if (Authentication.authenticate(sign)) {
			
			if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))
					&& StringUtil.isEmpty(Config.getString("qymm"))){
				//if(true){
				String resultXml = null;
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(ICaService.class);
				factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
				//factory.setAddress("http://10.71.33.72:8080/zfwebservice/caService?wsdl");//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				
				ICaService service = (ICaService) factory.create();
				resultXml =service.getTicketByOpenid(openId);
				
				try {
					if(containBindErrorCode(resultXml)){
						//sb.append("<ResultInfo><code>404</code><message>用户名或密码错误！</message></ResultInfo>");
						sb.append("<ResultInfo><code>404</code><message>"+getErrorCode(resultXml)+"</message></ResultInfo>");
						return sb.toString();
					}
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Document document;
				try {
					document = DocumentHelper.parseText(resultXml);
					Element root = document.getRootElement();  
					Element MH = root.element("MH");
					logger.error("yhm:"+MH.elementText("YHM"));
					userId = MH.elementText("YHM");
					
					try {
						map = getMapValue(resultXml);//获取ca认证返回过来的值
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				ThirdPartyEntity thirdEntity = new ThirdPartyEntity();
				thirdEntity.setOpenId(openId);
				List<ThirdPartyEntity> thirdEntityList = mobileCommonService.selectThirdParty(thirdEntity);
				if(thirdEntityList == null || thirdEntityList.size() == 0){
					sb.append("<ResultInfo><code>404</code><message>对不起，您无权访问！</message></ResultInfo>");
					return sb.toString();
				}
				userId = thirdEntityList.get(0).getUserId();
			}
			if(StringUtil.isEmpty(userId)){
				sb.append("<ResultInfo><code>404</code><message>对不起，您无权访问！</message></ResultInfo>");
				return sb.toString();
			}
			LoginModel model = new LoginModel();
			model.setYhm(userId);
			User user = loginService.cxYhxx(model);
			
			YhglModel yhglModel = new YhglModel();
			yhglModel.setZgh(userId);
			String keyCode = null;
			try {
				keyCode = MD5Util.md5Encode(
						userId + 
						Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
						System.currentTimeMillis()
				);
				if(StringUtils.isEmpty(keyCode))
					throw new Exception("产生秘钥异常!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			yhglModel.setStrKey(keyCode);
			loginService.updateStrKey(yhglModel);
			
			if (user != null) {
				if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
					sb.append("<ResultInfo><code>404</code><message>帐号没有被启用！</message></ResultInfo>");
					return sb.toString();
				}
				String dqxnxq = "";
				String loginXML = "";
				String appName = MobileSystemHolder.getPropertiesValue("app_name");
				
				sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><zfsoft><mh>");
				sb.append("<xm>").append(user.getXm()).append("</xm>");
				if ("student".equals(user.getYhlx())) {
					sb.append("<role>XS</role>");
				} else {
					sb.append("<role>JS</role>");
				}
				if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
					sb.append("<bm>").append(map.containsKey("BM") ? map.get("BM") : null).append("</bm>");
				}else{
					sb.append("<bm>").append((StringUtil.isEmpty(user.getBmmc()) ? "" : user.getBmmc())).append("</bm>");
				}
				sb.append("<yhm>").append(user.getYhm()).append("</yhm>");
				sb.append("<appname>").append(appName).append("</appname>");
				sb.append("<xy></xy>");
				sb.append("<zydm></zydm>");
				sb.append("<zymc></zymc>");
				sb.append("<bj></bj>");
				sb.append("<nj></nj>");
				if(!StringUtil.isEmpty(Config.getString("webservice.host.jw"))){
					sb.append("<dqxnxq>"+dqxnxq+"</dqxnxq>");
				}else{
					sb.append("<dqxnxq></dqxnxq>");
				}
				sb.append("<app_token>" + keyCode + "</app_token>");
				sb.append("</mh></zfsoft>");
			}else{
				sb.append("<ResultInfo><code>404</code><message>此帐号密码与移动信息服务管理平台不匹配！</message></ResultInfo>");
			}
			
			return sb.toString();
		}else{
			return "对不起，您无权访问";
		}
	}

	@Override
	public String deleteThirdParty(String userId, String type) {
		if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
			String resultXml = null;
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(ICaService.class);
			factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
			//factory.setAddress("http://10.71.33.72:8080/zfwebservice/caService?wsdl");//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			
			ICaService service = (ICaService) factory.create();
			resultXml =service.unbindUser(userId, type);
			try {
				if(containRightCode(resultXml))
					return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
			} catch (DocumentException e) {
				e.printStackTrace();
				
			}
		}else{
			ThirdPartyEntity thirdEntity = new ThirdPartyEntity();
			thirdEntity.setUserId(userId);
			thirdEntity.setType(type);
			mobileCommonService.deleteThirdParty(thirdEntity);
			return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
		}
		return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
	}

	@Override
	public String submitThirdParty(String userId, String openId, String type) {
		Gson gson = new Gson();
		if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
			//if(true){
			String resultXml = null;
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(ICaService.class);
			factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
			//factory.setAddress("http://10.71.33.72:8080/zfwebservice/caService?wsdl");//接口地址
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			
			ICaService service = (ICaService) factory.create();
			resultXml =service.bindUser(userId, openId, type);
			try {
				if(containRightCode(resultXml))
					return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
			} catch (DocumentException e) {
				//e.printStackTrace();
				logger.error(e);
				
			}
		}else{
			ThirdPartyEntity thirdEntity = new ThirdPartyEntity();
			thirdEntity.setOpenId(openId);
			thirdEntity.setUserId(userId);
			thirdEntity.setType(type);
			mobileCommonService.deleteThirdParty(thirdEntity);
			mobileCommonService.insertThirdParty(thirdEntity);
			return "{\"success\":\"200\", \"msg\":\"操作成功\"}";
		}
		FailureEntity failureEntity = new FailureEntity();
		failureEntity.failure = "ca接口获取第三方认证关系异常！";
		return gson.toJson(failureEntity);
	}

	@Override
	public String getThirdPartyList(String userId) {
		Gson gson = new Gson();
		List<ThirdPartyEntity> thirdEntityList = new ArrayList<ThirdPartyEntity>();
		if(!StringUtil.isEmpty(Config.getString("webservice.host.mh"))){
			//if(true){
			String zfxml = "";
			try {
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(ICaService.class);
				factory.setAddress(WebServiceConf.SERVICE_MHSERVICE);//接口地址
				//factory.setAddress("http://10.71.33.72:8080/zfwebservice/caService?wsdl");//接口地址
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				
				ICaService service = (ICaService) factory.create();
				zfxml =service.getBindings(userId);
				
				Document document = DocumentHelper.parseText(zfxml);
		        Element root = document.getRootElement();  
		        Element data = root.element("data");
		        List<Element> bindings = data.elements("bindings");
		        
		        ThirdPartyEntity thirdEntity;
		        if(bindings != null && bindings.size() > 0){
		        	for (int i = 0; i < bindings.size(); i++) {
		        		thirdEntity = new ThirdPartyEntity();
		        		thirdEntity.setUserId(bindings.get(i).elementText("yhm"));
		        		thirdEntity.setOpenId(bindings.get(i).elementText("openid"));
		        		thirdEntity.setType(bindings.get(i).elementText("opentype"));
		        		thirdEntityList.add(thirdEntity);
		        	}
		        }
				
			} catch (Exception e) {
				logger.error(e);
				FailureEntity failureEntity = new FailureEntity();
				failureEntity.failure = "ca接口获取第三方认证关系异常！";
				return gson.toJson(failureEntity);
			}
		}else{
			ThirdPartyEntity thirdEntity = new ThirdPartyEntity();
			thirdEntity.setUserId(userId);
			thirdEntityList = mobileCommonService.selectThirdParty(thirdEntity);
		}
		return gson.toJson(thirdEntityList);
		
		
	}

	@Override
	public String getSchoolSuggestList(String textContent,
			String versionNumber, String schoolCode, String userName,String pageIndex,
			String strKey) {
		DBEncrypt p = new DBEncrypt();
		Gson gson = new Gson();
		List<com.zfsoft.mobile.suggest.entity.SuggestEntity> suggestList = null;
//		if(!strKey.equals(p.eCode(versionNumber+schoolCode+userName))){
//			ResultEntity result = new ResultEntity<List<com.zfsoft.mobile.suggest.entity.SuggestEntity>>(0, "you has no privilege!", suggestList);
//			return gson.toJson(result);
//		}
		com.zfsoft.mobile.suggest.entity.SuggestEntity query = new com.zfsoft.mobile.suggest.entity.SuggestEntity();
		query.setSchoolCode(schoolCode);
		query.setUserName(userName);
		query.setVersionNumber(versionNumber);
		query.setTextContent(textContent);
		//query.setToPage(Integer.parseInt(pageIndex));
		suggestList = suggestService.getAllList(query);
		ResultEntity result = new ResultEntity<List<com.zfsoft.mobile.suggest.entity.SuggestEntity>>(1, "成功!", suggestList);
		return gson.toJson(result);
	}

	public void setSuggestService(ISuggestService suggestService) {
		this.suggestService = suggestService;
	}

	public ISuggestService getSuggestService() {
		return suggestService;
	}

	@Override
	public String getXfyjList() {
		List<XfyjEntity> list = xfyjService.getList();
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><xfyjList>");
        if(list != null){
        	for (XfyjEntity xfyjEntity : list) {
        		sb.append("<module>");
        		sb.append("<id>" + xfyjEntity.getId() + "</id>");
        		sb.append("<name>" + xfyjEntity.getName() + "</name>");
        		sb.append("</module>");
        	}
        }
        sb.append("</xfyjList>");
        return sb.toString();
	}

	@Override
	public String getXfyjDetails(String id,String userName,String start,String size) {
		XfyjxqQueryEntity query = new XfyjxqQueryEntity();
		query.setXfyjId(id);
		query.setZgh(userName);
		query.setToPage(Integer.valueOf(start));
		query.setPerPageSize(Integer.valueOf(size));
		
		List<XfyjxqEntity> list = xfyjService.getDetails(query);
		StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><xfyjDetails>");
        if(list != null){
        	for (XfyjxqEntity xfyjxqEntity : list) {
        		sb.append("<module>");
        		sb.append("<id>" + xfyjxqEntity.getId() + "</id>");
        		sb.append("<dataName>" + xfyjxqEntity.getDataName() + "</dataName>");
        		sb.append("<dataCon>" + xfyjxqEntity.getDataCon() + "</dataCon>");
        		sb.append("<color>" + xfyjxqEntity.getColor() + "</color>");
        		
        		sb.append("</module>");
        	}
        }
        sb.append("</xfyjDetails>");
        return sb.toString();
	}
	
	/**
	 * 获取食堂商家列表
	 */
	@Override
	public String getCanteenList(String canteenname,String startStr,String sizeStr){
		return null;
	}
	
	
	
	/**
	 * 根据商家id获取该商家所有食品类别及该类别下的食品列表----不分页
	 */
	public String getCanteenDetailList(String canteenId){
		return null;
	}
	
	/**
	 * 食堂下单
	 */
	public String placeOrder(String data){
		return null;
	}
	
	
	/**
	 * 根据用户账号获取该用户的所有订单
	 */
	public String getOrderlistByUser(String username,String startStr,String sizeStr){
		return null;
	}
	
	/**
	 * 根据用户账号和订单id订单详情
	 */
	public String findOrderDetail(String username,String orderId){
		return null;
	}
	
	
	
	/**
	 *根据用户id获取当前用户的送餐地址列表----分页
	 */
	public String getAddressListByUser(String username,String start,String size){
	    return null;
	}
	
	/**
	 * 新增或者修改并保存送餐地址
	 */
	public String submitAddressForUser(String addressId,String userId,String name,String mobilePhone,String schoolName,String specificAddress){
		return null;
	}
	
	/**
	 * 根据用户账号删除送餐地址
	 */
	public String deleteAddressByUser(String addressId){
		return null;
	}

}
