package com.zfsoft.mobile.suggest.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.anysoft.webservicetest.encoder.DBEncrypt;
import com.common.Config;
import com.common.WebServiceConf;
import com.google.gson.Gson;
import com.newmobile.login.CXFService.IWSSerServicePortType;
import com.newmobile.login.service.IWSSerService;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.suggest.entity.SuggestEntity;
import com.zfsoft.mobile.suggest.service.ISuggestService;
import com.zfsoft.util.base.StringUtil;

public class SuggestAction extends HrmAction {

	private static final Logger log = Logger.getLogger(SuggestAction.class);
	private ISuggestService suggestService;
	private SuggestEntity query = new SuggestEntity();
	private PageList<SuggestEntity> pageList;
	private SuggestEntity model;
	private String op = "add";
	
	public String list(){
//		String schoolCode = Config.getString("schoolCode");
//		if(!StringUtil.isEmpty(schoolCode)){
//			getValueStack().set("hasSchoolCode", "1");
//		}else{
//			getValueStack().set("hasSchoolCode", "0");
//		}
//		query.setSchoolCode(schoolCode);
		query.setIsApp("0");
		pageList = suggestService.getList(query);
		return "list";
	}
	
	public String outList(){
		String str=null;
		log.error("suggestaction---getSuggestList：" +
				"query.schoolCode="+query.getSchoolCode() + 
				"query.textContent="+query.getTextContent() +
				"query.userName="+query.getUserName() + 
				"query.versionNumber="+query.getVersionNumber()
			);
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWSSerServicePortType.class);
//		String portal_adress =  Config.getString("portal_adress","http://portal.zfsoft.com:9090/zftal-mobile/")
//		+"service/IWSSerService";
//		factory.setAddress(portal_adress);//接口地址
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		IWSSerServicePortType service = (IWSSerServicePortType) factory.create();
//		DBEncrypt p = new DBEncrypt();
//		str =service.getSchoolSuggestList(
//				query.getTextContent(),
//				query.getVersionNumber(),
//				Config.getString("schoolCode"),
//				query.getUserName(),
//				String.valueOf(query.getToPage()),
//				p.eCode(query.getVersionNumber()+query.getSchoolCode()+query.getUserName())
//			);
		
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
        bean.setServiceClass(IWSSerService.class);  
        String portal_adress =  Config.getString("portal_adress","http://portal.zfsoft.com:9090/zftal-mobile/");
        portal_adress += "webservice/WSSerService?wsdl";
        //bean.setAddress(WebServiceConf.SERVICE_NEWMOBILESERVICE);  
        bean.setAddress(portal_adress);
        String schoolCode = Config.getString("schoolCode");
        if(StringUtil.isEmpty(schoolCode)){
        	User user = (User) getRequest().getSession().getAttribute(BaseAction.USER_INFO_KEY);
        	schoolCode = user.getYhm();
        }
        IWSSerService helloWorldService = (IWSSerService)bean.create();  
        str = helloWorldService.getSchoolSuggestList(
        				query.getTextContent(),
        				query.getVersionNumber(),
        				schoolCode,
        				query.getUserName(),
        				String.valueOf(query.getToPage()),
        				"WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS"
        				);  
		
		
		Gson gson = new Gson();
		ResultEntity resultEntity = gson.fromJson(str, ResultEntity.class);
		log.error("获取getThirdPartyList结果："+resultEntity);
		ArrayList<SuggestEntity> suggestList = (ArrayList<SuggestEntity>) resultEntity.data;
		getValueStack().set("suggestList", suggestList);
		return "outList";
	}
	
	public String getMySuggestList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String schoolCode = request.getParameter("schoolCode");
		String userName   = request.getParameter("userName");
		query.setSchoolCode(schoolCode);
		query.setUserName(userName);
		query.setIsApp("1");
		pageList = suggestService.getList(query);
		return "appList";
		
	}
	
	public String getDetail(){
		model = suggestService.getDetail(query);
		model.setSuggestPictureList(suggestService.getPictureList(query.getId()));
		return "detail";
	}
	
	public String toReply(){
		query = suggestService.getDetail(query);
		return "toReply";
	}
	
	public String reply(){
		suggestService.reply(query);
		this.setSuccessMessage("回复成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public void setSuggestService(ISuggestService suggestService) {
		this.suggestService = suggestService;
	}

	public ISuggestService getSuggestService() {
		return suggestService;
	}

	public SuggestEntity getQuery() {
		return query;
	}

	public void setQuery(SuggestEntity query) {
		this.query = query;
	}

	public PageList<SuggestEntity> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<SuggestEntity> pageList) {
		this.pageList = pageList;
	}

	public SuggestEntity getModel() {
		return model;
	}

	public void setModel(SuggestEntity model) {
		this.model = model;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
}
