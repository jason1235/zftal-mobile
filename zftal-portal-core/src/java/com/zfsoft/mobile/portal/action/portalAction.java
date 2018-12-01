package com.zfsoft.mobile.portal.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.portal.entity.Card;
import com.zfsoft.mobile.portal.entity.bankCard;
import com.zfsoft.mobile.portal.entity.bookDetail;
import com.zfsoft.mobile.portal.entity.bookerDetail;
import com.zfsoft.mobile.portal.entity.businessFrom;
import com.zfsoft.mobile.portal.service.IPortalService;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.service.svcinterface.ILoginService;

public class portalAction  extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2894237722284061558L;
	
	private IPortalService portalService;
	
	private IServiceManagerService serviceManagerService;
	
	private IMobileCommonService mobileCommonService;
	
	private bookDetail query = new bookDetail();
	
	private String uid = "";

	public void setPortalService(IPortalService portalService) {
		this.portalService = portalService;
	}

	public IPortalService getPortalService() {
		return portalService;
	}
	
	
	
	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}

	public void setServiceManagerService(
			IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}
	
	

	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * 一卡通
	 */
	public String cardlist(){
		String yhm = SessionFactory.getUser().getYhm();
		Card card = portalService.getCardNumber(yhm);
		getValueStack().set("card", card);
		//User user = SessionFactory.getUser();
		
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
		
		if(card != null){
			//float yue = portalService.getYue(card.getClassKh());
			float yue = card.getClassYue();
			getValueStack().set("yue", yue);
		}
		
		return "cardlist";
	}
	
	/**
	 * 自助缴费
	 */
	public String paymyself(){
		return "paymyself";
	}
	
	/**
	 * 我的门户
	 */
	public String portal(){
		//start 应付演示
		/*LoginModel model = new LoginModel();
		model.setYhm("255");
		ILoginService loginService = (ILoginService) SpringHolder.getBean("loginService");
		User user = loginService.cxYhxx(model);
		HttpSession session = getSession();
		session.setAttribute(USER_INFO_KEY, user);*/
		
		//end 应付演示
		return "portal";
	}
	
	/**
	 * 缴费
	 */
	public String pay(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
		
		String yhm = SessionFactory.getUser().getYhm();
		bankCard bankQuery = new bankCard();
		bankQuery.setClassRyh(yhm);
		ArrayList<bankCard> bankCards = portalService.getBankCard(bankQuery);
		getValueStack().set("bankCards", bankCards);
		return "pay";
	}
	
	/**
	 * 一卡通充值
	 */
	public String cardCharge(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	
		String param = request.getParameter("param");
		getValueStack().set("param", param);
		
		String yhm = SessionFactory.getUser().getYhm();
		bankCard bankQuery = new bankCard();
		bankQuery.setClassRyh(yhm);
		//bankQuery.setClassIscheck("1");
		ArrayList<bankCard> bankCards = portalService.getBankCard(bankQuery);
		getValueStack().set("bankCards", bankCards);
		return "cardCharge";
	}
	
	/**
	 * 流水及账单
	 * @throws UnsupportedEncodingException 
	 */
	public String businessFrom() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	
		String dateTime = request.getParameter("date");
		dateTime = dateTime == null ? (new Date()).toString() : new String(dateTime.getBytes("iso-8859-1"),"utf-8").substring(0, 15);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		if(dateTime != null){
			dateTime = format.format(new Date(dateTime));
			getValueStack().set("dateTime", dateTime);
			//dateTime = dateTime + "-01";
		}
		//Date date = dateTime == null ?  null : new Date(dateTime);
		String yhm = SessionFactory.getUser().getYhm();
		businessFrom businessQuery = new businessFrom();
		businessQuery.setClassRyh(yhm);
		businessQuery.setClassDate(dateTime);
		ArrayList<businessFrom> businessList = portalService.getBusinessFrom(businessQuery);
		getValueStack().set("businessList", businessList);
		
		float totalIn = 0,totalOut = 0;
		for(businessFrom business : businessList){
			if(business.getClassOutIn().equals("in"))
				totalIn += business.getClassJye();
			else
				totalOut += business.getClassJye();
		}
		getValueStack().set("totalIn", totalIn);
		getValueStack().set("totalOut", totalOut);
		
		return "businessFrom";
	}
	/**
	 * 挂失
	 */
	public String getLoss(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	
		String yhm = SessionFactory.getUser().getYhm();
		Card card = portalService.getCardNumber(yhm);
		getValueStack().set("card", card);
		
		return "getLoss";
	}
	
	/**
	 * 图书馆
	 * @return
	 * @throws ParseException
	 */
	public String booklist() throws ParseException{
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	
    	
		String findli = request.getParameter("findli") ;
		findli = findli == null ? "all" : findli; 
		getValueStack().set("findli", findli);
		
		String yhm = SessionFactory.getUser().getYhm();
		query.setClassYrh(yhm);
		
		ArrayList<bookDetail> allBooks;
		//String warnDays = MobileSystemHolder.getPropertiesValue("warndate");
		String warnDays = Config.getString("warndate", "5");
		int warndate = warnDays != null ? Integer.parseInt(warnDays) : 5;
		query.setWarnDays(warndate);
		if("warn".equals(findli)){
			allBooks = portalService.getAllWarnBooks(query);
		}else if("late".equals(findli)){
			allBooks = portalService.getAllLateBooks(query);
		}else{
			allBooks = portalService.getAllBooks(query);
		}
		
		/*ArrayList<bookDetail> allBooks = portalService.getAllBooks(query);
		String warnDays = MobileSystemHolder.getPropertiesValue("warndate");
		int warndate = warnDays != null ? Integer.parseInt(warnDays) : 5;
		query.setWarnDays(warndate);
		ArrayList<bookDetail> warnBooks = portalService.getAllWarnBooks(query);
		ArrayList<bookDetail> lateBooks = portalService.getAllLateBooks(query);*/
		
		ArrayList<bookerDetail> books = portalService.getBooker(yhm);
		bookerDetail booker = books != null && books.size() > 0 ?
							  books.get(0) : new bookerDetail() ;
		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		/*for (int i = 0; i < allBooks.size(); i++) {
			boolean isOrNot = false;
			for(bookDetail bookLate : lateBooks){
				if(bookLate.equals(allBooks.get(i))){
					allBooks.get(i).setClassIsLate(0);
					isOrNot = true;
					break;
				}
			}
			for(bookDetail bookLate : warnBooks){
				if(bookLate.equals(allBooks.get(i))){
					allBooks.get(i).setClassIsLate(1);
					isOrNot = true;
					break;
				}  
			}
			if(!isOrNot){
				String sfh = allBooks.get(i).getClassSfgh();
				allBooks.get(i).setClassIsLate(!allBooks.get(i).getClassSfgh().equals("1") ? 2 : 3);
			}
		    
		}*/
		getValueStack().set("allBooks", allBooks);
		//getValueStack().set("lateBooks", lateBooks);
		//getValueStack().set("warnBooks", warnBooks);
		getValueStack().set("booker", booker);
		return "list";
	}
	
	/**
	 * 科研发布服务列表目录
	 * @return
	 */
	public String srlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	
		List<ServiceManager> services = serviceManagerService.getKyInfoClassService();
		
		if (services != null && services.size() > 0) {
			services = mobileCommonService.getOpenUrl(services, uid);
			String host = getImageHost();
			for (ServiceManager service : services) {
				String logo = service.getClassFwtbdz();
				
				boolean check = false;
				try {
					check = mobileCommonService.checkImage(logo, service.getClassFwtbid());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				if(check) {
					if (logo != null && !logo.startsWith("http")) {
						service.setClassFwtbdz(host + logo);
					}
				} else {
					service.setClassFwtbdz(host+"upload/default_image.jpg");
				}
				if("2".equals(type)){
					service.setAppUrl(service.getAppUrl().replace("type=1", "type=2"));
				}
					
			}
		}
		
		this.getValueStack().set("list", services);
		return "srlist";
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
	
	public String getBookDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
    	
    	String type = request.getParameter("type");
    	if(!StringUtils.isEmpty(type))
    		getValueStack().set("type", type);
    	
		String dzzh = request.getParameter("dzzh");
		String txm = request.getParameter("txm");
		String jcsj = request.getParameter("jcsj");
		
		String yhm = SessionFactory.getUser().getYhm();
		query.setClassJcsj(jcsj);
		query.setClassTxm(txm);
		query.setClassDzzh(dzzh);
		query.setClassYrh(yhm);
		ArrayList<bookDetail> books = portalService.getDetailBook(query);
		if(books != null && books.size() == 1)
			getValueStack().set("bookDetail", books.get(0));
		return "detail";
	}

	public void setQuery(bookDetail query) {
		this.query = query;
	}

	public bookDetail getQuery() {
		return query;
	}
	

}
